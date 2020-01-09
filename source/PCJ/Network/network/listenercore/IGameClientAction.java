package network.listenercore;

public interface IGameClientAction {
	
	void playerSearchGame(String type, String tailleP);
	void joueurRequeteJoinGame(String name, String type, String idConnection,String port);
	void joueurRentreTroupeau(String ram, String idp, String idm, String idt);
	void joueurAJouerCarte(String carteJouer, String uidPartie, String uidTour, String uidManche);
	void joueurInverseSensJeu(String inverserSensJeux, String uidPartie, String uidTour, String uidManche);
	String getStatut();
	void refusePlayerAutomatic(String string, String string2, String string3, String port);
}
