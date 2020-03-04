package Torbots.DatabaseScouting;

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
		System.out.println("Trying to connect to TBA");
		m_connector = connector;
		RawTeamList = m_connector.run("https://www.thebluealliance.com/api/v3/event/" + eventkey + "/teams");

		m_eventkey = eventkey;
		averages = new LinkedList<TeamAverages>();

		if (RawTeamList.length() > 0) {
			System.out.println("Connected and got a team list of " + RawTeamList.length());
		}
	}

	public void PrintRawList() {
		for (int i = 0; i < RawTeamList.length(); i++) {
			System.out.println(RawTeamList.get(i));
			JSONObject obj = (JSONObject) RawTeamList.get(i);
		}
	}

	public void CalculateAverages() throws IOException {
		// PrintRawList();

		for (int i = 0; i < RawTeamList.length(); i++) {
			JSONObject obj = (JSONObject) RawTeamList.get(i);

			String key = obj.getString("key");
			//System.out.println(key);
			TeamAverages avg = new TeamAveragesInfiniteRecharge(key);
			averages.add(avg);
		}

		for (int j = 0; j < averages.size(); j++) {
			// get all the regional events this team has played at
			// JSONArray events =
			// m_connector.run("https://www.thebluealliance.com/api/v3/team/" +
			// averages.get(j).ReturnTeamKey() + "/events/2020");

			JSONArray matches = m_connector.run("https://www.thebluealliance.com/api/v3/team/"
					+ averages.get(j).ReturnTeamKey() + "/event/" + m_eventkey + "/matches");

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
					// System.out.println(match.toString());
					JSONObject breakdown = match.getJSONObject("score_breakdown");

					averages.get(j).AddMatch();

					if (bluealliance.toString().contains(averages.get(j).ReturnTeamKey())) {
						JSONObject bluescores = breakdown.getJSONObject("blue");

						TeamAveragesInfiniteRecharge IR = (TeamAveragesInfiniteRecharge) averages.get(j);
						IR.AddAutoInitPoints(bluescores.getInt("autoInitLinePoints"));
						IR.AddAutoCells(bluescores.getInt("autoCellsBottom") + bluescores.getInt("autoCellsInner")
								+ bluescores.getInt("autoCellsOuter"));
						IR.AddTeleCells(bluescores.getInt("teleopCellsBottom") + bluescores.getInt("teleopCellsInner")
								+ bluescores.getInt("teleopCellsOuter"));
						IR.AddControlPanelPoints(bluescores.getInt("controlPanelPoints"));
						IR.AddEndGamePoints(bluescores.getInt("endgamePoints"));
						

					} else if (redalliance.toString().contains(averages.get(j).ReturnTeamKey())) {
						JSONObject redscores = breakdown.getJSONObject("red");

						TeamAveragesInfiniteRecharge IR = (TeamAveragesInfiniteRecharge) averages.get(j);
						IR.AddAutoInitPoints(redscores.getInt("autoInitLinePoints"));
						IR.AddAutoCells(redscores.getInt("autoCellsBottom") + redscores.getInt("autoCellsInner")
								+ redscores.getInt("autoCellsOuter"));
						IR.AddTeleCells(redscores.getInt("teleopCellsBottom") + redscores.getInt("teleopCellsInner")
								+ redscores.getInt("teleopCellsOuter"));
						IR.AddControlPanelPoints(redscores.getInt("controlPanelPoints"));
						IR.AddEndGamePoints(redscores.getInt("endgamePoints"));
						

					} else {
						System.err.println("COULDN'T FIND TEAM");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

		// print out all the averages regional summary			
		System.out.println("-----------------------------------------------");
		System.out.println("--------------REGIONAL SUMMARY-----------------");
		
		System.out.println("-----------------------------------------------");
	}

	public List<TeamAverages> GetAverages() {
		return averages;
	}
}
