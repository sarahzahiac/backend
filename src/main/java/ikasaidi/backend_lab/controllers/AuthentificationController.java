package ikasaidi.backend_lab.controllers;

import ikasaidi.backend_lab.DTO.Connexion;
import ikasaidi.backend_lab.DTO.ReponseAuthentification;
import ikasaidi.backend_lab.services.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur responsable de la gestion de l'authentification des utilisateurs.
 * Cette classe gère les endpoints permettant aux utilisateurs de :
 * <ul>
 *     <li>Se connecter à l'application.</li>
 *     <li>Créer un nouveau compte (inscription).</li>
 * </ul>
 *
 *
 * Les requêtes sont reçues sous forme d'objet {@link Connexion} et la réponse
 * est renvoyée sous forme de {@link ReponseAuthentification} contenant les informations
 * sur la réussite ou l'échec de l'opération.
 *
 * @author Aya
 * @version 1.0
 */
@RestController
@RequestMapping("auth")
@CrossOrigin
public class AuthentificationController {

    @Autowired
    private AuthentificationService authentificationService;

    /**
     * Endpoint permettant à un utilisateur de se connecter à l'application.
     *
     * @param request les informations de connexion de l'utilisateur (nom d'utilisateur, mot de passe)
     * @return un objet {@link ReponseAuthentification} contenant le statut de la connexion
     *         et un token JWT si la connexion est réussie
     *
     * <ul>
     *     <li><b>200: </b> L'authentification est réussie</li>
     *     <li><b>401: </b> Les identifiants sont invalides</li>
     * </ul>
     */
    @PostMapping("/login")
    public ResponseEntity<ReponseAuthentification> login(@RequestBody Connexion request) {
        ReponseAuthentification auth = authentificationService.connexion(request);

        if (!auth.isSuccess()) {
            return ResponseEntity.status(401).body(auth);
        }
        return ResponseEntity.ok(auth);
    }

    /**
     * Endpoint permettant à un nouvel utilisateur de s'enregistrer.
     *
     * @param request les informations d'inscription (nom d'utilisateur, mot de passe)
     * @return un objet {@link ReponseAuthentification} indiquant si l'inscription
     *         a réussi ou non
     *
     * <ul>
     *      <li><b>200: </b> L'inscription est réussie</li>
     *      <li><b>400: </b> Une erreur de validation survient (ex. utilisateur déjà existant)</li>
     * </ul>
     *
     */
    @PostMapping("/register")
    public ResponseEntity<ReponseAuthentification> register(@RequestBody Connexion request) {
        ReponseAuthentification resp = authentificationService.register(request);

        if (!resp.isSuccess()) {
            return ResponseEntity.status(400).body(resp);
        }
        return ResponseEntity.ok(resp);
    }
}
