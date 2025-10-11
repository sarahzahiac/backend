package ikasaidi.backend_lab.services;

import ikasaidi.backend_lab.DTO.Connexion;
import ikasaidi.backend_lab.DTO.ReponseAuthentification;
import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.repositories.PersonRepository;
import ikasaidi.backend_lab.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthentificationService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //  Connexion
    public ReponseAuthentification connexion(Connexion request) {
        return personRepository.findByEmail(request.getEmail())
                .map(person -> {
                    boolean passwordOk = false;

                    if (person.getPassword().startsWith("$2a$")) {
                        passwordOk = passwordEncoder.matches(request.getPassword(), person.getPassword());
                    } else {
                        passwordOk = person.getPassword().equals(request.getPassword());
                    }

                    if (!passwordOk) {
                        return new ReponseAuthentification(false, "Mot de passe incorrect", null, null, null);
                    }

                    String token = jwtUtil.generateToken(person.getEmail());
                    return new ReponseAuthentification(true, "Connexion réussie!", person.getId(), person.getName(), token);
                })
                .orElse(new ReponseAuthentification(false, "Email introuvable...", null, null, null));
    }

    // Inscription
    public ReponseAuthentification register(Connexion request) {
        if (personRepository.findByEmail(request.getEmail()).isPresent()) {
            return new ReponseAuthentification(false, "Cet email est déjà utilisé", null, null, null);
        }

        Person newUser = new Person();
        newUser.setEmail(request.getEmail());
        newUser.setName(request.getName());
        newUser.setGender(request.getGender());
        newUser.setPassword(passwordEncoder.encode(request.getPassword())); //bcrypte le code (hachés) voir sqlite

        Person saved = personRepository.save(newUser);
        String token = jwtUtil.generateToken(saved.getEmail());

        return new ReponseAuthentification(true, "Utilisateur créé avec succès", saved.getId(), saved.getName(), token);
    }

}
