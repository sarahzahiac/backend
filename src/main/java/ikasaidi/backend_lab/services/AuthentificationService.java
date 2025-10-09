package ikasaidi.backend_lab.services;

import ikasaidi.backend_lab.DTO.Connexion;
import ikasaidi.backend_lab.DTO.ReponseAuthentification;
import ikasaidi.backend_lab.repositories.PersonRepository;
import ikasaidi.backend_lab.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthentificationService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private JwtUtil jwtUtil;

    public ReponseAuthentification connexion(Connexion request) {
        return personRepository.findByEmail(request.getEmail()).map(person -> {
            if (person.getPassword() == null || !person.getPassword().equals(request.getPassword())) {
                return new ReponseAuthentification(false, "Mot de passe incorrect", null, null, null);
            }
            String token = jwtUtil.generateToken(person.getEmail());
            return new ReponseAuthentification(true, "Connexion réussie!", person.getId(), person.getName(), token);
        }).orElse(new ReponseAuthentification(false, "Email introuvable...", null, null, null));
    }
}


//package ikasaidi.backend_lab.services;
//
//import ikasaidi.backend_lab.DTO.Connexion;
//import ikasaidi.backend_lab.DTO.ReponseAuthentification;
//import ikasaidi.backend_lab.repositories.PersonRepository;
//import ikasaidi.backend_lab.utils.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthentificationService {
//
//    @Autowired
//    private PersonRepository personRepository;
//    @Autowired
//    private JwtUtil jwtUtil;
/// /    @Autowired
/// /    BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    public ReponseAuthentification connexion(Connexion request) {
//        return personRepository.findByEmail(request.getEmail())
//                .map(person -> {
//                    if (person.getPassword()== null || !bCryptPasswordEncoder.matches(request.getPassword(), person.getPassword()))  {
//                        return new ReponseAuthentification(false, "Mot de passe incorrect", null, null, null);
//                    }
//                    String token = jwtUtil.generateToken(person.getEmail());
//
//                    return new ReponseAuthentification(
//                            true, "Connexion réussie!",
//                            person.getId(),
//                            person.getName(),
//                            token
//                    );
//                })
//                .orElse(new ReponseAuthentification(false, "Email introuvable...", null, null, null));
//    }
//}