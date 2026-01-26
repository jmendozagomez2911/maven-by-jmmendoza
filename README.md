# Maven Inside (Didactic Multi-Module)

This repo is a didactic project to learn Maven concepts by running real commands. It is intentionally small and focused on **dependency behavior, scopes, lifecycles, BOMs, and packaging** rather than app complexity.

> Note: Existing folders `maven-java/` and `maven-scala/` are legacy and **not part of the new build**. The learning project lives under `modules/`.

## Project layout

```
modules/
  project-bom/   -> BOM module (dependency versions only)
  lib-old/       -> internal lib using commons-lang3 3.10.0
  lib-new/       -> internal lib using commons-lang3 3.12.0 (via BOM)
  app-cli/       -> CLI app with main(), thin JAR + fat JAR
  dist/          -> assembly module that builds a tar.gz distribution
system-libs/
  system-helpers.jar  -> local JAR for system scope demo (discouraged)
```

## Mapping to documentation topics

- **Repositories, mirrors, local repo**: see `documentation/0-UnderstandingMavenRepositories.md` and `documentation/compatibility/configurationOfMavenRepositories.md`.
- **POM + effective POM**: see `documentation/1-POMStructure.md` and run `mvn help:effective-pom`.
- **Dependency scopes and Dependency Plugin goals**: see `documentation/2-dependencies.md`.
- **Build lifecycles + default plugin bindings**: see `documentation/3-MavenBuildLifecycles.md`.
- **Archetypes**: see `documentation/4-MavenArchetypes.md`.
- **Enforcer rules**: see `documentation/5-MavenEnforcerPlugin.md`.
- **BOM vs parent dependencyManagement**: see `documentation/6-UnderstandingBOM.md`.
- **Release tar.gz flow**: see `documentation/Release.md`.
- **Java version compatibility**: see `documentation/compatibility/compatibilityJVMVersions.md`.

### mavenArchitecture.md mapping

There is no `mavenArchitecture.md` file in this repository. The mapping above uses the existing documentation files in `documentation/` as the source of truth for the required concepts.

## Modules (why each exists)

- `project-bom`: A BOM that centralizes dependency versions. It is **imported** with `<type>pom</type>` + `<scope>import</scope>`.
- `lib-old`: Depends on **commons-lang3 3.10.0** to create a **real transitive conflict**.
- `lib-new`: Depends on **commons-lang3 3.12.0** (via the BOM).
- `app-cli`: Depends on **lib-old + lib-new** to trigger the conflict. Produces:
  - thin JAR + `target/lib` dependencies (classpath demo)
  - fat JAR via `maven-shade-plugin`
- `dist`: Uses `maven-assembly-plugin` to build a `.tar.gz` with JARs, `lib/`, config, and scripts.

## Build modes: conflict vs fixed

This project uses Maven profiles to show dependency conflict resolution and fixes.

- **Conflict mode** (default): no forced version, so Maven resolves to the *nearest* version. In this graph, **lib-old wins** because it is declared first.
- **Fixed mode** (`-Pfixed`): uses `<dependencyManagement>` to **force** commons-lang3 to 3.12.0 across the graph.

Profiles:

- `-Pconflict` (active by default)
- `-Pfixed`
- `-Pexclude-old-lang3` (demonstrates exclusions)
- `-Prelease` (bans SNAPSHOT dependencies)

## Commands to try

### 1) Build + tests

```
mvn clean test
```

### 2) Lifecycle behavior (phases and bindings)

```
mvn -pl modules/app-cli -am compile
mvn -pl modules/app-cli -am test
mvn -pl modules/app-cli -am package
mvn -pl modules/app-cli -am install
```

Observe how each command runs **all phases up to the requested one**, and how default plugins bind to phases (compiler, surefire, jar).

### 3) Effective POM

```
mvn -pl modules/app-cli help:effective-pom
```

### 4) Dependency tree and conflicts

```
mvn -pl modules/app-cli -am dependency:tree
mvn -pl modules/app-cli -am dependency:tree -Dincludes=org.apache.commons:commons-lang3 -Dverbose
```

**What to observe:**
- In conflict mode, commons-lang3 appears in two versions (3.10.0 and 3.12.0).
- Maven resolves a **single** version for the classpath. With equal depth, **the first declared dependency wins**.

### 5) Conflict vs Fixed profiles

```
# conflict mode (default)
mvn -pl modules/app-cli -am test -Pconflict

# fixed mode (forced version)
mvn -pl modules/app-cli -am test -Pfixed
```

**What to observe:**
- In conflict mode, the test expects **3.10.0**.
- In fixed mode, the test expects **3.12.0**.

### 6) Exclusion demo

```
mvn -pl modules/app-cli -am dependency:tree -Pexclude-old-lang3 -Dincludes=org.apache.commons:commons-lang3 -Dverbose
```

**What to observe:**
- `lib-old` no longer brings commons-lang3 transitively (excluded in dependencyManagement).

### 7) Dependency Plugin goals

```
# Show tree
mvn -pl modules/app-cli -am dependency:tree

# Download everything for offline builds
mvn -pl modules/app-cli -am dependency:go-offline

# Fetch dependency sources
mvn -pl modules/app-cli -am dependency:sources

# WARNING: wipes artifacts from local repo and re-downloads
mvn -pl modules/app-cli -am dependency:purge-local-repository
```

### 8) Thin JAR (classpath) vs Fat JAR

```
# Build the app
mvn -pl modules/app-cli -am package
```

Thin JAR (classpath, Unix/macOS):
```
java -cp "modules/app-cli/target/app-cli-1.0-SNAPSHOT.jar:modules/app-cli/target/lib/*" io.github.josemanuel.maven.app.CliApp "hello from thin"
```

Thin JAR (classpath, Windows):
```
java -cp "modules\app-cli\target\app-cli-1.0-SNAPSHOT.jar;modules\app-cli\target\lib\*" io.github.josemanuel.maven.app.CliApp "hello from thin"
```

Fat JAR:
```
java -jar modules/app-cli/target/app-cli-1.0-SNAPSHOT-all.jar "hello from fat"
```

**What to observe:**
- The thin JAR requires external dependencies in `target/lib`.
- The fat JAR is self-contained.
- The app prints the resolved commons-lang3 version it actually loaded.

### 9) Local repository flow

```
# Installs module artifacts into ~/.m2/repository
mvn -pl modules/app-cli -am install
```

**Flow summary:**
1) Maven checks your local `~/.m2/repository`.
2) If missing, it goes to the configured **mirror** (if any) in `settings.xml`.
3) The mirror fetches from remote repos and caches locally.

Example mirror config (settings.xml):

```xml
<mirrors>
  <mirror>
    <id>internal-nexus</id>
    <mirrorOf>central</mirrorOf>
    <url>http://nexus.company.com/repository/maven-public/</url>
  </mirror>
</mirrors>
```

### 10) BOM usage

```
# lib-new and app-cli import the BOM
mvn -pl modules/lib-new help:effective-pom
```

**What to observe:**
- The BOM only provides **versions**.
- A dependency must still be **declared** to be used.

### 11) Enterprise-style release tar.gz (no deploy)

```
# Build full distribution
mvn -pl modules/dist -am package

# Inspect contents (Unix/macOS)
tar -tzf modules/dist/target/maven-by-jmmendoza-1.0-SNAPSHOT.tar.gz
```

The archive includes:
- `app-cli-*.jar`
- `lib/*.jar`
- `config/app.properties`
- `bin/run.sh`

## Dependency scopes (where to see them)

- **compile**: `commons-lang3` in `lib-old` and `lib-new`
- **runtime**: `slf4j-simple` in `app-cli`
- **provided**: `jakarta.servlet-api` in `app-cli` (not packaged)
- **test**: `junit-jupiter` in each module
- **system**: `system-helpers.jar` in `app-cli` (discouraged, local file only)

Use these to inspect classpaths:

```
mvn -pl modules/app-cli -am dependency:tree -Dscope=compile
mvn -pl modules/app-cli -am dependency:tree -Dscope=runtime
mvn -pl modules/app-cli -am dependency:tree -Dscope=test
```

## What to observe (quick checklist)

- The dependency tree shows **two versions** of commons-lang3 in conflict mode.
- The **first declared** dependency at the same depth wins.
- `-Pfixed` forces a single version through dependencyManagement.
- `-Pexclude-old-lang3` removes a transitive dependency.
- `help:effective-pom` shows **inherited and managed** configurations.
- `install` places artifacts in the local repo (`~/.m2/repository`).
- `maven-assembly-plugin` builds a `.tar.gz` without `mvn deploy`.

## Troubleshooting

- If the winning version changes, check the **dependency order** in `modules/app-cli/pom.xml`.
- If a dependency is missing, verify that the BOM **only manages versions**; it does not add dependencies.
- If tests fail in conflict mode, run `mvn -Pconflict -DskipTests` to build anyway.
- If you see a SNAPSHOT ban error, make sure the `-Prelease` profile is not enabled.

## Archetype reminder

From the documentation (optional but useful):

```
mvn archetype:generate -DgroupId=org.example -DartifactId=demo -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

---

This setup is intentionally simple so you can **see Maven’s behavior clearly** with real commands.