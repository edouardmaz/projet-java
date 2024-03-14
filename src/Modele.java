import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class Modele {

	Map<String, Map> repertoireMus = new HashMap<>();

	public class Musique {
		String titre;
		int dureem;
		int dureesec;
		int avis;
		String descr;

		public Musique(String t, int dm, int ds, int a, String de) {
			this.titre = t;
			this.dureem = dm;
			this.dureesec = ds;
			this.avis = a;
			this.descr = de;
		}

		public String toString() {
			return this.titre;
		}
	}

	public Modele(Musique[] ms) {

	}

	public void ajout(Map r, Musique m, int p) { //creation de table de table
		Map<Character, Musique> rep;
		if (m.titre.length() != p + 1) {
		}else {
			
		}
			Character az = m.titre.charAt(p);
			if (r.containsKey(az) == false) {
				rep = new HashMap<>();
				r.put(az, rep);
			} else {
				rep = (Map<Character, Modele.Musique>) r.get(az);
			}
			ajout(rep, m, p++);
	}

}
