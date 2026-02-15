package com.archivo.inventario.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entidad de dominio puro: Expediente.
 * Representa un registro del inventario archivístico.
 * NO contiene anotaciones de frameworks (JPA, Spring).
 * La llave primaria es natural: N° de Expediente (ej. "003-2022").
 *
 * NOTA: Se usa @Getter/@Setter en vez de @Data para evitar
 * problemas con equals/hashCode auto-generado por Lombok.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Expediente {

    /** Llave primaria natural - N° de Expediente (ej. "003-2022") */
    private String idExpediente;

    /** Fecha en que se registró el expediente */
    private LocalDate fechaRegistro;

    /** Tipo de documento (Oficio, Solicitud, Carta, etc.) */
    private String tipoDocumento;

    /** Número del documento */
    private String numDocumento;

    /** Nombre completo del solicitante */
    private String nombreSolicitante;

    /** DNI del solicitante (exactamente 8 dígitos) */
    private String dniSolicitante;

    /** Asunto o motivo del expediente */
    private String asunto;

    /** Persona o área a quien va dirigido */
    private String dirigidoA;

    /** Cantidad de folios (mínimo 1) */
    private Integer folios;

    /** Ubicación o referencia de archivado */
    private String archivadoCon;

    /** Observaciones adicionales */
    private String observaciones;

    /** Estado lógico: true = activo, false = eliminado (soft delete) */
    private Boolean activo;
}
