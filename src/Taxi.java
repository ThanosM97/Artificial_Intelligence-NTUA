import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Taxi extends Position {
	private int id;
	
	public Taxi(double x, double y, int identity) {
		super(x,y);
		this.id = identity;
	}

	public int getId() {
		return id;
	}
	
	public void print() {
		System.out.println(this.toString() + " id:"+ this.id);
	}

	@Override
	public String toString() {
		return "X:" + this.getX() + " Y:" + this.getY() + " id:" + this.id;
	}
	
	public static ArrayList<Taxi> read(String csvFile) {
		BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        Taxi aTaxi = null;
        ArrayList<Taxi> Taxis = new ArrayList<Taxi>();

        try {
            br = new BufferedReader(new FileReader(csvFile));
            line = br.readLine();
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] values = line.split(cvsSplitBy);
                double a = Double.parseDouble(values[0]);
                double b = Double.parseDouble(values[1]);
                int c = Integer.parseInt(values[2]);
                
                aTaxi = new Taxi(a,b,c);
                Taxis.add(aTaxi);
            }
            
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
        return Taxis;
	}
}
