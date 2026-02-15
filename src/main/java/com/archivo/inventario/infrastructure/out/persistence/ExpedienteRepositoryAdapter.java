package com.archivo.inventario.infrastructure.out.persistence;

import com.archivo.inventario.domain.model.Expediente;
import com.archivo.inventario.domain.port.ExpedienteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador de persistencia (Driven Adapter).
 * Implementa el puerto del dominio usando Spring Data JPA.
 * Traduce entre la entidad de dominio (Expediente) y la entidad JPA
 * (ExpedienteJpaEntity).
 */
@Component
@RequiredArgsConstructor
public class ExpedienteRepositoryAdapter implements ExpedienteRepositoryPort {

    private final ExpedienteJpaRepository jpaRepository;

    @Override
    public Expediente save(Expediente expediente) {
        ExpedienteJpaEntity entity = toJpaEntity(expediente);
        ExpedienteJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Expediente> findById(String idExpediente) {
        return jpaRepository.findById(idExpediente)
                .map(this::toDomain);
    }

    @Override
    public List<Expediente> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public boolean existsById(String idExpediente) {
        return jpaRepository.existsById(idExpediente);
    }

    // ==========================================
    // MÉTODOS DE MAPEO (Dominio <-> JPA Entity)
    // ==========================================

    /**
     * Convierte entidad JPA → modelo de dominio.
     */
    private Expediente toDomain(ExpedienteJpaEntity entity) {
        return Expediente.builder()
                .idExpediente(entity.getIdExpediente())
                .fechaRegistro(entity.getFechaRegistro())
                .tipoDocumento(entity.getTipoDocumento())
                .numDocumento(entity.getNumDocumento())
                .nombreSolicitante(entity.getNombreSolicitante())
                .dniSolicitante(entity.getDniSolicitante())
                .asunto(entity.getAsunto())
                .dirigidoA(entity.getDirigidoA())
                .folios(entity.getFolios())
                .archivadoCon(entity.getArchivadoCon())
                .observaciones(entity.getObservaciones())
                .activo(entity.getActivo())
                .build();
    }

    /**
     * Convierte modelo de dominio → entidad JPA.
     */
    private ExpedienteJpaEntity toJpaEntity(Expediente domain) {
        return ExpedienteJpaEntity.builder()
                .idExpediente(domain.getIdExpediente())
                .fechaRegistro(domain.getFechaRegistro())
                .tipoDocumento(domain.getTipoDocumento())
                .numDocumento(domain.getNumDocumento())
                .nombreSolicitante(domain.getNombreSolicitante())
                .dniSolicitante(domain.getDniSolicitante())
                .asunto(domain.getAsunto())
                .dirigidoA(domain.getDirigidoA())
                .folios(domain.getFolios())
                .archivadoCon(domain.getArchivadoCon())
                .observaciones(domain.getObservaciones())
                .activo(domain.getActivo())
                .build();
    }
}
