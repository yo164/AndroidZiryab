# Ziryab - Documentación Técnica

## Índice
1. [Descripción general](#descripción-general)
2. [Stack tecnológico](#stack-tecnológico)
3. [Arquitectura](#arquitectura)
4. [Módulos y funcionalidades](#módulos-y-funcionalidades)
5. [Base de datos local (Room)](#base-de-datos-local-room)
6. [Navegación](#navegación)
7. [Backend](#backend)
8. [WorkManager y notificaciones](#workmanager-y-notificaciones)
9. [Issues pendientes](#issues-pendientes)

---

## Descripción general

Aplicación Android para la gestión de centros educativos. Permite a profesores y alumnos gestionar asistencias, justificar faltas mediante fotografía, y recibir notificaciones de justificaciones pendientes.

Roles de usuario:
- **TEACHER**: gestiona asistencias, recibe notificaciones de justificaciones pendientes, valida justificantes.
- **STUDENT**: consulta sus faltas, sube justificantes mediante foto o archivo.

---

## Stack tecnológico

### Android
- Kotlin + Jetpack Compose
- Hilt (inyección de dependencias, KSP)
- Retrofit2 (llamadas remotas)
- Room 2.6.1 (SQLite local)
- WorkManager 2.11.1 + hilt-work 1.3.0
- Coil (carga de imágenes)
- CameraX (cámara)
- Firebase Auth (autenticación)
- Accompanist Permissions (gestión de permisos)
- Kotlinx Serialization (navegación type-safe)

### Backend
- Node.js + Prisma + PostgreSQL
- Firebase Auth (validación de tokens)

---

## Arquitectura

Patrón: **API → RemoteDataSource → Repository → ViewModel → UI**

### Regla offline-first en repositories
1. Consultar Room local
2. Si vacío → llamar remote → guardar en Room → devolver
3. Si falla remote → devolver local

### Capas
```
ui/           → Screens y ViewModels (Jetpack Compose)
domain/       → Modelos de dominio e interfaces de repository
data/
  remote/     → Retrofit, RemoteDataSource, modelos remotos
  local/      → Room, DAOs, entidades, LocalDataSource
  repository/ → Implementaciones de repository
  initialdata/→ InitialDataController
notifications/→ NotificationHelper
work/         → JustificationCheckWorker
```

---

## Módulos y funcionalidades

### Autenticación
- Login con Firebase Auth + JWT propio del backend
- Al login se guarda sesión en `SessionViewModel` (userId, userRole, token)
- Según rol se navega a `ClasesProfesor` o `ClasesAlumno`
- Logout limpia sesión y borra datos locales (excepto asistencias)

### Carga inicial de datos (`InitialDataController`)
Al hacer login se ejecuta `cargarDatosIniciales()`:

**TEACHER:**
- `weekScheduleRepository.getWeekScheduleByTeacher()`
- `assistanceRepository.getAll()` → carga las 96 asistencias en Room
- `teacherRepository.getTeacherById()`
- `programarWorker(idTeacher)` → lanza WorkManager

**STUDENT:**
- `studentWeekScheduleRepository.getWeekScheduleByStudent()`
- `assistanceRepository.getAll()` → carga todas las asistencias en Room
- `clasesAlumnoRepository.getClases()`

### Módulo de Asistencias

**Flujo profesor:**
1. Entra en una clase → ve lista de alumnos (`AlumnoListScreen`)
2. Marca asistencia de cada alumno (PRESENT / MISSING / LAG)
3. Pulsa enviar → si no hay asistencias → `createBulk`, si ya existen → `patchAsistencias` (solo los cambiados)

**Flujo alumno:**
1. Ve sus faltas en `FichaUsuarioScreen`
2. Pulsa en una falta → navega a `JustificarFaltaScreen`
3. Hace foto con cámara o sube archivo
4. La foto se mueve a carpeta pública: `/files/Pictures/justificantes/`
5. Confirma → llama a `assistanceRepository.justifyRequest(id, uri)`
6. El repository actualiza uri en local y llama al endpoint de justificación remoto

**Modelos remotos relevantes:**
```kotlin
AssistanceItemRemote(
    id, idStudentEnrollment, status, createdAt,
    studentEnrollment: AssistanceStudentEnrollmentRemote,
    session: AssistanceSessionNestedRemote(
        id, date,
        schedule: ScheduleNestedRemote(
            startTime,
            teacherAssignment: AssignmentNestedRemote(
                idTeacher,
                subject: SubjectNestedRemote(name)
            )
        )
    )
)
```

**Entidad Room:**
```kotlin
@Entity(tableName = "assistance")
data class AssistanceEntity(
    id, idStudent, idSession, idStudentEnrollment,
    idTeacher, status, subjectName, date, startTime, uri
)
```

**Dominio:**
```kotlin
data class AssistanceItem(
    id, idStudent, idSession, idStudentEnrollment,
    idTeacher, status, subjectName, date, startTime, uri
)
```

### Módulo de Notificaciones

**Flujo:**
1. WorkManager (`JustificationCheckWorker`) se ejecuta cada 15 minutos
2. Busca asistencias en Room con `(status = MISSING o LAG) AND uri IS NOT NULL AND idTeacher = :idTeacher`
3. Por cada resultado: lanza notificación al sistema + guarda `NotificationEntity` en Room
4. El profesor ve las notificaciones en `NotificationListScreen`
5. Pulsa una notificación → `JustificacionSessionService.set(uri, idAssistance)` → navega a `JustificarScreen`
6. El profesor ve la imagen del justificante
7. Pulsa el botón → `assistanceRepository.justifyAssistancebyId(id)` (remote + local) + `notificationsRepository.updateStatusByAssistanceId(id, "VIEWED", updatedAt)`

**Entidad Room:**
```kotlin
@Entity(
    tableName = "notification",
    foreignKeys = [ForeignKey(entity = AssistanceEntity::class, parentColumns = ["id"], childColumns = ["idAssistance"], onDelete = CASCADE)],
    indices = [Index(value = ["idAssistance"], unique = true)]
)
data class NotificationEntity(
    id (autoGenerate), idAssistance, status, createdAt, updatedAt
)
```

La constraint `unique = true` en `idAssistance` garantiza que no haya notificaciones duplicadas para la misma asistencia.

**POJO con relación:**
```kotlin
data class NotificationWithAssistance(
    @Embedded val notification: NotificationEntity,
    @Relation(parentColumn = "idAssistance", entityColumn = "id")
    val assistance: AssistanceEntity?
)
```

**Dominio:**
```kotlin
data class NotificationItem(
    id, idAssistance, status, createdAt, updatedAt,
    // datos de AssistanceItem via @Relation:
    idStudent, idSession, idStudentEnrollment, idTeacher,
    assistanceStatus, subjectName, date, startTime, uri
)
```

### Módulo de cámara

- `CameraScreen`: usa CameraX, guarda la foto en `externalCacheDir`
- `JustificarFaltaViewModel.onPhotoTaken(uri)`: mueve el fichero de caché a carpeta pública `/files/Pictures/justificantes/` para que Coil pueda leerla
- `onFileSelected(uri)`: maneja archivos seleccionados del dispositivo (sin mover)

### Sesión compartida entre pantallas

**`JustificacionSessionService`** (Singleton):
```kotlin
private val _uri = MutableStateFlow<String?>(null)
private val _idAssistance = MutableStateFlow<Int?>(null)
fun set(uri: String, idAssistance: Int)
fun clear()
```
Se usa para pasar datos de la pantalla de notificaciones a la pantalla de justificación sin pasar parámetros por la navegación.

**`AssignmentSessionService`**: guarda `currentSubjectId`, `currentGroupId`, `currentClassSession` para la pantalla de lista de alumnos.

---

## Base de datos local (Room)

**`AppDatabase`** versión actual: 14 (con `fallbackToDestructiveMigration`)

### Entidades
| Tabla | Descripción |
|-------|-------------|
| `assistance` | Asistencias de alumnos |
| `notification` | Notificaciones enviadas al profesor |
| `horario` | Horario semanal (con `idUser` y `role` para filtrar) |
| `teacher` | Datos del profesor |
| `subject` | Asignaturas del alumno |
| `group` | Grupos (en desuso, pendiente de revisar) |

### DAOs relevantes
- `AssistanceDao`: getBySessionId, getByStudentId, getPendingJustifications(idTeacher), insertAll, updateStatus, updateUri, deleteAll
- `NotificationDao`: getAll (@Transaction, devuelve NotificationWithAssistance), getById, insert, updateStatus, updateStatusByAssistanceId, deleteAll

---

## Navegación

### Rutas (`Route.kt`)
```kotlin
sealed class Route {
    Login
    ClasesAlumno
    ClasesProfesor
    Gestion
    GestionClases
    AlumnoList
    Temario(id, nombre)
    FichaUsuario
    Horario
    Calendario
    Tablon
    Groups
    Task
    JustificarScreen      // pantalla imagen justificante para profesor
    JustificarFalta(id, subjectName, date, startTime, status)  // para alumno
    Notificaciones
}
```

### Flujo de navegación por rol

**TEACHER:**
```
Login → ClasesProfesor → GestionClases → AlumnoList
Gestion(5) → Notificaciones → JustificarScreen
```

**STUDENT:**
```
Login → ClasesAlumno
Gestion(1) → FichaUsuario → JustificarFalta
```

### Footer (`AppFooter`)
Visible en: ClasesAlumno, ClasesProfesor, Gestion, GestionClases, Task, AlumnoList, Notificaciones, JustificarScreen

---

## Backend

### Endpoints relevantes

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | /login | Login, devuelve JWT |
| GET | /api/assistances | Todas las asistencias (con includes completos) |
| GET | /api/assistances/session/:id | Asistencias por sesión |
| POST | /api/assistances/bulk | Crear asistencias en bulk |
| PATCH | /api/assistances/:id/status | Actualizar estado |
| PATCH | /api/assistances/justify/:id | Justificar asistencia |
| GET | /api/teachers/:id | Datos del profesor |
| GET | /api/students/:id/subjects | Clases del alumno |

### Include anidado en `findAll`
El endpoint `GET /api/assistances` incluye el anidado completo para que Kotlin pueda extraer `subjectName`, `date`, `startTime` e `idTeacher` sin llamadas adicionales:
```
assistance → session → schedule → teacherAssignment (idTeacher) → subject (name)
assistance → studentEnrollment → student
```

---

## WorkManager y notificaciones

### `JustificationCheckWorker`
- `@HiltWorker` + `@AssistedInject`
- Se programa como `PeriodicWork` cada 15 minutos con `enqueueUniquePeriodicWork`
- Lee `idTeacher` de `inputData`
- Llama a `assistanceRepository.getPendingJustifications(idTeacher)` → primero intenta remote, si falla tira de Room local
- Por cada resultado: lanza notificación + inserta `NotificationEntity` en Room

### `PrimerBocetoApplication`
```kotlin
@HiltAndroidApp
class PrimerBocetoApplication: Application(), Configuration.Provider {
    @Inject lateinit var workerFactory: HiltWorkerFactory
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
```

### AndroidManifest
El inicializador automático de WorkManager está desactivado para permitir la configuración personalizada con Hilt:
```xml
<provider
    android:name="androidx.startup.InitializationProvider"
    android:authorities="${applicationId}.androidx-startup"
    tools:node="remove">
</provider>
```

### `NotificationHelper`
- `@Singleton`, inyecta `@ApplicationContext` y `NotificationsRepository`
- Canal: `justification_channel`
- `showJustificationNotification(id)`: lanza notificación del sistema + guarda en Room

---

## Issues pendientes

- [ ] Subir fichero justificante a almacenamiento remoto (S3/Cloudinary) en lugar de URI local
- [ ] Migrar Room a migraciones definidas (actualmente `fallbackToDestructiveMigration`)
- [ ] Footer no redirige correctamente según rol en todos los casos
- [ ] Pedir permiso `POST_NOTIFICATIONS` al arrancar la app (Android 13+)
- [ ] Cuando el permiso de cámara está denegado, mostrar mensaje "Ve a ajustes para activar la cámara"
- [ ] Reordenar ficheros: footer y header en ficheros separados
- [ ] Módulo de Groups pendiente de implementar
- [ ] Unificar criterio de paso de datos entre NavGraph y menús
