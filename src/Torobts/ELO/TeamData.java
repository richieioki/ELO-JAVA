package Torobts.ELO;

//Simple Data class to hold key information together
public class TeamData {
	private int TeamNumber;
	private int ELO;
	private String Key;
	
	public TeamData(String Key) {
		this.Key = Key;
		ELO = 1000;
	}
	
	public int getTeamNumber() {
		return TeamNumber;
	}

	public int getELO() {
		return ELO;
	}
	
	public void setELO(int eLO) {
		ELO = eLO;
	}
	
	public String getKey() {
		return Key;
	}
}
