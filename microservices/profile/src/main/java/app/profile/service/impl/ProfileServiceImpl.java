package app.profile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.profile.repository.ProfileRepository;
import app.profile.service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService{

	private ProfileRepository profileRepository;
	
	@Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
       
    }
}
