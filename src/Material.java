
public class Material {
	
	private int id;
	private String nazwa;
	private String rzadkosc;
	private int lvl;
	private int maksOfertaKupna;
	private int minOfertaSprzed;
	private int dostepnaIlosc;
	private int zapotrzebowanie;
	
	public Material(){};
	
	public Material(int id, String nazwa, String rzadkosc, int lvl, int maksOfertaKupna, int minOfertaSprzed, int dostepnaIlosc, int zapotrzebowanie) {
		this.id=id;
		this.nazwa=nazwa;
		this.rzadkosc=rzadkosc;
		this.lvl=lvl;
		this.maksOfertaKupna=maksOfertaKupna;
		this.minOfertaSprzed=minOfertaSprzed;
		this.dostepnaIlosc=dostepnaIlosc;
		this.zapotrzebowanie=zapotrzebowanie;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public String getRzadkosc() {
		return rzadkosc;
	}
	public void setRzadkosc(String rzadkosc) {
		this.rzadkosc = rzadkosc;
	}
	public int getLvl() {
		return lvl;
	}
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
	public int getMaksOfertaKupna() {
		return maksOfertaKupna;
	}
	public void setMaksOfertaKupna(int maksOfertaKupna) {
		this.maksOfertaKupna = maksOfertaKupna;
	}
	public int getMinOfertaSprzed() {
		return minOfertaSprzed;
	}
	public void setMinOfertaSprzed(int minOfertaSprzed) {
		this.minOfertaSprzed = minOfertaSprzed;
	}
	public int getDostepnaIlosc() {
		return dostepnaIlosc;
	}
	public void setDostepnaIlosc(int dostepnaIlosc) {
		this.dostepnaIlosc = dostepnaIlosc;
	}
	public int getZapotrzebowanie() {
		return zapotrzebowanie;
	}
	public void setZapotrzebowanie(int zapotrzebowanie) {
		this.zapotrzebowanie = zapotrzebowanie;
	}
	
}
