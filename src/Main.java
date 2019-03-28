import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import Torbots.PreScouting.MatchDataCollector;
import Torobts.ELO.*;

public class Main {

	public static void main(String[] args) {

		TBAConnector connection = new TBAConnector();

		if (args[0].contains("ELO")) {
			// TODO Auto-generated method stub
			ELOCalculator calculator = new ELOCalculator(connection);
			ExecuteSearch searcher = new ExecuteSearch(calculator, connection);

			try {
				calculator.Initalize();
				searcher.RunSearch("frc1197");
				System.out.println(
						"---------------------------PRINTING CALCULATED ELOs--------------------------------------");
				calculator.PrintAllELO();

				// export information to CSV
				File dir = new File("DATA");
				dir.mkdir();
				FileWriter out = new FileWriter(dir.getAbsolutePath() + "/2012-2018-1197Data.csv");

				CSVPrinter printer = CSVFormat.EXCEL.withHeader("Team Number", "ELO").print(out);
				calculator.PrintALLELO(printer);

				// ending file
				printer.flush();
				printer.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.print("ISSUE CONNECTING TO SERVER");
				e.printStackTrace();
			}
		} else {
			try {
				MatchDataCollector collector = new MatchDataCollector(connection, args[1]);				
				collector.CalculateAverages();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}					
		}
	}
}
