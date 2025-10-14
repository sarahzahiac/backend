package ikasaidi.backend_lab.services;

import ikasaidi.backend_lab.DTO.Connexion;
import ikasaidi.backend_lab.DTO.ReponseAuthentification;
import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.repositories.PersonRepository;
import ikasaidi.backend_lab.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service responsable de la gestion de l’authentification des utilisateurs.
 *
 * Cette classe fournit deux principales fonctionnalités :
 * <ul>
 *     <li>La connexion d’un utilisateur existant en vérifiant son mot de passe et en générant un token JWT.</li>
 *     <li>L’inscription d’un nouvel utilisateur avec hachage sécurisé du mot de passe.</li>
 * </ul>
 *
 *
 * Elle interagit principalement avec :
 * <ul>
 *     <li>{@link PersonRepository} pour accéder aux données des utilisateurs.</li>
 *     <li>{@link JwtUtil} pour générer les tokens JWT.</li>
 *     <li>{@link BCryptPasswordEncoder} pour le chiffrement des mots de passe.</li>
 * </ul>
 *
 *
 * @author Aya
 * @version 1.0
 */
@Service
public class AuthentificationService {

    /** Repository permettant l’accès et la manipulation des données des utilisateurs. */
    @Autowired
    private PersonRepository personRepository;

    /** Utilitaire de gestion des tokens JWT (génération et validation). */
    @Autowired
    private JwtUtil jwtUtil;

    /** Encodeur de mots de passe utilisant l’algorithme BCrypt pour plus de sécurité. */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Gère la connexion d’un utilisateur existant.
     *
     * Cette méthode :
     * <ul>
     *     <li>Recherche l’utilisateur par son email.</li>
     *     <li>Vérifie la validité du mot de passe (haché ou en clair selon le cas).</li>
     *     <li>Génère un token JWT si la connexion est réussie.</li>
     * </ul>
     *
     *
     * @param request objet {@link Connexion} contenant l’email et le mot de passe saisis par l’utilisateur
     * @return un objet {@link ReponseAuthentification} indiquant le succès ou l’échec de la connexion
     */
    public ReponseAuthentification connexion(Connexion request) {
        return personRepository.findByEmail(request.getEmail())
                .map(person -> {
                    boolean passwordOk = false;

                    // Vérifie si le mot de passe est haché avec BCrypt
                    if (person.getPassword().startsWith("$2a$")) {
                        passwordOk = passwordEncoder.matches(request.getPassword(), person.getPassword());
                    } else {
                        passwordOk = person.getPassword().equals(request.getPassword());
                    }

                    if (!passwordOk) {
                        return new ReponseAuthentification(false, "Mot de passe incorrect", null, null, null);
                    }

                    // Génération du token JWT pour l'utilisateur connecté
                    String token = jwtUtil.generateToken(person.getEmail());
                    return new ReponseAuthentification(true, "Connexion réussie!", person.getId(), person.getName(), token);
                })
                .orElse(new ReponseAuthentification(false, "Email introuvable...", null, null, null));
    }

    /**
     * Gère l’inscription d’un nouvel utilisateur.
     *
     * Cette méthode :
     * <ul>
     *     <li>Vérifie si l’email n’est pas déjà utilisé.</li>
     *     <li>Crée un nouvel utilisateur avec les informations reçues.</li>
     *     <li>Hache le mot de passe avant de l’enregistrer en base de données.</li>
     *     <li>Retourne une réponse avec un token JWT généré pour le nouvel utilisateur.</li>
     * </ul>
     *
     *
     * @param request objet {@link Connexion} contenant les informations d’inscription (nom, email, mot de passe, genre)
     * @return un objet {@link ReponseAuthentification} indiquant le succès ou l’échec de l’inscription
     */
    public ReponseAuthentification register(Connexion request) {
        // Vérifie si l’email est déjà enregistré
        if (personRepository.findByEmail(request.getEmail()).isPresent()) {
            return new ReponseAuthentification(false, "Cet email est déjà utilisé", null, null, null);
        }

        // Création du nouvel utilisateur
        Person newUser = new Person();
        newUser.setEmail(request.getEmail());
        newUser.setName(request.getName());
        newUser.setGender(request.getGender());
        newUser.setPassword(passwordEncoder.encode(request.getPassword())); // mot de passe haché avec BCrypt

        // Enregistrement et génération du token
        Person saved = personRepository.save(newUser);
        String token = jwtUtil.generateToken(saved.getEmail());

        return new ReponseAuthentification(true, "Utilisateur créé avec succès", saved.getId(), saved.getName(), token);
    }
}
