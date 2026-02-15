package com.archivo.inventario.domain.port;

import com.archivo.inventario.domain.model.Expediente;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida del dominio (Driven Port).
 * Define el contrato de persistencia que la capa de infraestructura
 * debe implementar. El dominio NO conoce la implementación (JPA, JDBC, etc.).
 */
public interface ExpedienteRepositoryPort {

    /**
     * Persiste un expediente (nuevo o existente).
     * 
     * @param expediente entidad de dominio a guardar
     * @return expediente persistido
     */
    Expediente save(Expediente expediente);

    /**
     * Busca un expediente por su N° de Expediente.
     * 
     * @param idExpediente llave primaria natural
     * @return Optional con el expediente si existe
     */
    Optional<Expediente> findById(String idExpediente);

    /**
     * Lista todos los expedientes activos.
     * 
     * @return lista de expedientes
     */
    List<Expediente> findAll();

    /**
     * Verifica si existe un expediente con el N° dado.
     * 
     * @param idExpediente llave primaria natural
     * @return true si existe
     */
    boolean existsById(String idExpediente);
}
