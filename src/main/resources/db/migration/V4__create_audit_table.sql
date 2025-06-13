CREATE TABLE auditoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    entidade VARCHAR(255) NOT NULL,
    acao VARCHAR(50) NOT NULL,
    protocolo BIGINT,
    detalhes TEXT,
    data_hora DATETIME NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);
