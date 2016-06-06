package nl.saxion.swaggerdemo.exception;

/**
 * Created by Veldmuus on 10/05/16.
 */

public class ApiException extends Exception{
    private int code;

    public ApiException (int code, String msg) {
        super(msg);
        this.code = code;
    }
}
