package calculo;

public class Main {
	public static double calculoSecuencial(long numSteps, long from, long to) {
		double step = 1.0 / numSteps;
		double sum = 0.0;
		for (long i = from; i < to; i++) {
			double x = (i + 0.5) * step;
			sum = sum + 4.0 / (1.0 + x * x);
		}
		return step*sum;
	}

	public static void main(String[] args) {
		final long start = System.currentTimeMillis();
		final double PI = calculoSecuencial(Constants.DEFAULT_NUM_STEPS, 0, Constants.DEFAULT_NUM_STEPS);
		final long end = System.currentTimeMillis();
		System.out.println("PI aproximado: " + PI + " | Tiempo: " + (end - start) + " ms.");
	}
}
