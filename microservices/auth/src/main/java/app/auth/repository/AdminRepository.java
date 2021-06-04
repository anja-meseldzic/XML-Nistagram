package app.auth.repository;

import app.auth.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findAdminByUser_UsernameAndUser_Password(String username, String password);
}
