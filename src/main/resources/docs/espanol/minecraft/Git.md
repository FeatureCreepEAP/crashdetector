# Guía de Configuración de la Herramienta Git y Forges Remotos de CrashDetector

CrashDetector incluye una herramienta Git para crear un repositorio Git local, crear o conectar un repositorio remoto, realizar commits y enviar copias de seguridad o historial del proyecto a un forge remoto. 

Un **forge** es un servicio de alojamiento Git con herramientas adicionales para proyectos, normalmente incluyendo una interfaz web, seguimiento de incidencias, pull requests o merge requests, cuentas de usuario, permisos y administración de repositorios. Algunos ejemplos son Pagure, GitHub, GitLab, Gitea, Forgejo, Bitbucket y Beanstalk. 

La herramienta Git de CrashDetector no depende de un único proveedor. Utiliza una pequeña abstracción de API llamada `GitForgeAPI`, lo que permite que distintos forges creen repositorios remotos a su manera mientras la interfaz gráfica permanece prácticamente igual. 

## Flujo Básico de la Herramienta Git

El flujo normal es:

1. Abrir la herramienta Git de CrashDetector.
2. Instalar o descargar las dependencias JGit faltantes si la interfaz indica que son necesarias.
3. Crear un repositorio Git local en la carpeta de trabajo actual de CrashDetector.
4. Seleccionar una API de forge en el desplegable correspondiente.
5. Introducir la URL del forge, el nombre del repositorio, el espacio de nombres o propietario si es necesario, y el token de API.
6. Permitir que CrashDetector cree el repositorio remoto.
7. CrashDetector guarda la URL remota devuelta como el `origin` local.
8. Realizar commits manualmente o habilitar commits automáticos.
9. Realizar push manualmente o habilitar push automático después de cada commit.

## Configuración del Repositorio Local

Utilice primero **Crear Repositorio Local**. Esto inicializa Git en la carpeta actual.

Después de este paso, la herramienta podrá:

* configurar un remoto llamado `origin`;
* realizar commits manuales;
* hacer push al remoto;
* realizar commits automáticos tras copias de seguridad;
* realizar push automático después de los commits.

Si el repositorio local ya existe, el botón de creación debería estar deshabilitado o resultar innecesario.

## Configuración Manual del Remoto

La configuración manual del remoto es la opción de respaldo.

Úsela cuando:

* la API del forge todavía no esté soportada;
* el repositorio ya exista;
* el token no tenga permisos para crear repositorios;
* el forge requiera un flujo de trabajo especial;
* quiera utilizar una URL remota no estándar.

Ejemplos típicos de remotos SSH:

```text
ssh://git@pagure.example.org/project.git
git@github.com:owner/project.git
git@gitlab.com:group/project.git
git@codeberg.org:owner/project.git
git@bitbucket.org:workspace/project.git
```

Ejemplos típicos de remotos HTTPS:

```text
https://github.com/owner/project.git
https://gitlab.com/group/project.git
https://codeberg.org/owner/project.git
```

Normalmente SSH es mejor una vez configuradas las claves. HTTPS suele ser más sencillo para una configuración inicial.

## Configuración Remota Mediante API

La configuración remota mediante API permite que CrashDetector cree el repositorio remoto automáticamente.

Campos compartidos:

```text
forgeUrl          URL base del forge.
token             Token de API o cadena de autenticación.
namespace         Espacio de nombres, organización, grupo, workspace o clave de proyecto.
propietario       Propietario u organización alternativa.
nombreRepositorio Nombre del repositorio.
descripcion       Descripción del repositorio.
urlProyecto       Sitio web opcional del proyecto.
defaultBranch     Normalmente main.
privado           Si el repositorio debe ser privado.
crearReadme       Si se debe inicializar con un README.
```

No todos los forges admiten todos los campos. CrashDetector enviará únicamente los campos que tengan sentido para cada forge.

## Orden Recomendado de Forges para CrashDetector y FeatureCreep

Para CrashDetector y FeatureCreep, el ecosistema preferido es:

1. Pagure, si existe un hogar adecuado alineado con Fedora, Red Hat o Enterprise Linux.
2. Pagure autoalojado, especialmente sobre Fedora, CentOS Stream, Rocky, Alma o servidores tipo RHEL.
3. Gitea / Forgejo, especialmente autoalojados o Codeberg para proyectos públicos pequeños.
4. GitHub, especialmente por visibilidad, almacenamiento, reportes de errores y desarrollo asistido por IA.
5. GitLab, especialmente cuando se desea un dominio personalizado o una plataforma DevOps autogestionada.
6. Bitbucket, especialmente en entornos empresariales que ya utilizan herramientas Atlassian.
7. Beanstalk, principalmente para equipos que ya utilizan Beanstalk.

# Pagure

Pagure es el forge más cercano al ecosistema de FeatureCreep y CrashDetector.

Es ligero, de código abierto, centrado en Git e históricamente muy conectado con la infraestructura de Fedora y Red Hat.

Pagure resulta especialmente atractivo para proyectos que se sienten culturalmente más próximos a Fedora, CentOS, Enterprise Linux, eventos de Red Hat, empaquetado de software libre e infraestructuras prácticas más pequeñas, en lugar de enormes plataformas comerciales.

Pagure no está tan pulido como GitHub, GitLab, Gitea o Forgejo. Puede ser más áspero en algunos aspectos y menos completo en ciertas áreas. Sin embargo, eso también forma parte de su encanto: es más simple, ligero y cercano al estilo tradicional de infraestructura del software libre.

Pagure es una excelente solución para autoalojamiento, especialmente cuando el servidor utiliza Fedora o una distribución de la familia Enterprise Linux.

Forges históricamente importantes relacionados con Pagure:

* `pagure.io`
* Infraestructura de paquetes y código fuente de Fedora.
* Infraestructura de código fuente de CentOS.
* Infraestructura de SUSE basada en un estilo inspirado en Pagure o relacionado con él.

CrashDetector utiliza Pagure por defecto porque encaja mejor con la cultura del proyecto. Personas de Red Hat, Fedora y CentOS aparecen frecuentemente en eventos de software libre como SCaLE, donde solemos hablar sobre Fedora, Pagure, CentOS, RHEL y otros temas relacionados. Además, el proyecto tiene una afinidad natural con ese ecosistema porque utilizamos herramientas de JBoss y Red Hat en muchos de nuestros proyectos, incluidos CrashDetector y FeatureCreep.

Utilizamos RHEL como sistema principal, con macOS/BSD, Solaris, Fedora, CentOS Stream y SUSE como alternativas cuando algún programa no funciona correctamente en RHEL. Si Red Hat, Fedora, CentOS o algún proyecto relacionado ofreciera un forge adecuado para CrashDetector o FeatureCreep en el futuro, migrar allí sería bienvenido. 

## Estado de Pagure.io

El forge público `pagure.io` tiene previsto ser retirado alrededor de Flock 2026.

Fedora ha estado trasladando el desarrollo específico de Fedora a una instancia de Forgejo, pero dicha instancia está destinada exclusivamente a proyectos de Fedora. Actualmente CrashDetector y FeatureCreep no cumplen los requisitos para alojarse allí.

Esto es desafortunado porque `pagure.io` encajaba culturalmente muy bien con este proyecto. 

## Pagure y Anubis

Pagure también funciona con la protección Anubis.

Anubis es especialmente memorable porque utiliza una página anti-bots con temática de chica anime. Esto encaja con la tradición de CrashDetector de combinar herramientas técnicas serias con una presentación más desenfadada.

## Configuración de la API de Pagure

Clase de CrashDetector:

```text
PagureGitForgeAPI
```

Forge por defecto:

```text
https://pagure.io
```

Endpoint de creación de repositorios:

```text
POST /api/0/new
```

Autenticación:

```text
Authorization: token TOKEN
```

Tipo de cuerpo:

```text
application/x-www-form-urlencoded
```

Campos importantes:

```text
name
description
private
create_readme
default_branch
namespace
url
wait
```

Correspondencia con CrashDetector:

```text
namespace         Espacio de nombres de Pagure, como rpms, modules, forks, o vacío para repositorios normales.
nombreRepositorio Nombre del proyecto.
descripcion       Descripción del proyecto.
urlProyecto       URL opcional del proyecto.
defaultBranch     Normalmente main.
privado           Si el proyecto es privado.
crearReadme       Si Pagure debe crear un README.
```

Para un proyecto Pagure normal, deje `namespace` vacío.

Ejemplo:

```text
forgeUrl:          https://pagure.io
namespace:
nombreRepositorio: crashdetector
defaultBranch:     main
privado:           false
crearReadme:       true
```

# Gitea y Forgejo

Gitea y Forgejo son excelentes opciones de forge de código abierto.

Ofrecen más funcionalidades en la interfaz web que Pagure y suelen resultar más amigables para usuarios ocasionales. También son mucho más completos para flujos de trabajo modernos de alojamiento Git, con configuraciones avanzadas, organizaciones, incidencias, pull requests, publicaciones, paquetes y acciones según la instancia utilizada.

Forgejo es una bifurcación de Gitea. Para los propósitos de la API de CrashDetector, ambos son lo suficientemente similares como para compartir una única implementación.

Clase de CrashDetector:

```text
GiteaGitForgeAPI
```

Nombre recomendado:

```text
Gitea / Forgejo
```

Forge público recomendado:

```text
https://codeberg.org
```

Codeberg es la instancia pública de Forgejo más conocida. Es excelente para proyectos libres y de código abierto, aunque no está pensado como almacenamiento general ilimitado. Para proyectos grandes, repositorios privados, binarios o recursos pesados, puede ser preferible alojar Forgejo por cuenta propia. 

# GitHub

GitHub es el forge público más grande y visible.

Es una buena opción para:

* visibilidad y descubrimiento del proyecto;
* contribuyentes externos;
* reportes de incidencias de usuarios que ya tienen cuentas de GitHub;
* mayor capacidad de alojamiento público en comparación con muchos forges comunitarios;
* flujos de trabajo asistidos por inteligencia artificial;
* GitHub Actions;
* visibilidad del proyecto;
* facilitar contribuciones de usuarios ocasionales.

GitHub es propiedad de Microsoft. GitHub generalmente no se considera un software de forge de código abierto en el mismo sentido que Pagure, Gitea o Forgejo, aunque una gran cantidad de proyectos de software libre se alojan allí. Además, GitHub y Microsoft suelen aparecer en eventos de código abierto como SCaLE, donde frecuentemente cuentan con actividades, patrocinios y material promocional interesante. 

Clase de CrashDetector:

```text
GitHubGitForgeAPI
```

Forge predeterminado:

```text
https://github.com
```

Endpoint para repositorios personales:

```text
POST https://api.github.com/user/repos
```

Endpoint para repositorios de organizaciones:

```text
POST https://api.github.com/orgs/{org}/repos
```

Autenticación:

```text
Authorization: Bearer TOKEN
```

Cabeceras importantes:

```text
Accept: application/vnd.github+json
X-GitHub-Api-Version: 2026-03-10
```

Correspondencia con CrashDetector:

```text
namespace         Organización alternativa.
propietario       Nombre de la organización.
nombreRepositorio Nombre del repositorio.
descripcion       Descripción del repositorio.
urlProyecto       Campo homepage de GitHub.
defaultBranch     No siempre se envía durante la creación inicial.
privado           Indicador de repositorio privado.
crearReadme       auto_init.
```

Para un repositorio personal de GitHub, deje `namespace` y `propietario` vacíos.

Para un repositorio de organización, establezca `namespace` o `propietario` con el nombre de la organización.

Utilice GitHub cuando:

* quiera que la mayor cantidad posible de usuarios encuentre el proyecto;
* quiera facilitar la creación de incidencias por parte de los usuarios;
* necesite GitHub Actions;
* quiera que herramientas de IA comprendan más fácilmente el repositorio;
* desee utilizar la plataforma más conocida dentro del ecosistema de desarrollo.

---

# GitLab

GitLab es tanto un forge público como una plataforma DevOps autoalojable.

GitLab resulta útil cuando:

* desea alojamiento Git y CI/CD en una sola plataforma;
* necesita una instalación autogestionada;
* desea utilizar GitLab.com;
* desea utilizar GitLab Dedicated;
* necesita un dominio personalizado;
* desea herramientas de gestión de proyectos más integradas que las de un forge minimalista.

CrashDetector utiliza una única clase para GitLab porque GitLab.com, GitLab Self-Managed y GitLab Dedicated comparten esencialmente la misma estructura de API REST. 

Clase de CrashDetector:

```text
GitLabGitForgeAPI
```

Forge predeterminado:

```text
https://gitlab.com
```

Base de la API:

```text
https://gitlab.com/api/v4
https://tu-gitlab.ejemplo.com/api/v4
```

Endpoint de creación de proyectos/repositorios:

```text
POST /projects
```

Autenticación:

```text
PRIVATE-TOKEN: TOKEN
```

Campos importantes:

```text
name
path
description
visibility
initialize_with_readme
namespace_id
default_branch
```

Correspondencia con CrashDetector:

```text
namespace         namespace_id numérico de GitLab para un grupo, si es necesario.
nombreRepositorio Nombre y ruta del proyecto.
descripcion       Descripción del proyecto.
defaultBranch     Normalmente main.
privado           visibility = private o public.
crearReadme       initialize_with_readme.
```

### Nota importante sobre GitLab

Para crear un proyecto dentro de un grupo de GitLab, GitLab requiere un `namespace_id` numérico, no solamente el nombre o ruta del grupo.

Si deja `namespace` vacío, el proyecto se creará bajo el usuario autenticado.

Utilice GitLab cuando:

* necesite CI/CD avanzado;
* quiera autoalojar una plataforma empresarial bien integrada;
* necesite un dominio personalizado;
* su organización ya utilice GitLab.

---

# Bitbucket Cloud

Bitbucket Cloud es el forge Git alojado de Atlassian.

Es muy común en entornos empresariales, especialmente cuando ya se utilizan Jira, Confluence u otras herramientas de Atlassian. Aunque tiene menos presencia en comunidades de software libre que GitHub o Codeberg, sigue siendo muy frecuente en equipos de desarrollo corporativos. 

Clase de CrashDetector:

```text
BitbucketCloudGitForgeAPI
```

Forge predeterminado:

```text
https://bitbucket.org
```

Base de la API:

```text
https://api.bitbucket.org/2.0
```

Endpoint de creación de repositorios:

```text
POST /repositories/{workspace}/{repo_slug}
```

Métodos de autenticación utilizados por CrashDetector:

```text
username:app_password
bearer:TOKEN
TOKEN
```

Si el token contiene dos puntos (`:`), CrashDetector lo interpreta como autenticación Basic:

```text
username:app_password
```

Si comienza con `bearer:`, se utiliza autenticación Bearer.

Campos importantes:

```text
scm
name
description
is_private
website
```

Correspondencia con CrashDetector:

```text
namespace         Workspace de Bitbucket.
propietario       Workspace alternativo.
nombreRepositorio Nombre del repositorio.
descripcion       Descripción del repositorio.
urlProyecto       Campo website.
privado           is_private.
```

### Nota importante sobre Bitbucket Cloud

`namespace` es obligatorio porque Bitbucket Cloud crea los repositorios dentro de un workspace.

Utilice Bitbucket Cloud cuando:

* su empresa utiliza herramientas Atlassian;
* la integración con Jira es importante;
* el proyecto está orientado al trabajo empresarial más que a la comunidad abierta;
* su equipo ya utiliza workspaces y contraseñas de aplicación de Bitbucket.

---

# Bitbucket Server / Data Center

Bitbucket Server y Bitbucket Data Center son productos autoalojados de Atlassian.

No deben combinarse con Bitbucket Cloud en el código porque sus APIs son diferentes. Bitbucket Cloud utiliza rutas basadas en workspace y repo slug, mientras que Server/Data Center utiliza claves de proyecto y el patrón `/rest/api`. 

Clase de CrashDetector:

```text
BitbucketServerGitForgeAPI
```

Forge predeterminado:

```text
https://bitbucket.example.com
```

Endpoint de creación de repositorios:

```text
POST /rest/api/1.0/projects/{projectKey}/repos
```

Métodos de autenticación utilizados por CrashDetector:

```text
username:password_or_token
bearer:TOKEN
TOKEN
```

Si el token contiene dos puntos (`:`), CrashDetector utiliza autenticación Basic.

Campos importantes:

```text
name
scmId
forkable
public
description
```

Correspondencia con CrashDetector:

```text
namespace         Clave del proyecto.
propietario       Clave del proyecto alternativa.
nombreRepositorio Nombre del repositorio.
descripcion       Descripción del repositorio.
privado           public = false.
```

Utilice Bitbucket Server / Data Center cuando:

* la empresa ya dispone de una infraestructura Atlassian autoalojada;
* los repositorios están organizados mediante claves de proyecto;
* el equipo utiliza Jira y flujos de trabajo empresariales de Atlassian;
* el código es interno o corporativo. 




# Beanstalk

Beanstalk es un servicio alojado orientado al control de código fuente y despliegue. No ocupa una posición tan central dentro del ecosistema moderno de software libre como GitHub, GitLab, Pagure o Forgejo, pero puede resultar útil para equipos que ya utilizan cuentas de Beanstalk. 

Clase de CrashDetector:

```text
BeanstalkGitForgeAPI
```

Formato de forge predeterminado:

```text
https://account.beanstalkapp.com
```

Endpoint para creación de repositorios:

```text
POST /api/repositories.json
```

Autenticación:

```text
Basic username:access_token
```

CrashDetector espera:

```text
token = username:access_token
```

Estructura importante del cuerpo de la petición:

```text
{
  "repository": {
    "type_id": "git",
    "name": "project",
    "title": "Project",
    "description": "Project description",
    "color_label": "label-blue"
  }
}
```

Correspondencia con CrashDetector:

```text
forgeUrl          URL de la cuenta de Beanstalk.
token             username:access_token.
nombreRepositorio Nombre del repositorio.
descripcion       Título y descripción del repositorio.
defaultBranch     Opcional si es compatible.
```

### Nota importante sobre Beanstalk

Beanstalk utiliza subdominios por cuenta. Debe introducir la URL de la cuenta específica, no un host API central genérico.

Utilice Beanstalk cuando:

* el equipo ya utiliza Beanstalk;
* los flujos de despliegue específicos de Beanstalk son importantes;
* el proyecto es privado y orientado al trabajo en equipo;
* necesita compatibilidad con una infraestructura Beanstalk existente.

---

# Valores Predeterminados Recomendados

Registro sugerido:

```java
public static void registrarPredeterminados() {
	registrar(new PagureGitForgeAPI());
	registrar(new GitHubGitForgeAPI());
	registrar(new GitLabGitForgeAPI());
	registrar(new GiteaGitForgeAPI());
	registrar(new BitbucketCloudGitForgeAPI());
	registrar(new BitbucketServerGitForgeAPI());
	registrar(new BeanstalkGitForgeAPI());
}
```

Orden sugerido de los forges en la interfaz gráfica:

```text
Pagure
Gitea / Forgejo
GitHub
GitLab
Bitbucket Cloud
Bitbucket Server / Data Center
Beanstalk
```

URLs predeterminadas sugeridas:

```text
Pagure:                         https://pagure.io
Gitea / Forgejo:                https://codeberg.org
GitHub:                         https://github.com
GitLab:                         https://gitlab.com
Bitbucket Cloud:                https://bitbucket.org
Bitbucket Server / Data Center: https://bitbucket.example.com
Beanstalk:                      https://account.beanstalkapp.com
```

---

# ¿Qué Forge Debería Elegir?

## Elija Pagure si:

* desea la opción más alineada con Fedora, Red Hat y Enterprise Linux;
* desea un forge ligero y de código abierto;
* aloja sus servicios en Fedora, CentOS Stream, RHEL, Rocky o Alma;
* prefiere una infraestructura práctica frente a interfaces llamativas;
* se siente cómodo con algunas asperezas o limitaciones.

## Elija Gitea / Forgejo si:

* desea un forge moderno y de código abierto;
* quiere una interfaz más agradable que la de Pagure;
* necesita compatibilidad con Codeberg;
* desea autoalojar la plataforma;
* quiere algo parecido a GitHub pero libre y de código abierto.

## Elija GitHub si:

* quiere que los colaboradores encuentren fácilmente el proyecto;
* necesita gran capacidad de alojamiento público;
* desea utilizar GitHub Actions;
* quiere que herramientas de IA trabajen fácilmente con el repositorio;
* desea utilizar la plataforma que la mayoría de los desarrolladores ya conocen.

## Elija GitLab si:

* desea Git junto con capacidades DevOps;
* CI/CD es una prioridad importante;
* necesita un dominio personalizado;
* desea utilizar GitLab.com o una instancia GitLab autogestionada;
* quiere una plataforma empresarial todo en uno.

## Elija Bitbucket si:

* su empresa utiliza herramientas Atlassian;
* la integración con Jira es importante;
* el proyecto pertenece a un entorno corporativo;
* el proyecto está más orientado a empresa que a comunidad.

## Elija Beanstalk si:

* su equipo ya utiliza Beanstalk;
* necesita flujos de despliegue específicos de Beanstalk;
* mantiene infraestructura heredada basada en Beanstalk;
* necesita compatibilidad con implementaciones existentes de Beanstalk.

---

# Seguridad de Tokens

Nunca incluya tokens de API en commits.

No publique tokens en:

* registros (logs);
* informes de fallos;
* capturas de pantalla;
* Discord;
* incidencias de GitHub;
* tickets de soporte.

Si sospecha que un token se ha filtrado:

1. Revóquelo inmediatamente.
2. Cree uno nuevo.
3. Revise los registros de acceso del repositorio si el forge los proporciona.
4. Sustituya el token en la configuración de CrashDetector.

Los tokens deben tener únicamente los permisos mínimos necesarios. Para crear repositorios normalmente se requieren permisos de administración o creación de repositorios.

---

# Resolución de Problemas Comunes

## El botón de crear repositorio remoto no hace nada

Compruebe que:

* las dependencias JGit están instaladas;
* existe un repositorio Git local;
* se ha seleccionado un forge;
* el token no está vacío;
* el namespace, workspace o clave de proyecto está configurado cuando sea obligatorio.

## La API devuelve 401

El token está ausente, expirado, es incorrecto o se está enviando con un formato de autenticación incorrecto.

Verifique el formato de token requerido por el forge correspondiente.

## La API devuelve 403

El token es válido, pero no tiene permisos para crear repositorios.

Para organizaciones, grupos, workspaces o proyectos, la cuenta debe disponer de permisos suficientes.

## La API devuelve 404

Normalmente alguno de los siguientes valores es incorrecto:

* URL del forge;
* ruta de la API;
* namespace;
* organización;
* workspace;
* clave de proyecto;
* subdominio de la cuenta.

## La API devuelve 409

Ya existe un repositorio con ese nombre.

Utilice la configuración manual del remoto o elija otro nombre.

## El repositorio remoto se creó, pero el push falla

Compruebe que:

* la clave SSH está añadida a la cuenta del forge;
* la URL remota devuelta utiliza SSH o HTTPS según corresponda;
* la rama local existe;
* el token o clave SSH tiene permisos de escritura;
* las reglas de protección de ramas no bloquean los pushes directos.

## La URL remota está vacía

Algunas APIs devuelven las URLs de clonación en campos distintos.

CrashDetector intenta priorizar URLs SSH y, si no existen, utilizar URLs HTTPS.

Si no se devuelve ninguna URL remota, configure manualmente el remoto utilizando la URL de clonación proporcionada por el forge.

---

# Notas para Desarrollo Futuro

Posibles mejoras útiles:

* un selector de tipo de token en lugar de reutilizar el campo `token`;
* etiquetas independientes para namespace, propietario, workspace, clave de proyecto y organización;
* mejor análisis JSON mediante bibliotecas opcionales cuando estén disponibles;
* un botón para probar tokens;
* un botón para verificar si el repositorio existe;
* detección automática de claves SSH;
* campos de contraseña para ocultar tokens;
* ayudas contextuales específicas para cada forge;
* soporte para múltiples remotos y espejado;
* creación automática de incidencias o páginas de lanzamiento después de crear el repositorio.

---

# Recomendación Final

Para CrashDetector y FeatureCreep, Pagure debería seguir siendo la opción predeterminada desde un punto de vista filosófico, porque encaja mejor con el ecosistema Fedora, Red Hat y Enterprise Linux. Es ligero, de código abierto, autoalojable y culturalmente cercano a los sistemas donde este proyecto encaja de forma natural. 

Dado que `pagure.io` está siendo retirado, las configuraciones prácticas también deberían ofrecer compatibilidad con Forgejo, GitHub, GitLab, Bitbucket y Beanstalk.

La configuración ideal a largo plazo probablemente sea:

* **Pagure** para entornos autoalojados alineados con Fedora y Red Hat.
* **Forgejo o Gitea** para autoalojamiento moderno de software libre.
* **GitHub** para visibilidad y contribuciones.
* **GitLab** para entornos con fuerte enfoque DevOps.
* **Bitbucket** para organizaciones que utilizan Atlassian.
* **Beanstalk** para equipos que ya trabajan dentro del ecosistema Beanstalk. 


