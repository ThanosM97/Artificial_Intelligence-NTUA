import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Client extends Position {
	
	public Client(double x, double y) {
		super(x,y);
	}

	public static Client read(String csvFile) {		
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        Client person = null;

        try {
            br = new BufferedReader(new FileReader(csvFile));
            line = br.readLine();
            line = br.readLine();

                String[] values = line.split(cvsSplitBy);
                double a = Double.parseDouble(values[0]);
                double b = Double.parseDouble(values[1]);
                
                person = new Client(a,b);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return person;
	}
	
}
