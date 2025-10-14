package ikasaidi.backend_lab.Securiter;

import ikasaidi.backend_lab.utils.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Classe de configuration principale pour la sécurité de l'application.
 *
 * Cette configuration utilise Spring Security pour :
 * <ul>
 *     <li>Désactiver la gestion d’état de session (mode stateless avec JWT).</li>
 *     <li>Configurer les autorisations d’accès aux endpoints REST.</li>
 *     <li>Gérer les requêtes CORS entre le frontend (React) et le backend.</li>
 *     <li>Intégrer un filtre JWT personnalisé pour la validation des tokens.</li>
 * </ul>
 *
 *
 *
 * Les endpoints publics sont :
 * <ul>
 *     <li><code>/auth/**</code> — pour la connexion et l’inscription</li>
 *     <li><code>/series/**</code>, <code>/persons/**</code>, <code>/vues/**</code>, <code>/ratings/**</code></li>
 * </ul>
 * Tous les autres endpoints nécessitent une authentification via un token JWT valide.
 *
 *
 * @author Aya
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /** Filtre JWT personnalisé pour l’authentification des requêtes. */
    private final JwtFilter jwtFilter;

    /**
     * Constructeur injectant le filtre JWT.
     *
     * @param jwtFilter filtre utilisé pour valider les tokens JWT
     */
    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    /**
     * Configure la chaîne de filtres de sécurité de l'application.
     *
     * Cette méthode :
     * <ul>
     *     <li>Désactive la protection CSRF (inutile pour les API REST).</li>
     *     <li>Active la configuration CORS personnalisée.</li>
     *     <li>Définit le mode stateless (aucune session côté serveur).</li>
     *     <li>Spécifie les routes publiques et protégées.</li>
     *     <li>Ajoute le filtre JWT avant le filtre d’authentification standard.</li>
     * </ul>
     *
     *
     * @param http l’objet {@link HttpSecurity} configuré par Spring
     * @return la chaîne de filtres {@link SecurityFilterChain}
     * @throws Exception si une erreur survient lors de la configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Désactivation des protections CSRF (non nécessaires pour une API REST)
                .csrf(csrf -> csrf.disable())
                // Activation du CORS avec la configuration personnalisée
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Mode stateless : aucune session stockée côté serveur
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Définition des autorisations d’accès
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/auth/**", "/series/**", "/persons/**", "/vues/**", "/ratings/**", "/ratings/episode/**").permitAll()
                        .anyRequest().authenticated()
                )
                // Ajout du filtre JWT avant l’authentification standard
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Définit la configuration CORS pour permettre la communication entre le backend
     * et le frontend (par défaut : React sur <code>localhost:5173</code>).
     *
     * @return la configuration CORS à appliquer à l’application
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * Définit le bean utilisé pour le chiffrement des mots de passe utilisateurs.
     *
     * @return instance de {@link BCryptPasswordEncoder}
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
