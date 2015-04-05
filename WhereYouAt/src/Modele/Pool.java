package Modele;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Controleur.TraceRouteConsommateur;
import Controleur.TraceRouteProducteur;
import Main.Menu;

public class Pool {
	int pool;
	private Menu m;
	ExecutorService executeProd;
	ExecutorService executeConso;

	public Pool(int pool, Menu m) {
		this.pool = pool;
		this.m = m;
		this.executeProd = Executors.newFixedThreadPool(pool);
		this.executeConso = Executors.newFixedThreadPool(pool);
	}

	public void start(TraceRouteProducteur traceRouteProducteur,
			TraceRouteConsommateur traceRouteConsommateur) {

		executeProd.execute(traceRouteProducteur);
		executeConso.execute(traceRouteConsommateur);

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

}
