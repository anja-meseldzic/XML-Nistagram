package app.auth.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.auth.client.ProfileClient;
import app.auth.model.AgentRegistrationRequest;
import app.auth.model.RegularUser;
import app.auth.model.Role;
import app.auth.model.User;
import app.auth.model.dto.AgentDTO;
import app.auth.model.dto.AgentRequestDTO;
import app.auth.repository.AgentRegistrationRequestRepository;
import app.auth.repository.RegularUserRepository;
import app.auth.repository.UserRepository;
import app.auth.service.AgentService;
import app.auth.util.PasswordUtil;

@Service
public class AgentServiceImpl implements AgentService{

	private UserRepository userRepository;
	private AgentRegistrationRequestRepository agentRegistrationRequestRepository;
    private RegularUserRepository regularUserRepository;
    private ProfileClient profileClient;

	
	
	@Autowired
    public AgentServiceImpl(UserRepository userRepository, AgentRegistrationRequestRepository agentRegistrationRequestRepository,
    		RegularUserRepository regularUserRepository, ProfileClient profileClient) {
		 this.userRepository = userRepository;
		 this.agentRegistrationRequestRepository = agentRegistrationRequestRepository;
		 this.regularUserRepository = regularUserRepository;
		 this.profileClient = profileClient;
		 
    }

	@Override
	public String sendRegistrationRequest(AgentDTO agentRequest) {
		for(User user : userRepository.findAll()) {
			if(user.getUsername().equals(agentRequest.getUsername()))
				return "This username is already taken.";
		}
		
		AgentRegistrationRequest request = new AgentRegistrationRequest();
		request.setReviewed(false);
		request.setName(agentRequest.getName());
		request.setSurname(agentRequest.getSurname());
		request.setGender(agentRequest.getGender());
		request.setEmail(agentRequest.getEmail());
		request.setPhoneNumber(agentRequest.getPhoneNumber());
		request.setBirthDate(agentRequest.getBirthDate());
		request.setWebsite(agentRequest.getWebsite());
		request.setBiography(agentRequest.getBiography());
		request.setUsername(agentRequest.getUsername());
		request.setPassword(agentRequest.getPassword());
		
		agentRegistrationRequestRepository.save(request);
		
		return "You successfully sent your registration request.";
	}

	@Override
	public List<AgentRequestDTO> getAllAgentRequests() {
		List <AgentRegistrationRequest> agentsRequests = agentRegistrationRequestRepository.findAll().stream().filter(r -> !r.isReviewed()).collect(Collectors.toList());
		List<AgentRequestDTO> result = new ArrayList<AgentRequestDTO>();
	
		for(AgentRegistrationRequest req : agentsRequests) {
			AgentRequestDTO dto = new AgentRequestDTO();
			dto.setIdOfRequest(req.getId());
			dto.setName(req.getName());
			dto.setSurname(req.getSurname());
			dto.setEmail(req.getEmail());
			dto.setWebsite(req.getWebsite());
			
			result.add(dto);
		}
		return result;
	}

	@Override
	public void acceptRegistration(Long idOfRequest) {
		AgentRegistrationRequest agentRequest = agentRegistrationRequestRepository.findOneById(idOfRequest);
		agentRequest.setReviewed(true);
		agentRegistrationRequestRepository.save(agentRequest);
		
		RegularUser newUser = new RegularUser();
		
		newUser.setName(agentRequest.getName());
		newUser.setSurname(agentRequest.getSurname());
		newUser.setGender(agentRequest.getGender());
		newUser.setEmail(agentRequest.getEmail());
		newUser.setPhoneNumber(agentRequest.getPhoneNumber());
		newUser.setBirthDate(agentRequest.getBirthDate());
		newUser.setWebsite(agentRequest.getWebsite());
		newUser.setBiography(agentRequest.getBiography());
		newUser.setUser(new User(agentRequest.getUsername(), PasswordUtil.hashPBKDF2(agentRequest.getPassword()), Role.AGENT));
		
		try {
			regularUserRepository.save(newUser);
        } catch (Exception e) {
            throw new IllegalArgumentException("Username must be unique");
        }
        profileClient.createFromUser(newUser.getUser().getUsername());
	}

	@Override
	public void rejectRegistration(Long idOfRequest) {
		AgentRegistrationRequest agentRequest = agentRegistrationRequestRepository.findOneById(idOfRequest);
		agentRequest.setReviewed(true);
		agentRegistrationRequestRepository.save(agentRequest);
		
	}
}
