package ee.silves.sectorstask.model;

import ee.silves.sectorstask.entity.User;
import lombok.Data;

@Data
public class SaveData {
    private String name;
    private long sectorId;
    private String sessionHash;
    private boolean agreedTerms;
}
