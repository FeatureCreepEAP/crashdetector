# CrashDetector Git Tool and Remote Forge Setup Guide

CrashDetector includes a Git tool for creating a local Git repository, creating or connecting a remote repository, committing changes, and pushing backups or project history to a remote forge.

A **forge** is a Git hosting service with extra project tools, usually including a web interface, issue tracking, pull requests or merge requests, user accounts, permissions, and repository management. Examples include Pagure, GitHub, GitLab, Gitea, Forgejo, Bitbucket, and Beanstalk.

The CrashDetector Git tool does not depend on one single hosting provider. It uses a small API abstraction called `GitForgeAPI`, so different forges can create remote repositories in their own way while the GUI stays mostly the same.

## Basic Git Tool Workflow

The normal workflow is:

1. Open the CrashDetector Git tool.
2. Install or download missing JGit dependencies if the GUI says they are missing.
3. Create a local Git repository in the current CrashDetector working folder.
4. Choose a forge API from the forge dropdown.
5. Enter the forge URL, repository name, namespace or owner if needed, and API token.
6. Let CrashDetector create the remote repository.
7. CrashDetector saves the returned remote URL as the local `origin`.
8. Commit manually, or enable automatic commits.
9. Push manually, or enable automatic push after commit.

## Local Repository Setup

Use **Create Local Repository** first. This initializes Git in the current folder.

After this step, the tool can:

* set a remote called `origin`;
* make a manual commit;
* push to the remote;
* optionally auto-commit after backups;
* optionally auto-push after commits.

If the local repository already exists, the create button should be disabled or unnecessary.

## Manual Remote Setup

Manual remote setup is the fallback option.

Use it when:

* the forge API is not supported yet;
* the repository already exists;
* the token does not have permission to create repositories;
* the forge requires a special workflow;
* you want to use a nonstandard remote URL.

Typical SSH remote examples:

```text
ssh://git@pagure.example.org/project.git
git@github.com:owner/project.git
git@gitlab.com:group/project.git
git@codeberg.org:owner/project.git
git@bitbucket.org:workspace/project.git
```

Typical HTTPS remote examples:

```text
https://github.com/owner/project.git
https://gitlab.com/group/project.git
https://codeberg.org/owner/project.git
```

SSH is usually better once keys are configured. HTTPS can be simpler for first-time setup.

## API Remote Setup

API remote setup lets CrashDetector create the remote repository for you.

The shared config fields are:

```text
forgeUrl          The base forge URL.
token             API token or authentication string.
namespace         Forge-specific namespace, organization, group, workspace, or project key.
propietario       Owner or organization fallback.
nombreRepositorio Repository name.
descripcion       Repository description.
urlProyecto       Optional project website.
defaultBranch     Usually main.
privado           Whether the remote should be private.
crearReadme       Whether to initialize the repository with a README.
```

Not every forge supports every field. CrashDetector sends the fields that make sense for that forge.

## Recommended Forge Order for CrashDetector and FeatureCreep

For CrashDetector and FeatureCreep, the preferred ecosystem is:

1. Pagure, if a suitable Fedora / Red Hat / Enterprise Linux-aligned home is available.
2. Self-hosted Pagure, especially on Fedora, CentOS Stream, Rocky, Alma, or RHEL-style servers.
3. Gitea / Forgejo, especially self-hosted or Codeberg for smaller public projects.
4. GitHub, especially for visibility, storage, issue reports, and AI-assisted development.
5. GitLab, especially when a custom domain or self-managed DevOps setup is wanted.
6. Bitbucket, especially in company environments already using Atlassian tools.
7. Beanstalk, mostly for teams already using Beanstalk.

## Pagure

Pagure is the forge closest to the FeatureCreep and CrashDetector ecosystem.

It is lightweight, open-source, Git-centered, and historically very connected to Fedora and Red Hat infrastructure. Pagure is especially attractive for projects that feel culturally closer to Fedora, CentOS, Enterprise Linux, Red Hat events, open-source packaging, and smaller practical infrastructure instead of giant commercial platforms.

Pagure is not as polished as GitHub, GitLab, Gitea, or Forgejo. It can be rougher around the edges, and in some areas it is less complete. That is also part of its charm: it is simpler, lighter, and closer to the traditional open-source infrastructure style.

Pagure is a great solution for self-hosting, especially when the server is running Fedora or an Enterprise Linux family system.

Historically important Pagure-related forges included:

* `pagure.io`
* Fedora package/source infrastructure
* CentOS source infrastructure
* SUSE source infrastructure using a Pagure-inspired or related forge style

CrashDetector defaults to Pagure because Pagure fits the project’s culture best. Red Hat, Fedora, and CentOS people frequently appear at open-source events such as SCaLE where we woul often talk with them about Fedora and Pagure and Centos and RHEL and other topics, and this project has a natural affinity with that ecosystem because we use JBoss and Red Hat tools in many of our projects including CrashDetector and FeatureCreep. We daily drive RHEL using MacOS/BSD, Solaris, and Fedora,CentosStream,SUSE as fallbacks when some program we want does not work on RHEL. If Red Hat, Fedora, CentOS, or a related project provides a suitable forge for CrashDetector or FeatureCreep in the future, migrating there would be welcome.

### Pagure.io Status

The public `pagure.io` forge has been planned for decommissioning around Flock 2026. Fedora has been moving Fedora-specific development to a Forgejo instance, but that instance is intended for Fedora-specific work only. CrashDetector and FeatureCreep do not currently qualify for that Fedora-specific Forgejo home.

This is unfortunate because `pagure.io` was a very good cultural fit for this project.

### Pagure and Anubis

Pagure also works with Anubis protection. Anubis is memorable because it uses an anime-girl-themed anti-bot page. That fits the general CrashDetector tradition of mixing serious technical tools with playful presentation.

### Pagure API Setup

CrashDetector class:

```text
PagureGitForgeAPI
```

Default forge:

```text
https://pagure.io
```

Repository creation endpoint:

```text
POST /api/0/new
```

Authentication:

```text
Authorization: token TOKEN
```

Body type:

```text
application/x-www-form-urlencoded
```

Important fields:

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

CrashDetector mapping:

```text
namespace         Pagure namespace, such as rpms, modules, forks, or blank for normal repo.
nombreRepositorio Project name.
descripcion       Project description.
urlProyecto       Optional project URL.
defaultBranch     Usually main.
privado           Whether the project is private.
crearReadme       Whether Pagure should create a README.
```

For a normal Pagure project, leave `namespace` blank.

Example:

```text
forgeUrl:          https://pagure.io
namespace:
nombreRepositorio: crashdetector
defaultBranch:     main
privado:           false
crearReadme:       true
```

## Gitea and Forgejo

Gitea and Forgejo are excellent open-source forge options.

They are more feature-rich in the web GUI than Pagure and are usually friendlier for casual users. They are also much more complete for modern Git hosting workflows, with richer project settings, organizations, issue tools, pull requests, releases, packages, and actions depending on the instance.

Forgejo is a fork of Gitea. For CrashDetector’s API purposes, they are similar enough to share one implementation.

CrashDetector class:

```text
GiteaGitForgeAPI
```

Recommended display name:

```text
Gitea / Forgejo
```

Recommended public default:

```text
https://codeberg.org
```

Codeberg is the most famous public Forgejo instance. It is excellent for free/open-source projects, but it is not meant to be unlimited general-purpose storage. For large projects, private repositories, binaries, or heavy assets, self-hosting Forgejo may be better.

Repository creation endpoints:

```text
POST /api/v1/user/repos
POST /api/v1/orgs/{org}/repos
```

Authentication:

```text
Authorization: token TOKEN
```

Important fields:

```text
name
description
private
auto_init
default_branch
website
```

CrashDetector mapping:

```text
namespace         Organization name, if creating under an organization.
propietario       Fallback organization owner.
nombreRepositorio Repository name.
descripcion       Repository description.
urlProyecto       Website field.
defaultBranch     Usually main.
privado           Private repository flag.
crearReadme       auto_init.
```

Use Gitea / Forgejo when:

* you want an open-source GitHub-like experience;
* you want a rich GUI;
* you want to self-host;
* you want Codeberg compatibility;
* you want a more modern and complete web forge than Pagure.

Use Pagure instead when:

* you want something lighter;
* Fedora / Red Hat / Enterprise Linux alignment matters;
* you prefer simpler infrastructure;
* you are comfortable with rougher edges.

## GitHub

GitHub is the largest and most visible public forge.

It is good for:

* discoverability;
* outside contributors;
* issue reports from users who already have GitHub accounts;
* large public storage compared with smaller community forges;
* AI-assisted coding workflows;
* GitHub Actions;
* project visibility;
* easier contribution from random users.

GitHub is owned by Microsoft. GitHub itself is not generally treated as an open-source forge platform in the same sense as Pagure, Gitea, or Forgejo, although many open-source projects are hosted on it and github and Microsoft also show up at open source events like SCALE where they often have cool merch and activities an are sponsors.

CrashDetector class:

```text
GitHubGitForgeAPI
```

Default forge:

```text
https://github.com
```

Personal repository endpoint:

```text
POST https://api.github.com/user/repos
```

Organization repository endpoint:

```text
POST https://api.github.com/orgs/{org}/repos
```

Authentication:

```text
Authorization: Bearer TOKEN
```

Important headers:

```text
Accept: application/vnd.github+json
X-GitHub-Api-Version: 2026-03-10
```

CrashDetector mapping:

```text
namespace         Organization fallback.
propietario       Organization name.
nombreRepositorio Repository name.
descripcion       Repository description.
urlProyecto       GitHub homepage field.
defaultBranch     Not always sent during initial creation.
privado           Private repository flag.
crearReadme       auto_init.
```

For a personal GitHub repository, leave `namespace` and `propietario` blank.

For an organization repository, set `namespace` or `propietario` to the organization name.

Use GitHub when:

* you want the most users to find the project;
* you want lots of users to easily report issues;
* you want GitHub Actions;
* you want AI coding tools to understand the repository more easily;
* you want broad open-source event familiarity.

## GitLab

GitLab is both a public forge and a self-hostable DevOps platform.

GitLab is useful when:

* you want Git hosting plus CI/CD in one platform;
* you want a self-managed installation;
* you want GitLab.com;
* you want GitLab Dedicated;
* you want a custom domain;
* you want more integrated project management than a minimal forge.

CrashDetector uses one GitLab class because GitLab.com, GitLab Self-Managed, and GitLab Dedicated use the same general REST API shape.

CrashDetector class:

```text
GitLabGitForgeAPI
```

Default forge:

```text
https://gitlab.com
```

API base:

```text
https://gitlab.com/api/v4
https://your-gitlab.example.com/api/v4
```

Repository/project creation endpoint:

```text
POST /projects
```

Authentication:

```text
PRIVATE-TOKEN: TOKEN
```

Important fields:

```text
name
path
description
visibility
initialize_with_readme
namespace_id
default_branch
```

CrashDetector mapping:

```text
namespace         Numeric GitLab namespace_id for a group, if needed.
nombreRepositorio Project name and path.
descripcion       Project description.
defaultBranch     Usually main.
privado           visibility = private or public.
crearReadme       initialize_with_readme.
```

Important GitLab note:

For creating a project inside a GitLab group, GitLab wants a numeric `namespace_id`, not only a group path. If you leave `namespace` blank, the project is created under the authenticated user.

Use GitLab when:

* you want serious CI/CD;
* you want self-hosting with a polished enterprise feature set;
* you want a custom domain;
* your project or organization already uses GitLab.

## Bitbucket Cloud

Bitbucket Cloud is Atlassian’s hosted Git forge.

It is common in company environments, especially where teams already use Jira, Confluence, or other Atlassian tools. It is less culturally central to hobbyist open-source than GitHub or Codeberg, but it is very common in business software teams.

CrashDetector class:

```text
BitbucketCloudGitForgeAPI
```

Default forge:

```text
https://bitbucket.org
```

API base:

```text
https://api.bitbucket.org/2.0
```

Repository creation endpoint:

```text
POST /repositories/{workspace}/{repo_slug}
```

Authentication options used by CrashDetector:

```text
username:app_password
bearer:TOKEN
TOKEN
```

If the token contains a colon, CrashDetector treats it as Basic auth:

```text
username:app_password
```

If it starts with `bearer:`, CrashDetector sends Bearer auth.

Important fields:

```text
scm
name
description
is_private
website
```

CrashDetector mapping:

```text
namespace         Bitbucket workspace.
propietario       Workspace fallback.
nombreRepositorio Repository name.
descripcion       Repository description.
urlProyecto       Website field.
privado           is_private.
```

Important Bitbucket Cloud note:

`namespace` is required because Bitbucket Cloud creates repositories inside a workspace.

Use Bitbucket Cloud when:

* your company uses Atlassian tools;
* Jira integration matters;
* the project is work-oriented rather than public-community-oriented;
* your team already has Bitbucket workspaces and app passwords.

## Bitbucket Server / Data Center

Bitbucket Server and Bitbucket Data Center are self-managed Atlassian products.

They should not be combined with Bitbucket Cloud in code because the API paths are different. Cloud uses workspace/repo slug paths. Server/Data Center uses project keys and the `/rest/api` pattern.

CrashDetector class:

```text
BitbucketServerGitForgeAPI
```

Default forge:

```text
https://bitbucket.example.com
```

Repository creation endpoint:

```text
POST /rest/api/1.0/projects/{projectKey}/repos
```

Authentication options used by CrashDetector:

```text
username:password_or_token
bearer:TOKEN
TOKEN
```

If the token contains a colon, CrashDetector treats it as Basic auth.

Important fields:

```text
name
scmId
forkable
public
description
```

CrashDetector mapping:

```text
namespace         Project key.
propietario       Project key fallback.
nombreRepositorio Repository name.
descripcion       Repository description.
privado           public = false.
```

Use Bitbucket Server / Data Center when:

* a company already has a self-hosted Atlassian environment;
* repositories are organized under project keys;
* the team uses Jira and enterprise Atlassian workflows;
* the code is internal or corporate.

## Beanstalk

Beanstalk is a hosted source-control and deployment-oriented service. It is not as culturally central to modern open-source as GitHub, GitLab, Pagure, or Forgejo, but it can be useful for teams already using Beanstalk accounts.

CrashDetector class:

```text
BeanstalkGitForgeAPI
```

Default forge format:

```text
https://account.beanstalkapp.com
```

Repository creation endpoint:

```text
POST /api/repositories.json
```

Authentication:

```text
Basic username:access_token
```

CrashDetector expects:

```text
token = username:access_token
```

Important body shape:

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

CrashDetector mapping:

```text
forgeUrl          Beanstalk account URL.
token             username:access_token.
nombreRepositorio Repository name.
descripcion       Repository title and description.
defaultBranch     Optional if supported.
```

Important Beanstalk note:

Beanstalk uses account subdomains. You must enter the account URL, not a generic central API host.

Use Beanstalk when:

* the team already uses Beanstalk;
* Beanstalk deployment workflows matter;
* the project is private/team-oriented;
* you need compatibility with an existing Beanstalk account.

## Recommended Defaults

Suggested registry:

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

Suggested default forge order in the GUI:

```text
Pagure
Gitea / Forgejo
GitHub
GitLab
Bitbucket Cloud
Bitbucket Server / Data Center
Beanstalk
```

Suggested default URLs:

```text
Pagure:                         https://pagure.io
Gitea / Forgejo:                https://codeberg.org
GitHub:                         https://github.com
GitLab:                         https://gitlab.com
Bitbucket Cloud:                https://bitbucket.org
Bitbucket Server / Data Center: https://bitbucket.example.com
Beanstalk:                      https://account.beanstalkapp.com
```

## Which Forge Should I Pick?

### Pick Pagure if:

* you want the most Fedora / Red Hat / Enterprise Linux-aligned option;
* you want a lightweight open-source forge;
* you are self-hosting on Fedora, CentOS Stream, RHEL, Rocky, or Alma;
* you prefer practical infrastructure over glossy interfaces;
* you are comfortable with some rough edges.

### Pick Gitea / Forgejo if:

* you want a modern open-source forge;
* you want a nicer GUI than Pagure;
* you want Codeberg compatibility;
* you want to self-host;
* you want something GitHub-like but free/open-source.

### Pick GitHub if:

* you want contributors and issue reporters to find the project easily;
* you want large public hosting;
* you want GitHub Actions;
* you want AI coding tools to work with the project easily;
* you want the default place many developers already know.

### Pick GitLab if:

* you want Git plus DevOps;
* CI/CD matters a lot;
* you want a custom domain;
* you want GitLab.com or a self-managed GitLab;
* you want an enterprise-ready all-in-one platform.

### Pick Bitbucket if:

* your company uses Atlassian tools;
* Jira integration matters;
* the project belongs in a company workspace;
* the project is more corporate than community-driven.

### Pick Beanstalk if:

* your team already uses Beanstalk;
* you need Beanstalk-specific deployment workflows;
* you are supporting legacy or existing Beanstalk infrastructure.

## Token Safety

Never commit API tokens.

Do not paste tokens into logs, crash reports, screenshots, Discord, GitHub issues, or support tickets.

If a token may have leaked:

1. revoke it immediately;
2. create a new token;
3. check repository access logs if the forge supports it;
4. replace the token in CrashDetector configuration.

Tokens should have the smallest permissions needed. For creating repositories, the token usually needs repository creation or administration rights.

## Common Troubleshooting

### The create remote button does nothing

Check:

* JGit dependencies are installed;
* a local Git repository already exists;
* a forge is selected;
* the token is not blank;
* the namespace/workspace/project key is filled when required.

### The API returns 401

The token is missing, expired, wrong, or sent in the wrong auth format.

Check the forge-specific token format.

### The API returns 403

The token is valid but does not have permission to create repositories.

For organizations, groups, workspaces, and projects, the account must have permission to create repositories there.

### The API returns 404

Usually one of these is wrong:

* forge URL;
* API path;
* namespace;
* organization;
* workspace;
* project key;
* account subdomain.

### The API returns 409

A repository with that name already exists.

Use manual remote setup, or choose a different repository name.

### The remote was created but push fails

Check:

* SSH key is added to the forge account;
* the returned remote URL is SSH or HTTPS as expected;
* the local branch exists;
* the token or SSH key has write access;
* protected branch rules do not block direct pushes.

### The remote URL is blank

Some APIs return clone URLs in different fields. CrashDetector tries to prefer SSH URLs and then falls back to HTTPS URLs where possible. If no remote is returned, manually set the remote using the forge’s clone URL.

## Notes for Future Development

Useful future improvements:

* a token type dropdown instead of overloading the `token` field;
* separate labels for namespace, owner, workspace, project key, and organization;
* better JSON parsing using an optional JSON library when available;
* a “test token” button;
* a “check repository exists” button for every forge;
* automatic SSH key detection;
* safer token entry using a password field;
* a help tooltip per forge;
* support for mirroring to more than one remote;
* support for creating issues or release pages after repository creation.

## Final Recommendation

For CrashDetector and FeatureCreep, Pagure should remain the spiritual default because it fits the Fedora / Red Hat / Enterprise Linux side of the open-source world best. It is lightweight, open-source, self-hostable, and culturally close to the systems where this project naturally belongs.

Because public `pagure.io` is being decommissioned, the practical defaults should also support Forgejo, GitHub, GitLab, Bitbucket, and Beanstalk. The best long-term setup is probably:

* Pagure for Fedora/Red Hat-aligned self-hosting;
* Forgejo or Gitea for modern open-source self-hosting;
* GitHub for visibility and contributors;
* GitLab for DevOps-heavy setups;
* Bitbucket for company/Atlassian environments;
* Beanstalk for teams already using Beanstalk.

