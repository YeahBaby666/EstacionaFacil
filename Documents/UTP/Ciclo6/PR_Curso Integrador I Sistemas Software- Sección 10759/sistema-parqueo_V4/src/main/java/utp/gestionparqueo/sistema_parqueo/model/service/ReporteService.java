package utp.gestionparqueo.sistema_parqueo.model.service;

import com.google.common.base.Strings; // <-- RÚBRICA: Guava

import jakarta.servlet.http.HttpServletResponse;
import utp.gestionparqueo.sistema_parqueo.model.entity.Reserva;
import utp.gestionparqueo.sistema_parqueo.model.entity.enums.EstadoReserva;
import utp.gestionparqueo.sistema_parqueo.model.repository.ReservaRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.Row; // <-- RÚBRICA: Apache POI
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReporteService {
    @Autowired private ReservaRepository reservaRepo;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void generarReporteReservas(HttpServletResponse response) throws IOException {
        List<Reserva> reservas = reservaRepo.findAll(); // Trae todas
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Reservas");
        
        // Encabezados
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID Reserva", "Usuario", "Vehículo", "Espacio", "Estado", "Inicio", "Fin", "Costo Total"};
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        // Datos
        int rowNum = 1;
        for (Reserva r : reservas) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(r.getIdReserva());
            // Usamos Guava para manejar nulos de forma segura
            row.createCell(1).setCellValue(Strings.nullToEmpty(r.getUsuario().getNombre()));
            row.createCell(2).setCellValue(Strings.nullToEmpty(r.getVehiculo().getPlaca()));
            row.createCell(3).setCellValue(Strings.nullToEmpty(r.getEspacio().getUbicacion()));
            row.createCell(4).setCellValue(r.getEstado().name());
            row.createCell(5).setCellValue(r.getHoraInicio() != null ? r.getHoraInicio().format(formatter) : "N/A");
            row.createCell(6).setCellValue(r.getHoraFin() != null ? r.getHoraFin().format(formatter) : "N/A");
            row.createCell(7).setCellValue(r.getCostoTotal() != null ? r.getCostoTotal().doubleValue() : 0.0);
        }
        
        workbook.write(response.getOutputStream());
        workbook.close();
    }
    
    public void generarReportePagosPorFecha(HttpServletResponse response, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws IOException {
        List<EstadoReserva> estadosPagados = List.of(EstadoReserva.FINALIZADA);
        List<Reserva> pagos = reservaRepo.findByEstadoInAndHoraFinBetween(estadosPagados, fechaInicio, fechaFin);
        
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Pagos");
        
        // Encabezados
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID Pago (Reserva)", "Fecha de Pago", "Cliente", "Vehículo", "Monto Pagado"};
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }
        
        // Datos
        int rowNum = 1;
        BigDecimal totalRecaudado = BigDecimal.ZERO;
        for (Reserva r : pagos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(r.getIdReserva());
            row.createCell(1).setCellValue(r.getHoraFin() != null ? r.getHoraFin().format(formatter) : "N/A");
            row.createCell(2).setCellValue(Strings.nullToEmpty(r.getUsuario().getNombre()));
            row.createCell(3).setCellValue(Strings.nullToEmpty(r.getVehiculo().getPlaca()));
            row.createCell(4).setCellValue(r.getCostoTotal() != null ? r.getCostoTotal().doubleValue() : 0.0);
            
            if (r.getCostoTotal() != null) {
                totalRecaudado = totalRecaudado.add(r.getCostoTotal());
            }
        }
        
        // Fila de Total
        Row totalRow = sheet.createRow(rowNum);
        totalRow.createCell(3).setCellValue("TOTAL RECAUDADO:");
        totalRow.createCell(4).setCellValue(totalRecaudado.doubleValue());
        
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}