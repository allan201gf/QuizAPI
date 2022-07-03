package br.com.allangf.quizapi.rest.service.impl;

import br.com.allangf.quizapi.damain.entity.SystemUser;
import br.com.allangf.quizapi.damain.exception.Errors;
import br.com.allangf.quizapi.damain.exception.PasswordInvalidOfException;
import br.com.allangf.quizapi.damain.exception.RuleOfException;
import br.com.allangf.quizapi.damain.repository.SystemUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private SystemUserRepository userRepository;

    public UserDetails authenticate(SystemUser user) {
        UserDetails userDetails = loadUserByUsername(user.getEmail());
        boolean passwordCorrect = encoder.matches(user.getPassword(), userDetails.getPassword());
        if (passwordCorrect) {
            return userDetails;
        }
        throw new PasswordInvalidOfException(Errors.PASSWORD_IS_WRONG);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        SystemUser user = userRepository.findByEmail(email).orElseThrow(
                () -> new RuleOfException(Errors.USER_NOT_FOUND)
        );

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();

    }
}
