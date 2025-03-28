# Maven POM - Project Object Model

## Overview of the POM

The **POM** (Project Object Model) is a crucial element in Maven, which describes the configuration and structure of a Maven project. The POM file is written in **XML** format and is typically named `pom.xml`.

---

### Key Details:

1. **What is POM?**
    - POM stands for **Project Object Model**.
    - It is an **XML document** that describes the Maven project and its structure.

2. **Schema Compliance:**
    - The `pom.xml` file must comply with the **`maven-4.0.0.xsd`** schema.
    - This schema defines the **rules** that the XML document must follow to be valid.

   > **Note**: The `maven-4.0.0.xsd` schema ensures that the POM file adheres to the correct XML structure and that Maven understands how to process it.

---

### POM Properties and Inheritance:

3. **Inheritance in POMs:**
    - POM files can **inherit properties** from a **parent POM**. This is useful for managing multiple projects that share common configurations.
    - **Sub-modules** (modules within a multi-module project) can also inherit from the parent POM, making it easier to manage configurations across different parts of the project.

4. **Effective POM:**
    - The **Effective POM** is the complete version of the POM, including all inherited properties from parent POMs.
    - You can view the effective POM for your project by running the following command:

   ```bash
   mvn help:effective-pom
