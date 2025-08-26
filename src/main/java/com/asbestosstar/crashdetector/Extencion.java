package com.asbestosstar.crashdetector;

/**
 * Extensiones para CrashDetector para agregar contenido. Generalmente no puedes acceder a las clases de la aplicación/juego.
 * procesoDeLaMonitorizaciónDePID y procesoDelApp se ejecutan en procesos diferentes y no pueden acceder al contenido del otro proceso.
 * Necesitas agregar un campo en tu manifiesto y no puedes usar un JAR dentro de otro JAR. La extensión del archivo debe ser .jar, .fpm o .cdext.
 * El campo del manifiesto debe contener la ruta completa de la clase. El nombre del campo en el manifiesto es ExtencionCrashDetector.
 */
public interface Extencion {
	
	/**
	 * Método para agregar contenido en el proceso de la aplicación. Registra parches aquí.
	 * Solo necesitas implementar este método si deseas que tu extensión funcione con todos los cargadores.
	 * Esto es similar a los Agents de FeatureCreep, ModLauncher ITransformationService o fabric-loader PreLaunchEntrypoint.
	 * La mayoría de los parches personalizados no se aplicarán en FabricMC porque carece de un sistema de transformaciones sin SpongeMixin,
	 * pero con FeatureCreep en fabric-loader sí es posible.
	 */
	public void procesoDelApp();
	
	
	/**
	 * Aquí se ejecuta el proceso de la monitorización de PID. Aquí puedes registrar verificaciones, ubicaciones para registros,
	 * botones de la barra lateral, APIs y destinos de registro, entre otras funcionalidades.
	 * No tienes acceso a las instancias del proceso principal (del juego/aplicación).
	 */
	public void procesoDeLaMonitorizacionDePID();

}