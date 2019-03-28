package Torobts.ELO;

//Simple Data class to hold key information together
public class TeamData {
	private int TeamNumber;
	private float ELO;
	private String Key;
	
	public TeamData(String Key) {
		this.Key = Key;
		ELO = 1000;
	}
	
	public int getTeamNumber() {
		return TeamNumber;
	}

	public float getELO() {
		return ELO;
	}
	
	public void AdjustELO(float delta) {
		ELO += delta;
		
		//Inserting min value so that team's don't have negative ELO rankings
		if(ELO < 1000) {
			ELO = 1000;
		}
	}
	
	public String getKey() {
		return Key;
	}
}
