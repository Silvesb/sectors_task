package ee.silves.sectorstask.repository;

import ee.silves.sectorstask.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SectorRepository extends JpaRepository<Sector,Long> {
    @Query("SELECT s FROM Sector s ORDER BY " +
            "COALESCE(s.parentId, s.id), s.id")
    List<Sector> findAllOrdered();
}
