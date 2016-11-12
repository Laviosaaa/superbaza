import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import com.opencsv.CSVReader;
import java.util.ArrayList;
import java.util.List;

public class CSVFileReader {

	// odczytywanie danych z pliku csv
	public List<Material> readFromCSV(String fileName) {
		List<Material> result = new ArrayList<Material>();
		int i = 0;
	    try {
	    	CSVReader reader = new CSVReader(new FileReader(fileName));
	    	String [] nextLine;
	    	reader.readNext();
	    	while ((nextLine = reader.readNext()) != null && i<300) {
	    		result.add(new Material(
	    				0, nextLine[1].replace("'", ""), rarityFromIntToString(Integer.parseInt(nextLine[2])), 
	    				Integer.parseInt(nextLine[3]), Integer.parseInt(nextLine[8]), 
	    				Integer.parseInt(nextLine[9]), Integer.parseInt(nextLine[10]), 
	    				Integer.parseInt(nextLine[11])
	    				));
	    		i++;
	    	}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return result;
	}
	
	// zamiana wartosci kolumny rzadkosc na slowna reprezentacje
	public String rarityFromIntToString(int rarity) {
	    String result;
	    switch (rarity) {
	    case 1: result="Basic";
	        break;
	    case 2: result="Fine";
	        break;
	    case 3: result="Masterwork";
        	break;
	    case 4: result="Rare";
	    	break;
	    case 5: result="Exotic";
        	break;
	    case 6: result="Ascended";
	    	break;
	    case 7: result="Legendary";
    		break;
	    default: result="Junk";
	    	break;
	    }
	    return result;
	}
}