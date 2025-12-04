package ee.silves.sectorstask.service;

import ee.silves.sectorstask.entity.Sector;
import ee.silves.sectorstask.entity.User;
import ee.silves.sectorstask.model.SaveData;
import ee.silves.sectorstask.repository.SectorRepository;
import ee.silves.sectorstask.repository.UserRepository;
import ee.silves.sectorstask.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SectorRepository sectorRepository;

    @InjectMocks
    private UserService userService;

    private Sector sector1;
    private Sector sector2;
    private Sector sector3;
    private User user;

    @BeforeEach
    void setUp() {
        sector1 = mockSector(1L, "Items", null);
        sector2 = mockSector(6L, "Non-Items", null);
        sector3 = mockSector(7L, "non", null);

        when(sectorRepository.existsById(1L)).thenReturn(true);
        when(sectorRepository.existsById(6L)).thenReturn(true);
        when(sectorRepository.existsById(7L)).thenReturn(true);

        user = new User();
        user.setId(1L);
        user.setName("Smith");
        user.setSessionHash("hash444");
        user.setChosenSector(sector2);

        when(userRepository.findBySessionHash("hash444")).thenReturn(user);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void saveUser_createNewUser() {
        SaveData data = new SaveData();
        data.setName("John");
        data.setSectorId(6L);
        data.setSessionHash("hash444");

        User user = userService.saveUserWithInput(data, true);

        assertNotNull(user.getSessionHash());
        assertEquals("John", user.getName());
        assertEquals(6L, user.getChosenSector().getId());
    }

    @Test
    void saveUser_updatesExistingUser() {
        SaveData data = new SaveData();
        data.setName("John");
        data.setSectorId(6L);
        data.setSessionHash("hash444");

        User user = userService.saveUserWithInput(data, false);

        assertEquals("John", user.getName());
        assertEquals(6L, user.getChosenSector().getId());
        assertEquals("hash444", user.getSessionHash());

        SaveData newData = new SaveData();
        newData.setName("Smith");
        newData.setSectorId(7L);
        newData.setSessionHash("hash444");

        user = userService.saveUserWithInput(newData, false);

        assertEquals("Smith", user.getName());
        assertEquals(7L, user.getChosenSector().getId());
        assertEquals("hash444", user.getSessionHash());
    }

    @Test
    void saveUser_updatesOnlyUserWithMatchingHash() {
        User user1 = new User();
        user1.setId(2L);
        user1.setName("Jane");
        user1.setSessionHash("hash21");
        user1.setChosenSector(sector1);

        when(userRepository.findBySessionHash("hash21")).thenReturn(user1);

        SaveData user2 = new SaveData();
        user2.setName("Smith");
        user2.setSectorId(7L);
        user2.setSessionHash("hash444");

        User updatedFirstUser = userService.saveUserWithInput(user2, false);

        assertEquals("Smith", updatedFirstUser.getName());
        assertEquals(7L, updatedFirstUser.getChosenSector().getId());
        assertEquals("hash444", updatedFirstUser.getSessionHash());

        SaveData updUser2 = new SaveData();
        updUser2.setName("Doe");
        updUser2.setSectorId(6L);
        updUser2.setSessionHash("hash21");

        User updated = userService.saveUserWithInput(updUser2, false);

        assertEquals("Doe", updated.getName());
        assertEquals(6L, updated.getChosenSector().getId());
        assertEquals("hash21", updated.getSessionHash());
        assertEquals(2L, updated.getId());

        assertNotEquals(updatedFirstUser.getId(), updated.getId());
        assertNotEquals(updatedFirstUser.getName(), updated.getName());
        assertNotEquals(updatedFirstUser.getChosenSector().getId(), updated.getChosenSector().getId());
    }

    private Sector mockSector(long id, String name, Long parentId) {
        Sector sector = new Sector();
        sector.setId(id);
        sector.setName(name);
        sector.setParentId(parentId);

        when(sectorRepository.findById(id)).thenReturn(Optional.of(sector));
        return sector;
    }
}