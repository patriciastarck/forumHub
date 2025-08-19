package io.github.patriciastarck.forumHub.domain;

//import io.github.patriciastarck.forumHub.domain.topic.Status;
//import io.github.patriciastarck.forumHub.domain.topic.TopicRepository;
//import io.github.patriciastarck.forumHub.domain.user.User;
import io.github.patriciastarck.forumHub.infra.exception.TopicAlreadyAnsweredException;
//import io.github.patriciastarck.forumHub.exception.UnauthorizedModificationException;
import io.github.patriciastarck.forumHub.infra.exception.UnauthorizedModificationException;
import io.github.patriciastarck.forumHub.topic.Status;
import io.github.patriciastarck.forumHub.topic.TopicRepository;
import io.github.patriciastarck.forumHub.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private TopicRepository topicRepository;

    public Answer createAnswer(AnswerDataReceiverDTO data) {
        var topic = topicRepository.getReferenceById(data.topic());

        var CONTEXT_AUTHOR = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (topic.getStatus().equals(Status.RESPONDIDO) || topic.getStatus().equals(Status.FECHADO)) {
            throw new TopicAlreadyAnsweredException("Esse tópico já foi respondido ou está fechado!");
        }

        return new Answer(data, CONTEXT_AUTHOR, topic);
    }

    public void setAsSolution(Answer answer) {
        answer.setSolution(true);
        var topic = topicRepository.getReferenceById(answer.getTopic().getId());
        if (answer.isSolution()) {
            topic.setStatus(Status.RESPONDIDO);
        }
    }

    public void validateAuthorAuthorizationUpdate(Answer answer) {
        var CONTEXT_AUTHOR = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!answer.getAuthor().getEmail().equals(CONTEXT_AUTHOR.getEmail())) {
            throw new UnauthorizedModificationException("Você não pode atualizar/modificar essa resposta!");
        }
    }

    public void validateAuthorAuthorizationDelete(Answer answer) {
        var CONTEXT_AUTHOR = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!answer.getAuthor().getEmail().equals(CONTEXT_AUTHOR.getEmail())) {
            if (!CONTEXT_AUTHOR.isAdmin()) {
                throw new UnauthorizedModificationException("Você não pode deletar essa resposta!");
            }
        }
    }
}
