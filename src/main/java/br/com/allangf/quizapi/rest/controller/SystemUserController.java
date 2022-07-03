package br.com.allangf.quizapi.rest.controller;


import br.com.allangf.quizapi.damain.entity.SystemUser;
import br.com.allangf.quizapi.rest.config.jwt.JwtService;
import br.com.allangf.quizapi.rest.dto.CredentialsDTO;
import br.com.allangf.quizapi.rest.dto.SystemUserDTO;
import br.com.allangf.quizapi.rest.dto.TokenDTO;
import br.com.allangf.quizapi.rest.service.SystemUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class SystemUserController {

    private SystemUserService userService;
    private JwtService jwtService;

    @PostMapping("/v1")
    public SystemUser createNewUser(@Valid @RequestBody SystemUserDTO credentialsDTO) {
        return userService.createNewUser(credentialsDTO);
    }

    @GetMapping("/v1")
    public List<SystemUser> allUser() {
        return userService.allUser();
    }

    @DeleteMapping("/v1/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser() {
        userService.deleteUser();
    }

    @PostMapping("/v1/login")
    public TokenDTO authenticate(@RequestBody @Valid CredentialsDTO credentialsDTO) {
        return userService.authenticate(credentialsDTO);
    }

    @GetMapping("/v1/getloggeduser")
    public Optional<SystemUser> getUserLogged() {
        return userService.getUserLogged();
    }

}
