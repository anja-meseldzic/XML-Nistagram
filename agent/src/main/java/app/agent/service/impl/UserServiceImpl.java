package app.agent.service.impl;

import app.agent.model.User;
import app.agent.repository.UserRepository;
import app.agent.service.UserService;
import app.agent.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(User user) {
        user.setPassword(PasswordUtil.hashPBKDF2(user.getPassword()));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Username must be unique");
        }
    }
}
