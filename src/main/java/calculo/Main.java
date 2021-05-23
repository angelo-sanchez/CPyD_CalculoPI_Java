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
		final double PI, step = 1.0 / numSteps;
		final long start = System.currentTimeMillis();
		if (runInParallel) {
			PI = step * new ForkJoinCalculator(step, 0, numSteps)
					.compute();
		} else {
			PI = step * calculoSecuencial(step, 0, numSteps);
		}
		final long end = System.currentTimeMillis();
		System.out.println("PI aproximado: " + PI + " | Tiempo: " + (end - start) + " ms.");
	}
}
