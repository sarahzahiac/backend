package ikasaidi.backend_lab.Controller;

import ikasaidi.backend_lab.controllers.UserController;
import ikasaidi.backend_lab.models.User;
import ikasaidi.backend_lab.models.Series;
import ikasaidi.backend_lab.repositories.UserRepository;
import ikasaidi.backend_lab.repositories.SeriesRepository;

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
class UserControllerTest {

    private MockMvc mvc;

    @Mock private UserRepository userRepository;
    @Mock private SeriesRepository seriesRepository;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setup() {

        //Pour débuger , cuz sprint injecte rien vu qu'on utilise mockito et
        // c'est autowired. on force l'injection

        ReflectionTestUtils.setField(userController, "personRepository", userRepository);
        ReflectionTestUtils.setField(userController, "seriesRepository", seriesRepository);

        mvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void addSerieToHistory() throws Exception {

        //Arrange
        User user = new User(1, "Alice Smith", "Female", "alice@x.com");
        user.setHistory(new ArrayList<>());

        Series series = new Series(2L, "Dark", "Sci-Fi", 26, 9.2);

        //Mock
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(seriesRepository.findById(2L)).thenReturn(Optional.of(series));
        when(userRepository.save(user)).thenReturn(user);


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

        when(userRepository.findById(123)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                userController.addSerieToHistory(123, 2L)
        );
        assertEquals("Personne non trouvée", ex.getMessage());

    }

    @Test
    void addSerieToHistorySeriesNotFound()  {

        // Arrange, la personne OK, mais la série introuvable
        User user = new User(1, "Alice Smith", "Female", "alice@x.com");
        user.setHistory(new ArrayList<>());
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(seriesRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                userController.addSerieToHistory(1, 999L)
        );
        assertEquals("Série non trouvée", ex.getMessage());;


    }
    }
