# Task 2 Test Suite Documentation

## Overview
This document provides detailed documentation for the test cases written for Task 2. It explicitly outlines the functionalities being tested, the scenarios covered (positive, negative, and edge cases), and their expected outcomes. Task 2 introduces the **Command Pattern**, which decouples the execution logic from the main workflow.

---

## **1. `testCaptureAdvertisement_PositiveCase`**

### **What It Tests:**
This test ensures that a valid advertisement can be captured using the `CaptureAdvertisementCommand`.

### **Scenarios:**
- **Positive Case:**
  - Adds a valid advertisement through the command pattern.

### **Expected Outcomes:**
- Advertisement is added with the correct content.
- The number of advertisements in the list increases appropriately.

---

## **2. `testCaptureAdvertisement_NegativeCase`**

### **What It Tests:**
This test validates the error handling in the `CaptureAdvertisementCommand`.

### **Scenarios:**
- **Negative Case:**
  - Ensures that an `IllegalArgumentException` is thrown when the advertisement content is missing.

### **Expected Outcomes:**
- Exception is thrown for invalid input, and the advertisement is not added.

---

## **3. `testReviewAdvertisement_PositiveCase`**

### **What It Tests:**
This test ensures that an advertisement can be reviewed and marked as "Approved" when appropriate.

### **Scenarios:**
- **Positive Case:**
  - Marks an advertisement as "Approved" if the content is valid.

### **Expected Outcomes:**
- Advertisement status is set to "Approved."

---

## **4. `testReviewAdvertisement_NegativeCase`**

### **What It Tests:**
This test ensures that inappropriate advertisements are rejected during the review process.

### **Scenarios:**
- **Negative Case:**
  - Marks an advertisement as "Rejected" if the content is inappropriate.

### **Expected Outcomes:**
- Advertisement status is set to "Rejected."

---

## **5. `testConfirmPayment_PositiveCase`**

### **What It Tests:**
This test validates the functionality of confirming payment through the `ConfirmPaymentCommand`.

### **Scenarios:**
- **Positive Case:**
  - Confirms payment for a valid advertisement.

### **Expected Outcomes:**
- Advertisement’s `isPaid` flag is set to `true`.

---

## **6. `testConfirmPayment_EdgeCase`**

### **What It Tests:**
This test ensures that an advertisement that has not yet been paid reflects the correct status.

### **Scenarios:**
- **Edge Case:**
  - Checks that the `isPaid` flag remains `false` when payment has not been confirmed.

### **Expected Outcomes:**
- Advertisement’s `isPaid` flag remains `false`.

---

## **7. `testApproveAdvertisement_PositiveCase`**

### **What It Tests:**
This test ensures that an advertisement can be approved if it has been reviewed and paid for.

### **Scenarios:**
- **Positive Case:**
  - Approves an advertisement meeting the required conditions.

### **Expected Outcomes:**
- Advertisement is marked as "Approved."

---

## **8. `testApproveAdvertisement_NegativeCase`**

### **What It Tests:**
This test ensures that an advertisement cannot be approved if it is either unpaid or rejected.

### **Scenarios:**
- **Negative Case:**
  - Advertisement is not approved if payment is not confirmed or if the review status is "Rejected."

### **Expected Outcomes:**
- Advertisement status remains "Rejected."

---

## **9. `testForwardAdvertisement_PositiveCase`**

### **What It Tests:**
This test validates the functionality of forwarding an advertisement to the `ProcessingCenter` for publication.

### **Scenarios:**
- **Positive Case:**
  - Processes an advertisement that is approved and paid.

### **Expected Outcomes:**
- Advertisement’s `isProcessed` flag is set to `true`.

---

## **10. `testArchiveAdvertisements_EdgeCase`**

### **What It Tests:**
This test ensures that old advertisements are archived appropriately through the `ArchiveAdvertisementCommand`.

### **Scenarios:**
- **Edge Case:**
  - Archives advertisements that are unused and more than six months old.

### **Expected Outcomes:**
- Old advertisements are archived, and their details are available in the archive.

---

## **11. `testCommandExecutor_ClearQueueAfterExecution`**

### **What It Tests:**
This test ensures that the `CommandExecutor` properly clears its queue after executing commands.

### **Scenarios:**
- **Positive Case:**
  - Adds a command to the queue and verifies that the queue is cleared after execution.

### **Expected Outcomes:**
- The command queue is empty after execution.

---

## Summary
This test suite ensures comprehensive validation of the functionalities implemented in Task 2 using the Command Pattern. The tests cover the behavior of individual commands, their integration with the `CommandExecutor`, and the overall system's ability to handle valid, invalid, and edge-case scenarios. Each test method targets specific behaviors to ensure robustness and reliability.

