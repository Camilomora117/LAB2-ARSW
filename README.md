Escuela Colombiana de Ingeniería

Arquitecturas de Software – ARSW

### Taller – programación concurrente, condiciones de carrera y sincronización de hilos. EJERCICIO INDIVIDUAL O EN PAREJAS.

#### Parte I – Antes de terminar la clase.

Creación, puesta en marcha y coordinación de hilos.

1. Revise el programa “primos concurrentes” (en la carpeta parte1), dispuesto en el paquete edu.eci.arsw.primefinder. Este es un programa que calcula los números primos entre dos intervalos, distribuyendo la búsqueda de los mismos entre hilos independientes. Por ahora, tiene un único hilo de ejecución que busca los primos entre 0 y 30.000.000. Ejecútelo, abra el administrador de procesos del sistema operativo, y verifique cuantos núcleos son usados por el mismo.

Código:

![image](https://user-images.githubusercontent.com/25957863/185159401-daf00445-416f-4a96-af39-8ba22032e6e9.png)

Administrador de procesos del sistema operativo:

![image](https://user-images.githubusercontent.com/25957863/185159442-5fabea7c-cfb2-4c3f-9a87-1790c26774f6.png)

Tiempo de ejecución:

![image](https://user-images.githubusercontent.com/25957863/185159501-99a8306a-c1db-4ed3-a452-9831dff28bc4.png)

#### Análisis:

Al ejecutar el buscador de números primos con un solo hilo vemos que el tiempo de ejecución del mismo es de 49.582 segundos. Y que se ocupa el 56% de la capacidad de procesamiento de la CPU. Es un proceso que no se está ejecutando concurrentemente con más hilos pero si concurrentemente con los procesos del ordenador por lo tanto vemos que el tiempo de espera es considerable. Los picos en las gráficas del administrador de procesos se deben a la regulación y optimización de recursos que realiza la CPU en la ejecución de un proceso determinado. 

2. Modifique el programa para que, en lugar de resolver el problema con un solo hilo, lo haga con tres, donde cada uno de éstos hará la tarcera parte del problema original. Verifique nuevamente el funcionamiento, y nuevamente revise el uso de los núcleos del equipo.

Código:

![image](https://user-images.githubusercontent.com/25957863/185159922-4f646a38-0983-4835-955e-06546e8a86e4.png)

Administrador de procesos del sistema operativo:

![image](https://user-images.githubusercontent.com/25957863/185160068-7db78479-86b7-480d-a04d-1b5b119faf16.png)

Tiempo de ejecución:

![image](https://user-images.githubusercontent.com/25957863/185160125-40bf4c3e-94ca-418a-92dd-4b179c9e23a7.png)

#### Análisis:

Al ejecutar el buscador de números primos con un tres hilos vemos que el tiempo de ejecución del mismo es de 53.825 segundos. Y que se ocupa el 60% de la capacidad de procesamiento de la CPU. Es un proceso que se está ejecutando concurrentemente con varios hilos y además con los procesos internos del ordenador por lo tanto vemos que el tiempo de espera es mayor que en el apartado anterior. Por otro lado con la La velocidad de reloj (Speed) se mide la cantidad de ciclos que ejecuta la CPU por segundo; para los apartados uno (un hilo) y dos (tres hilos) la velocidad fue la misma (3.68GHz).

3. Lo que se le ha pedido es: debe modificar la aplicación de manera que cuando hayan transcurrido 5 segundos desde que se inició la ejecución, se detengan todos los hilos y se muestre el número de primos encontrados hasta el momento. Luego, se debe esperar a que el usuario presione ENTER para reanudar la ejecución de los mismo.

#####Parte II 


Para este ejercicio se va a trabajar con un simulador de carreras de galgos (carpeta parte2), cuya representación gráfica corresponde a la siguiente figura:

![](./img/media/image1.png)

En la simulación, todos los galgos tienen la misma velocidad (a nivel de programación), por lo que el galgo ganador será aquel que (por cuestiones del azar) haya sido más beneficiado por el *scheduling* del
procesador (es decir, al que más ciclos de CPU se le haya otorgado durante la carrera). El modelo de la aplicación es el siguiente:

![](./img/media/image2.png)

Como se observa, los galgos son objetos ‘hilo’ (Thread), y el avance de los mismos es visualizado en la clase Canodromo, que es básicamente un formulario Swing. Todos los galgos (por defecto son 17 galgos corriendo en una pista de 100 metros) comparten el acceso a un objeto de tipo
RegistroLLegada. Cuando un galgo llega a la meta, accede al contador ubicado en dicho objeto (cuyo valor inicial es 1), y toma dicho valor como su posición de llegada, y luego lo incrementa en 1. El galgo que
logre tomar el ‘1’ será el ganador.

Al iniciar la aplicación, hay un primer error evidente: los resultados (total recorrido y número del galgo ganador) son mostrados antes de que finalice la carrera como tal. Sin embargo, es posible que una vez corregido esto, haya más inconsistencias causadas por la presencia de condiciones de carrera.

Taller.

1.  Corrija la aplicación para que el aviso de resultados se muestre
    sólo cuando la ejecución de todos los hilos ‘galgo’ haya finalizado.
    Para esto tenga en cuenta:

    a.  La acción de iniciar la carrera y mostrar los resultados se realiza a partir de la línea 38 de MainCanodromo.

    b.  Puede utilizarse el método join() de la clase Thread para sincronizar el hilo que inicia la carrera, con la finalización de los hilos de los galgos.

2.  Una vez corregido el problema inicial, corra la aplicación varias
    veces, e identifique las inconsistencias en los resultados de las
    mismas viendo el ‘ranking’ mostrado en consola (algunas veces
    podrían salir resultados válidos, pero en otros se pueden presentar
    dichas inconsistencias). A partir de esto, identifique las regiones
    críticas () del programa.
    
Cómo podemos observar algunas de las inconsistencias es que algunas veces varios galgos llegan en la primera posicion, pero solo uno se muestra cómo el ganador.
    
![image](https://user-images.githubusercontent.com/98135134/185421239-e88762e2-7418-47a9-ae03-a5a9d4f3e162.png)

Esto es un problema de sincronización.

### Por lo tanto encontramos las siguientes regiones criticas:
![image](https://user-images.githubusercontent.com/98135134/185422800-3dc7e6bd-d326-47e8-93de-e63d9a21b8ae.png)

Ya que estas son las funciones donde el programa tiene los conflictos para desidir el ganador.

3.  Utilice un mecanismo de sincronización para garantizar que a dichas
    regiones críticas sólo acceda un hilo a la vez. Verifique los
    resultados.

4.  Implemente las funcionalidades de pausa y continuar. Con estas,
    cuando se haga clic en ‘Stop’, todos los hilos de los galgos
    deberían dormirse, y cuando se haga clic en ‘Continue’ los mismos
    deberían despertarse y continuar con la carrera. Diseñe una solución que permita hacer esto utilizando los mecanismos de sincronización con las primitivas de los Locks provistos por el lenguaje (wait y notifyAll).


## Criterios de evaluación

1. Funcionalidad.

    1.1. La ejecución de los galgos puede ser detenida y resumida consistentemente.
    
    1.2. No hay inconsistencias en el orden de llegada registrado.
    
2. Diseño.   

    2.1. Se hace una sincronización de sólo la región crítica (sincronizar, por ejemplo, todo un método, bloquearía más de lo necesario).
    
    2.2. Los galgos, cuando están suspendidos, son reactivados son sólo un llamado (usando un monitor común).


