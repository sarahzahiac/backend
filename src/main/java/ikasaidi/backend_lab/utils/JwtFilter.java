package ikasaidi.backend_lab.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Filtre de sécurité JWT pour l’authentification des requêtes HTTP.
 *
 * Ce filtre intercepte chaque requête entrante (grâce à {@link OncePerRequestFilter})
 * afin de :
 * <ul>
 *     <li>Vérifier la présence d’un en-tête HTTP <code>Authorization</code> contenant un token JWT valide.</li>
 *     <li>Valider le token et en extraire les informations utilisateur.</li>
 *     <li>Configurer le contexte de sécurité de Spring pour l’utilisateur authentifié.</li>
 * </ul>
 *
 *
 *
 * En cas de token expiré ou invalide, le filtre renvoie une réponse HTTP 401 (UNAUTHORIZED)
 * sans exécuter le reste de la chaîne.
 *
 *
 *
 * Ce filtre est automatiquement enregistré dans la chaîne de sécurité via la configuration {@link ikasaidi.backend_lab.Securiter.SecurityConfig}.
 *
 *
 * @author Aya
 * @version 1.0
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    /** Utilitaire pour la génération et la validation des tokens JWT. */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Intercepte et traite chaque requête HTTP avant qu’elle atteigne les contrôleurs.
     *
     * Si un en-tête <code>Authorization</code> contenant un token JWT valide est détecté,
     * l’utilisateur est authentifié et injecté dans le {@link SecurityContextHolder}.
     *
     *
     * @param request la requête HTTP entrante
     * @param response la réponse HTTP sortante
     * @param filterChain la chaîne de filtres à exécuter
     * @throws ServletException si une erreur de filtre se produit
     * @throws IOException si une erreur d’entrée/sortie survient
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1️⃣ Récupérer l'en-tête Authorization
        String authHeader = request.getHeader("Authorization");

        // 2️⃣ Vérifier s’il contient un token JWT
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Supprime "Bearer "

            try {
                // 3️⃣ Valider le token et extraire les informations utilisateur
                Claims claims = jwtUtil.validationToken(token);

                // 4️⃣ Créer un objet d’authentification sans rôle (ici liste vide)
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(claims.getSubject(), null, Collections.emptyList());

                // 5️⃣ Injecter l’authentification dans le contexte de sécurité
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (ExpiredJwtException e) {
                // Token expiré → 401 Unauthorized
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expiré");
                return;
            } catch (Exception e) {
                // Token invalide → 401 Unauthorized
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalide");
                return;
            }
        }

        // 6️⃣ Continuer le traitement normal (autres filtres, contrôleurs, etc.)
        filterChain.doFilter(request, response);
    }
}
