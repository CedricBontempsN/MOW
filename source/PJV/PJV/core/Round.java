package core;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import card.Card;
import player.AIPlayer;
import player.Player;
import strategy.CommonStrategy;

public class Round {
		// --- Start Constructor	
		public Round(List<Player> players, boolean sens, int startPlayerNb, Game game) {
			this.players = players;
			currentPlayer = startPlayerNb - 1;
			direction = sens;
			this.game = game;
		}
		// --- End Constructor

		// --- Start Attributes
		private static Logger logger = Logger.getLogger(Round.class.toString());
		
		private Game game;
		private int currentHerd;
		// --- Start Player Management Attributes
		private List<Player> players;
		private int currentPlayer;
		private boolean direction;
		// --- End Player Management Attributes
		// --- End Attributes
		
		// --- Start Methods
		public void addTurn(int turnId) {
			currentHerd = turnId;
			System.err.println("CHECK TOUR " + game.getAIPosition() + " -- " + currentPlayer);
			if (game.getAIPosition() == currentPlayer) {
				CommonStrategy strat = new CommonStrategy(AIManager.getInstance().getAIPlayer());
//				Card c = Card.convertStringToCard("V.7");
//				c = AIManager.getInstance().getAIPlayer().getHand().getCards().get(0);
				Card c = strat.playTheCard(game.getCurrentHerd(), game);
//				AICard c = AIManager.getInstance().getAIPlayer().getHand().getCards().get(0);
				System.out.println(c + "\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
				if (c != null) {
					if (AIManager.getInstance().isLog()) {
						logger.log(Level.INFO, "Carte proposé : " + c.toString());
					}
					game.aiPlayerPlayed(c);
					AIManager.getInstance().getAIPlayer().getHand().getCards().remove(0);
//					game.takeHerd("false");
				}
				else {
					if (AIManager.getInstance().isLog()) {
						logger.log(Level.INFO, "Demande de ramassé le troupeau");
					}
					game.takeHerd("true");
				}
			}
			else {
				game.playerIs(currentPlayer);
			}
		}
		
		public void endTurn() {
			nextPlayer();
		}
		
		public void changeRotation() {
			direction = !direction;
		}
		
		public void changeRotation(boolean b) {
			direction = b;
		}
		
		public void nextPlayer() {
			if (direction) {
				if (currentPlayer < (players.size() - 1)) {
					currentPlayer++;
				}
				else {
					currentPlayer = 0;
				}
			}
			else {
				if (currentPlayer > 0) {
					currentPlayer--;
				}
				else {
					currentPlayer = (players.size() - 1);
				}
			}
		}
		
		public int getCurrentPlayer() {
			return currentPlayer;
		}
//		// --- End Methods
//


}