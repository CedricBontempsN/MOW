package network.analyse.utilitaire;

import java.util.ArrayList;

public class Encoder {
	
	public String splitTxt(ArrayList<String> txt) {
		StringBuilder builder = new StringBuilder();
		builder.append(txt.get(0));

		txt.remove(0);
		while(!txt.isEmpty()) {
			builder.append("-"+txt.get(0));
			txt.remove(0);
		}
		return builder.toString();
	}

}
