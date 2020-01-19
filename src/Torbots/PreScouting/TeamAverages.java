package Torbots.PreScouting;

/**
 * Container class for holding info for Jeffrey
 */
public class TeamAverages {
	
	private String TeamKey;
	private int total_matches;
	private int total_hatchpoints;
	private int total_cargopoints;
	private int total_climbpoints;
	
	public TeamAverages(String key) {
		total_matches = 0;
		total_hatchpoints = 0;
		total_cargopoints = 0;
		total_climbpoints = 0;
		TeamKey = key;
	}
	
	public String ReturnTeamKey() {
		return TeamKey;
	}
	
	public void AddMatch() {
		total_matches++;
	}
	
	public void IncreaseHatchScore(int score) {
		total_hatchpoints += score;
	}
	
	public void IncreaseCargoScore(int score) {
		total_cargopoints += score;
	}
	
	public void IncreaseClimbScore(int score) {
		total_climbpoints += score;
	}
	
	public float AvgHatchScore() {
		return total_hatchpoints/total_matches;
	}
	
	public float AvgCargoScore() {
		return total_cargopoints/total_matches;
	}
	
	public float AvgClimbScore() {
		return total_climbpoints/total_matches;
	}
}
