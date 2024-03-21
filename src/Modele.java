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

		public Musique(String t, Object object, Object object2, Object object3, Object de) {
			// TODO Auto-generated constructor stub
		}

		public String toString() {
			return this.titre+" "+this.dureem+":"+this.dureesec;
		}
	}

	public Modele() {
		
	}
	
	public Modele(Musique[] ms) {
		
	}

	public void ajout(Map r, Musique m, int p) { //creation de table de table
		Map<String, Musique> rep = null;
		if (m.titre.length() == p) {
			r.put("list", m);
		}else {			
			Character az = m.titre.charAt(p);
			if (r.containsKey(az) == false) {
				rep = new HashMap<>();
				r.put(az, rep);
			} else {
				rep = (Map<String, Modele.Musique>) r.get(az);
			}
			ajout(rep, m, p+1);
		}
	}
	
	public Musique trouve(Map r, String titre, int p) {
		Musique find = new Musique("musique non trouvé",null,null,null,null);;
		if (titre.length() == p) {
			find=(Modele.Musique) r.get("list");
		}else {
			Character az = titre.charAt(p);
			if (r.containsKey(az)){
				find=trouve((Map) r.get(az),titre,p+1);
			}
		}
		return find;
	}
	
	public void ajout(Musique m) {
		ajout(this.repertoireMus,m,0);
	}
	
	public Musique trouve(String titre) {
		//Musique test=trouve(this.repertoireMus,titre,0);
		//System.out.println(test);
		return trouve(this.repertoireMus,titre,0);
	}
	
	public static void main(String[] args) {
		Modele m=new Modele();
		Musique ab=m.new Musique("ab",1,11,4,"non");
		//System.out.println(ab);
		m.ajout(ab);
		System.out.println(m.trouve("ab"));
	}

}
