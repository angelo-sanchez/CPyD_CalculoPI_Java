package calculo;

import org.apache.commons.cli.*;

public class CommandLineUtil {
	private final HelpFormatter helpFormatter = new HelpFormatter();
	private final Options opciones = new Options();
	private CommandLine commandLine = null;


	public CommandLineUtil() {
		Option parallelize = new Option("p", "parallel", false, "Decide si la ejecución se hace en modo paralelo o no");
		Option numberOfSteps = new Option("s", "steps", true, "La cantidad de pasos o \"num_steps\", si no está presente se usará 100.000");
		Option threshold = new Option("t", "threshold", true, "El umbral a partir del cual el programa se ejecutará en modo secuencial, para evitar overhead de creación de hilos, si no está presente, el valor será de 5000");
		Option rounds = new Option("r", "rounds", true, "Cantidad de veces que se ejecutará el algoritmo, para promediar los tiempos de ejecución");
		Option help = new Option("h", "help", false, "Imprime la ayuda.");
		opciones.addOption(parallelize)
				.addOption(numberOfSteps)
				.addOption(threshold)
				.addOption(rounds)
				.addOption(help);
	}

	public CommandLineUtil(String[] args) {
		this();
		try {
			this.commandLine = new org.apache.commons.cli.DefaultParser().parse(opciones, args);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void printHelp() {
		if (commandLine.hasOption('h')) {
			helpFormatter.printHelp("calculo-java-pi", opciones);
			System.exit(0);
		}
	}

	public void usageAndExit() {
		helpFormatter.printHelp("calculo-java-pi", opciones, true);
		System.exit(1);
	}

	public boolean isParallel() {
		return commandLine.hasOption('p');
	}

	private Long getArg(char c) {
		try {
			if (commandLine.hasOption(c)) {
				String value = commandLine.getOptionValue(c);
				if (value != null) {
					return Long.parseLong(value);
				}
				usageAndExit();
			}
			return null;
		} catch (Exception e) {
			usageAndExit();
			return null;
		}
	}

	public Long getNumSteps() {
		return getArg('s');
	}

	public Long getRepeticiones() {
		return getArg('r');
	}

	public Long getUmbral() {
		return getArg('t');
	}
}
