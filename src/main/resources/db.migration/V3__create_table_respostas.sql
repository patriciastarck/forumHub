
CREATE TABLE respostas (
    id BIGSERIAL NOT NULL,
    mensagem VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    topico BIGINT NOT NULL,
    autor BIGINT NOT NULL,
    solucao BOOLEAN NOT NULL DEFAULT FALSE,

    PRIMARY KEY(id),
    CONSTRAINT fk_respostas_autor FOREIGN KEY(autor) REFERENCES usuarios(id),
    CONSTRAINT fk_respostas_topico FOREIGN KEY(topico) REFERENCES topicos(id)
);
