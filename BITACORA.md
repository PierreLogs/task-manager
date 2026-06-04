# Task Manager — Bitácora del Proyecto

## Versión de la bitácora: 1.0 — 04/06/2026

---

## Estado actual del proyecto

| Nivel | Estado | Descripción |
|-------|--------|-------------|
| **Nivel 1** | ✅ Completado | Java POO + Consola + Persistencia CSV |
| **Nivel 2** | ⏳ Pendiente | Migración a Spring Boot + REST API + PostgreSQL |
| **Nivel 3** | ⏳ Pendiente | Frontend React + Integración Full Stack |
| **Nivel 4** | ⏳ Pendiente | Testing avanzado + CI/CD (GitHub Actions) |

---

## Estructura del proyecto (Nivel 1)

```
task-manager/
├── pom.xml                    # Maven: Java 21, compilación
├── .gitignore                 # Ignora .idea/, target/, data/
├── src/
│   ├── main/java/com/taskmanager/
│   │   ├── Main.java          # Punto de entrada
│   │   ├── model/
│   │   │   ├── Status.java    # Enum: PENDING, IN_PROGRESS, COMPLETED, CANCELLED
│   │   │   ├── Priority.java  # Enum: LOW, MEDIUM, HIGH, CRITICAL
│   │   │   ├── Category.java  # Enum: WORK, PERSONAL, STUDY, HEALTH, FINANCE, OTHER
│   │   │   └── Task.java      # Clase principal del dominio
│   │   ├── repository/
│   │   │   └── TaskRepository.java  # Persistencia en CSV
│   │   ├── service/
│   │   │   └── TaskManager.java     # Lógica de negocio
│   │   └── ui/
│   │       └── ConsoleUI.java       # Menú interactivo por consola
│   └── test/java/com/taskmanager/
└── data/
    └── tasks.csv              # Se genera automáticamente al guardar
```

---

## Historial de commits realizados

| # | Commit | Descripción |
|---|--------|-------------|
| 1 | `8aae0e1` | chore: inicializar proyecto con .gitignore |
| 2 | `304d975` | feat(model): agregar enum Status |
| 3 | `465a0b3` | feat(model): agregar enum Priority |
| 4 | `915fe3e` | feat(model): agregar enum Category |
| 5 | `[hash]` | feat(model): agregar clase Task |
| 6 | `[hash]` | fix(model): agregar constructor completo para reconstrucción |
| 7 | `[hash]` | feat(repository): agregar TaskRepository |
| 8 | `[hash]` | feat(service): agregar TaskManager |
| 9 | `[hash]` | feat(ui): agregar ConsoleUI y Main |
| 10 | — | feat: agregar bitácora del proyecto |

---

## Modelo de dominio (diagrama conceptual)

```
┌──────────────┐     ┌──────────────────┐     ┌────────────────┐
│    Main      │────>│    ConsoleUI     │────>│  TaskManager   │
└──────────────┘     └──────────────────┘     └───────┬────────┘
                                                       │
                                                       ▼
                                               ┌────────────────┐
                                               │ TaskRepository │
                                               └───────┬────────┘
                                                       │
                                               ┌───────┴────────┐
                                               │   tasks.csv    │
                                               └────────────────┘

┌────────────────────────────────────────┐
│                Task                    │
├────────────────────────────────────────┤
│ - id: String (UUID)                    │
│ - title: String                        │
│ - description: String                  │
│ - status: Status                       │
│ - priority: Priority                   │
│ - category: Category                   │
│ - dueDate: LocalDate                   │
│ - createdAt: LocalDateTime (final)     │
│ - updatedAt: LocalDateTime             │
├────────────────────────────────────────┤
│ + isOverdue(): boolean                 │
└────────────────────────────────────────┘

       ▲            ▲            ▲
       │            │            │
  ┌─────────┐  ┌─────────┐  ┌──────────┐
  │ Status  │  │ Priority│  │ Category │
  │ (enum)  │  │ (enum)  │  │ (enum)   │
  └─────────┘  └─────────┘  └──────────┘
```

---

## Principios POO aplicados

| Principio | Dónde se aplica |
|-----------|-----------------|
| **Encapsulamiento** | Atributos `private`, acceso por getters/setters en `Task` |
| **Inmutabilidad parcial** | `id` y `createdAt` son `final` en `Task` |
| **Abstracción** | `TaskRepository` oculta persistencia CSV; `TaskManager` oculta lógica de negocio |
| **Composición** | `ConsoleUI` → `TaskManager` → `TaskRepository` |
| **Responsabilidad única** | Cada clase tiene 1 razón de cambio (modelo, persistencia, negocio, UI) |
| **Fail fast** | Título vacío lanza `IllegalArgumentException` en `createTask()` |
| **Auto-mutación** | Setters actualizan `updatedAt` automáticamente |
| **Método genérico** | `readEnum<T>()` funciona para cualquier enum |

---

## Cómo ejecutar

```bash
cd C:\Users\USER\Documents\task-manager
mvn compile
mvn exec:java -Dexec.mainClass="com.taskmanager.Main"
# O desde IntelliJ: Run Main.main()
```

---

## Próximos pasos (Nivel 2)

- [ ] Agregar dependencias Spring Boot al pom.xml
- [ ] Convertir Task en entidad JPA
- [ ] Crear TaskRepository como interfaz Spring Data JPA
- [ ] Crear controladores REST (CRUD de tareas)
- [ ] Configurar PostgreSQL como base de datos
- [ ] Agregar Docker Compose para PostgreSQL

---

## Nota sobre limpieza accidental

Durante el desarrollo, el `.git` se perdió y los archivos fuente se borraron accidentalmente.
Se restauraron desde la bitácora y se reconfiguró git.

Para continuar desde aquí:

1. Abre IntelliJ IDEA
2. `File` → `Open` → `C:\Users\USER\Documents\task-manager`
3. Verifica que veas `src/main/java/com/taskmanager/` con todas las clases
4. Ejecuta `Main.java` para probar

---

## Comandos útiles de git

```bash
git status                    # Ver archivos modificados
git log --oneline --graph     # Historial de commits
git add -p                    # Agregar cambios interactivamente
git commit -m "tipo: mensaje" # Commit convencional
git push                      # Subir a GitHub
```

## Configuración del entorno

- **JDK**: Java 21.0.11 LTS
- **Build**: Maven
- **IDE**: IntelliJ IDEA
- **GitHub CLI**: gh autenticado como PierreLogs
- **Repo remoto**: https://github.com/PierreLogs/task-manager
