--V7__insert_test_users.sql
INSERT INTO usuario (login, senha, municipio, role) VALUES
('admin', '$2a$10$XURPShQNCsLjp1ESc2laoObo9QZDhxz73hJPaEv7/cBha4pk0AgP.', NULL, 'ROLE_ADMIN'), -- Password: admin123
('municipal_sp', '$2a$10$XURPShQNCsLjp1ESc2laoObo9QZDhxz73hJPaEv7/cBha4pk0AgP.', 'São Paulo', 'ROLE_MUNICIPAL'), -- Password: sp123
('vigilancia', '$2a$10$XURPShQNCsLjp1ESc2laoObo9QZDhxz73hJPaEv7/cBha4pk0AgP.', NULL, 'ROLE_VIGILANCIA'); -- Password: vig123