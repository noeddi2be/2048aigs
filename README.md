# AIGS Project Documentation 
FHNW 2024 - AI Game Service (Software Engineering)

### Project Contributors
👨🏽‍💻 Manuel Notter

### Project Description
AIGS is a fully implemented gameserver. The initial server was supporting one game: TicTacToe. The repository of the original server as well as the client for TicTacToe can be found here:

Server repository: [Server Repository](https://gitlab.fhnw.ch/bradley.richards/aigs-spring-server) 
Client repository: [Client Repository](https://gitlab.fhnw.ch/bradley.richards/aigs-game-clients)

### Project Objective

The objective of this project was to extend the existing project by creating a client for an existing game, develop an entirely new game, or reimplement the game-server. As the last option would have been to big of a task, I chose to implement a new game for the game server and develop a frontend client to play the game.

## The Game: 2048

2048 is a single-player sliding block puzzle game. The game's objective is to slide numbered tiles on a grid to combine them to create a tile with the number 2048. 

<p align="center">
  <img src="github-media/gameplay.png" alt="Gameplay" width="600"/>
</p>

The game is won when a tile with the number 2048 appears on the board, hence the name of the game. The game is lost when the player has no legal moves left to make, resulting in the game board being full. 

In the current iteration of the game, 2048 is not defined as a win condition. The game is over, when the board is full and no more moves can be made. At that point, the score is calculated and the game is over.

## Demo GIF
<p align="center">
  <img src="github-media/demo.gif" alt="Demo" title="Demo" width="600"/>
</p>

## Running the Project

To run the project in demo mode, both the frontend and backend are needed. Currently, there is no way to specify the game server. The API of the server is hardcoded in the API utility class.

The project can be either run in development mode or 'production mode'. To run the project in development mode, the Java backend and the next.js frontend can be run with the usual steps for both environments (Maven / npm). For demonstration purposes, a docker-compose file is provided. Simply clone the repository and run the following command in the agis-spring-server directory of the project:

```bash
docker-compose up --build
```

To run the docker-compose file, docker and docker-compose need to be installed on the machine. The frontend will be available on 'localhost:3000'.
When initially running the command, it might take a while to build the images and start the containers.

<p align="center">
  <img src="github-media/dockercompose.png" alt="Docker-Compose" title="Docker-Compose"/>
</p>


## General Remarks

## Application Structure

The Game is a simple single-page application structured in the following way: 

### Top Controls
<p align="center">
  <img src="github-media/controls.png" alt="Top Controls" title="Top Controls" width="300"/>
</p>

Top controls are used to register a new user, login to the server, and start a new game. It is not possible to register a user, that is already registered. Although the server responds with an 200 OK, the user can't be registered twice, which is displayed in an alert. When logging in as an already logged in user, a new token is generated. When starting a new game, the game new gameboard is fetched from the server and the score is reset to 0.

### Game Board
<p align="center">
  <img src="github-media/gameboard.png" alt="Game Board" title="Game Board" width="400"/>
</p>

The game is played by pressing on the arrow keys on the keyboard. Because the state of the board is calculated on the server, no 'nice' animations are implemented in the frontend.
Illegal moves are not possible: When moving to a certain direction and it's not possible to do so, the server just responds with the same board as before.

### Bottom Control & Score
<p align="center">
  <img src="github-media/score.png" alt="Bottom Control & Score" title="Bottom Control & Score" width="300"/>
</p>

At the bottom, an additional button is used to logout again. When logging out, the username and password fields are reset, the token is deleted, the gameboard is reset and the score is set to 0.
Users are not persisted on the server after restarting the program. When simply logging out, a user will be able to log in again with the same credentials.

### Alerts
<p align="center">
  <img src="github-media/alert.png" alt="Alerts" title="Alerts" width="300"/>
</p>

Browser alerts are used to display information about failed or successful actions.

## Functionality




