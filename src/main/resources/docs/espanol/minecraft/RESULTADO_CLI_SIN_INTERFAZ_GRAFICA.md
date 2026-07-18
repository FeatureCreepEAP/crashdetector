# Transporte de resultados de la CLI sin interfaz gráfica

`MonitorDePID.local` ahora se imprime como la última línea no vacía de stdout
siempre que el monitor se ejecuta sin interfaz gráfica.

Todas las entradas de análisis registradas mediante:

```java
MonitorDePID.configurarEntradasCLI(rutas, rutasTemporales);
```

solicitan automáticamente este modo de resultados. Esto ya incluye:

- análisis directo de archivos y texto desde la CLI;
- registros de contenedores de Kubernetes, OpenShift, Rancher, Docker, Podman y Solaris;
- recopilación de registros mediante Ansible y salida de playbooks de Ansible ejecutados explícitamente;
- futuros adaptadores de nube que utilicen el mismo método central de entrada.

Un adaptador de nube que no registre rutas de registros mediante ese método
puede llamar a:

```java
MonitorDePID.solicitarResultadoCLI();
```

En el modo de resultados de la CLI, la JVM del monitor:

1. se inicia con `-Djava.awt.headless=true`;
2. recibe el argumento interno `--resultado-cli`;
3. omite los protocolos de transmisión por stdin y `Entregar`, porque sus entradas
   ya son archivos completos;
4. analiza inmediatamente en lugar de esperar a que finalice el PID principal;
5. hereda stdout y stderr del proceso que la invoca;
6. imprime `MonitorDePID.local`, vacía stdout y finaliza;
7. es esperada por el proceso principal para que la sustitución de comandos y
   los servicios que la invocan reciban el resultado completo.

La ruta final puede capturarse en Unix con:

```bash
resultado="$(java -jar CrashDetectorApp.jar analizar latest.log | tail -n 1)"
printf '%s\n' "$resultado"
```

Lo mismo se aplica a los comandos `container ...` y `ansible ...`.
