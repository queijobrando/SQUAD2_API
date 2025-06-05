package com.example.squad2_suporte.dto.Usuario;

import com.example.squad2_suporte.Usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioDto {
    @NotBlank(message = "Username é obrigatório")
    @Schema(description = "Nome de usuário único", example = "novoUsuario")
    private String username;

    @NotBlank(message = "Senha é obrigatória")
    @Schema(description = "Senha do usuário", example = "12345")
    private String password;

    @Schema(description = "Perfil do usuário", example = "MUNICIPAL")
    private Usuario.Role role;

    @Schema(description = "Município associado (obrigatório para Municipal)", example = "São Paulo")
    private String municipio;

    // Getters e Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Usuario.Role getRole() { return role; }
    public void setRole(Usuario.Role role) { this.role = role; }
    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }
}