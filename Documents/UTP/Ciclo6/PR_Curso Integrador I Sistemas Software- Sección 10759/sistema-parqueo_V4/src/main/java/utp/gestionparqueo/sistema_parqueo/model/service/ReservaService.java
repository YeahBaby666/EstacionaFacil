package utp.gestionparqueo.sistema_parqueo.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utp.gestionparqueo.sistema_parqueo.model.dto.EstadoParqueoDTO;
import utp.gestionparqueo.sistema_parqueo.model.entity.Espacio;
import utp.gestionparqueo.sistema_parqueo.model.entity.Reserva;
import utp.gestionparqueo.sistema_parqueo.model.entity.Tarifa;
import utp.gestionparqueo.sistema_parqueo.model.entity.Usuario;
import utp.gestionparqueo.sistema_parqueo.model.entity.Vehiculo;
import utp.gestionparqueo.sistema_parqueo.model.entity.enums.EstadoEspacio;
import utp.gestionparqueo.sistema_parqueo.model.entity.enums.EstadoReserva;
import utp.gestionparqueo.sistema_parqueo.model.entity.enums.TipoTarifa;
import utp.gestionparqueo.sistema_parqueo.model.repository.EspacioRepository;
import utp.gestionparqueo.sistema_parqueo.model.repository.ReservaRepository;
import utp.gestionparqueo.sistema_parqueo.model.repository.VehiculoRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

@Service
public class ReservaService {
    @Autowired private ReservaRepository reservaRepo;
    @Autowired private EspacioRepository espacioRepo;
    @Autowired private VehiculoRepository vehiculoRepo;
    @Autowired private TarifaService tarifaService;
    @Autowired private SimpMessagingTemplate messagingTemplate;

    public Reserva findReservaActiva(Usuario usuario) {
        List<EstadoReserva> estados = List.of(EstadoReserva.PENDIENTE, EstadoReserva.ACTIVA);
        // MODIFICADO: Devolver la ÚLTIMA reserva activa (la más reciente)
        List<Reserva> reservas = reservaRepo.findByUsuarioAndEstadoIn(usuario, estados);
        if (reservas.isEmpty()) {
            return null;
        }
        return reservas.get(reservas.size() - 1);
    }
    
    @Transactional
    public Reserva iniciarReserva(Long idEspacio, Long idVehiculo, Usuario usuario) {
        // ... (Validaciones: que el usuario no tenga otra reserva, que el espacio esté libre, etc.)
        
        Espacio espacio = espacioRepo.findById(idEspacio).orElseThrow();
        Vehiculo vehiculo = vehiculoRepo.findById(idVehiculo).orElseThrow();

        espacio.setEstado(EstadoEspacio.RESERVADO);
        espacioRepo.save(espacio);

        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setVehiculo(vehiculo);
        reserva.setEspacio(espacio);
        reserva.setEstado(EstadoReserva.PENDIENTE); // PENDIENTE = Recién reservado
        reserva.setHoraInicioReserva(LocalDateTime.now());
        
        Reserva reservaGuardada = reservaRepo.save(reserva);
        
        // Notificar a todos por WebSocket
        notificarActualizacionEspacio(espacio, vehiculo.getPlaca());
        
        return reservaGuardada;
    }

    @Transactional
    public Reserva confirmarEstacionamiento(Long idEspacio, Usuario usuario) {
        Reserva reserva = findReservaActiva(usuario);
        // ... (Validaciones: que la reserva exista y esté PENDIENTE)
        
        reserva.setEstado(EstadoReserva.ACTIVA); // ACTIVA = Estacionado
        reserva.setHoraInicio(LocalDateTime.now());
        reserva.getEspacio().setEstado(EstadoEspacio.OCUPADO);
        
        Reserva reservaGuardada = reservaRepo.save(reserva);
        
        // Notificar a todos por WebSocket
        notificarActualizacionEspacio(reserva.getEspacio(), reserva.getVehiculo().getPlaca());
        
        return reservaGuardada;
    }
    
    @Transactional
    public Reserva finalizarEstacionamiento(Long idReserva, Usuario usuario) {
        Reserva reserva = reservaRepo.findByIdReservaAndUsuario(idReserva, usuario)
            .orElseThrow(() -> new RuntimeException("Reserva activa no encontrada"));
            
        if (reserva.getEstado() != EstadoReserva.ACTIVA) {
            throw new RuntimeException("La reserva no está activa para finalizar");
        }
        
        reserva.setHoraFin(LocalDateTime.now());
        reserva.setEstado(EstadoReserva.FINALIZADA);
        
        // Calcular costo
        Map<String, BigDecimal> desglose = calcularDesgloseCosto(reserva);
        reserva.setCostoTotal(desglose.get("TOTAL"));

        Espacio espacio = reserva.getEspacio();
        espacio.setEstado(EstadoEspacio.DISPONIBLE);
        
        Reserva reservaGuardada = reservaRepo.save(reserva);
        espacioRepo.save(espacio);
        
        // Notificar a todos por WebSocket
        notificarActualizacionEspacio(espacio, null); // null porque ya no hay placa
        
        return reservaGuardada;
    }

    public Map<String, BigDecimal> calcularDesgloseCosto(Reserva reserva) {
        Tarifa tarifaHora = tarifaService.getTarifa(TipoTarifa.HORA_NORMAL);
        Tarifa tarifaReserva = tarifaService.getTarifa(TipoTarifa.COSTO_RESERVA);

        BigDecimal costoPorHora = (tarifaHora != null) ? tarifaHora.getMonto() : BigDecimal.ZERO;
        BigDecimal costoPorReserva = (tarifaReserva != null) ? tarifaReserva.getMonto() : BigDecimal.ZERO;

        long horas = 0L;
        if (reserva.getHoraInicio() != null && reserva.getHoraFin() != null) {
            // Calcular horas (redondeando hacia arriba)
            Duration duracion = Duration.between(reserva.getHoraInicio(), reserva.getHoraFin());
            horas = (long) Math.ceil(duracion.toMinutes() / 60.0);
            if (horas == 0) horas = 1; // Cobrar mínimo 1 hora
        }
        
        BigDecimal costoEstadia = costoPorHora.multiply(new BigDecimal(horas));
        BigDecimal total = costoEstadia.add(costoPorReserva);

        return Map.of(
            "Costo Base Reserva", costoPorReserva,
            "Costo Estadia", costoEstadia,
            "Horas", new BigDecimal(horas),
            "TOTAL", total
        );
    }
    
    private void notificarActualizacionEspacio(Espacio espacio, String placa) {
        EstadoParqueoDTO dto = new EstadoParqueoDTO(
            espacio.getIdEspacio(), 
            espacio.getEstado(), 
            espacio.getUbicacion(), 
            placa
        );
        // Enviar mensaje al topic /topic/parqueo
        messagingTemplate.convertAndSend("/topic/parqueo", dto);
    }
}
