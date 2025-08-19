package io.github.patriciastarck.forumHub.infra.exception;

public class UnauthorizedModificationException extends RuntimeException {
    public UnauthorizedModificationException(String message) {
        super(message);
    }
}
