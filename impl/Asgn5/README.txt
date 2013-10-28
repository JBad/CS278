This briefly describes how this application works:

It is a photo sharing app.

There is a server (SnapServer) this server accepts new clients, and stores the photos that they all push up to the server.

There is a client (SnapClone) this client can do three things: 
-register with the server (by passing a username)
-send a picture to the server.
-request a picture from the server.

When a user sends a picture to the server it gets a random number from the server (which is the identifier of the picture), note that a user MUST upload a picture so that it can begin to download images.

When the user requests a picture from the server, it will pass a number with it, notifying the server of the last number that the client has seen.  The server will respond with a picture and new number.  If the number is the most recent photo, the server will not change the nubmer returned and continuously repsond with the most recent picture. 
