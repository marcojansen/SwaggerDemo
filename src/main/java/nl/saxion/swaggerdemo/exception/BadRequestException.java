package nl.saxion.swaggerdemo.exception;

/**
 * Created by Veldmuus on 10/05/16.
 */
public class BadRequestException extends ApiException{
    private int code;
    public BadRequestException (int code, String msg) {
        super(code, msg);
        this.code = code;
    }
}
