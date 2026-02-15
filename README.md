# API REST - Inventario Archivístico

## Descripción
API REST robusta para el sistema de inventario interno de un Archivo Central, 
desarrollada con Java 21, Spring Boot 3.4, PostgreSQL y Clean Architecture.

## Stack Tecnológico
- **Java 21** (Records para DTOs)
- **Spring Boot 3.4.2**
- **PostgreSQL**
- **Spring Data JPA**
- **Jakarta Validation**
- **Lombok**

## Arquitectura
```
com.archivo.inventario
├── domain/                  # Capa de dominio (POJO puro)
│   ├── model/               # Entidad Expediente
│   ├── exception/           # Excepciones de dominio
│   └── port/                # Puerto de repositorio
├── application/             # Capa de aplicación
│   └── service/             # Lógica de negocio
└── infrastructure/          # Capa de infraestructura
    ├── in/web/              # Controlador REST + DTOs
    ├── out/persistence/     # Adaptador JPA
    └── config/              # Manejo global de excepciones
```

## Requisitos
- Java 21+
- PostgreSQL 14+
- Maven 3.9+

## Configuración
1. Crear la base de datos:
```sql
CREATE DATABASE inventario_archivo;
```
2. Ejecutar el script `src/main/resources/schema.sql`
3. Configurar credenciales en `src/main/resources/application.properties`

## Ejecución
```bash
mvnw.cmd spring-boot:run
```

## Endpoints
| Método | URL                          | Descripción                  | Código |
|--------|------------------------------|------------------------------|--------|
| POST   | /api/v1/expedientes          | Registrar nuevo expediente   | 201    |
| GET    | /api/v1/expedientes          | Listar todos los expedientes | 200    |
| GET    | /api/v1/expedientes/{id}     | Consultar por N° Expediente  | 200    |
| PUT    | /api/v1/expedientes/{id}     | Actualizar expediente        | 200    |
| DELETE | /api/v1/expedientes/{id}     | Soft Delete (activo=false)   | 204    |
