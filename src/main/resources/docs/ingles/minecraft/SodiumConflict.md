### **Sodium Option Override Conflict (Multiple overrides for option)**

The `conflicto_overrides_opciones_sodium` check detects the error:

```
java.lang.IllegalArgumentException: Multiple overrides for option 'sodium:general.fullscreen_mode'! Sources: chloride and cwb
```

---

### **What does this error mean?**

Sodium (and related mods such as **Embeddium**, **Rubidium** or **Chloride**) lets other mods
register values for its configuration options. The error appears when **two or more mods**
register a value for the **same** option (for example `sodium:general.fullscreen_mode`).

Sodium cannot decide which value to use, throws an `IllegalArgumentException`
and the game closes before finishing startup.

The message always looks like this:

* `option '<key>'` — the conflicting Sodium option.
* `Sources: <mod1> and <mod2>` — the mods trying to override it.

---

### **Is it always Chloride and CWB?**

No. `chloride` and `cwb` are just one example. Any combination of mods touching the same
Sodium options can trigger the error. The check does **not** look for specific mod names:
it reads the option and the conflicting mods dynamically from the log line, so it covers
**any** mod that overrides Sodium options.

---

### **Recommended fix**

1. Remove one of the mods listed in `Sources:`.
2. Review performance mods and Sodium settings packs, since many of them change the same
   options.
3. If both mods serve different purposes, look for a version or modpack where they do not
   clash.
4. As a last resort, report it to the author of the mod that adds the duplicate option.

---

### **Example detected log**

```
[Render thread/ERROR] (Minecraft) java.lang.IllegalArgumentException: Multiple overrides for option 'sodium:general.fullscreen_mode'! Sources: chloride and cwb
```

Result: the check marks the console and shows the option name and the conflicting mods in
the message.
