package Torbots.PreScouting;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import Torobts.ELO.TBAConnector;

public class MatchDataCollector {

	private TBAConnector m_connector;
	private JSONArray RawTeamList;
	private String m_eventkey;
	private List<TeamAverages> averages;

	public MatchDataCollector(TBAConnector connector, String eventkey) throws IOException {
		m_connector = connector;
		RawTeamList = m_connector.run("https://www.thebluealliance.com/api/v3/event/" + eventkey + "/teams");

		m_eventkey = eventkey;
		averages = new LinkedList<TeamAverages>();
	}

	public void PrintRawList() {
		for (int i = 0; i < RawTeamList.length(); i++) {
			System.out.println(RawTeamList.get(i));
			JSONObject obj = (JSONObject) RawTeamList.get(i);
		}
	}

	public void CalculateAverages() throws IOException {
		//PrintRawList();

		// Finding all the teams that are attending LV regional
		for (int i = 0; i < RawTeamList.length(); i++) {
			JSONObject obj = (JSONObject) RawTeamList.get(i);

			String key = obj.getString("key");
			TeamAverages avg = new TeamAverages(key);
			averages.add(avg);
		}

		for (int j = 0; j < averages.size(); j++) {
			// get all the regional events this team has played at
			JSONArray events = m_connector.run(
					"https://www.thebluealliance.com/api/v3/team/" + averages.get(j).ReturnTeamKey() + "/events/2019");

			for (int z = 0; z < events.length(); z++) {
				JSONObject event = (JSONObject) events.get(z);
				if (!event.getString("key").contains("2019nvlv")) {
					// get all the matches the team has played at those events
					String EventKey = event.getString("key");
					JSONArray matches = m_connector.run("https://www.thebluealliance.com/api/v3/team/"
							+ averages.get(j).ReturnTeamKey() + "/event/" + EventKey + "/matches");

					// Add Match data to the averages
					for (int q = 0; q < matches.length(); q++) {
						//System.out.println(matches.get(q));
						JSONObject match = matches.getJSONObject(q);
						JSONObject alliances = match.getJSONObject("alliances");
						JSONObject blue, red;
						blue = alliances.getJSONObject("blue");
						red = alliances.getJSONObject("red");
						JSONArray bluealliance, redalliance;
						bluealliance = blue.getJSONArray("team_keys");
						redalliance = red.getJSONArray("team_keys");

						try {
							System.out.println(match.toString());
							JSONObject breakdown = match.getJSONObject("score_breakdown");
							
							averages.get(j).AddMatch();

							if (bluealliance.toString().contains(averages.get(j).ReturnTeamKey())) {
								JSONObject bluescores = breakdown.getJSONObject("blue");

								averages.get(j).IncreaseCargoScore(bluescores.getInt("cargoPoints"));
								averages.get(j).IncreaseClimbScore(bluescores.getInt("habClimbPoints"));
								averages.get(j).IncreaseHatchScore(bluescores.getInt("hatchPanelPoints"));
								
							} else if (redalliance.toString().contains(averages.get(j).ReturnTeamKey())) {
								JSONObject redscores = breakdown.getJSONObject("red");

								averages.get(j).IncreaseCargoScore(redscores.getInt("cargoPoints"));
								averages.get(j).IncreaseClimbScore(redscores.getInt("habClimbPoints"));
								averages.get(j).IncreaseHatchScore(redscores.getInt("hatchPanelPoints"));
								
							} else {
								System.err.println("COULDN'T FIND TEAM");
							}
						} catch (JSONException e) {
<<<<<<< HEAD
							//e.printStackTrace();
=======
>>>>>>> branch 'master' of https://github.com/richieioki/ELO-JAVA.git
							//System.err.println("SCORE BREAK DOWN IS NULL : " + match);
						}
					}

				} else {
					// System.out.println("------Las Vegas Regional-----");
				}
			}
		}

		
		// print out all the averages
		TeamAverages currentteam;
		for (int l = 0; l < averages.size(); l++) {
			currentteam = averages.get(l);
		}
	}
	
	public List<TeamAverages> GetAverages() {
		return averages;
	}
}
