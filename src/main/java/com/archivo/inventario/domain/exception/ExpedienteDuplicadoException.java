package com.archivo.inventario.domain.exception;

/**
 * Excepción de dominio lanzada cuando se intenta registrar
 * un expediente con un N° que ya existe en el sistema.
 * Mapea a HTTP 409 Conflict.
 */
public class ExpedienteDuplicadoException extends RuntimeException {

    public ExpedienteDuplicadoException(String idExpediente) {
        super("Ya existe un expediente registrado con el N° " + idExpediente);
    }
}
