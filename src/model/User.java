package model;

public class User {

    private String name;
    private String password;
    private int gamesPlayed;
    private int gamesWon;
    private int winRatio;
    private int bestTimeMinutes;
    private int bestTimeSeconds;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        gamesPlayed = 0;
        gamesWon = 0;
        winRatio = 0;
        bestTimeMinutes = 0;
        bestTimeSeconds = 0;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }
    
    public void increaseGamesPlayed() {
        gamesPlayed++;
    }
    
    public int getGamesWon() {
        return gamesWon;
    }
    
    public void increaseGamesWon() {
        gamesWon++;
    }
    
    public int getWinRatio() {
        return winRatio;
    }
    
    public void updateWinRatio() {
        winRatio = (int)Math.round(100*gamesWon/gamesPlayed);
    }
    
    public int getBestTimeMinutes() {
        return bestTimeMinutes;
    }
    
    public void updateBestTime(int bestTimeMinutes, int bestTimeSeconds) {
        if (((this.bestTimeMinutes > bestTimeMinutes) || ((this.bestTimeMinutes == bestTimeMinutes) && (this.bestTimeSeconds > bestTimeSeconds))) || ((this.bestTimeMinutes == 0) && (this.bestTimeSeconds == 0))) {
            this.bestTimeMinutes = bestTimeMinutes;
            this.bestTimeSeconds = bestTimeSeconds;
        }
    }

    public int getBestTimeSeconds() {
        return bestTimeSeconds;
    }

    public void setBestTimeSeconds(int bestTimeSeconds) {
        this.bestTimeSeconds = bestTimeSeconds;
    }

}
