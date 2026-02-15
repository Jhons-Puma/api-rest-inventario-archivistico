package com.archivo.inventario.infrastructure.in.web;

import com.archivo.inventario.application.service.ExpedienteService;
import com.archivo.inventario.domain.model.Expediente;
import com.archivo.inventario.infrastructure.in.web.dto.ExpedienteRequestDto;
import com.archivo.inventario.infrastructure.in.web.dto.ExpedienteResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para el recurso Expediente.
 * Expone los endpoints CRUD del inventario archivístico.
 *
 * REGLAS DE SEGURIDAD:
 * - POST y PUT usan @RequestBody + @Valid obligatoriamente.
 * - DELETE realiza Soft Delete (activo = false).
 */
@RestController
@RequestMapping("/api/v1/expedientes")
@RequiredArgsConstructor
public class ExpedienteController {

    private final ExpedienteService expedienteService;

    // ==========================================
    // POST - Registrar nuevo expediente
    // ==========================================

    /**
     * Registra un nuevo expediente en el inventario.
     * 
     * @param requestDto datos validados del expediente
     * @return 201 Created con el expediente registrado
     */
    @PostMapping
    public ResponseEntity<ExpedienteResponseDto> registrar(
            @RequestBody @Valid ExpedienteRequestDto requestDto) {

        Expediente expediente = toDomain(requestDto);
        Expediente registrado = expedienteService.registrar(expediente);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponseDto(registrado));
    }

    // ==========================================
    // GET - Consultar por N° de Expediente
    // ==========================================

    /**
     * Busca un expediente por su N° de Expediente.
     * 
     * @param id llave primaria natural (ej. "003-2022")
     * @return 200 OK con el expediente encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExpedienteResponseDto> buscarPorId(@PathVariable String id) {
        Expediente encontrado = expedienteService.buscarPorId(id);
        return ResponseEntity.ok(toResponseDto(encontrado));
    }

    // ==========================================
    // GET - Listar todos los expedientes
    // ==========================================

    /**
     * Lista todos los expedientes registrados.
     * 
     * @return 200 OK con la lista de expedientes
     */
    @GetMapping
    public ResponseEntity<List<ExpedienteResponseDto>> listarTodos() {
        List<ExpedienteResponseDto> lista = expedienteService.listarTodos()
                .stream()
                .map(this::toResponseDto)
                .toList();

        return ResponseEntity.ok(lista);
    }

    // ==========================================
    // PUT - Actualizar expediente existente
    // ==========================================

    /**
     * Actualiza los datos de un expediente existente.
     * El flujo estricto en el servicio garantiza un UPDATE real.
     * 
     * @param id         N° de Expediente a actualizar
     * @param requestDto datos nuevos validados
     * @return 200 OK con el expediente actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExpedienteResponseDto> actualizar(
            @PathVariable String id,
            @RequestBody @Valid ExpedienteRequestDto requestDto) {

        Expediente datosNuevos = toDomain(requestDto);
        Expediente actualizado = expedienteService.actualizar(id, datosNuevos);

        return ResponseEntity.ok(toResponseDto(actualizado));
    }

    // ==========================================
    // DELETE - Soft Delete (activo = false)
    // ==========================================

    /**
     * Realiza un Soft Delete del expediente.
     * No elimina físicamente, solo marca activo = false.
     * 
     * @param id N° de Expediente a desactivar
     * @return 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        expedienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // MÉTODOS DE MAPEO (DTO <-> Dominio)
    // ==========================================

    /**
     * Convierte RequestDto → modelo de dominio.
     */
    private Expediente toDomain(ExpedienteRequestDto dto) {
        return Expediente.builder()
                .idExpediente(dto.idExpediente())
                .fechaRegistro(dto.fechaRegistro())
                .tipoDocumento(dto.tipoDocumento())
                .numDocumento(dto.numDocumento())
                .nombreSolicitante(dto.nombreSolicitante())
                .dniSolicitante(dto.dniSolicitante())
                .asunto(dto.asunto())
                .dirigidoA(dto.dirigidoA())
                .folios(dto.folios())
                .archivadoCon(dto.archivadoCon())
                .observaciones(dto.observaciones())
                .build();
    }

    /**
     * Convierte modelo de dominio → ResponseDto.
     */
    private ExpedienteResponseDto toResponseDto(Expediente expediente) {
        return new ExpedienteResponseDto(
                expediente.getIdExpediente(),
                expediente.getFechaRegistro(),
                expediente.getTipoDocumento(),
                expediente.getNumDocumento(),
                expediente.getNombreSolicitante(),
                expediente.getDniSolicitante(),
                expediente.getAsunto(),
                expediente.getDirigidoA(),
                expediente.getFolios(),
                expediente.getArchivadoCon(),
                expediente.getObservaciones(),
                expediente.getActivo());
    }
}
