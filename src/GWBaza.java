import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class GWBaza {
	
    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String DB_URL = "jdbc:sqlite:Baza.db";
    
    // tworzenie tabel
    public GWBaza() {
        createTableMaterial();
    }
    
	// funkcja zwracajaca liste materialow
	public List<Material> getMateral() {
		List<Material> materials = new ArrayList<Material>();
		
		// polaczenie z bazy
        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            // wpisywanie materialow do listy
            String query = "SELECT id, nazwa, rzadkosc, lvl, maks_oferta_kupna, min_oferta_sprzed, dostepna_ilosc, zapotrzebowanie FROM material";
            ResultSet rows = stmt.executeQuery(query);
            
            while(rows.next()) {
            	materials.add(new Material(
            			rows.getInt("id"),
            			rows.getString("nazwa"),
            			rows.getString("rzadkosc"),
            			rows.getInt("lvl"),
            			rows.getInt("maks_oferta_kupna"),
            			rows.getInt("min_oferta_sprzed"),
            			rows.getInt("dostepna_ilosc"),
            			rows.getInt("zapotrzebowanie")));
            }
            
            stmt.close();
            conn.close();
        }  catch (Exception ex) {
        	ex.printStackTrace();
        }
        return materials;
	}
	
	public List<Material> getMateral(String params) {
        List<Material> materials = new ArrayList<Material>();
        
        // polaczenie z bazy
        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            // wpisywanie materialow do listy
            String query = "SELECT id, nazwa, rzadkosc, lvl, maks_oferta_kupna, min_oferta_sprzed, dostepna_ilosc, zapotrzebowanie FROM material WHERE " + params;
            ResultSet rows = stmt.executeQuery(query);
            
            while(rows.next()) {
                materials.add(new Material(
                        rows.getInt("id"),
                        rows.getString("nazwa"),
                        rows.getString("rzadkosc"),
                        rows.getInt("lvl"),
                        rows.getInt("maks_oferta_kupna"),
                        rows.getInt("min_oferta_sprzed"),
                        rows.getInt("dostepna_ilosc"),
                        rows.getInt("zapotrzebowanie")));
            }
            
            stmt.close();
            conn.close();
        }  catch (Exception ex) {
            ex.printStackTrace();
        }
        return materials;
    }
	
	// wrzucanie material do bazy
	public void insertMaterial(Material material) {
        String command = String.format("INSERT INTO material(nazwa, rzadkosc, lvl, maks_oferta_kupna, min_oferta_sprzed, dostepna_ilosc, zapotrzebowanie) " +
        		"VALUES('%s', '%s', %d, %d, %d, %d, %d)",
        		material.getNazwa(), material.getRzadkosc(), material.getLvl(), 
        		material.getMaksOfertaKupna(), material.getMinOfertaSprzed(), 
        		material.getDostepnaIlosc(), material.getZapotrzebowanie());
		executeCommand(command);
	}
	
	// wrzucanie materialow za jednym zamachem
	// przy duzej ilosci danych moze troche potrwac
    public void bigInsertMaterial(List<Material> materialy) {
        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            Material material;
            for (int i =0 ;i<materialy.size();i++) {
                material=materialy.get(i);
                String command = String.format("INSERT INTO material(nazwa, rzadkosc, lvl, maks_oferta_kupna, min_oferta_sprzed, dostepna_ilosc, zapotrzebowanie) " +
                    "VALUES('%s', '%s', %d, %d, %d, %d, %d)",
                    material.getNazwa(), material.getRzadkosc(), material.getLvl(), 
                    material.getMaksOfertaKupna(), material.getMinOfertaSprzed(), 
                    material.getDostepnaIlosc(), material.getZapotrzebowanie());
                stmt.executeUpdate(command);
            }
            stmt.close();
            conn.close();
        }  catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	
	// update materialow
	public void updateMaterial(Material material) {
        String command = String.format("UPDATE material SET nazwa='%s', rzadkosc='%s', lvl=%d, maks_oferta_kupna=%d, " +
        		"min_oferta_sprzed=%d, dostepna_ilosc=%d, zapotrzebowanie=%d " +
        		"WHERE id=%d",
        		material.getNazwa(), material.getRzadkosc(), material.getLvl(), material.getMaksOfertaKupna(),
        		material.getMinOfertaSprzed(), material.getDostepnaIlosc(), material.getZapotrzebowanie(),
        		material.getId());
		executeCommand(command);
	}
	
	// usuwanie materialow
	public void deleteMaterial(Integer materialId) {
        String command = String.format("DELETE FROM material WHERE id = %d", materialId);
		executeCommand(command);
	}
	
	// tworzenie tabeli materialy
	public void createTableMaterial() {
        String command = String.format("CREATE TABLE IF NOT EXISTS material(id INTEGER PRIMARY KEY AUTOINCREMENT, nazwa VARCHAR(50), rzadkosc VARCHAR(12), lvl INTEGER, maks_oferta_kupna INTEGER, min_oferta_sprzed INTEGER, dostepna_ilosc INTEGER, zapotrzebowanie INTEGER)");
		executeCommand(command);
	}
	
	// czyszczenie tabeli material
    public void clearTableMaterial() {
        String command = String.format("DELETE FROM material");
        executeCommand(command);
    }
	
    // funkcja wywolujaca zapytanie sql
	private void executeCommand(String command) {
        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(command);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }  catch (Exception ex) {
            ex.printStackTrace();
        }
	}
}
