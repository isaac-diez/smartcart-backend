# SmartCart — Lista de Compra Inteligente

Aplicación de lista de compra compartida construida con una arquitectura de **microservicios** y **arquitectura hexagonal**. Permite gestionar listas de compra colaborativas en tiempo real, con autocompletado inteligente de productos mediante IA.

---

## ✨ Funcionalidades

- Registro y autenticación de usuarios con JWT
- Creación de grupos para compartir listas de compra
- Gestión de listas múltiples por grupo
- Artículos con ciclo de vida de estados: `WANTED → PURCHASED → STORED`
- Notificaciones en tiempo real via SSE cuando un miembro del grupo cambia el estado de un artículo
- Autocompletado de productos con Gemini AI y Open Food Facts API
- Historial de cambios de estado por usuario

---

## Arquitectura

### Microservicios

| Servicio | Puerto | Responsabilidad |
|---|---|---|
| `api-gateway` | 8080 | Punto de entrada único. Validación JWT y routing. |
| `auth-service` | 8081 | Registro, login y generación de tokens JWT. |
| `user-group-service` | 8082 | Gestión de usuarios y grupos compartidos. |
| `list-item-service` | 8083 | Core: listas de compra y artículos. |
| `product-service` | 8084 | Autocompletado con IA y caché Redis. |
| `notification-service` | 8085 | Notificaciones en tiempo real via SSE. |

### Arquitectura Hexagonal

Cada microservicio sigue el patrón de puertos y adaptadores:

```
src/main/java/com/smartcart/<servicio>/
├── domain/
│   ├── model/        ← Entidades de dominio puras (sin frameworks)
│   ├── port/
│   │   ├── in/       ← Interfaces de casos de uso
│   │   └── out/      ← Interfaces de dependencias externas
│   └── service/      ← Lógica de negocio
├── application/
│   └── dto/          ← DTOs de entrada/salida
└── infrastructure/
    ├── in/rest/      ← Controllers REST
    └── out/
        ├── persistence/  ← Adaptadores JPA
        └── notification/ ← Adaptadores HTTP/Kafka
```

**Regla fundamental:** el paquete `domain` no importa nada externo. Spring, JPA y cualquier framework solo existen en `infrastructure`.

### Flujo de autenticación

```
Frontend
    │  Authorization: Bearer <JWT>
    ▼
API Gateway (8080)     ← valida el JWT
    │  X-User-Id: <uuid>  ← propaga el userId
    ▼
Microservicio          ← confía en X-User-Id
```

---

## Stack tecnológico

**Backend**
- Java 21
- Spring Boot 3.3
- Spring Cloud Gateway
- Spring Data JPA + Hibernate
- Spring Security
- Flyway (migraciones de BD)
- JWT (jjwt)

**Base de datos e infraestructura**
- PostgreSQL en Supabase (3 schemas independientes: `auth_svc`, `usergroup_svc`, `listitem_svc`)
- Redis (caché de productos y rate limiting)
- Docker + Docker Compose

**Frontend** *(en desarrollo)*
- React 18 + TypeScript
- Vite
- TanStack Query
- TailwindCSS

---

## 🗄Modelo de datos

### auth_svc
```sql
users (id, username, email, password_hash, created_at, active)
```

### usergroup_svc
```sql
users (id, username, email, display_name, created_at)
groups (id, name, invite_code, created_by, created_at)
group_members (group_id, user_id, role, joined_at)
```

### listitem_svc
```sql
shopping_lists (id, group_id, name, created_by, created_at, archived)
items (id, list_id, name, category, quantity, unit, status,
       added_by, status_owner, added_at, purchased_at, stored_at, wanted_at)
```

---
## API Reference

Todas las peticiones (excepto auth) requieren el header:
```
Authorization: Bearer <access_token>
```

### Auth
```
POST /api/auth/register   { username, email, password }
POST /api/auth/login      { email, password }
POST /api/auth/refresh?refreshToken=<token>
```

### Listas
```
POST   /api/lists/create              { groupId, name }
GET    /api/lists/group/{groupId}
GET    /api/lists/{listId}
PUT    /api/lists/{listId}/archive
PUT    /api/lists/{listId}/unarchive
```

### Artículos
```
POST   /api/items                     { listId, name, category, quantity, unit }
GET    /api/items/getbylistandstatus?listId=<uuid>&status=<WANTED|PURCHASED|STORED>
PUT    /api/items/{itemId}/purchase
PUT    /api/items/{itemId}/return
PUT    /api/items/{itemId}/store
PUT    /api/items/{itemId}/wantAgain
DELETE /api/items/{itemId}
```

### Notificaciones (SSE)
```
GET    /api/notifications/groups/{groupId}/stream
```

---

## 🗺️ Roadmap

- [x] `auth-service` — registro, login, JWT
- [x] `api-gateway` — routing, validación JWT, propagación `X-User-Id`
- [x] `list-item-service` — listas y artículos con ciclo de estados
- [ ] `user-group-service` — usuarios, grupos e invitaciones
- [ ] `notification-service` — SSE en tiempo real
- [ ] `product-service` — autocompletado con Gemini + Open Food Facts
- [ ] Frontend React
- [ ] Kafka (eventos entre microservicios)
- [ ] Observabilidad: Prometheus + Grafana + Resilience4j

---

## 📐 Decisiones de arquitectura

**¿Por qué arquitectura hexagonal?**
El dominio no depende de ningún framework. Cambiar de PostgreSQL a MongoDB, de HTTP a Kafka, o de Spring Security a Keycloak solo afecta a los adaptadores — la lógica de negocio no se toca.

**¿Por qué un schema por servicio en lugar de una BD por servicio?**
El plan gratuito de Supabase permite un proyecto activo. Usando schemas separados (`auth_svc`, `usergroup_svc`, `listitem_svc`) se mantiene el aislamiento lógico de datos propio de microservicios sin coste adicional.

**¿Por qué X-User-Id en lugar de validar JWT en cada servicio?**
El gateway valida el token una sola vez y propaga el `userId` como header interno. Los servicios downstream confían en ese header, lo que reduce el consumo de recursos y centraliza la responsabilidad de autenticación.