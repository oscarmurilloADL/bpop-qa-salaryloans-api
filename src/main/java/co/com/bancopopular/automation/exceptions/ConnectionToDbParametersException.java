package co.com.bancopopular.automation.exceptions;

public class ConnectionToDbParametersException extends RuntimeException{
    public ConnectionToDbParametersException(){super("--> Can't connect to Parameters");}
}
