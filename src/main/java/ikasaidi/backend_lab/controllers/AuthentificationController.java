package ikasaidi.backend_lab.controllers;


import ikasaidi.backend_lab.DTO.Connexion;
import ikasaidi.backend_lab.DTO.ReponseAuthentification;
import ikasaidi.backend_lab.services.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@CrossOrigin()
public class AuthentificationController {
    @Autowired
    private AuthentificationService authentificationService;

    @PostMapping("/login")
    public ResponseEntity<ReponseAuthentification> login(@RequestBody Connexion request) {
        ReponseAuthentification auth = authentificationService.connexion(request);

        if(!auth.isSuccess()){
            return ResponseEntity.status(401).body(auth);
        }
        return ResponseEntity.ok(auth);
    }

}