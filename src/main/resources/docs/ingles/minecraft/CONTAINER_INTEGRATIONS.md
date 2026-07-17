# CrashDetector optional container log integrations

These integrations do not add mandatory Maven dependencies. They invoke the
official client already used for each platform and pass the captured UTF-8 log
text to `MonitorDePID.crearArchivoTemporalTextoCLI(...)`.

## Client discovery

For each platform, CrashDetector checks:

1. `--binary <path-or-command>`
2. `-Dcrashdetector.container.<platform>.binary=...`
3. `CRASHDETECTOR_<PLATFORM>_BINARY`
4. `deps/bin` in the instance, global CrashDetector directory, and working directory
5. `PATH`

Expected client names:

- Kubernetes: `kubectl`
- OpenShift: `oc`
- Rancher: `rancher`
- Docker: `docker`
- Podman: `podman`
- Oracle Solaris Zones: `zlogin`

## Commands

```bash
java -jar CrashDetectorApp.jar container kubernetes logs deployment/my-app \
  --namespace production --container app --tail 1000 --timestamps

java -jar CrashDetectorApp.jar container openshift logs pod/my-pod \
  --namespace my-project --previous

java -jar CrashDetectorApp.jar container rancher logs pod/my-pod \
  --namespace production
```

Rancher uses the currently authenticated Rancher CLI project/context and runs
`rancher kubectl logs ...`.

```bash
java -jar CrashDetectorApp.jar container docker logs my-container \
  --since 30m --tail 2000 --timestamps

java -jar CrashDetectorApp.jar container podman logs my-container \
  --since 30m --until 5m --tail 2000
```

Oracle Solaris Zones does not expose a generic equivalent to `docker logs`.
Specify the real log file inside a running or ready zone:

```bash
java -jar CrashDetectorApp.jar container solaris logs my-zone \
  --path /var/adm/messages --tail 1000
```

The `container` prefix is optional:

```bash
java -jar CrashDetectorApp.jar kubernetes logs pod/my-pod -n production
```

## Safety and operational behaviour

- No shell is used. Every argument is passed separately to `ProcessBuilder`.
- `--follow` and `-f` are rejected because CrashDetector needs a finite capture.
- Default timeout: 120 seconds.
- Default stdout limit: 64 MiB.
- Maximum configurable timeout: 3600 seconds.
- Maximum configurable capture: 1024 MiB.
- stdout and stderr are drained concurrently.
- A non-zero client exit code stops analysis and reports the client error.
- An empty successful response is treated as an error instead of analysing an empty log.
- Authentication, kubeconfig, Docker context, Podman connection, Rancher login,
  and Solaris RBAC remain the responsibility of the official client.
