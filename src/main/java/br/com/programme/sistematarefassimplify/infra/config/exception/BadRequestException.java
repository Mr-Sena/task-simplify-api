package br.com.programme.sistematarefassimplify.infra.config.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super (message);
    }

}
