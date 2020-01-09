package core.gameScript;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.gameScript.Exception.HandFullException;
import core.gameScript.Exception.WrongNetworkCodeException;
import core.interfaceScript.javafx.UIManager;
import javafx.application.Platform;

public class EventManager {

	// --- Start Singleton
	private static EventManager instance;

	private EventManager() {
		awake();
	}

	public static EventManager getInstance() {
		if (instance == null) {
			instance = new EventManager();
		}
		return instance;
	}
	// --- End Singleton



	// --- Start Attributes
	// --- Start Managers
	private DealManager dealManager;
	private GameManager gameManager;
	private NetworkManager networkManager;
	private UIManager uiManager;
	// --- End Managers

	// --- Start Thread
	private Thread endGameThread;
	// --- End Thread
	// --- End Attributes

	// --- Start Methods
	// --- Start Getter & Setter
	// --- Start Getter
	// --- End Getter
	// --- Start Setter
	// --- End Setter
	// --- End Getter & Setter

	// --- Start Construct Method
	private void awake() {
		dealManager = DealManager.getInstance();
		gameManager = GameManager.getInstance();
		uiManager = UIManager.getInstance();
		networkManager = NetworkManager.getInstance();
	}
	// --- End Construct Method

	// --- Start Announce Game Method
	public void announceGame() {
		System.out.println("ANNOUCE GAME");
		int port;
		String name;
		int maxPlayer;
		int maxRealPlayer;
		int maxVirtualPlayer;
		int realPlayerNb;
		int virtualPlayerNb;
		String status;

		port = gameManager.getPort();
		name = gameManager.getName();
		maxPlayer = gameManager.getNbPlayer();
		maxRealPlayer = gameManager.getMaxRealPlayer();
		maxVirtualPlayer = gameManager.getMaxVirtualPlayer();
		realPlayerNb = gameManager.getRealPlayer();
		virtualPlayerNb = gameManager.getVirtualPlayer();
		status = gameManager.getStatus();
		System.out.println("maxPlayer : " + maxPlayer);
		System.out.println("maxRealPlayer : " + maxRealPlayer);
		System.out.println("maxVirtualPlayer : " + maxVirtualPlayer);
		networkManager.announceGame(port, name, maxPlayer, maxRealPlayer, maxVirtualPlayer, realPlayerNb, virtualPlayerNb, status);
	}
	// --- End Announce Game Method

	// --- Start Game Creation Method
	public void createGame() {
		gameManager.createGame();
	}
	// --- End Game Creation Method

	// --- Start Game Modification Methods
	public int incrementMaxRealPlayer() {
		System.out.println("incrementMaxRealPlayer");
		return gameManager.incrementMaxRealPlayer();
	}

	public int decrementMaxRealPlayer() {
		System.out.println("decrementMaxRealPlayer");
		return gameManager.decrementMaxRealPlayer();
	}

	public int incrementMaxVirtualPlayer() {
		System.out.println("incrementMaxVirtualPlayer");
		return gameManager.incrementMaxVirtualPlayer();
	}

	public int decrementMaxVirtualPlayer() {
		System.out.println("decrementMaxVirtualPlayer");
		return gameManager.decrementMaxVirtualPlayer();
	}
	// --- End Game Modification Methods

	// --- Start Start Game Methods
	public void launchGame() {
		int gamePort;

		gamePort = gameManager.getPort();
		networkManager.startNetworkNewGame(gamePort);
		announceGame();
	}
	// --- End Start Game Method

	// --- Start Player Method
	public void playerSearchGame(int playerNb, String type) {
		int gamePlayerNb;
		int gameMaxRealPlayer;
		int gameMaxVirtualPlayer;

		gamePlayerNb = gameManager.getNbPlayer();
		gameMaxRealPlayer = gameManager.getMaxRealPlayer();
		gameMaxVirtualPlayer = gameManager.getMaxVirtualPlayer();

		System.out.println(gamePlayerNb +""+gameMaxRealPlayer+""+ gameMaxVirtualPlayer);

		System.out.println("gamePlayerNb : " + gamePlayerNb);
		System.out.println("gameMaxRealPlayer : " + gameMaxRealPlayer);
		System.out.println("gameMaxVirtualPlayer : " + gameMaxVirtualPlayer);

		if (gamePlayerNb <= playerNb) {
			switch (type) {
			case "JRU":
				if ((gameMaxRealPlayer > 0) && (gameMaxVirtualPlayer == 0)) {
					announceGame();
				}
				break;
			case "JVU":
				if ((gameMaxVirtualPlayer > 0) && (gameMaxRealPlayer == 0)) {
					announceGame();
				}
				break;
			case "MIXTE":
				if ((gameMaxRealPlayer != 0) && (gameMaxVirtualPlayer != 0)) {
					announceGame();
				}
				break;
			case "MIXSR":
				if ((gameMaxRealPlayer != 0) && (gameMaxVirtualPlayer != 0) && (gameMaxRealPlayer > gameMaxVirtualPlayer)) {
					announceGame();
				}
				break;
			case "MIXSV":
				if ((gameMaxRealPlayer != 0) && (gameMaxVirtualPlayer != 0) && (gameMaxVirtualPlayer > gameMaxRealPlayer)) {
					announceGame();
				}
				break;
			}
		}
	}

	public void addNewPlayer(String playerName, String playerType, String uidConnection, int playerPort) {
		System.out.println("==========Start ajout player===========");
		int playerPosition;

		if (!canAcceptPlayer(playerType)) {
			System.out.println("Can't accepte player type");
			networkManager.refusePlayer(uidConnection, playerPort);
		}
		else {
			System.out.println("Je passe dans cette partie la");
			if (!uiManager.acceptPlayer(playerName)) {
				System.out.println("Victime");
				networkManager.refusePlayer(uidConnection, playerPort);
			}
			else {
				System.out.println("ALED");
				playerPosition = gameManager.addPlayer(playerPort, playerName, playerType);
				Platform.runLater( () -> {
                    uiManager.addPlayerInLobby(playerName, playerPosition);
                });
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				networkManager.acceptPlayer(uidConnection, playerPort);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				announceGame();
			}
		}
	}

	private boolean canAcceptPlayer(String playerType) {
		if (playerType.equals("JR")) {
			return gameManager.canAcceptRealPlayer();
		}
		else if (playerType.equals("JV")) {
			return gameManager.canAcceptVirtualPlayer();
		}
		else {
			return false;
		}
	}
	// --- End Player Methods

	// --- Start Initialisation Methods
	public void startGame() {
		gameManager.startGame();
		initializeGame();
		wait(50);
		gameManager.newRound();
		initializeRound();
		wait(50);
		distributeHand();
		wait(50);
		initializeTurn();
	}

	private void initializeGame() {
		List<String> playersName;
		int gameID;

		playersName = gameManager.getPlayersNames();
		gameID = gameManager.getGameID();
		networkManager.initializeGame(playersName, gameID);
	}

	private void initializeRound() {
		boolean gameDirection;
		int gameID;
		int roundID;

		gameDirection = gameManager.getGameDirection();
		gameID = gameManager.getGameID();
		roundID = gameManager.getRoundID();
		networkManager.initializeRound(gameDirection, gameID, roundID);
	}

	private void initializeTurn() {
		int firstPlayer;
		int deckSize;
		int gameID;
		int roundID;
		int turnID;

		firstPlayer = gameManager.getFirstPlayerRound();
		deckSize = dealManager.nbCardInDeck();
		gameID = gameManager.getGameID();
		roundID = gameManager.getRoundID();
		turnID = gameManager.getTurnID();
		networkManager.initializeTurn(firstPlayer, deckSize, gameID, roundID, turnID);
	}
	// --- End Initialisation Methods

	// --- Start Finalize Methods
	private void finalizeGame() {
		String winner;
		int gameID;

		winner = gameManager.getWinner();
		gameID = gameManager.getGameID();
		networkManager.finalizeGame(winner, gameID);
		endGameThread = new Thread( () -> {
			try {
				gameManager.endGame();
				Thread.sleep(60000);
			} catch (InterruptedException interrupt) {
				System.out.println("Thread interrupt");
				Thread.currentThread().interrupt();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!Thread.currentThread().isInterrupted()) {
				gameManager.clearGame();
				networkManager.downNetworkGame();
			}
		});
		endGameThread.start();
		displayPlayerRanking();
	}

	private void finalizeRound() {
		gameManager.endRound();
		informRoundEnd();
		if (gameManager.gameContinue()) {
			gameManager.nextRound();
			dealManager.reset();
			initializeRound();
		}
		else {
			finalizeGame();
		}
	}
	// --- End Finalize Methods

	// --- Start Inform Action Methods
	private void informRoundEnd() {
		List<String> playersRoundScore;
		List<String> playersGameScore;
		int gameID;
		int roundID;

		playersRoundScore = gameManager.getPlayersRoundScore();
		playersGameScore = gameManager.getPlayersGameScore();
		gameID = gameManager.getGameID();
		roundID = gameManager.getRoundID();
		networkManager.finalizeRound(playersRoundScore, playersGameScore, gameID, roundID);
	}

	private void informPlayerAction() {
		boolean herdPickUp;
		String cardPlay;
		boolean reverseDirection;
		int gameID;
		int roundID;
		int turnID;

		herdPickUp = gameManager.turnHerdPickUp();
		cardPlay = gameManager.turnCardPlay();
		reverseDirection = gameManager.turnDirectionChange();
		gameID = gameManager.getGameID();
		roundID = gameManager.getRoundID();
		turnID = gameManager.getTurnID();
		networkManager.informPlayerAction(herdPickUp, cardPlay, reverseDirection, gameID, roundID, turnID);
	}

	private void indicateError(String errorCode) {
		int gameID;
		int roundID;
		int turnID;
		int playerPort;

		gameID = gameManager.getGameID();
		roundID = gameManager.getRoundID();
		turnID = gameManager.getTurnID();
		playerPort = gameManager.getPlayerPort();
		networkManager.indicateError(errorCode, gameID, roundID, turnID, playerPort);
	}

	private void distributeCard(String playerCard) {
		int gameID;
		int roundID;
		int turnID;
		int playerPort;

		gameID = gameManager.getGameID();
		roundID = gameManager.getRoundID();
		turnID = gameManager.getTurnID();
		playerPort = gameManager.getPlayerPort();
		networkManager.distributeCard(playerCard, gameID, roundID, turnID, playerPort);
	}
	// --- End Inform Action Methods

	// --- Start Hand Control Methods
	private void distributeHand() {
		int i;
		int nbPlayer;
		int gameID;
		int roundID;
		int playerPort;
		List<String> playerHand;

		nbPlayer = gameManager.getNbPlayer();
		for (i = 0 ; i < nbPlayer ; i++) {
			playerHand = dealManager.giveAHand();
			try {
				gameManager.givePlayerHand(i, playerHand);
			} catch (WrongNetworkCodeException e) {
				e.printStackTrace();
			}
			Platform.runLater( () -> {
                uiManager.manageDeck(dealManager.nbCardInDeck());
            });
			playerPort = gameManager.getPlayerPort(i);
			gameID = gameManager.getGameID();
			roundID = gameManager.getRoundID();
			networkManager.distributeHand(playerPort, playerHand, gameID, roundID);
		}
	}

	public void playerPlayCard(String card, int gameID, int roundID, int turnID) {
		System.out.println(" ------------------------------------------- ");
		int cardPosition;
		int currentPlayer;
		String cardColor;
		String cardValue;
		int deckSize;
		if (checkID(gameID, roundID, turnID)) {
			try {
				if (gameManager.isInPlayerHand(card)) {
					cardPosition = GameManager.getInstance().playCard(card);
					if (cardPosition >= 0) {
						currentPlayer = gameManager.getCurrentPlayer();
						cardColor = dealManager.getColor(card);
						cardValue = dealManager.getValue(card);
						deckSize = dealManager.nbCardInDeck();
						Platform.runLater(
								() -> {
									UIManager.getInstance().addCardToFlock(cardColor, cardValue, currentPlayer, cardPosition);
									UIManager.getInstance().manageDeck(deckSize);
								}
						);
						if (!dealManager.isSpecial(card)) {
							try {
								givePlayerCard();
							} catch (HandFullException e) {
								e.printStackTrace();
							}

							informPlayerAction();
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							gameManager.nextTurn();
							initializeTurn();
						}


					}
					else {
						indicateError("JC");
					}
				}
				else {
					indicateError("JC");
				}
			} catch (WrongNetworkCodeException e) {
				indicateError("JC");
			}
		}
		else {
			indicateError("JC");
		}
	}

	private void givePlayerCard() throws HandFullException {
		String pickCard;

		pickCard = dealManager.giveCard();
		try {
			gameManager.addCard(pickCard);
		} catch (WrongNetworkCodeException e) {
			e.printStackTrace();
		}
        Platform.runLater( () -> {
            uiManager.manageDeck(dealManager.nbCardInDeck());
        });

		distributeCard(pickCard);
	}
	// --- End Hand Control Methods

	// --- Start Herd Control Method
	public void pickUpHerd(int gameID, int roundID, int turnID) {
		if (checkID(gameID, roundID, turnID)) {
			if (dealManager.isEmpty()) {
				if (gameManager.herdIsEmpty()) {
					indicateError("RT");
				}
				else {
                    Platform.runLater( () -> {
                        uiManager.clearCurrentflock();
                    });
					gameManager.pickUpHerd();
					informPlayerAction();
					finalizeRound();
				}
			} else {
				if (gameManager.herdIsEmpty()) {
					indicateError("RT");
				}
				else {
                    Platform.runLater( () -> {
                        uiManager.clearCurrentflock();
                    });
					gameManager.pickUpHerd();
				}
			}
		}
		else {
			indicateError("RT");
		}
	}
	// --- End Herd Control Method


	// --- Start Change Direction
//	public void changeDirection(boolean changeDirection, int gameID, int roundID, int turnID) throws WrongNetworkCodeException {
//		String cardPlay;
//
//		if (checkID(gameID, roundID, turnID)) {
//			cardPlay = gameManager.turnCardPlay();
//			if (dealManager.isSpecial(cardPlay)) {
//				if (changeDirection) {
//					gameManager.changeDirection();
//				}
//				try {
//					givePlayerCard();
//				}
//				catch (Exception e) {
//					e.printStackTrace();
//				}
//				informPlayerAction();
//			}
//			else {
//				indicateError("ISJ");
//			}
//		}
//		else {
//			indicateError("ISJ");
//		}
//	}
//	// --- End Change Direction

    // --- Start Change Direction
    public void changeDirection(boolean changeDirection, int gameID, int roundID, int turnID) {
	    String cardPlay;

	    if (checkID(gameID, roundID, turnID)) {
            cardPlay = gameManager.turnCardPlay();
            try {
                if (dealManager.isSpecial(cardPlay)) {
                    if (changeDirection) {
                        gameManager.changeDirection();
                    }
                    try {
                        givePlayerCard();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    informPlayerAction();
                    gameManager.nextTurn();
                    initializeTurn();
                }
                else {
                    indicateError("ISJ");
                }
            } catch (WrongNetworkCodeException e) {
                e.printStackTrace();
            }
        }
	    else {
	        indicateError("ISJ");
        }
    }
    // --- End Change Direction

	// --- Start Restart Game
	public void restartGame() {
		endGameThread.interrupt();
		gameManager.restartGame();
		initializeGame();
		gameManager.newRound();
		initializeRound();
		distributeHand();
		initializeTurn();
	}
	// --- End Restart Game

	// --- Start End Game Screen
	private void displayPlayerRanking() {
		List<String> playerNameRanking;
		List<Integer> playerPointRanking;

		playerNameRanking = gameManager.getPlayerNameRanking();
		playerPointRanking = gameManager.getPlayerPointRanking();
		Platform.runLater( () -> {
            uiManager.loadScore(playerNameRanking, playerPointRanking);
        });
	}
	// --- End End Game Screen

	// --- Start Wait
	private void wait(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	// --- End Wait

	// --- Start ID Verification Method
	private boolean checkID(int gameID, int roundID, int turnID) {
		return (gameManager.getGameID() == gameID) && (gameManager.getRoundID() == roundID) && (gameManager.getTurnID() == turnID);
	}
	// --- End ID Verification Method

	// --- Start Game Information Methods
	public int getPort() {
		return gameManager.getPort();
	}

	public String getName() {
		return gameManager.getName();
	}

	public int getNbPlayer() {
		return gameManager.getNbPlayer();
	}

	public int getMaxRealPlayer() {
		return gameManager.getMaxRealPlayer();
	}

	public int getMaxVirtualPlayer() {
		return gameManager.getMaxVirtualPlayer();
	}

	public int getRealPlayer() {
		return gameManager.getRealPlayer();
	}

	public int getVirtualPlayer() {
		return gameManager.getVirtualPlayer();
	}

	public String getStatus() {
		return gameManager.getStatus();
	}

	public List<String> getPlayers() {
		return gameManager.getPlayersNames();
	}
	// --- End Game Information Methods

	// --- Start Exit PCJ Method
	public void shutDownPCJ() {
		System.exit(0);
	}
	// --- End Exit PCJ Method
	// --- End Methods

}
