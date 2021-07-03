package app.auth.service;

import java.util.List;

import app.auth.model.dto.AgentDTO;
import app.auth.model.dto.AgentRequestDTO;

public interface AgentService {

	String sendRegistrationRequest(AgentDTO agentRequest);

	List<AgentRequestDTO> getAllAgentRequests();

}
