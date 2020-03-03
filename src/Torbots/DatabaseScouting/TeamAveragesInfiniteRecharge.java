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
	
	public void AddAutoInitPoints(int score) {
		AutoInitLinePoints += score;
	}
	
	public void AddAutoCells(int score) {
		AutoCells += score;
	}
	
	public void AddTeleCells(int score) {
		TeleCells += score;
	}
	
	public void AddControlPanelPoints(int score) {
		ControlPanelPoints += score;
	}
	
	public void AddEndGamePoints(int score) {
		EndGamePoints += score;
	}
	
	public float AvgAutoInitPoints() {
		return (float)AutoInitLinePoints / Total_Matches;
	}
	
	public float AvgAutoCells() {
		return (float)AutoCells / Total_Matches;
	}
	
	public float AvgTeleCells() {
		return (float)TeleCells / Total_Matches;
	}
	
	public float AvgControlPanelPoints() {
		return (float)ControlPanelPoints / Total_Matches;
	}
	
	public float AvgEndGamePoints() {
		return (float)EndGamePoints / Total_Matches;
	}
	
}
