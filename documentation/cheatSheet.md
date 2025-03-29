# 🧾 Maven Cheat Sheet

A quick reference for common Maven commands, options, phases, and plugins.

---

## 🚀 Getting Started with Maven

### Create Java Project

```bash
mvn archetype:generate \
  -DgroupId=org.yourcompany.project \
  -DartifactId=application
```

### Create Web Project

```bash
mvn archetype:generate \
  -DgroupId=org.yourcompany.project \
  -DartifactId=application \
  -DarchetypeArtifactId=maven-archetype-webapp
```

### Create Archetype from Existing Project

```bash
mvn archetype:create-from-project
```

---

## 🔄 Main Build Phases

| Phase      | Description                                                          |
| ---------- | -------------------------------------------------------------------- |
| `clean`    | Delete the `target/` directory                                       |
| `validate` | Validate if the project structure is correct                         |
| `compile`  | Compile the source code (`.java`) into `.class` files                |
| `test`     | Run the unit tests                                                   |
| `package`  | Package the compiled code into a distributable format (JAR/WAR)      |
| `verify`   | Run checks to ensure the package is valid and meets quality criteria |
| `install`  | Install the package into your local Maven repository (`~/.m2`)       |
| `deploy`   | Deploy the package to a remote Maven repository                      |

---

## 🛠️ Useful Command Line Options

| Option                      | Description                           |
| --------------------------- | ------------------------------------- |
| `-DskipTests=true`          | Compile tests but skip running them   |
| `-Dmaven.test.skip=true`    | Skip compiling and running tests      |
| `-T 4`                      | Use 4 threads (parallel build)        |
| `-T 2C`                     | Use 2 threads per CPU core            |
| `-rf`, `--resume-from`      | Resume build from a specified module  |
| `-pl`, `--projects`         | Build specific modules only           |
| `-am`, `--also-make`        | Also build required dependencies      |
| `-o`, `--offline`           | Work offline                          |
| `-X`, `--debug`             | Enable debug output                   |
| `-P`, `--activate-profiles` | Activate specific profiles            |
| `-U`, `--update-snapshots`  | Force update of snapshot dependencies |
| `-ff`, `--fail-fast`        | Stop build after first failure        |

---

## 🔌 Essential Plugins

### Help Plugin

```bash
mvn help:describe         # Describes plugin attributes
mvn help:effective-pom    # Shows merged POM for the current build
```

### Dependency Plugin

```bash
mvn dependency:analyze     # Analyzes project dependencies
mvn dependency:tree        # Shows dependency tree
```

### Compiler Plugin

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-compiler-plugin</artifactId>
  <version>3.6.1</version>
  <configuration>
    <source>1.8</source>
    <target>1.8</target>
  </configuration>
</plugin>
```

### Version Plugin

Used to manage versions of artifacts in the project.

### Wrapper Plugin

Ensures a consistent Maven version across environments.

---

> For more awesome cheat sheets, visit [zeroturnaround.com/rebellabs](https://zeroturnaround.com/rebellabs)

