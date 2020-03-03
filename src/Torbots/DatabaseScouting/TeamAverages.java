package Torbots.DatabaseScouting;

/**
 * Container class for holding info for Jeffrey
 */
public class TeamAverages {
	
	private String TeamKey;
	private int total_matches;

	
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
	
}
