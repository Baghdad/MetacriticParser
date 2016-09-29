package com.kbogdanov.metaparser;

import java.util.Date;

/**
 * Author: Kirill Bogdanov
 * Created on 28.09.2016.
 */
public class Game {
    private String title;
    private String platform;
    private Date release;
    private int criticScore;
    private int criticNumber;
    private float userScore;
    private int userNumber;
    private String summary;
    private String rating;
    private String site;
    private String developer;
    private String genre;
    private String playerNumber;
    private String ESRBDescription;

    @Override
    public String toString() {
        return "Game{" +
                "title='" + title + '\'' +
                ", platform='" + platform + '\'' +
                ", release=" + release +
                ", criticScore=" + criticScore +
                ", criticNumber=" + criticNumber +
                ", userScore=" + userScore +
                ", userCount=" + userNumber +
                ", summary='" + summary + '\'' +
                ", rating='" + rating + '\'' +
                ", site='" + site + '\'' +
                ", developer='" + developer + '\'' +
                ", genre='" + genre + '\'' +
                ", playerNumber='" + playerNumber + '\'' +
                ", ESRBDescription='" + ESRBDescription + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Date getRelease() {
        return release;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    public int getCriticScore() {
        return criticScore;
    }

    public void setCriticScore(int criticScore) {
        this.criticScore = criticScore;
    }

    public int getCriticNumber() {
        return criticNumber;
    }

    public void setCriticNumber(int criticNumber) {
        this.criticNumber = criticNumber;
    }

    public float getUserScore() {
        return userScore;
    }

    public void setUserScore(float userScore) {
        this.userScore = userScore;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(String playerNumber) {
        this.playerNumber = playerNumber;
    }

    public String getESRBDescription() {
        return ESRBDescription;
    }

    public void setESRBDescription(String ESRBDescription) {
        this.ESRBDescription = ESRBDescription;
    }
}
