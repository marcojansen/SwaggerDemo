package nl.saxion.swaggerdemo.exception;

/**
 * Created by Veldmuus on 10/05/16.
 */
public class NotFoundException extends ApiException {
    private int code;
    public NotFoundException (int code, String msg) {
        super(code, msg);
        this.code = code;
    }
}