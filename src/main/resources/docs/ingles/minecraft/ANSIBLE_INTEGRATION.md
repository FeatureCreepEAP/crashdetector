# CrashDetector Ansible Integration

This optional integration collects remote logs with Ansible and passes each
managed host's log to CrashDetector as a separate analysis input.

It does not add a Maven dependency. It discovers `ansible-runner` and
`ansible-playbook` as optional external programs from an explicit option,
Java properties, environment variables, `deps/bin`, or `PATH`.

## Commands

### Check availability

```bash
java -jar CrashDetectorApp.jar ansible status
```

With explicit binaries:

```bash
java -jar CrashDetectorApp.jar ansible status \
  --runner-binary /opt/ansible/bin/ansible-runner \
  --playbook-binary /opt/ansible/bin/ansible-playbook
```

### Collect remote logs

```bash
java -jar CrashDetectorApp.jar ansible logs \
  --inventory inventory.yml \
  --hosts minecraft_servers \
  --path /opt/minecraft/logs/latest.log \
  --tail 5000
```

CrashDetector prefers `ansible-runner`. If Runner is unavailable, `auto`
falls back to `ansible-playbook`:

```bash
java -jar CrashDetectorApp.jar ansible logs \
  --inventory inventory.yml \
  --path /var/log/my-application.log \
  --engine auto
```

Force one engine:

```bash
--engine runner
--engine playbook
```

Privilege escalation and SSH options:

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

Use `--tail all` to read the complete file. This can be expensive; the default
is the last 5,000 lines.

### Execute an external playbook

External playbooks may change managed hosts, so explicit consent is mandatory:

```bash
java -jar CrashDetectorApp.jar ansible playbook \
  --inventory inventory.yml \
  --playbook playbooks/diagnostics.yml \
  --project-dir . \
  --allow-external-playbook
```

Check mode:

```bash
java -jar CrashDetectorApp.jar ansible playbook \
  --inventory inventory.yml \
  --playbook playbooks/diagnostics.yml \
  --allow-external-playbook \
  --check
```

Syntax only:

```bash
java -jar CrashDetectorApp.jar ansible playbook \
  --inventory inventory.yml \
  --playbook playbooks/diagnostics.yml \
  --allow-external-playbook \
  --syntax-check
```

The playbook's stdout, stderr, command, and exit code are converted to one
CrashDetector log. A non-zero playbook exit code is still analysed.

## Common options

```text
-i, --inventory <path>       Required inventory file, script, or directory.
-l, --hosts, --limit <expr>  Host/group pattern. Default: all.
-u, --user <name>            Remote user.
--private-key <file>         SSH private key.
-b, --become                 Enable privilege escalation.
--become-user <name>         Become user and enable --become.
-f, --forks <n>              1..1000. Default: 5.
--extra-vars-file <file>     YAML/JSON extra-vars file.
--vault-password-file <file> Vault password file.
--timeout-seconds <n>        1..86400. Default: 300.
--max-mib <n>                1..1024. Default: 64.
--runner-binary <path>       Alternate ansible-runner.
--playbook-binary <path>     Alternate ansible-playbook.
```

Raw inline extra vars are deliberately not supported because they can expose
secrets in process listings. Interactive password prompts are also disabled;
use an SSH agent, inventory configuration, vault, or password files.

## Binary discovery

Runner:

1. `--runner-binary`
2. `-Dcrashdetector.ansible.runner.binary=...`
3. `CRASHDETECTOR_ANSIBLE_RUNNER_BINARY`
4. Instance/global/working-directory `deps/bin`
5. `PATH`

Playbook:

1. `--playbook-binary`
2. `-Dcrashdetector.ansible.playbook.binary=...`
3. `CRASHDETECTOR_ANSIBLE_PLAYBOOK_BINARY`
4. Instance/global/working-directory `deps/bin`
5. `PATH`

## Log collection design

The integrated log playbook is read-only on managed hosts:

1. `ansible.builtin.stat` follows the requested path and verifies that its
   target is a regular file.
2. `ansible.builtin.command` invokes `tail -n` or `cat` through `argv`, without
   a remote shell.
3. A private aggregate callback stores each successful host result in a
   separate local UTF-8 file.
4. Java validates per-host and total output limits.
5. Each host file is copied into CrashDetector's existing temporary-log
   pipeline.

The temporary Ansible workspace is deleted after capture unless
`--keep-artifacts` is used.

## Security properties

- Java never invokes `/bin/sh -c` or another local shell.
- All local command arguments are passed separately to `ProcessBuilder`.
- Process stdin is closed immediately, preventing interactive prompts.
- Execution timeout and stdout/stderr limits are enforced.
- Remote paths must be absolute Unix paths and cannot contain newlines or NUL.
- Host names used as local filenames are sanitised and given a SHA-256 suffix.
- External playbooks require `--allow-external-playbook`.
- No credentials are printed or reconstructed by CrashDetector.

## Scope

This first version includes `logs`, `playbook`, `status`, and help. It does not
include AWX/Automation Controller REST support yet. The built-in log collector
is intended for Unix-like managed hosts that provide `tail` and `cat`.

## Official references

- Ansible Runner standalone CLI and private-data directory:
  https://docs.ansible.com/projects/runner/en/latest/standalone/
- Runner artifacts and job events:
  https://docs.ansible.com/projects/runner/en/latest/intro/
- `ansible-playbook` CLI:
  https://docs.ansible.com/projects/ansible/latest/cli/ansible-playbook.html
- Callback plugins:
  https://docs.ansible.com/projects/ansible/latest/plugins/callback.html
