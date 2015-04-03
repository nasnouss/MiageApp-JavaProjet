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
//1 - Quand on lance des traceroutes et qu'il y a plusieurs lignes il faut les ignorer
// Il faut que le Graphe soit bien partager dans le Menu lorsque on lance d'autres traceroute a la volee
// Ajouter la distance entre un pt et son origine


// Il faut ensuite commenter toutes les classes et refactoriser si possible la classe Menu et la classe TraceRouteProducteur