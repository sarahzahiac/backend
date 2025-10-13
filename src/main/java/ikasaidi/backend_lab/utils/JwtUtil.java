package ikasaidi.backend_lab.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Utilitaire pour la gestion des tokens JWT (JSON Web Token).
 *
 * Cette classe fournit les méthodes nécessaires pour :
 * <ul>
 *     <li>Générer un token JWT sécurisé contenant l’adresse courriel de l’utilisateur.</li>
 *     <li>Valider et décoder un token existant afin d’en extraire les informations (claims).</li>
 * </ul>
 *
 *
 *
 * Les paramètres du token (clé secrète et durée d’expiration) sont définis dans le fichier
 * <strong>application.properties</strong> :
 * <pre>
 * jwt.secret = ta_chaine_secrete
 * jwt.expiration = 86400000
 * </pre>
 *
 *
 *
 * Cette classe est utilisée par :
 * <ul>
 *     <li>{@link ikasaidi.backend_lab.utils.JwtFilter} pour valider les tokens à chaque requête HTTP.</li>
 *     <li>{@link ikasaidi.backend_lab.services.AuthentificationService} pour générer un token lors de la connexion ou de l’inscription.</li>
 * </ul>
 *
 *
 * @author Aya
 * @version 1.0
 */
@Component
public class JwtUtil {

    /** Clé secrète utilisée pour signer et vérifier les tokens JWT. */
    @Value("${jwt.secret}")
    private String secret;

    /** Durée d’expiration du token (en millisecondes). */
    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * Génère la clé de signature HMAC à partir de la clé secrète configurée.
     *
     * @return la clé secrète de type {@link SecretKey}
     */
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Génère un token JWT pour un utilisateur donné.
     *
     * Le token contient :
     * <ul>
     *     <li>Le sujet : l’adresse courriel de l’utilisateur.</li>
     *     <li>La date d’émission (issuedAt).</li>
     *     <li>La date d’expiration (déterminée par la propriété <code>jwt.expiration</code>).</li>
     * </ul>
     * Le token est signé avec l’algorithme HMAC SHA en utilisant la clé secrète configurée.
     *
     *
     * @param email l’adresse courriel de l’utilisateur (sera stockée comme sujet du token)
     * @return le token JWT généré sous forme de chaîne
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSecretKey())
                .compact();
    }

    /**
     * Valide un token JWT et retourne ses informations (claims).
     *
     * Si le token est expiré ou invalide, une exception sera levée
     * (gérée par {@link ikasaidi.backend_lab.utils.JwtFilter}).
     *
     *
     * @param token le token JWT à valider
     * @return un objet {@link Claims} contenant les informations extraites du token
     */
    public Claims validationToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
