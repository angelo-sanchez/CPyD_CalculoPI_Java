# Calculo aproximado del número PI con la API de Concurrencia de Java

Se compila con maven, usando `mvn clean install` o `mvn clean package`

Se agregó la dependencia de Apache Commons `commons-cli:1.4` para poder manejar más fácilmente los argumentos de la
línea de comandos.

El jar a ejecutar es el que está nombrado como **calculo-java-pi-1.0-jar-with-dependencies** y se puede ejecutar con los
siguientes parámetros:
```
-h,--help              Imprime la ayuda.
-p,--parallel          Decide si la ejecución se hace en modo paralelo o
no
-r,--rounds <num>      Cantidad de veces que se ejecutará el algoritmo,
para promediar los tiempos de ejecución, por defecto se ejecuta 10 veces.
-s,--steps <num>       La cantidad de pasos o "num_steps", si no está
presente se usará 100.000
-t,--threshold <num>   El umbral a partir del cual el programa se
ejecutará en modo secuencial, para evitar overhead
de creación de hilos, si no está presente, el
valor será de 5000