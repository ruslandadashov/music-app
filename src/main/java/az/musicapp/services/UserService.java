package az.musicapp.services;

import az.musicapp.domain.User;

public interface UserService {
    User findUserByUsername(String username);

    User findUserById(Long id);

    User saveUser(User user);

    User updateUser(User user);
}
