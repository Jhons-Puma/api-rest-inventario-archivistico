package com.archivo.inventario.infrastructure.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entidad JPA mapeada a la tabla "expedientes" en PostgreSQL.
 * IMPORTANTE: La llave primaria es NATURAL (N° de Expediente).
 * PROHIBIDO usar @GeneratedValue o secuencias autonuméricas.
 *
 * NOTA: Se usa @Getter/@Setter en vez de @Data para evitar
 * problemas con equals/hashCode en entidades JPA.
 */
@Entity
@Table(name = "expedientes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpedienteJpaEntity {

    /** Llave primaria natural — N° de Expediente (ej. "003-2022") */
    @Id
    @Column(name = "id_expediente", length = 20, nullable = false)
    private String idExpediente;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @Column(name = "tipo_documento", length = 100)
    private String tipoDocumento;

    @Column(name = "num_documento", length = 50)
    private String numDocumento;

    @Column(name = "nombre_solicitante", length = 200)
    private String nombreSolicitante;

    @Column(name = "dni_solicitante", length = 8)
    private String dniSolicitante;

    @Column(name = "asunto", columnDefinition = "TEXT")
    private String asunto;

    @Column(name = "dirigido_a", length = 200)
    private String dirigidoA;

    @Column(name = "folios")
    private Integer folios;

    @Column(name = "archivado_con", length = 200)
    private String archivadoCon;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "activo", nullable = false)
    private Boolean activo;
}
