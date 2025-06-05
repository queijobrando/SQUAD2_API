//script.js

const apiDocsUrl = '/v3/api-docs'; // Caminho para a especificação OpenAPI

// Função para carregar a especificação OpenAPI
async function loadOpenAPISpec() {
    try {
        const response = await fetch(apiDocsUrl);
        const spec = await response.json();
        return spec;
    } catch (error) {
        console.error('Erro ao carregar especificação OpenAPI:', error);
        return null;
    }
}

// Função atualizada para exibir schemas
async function viewSchema(schemaType) {
    const modal = document.getElementById('schemaModal');
    const schemaArea = document.getElementById('schemaArea');

    schemaArea.textContent = 'Carregando schema...';
    modal.style.display = 'block';

    const spec = await loadOpenAPISpec();
    if (spec && spec.components && spec.components.schemas[schemaType]) {
        schemaArea.textContent = JSON.stringify(spec.components.schemas[schemaType], null, 2);
    } else {
        schemaArea.textContent = JSON.stringify({
            type: "object",
            description: `Schema ${schemaType} não encontrado`
        }, null, 2);
    }
}

// Função atualizada para testar endpoints
async function executeTest() {
    const responseArea = document.getElementById('responseArea');
    responseArea.textContent = 'Executando teste...';

    const serverSelect = document.getElementById('serverSelect');
    const baseUrl = getBaseUrl(serverSelect.value);

    try {
        const response = await fetch(`${baseUrl}${currentEndpoint}`, {
            method: currentMethod,
            headers: {
                'Content-Type': 'application/json',
                // Adicione headers de autenticação se necessário
            }
        });
        const data = await response.json();
        responseArea.textContent = JSON.stringify(data, null, 2);
    } catch (error) {
        responseArea.textContent = JSON.stringify({
            error: 'Falha ao executar o teste',
            message: error.message
        }, null, 2);
    }
}
