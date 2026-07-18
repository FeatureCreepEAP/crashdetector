# Integración de Ansible con CrashDetector

Esta integración opcional recopila registros remotos mediante Ansible y pasa el
registro de cada host administrado a CrashDetector como una entrada de análisis
independiente.

No añade una dependencia de Maven. Detecta `ansible-runner` y
`ansible-playbook` como programas externos opcionales mediante una opción
explícita, propiedades de Java, variables de entorno, `deps/bin` o `PATH`.

## Comandos

### Comprobar disponibilidad

```bash
java -jar CrashDetectorApp.jar ansible status
```

Con binarios especificados explícitamente:

```bash
java -jar CrashDetectorApp.jar ansible status \
  --runner-binary /opt/ansible/bin/ansible-runner \
  --playbook-binary /opt/ansible/bin/ansible-playbook
```

### Recopilar registros remotos

```bash
java -jar CrashDetectorApp.jar ansible logs \
  --inventory inventory.yml \
  --hosts minecraft_servers \
  --path /opt/minecraft/logs/latest.log \
  --tail 5000
```

CrashDetector da preferencia a `ansible-runner`. Si Runner no está disponible,
`auto` utiliza `ansible-playbook` como alternativa:

```bash
java -jar CrashDetectorApp.jar ansible logs \
  --inventory inventory.yml \
  --path /var/log/my-application.log \
  --engine auto
```

Forzar el uso de un motor específico:

```bash
--engine runner
--engine playbook
```

Opciones de elevación de privilegios y SSH:

```bash
java -jar CrashDetectorApp.jar ansible logs \
  --inventory inventory.yml \
  --hosts production \
  --path /var/log/private-service.log \
  --become \
  --become-user root \
  --user deploy \
  --private-key ~/.ssh/production_ed25519
```

Utilice `--tail all` para leer el archivo completo. Esto puede consumir muchos
recursos; de forma predeterminada se leen las últimas 5,000 líneas.

### Ejecutar un playbook externo

Los playbooks externos pueden modificar los hosts administrados, por lo que se
requiere consentimiento explícito:

```bash
java -jar CrashDetectorApp.jar ansible playbook \
  --inventory inventory.yml \
  --playbook playbooks/diagnostics.yml \
  --project-dir . \
  --allow-external-playbook
```

Modo de comprobación:

```bash
java -jar CrashDetectorApp.jar ansible playbook \
  --inventory inventory.yml \
  --playbook playbooks/diagnostics.yml \
  --allow-external-playbook \
  --check
```

Comprobar únicamente la sintaxis:

```bash
java -jar CrashDetectorApp.jar ansible playbook \
  --inventory inventory.yml \
  --playbook playbooks/diagnostics.yml \
  --allow-external-playbook \
  --syntax-check
```

La salida estándar, la salida de error, el comando y el código de salida del
playbook se convierten en un único registro de CrashDetector. Un código de
salida distinto de cero también se analiza.

## Opciones comunes

```text
-i, --inventory <path>       Archivo, script o directorio de inventario obligatorio.
-l, --hosts, --limit <expr>  Patrón de hosts o grupos. Valor predeterminado: all.
-u, --user <name>            Usuario remoto.
--private-key <file>         Clave privada SSH.
-b, --become                 Activa la elevación de privilegios.
--become-user <name>         Usuario de destino y activa --become.
-f, --forks <n>              De 1 a 1000. Valor predeterminado: 5.
--extra-vars-file <file>     Archivo YAML/JSON de variables adicionales.
--vault-password-file <file> Archivo con la contraseña de Vault.
--timeout-seconds <n>        De 1 a 86400. Valor predeterminado: 300.
--max-mib <n>                De 1 a 1024. Valor predeterminado: 64.
--runner-binary <path>       Binario alternativo de ansible-runner.
--playbook-binary <path>     Binario alternativo de ansible-playbook.
```

Las variables adicionales sin procesar escritas directamente en la línea de
comandos no son compatibles deliberadamente, ya que pueden exponer secretos en
los listados de procesos. También se desactivan las solicitudes interactivas de
contraseña; utilice un agente SSH, la configuración del inventario, Vault o
archivos de contraseñas.

## Detección de binarios

Runner:

1. `--runner-binary`
2. `-Dcrashdetector.ansible.runner.binary=...`
3. `CRASHDETECTOR_ANSIBLE_RUNNER_BINARY`
4. `deps/bin` de la instancia, del directorio global de CrashDetector o del directorio de trabajo
5. `PATH`

Playbook:

1. `--playbook-binary`
2. `-Dcrashdetector.ansible.playbook.binary=...`
3. `CRASHDETECTOR_ANSIBLE_PLAYBOOK_BINARY`
4. `deps/bin` de la instancia, del directorio global de CrashDetector o del directorio de trabajo
5. `PATH`

## Diseño de la recopilación de registros

El playbook integrado de recopilación de registros es de solo lectura en los
hosts administrados:

1. `ansible.builtin.stat` sigue la ruta solicitada y verifica que su destino sea
   un archivo normal.
2. `ansible.builtin.command` ejecuta `tail -n` o `cat` mediante `argv`, sin
   utilizar un shell remoto.
3. Un callback agregado privado guarda el resultado correcto de cada host en un
   archivo UTF-8 local independiente.
4. Java valida los límites de salida por host y los límites totales.
5. Cada archivo de host se copia a la canalización existente de registros
   temporales de CrashDetector.

El espacio de trabajo temporal de Ansible se elimina después de la captura,
salvo que se utilice `--keep-artifacts`.

## Propiedades de seguridad

- Java nunca ejecuta `/bin/sh -c` ni ningún otro shell local.
- Todos los argumentos de comandos locales se pasan por separado a `ProcessBuilder`.
- La entrada estándar del proceso se cierra inmediatamente, lo que impide solicitudes interactivas.
- Se aplican límites de tiempo de ejecución y de tamaño para stdout y stderr.
- Las rutas remotas deben ser rutas Unix absolutas y no pueden contener saltos de línea ni NUL.
- Los nombres de host utilizados como nombres de archivo locales se depuran y reciben un sufijo SHA-256.
- Los playbooks externos requieren `--allow-external-playbook`.
- CrashDetector no imprime ni reconstruye credenciales.

## Alcance

Esta primera versión incluye `logs`, `playbook`, `status` y la ayuda. Todavía no
incluye compatibilidad con la API REST de AWX/Automation Controller. El
recopilador de registros integrado está diseñado para hosts administrados de
tipo Unix que proporcionen `tail` y `cat`.

## Referencias oficiales

- CLI independiente de Ansible Runner y directorio de datos privados:
  https://docs.ansible.com/projects/runner/en/latest/standalone/
- Artefactos de Runner y eventos de trabajos:
  https://docs.ansible.com/projects/runner/en/latest/intro/
- CLI de `ansible-playbook`:
  https://docs.ansible.com/projects/ansible/latest/cli/ansible-playbook.html
- Plugins de callback:
  https://docs.ansible.com/projects/ansible/latest/plugins/callback.html
