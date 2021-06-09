package app.auth.service.impl;

import app.auth.client.ProfileClient;
import app.auth.exception.UserNotFoundException;
import app.auth.model.RegularUser;
import app.auth.model.dto.UserInfoDTO;
import app.auth.repository.RegularUserRepository;
import app.auth.service.RegularUserService;
import app.auth.util.PasswordUtil;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
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
        user.getUser().setPassword(PasswordUtil.hashPBKDF2(user.getUser().getPassword()));
        try {
            repository.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Username must be unique");
        }
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
