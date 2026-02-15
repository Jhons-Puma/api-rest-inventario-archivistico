package com.archivo.inventario.domain.exception;

/**
 * Excepción de dominio lanzada cuando no se encuentra
 * un expediente con el N° proporcionado.
 * Mapea a HTTP 404 Not Found.
 */
public class ExpedienteNoEncontradoException extends RuntimeException {

    public ExpedienteNoEncontradoException(String idExpediente) {
        super("No se encontró el expediente con N° " + idExpediente);
    }
}
