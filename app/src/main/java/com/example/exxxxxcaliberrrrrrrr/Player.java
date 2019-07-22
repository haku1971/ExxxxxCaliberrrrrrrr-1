package com.example.exxxxxcaliberrrrrrrr;

import java.io.Serializable;

public class Player implements Serializable {
    private String ID;
    private Long score;

    public Player(){

    }
    public Player(String ID, Long score) {
        this.ID = ID;
        this.score = score;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return ""+score;
    }
}
