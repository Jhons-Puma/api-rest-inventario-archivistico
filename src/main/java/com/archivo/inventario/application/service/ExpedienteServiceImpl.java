package com.archivo.inventario.application.service;

import com.archivo.inventario.domain.exception.ExpedienteDuplicadoException;
import com.archivo.inventario.domain.exception.ExpedienteNoEncontradoException;
import com.archivo.inventario.domain.model.Expediente;
import com.archivo.inventario.domain.port.ExpedienteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación de los casos de uso del inventario archivístico.
 * Contiene TODA la lógica de negocio.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ExpedienteServiceImpl implements ExpedienteService {

    private final ExpedienteRepositoryPort repositoryPort;

    /**
     * Registra un nuevo expediente.
     * REGLA: Si el N° de Expediente ya existe, lanza 409 Conflict.
     * Se inicializa con activo = true por defecto.
     */
    @Override
    public Expediente registrar(Expediente expediente) {

        // Verificar duplicidad de llave natural
        if (repositoryPort.existsById(expediente.getIdExpediente())) {
            throw new ExpedienteDuplicadoException(expediente.getIdExpediente());
        }

        // Todo expediente nuevo se registra como activo
        expediente.setActivo(true);

        return repositoryPort.save(expediente);
    }

    /**
     * Busca un expediente por N° de Expediente.
     * REGLA: Si no existe, lanza 404 Not Found.
     */
    @Override
    @Transactional(readOnly = true)
    public Expediente buscarPorId(String idExpediente) {
        return repositoryPort.findById(idExpediente)
                .orElseThrow(() -> new ExpedienteNoEncontradoException(idExpediente));
    }

    /**
     * Lista todos los expedientes.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Expediente> listarTodos() {
        return repositoryPort.findAll();
    }

    /**
     * Actualiza un expediente existente.
     * FLUJO ESTRICTO (previene INSERT accidental):
     * 1. Buscar el registro existente con findById.
     * 2. Sobrescribir los campos con los datos nuevos.
     * 3. Ejecutar save() sobre la entidad ya gestionada → UPDATE real.
     */
    @Override
    public Expediente actualizar(String idExpediente, Expediente datosNuevos) {

        // Paso 1: Buscar registro existente (lanza 404 si no existe)
        Expediente existente = repositoryPort.findById(idExpediente)
                .orElseThrow(() -> new ExpedienteNoEncontradoException(idExpediente));

        // Paso 2: Mapear/sobrescribir datos nuevos sobre la entidad existente
        existente.setFechaRegistro(datosNuevos.getFechaRegistro());
        existente.setTipoDocumento(datosNuevos.getTipoDocumento());
        existente.setNumDocumento(datosNuevos.getNumDocumento());
        existente.setNombreSolicitante(datosNuevos.getNombreSolicitante());
        existente.setDniSolicitante(datosNuevos.getDniSolicitante());
        existente.setAsunto(datosNuevos.getAsunto());
        existente.setDirigidoA(datosNuevos.getDirigidoA());
        existente.setFolios(datosNuevos.getFolios());
        existente.setArchivadoCon(datosNuevos.getArchivadoCon());
        existente.setObservaciones(datosNuevos.getObservaciones());
        // NOTA: No se modifica 'activo' ni 'idExpediente' en la actualización

        // Paso 3: Save sobre la entidad gestionada → genera UPDATE
        return repositoryPort.save(existente);
    }

    /**
     * Soft Delete: Marca el expediente como inactivo (activo = false).
     * NO elimina físicamente el registro de la base de datos.
     */
    @Override
    public void eliminar(String idExpediente) {

        // Buscar registro existente (lanza 404 si no existe)
        Expediente expediente = repositoryPort.findById(idExpediente)
                .orElseThrow(() -> new ExpedienteNoEncontradoException(idExpediente));

        // Soft delete: marcar como inactivo
        expediente.setActivo(false);
        repositoryPort.save(expediente);
    }
}
