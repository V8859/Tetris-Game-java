import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.*;

public class HighScoreManager {
    private static final String FILE_PATH = "C:\\Users\\sbasi\\IdeaProjects\\Tetris\\src/highscores.json";
    private List<HighScore> highScores;

    public HighScoreManager() {
        highScores = loadHighScores();
        if (highScores == null) {
            highScores = new ArrayList<>(); // Ensure it is not null
        }
//        System.out.println("Loaded High Scores: " + highScores);
    }

    // Load high scores from JSON file
    private List<HighScore> loadHighScores() {
        File file = new File(FILE_PATH);

        // If the file doesn't exist, return an empty list instead of leaving it null
        if (!file.exists()) {
            System.out.println("highscores.json not found. Initializing with empty list.");
            return new ArrayList<>();  // Return an empty list for now
        }

        // Load high scores from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            List<HighScore> loadedScores = new Gson().fromJson(reader, new TypeToken<List<HighScore>>(){}.getType());

            // If the list is null, return an empty list
            if (loadedScores == null) {
                System.out.println("File was empty or corrupted, returning empty list.");
                return new ArrayList<>();
            }

            return loadedScores;
        } catch (IOException e) {
            System.out.println("Error loading high scores: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void addScore(String name, int score, GameConfig config) {
        // Create a new HighScore object
        HighScore newScore = new HighScore(name, score, config);
        if (score <= 0) {
            System.out.println("Invalid score: " + score + ". Score must be positive.");
            return;
        }

        // Add the new high score
        highScores.add(new HighScore(name, score, config));

        // Sort high scores in descending order
        highScores.sort((a, b) -> Integer.compare(b.score, a.score));

        // Keep only the top 10 scores
        if (highScores.size() > 10) {
            highScores = new ArrayList<>(highScores.subList(0, 10));
        }

        // Save the updated high scores to JSON
        saveHighScores();
    }

    // Save high scores to JSON
    public void saveHighScores() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            new Gson().toJson(highScores, writer);
        } catch (IOException e) {
            System.out.println("Error saving high scores: " + e.getMessage());
        }
    }


    // Return the list of high scores
    public List<HighScore> getHighScores() {
        return highScores;
    }
}