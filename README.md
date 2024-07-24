# Basketball Game Simulation

## Overview

This project simulates a basketball game using the Observer design pattern. It includes functionality to track scores, fouls, and other game-related statistics, with multiple observers providing real-time updates and predictions.

## Features

- **Scoring Class**: Manages and updates scores and fouls for two basketball teams.
- **PredictionObserver**: Predicts the final score and fouls based on current game data.
- **ScoreTrackerObserver**: Tracks the number of wins for each team and provides game statistics.
- **NewsGeneratorObserver**: Generates a headline summarizing the game result.

## Menu Options

- **Start a New Game**: Initializes a new game with random scores and fouls.
- **Simulate 1 Quarter**: Updates scores for one quarter and notifies observers.
- **Print Current Score**: Displays the current scores and fouls.
- **Print Current Prediction**: Shows predictions based on current game data.
- **Print Prediction Stats**: Provides statistics related to prediction accuracy.
- **Print Table of Scores**: Lists scores for each quarter of the game.
- **Generate News Piece Title**: Creates a headline based on the final game result.
- **Exit**: Closes the program.

## Setup

1. **Clone the Repository**:
   ```sh
   git clone https://github.com/your-username/basketball-game-simulation.git
   
2. **Navigate to the Project Directory**:
   ```sh
   cd basketball-game-simulation

3. **Compile and Run**:
   ```sh
   javac main/*.java other/*.java
   java main.BasketballGameSimulation
4. **Interact with the Menu**:
     Follow the on-screen instructions to use the simulation.

    ## **Usage**
        Start a New Game: Press 'A'
        Simulate 1 Quarter: Press 'B'
        Print Current Score: Press 'C'
        Print Current Prediction: Press 'D'
        Print Prediction Stats: Press 'E'
        Print Table of Scores: Press 'F'
        Generate News Piece Title: Press 'G'
        Exit: Press 'X'

  
