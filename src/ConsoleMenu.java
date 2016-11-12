import java.sql.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
	
	private List<Material> materialy = new ArrayList<Material>();
	private GWBaza database = new GWBaza();
	private CSVFileReader csvFReader = new CSVFileReader();
	
    private String CSV_FILE = "src/materials.csv";
    private String tableMaterialFormat = "| %-4d | %-50s | %-12s | %-4s | %-12s | %-12s | %-12s | %-12s |%n";
    private Scanner scanner = new Scanner(System.in);
	
    // funkcje menu
	public void startDialog() {
		boolean userWantsDialog = true;
		int wybor=-1;
		do
		{
			printUserOptions();
			try {
				wybor = scanner.nextInt();		
			} catch (Exception e) {
			    scanner.nextLine();
			} 
			switch(wybor){
			case 1: wczytajRekordyCSV();
				break;
			case 2: wypiszRekordyMaterialy();
				break;
			case 3: znajdzMaterial();
				break;
			case 4: wyczyscTabliceMaterialy();
            	break;
			case 0: userWantsDialog = false;
				break;
			default: break;
			}
		} while(userWantsDialog);
		scanner.close();
	}
	
	// wyswietlenie menu dla uzytkownika
	private void printUserOptions() {
		System.out.println("MENU:");
		System.out.println("  1. Wczytaj dane z pliku");
		System.out.println("  2. Wyswetl tabele materialow");
		System.out.println("  3. Znajdz material");
		System.out.println("  4. Wyczysc dane z tablicy materialow");
		System.out.println("  0. Wyjdz z programu");
		System.out.println(" ");
	}
	
	// funkcja wczytujaca dane z pliku csv do bazy
	private void wczytajRekordyCSV() {
		System.out.println("Trwa wczytywanie z pliku... To może chwile potrwać.");
        materialy=csvFReader.readFromCSV(CSV_FILE);
        database.bigInsertMaterial(materialy);
		System.out.println("Wczytywanie zakończone pomyślnie.");
	}
	
	// funkcja wyswietlajaca glowna tabele materialy
    private void wypiszRekordyMaterialy() {
        materialy=database.getMateral();
        wyswietlTablice(materialy);
    }
    
    private void znajdzMaterial() {
		System.out.println("MENU - Znajdz przedmiot:");
		System.out.println("  1. Znajdz po ID");
		System.out.println("  2. Znajdz po nazwie");
		System.out.println("  0. Wroc do menu glownego");
		System.out.println(" ");
		int wybor=-1;
		try {
			wybor = scanner.nextInt();		
		} catch (Exception e) {
		    scanner.nextLine();
		} 
		switch(wybor){
		case 1: znajdzMaterialId();
			break;
		case 2: znajdzMaterialNazwa();
			break;
		case 0: startDialog();
			break;
		default: break;
		}
    //	materialy=database.getMateral("id = '12'");
    //    wyswietlTablice(materialy);
    }
    
    // funkcja znajdujaca przedmiot po id
    private void znajdzMaterialId() {
    	System.out.println("Wpisz ID poszukiwanego przedmiotu:");
    	int search = scanner.nextInt();
    	materialy=database.getMateral("id = '" + search + "'");
        wyswietlTablice(materialy);
    }
    
    // funkcja znajdujaca przedmiot po nazwie
    private void znajdzMaterialNazwa() {
    	System.out.println("Wpisz nazwe poszukiwanego przedmiotu:");
    	scanner.nextLine(); // czyszczenie buffera
    	String search = scanner.nextLine();
    	materialy=database.getMateral("nazwa = '" + search + "'");
        wyswietlTablice(materialy);
    }
    
    // funkcja wyswietlajaca tabele
    private void wyswietlTablice(List<Material> materialy) {
    	Scanner scanner = new Scanner(System.in);
        if(materialy.size()==0) 
        	System.out.println("Brak elementów w tablicy.");
        else {
        	System.out.println("+=============================================================================================================================================+");
        	System.out.println("| ID   | Nazwa przedmiotu                                   | Rzadkość     | Lvl  | Max          | Min          | Ilość        | Zapotrzeb.   |");
        	System.out.println("+---------------------------------------------------------------------------------------------------------------------------------------------+");
        }
        int itemsPerPage = 300;
        int i=0;
        int pageNumber=1;
        while (i<materialy.size()) {
            for (;i<materialy.size() && i<pageNumber*itemsPerPage;i++) {
                Material tmpMat = materialy.get(i);
                System.out.format(tableMaterialFormat, tmpMat.getId(), tmpMat.getNazwa(), tmpMat.getRzadkosc(), tmpMat.getLvl(), tmpMat.getMaksOfertaKupna(), 
                    tmpMat.getMinOfertaSprzed(), tmpMat.getDostepnaIlosc(), tmpMat.getZapotrzebowanie());
            }
            pageNumber++;
            if (i>=materialy.size()) {
            	System.out.println("+=============================================================================================================================================+");
            	System.out.print("\n\n");
            }
            else {
            	System.out.print("\n\n");
                System.out.println("Wciśnij ENTER, żeby wyświetlić dalszą część tabeli ...");
                scanner.nextLine();
            }
        }  
    }
    
    // funkcja czyszczaca dane z tablicy materialy
    private void wyczyscTabliceMaterialy() {
    	database.clearTableMaterial();
    	materialy=new ArrayList<Material>();
		System.out.println("Dane zostały wyczyszczone.");
    }
}
