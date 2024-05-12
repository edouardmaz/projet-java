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
import java.util.Stack;
import java.util.TreeSet;
import java.io.FileWriter;
import java.util.PriorityQueue;

public class Mainer implements Serializable{
	private static final long serialVersionUID = 1L;
	
	User user = new User();
	File fic = new File("user.dat");
	Map<String, Map> repertoireMus = new HashMap<>();
	Map<String, Map> repertoireart = new HashMap<>();
	Map<String, Map> repertoiregenre = new HashMap<>();
	Map<Integer, ArrayList> repertoireannée = new HashMap<>();
	static enum Genre {kpop,pop,rap,rap_fr};
	static enum Langue {anglais,français,korean};
	static enum Annee {a80,a90,a00,a10};

	public class Musique implements Serializable,Comparable<Musique>{ // pensez à ajouter caracéristique
		private static final long serialVersionUID = 1L;
		
		int prefUser;
		String titre;
		String artist;
		Genre genre;
		Langue langue;
		Annee annee;
		Integer duree;
		Integer avi;

		public Musique(String t, String a, Genre g, Langue l, Annee an, Integer d) {
			this.titre = t;
			this.artist=a;
			this.genre=g;
			this.annee=an;
			this.langue=l;
			this.duree=d;
			this.avi=0;
		}

		public String toString() {
			return this.titre+" - "+this.artist;
		}

		@Override
		public int compareTo(Modele.Musique m) {
			if (this.prefUser!=m.prefUser) {
				return this.prefUser-m.prefUser;
			}else {
				return this.avis-m.avis;
			}
		}
	}

	public class User implements Serializable {
		private static final long serialVersionUID = 1L;
		Map<Integer, Integer> prefAnnée = new HashMap();
		/*Stack<Genre> prefGenres = new Stack<Genre>();
		Stack<Annee> prefAnnees = new Stack<Annee>();
		Stack<Langue> prefLangues = new Stack<Langue>();
		Stack<String> prefArtistes = new Stack<String>();*/
		Stack<Musique> likes = new Stack<Musique>();

		public User() {
			/*public User(Stack<Musique> m, Stack<Genre> g, Stack<Annee> a, Stack<Langue> l, Stack<String> ar) {
			this.prefGenres=g;
			this.prefAnnees=a;
			this.prefLangues=l;
			this.prefArtistes=ar;
			this.likes=m;
		}*/
		}

		public void statAnnée(int an) { //s'occupe de mettre à jour la preférence année
			Map r = this.prefAnnée;
			if (r.containsKey(an)) {
				int s = (int) r.get(an);
				r.replace(an, s+1);
				if ((int) r.get(an) > (int) r.get(r.get(9901))) {
					r.replace(9901, an);
				}
			} else {
				r.put(an, 1);
				if (r.containsKey(9901) == false) {
					r.put(9901, an);
				}
			}
		}
		
		/*public void like(Musique m) {
			this.prefAnnees.add(m.annee);
			this.prefGenres.add(m.genre);
			this.prefLangues.add(m.langue);
			this.prefArtistes.add(m.artist);
		}*/

		public int likeAnn() { //renvoi l'année favorite
			int fav = -300000;
			if (this.prefAnnée.containsKey(9901)) {
				fav = this.prefAnnée.get(9901);
			}
			return fav;
		}
		
		public Set<Musique> getReccomendations() { //à améliorer avec une file à priorité
			Set<Musique> threeReccomendations = new HashSet<Musique>();
			Musique top = this.likes.firstElement();
			for (Musique m : this.likes) {
				if (m.langue==top.langue) {
					if (m.genre==top.genre) {
						if (m.artist==top.artist) {
							if (m.titre!=top.titre) {threeReccomendations.add(m);}
						} else {threeReccomendations.add(m);}
					} else {threeReccomendations.add(m);}
				}
				threeReccomendations.add(m);
			}
			return threeReccomendations;
		}
	}

	public Modele() {
		if (fic.exists()) {

			try {
				FileInputStream fis = new FileInputStream(fic);
				ObjectInputStream ois = new ObjectInputStream(fis);

				this.user = (Modele.User) is.readObject();
				
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
			ArrayList<Musique> ml = new ArrayList<Musique>();
			if (r.containsKey("list")) {
				ml = (ArrayList) r.get("list");
				ml.add(m);
				r.replace("list", ml);
			} else {
				ml.add(m);
				r.put("list", ml);
			}
			if (this.repertoireannée.containsKey(m.ann)) {
				ArrayList al=(ArrayList) this.repertoireannée.get(m.ann);
				al.add(m);
				this.repertoireannée.replace(m.ann, al);
			}else {
				ArrayList<Musique> al=new ArrayList<Musique>();
				al.add(m);
				r.put(ml, al);
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
		int pa=user.likeAnn();
		Object[] pref= {(Object) pa};
		return pref;
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
	
	public PriorityQueue preference() {
		PriorityQueue fp=new PriorityQueue();
		String[] verifpref={"thisanne"}; //pas sure de cela
		Object[] userLike=this.getPref();
		ArrayList pot=new ArrayList(); 
		for (int i=0;i<userLike.length;i++) {
			if (i==0) {
				pot=this.repertoireannée.get(i);
				for (int j=0;j<userLike.length;j++) {        //<-------utilité pas ouf
					Musique m=(Modele.Musique) pot.get(j);
					if (m.ann==(int)userLike[i]) {						
						m.prefUser++;
						pot.set(j, m);
					}
				}
			}else {
				pot=this.repertoireannée.get(i); //<-------------changer les valeur à check
				for (int j=0;j<userLike.length;j++) {
					Musique m=(Modele.Musique) pot.get(j);
					if (m.ann==(int)userLike[i]) {						
						m.prefUser++;
						pot.set(j, m);
					}
				}
			}
		}
		fp.addAll(pot);
		return fp;
		
	}

	public ArrayList trouve(String titre) {
		// Musique test=trouve(this.repertoireMus,titre,0);
		// System.out.println(test);
		return trouve(this.repertoireMus, titre, 0);
	}

	public ArrayList trouveAll(Map r, String alpha, ArrayList find) {//renvoi une liste contenant toutes les musique du répertoire selectionner
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
				Musique a = new Musique(mu[0], m1, m2, m3, mu[4]);
				ajout(a);
			}
		}

	}
	

	public void MaJ(Musique m) throws IOException {
		String update = m.titre + "," + m.dureem + "," + m.dureesec + "," + m.avis + "," + m.descr;
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
		this.user.statAnnée(m.ann);
	}

	public static void main(String[] args) throws IOException {
		Modele m = new Modele();
		m.makeBDD();
		Musique hee = m.new Musique("test", 5, 5, 2, "nope");
		System.out.println(m.trouve("ab"));
		System.out.println(m.trouve("abc"));
		System.out.println(m.trouve("ef"));
		System.out.println(m.trouveAll());
		System.out.println(m.preference());

		//m.testlisten(hee);
		m.save();
	}

}


