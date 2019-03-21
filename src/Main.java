import java.io.IOException;
import Torobts.ELO.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TBAConnector connection = new TBAConnector();
		ELOCalculator calculator = new ELOCalculator(connection);
		ExecuteSearch searcher = new ExecuteSearch(calculator, connection);

		try {
			calculator.Initalize();
			searcher.RunSearch("frc1197");
			System.out.println(
					"---------------------------PRINTING CALCULATED ELOs--------------------------------------");
			calculator.PrintAllELO();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.print("ISSUE CONNECTING TO SERVER");
			e.printStackTrace();
		}
	}
}
