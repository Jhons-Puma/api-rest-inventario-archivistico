package com.archivo.inventario.application.service;

import com.archivo.inventario.domain.model.Expediente;

import java.util.List;

/**
 * Puerto de entrada de la aplicación (Driving Port).
 * Define los casos de uso del sistema de inventario archivístico.
 */
public interface ExpedienteService {

    /**
     * Registra un nuevo expediente en el inventario.
     * Lanza ExpedienteDuplicadoException si el N° ya existe.
     * 
     * @param expediente datos del expediente a registrar
     * @return expediente registrado
     */
    Expediente registrar(Expediente expediente);

    /**
     * Busca un expediente por su N° de Expediente.
     * Lanza ExpedienteNoEncontradoException si no existe.
     * 
     * @param idExpediente llave primaria natural
     * @return expediente encontrado
     */
    Expediente buscarPorId(String idExpediente);

    /**
     * Lista todos los expedientes registrados.
     * 
     * @return lista completa de expedientes
     */
    List<Expediente> listarTodos();

    /**
     * Actualiza los datos de un expediente existente.
     * Sigue el flujo: findById → mapear campos → save (UPDATE real).
     * Lanza ExpedienteNoEncontradoException si no existe.
     * 
     * @param idExpediente N° del expediente a actualizar
     * @param datosNuevos  datos nuevos a sobrescribir
     * @return expediente actualizado
     */
    Expediente actualizar(String idExpediente, Expediente datosNuevos);

    /**
     * Realiza un Soft Delete (activo = false).
     * Lanza ExpedienteNoEncontradoException si no existe.
     * 
     * @param idExpediente N° del expediente a desactivar
     */
    void eliminar(String idExpediente);
}
