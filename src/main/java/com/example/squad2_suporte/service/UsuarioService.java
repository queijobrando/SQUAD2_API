package com.example.squad2_suporte.service;

import com.example.squad2_suporte.Usuario.Usuario;
import com.example.squad2_suporte.repositorios.UsuarioRepository;
import com.example.squad2_suporte.dto.Usuario.UsuarioDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Busca um usuário pelo username.
     * @param username O nome de usuário a ser buscado.
     * @return O usuário encontrado ou null se não existir.
     */
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    /**
     * Retorna a lista de todos os usuários (acesso restrito a Admin).
     * @return Lista de usuários.
     * @throws AccessDeniedException Se o usuário atual não for Admin.
     */
    public List<Usuario> findAllUsuarios() {
        if (!isAdmin()) {
            throw new AccessDeniedException("Apenas Admin pode listar todos os usuários");
        }
        return usuarioRepository.findAll();
    }

    /**
     * Cria um novo usuário (acesso restrito a Admin).
     * Este método realiza as seguintes etapas:
     * 1. Verifica se o usuário atual tem permissão de Admin.
     * 2. Valida se o username já está em uso.
     * 3. Criptografa a senha fornecida.
     * 4. Define os dados do usuário (username, senha, role).
     * 5. Para usuários do tipo Municipal, valida e define o município.
     * 6. Salva o usuário no banco de dados.
     * @param usuarioDto Dados do usuário a ser criado (username, password, role, municipio).
     * @return O usuário criado.
     * @throws AccessDeniedException Se o usuário atual não for Admin.
     * @throws IllegalArgumentException Se o username já existir ou se o município for ausente para Municipal.
     */
    public Usuario createUsuario(UsuarioDto usuarioDto) {
        // Passo 1: Verifica se o usuário atual tem permissão de Admin
        if (!isAdmin()) {
            throw new AccessDeniedException("Apenas Admin pode criar usuários");
        }

        // Passo 2: Valida se o username já está em uso
        if (usuarioRepository.findByUsername(usuarioDto.getUsername()) != null) {
            throw new IllegalArgumentException("Username já existe");
        }

        // Passo 3: Cria uma nova entidade de usuário
        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioDto.getUsername());

        // Passo 4: Criptografa a senha e define os dados básicos
        usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
        usuario.setRole(usuarioDto.getRole());

        // Passo 5: Para usuários do tipo Municipal, valida e define o município
        if (usuario.getRole() == Usuario.Role.MUNICIPAL) {
            if (usuarioDto.getMunicipio() == null || usuarioDto.getMunicipio().isEmpty()) {
                throw new IllegalArgumentException("Município é obrigatório para Usuário Municipal");
            }
            usuario.setMunicipio(usuarioDto.getMunicipio());
        } else {
            // Para outros perfis (Admin, Vigilância), o município não é necessário
            usuario.setMunicipio(null);
        }

        // Passo 6: Salva o usuário no banco de dados e retorna
        return usuarioRepository.save(usuario);
    }

    /**
     * Verifica se o usuário atual é Admin.
     * @return true se o usuário for Admin, false caso contrário.
     */
    private boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return false;
        }
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}