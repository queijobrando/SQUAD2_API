package com.example.squad2_suporte.Controller;

import com.example.squad2_suporte.service.UsuarioService;
import com.example.squad2_suporte.dto.Usuario.UsuarioDto;
import com.example.squad2_suporte.Usuario.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Gerenciamento de Usuários", description = "Endpoints para criação e listagem de usuários na API LACEN")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Cria um novo usuário", description = "Cria um novo usuário no sistema. Apenas usuários com perfil Admin podem realizar essa operação. Para usuários do tipo Municipal, o campo 'municipio' é obrigatório.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição (ex.: username já existe, município ausente para Municipal)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado: apenas Admin pode criar usuários",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody UsuarioDto usuarioDto) {
        Usuario usuario = usuarioService.createUsuario(usuarioDto);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Lista todos os usuários", description = "Retorna a lista de todos os usuários cadastrados no sistema. Apenas usuários com perfil Admin podem acessar este endpoint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado: apenas Admin pode listar usuários",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }
}