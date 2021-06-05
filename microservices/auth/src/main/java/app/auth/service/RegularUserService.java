package app.auth.service;

import app.auth.model.RegularUser;

public interface RegularUserService {
    void register(RegularUser user);
    void update(RegularUser user);
    RegularUser getRegularUser(long id);
}