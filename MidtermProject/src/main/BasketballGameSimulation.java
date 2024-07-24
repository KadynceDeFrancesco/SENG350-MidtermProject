package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

interface Subject {
    void registerObserver(Observer o);
    void unregisterObserver(Observer o);
    void notifyObservers();
}

class Scoring implements Subject {
    private int teamAScore;
    private int teamBScore;
    private ArrayList<Observer> observerList;

    public Scoring() {
        observerList = new ArrayList<>();
        teamAScore = 0;
        teamBScore = 0;
    }

    public void updateScore() {
        // Simulate scoring for one quarter (you can replace this logic with actual game scores)
        Random random = new Random();
        teamAScore += random.nextInt(30) + 10;
        teamBScore += random.nextInt(30) + 10;
        notifyObservers();
    }

    public void printCurrentScore() {
        System.out.println("\nCurrent Score: Team A " + teamAScore + " - Team B " + teamBScore);
    }

    @Override
    public void registerObserver(Observer o) {
        observerList.add(o);
    }

    @Override
    public void unregisterObserver(Observer o) {
        observerList.remove(observerList.indexOf(o));
    }

    @Override
    public void notifyObservers() {
        for (Iterator<Observer> it = observerList.iterator(); it.hasNext();) {
            Observer o = it.next();
            o.update(teamAScore, teamBScore);
        }
    }
}

interface Observer {
    void update(int teamAScore, int teamBScore);
}

class PredictionObserver implements Observer {
    private int predictedTeamAScore;
    private int predictedTeamBScore;

    public PredictionObserver() {
        // You can implement your prediction logic here
        predictedTeamAScore = 100;
        predictedTeamBScore = 95;
    }

    @Override
    public void update(int teamAScore, int teamBScore) {
        // Update prediction logic based on current scores
        // This is a simple example; you can implement a more complex prediction algorithm
        predictedTeamAScore = teamAScore + 5;
        predictedTeamBScore = teamBScore - 5;
        display();
    }

    public void display() {
        System.out.println("\nPrediction: Team A " + predictedTeamAScore + " - Team B " + predictedTeamBScore);
    }
}

class ScoreTrackerObserver implements Observer {
    private int totalGames;
    private int teamAWins;
    private int teamBWins;

    @Override
    public void update(int teamAScore, int teamBScore) {
        // Update score tracking logic based on current game result
        totalGames++;
        if (teamAScore > teamBScore) {
            teamAWins++;
        } else if (teamAScore < teamBScore) {
            teamBWins++;
        }
        display();
    }

    public void display() {
        System.out.println("\nScore Tracker: Total Games - " + totalGames +
                ", Team A Wins - " + teamAWins + ", Team B Wins - " + teamBWins);
    }
}

class NewsGeneratorObserver implements Observer {
    private String lastGameResult;

    @Override
    public void update(int teamAScore, int teamBScore) {
        // Update news generation logic based on current game result
        lastGameResult = "Team A beat Team B by " + Math.abs(teamAScore - teamBScore) + " points";
        display();
    }

    public void display() {
        System.out.println("\nNews Generator: " + lastGameResult);
    }
}

public class BasketballGameSimulation {
    public static void main(String[] args) {
        Scoring scoring = new Scoring();
        PredictionObserver predictionObserver = new PredictionObserver();
        ScoreTrackerObserver scoreTrackerObserver = new ScoreTrackerObserver();
        NewsGeneratorObserver newsGeneratorObserver = new NewsGeneratorObserver();

        scoring.registerObserver(predictionObserver);
        scoring.registerObserver(scoreTrackerObserver);
        scoring.registerObserver(newsGeneratorObserver);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("A - Start a new game");
            System.out.println("B - Simulate 1 quarter");
            System.out.println("C - Print current score");
            System.out.println("D - Print current prediction");
            System.out.println("E - Print prediction stats");
            System.out.println("F - Print a table of scores of all finished games");
            System.out.println("G - Generate a news piece title");
            System.out.println("X - Exit");

            System.out.print("Enter your choice: ");
            char choice = scanner.next().charAt(0);

            switch (Character.toUpperCase(choice)) {
                case 'A':
                    scoring = new Scoring(); // Start a new game
                    scoring.registerObserver(predictionObserver);
                    scoring.registerObserver(scoreTrackerObserver);
                    scoring.registerObserver(newsGeneratorObserver);
                    break;
                case 'B':
                    scoring.updateScore(); // Simulate 1 quarter
                    break;
                case 'C':
                    scoring.printCurrentScore();
                    break;
                case 'D':
                    predictionObserver.display();
                    break;
                case 'E':
                    // Print prediction stats (you can implement additional stats)
                    System.out.println("Stats: ...");
                    break;
                case 'F':
                    // Print a table of scores of all finished games
                    System.out.println("Table of Scores: ...");
                    break;
                case 'G':
                    newsGeneratorObserver.display(); // Generate a news piece title
                    break;
                case 'X':
                    System.out.println("Exiting program.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
