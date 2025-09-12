package ikasaidi.backend_lab;

import ikasaidi.backend_lab.models.Series;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeriesServiceTest {

    @Test
    void testEpisodeCount(){
        Series s = new Series();
        s.setNbEpisodes(55);
        assertEquals(55, s.getNbEpisodes());
    }
}
