package ikasaidi.backend_lab.Controller;

import ikasaidi.backend_lab.controllers.PersonController;
import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.models.Series;
import ikasaidi.backend_lab.repositories.PersonRepository;
import ikasaidi.backend_lab.repositories.SeriesRepository;
import ikasaidi.backend_lab.services.PersonService;
import ikasaidi.backend_lab.services.RecommendationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

//Pour tester la méthode addHistory ou la logique n'est pas dans le service mais dans le controller
@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    private MockMvc mvc;

    @Mock private PersonRepository personRepository;
    @Mock private SeriesRepository seriesRepository;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    void setup() {

        //Pour débuger , cuz sprint injecte rien vu qu'on utilise mockito et
        // c'est autowired. on force l'injection

        ReflectionTestUtils.setField(personController, "personRepository", personRepository);
        ReflectionTestUtils.setField(personController, "seriesRepository", seriesRepository);

        mvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    void addSerieToHistory() throws Exception {

        //Arrange
        Person person = new Person(1, "Alice Smith", "Female", "alice@x.com");
        person.setHistory(new ArrayList<>());

        Series series = new Series(2L, "Dark", "Sci-Fi", 26, 9.2);

        //Mock
        when(personRepository.findById(1)).thenReturn(Optional.of(person));
        when(seriesRepository.findById(2L)).thenReturn(Optional.of(series));
        when(personRepository.save(person)).thenReturn(person);


        MockHttpServletResponse response = mvc.perform(
                        post("/persons/{id}/history/{seriesId}", 1, 2)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        String body = response.getContentAsString();
        assertTrue(body.contains("\"id\":1"));
        assertTrue(body.contains("\"history\""));
        assertTrue(body.contains("\"id\":2"));
    }


    //On sait qu'il aura une erreur, donc on va l'expecter pour que le test pass
    @Test
    void addSerieToHistoryPersonNotFound()  {

        when(personRepository.findById(123)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                personController.addSerieToHistory(123, 2L)
        );
        assertEquals("Personne non trouvée", ex.getMessage());

    }

    @Test
    void addSerieToHistorySeriesNotFound()  {

        // Arrange, la personne OK, mais la série introuvable
        Person person = new Person(1, "Alice Smith", "Female", "alice@x.com");
        person.setHistory(new ArrayList<>());
        when(personRepository.findById(1)).thenReturn(Optional.of(person));
        when(seriesRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                personController.addSerieToHistory(1, 999L)
        );
        assertEquals("Série non trouvée", ex.getMessage());;


    }
    }
