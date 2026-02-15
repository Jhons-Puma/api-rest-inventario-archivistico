package com.archivo.inventario.infrastructure.in.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

/**
 * DTO de entrada (Request) para registrar o actualizar un expediente.
 * Usa Java Record (inmutable) con validaciones Jakarta.
 *
 * REGLAS DE VALIDACIÓN:
 * - idExpediente: Obligatorio, no vacío.
 * - dniSolicitante: Exactamente 8 dígitos numéricos.
 * - folios: Mínimo 1.
 * - Campos de texto principales: Obligatorios.
 */
public record ExpedienteRequestDto(

        @NotBlank(message = "El N° de Expediente es obligatorio") String idExpediente,

        @NotNull(message = "La fecha de registro es obligatoria") LocalDate fechaRegistro,

        @NotBlank(message = "El tipo de documento es obligatorio") String tipoDocumento,

        @NotBlank(message = "El número de documento es obligatorio") String numDocumento,

        @NotBlank(message = "El nombre del solicitante es obligatorio") String nombreSolicitante,

        @NotBlank(message = "El DNI del solicitante es obligatorio") @Pattern(regexp = "^[0-9]{8}$", message = "El DNI debe contener exactamente 8 dígitos numéricos") String dniSolicitante,

        @NotBlank(message = "El asunto es obligatorio") String asunto,

        @NotBlank(message = "El campo 'dirigido a' es obligatorio") String dirigidoA,

        @NotNull(message = "La cantidad de folios es obligatoria") @Min(value = 1, message = "La cantidad de folios debe ser al menos 1") Integer folios,

        String archivadoCon,

        String observaciones) {
}
