package app.auth.service.impl;

import app.auth.client.ProfileClient;
import app.auth.model.Admin;
import app.auth.model.RegularUser;
import app.auth.repository.AdminRepository;
import app.auth.repository.RegularUserRepository;
import app.auth.util.PasswordUtil;
import app.auth.util.TokenUtils;
import app.auth.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final RegularUserRepository regularUserRepository;
    private final AdminRepository adminRepository;
    
    private final ProfileClient profileClient;

    @Autowired
    public AuthenticationServiceImpl(RegularUserRepository regularUserRepository, AdminRepository adminRepository,  ProfileClient profileClient) {
        this.regularUserRepository = regularUserRepository;
        this.adminRepository = adminRepository;
        this.profileClient = profileClient;
    }

    @Override
    public String login(String username, String password) {
        String hPassword = PasswordUtil.hashPBKDF2(password);
        RegularUser user = regularUserRepository.findRegularUserByUser_UsernameAndUser_Password(username, hPassword);
      
        if(user != null) {
        	if(profileClient.isProfileActive(username) == false) {
            	throw new IllegalArgumentException("Your profile is no longer active, due to inappropriate content sharing.");
            }
        	return TokenUtils.generateToken(user.getId(), user.getUser().getRole(), user.getUser().getUsername());
        }
        Admin admin = adminRepository.findAdminByUser_UsernameAndUser_Password(username, hPassword);
        if(admin != null)
            return TokenUtils.generateToken(admin.getId(), admin.getUser().getRole(), admin.getUser().getUsername());
        throw new IllegalArgumentException("Wrong username or password");
    }
}
