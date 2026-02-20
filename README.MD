# Cronoclase

Plataforma interactiva para la gestión visual de hitos académicos y seguimiento dinámico del progreso semestral.

## Introducción / Contexto

- **Descripción del problema:** La desarticulación entre el pacto pedagógico y el seguimiento diario genera confusión en los estudiantes sobre los plazos y una carga administrativa excesiva para los docentes al gestionar entregas.
- **Justificación:** Es relevante porque centraliza la planificación educativa en una herramienta visual, mejorando la organización del tiempo, reduciendo el incumplimiento de entregas y permitiendo un control transparente del avance académico.
- **Dominio:** Gestión de procesos educativos y seguimiento de cronogramas academicos.

## Objetivos

**Objetivo General** Permitir el seguimiento de un periodo académico a través de una línea de tiempo dinámica que se actualice conforme al avance del mismo, facilitando la gestión de hitos, evaluaciones y actividades para optimizar la planificación y el seguimiento del proceso educativo.

**Objetivos Específicos** - **OE1:** Implementar una línea de tiempo interactiva que refleje el progreso del semestre, permitiendo la definición y visualización de hitos clave.
- **OE2:** Diseñar un sistema de gestión de actividades que permita a los docentes asignar tareas y a los estudiantes realizar entregas dentro de los plazos.
- **OE3:** Integrar funcionalidades de notificación para alertar a los usuarios sobre evaluaciones y cierres de actividades próximos.
- **OE4:** Desarrollar un módulo de manejo de archivos para permitir la carga y descarga eficiente de documentos académicos.

## Alcance del Proyecto (Scope)

**Qué se va a desarrollar:** - Visualizador de línea de tiempo con estados de progreso.
- Módulo de gestión de hitos (creación, edición y visualización).
- Sistema de carga de tareas y gestión de archivos adjuntos.
- Panel de notificaciones para fechas críticas.
- Autenticación y perfiles diferenciados (Docente / Estudiante).

**Qué NO se va a desarrollar en esta versión (fuera de alcance):** - Integración con Google Calendar o plataformas externas de calendario.
- Calificación automática de cuestionarios.
- Generación de certificados finales en PDF.

## Tecnologías y Herramientas (Tech Stack)

- **Backend**: Spring Boot 3.x, Java 17/21, Spring Data JPA.
- **Frontend**: React.
- **Base de datos**: PostgreSQL.
- **Otras herramientas**: Git, GitHub, Postman, Swagger, Docker.

## Integrantes del Equipo

| Nombre                  | Rol principal              | Usuario GitHub     |
|-------------------------|----------------------------|--------------------|
| Irwin Colmenarez              | Líder / Frontend           | @Irwincol         |
| Paula  Gil              | Líder / Backend         | @GGP113         |
| Carlos Martínez              | Desarrollador Frontend            | @CMARTINEZ-95         |
| Estiben Manco              | Desarrollador Backend           | @Estibenmanco31         |
| Víctor Alejandro Berrío              | Desarrollador DB        | @Vastrocode72        |
| Sebastián Hernández             | Desarrollador Frontend        | @Sebas-1013         |

## Diagrama de Clases del Dominio (v1)

El diagrama de dominio muestra las entidades principales del sistema (por ejemplo, Usuario, Docente, Estudiante, Actividad, Hito, Entrega) y las relaciones entre ellas. Es una vista conceptual que ayuda a entender el modelo antes de entrar a la implementacion tecnica.

![Diagrama de Dominio v1](docs/diagrama-dominio-v1.png) 
*Diagrama inicial del modelo de dominio – versión 1. Se actualizará en futuras entregas.*

## Instrucciones de Instalación y Ejecución (para desarrolladores)

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/irwincol/cronoclase-grupo-5.git