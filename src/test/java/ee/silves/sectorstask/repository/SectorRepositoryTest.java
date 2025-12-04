package ee.silves.sectorstask.repository;

import ee.silves.sectorstask.entity.Sector;
import ee.silves.sectorstask.repository.SectorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class SectorRepositoryTest {

    @Mock
    private SectorRepository sectorRepository;

    @InjectMocks
    private FakeSectorRepository harness;

    static class FakeSectorRepository {
        private final SectorRepository repo;

        FakeSectorRepository(SectorRepository repo) {
            this.repo = repo;
        }

        public List<Sector> load() {
            return repo.findAllOrdered();
        }
    }

    @BeforeEach
    void setUp() {
        List<Sector> sectorList = Arrays.asList(
                new Sector(1L, "manuf", null),
                new Sector(13L, "coma", 1L)
        );

        when(sectorRepository.findAllOrdered()).thenReturn(sectorList);
    }

    @Test
    void loadsOrderedSectors() {
        List<Sector> result = harness.load();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());

        assertEquals(13L, result.get(1).getId());
        assertEquals(1, result.get(1).getParentId());
    }
}