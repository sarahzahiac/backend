package ikasaidi.backend_lab.DTO;

/**
 * Objet de transfert de données (DTO) représentant la réponse du serveur
 * après une tentative d’authentification (connexion ou inscription).
 *
 * Cet objet contient à la fois les informations de validation (succès ou échec),
 * un message explicatif et, en cas de succès, les détails de l’utilisateur
 * et un token d’authentification JWT.
 *
 * Il est principalement retourné par le
 * {@link ikasaidi.backend_lab.controllers.AuthentificationController}
 * et généré par le {@link ikasaidi.backend_lab.services.AuthentificationService}.
 *
 * @author Aya
 * @version 1.0
 */
public class ReponseAuthentification {

    /** Indique si l’opération d’authentification a réussi ou échoué. */
    private boolean success;

    /** Message de retour (ex. : “Connexion réussie” ou “Mot de passe invalide”). */
    private String message;

    /** Identifiant unique de l’utilisateur authentifié. */
    private Integer id;

    /** Nom de l’utilisateur connecté. */
    private String name;

    /** Token JWT généré lors d’une connexion réussie. */
    private String token;

    /**
     * Constructeur complet de la réponse d’authentification.
     *
     * @param success indique si l’authentification a réussi
     * @param message message de réponse du serveur
     * @param id identifiant de l’utilisateur connecté
     * @param name nom de l’utilisateur
     * @param token token JWT associé à la session
     */
    public ReponseAuthentification(boolean success, String message, Integer id, String name, String token) {
        this.success = success;
        this.message = message;
        this.id = id;
        this.name = name;
        this.token = token;
    }

    /**
     * Retourne le statut de réussite de l’authentification.
     *
     * @return {@code true} si l’authentification a réussi, sinon {@code false}
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Définit le statut de réussite de l’authentification.
     *
     * @param success {@code true} si la connexion est réussie
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Retourne le message de réponse du serveur.
     *
     * @return message explicatif (succès ou erreur)
     */
    public String getMessage() {
        return message;
    }

    /**
     * Définit le message de réponse du serveur.
     *
     * @param message nouveau message explicatif
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Retourne l’identifiant unique de l’utilisateur.
     *
     * @return identifiant de l’utilisateur
     */
    public Integer getId() {
        return id;
    }

    /**
     * Définit l’identifiant unique de l’utilisateur.
     *
     * @param id identifiant à attribuer
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retourne le nom de l’utilisateur connecté.
     *
     * @return nom de l’utilisateur
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom de l’utilisateur connecté.
     *
     * @param name nom de l’utilisateur
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne le token JWT de l’utilisateur.
     *
     * @return token d’authentification
     */
    public String getToken() {
        return token;
    }

    /**
     * Définit le token JWT de l’utilisateur.
     *
     * @param token nouveau token à attribuer
     */
    public void setToken(String token) {
        this.token = token;
    }
}
