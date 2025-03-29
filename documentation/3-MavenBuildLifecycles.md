# Maven Build Lifecycles

Maven is based on the concept of **build lifecycles**. A lifecycle is a predefined group of **phases** that define the steps Maven will follow during the build process. Each phase in the lifecycle can be bound to one or more **plugin goals** — that is, tasks performed by specific plugins.

### 🧪 Example
For instance, the `package` phase is typically bound to:
- `maven-jar-plugin:jar` → to create a JAR file

But you can also bind other goals to the same phase, like:
- `maven-shade-plugin:shade` → to create an uber-jar
- `exec-maven-plugin:exec` → to run a custom command

So, one phase like `package` can execute **multiple plugin goals**, depending on your project configuration.

---

## 🔄 Maven Predefined Lifecycles

Maven provides three **main lifecycles**:

1. **Clean Lifecycle**  
   Cleans the project by removing all build artifacts from the working directory to avoid interference with the new build.

   **Phases**:
   - `pre-clean`: Executes before the cleaning process.
   - `clean`: Deletes the `target/` directory and other generated files.
   - `post-clean`: Executes after cleaning.

2. **Default Lifecycle**  
   Handles the complete **build and deployment** process of your project.

   This lifecycle is composed of several ordered phases, each of which can trigger specific **plugin goals**. For a project with `jar` packaging, Maven uses **default plugins** automatically even if they are not explicitly declared in your `pom.xml`.

   ### 🧩 Lifecycle Phases and Default Plugins (for `jar` projects)

   | Phase                     | Purpose                                                                                | Default Plugin & Goal                         |
      |--------------------------|----------------------------------------------------------------------------------------|------------------------------------------------|
   | `validate`               | Verifies the project structure and configuration                                       | *(no default plugin)*                         |
   | `process-resources`      | 📦 Copies and filters main resources (e.g., `.properties`, `.xml`) to `target/classes` | `maven-resources-plugin : resources`          |
   | `compile`                | 🔧 Compiles the main source code                                                       | `maven-compiler-plugin : compile`             |
   | `process-test-resources`| 📦 Copies and filters test resources to `target/test-classes`                          | `maven-resources-plugin : testResources`      |
   | `test-compile`           | 🔧 Compiles test source code                                                           | `maven-compiler-plugin : testCompile`         |
   | `test`                   | 🧪 Runs unit tests using JUnit or TestNG                                               | `maven-surefire-plugin : test`                |
   | `package`                | 📦 Packages the compiled code into a JAR file                                          | `maven-jar-plugin : jar`                      |
   | `verify`                | ✅ Runs integration tests or other verifications                                        | *(no default plugin)*                         |
   | `install`                | 📂 Installs the JAR into your **local Maven repository** `(.m2/repository)`              | `maven-install-plugin : install`              |
   | `deploy`                 | 🚀 Deploys the artifact to a **remote Maven repository**                               | `maven-deploy-plugin : deploy`                |

   ### 🛠️ Notes
   - Maven automatically uses **default plugin bindings** based on your packaging type (`jar`, `war`, etc.).
   - ⚠️ The `maven-compiler-plugin` defaults to Java 1.6 — this may cause errors if your code uses newer Java features like `record` in Java 17.
   - You can **override plugins** in `pom.xml` to:
      - Set Java versions (e.g., `source` and `target`)
      - Lock plugin versions for stability
      - Pass custom configuration options

   ### 🧪 Example: Multiple Goals per Phase

   One phase can run **multiple plugin goals**. For example, the `package` phase might include:
   - `maven-jar-plugin:jar` → creates the standard JAR
   - `maven-shade-plugin:shade` → creates an uber-jar with dependencies
   - `exec-maven-plugin:exec` → runs a script or Java class

3. **Site Lifecycle**  
   Generates documentation for the project (e.g., reports, project site).  
   ⚠️ Less commonly used in enterprise environments.

---

## 🚀 How Maven Executes Lifecycles, Phases, and Goals

When you run a Maven command like `mvn install`, Maven:

1. Starts from the **first phase** (`validate`) of the specified lifecycle.
2. At each phase, it runs all **plugin goals** bound to that phase.
3. Continues through each phase **in order**, up to and including the one you invoked.

### 📌 Examples

- `mvn test` → runs:  
  `validate → process-resources → compile → process-test-resources → test-compile → test`

- `mvn install` → runs:  
  everything up to and including `install`

- `mvn deploy` → runs:  
  the **entire default lifecycle** from `validate` to `deploy`

> 💡 The **lifecycle** defines the **order of phases**, and **plugins** define what Maven does in each phase.

---
