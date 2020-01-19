import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import Torbots.PreScouting.MatchDataCollector;
import Torbots.PreScouting.TeamAverages;
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
				System.out.println("Match Connector");
				MatchDataCollector collector = new MatchDataCollector(connection, args[1]);				
				collector.CalculateAverages();
				
				File dir = new File("DATA");
				dir.mkdir();
				FileWriter out = new FileWriter(dir.getAbsolutePath() + "/" + args[1] + "data.csv");
				CSVPrinter printer = CSVFormat.EXCEL.withHeader("Team Number", "cargoPoints", "habClimbPoints", "hatchPanelPoints").print(out);
				
				List<TeamAverages> m_list = collector.GetAverages();
				
				for(TeamAverages t : m_list) {
					printer.printRecord(t.ReturnTeamKey(), t.AvgCargoScore(), t.AvgClimbScore(), t.AvgHatchScore());
				}
				
				System.out.print("Printing File");				
				// ending file
				printer.flush();
				printer.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}					
		}
	}
}
