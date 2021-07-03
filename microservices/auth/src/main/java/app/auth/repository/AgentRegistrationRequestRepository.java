package app.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.auth.model.AgentRegistrationRequest;

@Repository
public interface AgentRegistrationRequestRepository extends JpaRepository<AgentRegistrationRequest, Long> {

}
