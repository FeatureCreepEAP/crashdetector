# Headless CLI result transport

`MonitorDePID.local` is now printed as the final non-empty stdout line whenever
the monitor runs headlessly.

All analysis inputs registered through:

```java
MonitorDePID.configurarEntradasCLI(rutas, rutasTemporales);
```

automatically request this result mode. This already covers:

- direct CLI file and text analysis;
- Kubernetes, OpenShift, Rancher, Docker, Podman and Solaris container logs;
- Ansible log collection and explicit Ansible playbook output;
- future cloud adapters that use the same central entry method.

A cloud adapter that does not register log paths through that method can call:

```java
MonitorDePID.solicitarResultadoCLI();
```

In CLI-result mode the monitor JVM:

1. is launched with `-Djava.awt.headless=true`;
2. receives the internal `--resultado-cli` argument;
3. skips the stdin streaming and `Entregar` protocols because its inputs are
   already complete files;
4. analyses immediately instead of waiting for the parent PID to terminate;
5. inherits stdout and stderr from the invoking process;
6. prints `MonitorDePID.local`, flushes stdout and exits;
7. is awaited by the parent so command substitution and service callers receive
   the completed result.

The final path can be captured on Unix with:

```bash
resultado="$(java -jar CrashDetectorApp.jar analizar latest.log | tail -n 1)"
printf '%s\n' "$resultado"
```

The same applies to `container ...` and `ansible ...` commands.
