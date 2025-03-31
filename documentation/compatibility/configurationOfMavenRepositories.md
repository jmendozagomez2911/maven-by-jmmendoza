# 📦 Configuration of Maven Repositories

In Maven, repositories are locations where artifacts (such as JARs, POMs, and other resources) are stored and retrieved. Repositories can be remote (hosted on a server like Nexus or Artifactory) or local (on your machine).

This document explains how to configure repositories in Maven, how to use mirrors, profiles, and best practices for enterprise environments.

---

## 📁 Types of Repositories

### 1. **Local Repository**
The local repository is a directory on your machine where Maven stores all downloaded artifacts.

Default path:
```
~/.m2/repository
```

You can change this in `settings.xml`:
```xml
<localRepository>/path/to/your/local/repo</localRepository>
```

---

### 2. **Remote Repositories**
Maven retrieves artifacts from remote repositories defined in the `pom.xml` or `settings.xml`.

Example:
```xml
<repositories>
  <repository>
    <id>central</id>
    <url>https://repo1.maven.org/maven2</url>
  </repository>
</repositories>
```

You can also add `pluginRepositories` for plugin dependencies:
```xml
<pluginRepositories>
  <pluginRepository>
    <id>central</id>
    <url>https://repo1.maven.org/maven2</url>
  </pluginRepository>
</pluginRepositories>
```

Repositories can also be defined in `settings.xml` using `<profiles>`. POM-defined repositories are project-specific, while `settings.xml` repositories apply system-wide.

---

### 3. **Private/Internal Repositories (e.g., Nexus, Artifactory)**
In enterprise environments, teams often use a repository manager like **Nexus** to:
- Cache public dependencies (proxy)
- Host internal artifacts
- Control versioning and security

Example configuration:
```xml
<repositories>
  <repository>
    <id>company-nexus</id>
    <url>http://nexus.company.com/repository/maven-public</url>
  </repository>
</repositories>
```

Each `<repository>` element may include:
- `id`: Unique identifier
- `name`: Human-readable name (optional)
- `url`: Location of the repository
- `layout`: Usually `default`
- `releases` and `snapshots`: Policies for handling those artifact types

---

## 🧩 Repository Policy

Used in the `<releases>` and `<snapshots>` sections to define behavior:

```xml
<releases>
  <enabled>true</enabled>
  <updatePolicy>daily</updatePolicy>
  <checksumPolicy>warn</checksumPolicy>
</releases>
```

- `enabled`: `true` or `false`
- `updatePolicy`: `always`, `daily` (default), `interval:XXX`, or `never`
- `checksumPolicy`: What to do if checksum verification fails (`ignore`, `fail`, `warn`)

---

## 🔁 Mirrors

A **mirror** is used to redirect all repository requests to a specific server, often for caching or performance.

Mirrors override repository definitions from the project and are configured in the `settings.xml` file, located by default in:
```
<user_home>/.m2/settings.xml
```

```xml
<mirrors>
  <mirror>
    <id>nexus</id>
    <mirrorOf>*</mirrorOf>
    <url>http://nexus.company.com/repository/maven-public</url>
  </mirror>
</mirrors>
```

- Overrides the URLs of repositories defined in projects.
- Can be used to redirect to internal Nexus or regional servers.
- Applies to **all Maven projects** on that machine.

---

## 🔧 Profiles and Repository Activation

You can define different repositories in different `profiles`, and activate them manually or by conditions.

```xml
<profiles>
  <profile>
    <id>nexus-release</id>
    <repositories>
      <repository>
        <id>releases</id>
        <url>http://nexus.company.com/repository/releases</url>
      </repository>
    </repositories>
    <activation>
      <activeByDefault>false</activeByDefault>
    </activation>
  </profile>
</profiles>
```

Activate with:
```bash
mvn clean install -Pnexus-release
```

---

## 🚀 Deploying to a Repository

To upload artifacts (e.g., with `mvn deploy`), define a `distributionManagement` block:

```xml
<distributionManagement>
  <repository>
    <id>releases</id>
    <url>http://nexus.company.com/repository/releases</url>
  </repository>
  <snapshotRepository>
    <id>snapshots</id>
    <url>http://nexus.company.com/repository/snapshots</url>
  </snapshotRepository>
</distributionManagement>
```

Authentication is handled in `~/.m2/settings.xml`:
```xml
<servers>
  <server>
    <id>releases</id>
    <username>your-username</username>
    <password>your-password</password>
  </server>
</servers>
```

---

## ✅ Best Practices

- Use **mirrors** to reduce external traffic and improve speed.
- Separate **releases** and **snapshots** in Nexus.
- Use **profiles** to isolate repository environments.
- Never commit credentials in the `pom.xml` — use `settings.xml`.
- Keep your local repo clean: `mvn dependency:purge-local-repository`

---

With proper configuration, Maven can be highly efficient and secure in enterprise environments. Understanding how repositories work ensures reliable builds and better control of dependencies.

