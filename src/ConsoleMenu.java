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
			case 4: edytujTabliceMaterialy();
        		break;
			case 5: wyczyscTabliceMaterialy();
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
		System.out.println("MENU GLOWNE");
		System.out.println("===========");
		System.out.println("  1. Wczytaj dane z pliku");
		System.out.println("  2. Wyswetl tabele materialow");
		System.out.println("  3. Znajdz material");
		System.out.println("  4. Edytuj tabele materialow");
		System.out.println("  5. Wyczysc dane z tablicy materialow");
		System.out.println("  0. Wyjdz z programu");
		System.out.println(" ");
		System.out.print("Wybierz: ");
	}
	
	// funkcja wczytujaca dane z pliku csv do bazy
	private void wczytajRekordyCSV() {
		System.out.println("Trwa wczytywanie z pliku... To może chwile potrwać.");
        materialy=csvFReader.readFromCSV(CSV_FILE);
        database.bigInsertMaterial(materialy);
		System.out.println("Wczytywanie zakończone pomyślnie.");
		System.out.print("\n\n");
	}
	
	// funkcja wyswietlajaca glowna tabele materialy
    private void wypiszRekordyMaterialy() {
        materialy=database.getMateral();
        wyswietlTablice(materialy);
        System.out.print("\n\n");
    }
    
    // menu i funkcje znajdz przedmiot
    private void znajdzMaterial() {
		int wybor=-1;
		do {
			System.out.println("MENU GLOWNE // ZNAJDZ PRZEDMIOT");
			System.out.println("===============================");
			System.out.println("  1. Znajdz po ID");
			System.out.println("  2. Znajdz po nazwie");
			System.out.println("  0. Wroc do menu glownego");
			System.out.println(" ");
			System.out.print("Wybierz: ");
			try {
				wybor = scanner.nextInt();		
			} catch (Exception e) {
			    scanner.nextLine();
			} 
			switch(wybor) {
			case 1: znajdzMaterialId();
				break;
			case 2: znajdzMaterialNazwa();
				break;
			case 0: 
				break;
			default: break;
			}
		} while(wybor!=0);
    }
    
    // funkcja znajdujaca przedmiot po id
    private void znajdzMaterialId() {
    	System.out.println("Wpisz ID poszukiwanego przedmiotu:");
    	int search = inputInt();
    	materialy=database.getMateral("id = '" + search + "'");
        wyswietlTablice(materialy);
        System.out.print("\n\n");
    }
    
    // funkcja znajdujaca przedmiot po nazwie
    private void znajdzMaterialNazwa() {
    	System.out.println("Wpisz nazwe poszukiwanego przedmiotu:");
    	scanner.nextLine(); // czyszczenie buffera
    	String search = scanner.nextLine();
    	materialy=database.getMateral("nazwa = '" + search + "'");
        wyswietlTablice(materialy);
        System.out.print("\n\n");
    }
    
    // menu i funkcje edytuj tablice materialy
    private void edytujTabliceMaterialy() {
		int wybor=-1;
		do {
			System.out.println("MENU GLOWNE // EDYTUJ TABELE MATERIALOW");
			System.out.println("=======================================");
			System.out.println("  1. Dodaj nowy material");
			System.out.println("  2. Edytuj istniejacy material");
			System.out.println("  3. Usun istniejacy material");
			System.out.println("  0. Wroc do menu glownego");
			System.out.println(" ");
			System.out.print("Wybierz: ");
			try {
				wybor = scanner.nextInt();		
			} catch (Exception e) {
			    scanner.nextLine();
			} 
			switch(wybor) {
			case 1: edytujTabliceMaterialyDodaj();
				break;
			case 2: edytujTabliceMaterialyEdytuj();
				break;
			case 3: edytujTabliceMaterialyUsun();
				break;
			case 0: 
				break;
			default: break;
			}
		} while(wybor!=0);
    }
    
    private void edytujTabliceMaterialyDodaj() {
    	String tekst;
    	int liczba;
    	Material nowymaterial = new Material();
    	scanner.nextLine();
    	System.out.println("Podaj nazwe nowego przedmiotu:");
    	tekst = scanner.nextLine();
    	nowymaterial.setNazwa(tekst);
    	System.out.println("Tabela pomocnicza:");
    	System.out.println(" 1 = Basic");
    	System.out.println(" 2 = Fine");
    	System.out.println(" 3 = Masterwork");
    	System.out.println(" 4 = Rare");
    	System.out.println(" 5 = Exotic");
    	System.out.println(" 6 = Ascended");
    	System.out.println(" 7 = Legendary");
    	System.out.println(" Inna wartosc = Junk");
    	System.out.print("Podaj wartość rzadkosc w formie cyfry(skorzystaj z tabeli pomocniczej powyzej): ");
    	liczba=inputInt();
    	tekst = csvFReader.rarityFromIntToString(liczba);
    	nowymaterial.setRzadkosc(tekst);
    	System.out.println("Podaj poziom wymagany dla przedmiotu:");
    	liczba=inputInt();
    	nowymaterial.setLvl(liczba);
    	System.out.println("Podaj maksymalna oferte kupna przedmiotu:");
    	liczba=inputInt();
    	nowymaterial.setMaksOfertaKupna(liczba);
    	System.out.println("Podaj minimalna oferte sprzedazy przedmiotu:");
    	liczba=inputInt();
    	nowymaterial.setMinOfertaSprzed(liczba);
    	System.out.println("Podaj dostepna ilosc przedmiotu:");
    	liczba=inputInt();
    	nowymaterial.setDostepnaIlosc(liczba);
    	System.out.println("Podaj zapotrzebowanie na przedmiot:");
    	liczba=inputInt();
    	nowymaterial.setZapotrzebowanie(liczba);
    	database.insertMaterial(nowymaterial);
    	System.out.println("Przedmiot zostal pomyslnie dodany do bazy!");
    }
    
    private void edytujTabliceMaterialyEdytuj() {
    	int liczba;
    	System.out.println("Wpisz ID przedmiotu do edycji:");
    	int search = inputInt();
		int wybor=-1;
		materialy=database.getMateral("id = '" + search + "'");
		if(materialy.size()==0){
			System.out.println("Nie znaleziono przedmiotu.");
			System.out.print("\n\n");
			return;
		}
		do {
			System.out.println("MENU GLOWNE // EDYTUJ TABELE MATERIALOW // EDYTUJ ISTNIEJACY MATERIAL");
			System.out.println("=====================================================================");
			System.out.println("Wybierz kolumny, ktorych wartosc chcesz zmienic.");
			System.out.println("Pamietaj, zeby po wprowadzeniu zmian do przedmiotu zapisac calosc do bazy(Opcja 8 lub 9) inaczej wprowadzone informacje zostana utracone!");
			System.out.println("  1. Nazwe przedmiotu");
			System.out.println("  2. Rzadkosc przedmiotu");
			System.out.println("  3. Wymagany poziom przedmiotu");
			System.out.println("  4. Maksymalna oferte kupna przedmiotu");
			System.out.println("  5. Minimalna oferte kupna przedmiotu");
			System.out.println("  6. Dostepna ilosc przedmiotu");
			System.out.println("  7. Zapotrzebowanie na przedmiot");
			System.out.println(" ");
			System.out.println("  8. Zapisz edycje przedmiotu");
			System.out.println("  9. Zakoncz i zapisz edycje przedmiotu");
			System.out.println("  0. Anuluj edycje przedmiotu");
			wyswietlTablice(materialy);
			System.out.println(" ");
			System.out.print("Wybierz: ");
			try {
				wybor = scanner.nextInt();		
			} catch (Exception e) {
			    scanner.nextLine();
			} 
			switch(wybor) {
			case 1: System.out.println("Wprowadz nowa nazwe przedmiotu:");
				materialy.get(0).setNazwa(scanner.nextLine());
				break;
			case 2: System.out.println("Tabela pomocnicza:");
		    	System.out.println(" 1 = Basic");
		    	System.out.println(" 2 = Fine");
		    	System.out.println(" 3 = Masterwork");
		    	System.out.println(" 4 = Rare");
		    	System.out.println(" 5 = Exotic");
		    	System.out.println(" 6 = Ascended");
		    	System.out.println(" 7 = Legendary");
		    	System.out.println(" Inna wartosc = Junk");
		    	System.out.print("Wprowadz nowa wartość rzadkosc w formie cyfry(skorzystaj z tabeli pomocniczej powyzej): ");
				liczba=inputInt();
				materialy.get(0).setRzadkosc(csvFReader.rarityFromIntToString(liczba));
				break;
			case 3: System.out.println("Wprowadz nowy poziom wymagany przedmiotu:");
				materialy.get(0).setLvl(inputInt());
				break;
			case 4: System.out.println("Wprowadz nowa maksymalna oferte kupna przedmiotu:");
				materialy.get(0).setMaksOfertaKupna(inputInt());
				break;
			case 5: System.out.println("Wprowadz nowa minimalna oferte sprzedazy przedmiotu:");
				materialy.get(0).setMinOfertaSprzed(inputInt());
				break;
			case 6: System.out.println("Wprowadz nowa dostepna ilosc przedmiotu:");
				materialy.get(0).setDostepnaIlosc(inputInt());
				break;
			case 7: System.out.println("Wprowadz nowe zapotrzebowanie dla przedmiotu:");
				materialy.get(0).setZapotrzebowanie(inputInt());
				break;
			case 8:	database.updateMaterial(materialy.get(0));
				materialy=database.getMateral("id = '" + search + "'");
				System.out.println("Edycja zapisana.");
				break;
			case 9: database.updateMaterial(materialy.get(0));
				System.out.println("Edycja zapisana i zakonczona pomyslnie.");
				break;
			case 0: System.out.println("Edycja zakonczona.");
				break;
			default: break;
			}
			System.out.print("\n\n");
		} while(wybor!=0 && wybor!=9);
    }
    
    private void edytujTabliceMaterialyUsun() {
    	int liczba;
    	System.out.println("Podaj ID przedmiotu do usuniecia:");
    	liczba=inputInt();
    	database.deleteMaterial(liczba);
    }
    
    // wprowadzenie liczby, które zwraca wartosc 0 jeżeli podało się nieoczekiwana wartosc
    private int inputInt() {
    	int liczba;
    	try {
    		liczba = scanner.nextInt();
    	} catch (Exception e) {
    		scanner.nextLine();
    		return liczba=0;
    	}
    	return liczba;
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
            }
            else {
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
		System.out.print("\n\n");
    }
}
