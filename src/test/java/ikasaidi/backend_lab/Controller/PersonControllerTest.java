package ikasaidi.backend_lab.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ikasaidi.backend_lab.controllers.PersonController;
import ikasaidi.backend_lab.controllers.SeriesController;
import ikasaidi.backend_lab.models.Person;
import ikasaidi.backend_lab.models.Series;
import ikasaidi.backend_lab.repositories.PersonRepository;
import ikasaidi.backend_lab.repositories.SeriesRepository;
import ikasaidi.backend_lab.services.PersonService;
import ikasaidi.backend_lab.services.RecommendationService;
import ikasaidi.backend_lab.services.SeriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

//Pour tester la méthode addHistory ou la logique n'est pas dans le service mais dans le controller


@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    private MockMvc mvc;

    @Mock
    private PersonService personService;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private SeriesRepository seriesRepository;
    @Mock
    private RecommendationService recommendationService;

    @InjectMocks
    private PersonController personController;

    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(new Jdk8Module())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

}
