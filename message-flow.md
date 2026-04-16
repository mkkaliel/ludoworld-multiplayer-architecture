# LudoWorld Message Flow

## Overview

LudoWorld uses WebSocket-based real-time communication to coordinate gameplay between connected players.

The platform follows a **server-authoritative message flow**, meaning that clients send requests, but the server validates and applies the official game state changes.

---

## Core Flow

At a high level, gameplay follows this pattern:

1. client sends action
2. server validates action
3. server updates official room state
4. server broadcasts result to room participants
5. clients update UI based on server-approved state

This ensures that all participants stay synchronized.

---

## Typical Room Lifecycle

### 1. Player Connects
A player opens the game client and establishes a WebSocket connection.

The server:

- accepts the connection
- identifies the player/session
- attaches the player to a room or match
- prepares room-scoped communication

---

### 2. Player Joins a Match Room
Once assigned to a room, the player becomes part of a specific game session.

The server may broadcast room events such as:

- player joined
- player ready
- game starting

---

### 3. Gameplay Begins
During gameplay, all key actions pass through the server.

Typical action categories:

- roll request
- move request
- chat message
- room event
- disconnect/reconnect event

---

## Example: Dice Roll Flow

### Client Side
The active player performs a dice action.

The client sends a request such as:

- roll dice
- current player identifier
- room identifier

### Server Side
The server:

- checks whether it is actually that player's turn
- determines or validates the roll result
- calculates playable outcomes
- updates the room state
- broadcasts the result to the room

### Room Broadcast
All players in the room receive the approved result.

Clients then:

- update dice display
- highlight possible moves
- update turn information

---

## Example: Token Move Flow

### Client Side
The player selects a token and requests a move.

### Server Side
The server validates:

- correct room
- correct player
- legal token
- legal move distance
- rule compliance

If valid, the server updates the official state.

### Broadcast
The new state is sent to all players in the room so everyone sees the same board result.

---

## Turn Switching Flow

When a move is completed, the server determines the next state.

This may include:

- same player continues
- next player turn starts
- special move logic
- end-game checks

The server then broadcasts the turn update.

Clients update the UI accordingly.

---

## Disconnect Handling

If a player disconnects, the server may:

- mark the player inactive
- clean up the session
- preserve room state if reconnect is expected
- notify the room
- apply turn or match handling rules

This is important in real-time multiplayer systems because session instability is normal on mobile and web networks.

---

## Why Server Authority Matters

A server-authoritative model improves:

- fairness
- synchronization
- cheat resistance
- state consistency
- replay readiness

Without server authority, each client could drift into different state interpretations.

---

## Conceptual Flow Diagram

```text
Client Action
    ↓
WebSocket Message
    ↓
Room Routing
    ↓
Server Validation
    ↓
Official Game State Update
    ↓
Broadcast To Room
    ↓
Client UI Refresh
```

---

## Message Categories

Examples of conceptual message categories in the platform include:

- room join / leave
- roll action
- move action
- turn update
- chat / social messages
- disconnect / reconnect
- game completion

The exact implementation details may evolve, but the architectural principle remains the same: all important gameplay transitions pass through the server.

---

## Future Message Flow Enhancements

This architecture supports future additions such as:

- deterministic replay event logs
- predictive client simulation
- distributed room routing
- replay-driven debugging
- live spectator streams

These enhancements build naturally on the same event-oriented message flow model.

---

## Summary

The LudoWorld message flow is built around:

- room-scoped communication
- authoritative server validation
- synchronized broadcasts
- event-based gameplay progression

This structure makes the platform easier to reason about, debug, scale, and extend.

## License

MIT