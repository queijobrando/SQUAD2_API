DROP INDEX IF EXISTS idx_amostra_data_hora;
DROP INDEX IF EXISTS idx_amostra_protocolo;
CREATE INDEX idx_amostra_data_hora_protocolo ON amostra(data_hora, protocolo);