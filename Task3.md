# Documentation for Task 3

## Overview

Task 3 builds on the functionality of Task 1 by introducing **Object Constraint Language (OCL) concepts** for validation, constraints, and ensuring the consistency of operations. This implementation includes enhancements like validating advertisement details, enforcing uniqueness of advertisement IDs, and introducing additional methods to ensure adherence to constraints.

This document explains the purpose of each class and method in Task 3 and highlights the differences from Task 1.

---

## Classes and Their Responsibilities

### 1. **Advertisement**
Represents an advertisement and its lifecycle.

#### Attributes:
- **`advertID`**: Unique identifier for the advertisement.
- **`advertiserName`**: Name of the advertiser.
- **`contactInfo`**: Contact details of the advertiser.
- **`content`**: The content of the advertisement.
- **`placementPreferences`**: Where the advertisement should appear.
- **`appearanceDate`**: The date the advertisement is scheduled to appear.
- **`reviewStatus`**: Status of the review process (e.g., "Pending," "Approved," "Rejected").
- **`isPaid`**: Boolean indicating whether payment has been confirmed.
- **`size`**: Size of the advertisement.
- **`isProcessed`**: Boolean indicating whether the advertisement has been processed for publication.
- **`isArchived`**: Boolean indicating whether the advertisement has been archived.

#### Methods:
- **`validateDetails()`**:
  Validates the completeness of advertisement details. Throws an exception if any required field is missing.

- **`archiveIfUnused()`**:
  Archives the advertisement if it is marked "Unused" and older than six months.

- **`requiresSuitability()`**:
  Returns true if the advertisement requires suitability assessment.

- **`ensurePaidBeforePublication()`**:
  Throws an exception if the advertisement is not paid before publication.

- **`getDetails()`**:
  Returns a formatted string containing the advertisement details.

- **`recordDetails()`**:
  Adds the advertisement to a static list for centralized tracking.

---

### 2. **MarketingStaff**
Represents a marketing staff member responsible for managing advertisements.

#### Attributes:
- **`staffID`**: Unique identifier for the staff member.
- **`name`**: The name of the staff member.
- **`advertisements`**: A list of advertisements managed by the staff member.

#### Methods:
- **`captureAdvertisementDetails()`**:
  Captures the details of a new advertisement, validates them using `validateDetails()`, and adds the advertisement to the list.

  **Enhanced Features:**
  - Ensures uniqueness of advertisement IDs using a helper method `addAdvertisement()`.

- **`reviewAdvertisementSuitability()`**:
  Reviews the suitability of an advertisement by invoking the `ReviewProcess` class. Includes checks to ensure the suitability status matches expectations.

- **`approveAdvertisement()`**:
  Approves an advertisement if it has been reviewed and the payment is confirmed. Otherwise, marks the advertisement as "Not Paid" or "Rejected."

- **`getAdvertisementsByStatus()`**:
  Retrieves advertisements filtered by their status (e.g., "Approved," "Rejected").

---

### 3. **ArchiveManager**
Handles the archiving of advertisements that are unused for six months or more.

#### Methods:
- **`archiveUnusedAdvertisements()`**:
  Iterates through the list of advertisements and calls `archiveIfUnused()` on each. Prints details of archived advertisements.

---

### 4. **PaymentProcessor**
Handles payment confirmation and enforces payment constraints.

#### Methods:
- **`confirmPayment()`**:
  Confirms payment for an advertisement and invokes `ensurePaidBeforePublication()` to enforce payment constraints.

---

### 5. **ReviewProcess**
Manages the review and approval of advertisements.

#### Methods:
- **`assessSuitability()`**:
  Assesses the suitability of an advertisement's content. Marks the advertisement as "Approved" or "Rejected" based on predefined rules.

---

### 6. **ProcessingCenter**
Handles the processing of advertisements for publication.

#### Methods:
- **`receiveAdvertisement()`**:
  Processes an advertisement for publication if it is both approved and paid. Marks the advertisement as processed.

---

## Differences Between Task 1 and Task 3

### 1. **Validation**
- **Task 1**:
  - No validation of advertisement details.
- **Task 3**:
  - Introduces `validateDetails()` in the `Advertisement` class to ensure completeness of advertisement details before submission.

### 2. **Uniqueness of Advertisement IDs**
- **Task 1**:
  - Assumes unique IDs without enforcement.
- **Task 3**:
  - Enforces uniqueness of IDs using `addAdvertisement()` in the `MarketingStaff` class.

### 3. **Object Constraint Language (OCL) Features**
- **Task 1**:
  - No OCL constraints.
- **Task 3**:
  - Adds methods like `archiveIfUnused()`, `requiresSuitability()`, and `ensurePaidBeforePublication()` to enforce business constraints.

### 4. **Archiving Enhancements**
- **Task 1**:
  - Simple archival based on date.
- **Task 3**:
  - Uses `archiveIfUnused()` to archive advertisements that are unused and older than six months.

### 5. **Error Handling**
- **Task 1**:
  - No structured error handling.
- **Task 3**:
  - Implements error handling for invalid inputs, duplicate IDs, and business logic violations.

### 6. **Enhanced Suitability Checks**
- **Task 1**:
  - Basic suitability checks.
- **Task 3**:
  - Adds consistency checks for suitability status in `reviewAdvertisementSuitability()`.

---

## Summary

Task 3 enhances the advertisement management system by introducing OCL-inspired validation and constraints. It ensures the completeness and correctness of advertisement details, enforces business rules, and improves error handling. These features make the system more robust and reliable compared to Task 1.

