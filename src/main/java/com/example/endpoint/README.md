\# endpoint package



This package contains a \*\*real Jakarta WebSocket endpoint example\*\* showing how to use the room manager package inside a live WebSocket application.



It is intentionally simple and designed for learning, adaptation, and extension.



\---



\## Package Goal



The goal of this package is to demonstrate:



\- how a WebSocket connection is accepted

\- how query parameters are read

\- how a `PlayerSession` is created

\- how a session joins a room

\- how incoming messages are broadcast to the room

\- how cleanup happens on disconnect



This package acts as the bridge between:



\- the WebSocket transport layer

\- the room management layer



\---



\## Main class



\### `RoomWebSocketEndpoint`

A simple Jakarta WebSocket endpoint using `@ServerEndpoint`.



Responsibilities:



\- accept WebSocket connections

\- read request parameters

\- validate required parameters

\- create `PlayerSession`

\- join the user to a room

\- broadcast incoming messages to the room

\- remove the user on close or error



\---



\## Example connection URL



```text

ws://localhost:8080/yourapp/ws/room?roomId=room-1\&playerId=u1\&playerName=Alice

```



Expected parameters:



\- `roomId`

\- `playerId`

\- `playerName`



\---



\## Request lifecycle



\### On Open

When the socket opens:



1\. read request parameters

2\. validate the required values

3\. create a `PlayerSession`

4\. store session-related data in `session.getUserProperties()`

5\. create or retrieve the room

6\. join the room

7\. broadcast a system join message



\---



\### On Message

When a text message arrives:



1\. get the room ID from session properties

2\. get the `PlayerSession`

3\. prepend sender info

4\. broadcast the message to the room



\---



\### On Close

When the socket closes:



1\. identify the room/session data

2\. remove the player from the room

3\. broadcast a leave message

4\. allow empty room cleanup



\---



\### On Error

If an error occurs:



1\. log the error

2\. attempt cleanup

3\. remove the player from the room if needed



\---



\## Why this example is intentionally simple



This endpoint is a starter example, not a full production endpoint.



It avoids complexity such as:



\- authentication

\- structured JSON commands

\- authorization rules

\- rate limiting

\- room host permissions

\- persistence

\- distributed clustering



That simplicity makes it easier to understand and easier to reuse.



\---



\## Recommended production improvements



For production systems, a developer may want to add:



\- JSON message parsing

\- structured message types

\- authentication tokens

\- room access control

\- heartbeat / ping handling

\- session timeout logic

\- reconnection support

\- metrics and tracing

\- distributed coordination with Redis

\- graceful retry logic



\---



\## How this package connects to `roommanager`



The endpoint package should stay thin.



Its main role is to translate WebSocket events into calls such as:



\- `RoomManager.createRoom(...)`

\- `RoomManager.joinRoom(...)`

\- `RoomManager.broadcastToRoom(...)`

\- `RoomManager.leaveRoom(...)`



This separation keeps the transport layer and room logic cleanly separated.



\---



\## AI-assisted development notes



This markdown file helps AI tools understand the intended role of the endpoint package.



A developer can modify this file and ask an AI assistant to generate bespoke endpoint variants such as:



\- a JSON command-based endpoint

\- a team-room endpoint

\- a private invite-only room endpoint

\- a classroom session endpoint

\- a multiplayer match endpoint with ready-state support



Examples of prompts:



\- "Change this endpoint into a JSON-based chat endpoint"

\- "Add authentication token validation before room join"

\- "Convert this into a multiplayer lobby endpoint"

\- "Add room capacity checks and return close reason if full"



\---



\## Key design principle



Keep the endpoint focused on transport handling.



Keep room behavior in the `roommanager` package.



Keep business logic in higher-level application services.

