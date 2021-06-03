package app.auth.service.impl;

import app.auth.model.RegularUser;
import app.auth.repository.RegularUserRepository;
import app.auth.service.RegularUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegularUserServiceImpl implements RegularUserService {
    RegularUserRepository repository;

    @Autowired
    public RegularUserServiceImpl(RegularUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void register(RegularUser user) {
        repository.save(user);
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
}
