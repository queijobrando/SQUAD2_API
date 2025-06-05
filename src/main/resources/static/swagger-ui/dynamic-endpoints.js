// dynamic-endpoints.js
async function renderEndpoints() {
    const endpointsList = document.getElementById('endpointsList');
    const spec = await loadOpenAPISpec();

    if (!spec || !spec.paths) {
        endpointsList.innerHTML = '<p>Erro ao carregar especificação OpenAPI</p>';
        return;
    }

    endpointsList.innerHTML = ''; // Limpar lista existente

    for (const [path, methods] of Object.entries(spec.paths)) {
        for (const [method, details] of Object.entries(methods)) {
            const card = document.createElement('div');
            card.className = 'endpoint-card fade-in';
            card.dataset.tags = details.tags ? details.tags.join(' ') : '';

            card.innerHTML = `
                <div class="endpoint-header" onclick="toggleEndpoint(this)">
                    <div class="endpoint-title">
                        <span class="method-badge method-${method.toLowerCase()}">${method.toUpperCase()}</span>
                        <div>
                            <div class="endpoint-name">${details.summary || path}</div>
                            <div class="endpoint-description">${details.description || 'Sem descrição'}</div>
                        </div>
                    </div>
                    <i class="fas fa-chevron-down expand-icon"></i>
                </div>
                <div class="endpoint-content">
                    <div class="endpoint-details">
                        <div class="endpoint-url">${method.toUpperCase()} ${path}</div>
                        <p>${details.description || 'Sem descrição detalhada'}</p>
                        <div class="action-buttons">
                            <button class="btn btn-primary" onclick="testEndpoint('${path}', '${method.toUpperCase()}')">
                                <i class="fas fa-play"></i>
                                Testar Endpoint
                            </button>
                            <button class="btn btn-secondary" onclick="viewSchema('${details.operationId || path.replace(/\//g, '-')}')">
                                <i class="fas fa-code"></i>
                                Ver Schema
                            </button>
                        </div>
                    </div>
                </div>
            `;
            endpointsList.appendChild(card);
        }
    }
}

// Chamar a função ao carregar a página
document.addEventListener('DOMContentLoaded', renderEndpoints);