package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import card.Card;
import player.Player;


public class Game {
	
	private AIManager aiManager;
	
	private int gameId;
	private int currentRoundId;
	private int currentTurnId;
	private int currentHerdId;	
	
	private List<Player> players;
	private HashMap<Integer, Herd> herds = new HashMap<>();
	private List<Round> rounds;
	private int deckSize;
	
	private String gameStatus;
	
	// --- Start Constructor
	public Game(List<Player> players, int id) {
		gameId = id;
		this.players = players;
		rounds = new ArrayList<>();
		aiManager = AIManager.getInstance();
		currentHerdId = 0;
	}
	// --- End Constructor
	
	public void addRound(Round r) {
		rounds.add(r);
	}
		
	// --- Start Initialize
	public void initializeRound(boolean b, int roundId) {
		currentRoundId = roundId;
		addRound(new Round(players, b, roundId, this));
		initializeHerd(currentHerdId);
	}
	
	public void initializeTurn(int numPlayerStart, int deckSize, int turnId) {
		currentTurnId = turnId;
		this.deckSize = deckSize;
		getCurrentRound().addTurn(turnId);
	}
	
	public void initializeHerd(int herdNb) {
		herds.put(herdNb, new Herd());
	}
	
	public void cleanHerd() {
		currentHerdId++;
	}
	// --- End Initialize
	
	private Round getRound(int currRoundId) {
		return rounds.get(currRoundId);
	}
	
	public Round getCurrentRound() {
		return rounds.get(currentRoundId - 1);
	}

	private int getCurrentRoundId() {
		return currentRoundId;
	}

	public List<Round> getRounds(){
		return rounds;
	}
	
	public int getId() {
		return gameId;
	}

	public int getAIPosition() {
		return aiManager.getAIPosition();
	}

	public void aiPlayerPlayed(Card c) {
		System.out.println(" ---------- " + aiManager);
		aiManager.playCard(c);
	}
	
	public Herd getCurrentHerd() {
		return herds.get(currentHerdId);
	}
	
	public int getHerdFlies() {
		return getCurrentHerd().getFlies();
	}

	public void takeHerd(String bool) {
		aiManager.takeHerd(bool);
		cleanHerd();
	}
	
	public int getDeckSize() {
		return deckSize;
	}

	public void playerIs(int currentPlayer) {
		// TODO in other version 
		// analyse other 
	}

	public void setStatus(String status) {
		gameStatus = status;
	}
	
	// --- Start Turn Action
	public void nextTurn(Card c) {
		getCurrentRound().endTurn();
	}
	
	public void turnPlayed(Card c) {
		getCurrentHerd().addCard(c);
		nextTurn(c);
	}

	public void turnPlayed(boolean pickHerd) {
		if (pickHerd) {
			cleanHerd();
		}
	}
	
	public void turnPlayed(boolean herdPickUp, Card cardPlay, boolean reverseDirection) {
		turnPlayed(herdPickUp);
		turnPlayed(cardPlay);
		getCurrentRound().changeRotation(reverseDirection);
	}
	
	public void turnPlayedTR(boolean herdPickUp, Card c) {
		turnPlayed(herdPickUp);
		turnPlayed(c);
	}

	public void turnPlayedRot(boolean rot, Card c) {
		turnPlayed(c);
		getCurrentRound().changeRotation(rot);
	}
	// --- End Turn Action

}