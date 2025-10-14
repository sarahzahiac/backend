package ikasaidi.backend_lab.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur de démonstration pour la sécurité de l’application.
 * <p>
 * Ce contrôleur expose un seul endpoint permettant de tester
 * l’accès à une ressource sécurisée.
 * </p>
 *
 * <p>Lorsque la configuration de sécurité est active, l’accès à cette route
 * peut être restreint aux utilisateurs authentifiés via un token JWT.</p>
 *
 * @author Aya
 * @version 1.0
 */
@RestController
public class SecurityController {

    /**
     * Endpoint de test pour vérifier que la sécurité et le token JWT
     * fonctionnent correctement.
     *
     * @return un message indiquant que la ressource est sécurisée
     *
     * <ul>
     *     <li><b>200: </b>Si l'accès est autorisé</li>
     *     <li><b>401: </b>Si l'utilisateur  n'est pas authentifié</li>
     * </ul>
     */
    @GetMapping("/secure/token")
    public String getSecurityData() {
        return "C'est securisé!";
    }
}
