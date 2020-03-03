package Torbots.DatabaseScouting;

/**
 * Container class for holding info for Jeffrey
 */
public class TeamAverages {
	
	protected String TeamKey;
	protected int Total_Matches;

	
	public TeamAverages(String key) {
		Total_Matches = 0;
		TeamKey = key;
	}
	
	public String ReturnTeamKey() {
		return TeamKey;
	}
	
	public void AddMatch() {
		Total_Matches++;
	}
	
	public int GetTotalMatches() {
		return Total_Matches;
	}
}
