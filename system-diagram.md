# Ludoworld System Diagram

## Overview

This document describes the high-level system architecture of ludoworld using a simplified diagram and layered explanation.

The goal is to show how different components interact in a real-time multiplayer environment using WebSockets and a server-authoritative model.

---

## High-Level Architecture Diagram

```text
         ┌──────────────────────┐
         │   Web Clients        │
         │ (Browser Gameplay)   │
         └─────────┬────────────┘
                   │
         ┌─────────▼────────────┐
         │   Mobile Clients     │
         │ (Flutter + WebView)  │
         └─────────┬────────────┘
                   │
                   ▼
        ┌─────────────────────────┐
        │ WebSocket Connection    │
        │ Layer                   │
        └─────────┬──────────────┘
                  │
                  ▼
        ┌─────────────────────────┐
        │ Room / Session Manager  │
        │                         │
        │ - player tracking       │
        │ - room assignment       │
        │ - session lifecycle     │
        └─────────┬──────────────┘
                  │
                  ▼
        ┌─────────────────────────┐
        │ Authoritative Game      │
        │ Logic Engine            │
        │                         │
        │ - dice validation       │
        │ - move validation       │
        │ - turn control          │
        │ - game state updates    │
        └─────────┬──────────────┘
                  │
                  ▼
        ┌─────────────────────────┐
        │ State Broadcast Layer   │
        │                         │
        │ - send updates to all   │
        │   players in room       │
        └─────────┬──────────────┘
                  │
                  ▼
        ┌─────────────────────────┐
        │ Persistence / Support   │
        │ Layer                   │
        │                         │
        │ - user data             │
        │ - match summaries       │
        │ - optional event logs   │
        └─────────────────────────┘
```

---

## Component Breakdown

### 1. Client Layer

Includes:

- browser-based clients
- mobile clients (Flutter WebView)

Responsibilities:

- render UI
- send user actions
- display server-approved updates
- maintain WebSocket connection

---

### 2. WebSocket Connection Layer

Handles:

- persistent connections
- real-time messaging
- bidirectional communication

Why it matters:

- allows low-latency interaction
- enables instant gameplay updates
- keeps players synchronized

---

### 3. Room / Session Manager

Core responsibility:

- group players into isolated game rooms

Handles:

- player join/leave
- room lifecycle
- session tracking
- room-based message routing

Key idea:

> Each game is an isolated room.

---

### 4. Authoritative Game Logic Engine

This is the most important layer.

Responsibilities:

- validate all gameplay actions
- enforce rules
- update official game state

Examples:

- dice roll validation
- token movement validation
- turn switching
- win detection

Key principle:

> The server is always the source of truth.

---

### 5. State Broadcast Layer

After processing a move:

- server sends updated state to all players in the room

This ensures:

- all players see the same board
- no desynchronization occurs

---

### 6. Persistence / Support Layer

Stores long-term data such as:

- user accounts
- match results
- credits or rewards
- optional event logs

Note:

Live gameplay should not depend heavily on database calls for performance reasons.

---

## Data Flow Summary

```text
Client Action
      ↓
WebSocket Message
      ↓
Room Routing
      ↓
Server Validation
      ↓
State Update
      ↓
Broadcast to Room
      ↓
Client UI Refresh
```

---

## Architectural Characteristics

### Room-Based Isolation
Each match is independent.

This allows:

- easier scaling
- cleaner logic separation
- reduced interference between games

---

### Server Authority
Clients request actions.

Server decides:

- what is valid
- what is applied
- what is broadcast

---

### Event-Driven Design
Game state changes are triggered by events:

- roll
- move
- join
- leave
- turn change

This supports:

- debugging
- replay systems
- analytics

---

## Future Extensions

This architecture supports future improvements:

- deterministic replay system
- spectator mode
- distributed room scaling
- predictive client simulation
- analytics and event tracking

---

## AI-Friendly Notes

This diagram is intentionally written in structured text form so it can be:

- converted into visual diagrams
- interpreted by AI tools
- used to generate custom architecture variants

Possible AI prompts:

- "Convert this into a Kubernetes architecture diagram"
- "Add Redis layer for distributed room coordination"
- "Turn this into a scalable multi-region system"
- "Add replay system using event sourcing"

---

## Summary

ludoworld architecture is built around:

- real-time WebSocket communication
- room-based multiplayer sessions
- server-authoritative game logic
- event-driven state updates

This design provides a strong foundation for building scalable, fair, and synchronized multiplayer experiences.

## License

MIT