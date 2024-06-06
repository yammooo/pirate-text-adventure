# Pirate Text Adventure

## High-Level Description

Welcome to the Pirate Text Adventure game! This 2D text-based adventure game revolves around a pirate who has landed on an archipelago in search of hidden treasure. The player navigates through multiple islands, interacts with various entities, and faces numerous challenges. The goal is to find the treasure by collecting keys, solving puzzles, and overcoming obstacles. The game features a graphical user interface developed using Java Swing, and it supports saving and loading game states using AWS S3.

## Features

1. **Interactive Gameplay**: Interact with various items and NPCs, looking for the keys that unlock the treasure chest.
2. **Multiple Sessions**: Start a new game or load a saved game to continue playing across multiple sessions.
3. **Automatic Saving**: The game state is automatically saved to AWS S3.
4. **Map Exploration**: Explore different locations on multiple islands, each with unique challenges and items.
5. **Obstacle Management**: Encounter and deal with various obstacles and enemies to progress further.
6. **Treasure Hunt**: Collect all necessary items and clues to discover the hidden treasure before losing all lives.

## Installation and Launch Instructions

### Prerequisites

- Ensure you have Java 21 installed. You can download it from the [official website](https://www.oracle.com/java/technologies/downloads/).
- Apache Maven is required for managing dependencies and building the project. You can download it from the [Maven website](https://maven.apache.org/download.cgi).

### Clone the Repository

```bash
git clone https://github.com/yammooo/pirate-text-adventure.git
cd pirate-text-adventure
```

### Build the Project

Use Maven to build the project and download necessary dependencies:

```bash
mvn clean install
```

### Run the Game

After building the project, run the main application:

```bash
mvn exec:java
```

## Main Functions Reused from Existing Libraries

- **Java Swing**: Used for creating the graphical user interface for the game.
- **JUnit and Mockito**: Used for unit testing to ensure the reliability of the game logic.
- **Google Gson**: Used for serializing and deserializing game states to JSON.
- **AWS SDK for Java**: Used for saving and loading game states to and from AWS S3.

## Documentation
* [API Javadoc](): Documentation for the current release
* [User Stories](docs/UserStories.md): This document includes the user stories and acceptance criteria
* [Design document](docs/DesignDocument.md): This document includes the Domain Model, System Sequence Diagrams, Design Class Model, and Internal Sequence Diagrams
* [System Test Document](docs/SystemTestReport.md): This document reports the validation of the system tests