package Torobts.ELO;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import org.json.JSONArray;
import org.json.JSONObject;

public class ExecuteSearch {
	
	private static String BASE_URL = "https://www.thebluealliance.com/api/v3";
	
	private ELOCalculator m_calculator;
	private TBAConnector m_connection;
	
	public ExecuteSearch(ELOCalculator calculator,TBAConnector connection) {
		m_calculator = calculator;
		m_connection = connection;
	}
	
	/**
	 * Mega function that launches the actual search
	 * @throws IOException 
	 */
	public void RunSearch(String TeamKey) throws IOException {
		Queue<String> Events = new LinkedList<String>();
		if(!TeamKey.contains("1197")) {
			System.out.println("NOT TORBOTS");
		} else {
			Events = m_calculator.GetTorbotEvents();
		}
		
		
		for(int i = Events.size()-2; i < Events.size(); i++) {
			//get matches for Torbots
			JSONArray Matches = m_connection.run(BASE_URL + "/team/" + TeamKey + "/event/" + Events.poll().trim() + "/matches/simple");
			
			for(int j = 0; j < Matches.length(); j++) {
				JSONObject Match = (JSONObject) Matches.get(j);
				JSONObject ALLIANCES = Match.getJSONObject("alliances");
				
				//RED ALLIANCE
				JSONObject RED_ALLIANCE = ALLIANCES.getJSONObject("red");
				JSONArray RED_TEAMKEYS = RED_ALLIANCE.getJSONArray("team_keys");
				
				//BLUE ALLIANCE
				JSONObject BLUE_ALLIANCE = ALLIANCES.getJSONObject("blue");
				JSONArray BLUE_TEAMKEYS = BLUE_ALLIANCE.getJSONArray("team_keys");
				
				//check if red have ELO				
				for(int z = 0; z < RED_TEAMKEYS.length(); z++) {
					if(m_calculator.isNewTeam(RED_TEAMKEYS.getString(z))) {
						m_calculator.NewTeam(RED_TEAMKEYS.getString(z));
						
						//calculate ELO for this new team
						System.out.println("ADDING NEW TEAM " + RED_TEAMKEYS.getString(z));
					}
					
					//once you have ELO then calculate average for average ELO for alliance
				}
				
				
				//check if blue have ELO
				for(int z = 0; z < BLUE_TEAMKEYS.length(); z++) {
					if(m_calculator.isNewTeam(BLUE_TEAMKEYS.getString(z))) {
						m_calculator.NewTeam(BLUE_TEAMKEYS.getString(z));
						
						//calculate ELO for this new team
						System.out.println("ADDING NEW TEAM " + BLUE_TEAMKEYS.getString(z));
					}
					
					//once you have ELO then calculate average for average ELO for alliance
				}				

			}
			
		}
	}
}
