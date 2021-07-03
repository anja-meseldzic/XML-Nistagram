package app.auth.service;

import java.util.List;

import app.auth.model.dto.AgentDTO;
import app.auth.model.dto.AgentRequestDTO;

public interface AgentService {

	String sendRegistrationRequest(AgentDTO agentRequest);

	List<AgentRequestDTO> getAllAgentRequests();

	void acceptRegistration(Long idOfRequest);

	void rejectRegistration(Long idOfRequest);

	String registerAgentByAdmin(AgentDTO agentRequest);

}
