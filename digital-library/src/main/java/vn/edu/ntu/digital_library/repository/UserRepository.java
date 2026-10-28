package vn.edu.ntu.digital_library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.ntu.digital_library.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserCode(String userCode);
    Optional<User> findByEmail(String email);
    boolean existsByUserCode(String userCode);
}