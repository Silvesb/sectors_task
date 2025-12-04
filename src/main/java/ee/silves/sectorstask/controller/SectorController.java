package ee.silves.sectorstask.controller;

import ee.silves.sectorstask.entity.Sector;
import ee.silves.sectorstask.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SectorController {

    @Autowired
    SectorRepository sectorRepository;

    @GetMapping("/sectors")
    public List<Sector> getAllSectors() {
        return sectorRepository.findAllOrdered();
    }
}
