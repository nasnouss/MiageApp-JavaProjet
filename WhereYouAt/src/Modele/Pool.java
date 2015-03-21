package Modele;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Controleur.TraceRouteConsommateur;
import Controleur.TraceRouteProducteur;
import Main.Menu;

public class Pool extends Thread {
	int pool;

	public Pool(int pool) {
		this.pool = pool;

	}

	public void run() {

		ExecutorService executeProd = Executors.newFixedThreadPool(pool);
		ExecutorService executeConso = Executors.newFixedThreadPool(pool);

		executePoolProd(executeProd, Menu.ltrProd);
		executePoolConso(executeConso, Menu.ltrCons);

	}

	public static void executePoolProd(final ExecutorService service,
			List<TraceRouteProducteur> lstprod) {

		for (TraceRouteProducteur r : lstprod) {
			service.execute(r);

		}
		service.shutdown();
	}

	public static void executePoolConso(final ExecutorService service,
			List<TraceRouteConsommateur> lstConso) {

		for (TraceRouteConsommateur r : lstConso) {
			service.execute(r);

		}
		service.shutdown();
	}

	public static void main(String[] args) {

	}

}
