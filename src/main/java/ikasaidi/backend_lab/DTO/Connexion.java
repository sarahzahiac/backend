package ikasaidi.backend_lab.DTO;

/**
 * Objet de transfert de données (DTO) représentant les informations
 * d’un utilisateur lors de la connexion ou de l’inscription.
 * Cet objet est utilisé pour transmettre les données entre le frontend
 * et le backend, notamment les champs nécessaires à l’authentification :
 * <ul>
 *     <li>email</li>
 *     <li>mot de passe</li>
 *     <li>nom</li>
 *     <li>genre</li>
 * </ul>
 *
 * Ce DTO est principalement consommé par le
 * {@link ikasaidi.backend_lab.controllers.AuthentificationController}
 * et le {@link ikasaidi.backend_lab.services.AuthentificationService}.
 *
 * @author Aya
 * @version 1.0
 */
public class Connexion {

    /** Adresse courriel de l’utilisateur. */
    private String email;

    /** Mot de passe de l’utilisateur. */
    private String password;

    /** Nom complet de l’utilisateur. */
    private String name;

    /** Genre de l’utilisateur (ex. "F", "M", ou autre). */
    private String gender;

    /**
     * Retourne l’adresse courriel de l’utilisateur.
     *
     * @return email de l’utilisateur
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l’adresse courriel de l’utilisateur.
     *
     * @param email nouvelle adresse courriel
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retourne le mot de passe de l’utilisateur.
     *
     * @return mot de passe
     */
    public String getPassword() {
        return password;
    }

    /**
     * Définit le mot de passe de l’utilisateur.
     *
     * @param password nouveau mot de passe
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retourne le nom complet de l’utilisateur.
     *
     * @return nom de l’utilisateur
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom complet de l’utilisateur.
     *
     * @param name nouveau nom
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne le genre de l’utilisateur.
     *
     * @return genre de l’utilisateur
     */
    public String getGender() {
        return gender;
    }

    /**
     * Définit le genre de l’utilisateur.
     *
     * @param gender nouveau genre
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
}
