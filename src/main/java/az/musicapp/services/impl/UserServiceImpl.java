package az.musicapp.services.impl;

import az.musicapp.domain.Role;
import az.musicapp.domain.User;
import az.musicapp.exceptions.AlreadyExistsException;
import az.musicapp.exceptions.NotFoundException;
import az.musicapp.repositories.RoleRepository;
import az.musicapp.repositories.UserRepository;
import az.musicapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsername(username);


        if (user == null)
            throw new UsernameNotFoundException(String.format("User: %s does not exist", username));
        return user;
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority("ROLE_" + role.getName())
                ).collect(Collectors.toList());
    }



    public User findUserByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    public User findUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User ID: %s does not exist", id)));
    }

    public User saveUser(User user) {

        try {
            List<Role> userRoles = new ArrayList<>();

            Role roleUser = roleRepository.findByName("USER");
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRoles.add(roleUser);
            user.setRoles(userRoles);
            return userRepository.save(user);
        } catch (Exception e) {
            throw new AlreadyExistsException(String.format("Username: %s already exists", user.getUsername()));
        }
    }

    public User updateUser(User user) {

        return userRepository.save(user);
    }

}
