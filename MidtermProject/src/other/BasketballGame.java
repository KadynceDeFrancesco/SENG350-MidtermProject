package other;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

// Subject interface for observers
interface Subject {
    void registerObserver(Observer o);

    void unregisterObserver(Observer o);

    void notifyObservers();
}

// Concrete Subject (Scoring class for basketball game)
class BasketballScoring implements Subject {
    private int teamAScore;
    private int teamBScore;
    private int teamAFouls;
    private int teamBFouls;
    private ArrayList<Observer> observerList;
    private int currentQuarter;
    private int[] cumulativeTeamAScores;
    private int[] cumulativeTeamBScores;

    public BasketballScoring() {
        observerList = new ArrayList<>();
        currentQuarter = 1;
        cumulativeTeamAScores = new int[5]; // 5 quarters (index 0 not used)
        cumulativeTeamBScores = new int[5]; // 5 quarters (index 0 not used)
    }

    public int getTeamAScore() {
        return teamAScore;
    }

    public int getTeamBScore() {
        return teamBScore;
    }

    public int getTeamAFouls() {
        return teamAFouls;
    }

    public int getTeamBFouls() {
        return teamBFouls;
    }

    public void setScores(int teamAScore, int teamBScore, int teamAFouls, int teamBFouls) {
        this.teamAScore = teamAScore;
        this.teamBScore = teamBScore;
        this.teamAFouls = teamAFouls;
        this.teamBFouls = teamBFouls;
        notifyObservers();
    }

    public int getCurrentQuarter() {
        return currentQuarter;
    }

    @Override
    public void registerObserver(Observer o) {
        observerList.add(o);
    }

    @Override
    public void unregisterObserver(Observer o) {
        observerList.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Iterator<Observer> it = observerList.iterator(); it.hasNext();) {
            Observer o = it.next();
            o.update(teamAScore, teamBScore, teamAFouls, teamBFouls, currentQuarter);
        }
    }

    public void simulateQuarter() {
        if (currentQuarter <= 4) {
           
            Random random = new Random();
            int teamAScore = (currentQuarter == 1) ? random.nextInt(31) : 
                              (currentQuarter == 2) ? random.nextInt(41) + 30 :
                              (currentQuarter == 3) ? random.nextInt(31) + 70 :
                              (currentQuarter == 4) ? random.nextInt(21) + 100 : 0;
            int teamBScore = (currentQuarter == 1) ? random.nextInt(31) :
                              (currentQuarter == 2) ? random.nextInt(31) + 40 :
                              (currentQuarter == 3) ? random.nextInt(31) + 70 :
                              (currentQuarter == 4) ? random.nextInt(21) + 100 : 0;
            int teamAFouls = random.nextInt(5); 
            int teamBFouls = random.nextInt(5); 

            setScores(teamAScore, teamBScore, teamAFouls, teamBFouls);
            cumulativeTeamAScores[currentQuarter] = teamAScore;
            cumulativeTeamBScores[currentQuarter] = teamBScore;

            System.out.println("End of Quarter " + currentQuarter);
            System.out.println("Team A Fouls: " + teamAFouls + ", Team B Fouls: " + teamBFouls);
            currentQuarter++;

            if (currentQuarter <= 4) {
                System.out.println("Press Enter to start the next quarter...");
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();
            }
        } else {
            System.out.println("The game has ended.");
        }
    }

    public int getTeamAScoreAtQuarter(int quarter) {
        if (quarter < 1 || quarter > currentQuarter) {
            throw new IllegalArgumentException("Invalid quarter number");
        }

        return cumulativeTeamAScores[quarter];
    }

    public int getTeamBScoreAtQuarter(int quarter) {
        if (quarter < 1 || quarter > currentQuarter) {
            throw new IllegalArgumentException("Invalid quarter number");
        }

        return cumulativeTeamBScores[quarter];
    }
}

interface Observer {
    void update(int teamAScore, int teamBScore, int teamAFouls, int teamBFouls, int currentQuarter);
}

// Concrete Observer A (PredictionObserver)
class PredictionObserver implements Observer {
    private int predictedEndOfQuarterScoreA;
    private int predictedEndOfQuarterScoreB;
    private String projectedWinner;
    private int predictedFoulsA;
    private int predictedFoulsB;

    @Override
    public void update(int teamAScore, int teamBScore, int teamAFouls, int teamBFouls, int currentQuarter) {
        // Example: Predict end-of-quarter score, projected winner, and predicted fouls
        if (currentQuarter == 3) {  // Predict at the end of the third quarter
            // Use a simple logic for prediction based on average scores
            predictedEndOfQuarterScoreA = teamAScore + (int) ((teamAScore - teamBScore) * 0.5);
            predictedEndOfQuarterScoreB = teamBScore + (int) ((teamBScore - teamAScore) * 0.5);
            predictedFoulsA = Math.min(5, teamAFouls + 1);  // Example logic for predicted fouls
            predictedFoulsB = Math.min(5, teamBFouls + 1);

            // Determine the projected winner
            projectedWinner = (predictedEndOfQuarterScoreA > predictedEndOfQuarterScoreB) ? "Team A" : "Team B";

            System.out.println("Prediction: End-of-Quarter Score Prediction - Team A: " + predictedEndOfQuarterScoreA +
                    ", Team B: " + predictedEndOfQuarterScoreB);
            System.out.println("Projected Winner: " + projectedWinner);
            System.out.println("Predicted Fouls - Team A: " + predictedFoulsA + ", Team B: " + predictedFoulsB);
        }
    }

    // Additional methods to get predictions
    public int getPredictedEndOfQuarterScoreA() {
        return predictedEndOfQuarterScoreA;
    }

    public int getPredictedEndOfQuarterScoreB() {
        return predictedEndOfQuarterScoreB;
    }

    public String getProjectedWinner() {
        return projectedWinner;
    }

    public int getPredictedFoulsA() {
        return predictedFoulsA;
    }

    public int getPredictedFoulsB() {
        return predictedFoulsB;
    }
}

// Concrete Observer B (StatsObserver)
class StatsObserver implements Observer {
    private int correctPredictions;
    private int totalGames;

    @Override
    public void update(int teamAScore, int teamBScore, int teamAFouls, int teamBFouls, int currentQuarter) {
        if (currentQuarter == 4) {
            totalGames++;
            // Example: Keep track of correct predictions
            if (teamAScore > teamBScore) {
                correctPredictions++;
            }

            System.out.println("Stats: Total Games - " + totalGames + ", Correct Predictions - " + correctPredictions);
        }
    }

    // Additional method to get prediction stats
    public String getPredictionStats() {
        return "Total Games - " + totalGames + ", Correct Predictions - " + correctPredictions;
    }
}

// Concrete Observer C (NewsObserver)
class NewsObserver implements Observer {
    private String lastGameResult;

    @Override
    public void update(int teamAScore, int teamBScore, int teamAFouls, int teamBFouls, int currentQuarter) {
        if (currentQuarter == 4) {
            // Example: Generate news piece title based on the result
            String result = (teamAScore > teamBScore) ? "Team A" : "Team B";
            int pointDifference = Math.abs(teamAScore - teamBScore);
            String gameStatus = (pointDifference >= 20) ? "landslide" : "tightly contested";

            lastGameResult = result + " beat the other team by " + pointDifference +
                    " points in a " + gameStatus + " game";
            System.out.println("News: " + lastGameResult);
        }
    }

    // Additional method to get the last game result
    public String getLastGameResult() {
        return lastGameResult;
    }
}

// Main class
public class BasketballGame {
    public static void main(String[] args) {
        BasketballScoring basketballScoring = new BasketballScoring();
        PredictionObserver predictionObserver = new PredictionObserver();
        StatsObserver statsObserver = new StatsObserver();
        NewsObserver newsObserver = new NewsObserver();

        // Register observers during initialization
        basketballScoring.registerObserver(predictionObserver);
        basketballScoring.registerObserver(statsObserver);
        basketballScoring.registerObserver(newsObserver);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Start a new game");
            System.out.println("2. Simulate 1 quarter");
            System.out.println("3. Print current score");
            System.out.println("4. Print current prediction");
            System.out.println("5. Print prediction stats");
            System.out.println("6. Print table of scores");
            System.out.println("7. Generate news piece title");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    basketballScoring = new BasketballScoring();
                    basketballScoring.registerObserver(predictionObserver);
                    basketballScoring.registerObserver(statsObserver);
                    basketballScoring.registerObserver(newsObserver);
                    break;
                case 2:
                    if (basketballScoring != null) {
                        basketballScoring.simulateQuarter();
                    } else {
                        System.out.println("Start a new game first.");
                    }
                    break;
                case 3:
                    if (basketballScoring != null) {
                        System.out.println("Current Score - Team A: " + basketballScoring.getTeamAScore() +
                                ", Team B: " + basketballScoring.getTeamBScore() +
                                ", Team A Fouls: " + basketballScoring.getTeamAFouls() +
                                ", Team B Fouls: " + basketballScoring.getTeamBFouls() +
                                ", Quarter: " + basketballScoring.getCurrentQuarter());
                    } else {
                        System.out.println("No game started yet.");
                    }
                    break;
                case 4:
                    if (basketballScoring != null) {
                        basketballScoring.simulateQuarter();
                        int predictedEndOfQuarterScoreA = predictionObserver.getPredictedEndOfQuarterScoreA();
                        int predictedEndOfQuarterScoreB = predictionObserver.getPredictedEndOfQuarterScoreB();
                        String projectedWinner = predictionObserver.getProjectedWinner();
                        int predictedFoulsA = predictionObserver.getPredictedFoulsA();
                        int predictedFoulsB = predictionObserver.getPredictedFoulsB();

                        System.out.println("Current Prediction - End-of-Quarter Score Prediction: Team A: " + predictedEndOfQuarterScoreA +
                                ", Team B: " + predictedEndOfQuarterScoreB +
                                ", Projected Winner: " + projectedWinner +
                                ", Predicted Fouls - Team A: " + predictedFoulsA + ", Team B: " + predictedFoulsB);
                    } else {
                        System.out.println("No game started yet.");
                    }
                    break;
                case 5:
                    if (basketballScoring != null) {
                        basketballScoring.simulateQuarter();
                        String predictionStats = statsObserver.getPredictionStats();
                        System.out.println("Prediction Stats: " + predictionStats);
                    } else {
                        System.out.println("No game started yet.");
                    }
                    break;
                case 6:
                    if (basketballScoring != null) {
                        ArrayList<String> tableOfScores = new ArrayList<>();
                        for (int i = 1; i <= 4; i++) {
                            tableOfScores.add("End of Quarter " + i + " - TeamA: " + basketballScoring.getTeamAScoreAtQuarter(i) +
                                    ", TeamB: " + basketballScoring.getTeamBScoreAtQuarter(i));
                        }

                        System.out.println("Table of Scores:");
                        for (String score : tableOfScores) {
                            System.out.println(score);
                        }
                    } else {
                        System.out.println("No game started yet.");
                    }
                    break;
                case 7:
                    if (basketballScoring != null) {
                        basketballScoring.simulateQuarter();
                        String lastGameResult = newsObserver.getLastGameResult();
                        System.out.println("Last Game Result: " + lastGameResult);
                    } else {
                        System.out.println("No game started yet.");
                    }
                    break;
                case 8:
                    System.out.println("Exiting the program.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }
        }
    }
}



