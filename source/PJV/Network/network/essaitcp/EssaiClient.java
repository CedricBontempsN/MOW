package network.essaitcp;

import network.facade.client.FacadeClient;

public class EssaiClient implements Runnable {
	

	private static FacadeClient network;
	
	public static void main (String[] args) throws Exception {
		network = new FacadeClient(null);
		
	
		
	}

	@Override
	public void run() {
			
				try {
					main(null);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			
	}	 


}
