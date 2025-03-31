# 🔒 Maven Enforcer Plugin - Guide

## 🎯 Purpose

The **Maven Enforcer Plugin** is used to **enforce rules during the build** to ensure that all modules in a multi-module project comply with specific technical requirements.

This prevents common issues in large projects, such as:
- Using incorrect Java or Maven versions
- Deploying with `SNAPSHOT` dependencies
- Forgetting required properties like `revision`
- Conflicting or duplicated dependency versions

---

## 🧱 Why Use It?

| Situation                                               | Problem it prevents                                        |
|---------------------------------------------------------|-------------------------------------------------------------|
| Developers using outdated Java/Maven versions           | Inconsistent builds                                         |
| SNAPSHOT dependencies left in production builds         | Unstable or non-repeatable deployments                     |
| Missing properties like `revision`                     | Build fails or behavior becomes unpredictable              |
| Conflicting dependency versions across modules          | Runtime errors or subtle bugs                              |

Using the Enforcer plugin ensures that your project **fails fast**, with clear error messages when something breaks your standards.

---

## 🧩 Where to Configure It

> ✅ **In the `pom.xml` of the parent module**, so it applies to all children automatically.

```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-enforcer-plugin</artifactId>
      <version>3.3.0</version>
      <executions>
        <execution>
          <id>enforce-standard-rules</id>
          <goals>
            <goal>enforce</goal>
          </goals>
          <configuration>
            <rules>
              <requireMavenVersion>
                <version>[3.8.1,)</version>
              </requireMavenVersion>
              <requireJavaVersion>
                <version>[17,)</version>
              </requireJavaVersion>
              <requireProperty>
                <property>revision</property>
                <message>You must define the 'revision' property in the parent POM</message>
              </requireProperty>
              <banSnapshots>
                <message>No SNAPSHOT dependencies allowed in release builds!</message>
              </banSnapshots>
            </rules>
            <failFast>true</failFast>
          </configuration>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

---

## 🛠 Common Rules You Can Enforce

| Rule                        | Description                                                  |
|-----------------------------|--------------------------------------------------------------|
| `requireJavaVersion`        | Ensures the correct Java version is used                     |
| `requireMavenVersion`       | Validates Maven version                                      |
| `requireProperty`           | Forces specific properties (e.g., `revision`)                |
| `banSnapshots`              | Disallows use of `-SNAPSHOT` dependencies                    |
| `requireUpperBoundDeps`     | Ensures consistent dependency versions across modules         |
| `banDuplicatePomDependencyVersions` | Avoids duplicated versions of the same dependency    |

---

## 🚀 When to Use It

✅ Recommended when:
- You are working in a **multi-module** project
- You're in a **regulated sector** like banking
- Builds are deployed via **Jenkins or CI/CD**
- Teams share code and environments

❌ Optional when:
- Working alone on a local or toy project
- You fully control the environment and versions manually

---

## ✅ Summary

The Maven Enforcer Plugin is a powerful tool to:
- Detect and prevent mistakes **early** in the build process
- Enforce technical standards across teams
- Avoid wasting time on failed deployments due to simple config issues

Use it in your **parent POM**, and your entire Maven project will benefit from safer, more predictable builds.

