# Module 1: Coding Standard

## Reflection 1
Throughout this module, I have applied clean coding principles to ensure the code is readable, maintainable, and well-structured. I used meaningful names for classes and methods, making it clear what each part of the code does. Additionally, I ensured that different responsibilities were kept separate, which improves modularity and makes future updates easier. Functions were designed to perform a single task, following best practices to maintain simplicity and reusability.

Regarding security, I have implemented good practices but identified areas for improvement. Currently, there is no input validation, which could lead to potential issues with incorrect or missing data. Adding validation to ensure that product names are not empty and product quantities are always positive would enhance reliability. Additionally, error handling could be improved by using custom exceptions instead of returning null, making debugging easier and preventing unexpected behaviors.

Another improvement that can be made is data storage. Right now, the application stores product data in memory, meaning all information is lost when the application restarts. Integrating a proper database, such as PostgreSQL, would make the system more scalable and secure by providing persistent storage. 

Overall, I have gained valuable insights into writing clean and secure code. Moving forward, I plan to enhance the application by adding proper input validation, improving error handling, and integrating database storage. These improvements will ensure better security, performance, and maintainability for the application.


## Reflection 2
1. Writing unit tests gives me a sense of confidence in the reliability of the code. It helps catch potential issues early and ensures that different scenarios, including edge cases, are properly handled. Through testing, I have also realized the importance of writing modular and maintainable code, as well-structured code is easier to test.

    The number of unit tests in a class depends on the complexity of the functionality. Each feature should be tested with different scenarios, including expected inputs, edge cases, and invalid inputs. It is important to balance thoroughness with maintainability, avoiding unnecessary duplication while ensuring sufficient coverage.

   To verify that our unit tests are enough, we can analyze various test cases and use code coverage tools to measure how much of the code is executed during tests. However, 100% code coverage does not guarantee bug-free software. It only means that all lines of code were executed at least once, but it does not ensure correctness or account for unexpected behaviors. The quality of test cases and proper validation of expected outcomes are more important than just achieving high coverage.

2. Creating a new functional test suite with the same setup procedures and instance variables as CreateProductFunctionalTest.java can lead to code duplication and reduced maintainability. Repeating setup logic across multiple test classes makes the code harder to manage and increases the risk of inconsistencies when updates are needed. Copying the same setup and test logic in multiple classes results in redundancy, making future modifications more difficult and time-consuming. Additionally, duplicating initialization logic reduces reusability, which can make the test suite harder to maintain as it expands. This also violates the "Don't Repeat Yourself" (DRY) principle, which aims to minimize code repetition and improve efficiency.

   To address these issues, we can extract shared setup logic into a base test class, allowing all functional test suites to inherit from it. This reduces duplication and ensures consistency across tests. Another improvement is using helper methods for common actions, such as creating a product or verifying the item count, to prevent rewriting the same UI interaction logic in multiple test cases. Additionally, using parameterized tests helps reduce redundancy by allowing different input scenarios to be tested in a single test method. These improvements enhance the overall cleanliness of the test suite, making it more maintainable and scalable without unnecessary duplication.


# Module 2: CI/CD & DevOps
## Reflection
1. **Code Quality Issues and Fixing Strategy**
   1. Unnecessary `public` Modifiers in Test Classes:
      Removed unnecessary `public` modifiers in test classes to align with Java best practices, improving code cleanliness and maintainability.
   2. Unnecessary `throws Exception` Declarations:
      Cleaned up redundant `throws Exception` declarations in test methods, which helps reduce verbosity and avoid misleading exceptions.
   3. Unused Imports:
      Removed unused imports to eliminate warnings and streamline the codebase, contributing to better performance and maintainability.
   4. Inconsistent Method Parameter Naming:
      Renamed method parameters to improve code readability and avoid confusion.
   5. Handling Empty Methods:
      Addressed the empty `setup()` method with comment to avoid confusion and improve test preparation.
   6. Correcting Assertions:
      Updated assertions to avoid comparing literals directly, ensuring accurate validation of method outputs.
   
2. **Evaluation of CI/CD Implementation**
   I believe my current project meets the definition of Continuous Integration (CI) and Continuous Deployment (CD). By utilizing GitHub Actions to run workflows defined in the `.github/workflows` directory, I've implemented CI since the workflows automatically trigger whenever there's a push or pull request to a branch. This ensures that all code changes are consistently tested and validated through automated testing and code quality checks.

   For Continuous Deployment (CD), I have integrated Koyeb as the deployment platform. The setup enables automatic deployment of the project every time new changes are pushed or merged into the branch. This not only reduces manual deployment effort but also keeps the production environment consistently updated with the latest code changes.


## Code Coverage
![Code Coverage JaCoCo](https://github.com/user-attachments/assets/e3186e9c-06ea-470b-b7e7-6d4c5f028f94)
![SonarQube Analysis](https://github.com/user-attachments/assets/9d61d392-2ec3-47a2-aa95-c416905d6cfa)
