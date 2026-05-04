# LudoWorld Architecture

## Overview

ludoworld is a real-time multiplayer game platform that enables synchronized gameplay between distributed players on web and mobile clients.

The platform is designed around a **room-based, server-authoritative architecture**. Each active match is treated as an isolated game room, and all meaningful gameplay decisions are validated and applied by the server.

This design helps maintain fairness, synchronization, and consistency across all connected players.

---

## Architectural Principles

### 1. Room-Based Design
Each multiplayer game is organized as a distinct room.

A room contains:

- the active players in the match
- the game state for that match
- the turn order
- token positions
- room-scoped messages and events

This allows multiple games to run in parallel without interfering with one another.

---

### 2. Persistent WebSocket Communication
Clients maintain a persistent WebSocket connection with the server.

This allows:

- real-time move updates
- turn notifications
- chat and room events
- reconnect-aware session handling
- immediate synchronization across players

WebSockets are used because turn-based multiplayer still benefits from low-latency bidirectional communication.

---

### 3. Authoritative Server Control
The server is the final source of truth for gameplay.

The server is responsible for validating and applying:

- dice roll results
- legal moves
- turn switching
- capture events
- end-game states

This helps prevent client-side manipulation and keeps all players synchronized around one authoritative state.

---

### 4. Event-Driven State Updates
Game progression is expressed through discrete events.

Examples include:

- player joins room
- dice is rolled
- token is moved
- turn changes
- player disconnects
- game ends

This creates a clean mental model for both debugging and future replay systems.

---

## High-Level Platform Layers

### Client Layer
The client layer consists of:

- browser-based gameplay clients
- mobile clients using Flutter WebView

Responsibilities:

- render game UI
- send player actions
- display server-approved updates
- maintain WebSocket connection
- present synchronized board state

---

### Real-Time Communication Layer
This layer handles the live WebSocket connection between clients and backend.

Responsibilities:

- accept client connections
- receive gameplay and room messages
- push state updates to connected players
- maintain live interaction for multiplayer rooms

---

### Game Room / Session Layer
This layer tracks the active room and players in that room.

Responsibilities:

- assign players to matches
- manage room membership
- identify active participants
- isolate state by room
- clean up disconnected sessions

---

### Gameplay Logic Layer
This layer applies the game rules.

Responsibilities:

- validate legal moves
- process turn order
- update token state
- enforce server-authoritative rules
- determine game completion

---

### Persistence / Support Layer
This layer stores durable or supporting data outside the hot gameplay path.

Possible responsibilities:

- user account records
- game summaries
- event history
- audit trail
- optional replay support

---

## Room-Based Architecture

The room is a central unit of the platform.

Each room may include:

- unique room ID
- list of players
- active turn index
- token positions
- room status
- event history
- room-scoped chat

Because each room is isolated, the platform can conceptually scale by distributing rooms across multiple server workers in the future.

---

## Server-Authoritative Gameplay

The server-authoritative model means that the client does not own the game state.

Instead:

1. the client sends an action request
2. the server validates the request
3. the server updates the official game state
4. the server broadcasts the result to all room participants

This protects the game from inconsistent state and creates a foundation for fair multiplayer logic.

---

## Hybrid Web / Mobile Access

ludoworld supports both browser clients and mobile app access.

The mobile app uses a hybrid architecture:

- Flutter container
- embedded WebView
- JavaScript ↔ native communication bridge
- real-time WebSocket gameplay inside the web layer

This makes it possible to combine a shared gameplay frontend with mobile-native features.

---

## Future Architectural Direction

The architecture is designed to support future improvements such as:

- deterministic replay systems
- predictive client simulation
- spectator mode
- room ownership partitioning
- distributed room scaling
- analytics and event history

These future directions build naturally on the current room-based, event-driven design.

---

## Summary

ludoworld is not only a digital board game implementation. It is a real-time multiplayer platform built around:

- isolated room-based matches
- persistent WebSocket communication
- authoritative server-side gameplay control
- hybrid client access across web and mobile

This architecture provides a strong foundation for fair gameplay, synchronized state, and future scalability.

## License

MIT