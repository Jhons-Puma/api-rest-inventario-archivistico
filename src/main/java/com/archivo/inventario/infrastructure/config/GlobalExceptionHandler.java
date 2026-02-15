package com.archivo.inventario.infrastructure.config;

import com.archivo.inventario.domain.exception.ExpedienteDuplicadoException;
import com.archivo.inventario.domain.exception.ExpedienteNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manejador global de excepciones para toda la API.
 * Intercepta excepciones y devuelve respuestas JSON limpias y consistentes.
 *
 * Excepciones manejadas:
 * - MethodArgumentNotValidException → 400 Bad Request (errores de validación)
 * - ExpedienteDuplicadoException → 409 Conflict
 * - ExpedienteNoEncontradoException → 404 Not Found
 * - Exception genérica → 500 Internal Server Error
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ==========================================
    // 400 - VALIDATION ERRORS (@Valid)
    // ==========================================

    /**
     * Captura errores de validación de @Valid en @RequestBody.
     * Devuelve un listado detallado de todos los campos que fallaron.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        // Extraer cada error de campo con su mensaje
        List<Map<String, String>> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> {
                    Map<String, String> error = new HashMap<>();
                    error.put("campo", fieldError.getField());
                    error.put("mensaje", fieldError.getDefaultMessage());
                    error.put("valor_rechazado",
                            fieldError.getRejectedValue() != null
                                    ? fieldError.getRejectedValue().toString()
                                    : "null");
                    return error;
                })
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Error de validación");
        response.put("mensaje", "Los datos enviados no cumplen con las reglas de validación");
        response.put("errores", errores);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // ==========================================
    // 409 - CONFLICT (Expediente duplicado)
    // ==========================================

    /**
     * Captura intentos de registrar un expediente con un N° que ya existe.
     */
    @ExceptionHandler(ExpedienteDuplicadoException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicado(
            ExpedienteDuplicadoException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("error", "Conflicto");
        response.put("mensaje", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    // ==========================================
    // 404 - NOT FOUND (Expediente no encontrado)
    // ==========================================

    /**
     * Captura búsquedas de expedientes que no existen en el sistema.
     */
    @ExceptionHandler(ExpedienteNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleNoEncontrado(
            ExpedienteNoEncontradoException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "No encontrado");
        response.put("mensaje", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    // ==========================================
    // 500 - INTERNAL SERVER ERROR (Genérico)
    // ==========================================

    /**
     * Captura cualquier otra excepción no controlada.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Error interno del servidor");
        response.put("mensaje", "Ocurrió un error inesperado. Contacte al administrador.");

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
