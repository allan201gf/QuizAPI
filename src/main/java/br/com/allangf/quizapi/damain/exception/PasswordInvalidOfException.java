package br.com.allangf.quizapi.damain.exception;

public class PasswordInvalidOfException extends RuntimeException {

    public PasswordInvalidOfException(String message) {
        super(Errors.PASSWORD_IS_WRONG);
    }

}
