# Maven Dependency Scopes

In Maven, **dependency scopes** define **where and when** a dependency is available during the project lifecycle. To understand the dependency scopes better, let's first define the key phases of a Maven project.

---

### **Definitions:**

1. **Build**:
   - **Build** is the process of preparing the project by compiling the source code, running unit tests, and packaging the application into a deployable artifact (e.g., a JAR or WAR file). During this phase, dependencies in the **`compile`** and **`test`** scopes are used.

2. **Run (Execution)**:
   - **Run** refers to the process of executing the application after it has been built. During this phase, your application is started, and the dependencies in the **`runtime`** scope are used.

3. **Testing**:
   - **Testing** refers to running unit tests to ensure that the project functions as expected. Dependencies in the **`test`** scope are used during this phase to verify the correctness of the code.

---

### **Dependency Scopes**

Below are the most common scopes in Maven and how they are used throughout the **build**, **run**, and **test** phases:

---

### **1. `compile`**
- **Available in all phases**: compilation, testing, and execution.
- **Usage**: Essential dependencies required throughout the entire project lifecycle (compiling, testing, and running the application).
- **Example**: Libraries needed for **compiling**, **testing**, and **running** your project, such as logging frameworks, JSON parsers, or core libraries.

---

### **2. `provided`**
- **Available only in compilation and test**: Not included in the final artifact (e.g., provided by a container).
- **Usage**: Used for dependencies that are already provided in the runtime environment (e.g., Servlets in a web container like Tomcat).
- **Example**: **Servlet API** in web applications, where Tomcat provides the servlet container, so it’s not needed in the final WAR.

---

### **3. `runtime`**
- **Available only at runtime and test, not in compilation**.
- **Usage**: Dependencies needed **only at runtime**, but not for compilation (e.g., database drivers).
- **Example**: **JDBC drivers** for connecting to a database. These are required **only at runtime**, not during compilation.

---

### **4. `test`**
- **Available only on the test classpath**: Not included in the compilation or execution.
- **Usage**: Dependencies required **only for testing** purposes (e.g., JUnit).
- **Example**: **JUnit** or **Mockito** libraries for running unit tests, which are not included in the final production build.

---

### **5. `system`**
- **Similar to provided**, but the JAR is explicitly added from a local file.
- **Usage**: Used for dependencies that are not available in public repositories (e.g., local JARs or custom libraries that aren't hosted in a remote repository).
- **Example**: A **custom library** located on your local file system that is needed for your project but isn't available in a Maven repository.

---

### **Recap:**

- **Build**: The process of **compiling**, **testing**, and **packaging** the application. Dependencies in the **`compile`** and **`test`** scopes are used.
- **Run (Execution)**: The process of **executing** the application. Dependencies in the **`runtime`** scope are used during this phase.
- **Testing**: Running unit tests to ensure functionality. Dependencies in the **`test`** scope are used during this phase.

---


## Dependency Plugin

### Overview

Dependencies in Maven are managed by the **Maven Dependency Plugin**. This plugin helps to **resolve, download, and manage dependencies** in various ways. It also allows you to inspect and manipulate your project's dependencies to ensure that they are correctly configured and available for build and execution.

---

### Important Goals:

1. **dependency:tree**:
   - **Displays the dependency tree** of your project, showing all the dependencies and their transitive dependencies.
   - This is useful for resolving **dependency conflicts** and understanding the **hierarchical structure** of your dependencies, making it easier to debug issues or optimize dependency versions.

   **Example Use Case**: If you have multiple dependencies with different versions of the same library, `dependency:tree` helps you identify and resolve version conflicts.


2. **dependency:go-offline**:
   - **Resolves all dependencies** and prepares your project to go offline.
   - Ensures that all required dependencies are downloaded and available in your **local repository**, so you can build your project without an internet connection.
   - This is helpful when you need to **work offline** or when you want to **pre-download all dependencies** before starting development or building in an environment without internet access.

   **Example Use Case**: Use this when you are in a remote location with no internet access but still need to build your project.


3. **dependency:purge-local-repository**:
   - **Clears artifacts** from your local repository.
   - This is useful when you want to **refresh or clean up your local Maven repository**. For instance, if some dependencies have been corrupted or you want to force Maven to re-download dependencies from remote repositories.
   - **Caution**: This command removes artifacts that are cached locally, which could result in Maven downloading them again on the next build.

   **Example Use Case**: You might use this if you're encountering issues with corrupted dependencies or want to ensure that the latest versions of dependencies are used.


4. **dependency:sources**:
   - Retrieves **source code** for all dependencies, typically in the form of `.java` files from the source JARs.
   - This is useful when you want to **inspect, debug, or examine the source code** of the dependencies your project relies on. It allows you to view the actual implementation of a library, which is especially helpful for troubleshooting or understanding how a dependency works.
   - **Why is this useful?** Even if a dependency is well-tested and stable, you might want to inspect the source code for debugging or learning purposes. You can also use this to understand **internal implementation** or see how a specific method or feature is implemented.

   **Example Use Case**: If you encounter an issue with a third-party library and need to understand its inner workings or identify a bug, you can use `dependency:sources` to download and inspect the source code of that library.

---

### Summary:

- **`dependency:tree`**: Helps visualize and manage the hierarchical structure of your project's dependencies.
- **`dependency:go-offline`**: Downloads all dependencies to work offline, ensuring everything is cached locally for later use.
- **`dependency:purge-local-repository`**: Clears and refreshes your local repository, forcing Maven to download fresh versions of dependencies.
- **`dependency:sources`**: Downloads the source code for dependencies, allowing you to inspect, debug, or learn from the libraries you're using.

These goals provide powerful ways to manage, troubleshoot, and inspect the dependencies in your Maven project. They ensure that your project's dependencies are properly resolved and help you maintain the quality and efficiency of your builds.

---

Let me know if you'd like further adjustments or explanations on any of these goals!
