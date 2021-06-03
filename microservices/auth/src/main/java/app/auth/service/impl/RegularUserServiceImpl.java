package app.auth.service.impl;

import app.auth.model.RegularUser;
import app.auth.repository.RegularUserRepository;
import app.auth.service.RegularUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
