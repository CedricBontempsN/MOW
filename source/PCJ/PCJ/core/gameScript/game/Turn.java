package core.gameScript.game;

import core.gameScript.cardGame.Card;

public class Turn {

    // --- Start Constructor
    public Turn() {
        turnID = 1;
        herdPickUp = false;
        cardPlay = null;
        directionChange = false;
    }
    // --- End Constructor


    // --- Start Attributes
    private int turnID;
    private boolean herdPickUp;
    private Card cardPlay;
    private boolean directionChange;
    // --- End Attributes


    // --- Start Methods
    // --- Start Getter
    public int getTurnID() {
        return turnID;
    }

    public boolean getHerdPickUp() {
        return herdPickUp;
    }

    public Card getCardPlay() {
        return cardPlay;
    }

    public boolean getDirectionChange() {
        return directionChange;
    }
    // --- End Getter

    // --- Start Turn Control Methods
    public void nextTurn() {
        turnID++;
        herdPickUp = false;
        cardPlay = null;
        directionChange = false;
    }

    public void pickUpHerd() {
        herdPickUp = true;
    }

    public void playCard(Card cardPlay) {
        this.cardPlay = cardPlay;
    }

    public void changeDirection() {
        directionChange = true;
    }
    // --- End Turn Control Methods
    // --- End Methods
}
