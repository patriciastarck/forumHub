package io.github.patriciastarck.forumHub.infra.exception;

public class TopicAlreadyAnsweredException extends RuntimeException {
    public TopicAlreadyAnsweredException(String message) {
        super(message);
    }
}
