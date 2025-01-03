# Task 3 Test Suite Documentation

## Overview
This document provides detailed documentation for the test cases written for Task 3. It explicitly outlines the functionalities being tested, the scenarios covered (positive, negative, and edge cases), and their expected outcomes. Task 3 introduces **Object Constraint Language (OCL)** validations to ensure advertisements adhere to defined business rules and constraints.

---

## **1. `testValidateAdvertisementDetails_PositiveCase`**

### **What It Tests:**
Validates that an advertisement with complete and valid details passes the validation process.

### **Scenarios:**
- **Positive Case:**
  - Valid advertisement details (all required fields are non-empty).

### **Expected Outcomes:**
- Validation passes without throwing an exception.

---

## **2. `testValidateAdvertisementDetails_NegativeCase`**

### **What It Tests:**
Ensures that an advertisement with missing or invalid details fails the validation process.

### **Scenarios:**
- **Negative Case:**
  - Missing required fields such as `content` or `advertiserName`.

### **Expected Outcomes:**
- Throws an `IllegalArgumentException` for invalid input.

---

## **3. `testCaptureAdvertisement_PositiveCase`**

### **What It Tests:**
Verifies that a valid advertisement can be captured successfully.

### **Scenarios:**
- **Positive Case:**
  - Adds a valid advertisement to the system.

### **Expected Outcomes:**
- Advertisement is added to the list.
- No exceptions are thrown.

---

## **4. `testReviewAdvertisementSuitability_PositiveCase`**

### **What It Tests:**
Ensures that an advertisement with valid content is approved during the review process.

### **Scenarios:**
- **Positive Case:**
  - Advertisement content is valid.

### **Expected Outcomes:**
- Advertisement status is set to "Approved."

---

## **5. `testReviewAdvertisementSuitability_NegativeCase`**

### **What It Tests:**
Validates that an advertisement with inappropriate content is rejected during the review process.

### **Scenarios:**
- **Negative Case:**
  - Advertisement content contains inappropriate words or phrases.

### **Expected Outcomes:**
- Advertisement status is set to "Rejected."

---

## **6. `testConfirmPayment_PositiveCase`**

### **What It Tests:**
Verifies that payment can be successfully confirmed for a valid advertisement.

### **Scenarios:**
- **Positive Case:**
  - Confirms payment for a valid advertisement.

### **Expected Outcomes:**
- Advertisement’s `isPaid` flag is set to `true`.

---

## **7. `testConfirmPayment_NotPaidBeforePublication`**

### **What It Tests:**
Ensures that an advertisement cannot proceed to publication without confirmed payment.

### **Scenarios:**
- **Negative Case:**
  - Attempts to publish an advertisement that has not been paid for.

### **Expected Outcomes:**
- Throws an `IllegalStateException` for unpaid advertisements.

---

## **8. `testArchiveUnusedAdvertisements_PositiveCase`**

### **What It Tests:**
Ensures that advertisements older than six months and unused are archived.

### **Scenarios:**
- **Positive Case:**
  - Advertisement is older than six months and not processed.

### **Expected Outcomes:**
- Advertisement’s `isArchived` flag is set to `true`.

---

## **9. `testArchiveUnusedAdvertisements_NegativeCase`**

### **What It Tests:**
Validates that active advertisements or advertisements with a recent appearance date are not archived.

### **Scenarios:**
- **Negative Case:**
  - Advertisement has a future or recent appearance date.

### **Expected Outcomes:**
- Advertisement’s `isArchived` flag remains `false`.

---

## **10. `testProcessingAdvertisement_PositiveCase`**

### **What It Tests:**
Verifies that an advertisement is processed for publication if it is approved and paid.

### **Scenarios:**
- **Positive Case:**
  - Advertisement is reviewed, approved, and paid.

### **Expected Outcomes:**
- Advertisement’s `isProcessed` flag is set to `true`.

---

## **11. `testProcessingAdvertisement_NotApproved`**

### **What It Tests:**
Ensures that an advertisement cannot be processed if it is not approved.

### **Scenarios:**
- **Negative Case:**
  - Advertisement has not been approved or is marked as rejected.

### **Expected Outcomes:**
- Advertisement’s `isProcessed` flag remains `false`.

---

## Summary
This test suite comprehensively validates the functionalities of Task 3, focusing on OCL-based constraints, validations, and business rules. The tests ensure that the system behaves correctly across a variety of positive, negative, and edge-case scenarios, ensuring the robustness of the implementation.

