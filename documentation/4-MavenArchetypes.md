# 📦 Maven Archetypes

## 📘 What is a Maven Archetype?

A **Maven Archetype** is a template toolkit in Maven that allows you to generate a new project structure based on predefined patterns. It's essentially a **project generator**.

> Think of it as a "starter pack" for common project types—like a Spring Boot app, a multi-module project, or a Java library.

---

## 🎯 Why Use Archetypes?

- 🚀 **Quickly bootstrap** new projects.
- 🔁 **Standardize** project structures across teams.
- 🧱 **Reuse** boilerplate code and configurations.
- 🔧 **Customize** templates for your company’s needs.

---

## 🛠️ Common Archetypes

Here are a few popular archetypes available out of the box:

| Archetype                                | Description                              |
|------------------------------------------|------------------------------------------|
| `maven-archetype-quickstart`             | Basic Java project with one class & test |
| `maven-archetype-webapp`                 | Java EE Web application structure        |
| `spring-boot-archetype` (external)       | Spring Boot project starter              |
| `maven-archetype-site`                   | Maven Site documentation structure       |

You can find more on [Maven Central](https://search.maven.org/) or create your own.

---

## ⚙️ How to Use an Archetype

Use the following command to generate a project from an archetype:

```bash
mvn archetype:generate
```

You’ll be prompted to choose:

- Archetype groupId
- Archetype artifactId
- Archetype version
- Your project's `groupId`, `artifactId`, `version`, and `package`

---

## 🔍 Example: Quickstart Project

```bash
mvn archetype:generate \
  -DgroupId=com.example.app \
  -DartifactId=my-app \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DinteractiveMode=false
```

This creates:

```
my-app/
├── pom.xml
└── src/
    ├── main/java/com/example/app/App.java
    └── test/java/com/example/app/AppTest.java
```

---

## 🧪 Creating a Custom Archetype

1. Create a project with the structure you want to reuse.
2. Run:
   ```bash
   mvn archetype:create-from-project
   ```
3. Navigate to:
   ```bash
   target/generated-sources/archetype/
   ```
4. Install it locally or publish to a remote repository:
   ```bash
   mvn install
   ```

---

## 📝 Tips

- Use `archetype-catalog.xml` to organize multiple custom archetypes.
- Always use `interactiveMode=false` in scripts to avoid prompts.
- Version control your archetypes to maintain consistency across teams.

---

## 📚 Resources

- [Apache Maven Archetype Plugin](https://maven.apache.org/archetype/maven-archetype-plugin/)
- [Official Archetypes List](https://maven.apache.org/archetypes/index.html)
