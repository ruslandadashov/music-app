package az.musicapp.controllers;

import az.musicapp.domain.User;
import az.musicapp.payload.requests.LoginRequest;
import az.musicapp.payload.responses.JWTLoginSuccessResponse;
import az.musicapp.security.JwtTokenProvider;
import az.musicapp.services.impl.UserServiceImpl;
import az.musicapp.validators.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static az.musicapp.security.SecurityConstants.TOKEN_PREFIX;


@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final PasswordValidator passwordValidator;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        User user = userService.findUserByUsername(loginRequest.getUsername());

        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + loginRequest.getUsername() + " not found");
        }

        String jwt = TOKEN_PREFIX + jwtTokenProvider.createToken(loginRequest.getUsername(),user.getRoles());

        JWTLoginSuccessResponse jwtResponse = new JWTLoginSuccessResponse(true, jwt);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        passwordValidator.validate(user, result);

        if (result.hasErrors()) {

            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }
        User newUser = userService.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
