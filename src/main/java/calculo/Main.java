package calculo;

public class Main {
	public static long umbral = Constants.SEQ_THRESHOLD;

	public static double calculoSecuencial(double step, long from, long to) {
		double sum = 0.0;
		for (long i = from; i < to; i++) {
			double x = (i + 0.5) * step;
			sum = sum + 4.0 / (1.0 + x * x);
		}
		return sum;
	}

	public static void main(String[] args) {
		boolean runInParallel = false; // por defecto se hará secuencial.
		long numSteps = Constants.DEFAULT_NUM_STEPS; // por defecto el valor es de 100.000
		int repeticiones = Constants.ROUNDS;
		if (args.length > 0) {
			CommandLineUtil cli = new CommandLineUtil(args);
			cli.printHelp();
			runInParallel = cli.isParallel();
			numSteps = cli.getNumSteps() == null ? numSteps : cli.getNumSteps();
			repeticiones = cli.getRepeticiones() == null ? repeticiones : cli.getRepeticiones()
					.intValue() + 1; // Una ronda más, ya que se descarta la primera.
			umbral = cli.getUmbral() == null ? umbral : cli.getUmbral();
		}

		System.out.printf("Usando la version %s%n", runInParallel ? "paralela" : "secuencial");
		double tiempo = promedioTiempoEjecucion(numSteps, repeticiones, runInParallel);
		System.out.printf("Tiempo promedio de ejecución: %s%n", tiempo);
	}

	private static double promedioTiempoEjecucion(long numSteps, int repeticiones, boolean runInParallel) {
		long[] ejecuciones = new long[repeticiones];
		for (int i = 0; i < ejecuciones.length; ++i) {
			ejecuciones[i] = runInParallel ? tiempoCalculoParalelo(numSteps) : tiempoCalculoSecuencial(numSteps);
		}
		long suma = -ejecuciones[0]; // Para no contar el primer tiempo
		for (long tiempo : ejecuciones) {
			suma += tiempo;
		}

		return (double) suma / (double) (repeticiones - 1);
	}

	public static long tiempoCalculoParalelo(long numSteps) {
		double h = 1.0D / (double) numSteps;
		long start = System.currentTimeMillis();
		ForkJoinCalculator calculator = new ForkJoinCalculator(h, 0L, numSteps);
		double computed = calculator.compute();
		long end = System.currentTimeMillis();
		final double PI = computed * h;
		System.out.printf("PI Aproximado: %s | Tiempo transcurrido: %dms.%n", PI, end - start);
		return end - start;
	}

	public static long tiempoCalculoSecuencial(long numSteps) {
		double h = 1.0D / (double) numSteps;
		long start = System.currentTimeMillis();
		double computed = calculoSecuencial(h, 0L, numSteps);
		long end = System.currentTimeMillis();
		final double PI = computed * h;
		System.out.printf("PI Aproximado: %s | Tiempo transcurrido: %dms.%n", PI, end - start);
		return end - start;
	}
}
