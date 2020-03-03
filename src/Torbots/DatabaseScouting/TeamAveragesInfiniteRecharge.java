package Torbots.DatabaseScouting;

public class TeamAveragesInfiniteRecharge extends TeamAverages {

	//2020 Specific Data
	private int AutoInitLinePoints;
	private int AutoCells; //Total auto cells not counting how many are 3 vs 2 right now.  Low goals are also included
	private int TeleCells; //Total Tele cells not counting how many are 3 vs 2 right now.  Low goals are also included
	private int ControlPanelPoints;
	private int EndGamePoints;
	
	public TeamAveragesInfiniteRecharge(String key) {
		super(key);
		// TODO Auto-generated constructor stub
	}
	public void AddCargoScore(int score) {
		//CargoScore += score;
	}
	
	public void AddClimbScore(int score) {
		//ClimbScore += score;
	}

	public void AddHatchScore(int score) {
		//HatchScore += score;
	}
	
	/*public float AvgCargoScore() {
		return CargoScore / total_matches;
	}
	
	public float AvgClimbScore() {
		return ClimbScore / total_matches;
	}
	
	public float AvgHatchScore() {
		return HatchScore / total_matches;
	}*/
}
