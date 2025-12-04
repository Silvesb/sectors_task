package ee.silves.sectorstask.services;

import ee.silves.sectorstask.entity.Sector;
import ee.silves.sectorstask.entity.User;
import ee.silves.sectorstask.model.SaveData;
import ee.silves.sectorstask.repository.SectorRepository;
import ee.silves.sectorstask.repository.UserRepository;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SectorRepository sectorRepository;

    public User saveUserWithInput(SaveData saveData, boolean newUser) {
        if (saveData == null) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        Sector sector;
        User user;
        if (newUser) {
            user = new User();
            user.setSessionHash(getSaltString());
        } else {
            user = userRepository.findBySessionHash(saveData.getSessionHash());
        }

        if (!sectorRepository.existsById(saveData.getSectorId())) {
            throw new RuntimeException("Sector with id " + saveData.getSectorId() + " does not exist");
        }
        sector = sectorRepository.findById(saveData.getSectorId()).orElse(null);
        user.setName(saveData.getName());
        user.setChosenSector(sector);

        userRepository.save(user);

        return user;

    }

    /**
     * thanks to https://stackoverflow.com/a/20536597
     */
    public String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();

    }

}
