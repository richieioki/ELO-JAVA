import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

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
			System.out.println("---------------------------PRINTING CALCULATED ELOs--------------------------------------");
			calculator.PrintAllELO();
			
			//export information to CSV		
			File dir = new File("DATA");
			dir.mkdir();
			FileWriter out = new FileWriter(dir.getAbsolutePath() + "/2012-2018-1197Data.csv");
			
			CSVPrinter printer = CSVFormat.EXCEL.withHeader("Team Number", "ELO").print(out);
			calculator.PrintALLELO(printer);
			
			//ending file
			printer.flush();
			printer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.print("ISSUE CONNECTING TO SERVER");
			e.printStackTrace();
		}
	}
}
