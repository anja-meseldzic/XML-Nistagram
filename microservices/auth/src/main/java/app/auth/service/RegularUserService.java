package app.auth.service;

import app.auth.dto.TargetGroup;
import app.auth.exception.UserNotFoundException;
import app.auth.model.RegularUser;
import app.auth.model.dto.UserInfoDTO;

import java.util.List;

public interface RegularUserService {
    void register(RegularUser user);
    void update(RegularUser user);
    RegularUser getRegularUser(long id);
    UserInfoDTO get(String username) throws UserNotFoundException;
    List<String> getByTargetGroup(TargetGroup targetGroup);
}
