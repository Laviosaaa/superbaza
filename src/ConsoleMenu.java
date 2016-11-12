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
	
    // funkcje menu
	public void startDialog() {
		Scanner reader = new Scanner(System.in);
		boolean userWantsDialog = true;
		int wybor;
		do
		{
			printUserOptions();
			wybor = reader.nextInt();
			switch(wybor){
			case 1: wczytajRekordyCSV();
				break;
			case 2: wypiszRekordyMaterialy();
				break;
			case 3: wyczyscTabliceMaterialy();
            	break;
			case 0: userWantsDialog = false;
				break;
			}
			
		}while(userWantsDialog);
		reader.close();
	}
	
	// wyswietlenie menu dla uzytkownika
	private void printUserOptions() {
		System.out.println("MENU:");
		System.out.println("  1. Wczytaj dane z pliku");
		System.out.println("  2. Wyswetl tabele materialow");
		System.out.println("  3. Wyczysc dane z tablicy materialow");
		System.out.println("  0. Wyjdz z programu");
		System.out.println(" ");
	}
	
	// funkcja wczytujaca dane z pliku csv do bazy
	private void wczytajRekordyCSV() {
		System.out.println("Trwa wczytywanie z pliku... To mo¿e chwile potrwaæ.");
        materialy=csvFReader.readFromCSV(CSV_FILE);
        database.bigInsertMaterial(materialy);
		System.out.println("Wczytywanie zakoñczone pomyœlnie.");
	}
	
	// funkcja wyswietlajaca glowna tabele materialy
    private void wypiszRekordyMaterialy() {
        materialy=database.getMateral();
        Scanner scanner = new Scanner(System.in);
        if(materialy.size()==0) 
        	System.out.println("Brak elementów w tablicy.");
        else {
        	System.out.println("+== Tabela Materialy =========================================================================================================================+");
        	System.out.println("| ID   | Nazwa przedmiotu                                   | Rzadkoœæ     | Lvl  | Max          | Min          | Ilosc        | Zapotrzeb.   |");
        	System.out.println("+---------------------------------------------------------------------------------------------------------------------------------------------+");
        }
        int itemsPerPage = 300;
        int i=0;
        int pageNumber=1;
        while (i<materialy.size()) {
            for (;i<materialy.size() && i<pageNumber*itemsPerPage;i++) {
                Material tmpMat = materialy.get(i);
                System.out.format(tableMaterialFormat, i, tmpMat.getNazwa(), tmpMat.getRzadkosc(), tmpMat.getLvl(), tmpMat.getMaksOfertaKupna(), 
                    tmpMat.getMinOfertaSprzed(), tmpMat.getDostepnaIlosc(), tmpMat.getZapotrzebowanie());
            }
            pageNumber++;
            if (i>=materialy.size()) {
            	System.out.println("+=============================================================================================================================================+");
            	System.out.print("\n\n");
                System.out.println("----- KONIEC TABELI -----");
            }
            else {
            	System.out.print("\n\n");
                System.out.println("Wciœnij ENTER, ¿eby wyœwietliæ dalsz¹ czêœæ tabeli...");
                scanner.nextLine();
            }
        }         
    }
    
    // funkcja czyszczaca dane z tablicy materialy
    private void wyczyscTabliceMaterialy() {
    	database.clearTableMaterial();
    	materialy=new ArrayList<Material>();
		System.out.println("Dane zosta³y wyczyszczone.");
    }
}
