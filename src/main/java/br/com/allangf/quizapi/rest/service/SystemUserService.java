package br.com.allangf.quizapi.rest.service;

import br.com.allangf.quizapi.damain.entity.SystemUser;
import br.com.allangf.quizapi.rest.dto.CredentialsDTO;
import br.com.allangf.quizapi.rest.dto.SystemUserDTO;
import br.com.allangf.quizapi.rest.dto.TokenDTO;

import java.util.List;
import java.util.Optional;

public interface SystemUserService {

    SystemUser createNewUser(SystemUserDTO credentialsDTO);

    List<SystemUser> allUser();

    void deleteUser();

    TokenDTO authenticate(CredentialsDTO credentialsDTO);

    Optional<SystemUser> getUserLogged();

}
