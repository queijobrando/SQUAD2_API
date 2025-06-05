
-- Renomear a tabela amostra para envio_amostras
ALTER TABLE amostra RENAME TO envio_amostras;

-- Atualizar chaves estrangeiras nas tabelas filhas
ALTER TABLE escorpioes DROP FOREIGN KEY escorpioes_amostra_id_fk;
ALTER TABLE escorpioes ADD CONSTRAINT escorpioes_envio_amostras_id_fk FOREIGN KEY (id) REFERENCES envio_amostras(id);

ALTER TABLE flebotomineos DROP FOREIGN KEY flebotomineos_amostra_id_fk;
ALTER TABLE flebotomineos ADD CONSTRAINT flebotomineos_envio_amostras_id_fk FOREIGN KEY (id) REFERENCES envio_amostras(id);

ALTER TABLE triatomineos DROP FOREIGN KEY triatomineos_amostra_id_fk;
ALTER TABLE triatomineos ADD CONSTRAINT triatomineos_envio_amostras_id_fk FOREIGN KEY (id) REFERENCES envio_amostras(id);

ALTER TABLE moluscos DROP FOREIGN KEY moluscos_amostra_id_fk;
ALTER TABLE moluscos ADD CONSTRAINT moluscos_envio_amostras_id_fk FOREIGN KEY (id) REFERENCES envio_amostras(id);

ALTER TABLE larvas DROP FOREIGN KEY larvas_amostra_id_fk;
ALTER TABLE larvas ADD CONSTRAINT larvas_envio_amostras_id_fk FOREIGN KEY (id) REFERENCES envio_amostras(id);

-- Atualizar índices
DROP INDEX idx_amostra_data_hora_protocolo ON envio_amostras;
CREATE INDEX idx_envio_amostras_data_hora_protocolo ON envio_amostras(data_hora, protocolo);
