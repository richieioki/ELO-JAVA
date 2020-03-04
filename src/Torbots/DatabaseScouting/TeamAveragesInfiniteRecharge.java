package Torbots.DatabaseScouting;

public class TeamAveragesInfiniteRecharge extends TeamAverages {

	//2020 Specific Data
	private int AutoInitLinePoints;
	private int AutoCells; //Total auto cells not counting how many are 3 vs 2 right now.  Low goals are also included
	private int TeleCells; //Total Tele cells not counting how many are 3 vs 2 right now.  Low goals are also included
	private int ControlPanelPoints;
	private int EndGamePoints;
	
	//Additional Data for deeper insight
	private int Tele3Balls;
	private int Tele2Balls;
	private int Auto3Balls;
	private int Auto2Balls;
	private int AutoScore;
	private int TeleScore;
	
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

//////////////////////////ADDITIONAL DATA FUNCTIONS////////////////////////////////	
	public void AddAutoPoints(int score) {
		AutoScore += score;
	}
	
	public void AddTeleScore(int score) {
		TeleScore += score;
	}
	
	public void AddAuto3Balls(int score) {
		Auto3Balls += score;
	}
	
	public void AddAuto2Balls(int score) {
		Auto2Balls += score;
	}
	
	public void AddTele3Balls(int score) {
		Tele3Balls += score;
	}
	
	public void AddTele2Balls(int score) {
		Tele2Balls += score;
	}
	
	public float AvgAutoPoints() {
		return (float)AutoScore / Total_Matches;
	}
	
	public float AvgTelePoints() {
		return (float)TeleScore / Total_Matches;
	}
	
	public float AvgAuto3Ball() {
		return (float)Auto2Balls / Total_Matches;
	}
	
	public float AvgAuto2Ball() {
		return (float)Auto3Balls / Total_Matches;
	}
	
	public float AvgTele3Ball() {
		return (float)Tele3Balls / Total_Matches;
	}
	
	public float AvgTele2Ball() {
		return (float)Tele2Balls / Total_Matches;
	}
}
