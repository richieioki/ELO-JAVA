import java.io.IOException;

import Torobts.ELO.TBAConnector;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TBAConnector connection = new TBAConnector();
		
		try {
			connection.run("https://www.thebluealliance.com/api/v3/team/frc1197/event/2004ca/matches/simple");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
