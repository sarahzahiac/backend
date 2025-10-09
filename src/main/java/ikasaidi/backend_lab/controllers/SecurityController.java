package ikasaidi.backend_lab.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @GetMapping("/secure/token")
    public String getSecurityData() {
        return "C'est securisé!";
    }
}