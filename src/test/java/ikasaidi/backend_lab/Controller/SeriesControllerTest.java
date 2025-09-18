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

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(new Jdk8Module())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //"test slm le coontroller"
        mvc = MockMvcBuilders.standaloneSetup(seriesController).build();
    }

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

    @Test
    void getByIdFoundTest() throws Exception {
        var s = new Series(2L, "Dark", "Sci-Fi", 26, 9.2);
        given(seriesService.findById(2L)).willReturn(s);

        MockHttpServletResponse res = mvc.perform(get("/series/2")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(res.getContentAsString()).isEqualTo(mapper.writeValueAsString(s));
    }

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

    @Test
    void deleteSerieTest() throws Exception {
        MockHttpServletResponse res = mvc.perform(delete("/series/1"))
                .andReturn().getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(res.getContentAsString()).isEmpty();
    }

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
