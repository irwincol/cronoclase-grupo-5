# 💡 Posibles Lógicas de Negocio a Implementar

Basado en la estructura actual (Profesores, Estudiantes, Cursos, Grupos, Evaluaciones y Entregas), la API tiene una excelente base para expandirse y convertirse en una plataforma de gestión académica completa. 

A continuación, se detallan varias lógicas de negocio avanzadas que podrían implementarse en el futuro:

---

## 1. 📊 Sistema de Calificaciones y Promedios Automáticos
- **Añadir notas:** Agregar un campo `nota` (ej. de 0.0 a 5.0) en la entidad `Entrega`.
- **Lógica de Promedios:** Crear un endpoint en `GrupoController` que calcule la nota final de un estudiante en ese grupo. La lógica multiplicaría la `nota` de cada entrega por el `porcentaje` de su respectiva `Evaluacion` y sumaría los resultados.
- **Validación del 100%:** Crear una regla estricta en `EvaluacionService` que impida crear una nueva evaluación si la suma de los porcentajes de todas las evaluaciones de ese grupo específico supera el 100%.

## 2. 🔐 Autenticación y Autorización (Spring Security + JWT)
- **Login:** Crear un endpoint `/api/auth/login` que valide el correo y contraseña (previamente encriptada en la Base de Datos con BCrypt) y devuelva un Token JWT.
- **Manejo de Roles:** Diferenciar los permisos de acceso de los usuarios:
  - Un *Estudiante* solo puede ver sus propias entregas, modificar su perfil y subir archivos a evaluaciones asignadas. No puede crear Grupos ni calificar.
  - Un *Profesor* solo puede crear evaluaciones y calificar entregas de sus **propios grupos**, bloqueando el acceso a los grupos de otros profesores.

## 3. 📅 Validaciones Anti-Choques (Horarios y Cupos)
- **Cruce de horarios:** Si se intenta matricular a un estudiante en un `Grupo` (ej. Lunes en la Noche), el `MatriculaService` debe revisar la base de datos buscando todas sus otras matrículas. Si ya tiene otro grupo los Lunes en la Noche, el sistema debe lanzar un error `ConflictException` (HTTP 409): *"El estudiante ya tiene clases en este horario"*.
- **Capacidad de Grupo:** Añadir un campo `cupoMaximo` a la entidad `Grupo` (ej. 30 alumnos). Si se intenta crear la matrícula número 31, arrojar un error: *"El grupo ha alcanzado su límite de capacidad"*.

## 4. ⏰ Tareas Programadas Automáticas (`@Scheduled`)
- **Avisos de Vencimiento:** Usar la anotación `@Scheduled` de Spring Boot para que el servidor despierte periódicamente (ej. todos los días a las 8:00 AM) y revise qué `Evaluaciones` vencen al día siguiente, enviando notificaciones o correos a los alumnos.
- **Actualización masiva de estados:** Un proceso automático que se ejecute a la medianoche buscando todas las entregas con estado `PENDIENTE` cuya fecha límite ya pasó, cambiándolas automáticamente a `TARDE` sin necesidad de consumir un endpoint manualmente.

## 5. 🙋‍♂️ Control de Asistencia (Nueva Entidad)
- **Entidad `Asistencia`:** Crear un nuevo modelo relacional vinculado a la `Matricula` y al `Grupo`.
- **Lógica de Reprobación:** Si un estudiante acumula más del 20% de inasistencias a lo largo de las sesiones del curso, una lógica interna en el Service de asistencias cambiaría automáticamente el estado de su matrícula a `REPROBADO_POR_FALLAS`.

## 6. 🛡️ Prevención de Duplicados e Integridad Referencial
- **Regla en Matrícula:** Validar a nivel de servicio y Base de Datos que un estudiante no pueda ser matriculado dos veces exactamente en el mismo grupo.
- **Regla en Entregas:** Restringir que un estudiante solo pueda tener **una única** `Entrega` activa por cada `Evaluacion`. Si el estudiante intenta hacer otro envío, el sistema debe "sobrescribir" la entrega original (actualizando la `fechaEntregaReal` y la `archivoUrl`) en lugar de insertar un nuevo registro en la Base de Datos.
