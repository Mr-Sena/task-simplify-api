package br.com.programme.sistematarefassimplify.config.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super (message);
    }

}
