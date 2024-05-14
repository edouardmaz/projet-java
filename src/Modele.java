import java.util.Dictionary;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.io.FileWriter;
import java.util.PriorityQueue;

public class Modele implements Serializable {
	private static final long serialVersionUID = 1L;

	User user = new User();
	File fic = new File("user.dat");
	Map<String, Map> repertoireMus = new HashMap<>();
	Map<String, ArrayList> repertoireart = new HashMap<>();
	Map<Genre, ArrayList> repertoiregenre = new HashMap<>();
	Map<Annee, ArrayList> repertoireannée = new HashMap<>();
	Map<Langue, ArrayList> repertoirelangue = new HashMap<>();

	static enum Genre {
		pref, kpop, pop, rap, rap_fr
	};

	static enum Langue {
		pref, anglais, français, korean
	};

	static enum Annee {
		pref, a80, a90, a00, a10
	};

	public class Musique implements Serializable, Comparable<Musique> {
		private static final long serialVersionUID = 1L;

		int perti = 0;
		String titre;
		String artist;
		Genre genre;
		Langue langue;
		Annee annee;
		Integer duree;
		Integer avis;

		public Musique(String t, String a, Genre g, Langue l, Annee an, Integer d) {
			this.titre = t;
			this.artist = a;
			this.genre = g;
			this.annee = an;
			this.langue = l;
			this.duree = d;
			this.avis = 0;
		}

		public String toString() {
			return this.titre + " - " + this.artist;
		}

		@Override
		public int compareTo(Modele.Musique m) {
			if (this.perti != m.perti) {
				return  m.perti-this.perti ;
			} else {
				return  m.avis-this.avis;
			}
		}
	}

	public class User implements Serializable {
		private static final long serialVersionUID = 1L;
		Map<Genre, Object> prefGenre = new HashMap();
		Map<Annee, Object> prefAnnée = new HashMap();
		Map<Langue, Object> prefLangue = new HashMap();
		Map<String, Object> prefArtistes = new HashMap();
		/*
		 * Stack<Genre> prefGenres = new Stack<Genre>(); Stack<Annee> prefAnnees = new
		 * Stack<Annee>(); Stack<Langue> prefLangues = new Stack<Langue>();
		 * Stack<String> prefArtistes = new Stack<String>();
		 */

		public User() {
			/*
			 * public User(Stack<Genre> g, Stack<Annee> a, Stack<Langue> l, Stack<String>
			 * ar) { this.prefGenres=g; this.prefAnnees=a; this.prefLangues=l;
			 * this.prefArtistes=ar; }
			 */
		}
		/*
		 * public void like(Musique m) { this.prefAnnees.add(m.annee);
		 * this.prefGenres.add(m.genre); this.prefLangues.add(m.langue);
		 * this.prefArtistes.add(m.artist); }
		 */

		// genre
		public void statGenre(Genre g) { // s'occupe de mettre à jour la preférence année
			Map r = this.prefGenre;
			if (r.containsKey(g)) {
				int s = (int) r.get(g);
				r.replace(g, s + 1);
				if ((int) r.get(g) > (int) r.get(r.get(Genre.pref))) {
					r.replace(Genre.pref, g);
				}
			} else {
				r.put(g, 1);
				if (r.containsKey(Genre.pref) == false) {
					r.put(Genre.pref, g);
				}
			}
		}

		public Genre likeGenre() { // renvoi l'année favorite
			Genre fav = null;
			if (this.prefGenre.containsKey(Genre.pref)) {
				fav = (Modele.Genre) this.prefGenre.get(Genre.pref);
			}
			return fav;
		}

		// annee
		public void statAnnée(Annee an) { // s'occupe de mettre à jour la preférence année
			Map r = this.prefAnnée;
			if (r.containsKey(an)) {
				int s = (int) r.get(an);
				r.replace(an, s + 1);
				if ((int) r.get(an) > (int) r.get(r.get(Annee.pref))) {
					r.replace(Annee.pref, an);
				}
			} else {
				r.put(an, 1);
				if (r.containsKey(Annee.pref) == false) {
					r.put(Annee.pref, an);
				}
			}
		}

		public Annee likeAnn() { // renvoi l'année favorite
			Annee fav = null;
			if (this.prefAnnée.containsKey(Annee.pref)) {
				fav = (Modele.Annee) this.prefAnnée.get(Annee.pref);
			}
			return fav;
		}

		// Langue
		public void statLangue(Langue l) { // s'occupe de mettre à jour la preférence année
			Map r = this.prefLangue;
			if (r.containsKey(l)) {
				int s = (int) r.get(l);
				r.replace(l, s + 1);
				if ((int) r.get(l) > (int) r.get(r.get(Langue.pref))) {
					r.replace(Langue.pref, l);
				}
			} else {
				r.put(l, 1);
				if (r.containsKey(Langue.pref) == false) {
					r.put(Langue.pref, l);
				}
			}
		}

		public Langue likelan() { // renvoi l'année favorite
			Langue fav = null;
			if (this.prefLangue.containsKey(Langue.pref)) {
				fav = (Modele.Langue) this.prefLangue.get(Langue.pref);
			}
			return fav;
		}

		// artiste
		public void statArtiste(String ar) { // s'occupe de mettre à jour la preférence année
			Map r = this.prefArtistes;
			if (r.containsKey(ar)) {
				int s = (int) r.get(ar);
				r.replace(ar, s + 1);
				if ((int) r.get(ar) > (int) r.get(r.get("pref"))) {
					r.replace("pref", ar);
				}
			} else {
				r.put(ar, 1);
				if (r.containsKey("pref") == false) {
					r.put("pref", ar);
				}
			}
		}

		public String likeart() { // renvoi l'année favorite
			String fav = null;
			if (this.prefArtistes.containsKey("pref")) {
				fav = (String) this.prefArtistes.get("pref");
			}
			return fav;
		}

	}

	public Modele() {
		if (fic.exists()) {

			try {
				FileInputStream fis = new FileInputStream(fic);
				ObjectInputStream ois = new ObjectInputStream(fis);

				this.user = (Modele.User) ois.readObject();

				ois.close();
				fis.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("Impossible de récupérer les données");
			}
		}
	}

	public void save() {
		try {
			this.fic.createNewFile();
			FileOutputStream fos = new FileOutputStream(fic);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(this.user);

			oos.close();
			fos.close();
		} catch (IOException e) {
			throw new RuntimeException("Impossible de sauvegarder");
		}
	}

	public void ajout(Map r, Musique m, int p) { // creation de table de table
		Map<String, Musique> rep = null;
		if (m.titre.length() == p) {
			ArrayList<Musique> ml = new ArrayList<Musique>(); // ajouter cas ou musique existe deja
			if (r.containsKey("list")) {
				ml = (ArrayList) r.get("list");
				ml.add(m);
				r.replace("list", ml);
			} else {
				ml.add(m);
				r.put("list", ml);
			}
			if (this.repertoirelangue.containsKey(m.langue)) {
				ArrayList al = (ArrayList) this.repertoirelangue.get(m.langue);
				al.add(m);
				this.repertoirelangue.replace(m.langue, al);
			} else {
				ArrayList<Musique> al = new ArrayList<Musique>();
				al.add(m);
				this.repertoirelangue.put(m.langue, al);
			}
			if (this.repertoireannée.containsKey(m.annee)) {
				ArrayList al = (ArrayList) this.repertoireannée.get(m.annee);
				al.add(m);
				this.repertoireannée.replace(m.annee, al);
			} else {
				ArrayList<Musique> al = new ArrayList<Musique>();
				al.add(m);
				this.repertoireannée.put(m.annee, al);
			}
			if (this.repertoiregenre.containsKey(m.genre)) {
				ArrayList al = (ArrayList) this.repertoiregenre.get(m.genre);
				al.add(m);
				this.repertoiregenre.replace(m.genre, al);
			} else {
				ArrayList<Musique> al = new ArrayList<Musique>();
				al.add(m);
				this.repertoiregenre.put(m.genre, al);
			}
			if (this.repertoireart.containsKey(m.artist)) {
				ArrayList al = (ArrayList) this.repertoireart.get(m.artist);
				al.add(m);
				this.repertoireart.replace(m.artist, al);
			} else {
				ArrayList<Musique> al = new ArrayList<Musique>();
				al.add(m);
				this.repertoireart.put(m.artist, al);
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

	public Object[] getPref() {
		Genre pg = user.likeGenre();
		Annee pan = user.likeAnn();
		Langue pl = user.likelan();
		String pat = user.likeart();
		Object[] pref = { pg, pan, pl, pat };
		return pref;
	}

	public PriorityQueue preference() {
		Object[] userLike = this.getPref();
		ArrayList base = this.trouveAll();
		return this.sort(base, userLike);
		/*
		 * this.prefGenres=g; this.prefAnnees=a; this.prefLangues=l;
		 * this.prefArtistes=ar;
		 */
	}

	public ArrayList trouve(Map r, String titre, int p) {
		ArrayList find = new ArrayList();
		find.add("musique non trouvée");
		if (titre.length() == p) {
			// find = (ArrayList) r.get("list");
			find = trouveAll(r);
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

	public PriorityQueue sort(ArrayList base, Object[] userLike) {
		PriorityQueue fp = new PriorityQueue();
		ArrayList pot;
		if (base.size() != 0) {
			pot = base;
		} else {
			pot = this.trouveAll();
		}
		for (int reset = 0; reset < pot.size(); reset++) {
			Musique m = (Modele.Musique) pot.get(reset);
			m.perti = 0;
			pot.set(reset, m);
		}
		for (int i = 0; i < userLike.length; i++) {
			if (pot.size() == 0 && userLike[i] != null) { // I want to fucking die
				for (int j = 0; j < userLike.length; j++) {
					Musique m = (Modele.Musique) pot.get(j);
					// add case switch
					switch (i) {
					case 0:
						if (m.genre == userLike[i]) {
							m.perti++;
							pot.set(j, m);
						}
						break;
					case 1:
						if (m.annee == userLike[i]) {
							m.perti++;
							pot.set(j, m);
						}
						break;
					case 2:
						if (m.langue == userLike[i]) {
							m.perti++;
							pot.set(j, m);
						}
						break;
					case 3:
						if (m.artist == userLike[i]) {
							m.perti++;
							pot.set(j, m);
						}
						break;
					}
				}
			} else if (pot.size() != 0 && userLike[i] != null) {
				for (int j = 0; j < pot.size(); j++) {
					Musique m = (Modele.Musique) pot.get(j);
					switch (i) {
					case 0:
						if (m.genre == userLike[i]) {
							m.perti++;
							pot.set(j, m);
						}
						break;
					case 1:
						if (m.annee == userLike[i]) {
							m.perti++;
							pot.set(j, m);
						}
						break;
					case 2:
						if (m.langue == userLike[i]) {
							m.perti++;
							pot.set(j, m);
						}
						break;
					case 3:
						if (m.artist == userLike[i]) {
							m.perti++;
							pot.set(j, m);
						}
						break;
					}
				}
			}
		}
		fp.addAll(pot);
		return fp;

	}

	public ArrayList filter(ArrayList base, Object[] userLike) {
		ArrayList filtre = new ArrayList();
		ArrayList pot;
		if (base.size() != 0) {
			pot = base;
		} else {
			pot = this.trouveAll();
		}
		for (int i = 0; i < userLike.length; i++) {
			if (pot.size() == 0 && userLike[i] != null) { // I want to fucking die
				// add case switch
				switch(i) {
				case 0:
					pot=this.repertoiregenre.get(userLike[i]);
				break;
				case 1:
					pot = this.repertoireannée.get(userLike[i]);
				break;
				case 2:
					pot = this.repertoirelangue.get(userLike[i]);
					break;
				case 3:
					pot = this.repertoireart.get(userLike[i]);
					break;
				}
			} else if (pot.size() != 0 && userLike[i] != null) {
				// add case switch
				int j = 0;
				while (j != pot.size()) { 
					Musique m = (Modele.Musique) pot.get(j);
					switch(i) {
					case 0:
						if (m.genre == userLike[i]) { 
							j++;
						} else {
							pot.remove(j);
						}
					break;
					case 1:
						if (m.annee == userLike[i]) {
							j++;
						} else {
							pot.remove(j);
						}
					break;
					case 2:
						if (m.langue == userLike[i]) {
							j++;
						} else {
							pot.remove(j);
						}
						break;
					case 3:
						if (m.artist == userLike[i]) {
							j++;
						} else {
							pot.remove(j);
						}
						break;
					}
				}
			}
		}filtre.addAll(pot);return filtre;

	}

	public ArrayList trouve(String titre) {
		// Musique test=trouve(this.repertoireMus,titre,0);
		// System.out.println(test);
		return trouve(this.repertoireMus, titre, 0);
	}

	public ArrayList trouveAll(Map r, String alpha, ArrayList find) {// renvoi une liste contenant toutes les musique du
																		// répertoire selectionner
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
		return trouveAll(this.repertoireMus, "abcdefghijklmnopqrstuvwxyz", find);
	}

	public ArrayList trouveAll(Map r) {
		ArrayList find = new ArrayList();
		return trouveAll(r, "abcdefghijklmnopqrstuvwxyz", find);
	}

	public void makeBDD() throws IOException {
		FileReader in = new FileReader("musique");
		try (BufferedReader br = new BufferedReader(in)) {
			String bdd = br.readLine();
			String[] musbd = bdd.split(";");
			for (int i = 0; i < musbd.length; i++) {
				String[] mu = musbd[i].split(",");
				int m1 = mu[1].codePointAt(0) - 48;
				int m2 = (mu[2].codePointAt(0) - 48) * 10 + mu[2].codePointAt(1) - 48;
				int m3 = mu[3].codePointAt(0) - 48;
				// Musique a = new Musique(mu[0], m1, m2, m3, mu[4]);
				// ajout(a);
			}
		}

	}

	public void MaJ(Musique m) throws IOException {
		String update = m.titre + "," + m.duree + "," + m.avis;
		FileReader in = new FileReader("musique");
		try (BufferedReader br = new BufferedReader(in)) {
			String bdd = br.readLine();
			if (bdd != null) {
				String[] ver = bdd.split(";");
				int iv = 0;
				boolean notin = true;
				while (iv < ver.length && notin) {
					if (ver[iv].equals(update)) {
						notin = false;
					}
					iv++;
				}
				if (notin) {
					bdd = bdd + update + ";";
				}
			} else {
				bdd = update + ";";
			}
			try {
				FileWriter out = new FileWriter("musique");
				for (int i = 0; i < bdd.length(); i++)
					out.write(bdd.charAt(i));
				out.close();
			} catch (Exception e) {
			}
		}
		ajout(m);
	}

	public void testlisten(Musique m) {
		this.user.statGenre(m.genre);
		this.user.statAnnée(m.annee);
		this.user.statLangue(m.langue);
		this.user.statArtiste(m.artist);
	}

	public static void main(String[] args) throws IOException {
		Modele m = new Modele();
		m.makeBDD();
		Musique hee = m.new Musique("test", "Twewy", Genre.rap, Langue.anglais, Annee.a00, 180);
		Musique dhee = m.new Musique("dtest", "Twewy", Genre.pop, Langue.anglais, Annee.a00, 180);
		m.ajout(hee);
		m.ajout(dhee);
		System.out.println(m.trouveAll());
		System.out.println(m.trouve("te"));
		Object[] tfil = { Genre.pop, null, null, null, null };
		Object[] tsor = { Genre.rap, null, null, null, null };
		Object[] t2fil = { null, Annee.a00, null, null, null };
		System.out.println(m.filter(m.trouveAll(), tfil));
		System.out.println(m.sort(m.trouveAll(), tsor));
		System.out.println(m.filter(m.trouve("te"), tfil));
		System.out.println(m.filter(m.trouveAll(), t2fil));
		System.out.println(m.preference());

		//m.testlisten(hee);
		m.save();
	}

}
