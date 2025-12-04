package ee.silves.sectorstask.repository;

import ee.silves.sectorstask.entity.User;
import ee.silves.sectorstask.model.SaveData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findAll();

    User findBySessionHash(String hashCode);
}
