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

	public ExecuteSearch(ELOCalculator calculator, TBAConnector connection) {
		m_calculator = calculator;
		m_connection = connection;
	}

	/**
	 * Mega function that launches the actual search
	 * 
	 * @throws IOException
	 */
	public void RunSearch(String TeamKey) throws IOException {
		Queue<String> Events = new LinkedList<String>();
		if (!TeamKey.contains("1197")) {
			Events = m_calculator.GetEventList(TeamKey);
		} else {
			Events = m_calculator.GetTorbotEvents();
		}

		for (int i = 0; i < Events.size(); i++) {
			// get matches for Torbots
			JSONArray Matches = m_connection
					.run(BASE_URL + "/team/" + TeamKey + "/event/" + Events.poll().trim() + "/matches/simple");

			for (int j = 0; j < Matches.length(); j++) {
				JSONObject Match = (JSONObject) Matches.get(j);
				JSONObject ALLIANCES = Match.getJSONObject("alliances");

				// RED ALLIANCE
				JSONObject RED_ALLIANCE = ALLIANCES.getJSONObject("red");
				JSONArray RED_TEAMKEYS = RED_ALLIANCE.getJSONArray("team_keys");

				// BLUE ALLIANCE
				JSONObject BLUE_ALLIANCE = ALLIANCES.getJSONObject("blue");
				JSONArray BLUE_TEAMKEYS = BLUE_ALLIANCE.getJSONArray("team_keys");

				String RESULT = Match.getString("winning_alliance");

				int RedELO = 0, BlueELO = 0;
				int RedAverage = 0, BlueAverage;
				// check if red have ELO
				for (int z = 0; z < RED_TEAMKEYS.length(); z++) {
					if (m_calculator.isNewTeam(RED_TEAMKEYS.getString(z))) {
						m_calculator.NewTeam(RED_TEAMKEYS.getString(z).trim());

						// calculate ELO for this new team
						//System.out.println("ADDING NEW TEAM " + RED_TEAMKEYS.getString(z));
						RunSearch(RED_TEAMKEYS.getString(z));
					}

					// once you have ELO then calculate average for average ELO for alliance
					RedELO += m_calculator.GetELO(RED_TEAMKEYS.getString(z).trim());
				}

				// System.out.println("RED ELO FOR MATCH " + Match.getInt("match_number") + " is
				// " + RedELO);

				// check if blue have ELO
				for (int z = 0; z < BLUE_TEAMKEYS.length(); z++) {
					if (m_calculator.isNewTeam(BLUE_TEAMKEYS.getString(z))) {
						m_calculator.NewTeam(BLUE_TEAMKEYS.getString(z));

						// calculate ELO for this new team
						//System.out.println("ADDING NEW TEAM " + BLUE_TEAMKEYS.getString(z));
						RunSearch(BLUE_TEAMKEYS.getString(z).trim());
					}

					// once you have ELO then calculate average for average ELO for alliance
					BlueELO += m_calculator.GetELO(RED_TEAMKEYS.getString(z).trim());
				}

				// System.out.println("BLUE ELO FOR MATCH " + Match.getInt("match_number") + "
				// is " + BlueELO);

				// completed calculating the ELOs for the matches
				RedAverage = RedELO / RED_TEAMKEYS.length();
				BlueAverage = BlueELO / BLUE_TEAMKEYS.length();

				float RedWinPercent, BlueWinPercent;
				float[] percentages = m_calculator.CalculateWinPercentages(RedAverage, BlueAverage);
				RedWinPercent = percentages[0];
				BlueWinPercent = percentages[1];

				// Next get the actual result of the match so that we can change the ELO of the
				// teams;
				//System.out.println("RED WIN PERCENTAGE " + RedWinPercent);
				//System.out.println("BLUE WIN PERCENTAGE " + BlueWinPercent);
				float delta = Math.abs(32 * (1 - RedWinPercent));
				//System.out.println("THE DELTA Change in elo is " + delta);

				String[] r_keys = new String[RED_TEAMKEYS.length()];
				String[] b_keys = new String[BLUE_TEAMKEYS.length()];

				for (int r = 0; r < RED_TEAMKEYS.length(); r++) {
					r_keys[r] = RED_TEAMKEYS.getString(r);
				}

				for (int b = 0; b < BLUE_TEAMKEYS.length(); b++) {
					b_keys[b] = BLUE_TEAMKEYS.getString(b);
				}

				m_calculator.ExecuteMatchResult(r_keys, b_keys, RESULT, delta);
			}

		}
	}
}
