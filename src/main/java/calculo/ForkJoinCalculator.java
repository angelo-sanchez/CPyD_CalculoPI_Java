package calculo;

import java.util.concurrent.RecursiveTask;

import static calculo.Main.umbral;

public class ForkJoinCalculator extends RecursiveTask<Double> {
	private final double step;
	private final long from;
	private final long to;

	public ForkJoinCalculator(double step, long from, long to) {
		this.step = step;
		this.from = from;
		this.to = to;
	}

	@Override
	protected Double compute() {
		if (to - from <= umbral) {
			return Main.calculoSecuencial(step, from, to);
		}
		final long mid = (from + to) / 2;
		final ForkJoinCalculator leftFork = new ForkJoinCalculator(step, from, mid);
		final ForkJoinCalculator rightFork = new ForkJoinCalculator(step, mid, to);

		invokeAll(leftFork, rightFork);
		final Double left = leftFork.getRawResult();
		final Double right = rightFork.getRawResult();

		final double leftAns = left == null || left.isNaN() ? 0 : left;
		final double rightAns = right == null || right.isNaN() ? 0 : right;
		return leftAns + rightAns;
	}
}
