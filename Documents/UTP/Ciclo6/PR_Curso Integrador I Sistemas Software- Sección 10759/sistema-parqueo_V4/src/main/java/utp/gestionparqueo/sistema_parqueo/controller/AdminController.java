package utp.gestionparqueo.sistema_parqueo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import utp.gestionparqueo.sistema_parqueo.model.entity.Tarifa;
import utp.gestionparqueo.sistema_parqueo.model.repository.TarifaRepository;
import utp.gestionparqueo.sistema_parqueo.model.service.ReporteService;
import utp.gestionparqueo.sistema_parqueo.model.service.TarifaService;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired private TarifaService tarifaService;
    @Autowired private TarifaRepository tarifaRepo; // Lo usamos para guardar
    @Autowired private ReporteService reporteService;

    @GetMapping("/panel")
    public String adminPanel(Model model) {
        model.addAttribute("tarifas", tarifaService.getAllTarifas());
        model.addAttribute("tarifaEdit", new Tarifa()); // Para el formulario
        return "admin-panel";
    }

    @PostMapping("/tarifa/guardar")
    public String guardarTarifa(@ModelAttribute("tarifaEdit") Tarifa tarifa) {
        // MODIFICADO: Lógica para diferenciar entre CREAR (ID es null) y EDITAR (ID no es null)
        Tarifa t;
        
        // SI el ID NO es nulo, ES UNA EDICIÓN. Buscamos el original.
        if (tarifa.getIdTarifa() != null) {
            t = tarifaRepo.findById(tarifa.getIdTarifa())
                          .orElseThrow(() -> new RuntimeException("Tarifa no encontrada"));
        } 
        // SI el ID ES nulo, ES NUEVO.
        else {
            t = new Tarifa();
        }
        
        // Actualizamos los datos (nuevos o existentes) y guardamos
        t.setDescripcion(tarifa.getDescripcion());
        t.setMonto(tarifa.getMonto());
        t.setTipo(tarifa.getTipo());
        tarifaRepo.save(t);
        
        return "redirect:/admin/panel";
    }
    
    @GetMapping("/reporte/reservas")
    public void descargarReporteReservas(HttpServletResponse response) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_reservas.xlsx");
        try {
            reporteService.generarReporteReservas(response);
        } catch (Exception e) {
            // Manejar error
            e.printStackTrace();
        }
    }
    
    @PostMapping("/reporte/pagos")
    public void descargarReportePagos(
            HttpServletResponse response,
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_pagos.xlsx");
        
        try {
            reporteService.generarReportePagosPorFecha(response, fechaInicio, fechaFin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
