package Torbots.DatabaseScouting;

/**
 * Container class for holding info for Jeffrey
 */
public class TeamAverages {
	
	protected String TeamKey;
	protected int total_matches;

	
	public TeamAverages(String key) {
		total_matches = 0;
		TeamKey = key;
	}
	
	public String ReturnTeamKey() {
		return TeamKey;
	}
	
	public void AddMatch() {
		total_matches++;
	}
	
	public int GetTotalMatches() {
		return total_matches;
	}
}
