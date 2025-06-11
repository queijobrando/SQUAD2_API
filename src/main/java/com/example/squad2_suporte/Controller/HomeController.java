package com.example.squad2_suporte.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

    @GetMapping({"/", "/api", "/api/"})
    @ResponseBody
    public String home() {
        log.info("🏠 Serving welcome page at {}", java.time.LocalDateTime.now());
        return """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>API de Envio de Amostras - LACEN</title>
                    <link rel="preconnect" href="https://fonts.googleapis.com">
                    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
                    <style>
                        :root {
                            --primary-color: #003366;
                            --accent-color: #ffcc00;
                            --text-color: #333333;
                            --light-gray: #f7f7f7;
                            --medium-gray: #e0e0e0;
                            --box-shadow: 0 4px 12px rgba(0,0,0,0.1);
                            --transition: all 0.3s ease;
                        }
                       
                        * {
                            box-sizing: border-box;
                            margin: 0;
                            padding: 0;
                        }
                       
                        body {
                            font-family: 'Montserrat', sans-serif;
                            margin: 0;
                            padding: 0;
                            color: var(--text-color);
                            background-color: var(--light-gray);
                            line-height: 1.6;
                        }
                       
                        header {
                            background-color: var(--primary-color);
                            color: white;
                            padding: 25px 20px;
                            text-align: center;
                            border-bottom: 4px solid var(--accent-color);
                            box-shadow: var(--box-shadow);
                            position: relative;
                        }
                       
                        .header-content {
                            display: flex;
                            align-items: center;
                            justify-content: center;
                            max-width: 1200px;
                            margin: 0 auto;
                        }
                       
                        .logo {
                            height: 60px;
                            margin-right: 20px;
                        }
                       
                        .header-title {
                            text-align: left;
                        }
                       
                        .header-title h1 {
                            font-size: 28px;
                            font-weight: 600;
                            margin: 0;
                        }
                       
                        .header-title p {
                            font-size: 16px;
                            font-weight: 400;
                            margin-top: 5px;
                            opacity: 0.9;
                        }
                       
                        .yellow-accent {
                            color: var(--accent-color);
                            margin: 0 8px;
                        }
                       
                        .container {
                            max-width: 1200px;
                            margin: 40px auto;
                            padding: 0 20px;
                        }
                       
                        .card {
                            background-color: white;
                            border-radius: 8px;
                            padding: 30px;
                            margin-bottom: 30px;
                            box-shadow: var(--box-shadow);
                            transition: var(--transition);
                        }
                       
                        .card:hover {
                            transform: translateY(-5px);
                            box-shadow: 0 6px 16px rgba(0,0,0,0.15);
                        }
                       
                        .welcome-card {
                            border-top: 4px solid var(--accent-color);
                            text-align: center;
                        }
                       
                        .buttons {
                            display: flex;
                            flex-wrap: wrap;
                            justify-content: center;
                            gap: 15px;
                            margin-top: 30px;
                        }
                       
                        .btn {
                            display: inline-flex;
                            align-items: center;
                            justify-content: center;
                            background-color: var(--primary-color);
                            color: white;
                            padding: 12px 24px;
                            text-decoration: none;
                            border-radius: 6px;
                            font-weight: 600;
                            transition: var(--transition);
                            min-width: 200px;
                            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                        }
                       
                        .btn:hover {
                            background-color: #004488;
                            transform: translateY(-2px);
                            box-shadow: 0 4px 8px rgba(0,0,0,0.15);
                        }
                       
                        .primary-btn {
                            background-color: var(--primary-color);
                        }
                       
                        .accent-btn {
                            background-color: var(--accent-color);
                            color: var(--primary-color);
                        }
                       
                        h2, h3 {
                            color: var(--primary-color);
                            margin-bottom: 20px;
                            position: relative;
                            padding-bottom: 10px;
                        }
                       
                        h2:after, h3:after {
                            content: '';
                            position: absolute;
                            bottom: 0;
                            left: 0;
                            width: 60px;
                            height: 3px;
                            background-color: var(--accent-color);
                        }
                       
                        h2:after {
                            left: 50%;
                            transform: translateX(-50%);
                        }
                       
                        p {
                            margin-bottom: 15px;
                        }
                       
                        .card-grid {
                            display: grid;
                            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
                            gap: 30px;
                        }
                       
                        ul {
                            padding-left: 20px;
                            list-style-type: none;
                        }
                       
                        li {
                            margin-bottom: 12px;
                            position: relative;
                            padding-left: 25px;
                        }
                       
                        li:before {
                            content: '';
                            position: absolute;
                            left: 0;
                            top: 8px;
                            width: 8px;
                            height: 8px;
                            background-color: var(--accent-color);
                            border-radius: 50%;
                        }
                       
                        .key-value {
                            display: flex;
                            margin-bottom: 12px;
                        }
                       
                        .key {
                            font-weight: 600;
                            width: 120px;
                            flex-shrink: 0;
                        }
                       
                        footer {
                            background-color: var(--primary-color);
                            color: white;
                            text-align: center;
                            padding: 30px 20px;
                            margin-top: 60px;
                        }
                       
                        footer p {
                            margin: 8px 0;
                            font-size: 14px;
                        }
                       
                        @media (max-width: 768px) {
                            .header-content { flex-direction: column; }
                            .logo { margin-right: 0; margin-bottom: 15px; }
                            .header-title { text-align: center; }
                            .buttons { flex-direction: column; align-items: center; }
                            .btn { width: 100%; max-width: 300px; }
                        }
                    </style>
                </head>
                <body>
                    <header>
                        <div class="header-content">
                            <img src="https://fsph.se.gov.br/assets/img/fsph_logoLAzul.png" alt="FSPH Logo" class="logo">
                            <div class="header-title">
                                <h1>API de Envio de Amostras <span class="yellow-accent">|</span> LACEN</h1>
                                <p>Fundação de Saúde Parreiras Horta</p>
                            </div>
                        </div>
                    </header>
                   
                    <div class="container">
                        <div class="card welcome-card">
                            <h2>Bem-vindo à API de Envio de Amostras</h2>
                            <p>API para gerenciar o cadastro, envio e controle de amostras biológicas enviadas pelas prefeituras para o LACEN/SE. Desenvolvido pelo Squad 2 para a Fundação de Saúde Parreiras Horta (FSPH).</p>
                           
                            <div class="buttons">
                                <a href="/swagger-fsph" class="btn primary-btn">Documentação da API</a>
                                <a href="/swagger-ui/index.html#/" class="btn accent-btn">Swagger UI Personalizado</a>
                                <!-- Add authentication link if applicable -->
                            </div>
                        </div>
                       
                        <div class="card-grid">
                            <div class="card">
                                <h3>Recursos Principais</h3>
                                <ul>
                                    <li>Gestão de amostras biológicas (flebotomíneos, escorpiões, triatomíneos, moluscos, larvas)</li>
                                    <li>Controle de lotes</li>
                                    <li>Validações por tipo de amostra</li>
                                    <li>Emissão de laudos técnicos</li>
                                    <li>Fluxo completo de amostras</li>
                                    <li>Conformes as normas LGPD</li>
                                </ul>
                            </div>
                           
                            <div class="card">
                                <h3>Informações Técnicas</h3>
                                <div class="key-value">
                                    <div class="key">Versão:</div>
                                    <div class="value">1.0.0</div>
                                </div>
                                <div class="key-value">
                                    <div class="key">Backend:</div>
                                    <div class="value">Java 17 com Spring Boot 3.2.3</div>
                                </div>
                                <div class="key-value">
                                    <div class="key">Banco:</div>
                                    <div class="value">MySQL / H2</div>
                                </div>
                                <div class="key-value">
                                    <div class="key">Autenticação:</div>
                                    <div class="value">JWT (JSON Web Token)</div>
                                </div>
                                <div class="key-value">
                                    <div class="key">Docs:</div>
                                    <div class="value">OpenAPI 3.0</div>
                                </div>
                            </div>
                        </div>
                    </div>
                   
                    <footer>
                        <p><strong>Fundação de Saúde Parreiras Horta (FSPH)</strong></p>
                        <p>Av. Prof. José Bonifácio Fortes Neto, 400 - Bloco Adm 02 - Capucho</p>
                        <p>CEP: 49095-000 Aracaju/SE</p>
                        <p>© 2025 - Todos os direitos reservados</p>
                    </footer>
                </body>
                </html>
                """;
    }
}