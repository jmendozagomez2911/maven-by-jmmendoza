# ☕ Java `source` vs `target`: Compilation, Compatibility, and Runtime Explained

## 🎯 Overview

This guide explains how `source` and `target` affect Java and Scala compilation using Maven, and how they interact with environments like IntelliJ, Jenkins, and Spark.

---

## 🔍 What Are `source` and `target`?

| Term     | What It Does                                                                                  | Example Behavior                                        |
| -------- | --------------------------------------------------------------------------------------------- | ------------------------------------------------------- |
| `source` | Tells the compiler what **Java syntax and language features** you can use                     | Allows `var` (Java 10), forbids `record` if < Java 14   |
| `target` | Tells the compiler what **JVM version** should be able to run the generated `.class` bytecode | `.class` can run on Java 8, 11, or 17 depending on this |




### 🧠 Why are they separated?

Because developers may:

- Use a **newer compiler** (e.g., JDK 17)
- But generate bytecode for an **older JVM** (e.g., Java 11)

This flexibility helps maintain compatibility across environments like Spark, which may use older JVMs.

🚫 However, **`source > target` is never allowed**, even if you don't use newer features.

This is because the compiler assumes that `source` defines the language level (e.g., Java 17 syntax), which may generate bytecode that is incompatible with the older `target` JVM.

✅ For safety and consistency, `source` and `target` are usually set to the **same value**. This ensures that:

- You only use features supported by the JVM where the code will run
- The generated `.class` files behave predictably across environments
- You avoid compilation errors or runtime `UnsupportedClassVersionError`s

---

## ✅ Valid Configuration Example

```xml
  <properties>
  <java.version>11</java.version>
  <maven.compiler.source>${java.version}</maven.compiler.source>
  <maven.compiler.target>${java.version}</maven.compiler.target>
</properties>
```

---

## ❓ What if I have Java 17 installed and want to target Java 11?

You can still compile for Java 11 if:

```xml
<source>11</source>
<target>11</target>
```

Maven will use the **JDK 17 compiler**, but restrict the code to Java 11 features and bytecode.

✅ Everything will work as expected **as long as you avoid Java 17 features**.

🚫 If you try to use Java 17-specific features (like `record`, `sealed`, or `switch` enhancements), the compilation will fail because they are not supported in `source` 11. This is because `source` is what validates language syntax — not `target` — so any unsupported syntax for that language level will cause a compile-time error, even if your installed JDK is newer.

For instance, if you try to use Java 17 features (like `record`), you’ll get:

```
error: record is not supported in -source 11
```

---

## ⚙️ Where Does `<java.version>` Fit In?

`<java.version>` is just a placeholder. It only affects the build **if used in**:

```xml
<maven.compiler.source>${java.version}</maven.compiler.source>
<maven.compiler.target>${java.version}</maven.compiler.target>
```

By itself, it does nothing.

---

## 🧠 Environment Comparison

| Tool / Platform    | Java Version Used                           |
| ------------------ | ------------------------------------------- |
| IntelliJ           | JDK set in Project Structure                |
| Maven (standalone) | JDK from your system (`JAVA_HOME`)          |
| Maven (IntelliJ)   | JDK set in *Settings → Build Tools → Maven* |
| Jenkins agent      | JDK installed in Jenkins node               |
| Spark cluster      | JVM installed in Spark runtime              |

✅ You don’t need a globally installed JDK if using IntelliJ only.

❗ You **do** need it for builds via terminal, Jenkins, or Spark.


---

## ❓ Quick Answers to Common Doubts

- `<java.version>` is just a reusable variable. It only works if you reference it in `<source>` and `<target>`.

- Maven compiles using the JDK defined in IntelliJ (if embedded) or from `JAVA_HOME` (if standalone).

- You can use newer JDKs as long as you compile for an older target — just make sure your source and target match, and that you don't use language features newer than your declared source version

- You **cannot** set `source=17` and `target=8` — even if you don’t use Java 17 features.

- If your JDK is older than your `source`, the build will fail with a “source not supported” error.

* Use the same JDK in dev/build/deploy for consistency



This ensures your Java/Scala projects work across tools like Maven, Jenkins, and Spark.

