package api.com.transmission.specializations.repositories;

import api.com.transmission.specializations.dtos.RegisterRequestDto;
import api.com.transmission.specializations.models.User;
import org.junit.jupiter.api.Test;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Create User and Find By Email - Success")
    void findByEmail1() {
        String email = "test@test.com";
        RegisterRequestDto data =new RegisterRequestDto("example", email, "123456");
        this.createUser(data);

        Optional<User> foundedUser =  this.userRepository.findByEmail(email);
        assertThat(foundedUser.isPresent()).isTrue();

    }
    @Test
    @DisplayName("Find By Email - User Error")
    void findByEmail2() {
        String email = "test@test.com";

        Optional<User> foundedUser =  this.userRepository.findByEmail(email);
        assertThat(foundedUser.isEmpty()).isTrue();

    }

    private User createUser(RegisterRequestDto data) {
        User newUser = new User(data);
        this.entityManager.persist(newUser);
        return newUser;

    }
}