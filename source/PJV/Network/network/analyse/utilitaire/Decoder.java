package network.analyse.utilitaire;

import java.util.ArrayList;

public class Decoder {
	
	public ArrayList<String> splitTxt(String txt) {
		ArrayList<String> list = new ArrayList<>();
		for(String retval: txt.split("-")) {
			System.out.println("MESSAGE "+retval );
			list.add(retval);
		}
		return list;
	}
	
}
