# Maven Build Lifecycles

Maven is based on the concept of **build lifecycles**. A lifecycle is a predefined group of **phases** that define the steps Maven will follow during the build process. Each phase in the lifecycle can be bound to one or more **plugin goals**.

---

## Maven Predefined Lifecycles

Maven provides three **predefined lifecycles**:

1. **Clean Lifecycle**: Cleans the project by removing all build artifacts from the working directory. This ensures that no outdated files interfere with the new build.

   **Key Phases**:
    - **pre-clean**: Executes before the clean phase.
    - **clean**: Removes the `target/` directory and other build artifacts.
    - **post-clean**: Executes after the clean phase.

2. **Default Lifecycle**: Handles the build and deployment process of the project. This lifecycle includes compiling, testing, packaging, installing, and deploying the project.

   **Key Phases**:
    - **validate**: Verifies the project is correct and all necessary information is available.
    - **compile**: Compiles the source code.
    - **test**: Runs unit tests.
    - **package**: Packages the compiled code into a distributable format (e.g., JAR, WAR).
    - **verify**: Runs integration tests and performs additional verification steps.

    - **install**: Installs the packaged artifact into the local Maven repository.
    - **deploy**: Deploys the artifact to a remote repository for sharing.

3. **Site Lifecycle**: Used for generating project documentation (e.g., reports, website).
    - **Key Use**: Generates a website and documentation for the project, though it is **less commonly used** in enterprise projects.

---

## Default Lifecycle for JAR Packaging

The **Default Maven Lifecycle** defines a sequence of phases executed when building a project. Each phase can be associated with one or more **plugin goals**, which tell Maven what to do at each stage.

Even if you don’t explicitly declare plugins in your `pom.xml`, Maven will use **default plugin bindings** based on the project’s `packaging` type (e.g., `jar`).

For **JAR packaging**, the following phases are executed in sequence:

---

1. **process-resources**
   - **Plugin**: `maven-resources-plugin : resources`
   - 📦 Copies and filters project resources (e.g., `.properties`, `.xml`) to the `target/classes` directory.

2. **compile**
   - **Plugin**: `maven-compiler-plugin : compile`
   - 🔧 Compiles the project's main source code.
   - 🛠️ If **not declared**, Maven still uses this plugin with default settings (e.g., Java 1.6).
   - ⚠️ This may fail if your code uses newer Java features (e.g., `record` in Java 17).

3. **process-test-resources**
   - **Plugin**: `maven-resources-plugin : testResources`
   - 📦 Copies and filters test resources to the `target/test-classes` directory.

4. **test-compile**
   - **Plugin**: `maven-compiler-plugin : testCompile`
   - 🔧 Compiles test source code.

5. **test**
   - **Plugin**: `maven-surefire-plugin : test`
   - 🧪 Runs unit tests using **JUnit** or **TestNG**.

6. **package**
   - **Plugin**: `maven-jar-plugin : jar`
   - 📦 Packages compiled code and resources into a `.jar` file.

7. **install**
   - **Plugin**: `maven-install-plugin : install`
   - 📂 Installs the `.jar` into your **local Maven repository** for reuse in other local projects.

8. **deploy**
   - **Plugin**: `maven-deploy-plugin : deploy`
   - 🚀 Deploys the artifact to a **remote Maven repository** for sharing across teams or CI/CD.

---

> 🧠 **Note**: Plugins are not part of your application’s runtime code. They are tools Maven uses during the **build process**, such as compiling, testing, or packaging.
>
> 📌 Declaring plugins in your `pom.xml` allows you to:
> - Set the **Java version** (e.g., `source` and `target` = 17)
> - Choose a **specific plugin version** (for stability)
> - Pass **custom configuration options**


---

## How Maven Executes Lifecycles, Phases, and Goals

Maven runs the phases in sequence, following the order of the predefined **lifecycles**. When you run a Maven command (e.g., `mvn clean install`), Maven will execute the corresponding phases and plugin goals:

1. Start with the **first phase** of the lifecycle.
2. Execute all associated **plugin goals** for that phase.
3. Move to the next phase, executing all associated goals.
4. Continue until all phases are completed.
