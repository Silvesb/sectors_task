package ee.silves.sectorstask.controller;

import ee.silves.sectorstask.entity.Sector;
import ee.silves.sectorstask.entity.User;
import ee.silves.sectorstask.model.SaveData;
import ee.silves.sectorstask.repository.SectorRepository;
import ee.silves.sectorstask.repository.UserRepository;
import ee.silves.sectorstask.services.UserService;
import ee.silves.sectorstask.util.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    InputValidator inputValidator;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public User createUser(@RequestBody SaveData saveData) {
        if (saveData == null || !inputValidator.validateName(saveData.getName())) {
            throw new RuntimeException("Submitted data is invalid");
        }
        return userService.saveUserWithInput(saveData, true);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody SaveData saveData) {
        if (saveData == null || !inputValidator.validateName(saveData.getName())) {
            throw new RuntimeException("Submitted data is invalid");
        }
        return userService.saveUserWithInput(saveData, false);
    }
}
