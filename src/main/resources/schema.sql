-- =====================================================
-- SCRIPT SQL: Creación de la tabla "expedientes"
-- Base de datos: PostgreSQL
-- Sistema: Inventario Archivístico - Archivo Central
-- =====================================================

-- Crear la base de datos (ejecutar por separado si no existe)
-- CREATE DATABASE inventario_archivo;

-- Tabla única (modelo plano) para máxima velocidad de Data Entry
CREATE TABLE IF NOT EXISTS expedientes (

    -- Llave primaria NATURAL: N° de Expediente (ej. "003-2022")
    -- PROHIBIDO usar secuencias autonuméricas
    id_expediente   VARCHAR(20)     PRIMARY KEY,

    -- Fecha de registro del expediente
    fecha_registro  DATE            NOT NULL,

    -- Tipo de documento (Oficio, Solicitud, Carta, etc.)
    tipo_documento  VARCHAR(100)    NOT NULL,

    -- Número del documento
    num_documento   VARCHAR(50)     NOT NULL,

    -- Nombre completo del solicitante
    nombre_solicitante VARCHAR(200) NOT NULL,

    -- DNI del solicitante (exactamente 8 dígitos)
    dni_solicitante VARCHAR(8)      NOT NULL,

    -- Asunto o motivo del expediente
    asunto          TEXT            NOT NULL,

    -- Persona o área destinataria
    dirigido_a      VARCHAR(200)    NOT NULL,

    -- Cantidad de folios (mínimo 1)
    folios          INTEGER         NOT NULL CHECK (folios >= 1),

    -- Ubicación o referencia de archivado
    archivado_con   VARCHAR(200),

    -- Observaciones adicionales
    observaciones   TEXT,

    -- Estado lógico para Soft Delete (true = activo)
    activo          BOOLEAN         NOT NULL DEFAULT TRUE
);

-- =====================================================
-- ÍNDICES para optimizar consultas frecuentes
-- =====================================================
CREATE INDEX IF NOT EXISTS idx_expedientes_dni
    ON expedientes (dni_solicitante);

CREATE INDEX IF NOT EXISTS idx_expedientes_activo
    ON expedientes (activo);

CREATE INDEX IF NOT EXISTS idx_expedientes_fecha
    ON expedientes (fecha_registro);

-- =====================================================
-- COMENTARIOS sobre la tabla
-- =====================================================
COMMENT ON TABLE expedientes IS 'Inventario Archivístico - Modelo plano para Archivo Central';
COMMENT ON COLUMN expedientes.id_expediente IS 'Llave primaria natural: N° de Expediente (ej. 003-2022)';
COMMENT ON COLUMN expedientes.activo IS 'Soft Delete: false = eliminado lógicamente';

-- =====================================================
-- DATOS DE PRUEBA (5 registros)
-- =====================================================
INSERT INTO expedientes (id_expediente, fecha_registro, tipo_documento, num_documento, nombre_solicitante, dni_solicitante, asunto, dirigido_a, folios, archivado_con, observaciones, activo)
VALUES
    ('001-2025', '2025-01-15', 'Oficio', 'OF-001-2025', 'Juan Carlos Pérez Gómez', '74125896', 'Solicitud de acceso a documentos históricos del periodo 2010-2015', 'Dirección de Archivo Central', 5, 'Estante A-01, Caja 12', 'Expediente completo y verificado', TRUE),
    ('002-2025', '2025-02-20', 'Solicitud', 'SOL-015-2025', 'María Elena Torres Ríos', '48521369', 'Pedido de copias certificadas de resoluciones municipales', 'Secretaría General', 12, 'Estante B-03, Caja 07', NULL, TRUE),
    ('003-2025', '2025-03-10', 'Carta', 'CART-008-2025', 'Roberto Antonio Díaz Vargas', '65874123', 'Donación de documentos familiares al archivo histórico', 'Jefatura de Archivo', 28, 'Estante C-05, Caja 02', 'Incluye fotografías originales de 1950', TRUE),
    ('004-2025', '2025-04-05', 'Memorando', 'MEM-022-2025', 'Ana Lucía Mendoza Castillo', '71236548', 'Transferencia de expedientes del área de contabilidad', 'Unidad de Gestión Documental', 45, 'Estante D-02, Caja 15', 'Transferencia parcial - pendiente segunda remesa', TRUE),
    ('005-2025', '2025-05-18', 'Informe', 'INF-003-2025', 'Carlos Eduardo Salazar Huamán', '80145236', 'Informe de inventario de documentos clasificados', 'Comité de Evaluación Documental', 8, 'Estante A-04, Caja 09', 'Documento confidencial - acceso restringido', TRUE);
