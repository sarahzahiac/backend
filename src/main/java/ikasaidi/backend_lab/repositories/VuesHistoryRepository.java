package ikasaidi.backend_lab.repositories;

import ikasaidi.backend_lab.models.VuesHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour la gestion des entités {@link VuesHistory}.
 *
 * Cette interface hérite de {@link JpaRepository}, offrant ainsi toutes les opérations
 * CRUD standards pour manipuler l’historique de visionnage des utilisateurs.
 *
 *
 *
 * Chaque entrée de {@link VuesHistory} relie une {@link ikasaidi.backend_lab.models.Person}
 * à une {@link ikasaidi.backend_lab.models.Series}, accompagnée d’informations telles que
 * la date de visionnage et le niveau de progression.
 *
 *
 * Ce repository est principalement utilisé dans :
 * <ul>
 *     <li>{@link ikasaidi.backend_lab.controllers.VuesHistoryController}</li>
 *     <li>{@link ikasaidi.backend_lab.services.TrendingService}</li>
 * </ul>
 *
 *
 *
 * Si nécessaire, de futures méthodes personnalisées peuvent être ajoutées ici,
 * par exemple pour filtrer les vues par utilisateur, par série ou par période.
 *
 *
 * @author Rachel
 * @version 1.0
 */
@Repository
public interface VuesHistoryRepository extends JpaRepository<VuesHistory, Long> {
    // Pour le moment, aucune méthode personnalisée n’est requise.
    // Les opérations de base (findAll, findById, save, deleteById, etc.) suffisent.
}
