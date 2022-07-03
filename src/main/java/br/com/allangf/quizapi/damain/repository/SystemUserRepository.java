package br.com.allangf.quizapi.damain.repository;

import br.com.allangf.quizapi.damain.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemUserRepository extends JpaRepository<SystemUser, Integer> {

    Optional<SystemUser> findByEmail(String email);

}
