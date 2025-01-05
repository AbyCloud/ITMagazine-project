# Task 1 Test Suite Documentation

## Overview
This document provides detailed documentation for the test cases written for Task 1. It explicitly outlines the functionalities being tested, the scenarios covered (positive, negative, and edge cases), and their expected outcomes.

---

## **1. `testCaptureAdvertisementDetails`**

### **What It Tests:**
This test ensures the proper functionality of the `captureAdvertisementDetails` method in the `MarketingStaff` class.

### **Scenarios:**
- **Positive Case:**
  - Adds a valid advertisement.
  - Verifies that the advertisement is added to the list with the correct ID and content.
- **Negative Case:**
  - Ensures that an `IllegalArgumentException` is thrown when the content is missing.

### **Expected Outcomes:**
- Advertisement is added with the correct ID for valid input.
- Exception is thrown for invalid input.

---

## **2. `testReviewAdvertisementSuitability`**

### **What It Tests:**
This test verifies the functionality of the `reviewAdvertisementSuitability` method in the `MarketingStaff` class.

### **Scenarios:**
- **Positive Case:**
  - Approves an advertisement with valid content.
- **Negative Case:**
  - Rejects an advertisement with inappropriate content.

### **Expected Outcomes:**
- Advertisement is marked as "Approved" for valid content.
- Advertisement is marked as "Rejected" for inappropriate content.

---

## **3. `testConfirmPayment`**

### **What It Tests:**
This test ensures the proper functionality of the `confirmPayment` method in the `PaymentProcessor` class.

### **Scenarios:**
- **Positive Case:**
  - Successfully confirms payment for an advertisement.
- **Negative Case:**
  - Ensures double payment confirmation does not cause issues.
- **Edge Case:**
  - Throws a `NullPointerException` when a `null` advertisement is passed.

### **Expected Outcomes:**
- Advertisement's `isPaid` flag is set to `true` for valid input.
- Double confirmation logs but does not change the state.
- Throws exception for `null` input.

---

## **4. `testApproveAdvertisement`**

### **What It Tests:**
This test validates the `approveAdvertisement` method in the `MarketingStaff` class.

### **Scenarios:**
- **Positive Case:**
  - Approves an advertisement that has been reviewed and paid for.
- **Negative Case:**
  - Marks the advertisement as "Not Paid" if payment is not confirmed.
- **Edge Case:**
  - Throws a `NullPointerException` for `null` advertisements.

### **Expected Outcomes:**
- Advertisement is approved if reviewed and paid for.
- Advertisement is marked "Not Paid" otherwise.
- Throws exception for `null` input.

---

## **5. `testReceiveAdvertisement`**

### **What It Tests:**
This test verifies the functionality of the `receiveAdvertisement` method in the `ProcessingCenter` class.

### **Scenarios:**
- **Positive Case:**
  - Processes an advertisement that is approved and paid.
- **Negative Case:**
  - Does not process an unapproved advertisement.

### **Expected Outcomes:**
- Advertisement's `isProcessed` flag is set to `true` for valid input.
- Advertisement remains unprocessed for invalid input.

---

## **6. `testArchiveUnusedAdvertisements`**

### **What It Tests:**
This test ensures the functionality of the `archiveUnusedAdvertisements` method in the `ArchiveManager` class.

### **Scenarios:**
- **Positive Case:**
  - Archives an advertisement that is old and unused (more than six months old).
- **Negative Case:**
  - Ensures advertisements with a future `appearanceDate` are not archived.
- **Edge Case:**
  - Ensures advertisements exactly six months old are not archived.

### **Expected Outcomes:**
- Old and unused advertisements are archived.
- Advertisements with future dates or exactly six months old remain unarchived.

---

## Summary
The test suite comprehensively validates the core functionalities of Task 1, covering a range of scenarios to ensure robustness and correctness of the implementation. Each test method targets specific behaviors in positive, negative, and edge cases, ensuring the system meets its intended requirements.

