# Banking System (TDD)

A robust Java-based banking application developed using **Test-Driven Development (TDD)** principles. This project manages core banking functionalities while ensuring high code reliability through automated testing and mutation analysis.

## 🚀 Key Features
* **Account Management**: Supports specialized account types including Savings and CD (Certificate of Deposit) accounts.
* **TDD Methodology**: Built with a "test-first" approach to ensure 100% logic verification for banking transactions.
* **Mutation Testing**: Integrated with PITest to verify the effectiveness of the test suite.
* **Automated CI/CD**: Configured with GitLab CI for automated build and quality checks.

## 🛠️ Tech Stack
* **Language**: Java (100%)
* **Build Tool**: Gradle
* **Testing**: JUnit, PITest
* **Code Quality**: Code Climate

## 📋 Prerequisites
* Java 11 or higher
* Gradle (included via wrapper)

## 🔧 Installation & Setup
1. **Clone the repository**:
   ```bash
   git clone [https://github.com/ruhmahashmi/Banking-System-with-Test-Driven-Development-TDD-.git](https://github.com/ruhmahashmi/Banking-System-with-Test-Driven-Development-TDD-.git)
2. Build the project:

Bash
./gradlew build

3. Run Tests:
Bash
./gradlew test

## 🧪 Mutation Testing
* To run mutation tests and verify test strength, use:

Bash
./gradlew pitest

---
