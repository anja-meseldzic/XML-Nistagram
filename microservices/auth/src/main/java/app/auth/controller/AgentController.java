package app.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import app.auth.model.dto.AgentDTO;
import app.auth.model.dto.AgentRequestDTO;
import app.auth.service.AgentService;
import app.auth.util.TokenUtils;


@RestController
@RequestMapping(value = "agent")
public class AgentController {
	 
	private  AgentService agentService;

	@Autowired
	public AgentController(AgentService agentService) {
	        this.agentService = agentService;
	}
	
	 @PostMapping(value = "agentRegistrationRequest")
	 public ResponseEntity<String> sendRegistrationRequest(@RequestBody AgentDTO agentRequest, @RequestHeader("Authorization") String auth) {
		 String message = agentService.sendRegistrationRequest(agentRequest);
		 return new ResponseEntity<>(message, HttpStatus.OK);
	    
	 }
	 
	 @GetMapping(value = "getAllRegistrationRequests")
		public ResponseEntity<List<AgentRequestDTO>> getAllAgentRequests(@RequestHeader("Authorization") String auth) {
		 if(!TokenUtils.verify(auth, "ADMIN"))
	            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
			List<AgentRequestDTO> agents = agentService.getAllAgentRequests();
			return new ResponseEntity<>(agents, HttpStatus.OK);
		}
	 
	@PostMapping(value = "acceptRegistration")
	public ResponseEntity<String> acceptRegistration(@RequestBody Long idOfRequest,  @RequestHeader("Authorization") String auth)
		{
			if(!TokenUtils.verify(auth, "ADMIN"))
		        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			try {
				 agentService.acceptRegistration(idOfRequest);
			} catch (IllegalArgumentException e) {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
			return new ResponseEntity<>("ok", HttpStatus.OK);
	}
		
	@PostMapping(value = "rejectRegistration")
	public ResponseEntity<String> rejectRegistration(@RequestBody Long idOfRequest,  @RequestHeader("Authorization") String auth)
	{
			if(!TokenUtils.verify(auth, "ADMIN"))
		            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			agentService.rejectRegistration(idOfRequest);
			return new ResponseEntity<>("ok", HttpStatus.OK);
	}
	
	 @PostMapping(value = "registerAgent")
	 public ResponseEntity<String> registerAgentByAdmin(@RequestBody AgentDTO agentRequest, @RequestHeader("Authorization") String auth) {
		 if(!TokenUtils.verify(auth, "ADMIN"))
	            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		 String message = agentService.registerAgentByAdmin(agentRequest);
		 return new ResponseEntity<>(message, HttpStatus.OK);
	    
	 }
}
