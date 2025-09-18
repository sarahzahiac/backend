package ikasaidi.backend_lab.Service;

import ikasaidi.backend_lab.models.Series;
import ikasaidi.backend_lab.repositories.SeriesRepository;
import ikasaidi.backend_lab.services.SeriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SeriesServiceTest {

    @Mock
    private SeriesRepository seriesRepository;

    @InjectMocks
    private SeriesService seriesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Series s1 = new Series(1L, "Dark", "Sci-Fi", 10, 9.1);
        Series s2 = new Series(2L, "Breaking Bad", "Drama", 62, 9.5);

        when(seriesRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        List<Series> result = seriesService.findAll();

        assertEquals(2, result.size());
        assertEquals("Dark", result.get(0).getTitle());
    }

    @Test
    void testFindByIdFound() {
        Series s1 = new Series(1L, "Dark", "Sci-Fi", 10, 9.1);

        // Optional.of(s1) =  série trouvée
        when(seriesRepository.findById(1L)).thenReturn(Optional.of(s1));

        Series result = seriesService.findById(1L);
        assertEquals("Dark", result.getTitle());
    }

    @Test
    void testFindByIdNotFound() {

        // Optional.empty() = aucune série trouvée
        when(seriesRepository.findById(99L)).thenReturn(Optional.empty());

        Series result = seriesService.findById(99L);

        assertNull(result);
    }

    @Test
    void testNewSerie() {

        Series s1 = new Series(null, "Severance", "Drama", 9, 8.6);

        //Sauvegarde s1 dans la bd (mock)
        when(seriesRepository.save(s1)).thenReturn(new Series(10L, "Severance", "Drama", 9, 8.6));

        Series result = seriesService.newSerie(s1);

        assertEquals(10L, result.getId());
        assertEquals("Severance", result.getTitle());
    }

    @Test
    void testUpdateSerieFound() {
        Series old = new Series(1L, "Old Title", "Sci-Fi", 5, 8.0);
        Series updated = new Series(null, "New Title", "Sci-Fi", 10, 9.0);

        when(seriesRepository.findById(1L)).thenReturn(Optional.of(old));
        when(seriesRepository.save(old)).thenReturn(old);

        Series result = seriesService.updateSerie(1L, updated);

        assertEquals("New Title", result.getTitle());
        assertEquals(10, result.getNbEpisodes());
        assertEquals(9.0, result.getNote());
    }

    @Test
    void testUpdateSerie_notFound() {
        Series updated = new Series(null, "X", "Drama", 1, 5.0);

        when(seriesRepository.findById(42L)).thenReturn(Optional.empty());

        Series result = seriesService.updateSerie(42L, updated);

        assertNull(result);
    }

    @Test
    void testDeleteSerie() {
        when(seriesRepository.existsById(1L)).thenReturn(true);

        seriesService.deleteSerie(1L);

        // 3) vérifier que deleteById a bien été appelé
        verify(seriesRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteSerie_notFound() {
        when(seriesRepository.existsById(99L)).thenReturn(false);

        seriesService.deleteSerie(99L);

        verify(seriesRepository, never()).deleteById(anyLong());
        // vérifier qu’il a bien vérifié l’existence :
        verify(seriesRepository).existsById(99L);
    }

    @Test
    void testSearch_byGenreAndEpisodes() {
        Series s1 = new Series(1L, "Dark", "Sci-Fi", 20, 9.1);
        when(seriesRepository.findByGenreAndNbEpisodesGreaterThanEqual("Sci-Fi", 10))
                .thenReturn(List.of(s1));

        var result = seriesService.searchSerie("Sci-Fi", 10);

        assertEquals(1, result.size());
        assertEquals("Dark", result.get(0).getTitle());
    }

    @Test
    void testSearch_byGenreOnly() {
        Series s1 = new Series(2L, "Breaking Bad", "Drama", 62, 9.5);
        when(seriesRepository.findByGenre("Drama")).thenReturn(List.of(s1));

        var result = seriesService.searchSerie("Drama", null);

        assertEquals(1, result.size());
        assertEquals("Breaking Bad", result.get(0).getTitle());
    }

    @Test
    void testSearch_byEpisodesOnly() {
        Series s1 = new Series(3L, "Friends", "Comedy", 200, 8.9);
        when(seriesRepository.findByNbEpisodesGreaterThanEqual(100))
                .thenReturn(List.of(s1));

        var result = seriesService.searchSerie(null, 100);

        assertEquals(1, result.size());
        assertEquals("Friends", result.get(0).getTitle());
    }

    @Test
    void testSearch_noParams() {
        Series s1 = new Series(4L, "Severance", "Drama", 9, 8.6);
        when(seriesRepository.findAll()).thenReturn(List.of(s1));

        var result = seriesService.searchSerie(null, null);

        assertEquals(1, result.size());
        assertEquals("Severance", result.get(0).getTitle());
    }



}

