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
						RunSearch(RED_TEAMKEYS.getString(z));
					}

					// once you have ELO then calculate average for average ELO for alliance
					RedELO += m_calculator.GetELO(RED_TEAMKEYS.getString(z).trim());
				}

				// check if blue have ELO
				for (int z = 0; z < BLUE_TEAMKEYS.length(); z++) {
					if (m_calculator.isNewTeam(BLUE_TEAMKEYS.getString(z))) {
						m_calculator.NewTeam(BLUE_TEAMKEYS.getString(z));

						// calculate ELO for this new team
						RunSearch(BLUE_TEAMKEYS.getString(z).trim());
					}

					// once you have ELO then calculate average for average ELO for alliance
					BlueELO += m_calculator.GetELO(RED_TEAMKEYS.getString(z).trim());
				}

				// completed calculating the ELOs for the matches
				if (RED_TEAMKEYS.length() == 0 || BLUE_TEAMKEYS.length() == 0) {
					System.err.println("Tried to divide by zero for teams list.  BLUE : " + BLUE_TEAMKEYS.length()
							+ "  RED : " + RED_TEAMKEYS.length());
				} else {
					RedAverage = RedELO / RED_TEAMKEYS.length();
					BlueAverage = BlueELO / BLUE_TEAMKEYS.length();

					float RedWinPercent, BlueWinPercent;
					float[] percentages = m_calculator.CalculateWinPercentages(RedAverage, BlueAverage);
					RedWinPercent = percentages[0];
					BlueWinPercent = percentages[1];

					// Calculating the delta based on RedWinPercentage
					float delta = Math.abs(32 * (1 - RedWinPercent));

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
}
