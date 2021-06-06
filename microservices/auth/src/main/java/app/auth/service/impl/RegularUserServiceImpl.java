package app.auth.service.impl;

import app.auth.client.ProfileClient;
import app.auth.exception.UserNotFoundException;
import app.auth.model.RegularUser;
import app.auth.model.dto.UserInfoDTO;
import app.auth.repository.RegularUserRepository;
import app.auth.service.RegularUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegularUserServiceImpl implements RegularUserService {
    private final RegularUserRepository repository;

    private final ProfileClient profileClient;

    @Autowired
    public RegularUserServiceImpl(RegularUserRepository repository, ProfileClient profileClient) {
        this.repository = repository;
        this.profileClient = profileClient;
    }

    @Override
    public void register(RegularUser user) {
        repository.save(user);
        profileClient.createFromUser(user.getUser().getUsername());
    }

    @Override
    public void update(RegularUser user) {
        RegularUser regularUser = repository.findById(user.getId()).get();
        user.getUser().setPassword(regularUser.getUser().getPassword());
        repository.save(user);
    }

    @Override
    public RegularUser getRegularUser(long id) {
        Optional<RegularUser> user = repository.findById(id);
        if(!user.isPresent())
            throw new IllegalArgumentException("No user with id " + id);
        return user.get();
    }

    @Override
    public UserInfoDTO get(String username) throws UserNotFoundException {
        Optional<RegularUser> originalUser = repository.findAll().stream().filter(u -> u.getUser().getUsername().equals(username)).findFirst();
        if(!originalUser.isPresent())
            throw new UserNotFoundException();
        RegularUser user = originalUser.get();
        UserInfoDTO result = new UserInfoDTO();
        result.setBio(user.getBiography());
        result.setBirthDate(user.getBirthDate());
        result.setEmail(user.getEmail());
        result.setGender(user.getGender().toString());
        result.setWebsite(user.getWebsite());
        result.setFullName(user.getName() != null ? user.getName() : "" + " " + user.getSurname() != null ? user.getSurname() : "");
        return result;
    }
}
