package nl.saxion.swaggerdemo.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by MarcoJansen on 06-06-16.
 */

@XmlRootElement
public class ErrorResponse {
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
