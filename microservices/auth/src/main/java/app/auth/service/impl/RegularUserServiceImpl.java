package app.auth.service.impl;

import app.auth.client.ProfileClient;
import app.auth.dto.AgeGroup;
import app.auth.dto.TargetGroup;
import app.auth.exception.UserNotFoundException;
import app.auth.model.Gender;
import app.auth.model.RegularUser;
import app.auth.model.dto.UserInfoDTO;
import app.auth.repository.RegularUserRepository;
import app.auth.service.RegularUserService;
import app.auth.util.PasswordUtil;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.coreapis.commands.CreateUserCommand;

import java.time.LocalDate;
import java.util.*;

@Service
public class RegularUserServiceImpl implements RegularUserService {
    private final RegularUserRepository repository;
    private transient CommandGateway commandGateway;
//    private final ProfileClient profileClient;

//    @Autowired
//    public RegularUserServiceImpl(RegularUserRepository repository, ProfileClient profileClient) {
//        this.repository = repository;
//        this.profileClient = profileClient;
//    }

    @Autowired
    public RegularUserServiceImpl(RegularUserRepository repository, CommandGateway commandGateway) {
        this.repository = repository;
        this.commandGateway = commandGateway;
    }

    @Override
    public void register(RegularUser user) {
        user.getUser().setPassword(PasswordUtil.hashPBKDF2(user.getUser().getPassword()));
        RegularUser regularUser = null;
        try {
            regularUser = repository.save(user);
            commandGateway.send(new CreateUserCommand(regularUser.getUser().getUsername(), regularUser.getId(), regularUser.getUser().getId()));
        } catch (Exception e) {
            throw new IllegalArgumentException("Username must be unique");
        }
//        profileClient.createFromUser(user.getUser().getUsername());
    }

    @Override
    public void update(RegularUser user) {
        RegularUser regularUser = repository.findById(user.getId()).get();
        user.getUser().setPassword(regularUser.getUser().getPassword());
        repository.save(user);
    }

    @Override
    public void remove(long id) {
        repository.deleteById(id);
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

    @Override
    public List<String> getByTargetGroup(TargetGroup targetGroup) {
        List<String> result = new ArrayList<>();
        for(RegularUser user : repository.findAll()) {
            if(fitsAnyAgeGroup(user, targetGroup.getAgeGroups()) && fitsAnyGender(user, targetGroup.getGenders())) {
                result.add(user.getUser().getUsername());
            }
        }
        return result;
    }

    private boolean fitsAnyGender(RegularUser user, Set<Gender> genders) {
        return genders.contains(user.getGender());
    }

    private boolean fitsAnyAgeGroup(RegularUser user, Set<AgeGroup> ageGroups) {
        boolean result = false;
        int userYears = user.getBirthDate().until(LocalDate.now()).getYears();
        for(AgeGroup ageGroup : ageGroups) {
            if(userYears >= ageGroup.getMinAge() && userYears <= ageGroup.getMaxAge()) {
                result = true;
                break;
            }
        }
        return result;
    }
}
