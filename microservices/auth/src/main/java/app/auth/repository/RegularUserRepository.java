package app.auth.repository;

import app.auth.model.RegularUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegularUserRepository extends JpaRepository<RegularUser, Long> {
    RegularUser findRegularUserByUser_UsernameAndUser_Password(String username, String password);
}
