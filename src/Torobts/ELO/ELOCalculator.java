package Torobts.ELO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Terrible class that calculates and stores all ELO data Only compare back to
 * our rookie year, 2003.
 * 
 * @author Richie Ioki
 *
 */
public class ELOCalculator {

	private ArrayList<TeamData> Teams;
	private TBAConnector m_connection;
	private Queue<String> TorbotEventKeys;

	public ELOCalculator(TBAConnector connection) {
		m_connection = connection;
		TorbotEventKeys = new LinkedList<String>();
	}

	public void Initalize() throws IOException {
		Teams = new ArrayList<TeamData>();

		// look up data for team 1197 for all years of competition
		JSONArray TorbotsEvents = m_connection.run("https://www.thebluealliance.com/api/v3/team/frc1197/events/simple");

		// search through all the events for official regionals and insert into queue
		for (int i = 0; i < TorbotsEvents.length(); i++) {
			JSONObject event = (JSONObject) TorbotsEvents.get(i);
			if (event.get("name").toString().contains("Regional")
					&& Integer.parseInt(event.get("year").toString()) >= 2003 && 
					Integer.parseInt(event.get("year").toString()) != 2019) {
				String key = event.get("key").toString();
				TorbotEventKeys.add(key);
			}
		}
	}

	/**
	 * Function that returns a list of that team's events
	 * 
	 * @param TeamKey
	 * @return
	 * @throws IOException
	 */
	public Queue<String> GetEventList(String TeamKey) throws IOException {
		Queue<String> Events = new LinkedList<String>();

		// look up data for team 1197 for all years of competition
		JSONArray RawEvents = m_connection
				.run("https://www.thebluealliance.com/api/v3/team/" + TeamKey + "/events/simple");

		// search through all the events for official regionals and insert into queue
		for (int i = 0; i < RawEvents.length(); i++) {
			JSONObject event = (JSONObject) RawEvents.get(i);
			if (event.get("name").toString().contains("Regional")
					&& Integer.parseInt(event.get("year").toString()) >= 2003 && 
					Integer.parseInt(event.get("year").toString()) != 2019) {
				String key = event.get("key").toString();
				Events.add(key);
			}
		}

		return Events;
	}

	public void NewTeam(String Key) {
		TeamData data = new TeamData(Key);
		Teams.add(data);
	}

	public boolean isNewTeam(String TeamKey) {
		boolean contained = false;
		for (int i = 0; i < Teams.size(); i++) {
			if (Teams.get(i).getKey().contains(TeamKey.trim())) {
				contained = true;
			}
		}

		return !contained;
	}

	public Queue<String> GetTorbotEvents() {
		return TorbotEventKeys;
	}

	public float GetELO(String TEAMKEY) {
		float elo = 0;
		for (int i = 0; i < Teams.size(); i++) {
			if (Teams.get(i).getKey().contains(TEAMKEY.trim())) {
				elo = Teams.get(i).getELO();
			}
		}
		if (elo == 0) {
			System.err.println("ELO IS 0 FOR " + TEAMKEY);
		}

		return elo;
	}

	public TeamData GetTeamData(String TeamKey) {
		TeamData data = null;

		for (int i = 0; i < Teams.size(); i++) {
			if (Teams.get(i).getKey().contains(TeamKey)) {
				data = Teams.get(i);
				return data;
			}
		}

		return data;
	}

	/**
	 * Simple function to calculate the percentages of RED winning and Blue winning.
	 * Array is returned and always Red = 0, Blue = 1
	 * 
	 * @param RedELO
	 * @param BlueELO
	 * @return
	 */
	public float[] CalculateWinPercentages(int RedELO, int BlueELO) {
		float[] percentages = new float[2]; // always RED THEN BLUE

		float powRED = (RedELO - BlueELO) / 400;
		float powBLUE = (BlueELO - RedELO) / 400;
		percentages[0] = 1 / (1 + (float) Math.pow(10, powRED));
		percentages[1] = 1 / (1 + (float) Math.pow(10, powBLUE));

		return percentages;
	}

	public void ExecuteMatchResult(String[] RED_KEYS, String[] BLUE_KEYS, String RESULT, float delta) {
		// Calculate Delta for win and for loss
		if (RESULT.contains("red")) {
			// find the red teams and increase ELO by delta
			for (int i = 0; i < RED_KEYS.length; i++) {
				TeamData team = GetTeamData(RED_KEYS[i]);
				if (team != null) {					
					team.AdjustELO(Math.abs(delta));
					//System.out.println("ADJUSTING ELO FOR RED " + team.getKey());
					//System.out.println("NEW ELO IS " + team.getELO());
				} else {
					System.err.println("DIDN'T FIND TEAM " + RED_KEYS[i]);
				}
			}

			// find blue teams and decrease ELO by delta
			for (int i = 0; i < BLUE_KEYS.length; i++) {
				TeamData team = GetTeamData(BLUE_KEYS[i]);
				if (team != null) {
					team.AdjustELO(-Math.abs(delta));
					//System.out.println("ADJUSTING ELO FOR BLUE " + team.getKey());
					//System.out.println("NEW ELO IS " + team.getELO());
				} else {
					System.err.println("DIDN'T FIND TEAM " + BLUE_KEYS[i]);
				}
			}

		} else if (RESULT.contains("blue")) {
			// find the red teams and decrease ELO by delta
			for (int i = 0; i < RED_KEYS.length; i++) {
				TeamData team = GetTeamData(RED_KEYS[i]);
				if (team != null) {
					team.AdjustELO(-Math.abs(delta));
					//System.out.println("ADJUSTING ELO FOR RED " + team.getKey());
					//System.out.println("NEW ELO IS " + team.getELO());
				} else {
					System.err.println("DIDN'T FIND TEAM " + RED_KEYS[i]);
				}
			}

			// find blue teams and incrase ELO by delta

			for (int i = 0; i < BLUE_KEYS.length; i++) {
				TeamData team = GetTeamData(BLUE_KEYS[i]);
				if (team != null) {
					team.AdjustELO(Math.abs(delta));
					//System.out.println("ADJUSTING ELO FOR BLUE " + team.getKey());
					//System.out.println("NEW ELO IS " + team.getELO());
				} else {
					System.err.println("DIDN'T FIND TEAM " + BLUE_KEYS[i]);
				}
			}
		} else {
			System.err.println("IN CORRECT RESULT " + RESULT);
		}

	}

	public void PrintAllELO() {

		for (int i = 0; i < Teams.size(); i++) {
			System.out.println("Team : " + Teams.get(i).getKey() + " ELO : " + Teams.get(i).getELO());
		}

	}
}
