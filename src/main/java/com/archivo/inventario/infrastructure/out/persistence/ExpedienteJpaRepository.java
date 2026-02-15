package com.archivo.inventario.infrastructure.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad ExpedienteJpaEntity.
 * La llave primaria es String (N° de Expediente).
 */
@Repository
public interface ExpedienteJpaRepository extends JpaRepository<ExpedienteJpaEntity, String> {
}
