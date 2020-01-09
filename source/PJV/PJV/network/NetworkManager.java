package network;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import card.Card;
import core.AIManager;
import core.Round;
import network.facade.client.FacadeClient;
import network.listenercore.IGameServerAction;
import player.Player;

public class NetworkManager implements IGameServerAction{

	// --- Start Singleton
	private static NetworkManager instance;

	private NetworkManager() {
		awake();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static NetworkManager getInstance() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}
	// --- End Singleton

	// --- Start Attributes
	private static Logger logger = Logger.getLogger(NetworkManager.class.toString());
	private static final String DELIMITER = ",";
	private final ExecutorService workers = Executors.newCachedThreadPool();
	private FacadeClient fc;

	// --- Start ID
	private AIManager aiManager;
	private String uidGame;
	private String uidRound;
	private String uidTurn;
	private String ip;
	private String name;
	private int port;
	private int nbPlayers;
	private String status;
	// --- End ID

	// --- End Attributes

	private void waitFew() {
		try {
			Thread.sleep(100);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void waitFew(int i) {
		try {
			Thread.sleep(i);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void awake() {
		NetworkManager nm;
		aiManager = AIManager.getInstance();
		nm = this;
		workers.submit( () ->{
			fc = new FacadeClient(nm);
		});
	}

	public void searchGame(String typePartie, int taillePartie) {
		waitFew();
		if (aiManager.isLog()) {
			logger.log(Level.INFO, "Recherche de partie");
		}
		fc.rechercherPartie(typePartie, taillePartie);
	}

	public void joinGame(String type, String idConnection, int port) {
		waitFew();
		fc.requeteJoinGame(aiManager.getAIPlayer().getName(), type, idConnection, port);
//		fc.requeteJoinLocalHost(name, type, idConnection, port);
		aiManager.setGameStatus("ATTENTE");
	}

	public void stopClient() {
		waitFew();
		fc.arretClient();
	}

	public void playCard(Card c) {
		waitFew(2000);
		System.out.println("gogogogogo");
		fc.jouerCarte(c.getNetworkCode(), uidGame, uidRound, uidTurn);
	}

	public void takeHerd(String bool) {
		waitFew(2000);
		System.out.println("take troupeau");
		fc.rentreTroupeau(bool, uidGame, uidRound, uidTurn);
	}

	@Override
	public void announceDeconnexion(int gameID, int roundID, int turnID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void serverAcceptPlayer(String connectionID) {

	}

	@Override
	public void serverRefusePLayer(String connectionID) {

	}

	// --- Start Initialize
	@Override
	public void serverInitializeGame(String playerList, int gameID) {
		waitFew();
		if (aiManager.isLog()) {
			logger.log(Level.INFO, "Chargement de la game " + gameID + " contanant " + playerList);
		}
		
		uidGame = Integer.toString(gameID);
		List<Player> players = new ArrayList<>();
		String[] playersStr = playerList.split(DELIMITER);
		for (int i = 0; i < playersStr.length; i++) {
			players.add(new Player(0, playersStr[i], null));
		}
		aiManager.initializeGame(players, gameID);
	}

	@Override
	public void serverInitializeRound(String gameDirection, int gameID, int roundID) {
		waitFew();
		uidRound = Integer.toString(roundID);
		if (gameDirection.equals("CROI")) {
			aiManager.initializeRound(true, roundID);
		}
		else if (gameDirection.equals("DECROI")){
			aiManager.initializeRound(false, roundID);
		}
	}

	@Override
	public void serverInitializeTurn(String numPlayer, int deckSize, int gameID, int roundID, int turnID) {
		waitFew();
		if (aiManager.isVerbose()) {
			logger.log(Level.INFO, "Chargement du tour " + turnID + " contanant " + deckSize);
		}
		uidTurn = Integer.toString(turnID);
		aiManager.initializeTurn(Integer.parseInt(numPlayer), deckSize, turnID);
	
	}
	// --- End Initialize

	@Override
	public void serverDistributeHand(String playerHand, int gameID, int roundID) {
		waitFew();
		System.err.println("TOUR");
		if (aiManager.isVerbose()) {
			logger.log(Level.INFO, "Main distribue " + playerHand);
		}
		
		List<Card> cards = new ArrayList<>();
		String[] cardsStr = playerHand.split(DELIMITER);
		for (int i = 0; i < cardsStr.length; i++) {
			Card c = Card.convertStringToCard(cardsStr[i]);
			if (c != null) {
				cards.add(c);
			}
		}
		aiManager.startGame(cards);
	}

	@Override
	public void serverDistributeCard(String playerCard, int gameID, int roundID, int turnID) {
		waitFew();
		Card c = Card.convertStringToCard(playerCard);
		if (c != null) {
			aiManager.distributeCard(c);
		}
	}

	@Override
	public void serverIndicateError(String errorCode, int gameID, int roundID, int turnID) {
		waitFew();
		aiManager.replay();
	}

	@Override
	public void serverEndRound(String roundPlayersScore, String actualPlayersScore, int gameID, int roundID) {
		waitFew();
		List<Card> roundScore = new ArrayList<>();
		String[] roundScoreStr = roundPlayersScore.split(DELIMITER);
		for (int i = 0; i < roundScoreStr.length; i++) {
			roundScore.add(Card.convertStringToCard(roundScoreStr[i]));
		}
		// --- 
		List<Card> totalScore = new ArrayList<>();
		String[] totalScoreStr = actualPlayersScore.split(DELIMITER);
		for (int i = 0; i < totalScoreStr.length; i++) {
			totalScore.add(Card.convertStringToCard(totalScoreStr[i]));
		}
		// ---
		aiManager.endRound(roundScore, totalScore);
	}

	@Override
	public void serverEndGame(String winner, int gameID) {
		// TODO Auto-generated method stub
		// Nothing because bot don't care of winner
	}

	@Override
	public void serverEndGameSession(int gameID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void serverRestartGame(int gameID, int newGameID) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void serverAnnounceGame(String ip, String string, String name, String string2, String string3,
			String string4, String string5, String string6, String status) {
		waitFew();
		if (aiManager.isVerbose()) {
			logger.log(Level.INFO, "Parti annonce par le serveur " + ip + ", " + name);
		}		
		port = Integer.parseInt(string);
		if (port > 1024 && port < 65553) {
			this.ip = ip;
			this.name = name;
			nbPlayers = Integer.parseInt(string2);
			this.status = status;
			joinGame("JV", "609", port);
		}
	}

	@Override
	public void serverAnnounceDeconnexion(int i, int j, int k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void serverInformPlayerActionTRandReverseGame(boolean herdPickUp, String cardPlay, boolean reverseDirection,
			int gameID, int roundID, int turnID) {
		waitFew();
		System.out.println("----------------------------------------------------");
		Card c = Card.convertStringToCard(cardPlay);
		if (c != null) {
			aiManager.turnActionTakeHerdReverse(herdPickUp, c, reverseDirection);	
		}
	}

	@Override
	public void serverInformPlayerActionOnlyCard(String cardPlay, int gameID, int roundID, int turnID) {
		waitFew();
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		Card c = Card.convertStringToCard(cardPlay);
		if (c != null) {
			aiManager.turnActionCard(c);
		}	
	}

	@Override
	public void serverInformPlayerActionOnlyTR(boolean herdPickUp, String string, int i4, int i5, int i6) {
		waitFew();
		System.out.println("//////////////////////////////////////////////////////");
		Card c = Card.convertStringToCard(string);
		if (c != null) {
			aiManager.turnActionTakeHerdCard(herdPickUp, c);
		}	
	}

	@Override
	public void serverInformPlayerActionOnlyReverse(String string, boolean rot, int i4, int i5, int i6) {
		waitFew();
		System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
		Card c = Card.convertStringToCard(string);
		if (c != null) {
			aiManager.turnActionReverseRotation(rot, c);
		}
	}

	@Override
	public void serverInformPlayerActionOnlyTRNotCard(boolean pickHerd, int gID, int rID, int tID) {
		waitFew();
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		aiManager.turnActionTakeHerdNoCard(pickHerd);
	}
}
