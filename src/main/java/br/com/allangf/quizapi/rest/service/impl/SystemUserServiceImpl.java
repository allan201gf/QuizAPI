package br.com.allangf.quizapi.rest.service.impl;

import br.com.allangf.quizapi.damain.entity.SystemUser;
import br.com.allangf.quizapi.damain.enums.RolesEnum;
import br.com.allangf.quizapi.damain.exception.Errors;
import br.com.allangf.quizapi.damain.exception.PasswordInvalidOfException;
import br.com.allangf.quizapi.damain.exception.RuleOfException;
import br.com.allangf.quizapi.damain.repository.SystemUserRepository;
import br.com.allangf.quizapi.rest.config.jwt.JwtService;
import br.com.allangf.quizapi.rest.dto.CredentialsDTO;
import br.com.allangf.quizapi.rest.dto.SystemUserDTO;
import br.com.allangf.quizapi.rest.dto.TokenDTO;
import br.com.allangf.quizapi.rest.service.SystemUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SystemUserServiceImpl implements SystemUserService {

    private final SystemUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;

    public SystemUser createNewUser(SystemUserDTO userDTO) {

        Optional<SystemUser> existUser = userRepository.findByEmail(userDTO.getEmail());

        if (existUser.isPresent()) {
            throw new RuleOfException(Errors.EMAIL_ALREADY_REGISTERED);
        }

        SystemUser user = new SystemUser();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setName(userDTO.getName());
        user.setRole(RolesEnum.USER.toString());

        return userRepository.save(user);
    }

    @Override
    public List<SystemUser> allUser() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser() {
        try {
            Optional<SystemUser> loggedUser = getUserLogged();

            userRepository.deleteById(loggedUser.get().getId());
        } catch (EmptyResultDataAccessException e) {
            throw new RuleOfException(Errors.USER_NOT_FOUND);
        } catch (Exception e) {
            throw new RuleOfException(Errors.UNABLE_DELETE_USER_ASSIGNED_POST);
        }
    }

    @Override
    public TokenDTO authenticate(CredentialsDTO credentialsDTO) {
        try {
            SystemUser user = SystemUser.builder()
                    .email(credentialsDTO.getEmail())
                    .password(credentialsDTO.getPassword())
                    .build();
            UserDetails authenticatedUser = userDetailsService.authenticate(user);

            String token = jwtService.generateToken(user);

            return new TokenDTO(user.getEmail(), token);
        } catch (UsernameNotFoundException | PasswordInvalidOfException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    public Optional<SystemUser> getUserLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName());
    }
}
