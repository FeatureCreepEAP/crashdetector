### **Conflicto de Sobrescritura de Opciones de Sodium (Multiple overrides for option)**

La verificación `conflicto_overrides_opciones_sodium` detecta el error:

```
java.lang.IllegalArgumentException: Multiple overrides for option 'sodium:general.fullscreen_mode'! Sources: chloride and cwb
```

---

### **¿Qué significa este error?**

Sodium (y sus derivados como **Embeddium**, **Rubidium** o **Chloride**) permite que otros mods
registren valores para sus opciones de configuración. El error aparece cuando **dos o más mods**
registran un valor para la **misma** opción (por ejemplo `sodium:general.fullscreen_mode`).

Sodium no puede decidir cuál de los dos valores usar, lanza una `IllegalArgumentException`
y el juego se cierra antes de terminar de arrancar.

El mensaje siempre tiene esta forma:

* `option '<clave>'` — la opción de Sodium en conflicto.
* `Sources: <mod1> and <mod2>` — los mods que intentan anularla.

---

### **¿Es siempre Chloride y CWB?**

No. `chloride` y `cwb` son solo un ejemplo. Cualquier combinación de mods que toque las
mismas opciones de Sodium puede provocar el error. La verificación **no** busca nombres de
mods concretos: lee dinámicamente la opción y los mods en conflicto desde la línea del log,
por lo que cubre **cualquier** mod que anule opciones de Sodium.

---

### **Solución recomendada**

1. Quita uno de los mods indicados en `Sources:`.
2. Revisa los mods de optimización y los paquetes de ajustes de Sodium, ya que muchos de
   ellos modifican las mismas opciones.
3. Si ambos mods cumplen funciones distintas, busca una versión o un modpack donde no
   colisionen.
4. Como último recurso, informa al autor del mod que añade la opción duplicada.

---

### **Ejemplo de log detectado**

```
[Render thread/ERROR] (Minecraft) java.lang.IllegalArgumentException: Multiple overrides for option 'sodium:general.fullscreen_mode'! Sources: chloride and cwb
```

Resultado: la verificación marca la consola y muestra el nombre de la opción y de los mods
en conflicto en el mensaje.
