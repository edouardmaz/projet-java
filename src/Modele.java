import java.util.Dictionary;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Modele {

	Map<String, Map> repertoireMus = new HashMap<>();

	public class Musique { // pensez à ajouter des années
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

		public Musique(String t, Object object, Object object2, Object object3, Object de) {
			// TODO Auto-generated constructor stub
		}

		public String toString() {
			return this.titre + " " + this.dureem + ":" + this.dureesec;
		}
	}

	public Modele() {

	}

	public Modele(Musique[] ms) {

	}

	public void ajout(Map r, Musique m, int p) { // creation de table de table
		Map<String, Musique> rep = null;
		if (m.titre.length() == p) {
			ArrayList<Musique> ml = new ArrayList<Musique>();
			if (r.containsKey("list")) {
				ml = (ArrayList) r.get("list");
				ml.add(m);
				r.replace("list", ml);
			} else {
				ml.add(m);
				r.put("list", ml);
			}
		} else {
			Character az = m.titre.charAt(p);
			if (r.containsKey(az) == false) {
				rep = new HashMap<>();
				r.put(az, rep);
			} else {
				rep = (Map<String, Modele.Musique>) r.get(az);
			}
			ajout(rep, m, p + 1);
		}
	}

	public ArrayList trouve(Map r, String titre, int p) {
		ArrayList find = new ArrayList();
		find.add("musique non trouvée");
		if (titre.length() == p) {
			find = (ArrayList) r.get("list");
		} else {
			Character az = titre.charAt(p);
			if (r.containsKey(az)) {
				find = trouve((Map) r.get(az), titre, p + 1);
			}
		}
		return find;
	}

	public void ajout(Musique m) {
		ajout(this.repertoireMus, m, 0);
	}

	public ArrayList trouve(String titre) {
		// Musique test=trouve(this.repertoireMus,titre,0);
		// System.out.println(test);
		return trouve(this.repertoireMus, titre, 0);
	}

	public ArrayList trouveAll(Map r, String alpha, ArrayList find) {
		if (r.containsKey("list")) {
			find.addAll((Collection) r.get("list"));
		}
		for (int i = 0; i < alpha.length(); i++) {
			Character az = alpha.charAt(i);
			if (r.containsKey(az)) {
				find = trouveAll((Map) r.get(az), alpha, find);
			}
		}
		return find;
	}

	public ArrayList trouveAll() {
		ArrayList find = new ArrayList();
		return trouveAll(this.repertoireMus,"abcdefghijklmnopqrstuvwxyz",find);
	}

	public static void main(String[] args) {
		Modele m = new Modele();
		Musique ab1 = m.new Musique("ab", 1, 11, 4, "non");
		Musique ab2 = m.new Musique("ab", 2, 25, 4, "non");
		Musique ab3 = m.new Musique("ab", 0, 11, 4, "non");
		Musique cd1 = m.new Musique("cd", 1, 55, 2, "non");
		Musique cd2 = m.new Musique("cd", 0, 59, 5, "fire");
		// System.out.println(ab);
		m.ajout(ab1);
		m.ajout(ab2);
		m.ajout(ab3);
		m.ajout(cd1);
		m.ajout(cd2);
		System.out.println(m.trouve("ab"));
		System.out.println(m.trouve("ef"));
		System.out.println(m.trouveAll());
	}

}
