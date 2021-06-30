package app.agent.service;

import app.agent.model.User;

public interface UserService {
    void register(User user);

    String login(String username, String password);
}
