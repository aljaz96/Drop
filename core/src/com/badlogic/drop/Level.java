package com.badlogic.drop;

/**
 * Created by mikik on 4. 06. 2017.
 */

public class Level {
    int lvl;
    int scoreAchieved;
    int maxScore;
    float scorePercentage;

    public Level(int lvl, int scoreAchieved, int maxScore ) {
        this.lvl = lvl;
        this.scoreAchieved = scoreAchieved;
        this.maxScore = maxScore;
        this.scorePercentage = (scoreAchieved / maxScore) * 100;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getScoreAchieved() {
        return scoreAchieved;
    }

    public void setScoreAchieved(int scoreAchieved) {
        this.scoreAchieved = scoreAchieved;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public float getScorePercentage() {
        return (this.scoreAchieved / this.maxScore) * 100;
    }

    public void setScorePercentage() {
        scorePercentage = (this.scoreAchieved / this.maxScore) * 100;
    }
}
