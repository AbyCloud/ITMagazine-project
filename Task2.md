# Documentation for Task 2 (Command Pattern)

## Overview

Task 2 refines the functionality of Task 1 by implementing the **Command Pattern**, a behavioral design pattern that encapsulates requests as objects. This approach decouples the main workflow from the execution logic of various operations, enhancing flexibility, modularity, and scalability.

This document explains the purpose of each class and method in Task 2, compares it to Task 1, and highlights the differences.

---

## Classes and Their Responsibilities

### 1. **Command Interface**
Defines the common interface for all commands in the system.

#### Methods:
- **`execute()`**:
  - Executes the specific command's logic. This method is implemented by all concrete command classes.

### 2. **Concrete Command Classes**
Each concrete command encapsulates the logic for a specific operation. Below are the commands implemented in Task 2:

#### **CaptureAdvertisementCommand**
Encapsulates the logic to capture advertisement details using the `MarketingStaff` class.

##### Attributes:
- **`staff`**: Reference to the `MarketingStaff` object.
- **`advertiserName`**: Name of the advertiser.
- **`contactInfo`**: Contact information for the advertiser.
- **`content`**: Advertisement content.
- **`placementPreferences`**: Placement preferences for the advertisement.
- **`appearanceDate`**: Date the advertisement will appear.
- **`size`**: Size of the advertisement.

##### Methods:
- **`execute()`**:
  Calls the `captureAdvertisementDetails()` method of the `MarketingStaff` class with the provided attributes.

#### **ReviewAdvertisementCommand**
Encapsulates the logic to review the suitability of an advertisement.

##### Attributes:
- **`staff`**: Reference to the `MarketingStaff` object.
- **`advertisement`**: The advertisement to be reviewed.

##### Methods:
- **`execute()`**:
  Calls the `reviewAdvertisementSuitability()` method of the `MarketingStaff` class for the given advertisement.

#### **ConfirmPaymentCommand**
Encapsulates the logic to confirm payment for an advertisement.

##### Attributes:
- **`paymentProcessor`**: Reference to the `PaymentProcessor` object.
- **`advertisement`**: The advertisement for which payment is being confirmed.

##### Methods:
- **`execute()`**:
  Calls the `confirmPayment()` method of the `PaymentProcessor` class for the given advertisement.

#### **ApproveAdvertisementCommand**
Encapsulates the logic to approve an advertisement after review and payment confirmation.

##### Attributes:
- **`staff`**: Reference to the `MarketingStaff` object.
- **`advertisement`**: The advertisement to be approved.

##### Methods:
- **`execute()`**:
  Calls the `approveAdvertisement()` method of the `MarketingStaff` class for the given advertisement.

#### **ForwardAdvertisementCommand**
Encapsulates the logic to forward an advertisement to the `ProcessingCenter`.

##### Attributes:
- **`processingCenter`**: Reference to the `ProcessingCenter` object.
- **`advertisement`**: The advertisement to be forwarded.

##### Methods:
- **`execute()`**:
  Calls the `receiveAdvertisement()` method of the `ProcessingCenter` class for the given advertisement.

#### **ArchiveAdvertisementCommand**
Encapsulates the logic to archive advertisements that are unused or outdated.

##### Attributes:
- **`archiveManager`**: Reference to the `ArchiveManager` object.
- **`advertisements`**: List of advertisements to be archived.

##### Methods:
- **`execute()`**:
  Calls the `archiveUnusedAdvertisements()` method of the `ArchiveManager` class with the list of advertisements.

---

### 3. **CommandExecutor (Invoker)**
Manages the execution of commands in the system.

#### Attributes:
- **`commandQueue`**: A list of commands to be executed.

#### Methods:
- **`addCommand(Command command)`**:
  Adds a command to the queue for execution.

- **`executeCommands()`**:
  Executes all commands in the queue sequentially and clears the queue after execution.

---

### 4. **Client Code (Task2)**
Acts as the client that sets up and executes commands using the `CommandExecutor`.

#### Steps:
1. Initializes all necessary components, including `MarketingStaff`, `PaymentProcessor`, `ProcessingCenter`, `ArchiveManager`, and `CommandExecutor`.
2. Adds commands to the `CommandExecutor` for each workflow step:
   - **Step 1**: Captures advertisement details using `CaptureAdvertisementCommand`.
   - **Step 2**: Reviews advertisements using `ReviewAdvertisementCommand`.
   - **Step 3**: Confirms payments using `ConfirmPaymentCommand`.
   - **Step 4**: Approves advertisements using `ApproveAdvertisementCommand`.
   - **Step 5**: Forwards advertisements to the processing center using `ForwardAdvertisementCommand`.
   - **Step 6**: Archives unused advertisements using `ArchiveAdvertisementCommand`.
3. Executes all commands using `executeCommands()`.

---

## Differences Between Task 1 and Task 2

### 1. **Use of Design Patterns**
- **Task 1**:
  - Operations are directly called from the main workflow, tightly coupling the main class (`Task1`) with the execution logic.
- **Task 2**:
  - Uses the **Command Pattern** to encapsulate operations as commands, decoupling the main class (`Task2`) from the execution logic.

### 2. **Modularity and Reusability**
- **Task 1**:
  - Operations are hardcoded, making it less modular and harder to reuse.
- **Task 2**:
  - Commands are modular and reusable. Each operation is encapsulated in a separate command class.

### 3. **Workflow Flexibility**
- **Task 1**:
  - Workflow steps are fixed and hardcoded in the main class.
- **Task 2**:
  - Workflow steps are dynamically added to the `CommandExecutor`. The order of execution can be easily changed by reordering commands in the queue.

### 4. **Separation of Concerns**
- **Task 1**:
  - The main class handles both the workflow orchestration and the direct execution of operations.
- **Task 2**:
  - The main class focuses on setting up the workflow, while the `CommandExecutor` handles the execution of commands.

### 5. **Code Maintenance**
- **Task 1**:
  - Adding new operations requires changes to the main class and potentially other related classes.
- **Task 2**:
  - Adding new operations only requires creating a new command class and integrating it with the `CommandExecutor`.

---

## Summary

Task 2 enhances the advertisement management system by introducing the Command Pattern, which improves modularity, reusability, and flexibility. The system is more scalable and maintainable compared to Task 1, as commands encapsulate individual operations, and the workflow is dynamically managed by the `CommandExecutor`. This approach is particularly beneficial for complex workflows where operations may vary or need to be extended in the future.

