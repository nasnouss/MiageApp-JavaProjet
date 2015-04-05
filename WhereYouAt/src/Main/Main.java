package Main;

public class Main {

	public static void main(String[] args) {
			Menu m = new Menu();
			try {
				m.Start();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
}
// Il faut regler plusieurs pb : 
// Il faut que le Graphe soit bien partager dans le Menu lorsque on lance d'autres traceroute a la volee
// Il faut ensuite commenter toutes les classes et refactoriser si possible la classe Menu et la classe TraceRouteProducteur

