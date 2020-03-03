package Torbots.DatabaseScouting;

public class TeamAveragesDeepSpace extends TeamAverages {

	//2019 Specific Data
	private int CargoScore, ClimbScore, HatchScore;
	
	public TeamAveragesDeepSpace(String key) {
		super(key);

		CargoScore = 0;
		ClimbScore = 0;
		HatchScore = 0;
	}

	public void AddCargoScore(int score) {
		CargoScore += score;
	}
	
	public void AddClimbScore(int score) {
		ClimbScore += score;
	}

	public void AddHatchScore(int score) {
		HatchScore += score;
	}
	
	public float AvgCargoScore() {
		return CargoScore / Total_Matches;
	}
	
	public float AvgClimbScore() {
		return ClimbScore / Total_Matches;
	}
	
	public float AvgHatchScore() {
		return HatchScore / Total_Matches;
	}
}
