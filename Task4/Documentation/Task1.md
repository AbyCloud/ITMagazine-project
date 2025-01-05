# Documentation for Task 1 (Advertisement Management System)

## Overview

This document provides a detailed explanation of the Advertisement Management System. It describes the purpose of each class and its methods, as well as any additional functionality implemented beyond the initial class diagram provided.

## Classes and Their Responsibilities

### 1. MarketingStaff

Represents a marketing staff member responsible for managing advertisements.

#### Attributes:

- **staffID**: A unique identifier for the staff member.
- **name**: The name of the staff member.
- **advertisements**: A list of advertisements managed by the staff member.

#### Methods:

- **`captureAdvertisementDetails()`**:
  Captures the details of a new advertisement and adds it to the list of advertisements.
  
  **Parameters**: Advertiser name, contact information, content, placement preferences, appearance date, and size.

- **`reviewAdvertisementSuitability()`**:
  Reviews the suitability of an advertisement by invoking the `ReviewProcess` class.

- **`approveAdvertisement()`**:
  Approves an advertisement if it has been reviewed and the payment is confirmed. Otherwise, marks the advertisement as "Not Paid" or "Rejected."

- **`getAdvertisementsByStatus()`**:
  Retrieves advertisements filtered by their status (e.g., "Approved," "Rejected").

### 2. Advertisement

Represents an advertisement and its lifecycle.

#### Attributes:

- **advertID**: Unique identifier for the advertisement.
- **advertiserName**: Name of the advertiser.
- **contactInfo**: Contact details of the advertiser.
- **content**: The content of the advertisement.
- **placementPreferences**: Where the advertisement should appear in the magazine.
- **appearanceDate**: The date the advertisement is scheduled to appear.
- **reviewStatus**: Status of the review process (e.g., "Pending," "Approved," "Rejected").
- **isPaid**: Boolean indicating whether payment has been confirmed.
- **size**: Size of the advertisement.
- **isProcessed**: Boolean indicating whether the advertisement has been processed for publication.

#### Methods:

- **`getDetails()`**:
  Returns a formatted string containing the advertisement details.

- **`recordDetails()`**:
  Adds the advertisement to a static list for global access.

### 3. ArchiveManager

Handles the archiving of advertisements that are unused for six months or more.

#### Attributes:

- **archivedAdvertisements**: A list of archived advertisements.

#### Methods:

- **`archiveUnusedAdvertisements()`**:
  Archives advertisements that have not been processed and are older than six months from the current date.

- **`getArchivedAdvertisements()`**:
  Retrieves the list of archived advertisements.

### 4. PaymentProcessor

Handles payment confirmation and status checks for advertisements.

#### Attributes:

- None (stateless operations).

#### Methods:

- **`confirmPayment()`**:
  Confirms the payment for an advertisement by setting the `isPaid` attribute to `true`.

- **`checkPaymentStatus()`**:
  Checks whether the payment for a specific advertisement has been confirmed.

### 5. ReviewProcess

Manages the review and approval of advertisements.

#### Attributes:

- None (stateless operations).

#### Methods:

- **`assessSuitability()`**:
  Assesses the suitability of an advertisement's content. Marks the advertisement as "Approved" or "Rejected" based on predefined rules.

- **`approveForForwarding()`**:
  Approves an advertisement for processing if it meets all criteria (not explicitly implemented in the code).

### 6. ProcessingCenter

Represents the center responsible for processing advertisements for publication.

#### Attributes:

- None.

#### Methods:

- **`receiveAdvertisement()`**:
  Processes an advertisement for publication if it is both approved and paid. Marks the advertisement as processed.

## Additional Features Beyond the Initial Diagram

### 1. Advertisement Class Enhancements

- **`recordDetails()`**:
  A new method to add advertisements to a global static list for centralized tracking.

- **`isProcessed` Attribute**:
  Tracks whether an advertisement has been processed for publication.

### 2. MarketingStaff Class Enhancements

- **`getAdvertisementsByStatus()`**:
  Allows filtering of advertisements based on their review status.

### 3. Edge Case Handling

- The `ReviewProcess` class handles borderline content with "almost inappropriate" phrases.
- The `ArchiveManager` explicitly checks for advertisements exactly six months old to ensure consistent behavior.

### 4. Expanded Workflow

- Added handling for advertisements that are paid but fail the review process (e.g., marked as "Rejected" even after payment).
- Integrated `ProcessingCenter` to finalize advertisements ready for publication.

## Summary

This system effectively manages the lifecycle of advertisements, including their creation, review, payment, approval, processing, and archival. Additional functionality has been implemented to ensure robustness, such as handling edge cases, filtering by status, and centralized advertisement tracking. The modular design enables scalability and ease of maintenance.

