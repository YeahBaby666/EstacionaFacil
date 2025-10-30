// static/js/app.js
document.addEventListener('DOMContentLoaded', () => {
    
    const grid = document.getElementById('parqueo-grid');
    const selectVehiculo = document.getElementById('selectVehiculo');
    let stompClient = null;

    function connect() {
        // Conexión directa a WebSocket nativo (sin SockJS)
        const socket = new WebSocket('ws://localhost:8080/ws');
        stompClient = Stomp.over(socket);
        
        stompClient.connect({}, function (frame) {
            console.log('Conectado: ' + frame);
            
            // Suscripción al topic de actualizaciones
            stompClient.subscribe('/topic/parqueo', function (mensaje) {
                actualizarEspacio(JSON.parse(mensaje.body));
            });
        }, function(error) {
            console.error('Error de conexión WebSocket:', error);
        });
    }

    function actualizarEspacio(data) {
        const espacioDiv = document.getElementById(`espacio-${data.idEspacio}`);
        if (!espacioDiv) return;

        // Quitar clases antiguas
        espacioDiv.classList.remove('disponible', 'reservado', 'ocupado');
        
        const placaSpan = espacioDiv.querySelector('.placa');
        
        // Añadir nueva clase y placa
        switch (data.nuevoEstado) {
            case 'DISPONIBLE':
                espacioDiv.classList.add('disponible');
                placaSpan.textContent = '';
                break;
            case 'RESERVADO':
                espacioDiv.classList.add('reservado');
                placaSpan.textContent = data.placaVehiculo;
                break;
            case 'OCUPADO':
                espacioDiv.classList.add('ocupado');
                placaSpan.textContent = data.placaVehiculo;
                break;
        }
    }

    // Lógica de Clics en los espacios
    grid.addEventListener('click', (e) => {
        const espacioDiv = e.target.closest('.espacio');
        if (!espacioDiv || !stompClient) return;
        
        const idEspacio = espacioDiv.getAttribute('data-id');
        
        // Caso 1: Reservar (Click en DISPONIBLE)
        if (espacioDiv.classList.contains('disponible')) {
            const idVehiculo = selectVehiculo.value;
            if (!idVehiculo) {
                alert('Por favor, seleccione un vehículo primero.');
                return;
            }
            stompClient.send("/app/reservar", {}, JSON.stringify({ idEspacio, idVehiculo }));
        }
        
        // Caso 2: Estacionar (Click en RESERVADO)
        else if (espacioDiv.classList.contains('reservado')) {
            // El idVehiculo no es necesario aquí, el servicio lo busca por la reserva activa
            stompClient.send("/app/estacionar", {}, JSON.stringify({ idEspacio }));
        }
    });

    connect();
});