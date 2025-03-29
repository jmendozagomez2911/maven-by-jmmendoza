# 📦 Release Process - Enterprise Multi-Module Project (No Deploy)

This document describes the official release process for a multi-module Maven project. It reflects a Jenkins-driven flow that avoids the standard `mvn deploy` mechanism in favour of a `.tar.gz`-based delivery package.

---

## 🧱 Project Structure

- Multi-module Maven project
- Modules include core libraries, processors, and action workflows
- A final distributable `.tar.gz` is generated using the `maven-assembly-plugin`

---

## 🚀 Step-by-Step Release Flow

### 1. Development Phase
- Developers work on feature branches (e.g., `feature/XYZ`)
- Local builds validated with:
  ```bash
  mvn clean install -DskipTests
  ```

### 2. Build on Jenkins (`development` branch)
- Jenkins is triggered automatically on push
- Executes:
  ```bash
  mvn clean package
  ```
- Builds all modules and creates `.jar` files
- Generates `assembly.tar.gz` including:
    - Module JARs
    - `lib/` with dependencies
    - Configuration files (`.yml`, `.properties`)
    - Startup scripts (`run.sh`, etc.)

### 3. Merge to Main
- If the build is successful and validated, merge `development` → `main`

### 4. Final Builds for Main and Development
- Jenkins builds both branches again
- Ensures consistency across environments

### 5. Mark as Release
- The `pom.xml` in `main` is updated to remove `-SNAPSHOT`
  ```xml
  <version>1.2.0</version>
  ```
- Jenkins runs:
  ```bash
  mvn clean package
  ```
- Generates the final `.tar.gz` artifact (release version)

### 6. Deploy to Preproduction
- The `.tar.gz` is copied and unpacked in the preproduction environment
- Configuration and secrets injected
- Application is tested and validated

### 7. Approval
- Change request is created and submitted to the approval board
- If approved, the same artifact is promoted to production

### 8. Deploy to Production
- Exact same `.tar.gz` used in preproduction is deployed to production
- No rebuilds are allowed

---

## ✅ Summary
- No use of `mvn deploy`
- `.tar.gz` is the final delivery unit
- All builds are reproducible and verifiable
- Strong separation between development, preproduction, and production environments
- Ensures traceability, security, and control

