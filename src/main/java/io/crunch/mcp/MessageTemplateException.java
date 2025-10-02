package io.crunch.mcp;

public class MessageTemplateException extends RuntimeException {

    public MessageTemplateException(String message) {
        super(message);
    }

    public MessageTemplateException(String message, Throwable cause) {
        super(message, cause);
    }
}
