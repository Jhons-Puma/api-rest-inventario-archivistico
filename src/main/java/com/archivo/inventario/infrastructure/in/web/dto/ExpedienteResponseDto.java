package com.archivo.inventario.infrastructure.in.web.dto;

import java.time.LocalDate;

/**
 * DTO de salida (Response) para devolver los datos de un expediente.
 * Usa Java Record (inmutable). Incluye todos los campos visibles al cliente.
 */
public record ExpedienteResponseDto(
        String idExpediente,
        LocalDate fechaRegistro,
        String tipoDocumento,
        String numDocumento,
        String nombreSolicitante,
        String dniSolicitante,
        String asunto,
        String dirigidoA,
        Integer folios,
        String archivadoCon,
        String observaciones,
        Boolean activo) {
}
