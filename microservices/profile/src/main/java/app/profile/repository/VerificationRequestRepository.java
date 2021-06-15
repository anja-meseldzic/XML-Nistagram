package app.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.profile.model.VerificationRequest;

public interface VerificationRequestRepository extends JpaRepository<VerificationRequest, Long> {

}
