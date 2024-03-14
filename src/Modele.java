
public class Modele {
	
	Musique[] listeMus;
	
	public class Musique{
		String titre;
		int dureem;
		int dureesec;
		int avis;
		String descr;
		
		public Musique(String t,int dm,int ds,int a,String de) {
			this.titre=t;
			this.dureem=dm;
			this.dureesec=ds;
			this.avis=a;
			this.descr=de;
		}
	}
	
	public Modele(Musique[] ms) {
		this.listeMus=ms;
	}

}
