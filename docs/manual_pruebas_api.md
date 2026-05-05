# 📖 Manual de Pruebas API: Cronoclase

Debido a la estructura relacional de la base de datos, no es posible crear ciertas entidades sin que existan previamente sus dependencias (por ejemplo, un *Grupo* no puede existir sin un *Profesor* y un *Curso*). 

Para poder probar la lógica de entregas y calcular correctamente si una actividad fue entregada "A tiempo" o "Tarde", debes seguir **estrictamente este orden de creación** en Postman:

---

## 🟢 FASE 1: Entidades Independientes
Estas entidades no dependen de ninguna otra. Puedes crearlas en cualquier orden.

### 1. Crear un Profesor
Crea al profesor que estará encargado del grupo.
- **Endpoint**: `POST /api/profesor`
- **Body (JSON)**:
```json
{
  "documentoID": "101010101",
  "nombre": "Profesor Oak",
  "email": "oak@pokemon.com",
  "contrasena": "password123"
}
```
*Anota el `id` que te devuelve (ej: `1`).*

### 2. Crear un Curso
Crea el curso genérico (ej. "Programación Web", "Matemáticas").
- **Endpoint**: `POST /api/curso`
- **Body (JSON)**:
```json
{
  "nombre": "Programación Avanzada",
  "descripcion": "Curso de Java y Spring Boot"
}
```
*Anota el `id` que te devuelve (ej: `1`).*

### 3. Crear un Estudiante
Crea al estudiante que enviará la entrega más adelante.
- **Endpoint**: `POST /api/estudiante`
- **Body (JSON)**:
```json
{
  "documentoID": "987654321",
  "nombre": "Ash Ketchum",
  "email": "ash@pokemon.com",
  "contrasena": "pikachu123"
}
```
*Anota el `id` que te devuelve (ej: `1`).*

---

## 🟡 FASE 2: Entidades Relacionales
Estas entidades requieren los IDs de las entidades creadas en la Fase 1.

### 4. Crear un Grupo
El grupo relaciona el Curso con el Profesor en un horario específico.
- **Endpoint**: `POST /api/grupo`
- **Body (JSON)** *(Reemplaza los IDs con los obtenidos anteriormente)*:
```json
{
  "nombre": "Grupo A - Noche",
  "dia": "LUNES",
  "jornada": "NOCHE",
  "cursoId": 1,
  "profesorId": 1
}
```
*Anota el `id` que te devuelve (ej: `1`).*

### 5. Matricular al Estudiante
Aunque a nivel de Base de Datos la "Entrega" no exige la "Matrícula", la lógica de negocio real sí. Aquí unimos al Estudiante con el Grupo.
- **Endpoint**: `POST /api/matricula`
- **Body (JSON)**:
```json
{
  "estadoMatricula": "ACTIVA",
  "estudianteId": 1,
  "grupoId": 1
}
```

---

## 🔴 FASE 3: Evaluaciones y Entregas (¡Prueba de Lógica!)
Aquí es donde probarás la lógica de fechas y estados.

### 6. Crear una Evaluación (Tarea/Examen)
El profesor asigna una tarea a su Grupo. **Presta atención a la `fechaEntrega`** (es la fecha límite).
- **Endpoint**: `POST /api/evaluacion`
- **Body (JSON)**:
```json
{
  "titulo": "Proyecto Final Spring Boot",
  "tipo": "PROYECTO",
  "porcentaje": 30.0,
  "fechaEntrega": "2026-05-10", 
  "grupoId": 1
}
```
*Anota el `id` que te devuelve (ej: `1`).*

### 7. Realizar una Entrega (¡El paso final!)
El estudiante envía su trabajo. Aquí se ejecutará automáticamente el método `calcularEstado()` en tu `EntregaService`.

- **Endpoint**: `POST /api/entrega`
- **Caso A (Entrega a tiempo)**: Pon una `fechaEntregaReal` menor o igual a la `fechaEntrega` de la Evaluación.
```json
{
  "fechaEntregaReal": "2026-05-08",
  "archivoUrl": "http://mi-drive.com/proyecto.zip",
  "comentario": "Profe, aquí está mi proyecto",
  "estudianteId": 1,
  "evaluacionId": 1
}
```
> **Resultado esperado**: El JSON de respuesta mostrará `"estado": "ENTREGADO"`.

- **Caso B (Entrega Tarde)**: Pon una fecha posterior a la fecha límite.
```json
{
  "fechaEntregaReal": "2026-05-12",
  "archivoUrl": "http://mi-drive.com/proyecto.zip",
  "comentario": "Perdón por la demora",
  "estudianteId": 1,
  "evaluacionId": 1
}
```
> **Resultado esperado**: El JSON de respuesta mostrará `"estado": "TARDE"`.

- **Caso C (Aún sin entregar)**: Deja la fecha real como `null`.
```json
{
  "fechaEntregaReal": null,
  "archivoUrl": null,
  "comentario": null,
  "estudianteId": 1,
  "evaluacionId": 1
}
```
> **Resultado esperado**: Si la fecha de hoy es menor a la fecha límite, será `"PENDIENTE"`. Si ya pasó, será `"TARDE"`.
