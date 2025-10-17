package ikasaidi.backend_lab.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ikasaidi.backend_lab.controllers.SeriesController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ikasaidi.backend_lab.models.Series;
import ikasaidi.backend_lab.services.SeriesService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


//Source : https://mosy.tech/blog/spring-boot-controller-testing-guide/

/**
 * Classe de test pour le contrôleur {@link SeriesController}.
 * <ul>
 *   <li><b>GET /series</b> : Récupère toutes les séries.</li>
 *   <li><b>GET /series/{id}</b> : Récupère une série par ID.</li>
 *   <li><b>POST /series</b> : Crée une nouvelle série.</li>
 *   <li><b>PUT /series/{id}</b> : Met à jour une série existante.</li>
 *   <li><b>DELETE /series/{id}</b> : Supprime une série.</li>
 *   <li><b>GET /series/search</b> : Recherche des séries par genre ou nombre d’épisodes.</li>
 * </ul>
 *
 * <b>Technologies utilisées :</b>
 * <ul>
 *   <li>JUnit 5 → Framework de test</li>
 *   <li>Mockito → Simulation des dépendances du service</li>
 *   <li>Spring MockMvc → Simulation des requêtes HTTP</li>
 *   <li>Jackson → Conversion JSON des objets</li>
 * </ul>
 *
 * <b>But du test :</b>
 * Vérifier que le contrôleur {@link SeriesController} renvoie les statuts HTTP et les données JSON attendues pour chaque requête.
 *
 * @author Ikram
 * @version 1.0
 */

@ExtendWith(MockitoExtension.class)
class SeriesControllerTest {

    //pour les requetes http
    private MockMvc mvc;

    @Mock
    private SeriesService seriesService;

    @InjectMocks
    private SeriesController seriesController;

    //convertir objets <-> JSON
    private ObjectMapper mapper;

    /**
     * Prépare le contexte de test avant chaque méthode.
     * Initialise {@link MockMvc} et {@link ObjectMapper} pour simuler
     * les requêtes HTTP et la conversion JSON.
     */
    @BeforeEach
    void setup() {
        mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(new Jdk8Module())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //"test slm le coontroller"
        mvc = MockMvcBuilders.standaloneSetup(seriesController).build();
    }

    /**
     * Teste l’endpoint <b>GET /series</b>.
     * <ul>
     *   <li>Vérifie que le contrôleur renvoie toutes les séries avec le code 200 OK.</li>
     *   <li>Compare le JSON retourné avec la liste simulée.</li>
     * </ul>
     * @throws Exception en cas d’erreur pendant l’exécution du test.
     */
    @Test
    void getAllTest() throws Exception {
        // ARRANGE : données simulées
        var s1 = new Series(1L, "Dark", "Sci-Fi", 26, 9.2);
        var s2 = new Series(2L, "Breaking Bad", "Drama", 62, 9.5);
        given(seriesService.findAll()).willReturn(List.of(s1, s2));

        // ACT : appel GET /series
        MockHttpServletResponse res = mvc.perform(get("/series")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // ASSERT : vérifier le status et le Json
        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(res.getContentAsString()).isEqualTo(mapper.writeValueAsString(List.of(s1, s2)));
    }

    /**
     * Teste l’endpoint <b>GET /series/{id}</b> lorsque la série existe.
     * <ul>
     *   <li>Vérifie que la série demandée est retournée avec le code 200 OK.</li>
     * </ul>
     * @throws Exception en cas d’erreur d’exécution.
     */
    @Test
    void getByIdFoundTest() throws Exception {
        var s = new Series(2L, "Dark", "Sci-Fi", 26, 9.2);
        given(seriesService.findById(2L)).willReturn(s);

        MockHttpServletResponse res = mvc.perform(get("/series/2")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(res.getContentAsString()).isEqualTo(mapper.writeValueAsString(s));
    }

    /**
     * Teste l’endpoint <b>GET /series/{id}</b> lorsque la série est introuvable.
     * <ul>
     *   <li>Vérifie que la réponse est vide mais renvoie tout de même un statut 200 OK.</li>
     * </ul>
     * @throws Exception en cas d’erreur d’exécution.
     */
    @Test
    void getByIdNullTest() throws Exception {
        given(seriesService.findById(40L)).willReturn(null);

        MockHttpServletResponse res = mvc.perform(get("/series/40")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //envoi null
        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(res.getContentAsString()).isEmpty();
    }

    /**
     * Teste l’endpoint <b>POST /series</b>.
     * <ul>
     *   <li>Simule la création d’une série et vérifie que le code 200 OK est renvoyé.</li>
     *   <li>Compare le JSON du résultat avec la série sauvegardée simulée.</li>
     * </ul>
     * @throws Exception en cas d’erreur d’exécution.
     */
    @Test
    void createSerieTest() throws Exception {
        var entrer = new Series(null, "Severance", "Drama", 9, 8.6);
        var saved   = new Series(10L, "Severance", "Drama", 9, 8.6);
        given(seriesService.newSerie(any(Series.class))).willReturn(saved);

        MockHttpServletResponse res = mvc.perform(post("/series")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(entrer)))
                .andReturn().getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value()); // renvoie 200
        assertThat(res.getContentAsString())
                .isEqualTo(mapper.writeValueAsString(saved));
    }

    /**
     * Teste l’endpoint <b>PUT /series/{id}</b>.
     * <ul>
     *   <li>Vérifie que la mise à jour d’une série retourne le bon JSON et un statut 200 OK.</li>
     * </ul>
     * @throws Exception en cas d’erreur d’exécution.
     */
    @Test
    void updateTest() throws Exception {
        var old   = new Series(null, "Dark S1", "Sci-Fi", 10, 9.1);
        var updated = new Series(1L, "Dark S1", "Sci-Fi", 10, 9.1);
        given(seriesService.updateSerie(eq(1L), any(Series.class))).willReturn(updated);

        MockHttpServletResponse res = mvc.perform(put("/series/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(old)))
                .andReturn().getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(res.getContentAsString())
                .isEqualTo(mapper.writeValueAsString(updated));
    }

    /**
     * Teste l’endpoint <b>DELETE /series/{id}</b>.
     * <ul>
     *   <li>Vérifie que la suppression d’une série renvoie un statut 200 OK et une réponse vide.</li>
     * </ul>
     * @throws Exception en cas d’erreur d’exécution.
     */
    @Test
    void deleteSerieTest() throws Exception {
        MockHttpServletResponse res = mvc.perform(delete("/series/1"))
                .andReturn().getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(res.getContentAsString()).isEmpty();
    }

    /**
     * Teste l’endpoint <b>GET /series/search</b>.
     * <ul>
     *   <li>Vérifie que la recherche par genre et nombre d’épisodes renvoie les séries correspondantes.</li>
     *   <li>Statut attendu : 200 OK.</li>
     * </ul>
     * @throws Exception en cas d’erreur d’exécution.
     */
    @Test
    void searchSerieTest() throws Exception {
        var s = new Series(5L, "Mr. Robot", "Drama", 45, 8.8);
        given(seriesService.searchSerie("Drama", 40)).willReturn(List.of(s));

        MockHttpServletResponse res = mvc.perform(get("/series/search")
                        .param("genre", "Drama")
                        .param("nbEpisodes", "40")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(res.getContentAsString()).isEqualTo(mapper.writeValueAsString(List.of(s)));
    }
}
