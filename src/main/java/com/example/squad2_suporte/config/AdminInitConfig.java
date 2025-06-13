package com.example.squad2_suporte.config;
import com.example.squad2_suporte.Usuario.Usuario;
import com.example.squad2_suporte.Usuario.Usuario.Role;
import com.example.squad2_suporte.repositorios.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitConfig {

    @Bean
    public CommandLineRunner criarUsuariosIniciais(UsuarioRepository repo, PasswordEncoder encoder) {
        return args -> {

            if (repo.findByUsername("admin") == null) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("12345"));
                admin.setRole(Role.ADMIN);
                admin.setMunicipio(null);
                repo.save(admin);
                System.out.println("[DEBUG] Usuário ADMIN criado");
            }

            if (repo.findByUsername("municipal") == null) {
                Usuario municipal = new Usuario();
                municipal.setUsername("municipal");
                municipal.setPassword(encoder.encode("12345"));
                municipal.setRole(Role.MUNICIPAL);
                municipal.setMunicipio("Aracaju");
                repo.save(municipal);
                System.out.println("[DEBUG] Usuário MUNICIPAL criado");
            }

            if (repo.findByUsername("vigilancia") == null) {
                Usuario vigilancia = new Usuario();
                vigilancia.setUsername("vigilancia");
                vigilancia.setPassword(encoder.encode("12345"));
                vigilancia.setRole(Role.VIGILANCIA);
                vigilancia.setMunicipio(null);
                repo.save(vigilancia);
                System.out.println("[DEBUG] Usuário VIGILANCIA criado");
            }
        };
    }
}
