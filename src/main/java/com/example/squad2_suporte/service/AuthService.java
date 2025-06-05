package com.example.squad2_suporte.service;

import com.example.squad2_suporte.Usuario.Usuario;
import com.example.squad2_suporte.config.JwtUtil;
import com.example.squad2_suporte.repositorios.UsuarioRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String login(String username, String password) {
        System.out.println("[DEBUG] Tentando login com usuário: " + username);

        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario == null) {
            System.out.println("[DEBUG] Usuário não encontrado no banco.");
            return null;
        }

        System.out.println("[DEBUG] Usuário encontrado. Hash armazenado: " + usuario.getPassword());
        System.out.println("[DEBUG] Comparando senha informada com hash...");

        boolean senhaCorreta = passwordEncoder.matches(password, usuario.getPassword());
        System.out.println("[DEBUG] Resultado da comparação: " + senhaCorreta);

        if (senhaCorreta) {
            String token = jwtUtil.generateToken(
                    usuario.getUsername(),
                    usuario.getRole().name(),
                    usuario.getMunicipio()
            );
            System.out.println("[DEBUG] Token gerado com sucesso.");
            return token;
        }

        System.out.println("[DEBUG] Senha incorreta.");
        return null;
    }

    public String refresh(String token) {
        if (!jwtUtil.isValid(token)) {
            System.out.println("[DEBUG] Token inválido, não pode ser renovado.");
            return null;
        }

        Claims claims = jwtUtil.getClaims(token);
        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = claims.getExpiration().getTime();
        long timeLeftMillis = expirationTimeMillis - currentTimeMillis;

        if (timeLeftMillis > 0 && timeLeftMillis <= (5 * 60 * 1000)) {
            String username = claims.getSubject();
            String role = claims.get("role", String.class);
            String municipio = claims.get("municipio", String.class);
            System.out.println("[DEBUG] Token será renovado por mais 15 minutos.");
            return jwtUtil.generateToken(username, role, municipio, 15 * 60 * 1000);
        } else {
            System.out.println("[DEBUG] Token ainda não está próximo de expirar. Não será renovado.");
            return null;
        }
    }
}
