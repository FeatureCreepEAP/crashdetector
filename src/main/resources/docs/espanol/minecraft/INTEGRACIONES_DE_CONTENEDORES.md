# Integraciones opcionales de registros de contenedores para CrashDetector

Estas integraciones no añaden dependencias obligatorias de Maven. Ejecutan el
cliente oficial ya utilizado para cada plataforma y pasan el texto de registro
UTF-8 capturado a `MonitorDePID.crearArchivoTemporalTextoCLI(...)`.

## Detección del cliente

Para cada plataforma, CrashDetector comprueba:

1. `--binary <path-or-command>`
2. `-Dcrashdetector.container.<platform>.binary=...`
3. `CRASHDETECTOR_<PLATFORM>_BINARY`
4. `deps/bin` en la instancia, en el directorio global de CrashDetector y en el directorio de trabajo
5. `PATH`

Nombres de cliente esperados:

- Kubernetes: `kubectl`
- OpenShift: `oc`
- Rancher: `rancher`
- Docker: `docker`
- Podman: `podman`
- Oracle Solaris Zones: `zlogin`

## Comandos

```bash
java -jar CrashDetectorApp.jar container kubernetes logs deployment/my-app \
  --namespace production --container app --tail 1000 --timestamps

java -jar CrashDetectorApp.jar container openshift logs pod/my-pod \
  --namespace my-project --previous

java -jar CrashDetectorApp.jar container rancher logs pod/my-pod \
  --namespace production
```

Rancher utiliza el proyecto o contexto de la CLI de Rancher que esté autenticado
actualmente y ejecuta `rancher kubectl logs ...`.

```bash
java -jar CrashDetectorApp.jar container docker logs my-container \
  --since 30m --tail 2000 --timestamps

java -jar CrashDetectorApp.jar container podman logs my-container \
  --since 30m --until 5m --tail 2000
```

Oracle Solaris Zones no proporciona un equivalente genérico de `docker logs`.
Especifique el archivo de registro real dentro de una zona en ejecución o lista:

```bash
java -jar CrashDetectorApp.jar container solaris logs my-zone \
  --path /var/adm/messages --tail 1000
```

El prefijo `container` es opcional:

```bash
java -jar CrashDetectorApp.jar kubernetes logs pod/my-pod -n production
```

## Seguridad y comportamiento operativo

- No se utiliza ningún shell. Cada argumento se pasa por separado a `ProcessBuilder`.
- `--follow` y `-f` se rechazan porque CrashDetector necesita una captura finita.
- Tiempo de espera predeterminado: 120 segundos.
- Límite predeterminado de stdout: 64 MiB.
- Tiempo de espera máximo configurable: 3600 segundos.
- Tamaño máximo de captura configurable: 1024 MiB.
- stdout y stderr se vacían simultáneamente.
- Un código de salida del cliente distinto de cero detiene el análisis e informa del error del cliente.
- Una respuesta correcta pero vacía se trata como un error, en lugar de analizar un registro vacío.
- La autenticación, kubeconfig, el contexto de Docker, la conexión de Podman, el
  inicio de sesión de Rancher y RBAC de Solaris siguen siendo responsabilidad
  del cliente oficial.
