package ch.cpnv.servlets;

public class BeanTest {
	public class MonBean{
		private String Nom;
		private int Diff;
		
		public String getNom() {
			return this.Nom;
		}

		public int getDiff() {
			return this.Diff;
		}

		public void setNom( String Nom ) {
			this.Nom = Nom;
		}

		public void setDiff( int Diff ) {
			this.Diff = Diff;
		}
	}
}
