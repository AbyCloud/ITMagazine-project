import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class containing all functionalities and a simulation of the workflow.
 */
public class Task1 {
    public static void main(String[] args) {
        // Initialize main components
        MarketingStaff staff = new MarketingStaff("M001", "John Doe");
        PaymentProcessor paymentProcessor = new PaymentProcessor();
        ProcessingCenter processingCenter = new ProcessingCenter();
        ArchiveManager archiveManager = new ArchiveManager();

        // Step 1: Capture advertisement details
        System.out.println("=== Step 1: Capture Advertisement Details ===");
        staff.captureAdvertisementDetails("Advertiser A", "contact@a.com", "This is a valid ad.", "Front Page", LocalDate.of(2025, 1, 15), "Large");
        staff.captureAdvertisementDetails("Advertiser B", "contact@b.com", "This ad is inappropriate.", "Back Page", LocalDate.of(2025, 2, 10), "Medium");
        staff.captureAdvertisementDetails("Advertiser C", "contact@c.com", "Another valid ad.", "Middle Page", LocalDate.of(2025, 3, 5), "Small");
        staff.captureAdvertisementDetails("Advertiser D", "contact@d.com", "Valid ad but not paid.", "Front Page", LocalDate.of(2025, 4, 1), "Large"); // Not paid
        staff.captureAdvertisementDetails("Advertiser E", "contact@e.com", "This content is inappropriate.", "Back Page", LocalDate.of(2025, 4, 10), "Medium"); // Paid but review rejected
        staff.captureAdvertisementDetails("Advertiser F", "contact@f.com", "Old ad for archival.", "Middle Page", LocalDate.of(2023, 1, 15), "Small"); // Should be archived

        // Step 2: Review advertisement suitability
        System.out.println("\n=== Step 2: Review Advertisement Suitability ===");
        for (Advertisement ad : staff.advertisements) {
            staff.reviewAdvertisementSuitability(ad);
        }

        // Step 3: Handle payment confirmation
        System.out.println("\n=== Step 3: Confirm Payment ===");
        paymentProcessor.confirmPayment(staff.advertisements.get(0)); // Confirm payment for first ad
        paymentProcessor.confirmPayment(staff.advertisements.get(2)); // Confirm payment for third ad
        paymentProcessor.confirmPayment(staff.advertisements.get(4)); // Confirm payment for fifth ad (rejected review)

        // Step 4: Approve advertisements
        System.out.println("\n=== Step 4: Approve Advertisements ===");
        for (Advertisement ad : staff.advertisements) {
            staff.approveAdvertisement(ad);
        }

        // Step 5: Forward advertisements to processing center
        System.out.println("\n=== Step 5: Forward Advertisements ===");
        for (Advertisement ad : staff.advertisements) {
            processingCenter.receiveAdvertisement(ad);
        }

        // Step 6: Archive unused advertisements
        System.out.println("\n=== Step 6: Archive Unused Advertisements ===");
        archiveManager.archiveUnusedAdvertisements(staff.advertisements);
        System.out.println("Archived Advertisements:");
        for (Advertisement archivedAd : archiveManager.getArchivedAdvertisements()) {
            System.out.println(archivedAd.getDetails());
        }

        // Step 7: View current advertisements
        System.out.println("\n=== Step 7: View Current Advertisements ===");
        for (Advertisement ad : staff.advertisements) {
            System.out.println(ad.getDetails());
        }
    }
}


/**
 * Represents an advertisement with its details and methods to manage its lifecycle.yes
 */
class Advertisement {
    private static List<Advertisement> advertisementList = new ArrayList<>(); // Static list to hold all advertisements

    private String advertID;
    private String advertiserName;
    private String contactInfo;
    private String content;
    private String placementPreferences;
    private LocalDate appearanceDate;
    private String reviewStatus;
    private boolean isPaid;
    private String size;
    private boolean isProcessed; // Tracks if the advertisement has been processed

    // Constructor to initialize an advertisement
    public Advertisement(String advertID, String advertiserName, String contactInfo, String content, String placementPreferences, LocalDate appearanceDate, String size) {
        this.advertID = advertID;
        this.advertiserName = advertiserName;
        this.contactInfo = contactInfo;
        this.content = content;
        this.placementPreferences = placementPreferences;
        this.appearanceDate = appearanceDate;
        this.reviewStatus = "Pending";
        this.isPaid = false;
        this.size = size;
        this.isProcessed = false;
    }

    // Getters and setters for advertisement fields
    public String getAdvertID() {
        return advertID;
    }

    public String getContent() {
        return content;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean isProcessed) {
        this.isProcessed = isProcessed;
    }

    // Returns the details of the advertisement as a formatted string
    public String getDetails() {
        return "ID: " + advertID + ", Advertiser: " + advertiserName + ", Content: " + content + ", Size: " + size + ", Status: " + reviewStatus;
    }
    
    // Records the advertisement into the static list
    public void recordDetails() {
        advertisementList.add(this);
        System.out.println("Advertisement added to the list: " + getDetails());
    }

    // Retrieves the static list of all advertisements
    public static List<Advertisement> getAdvertisementList() {
        return advertisementList;
    }

	public LocalDate getAppearanceDate() {
		return appearanceDate;
	}
}

/**
 * Represents a marketing staff member and manages advertisement operations.
 */
class MarketingStaff {
    private String staffID;
    private String name;
    public List<Advertisement> advertisements;

    // Constructor to initialize a marketing staff member
    public MarketingStaff(String staffID, String name) {
        this.staffID = staffID;
        this.name = name;
        this.advertisements = new ArrayList<>();
    }

    public void captureAdvertisementDetails(String advertiserName, String contactInfo, String content, String placementPreferences, LocalDate appearanceDate, String size) {
        // Validate inputs
        if (advertiserName == null || advertiserName.isEmpty()) {
            throw new IllegalArgumentException("Advertiser name cannot be null or empty.");
        }
        if (contactInfo == null || contactInfo.isEmpty()) {
            throw new IllegalArgumentException("Contact information cannot be null or empty.");
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty.");
        }
        if (placementPreferences == null || placementPreferences.isEmpty()) {
            throw new IllegalArgumentException("Placement preferences cannot be null or empty.");
        }
        if (appearanceDate == null) {
            throw new IllegalArgumentException("Appearance date cannot be null or in the past.");
        }
        if (size == null || size.isEmpty() || size.length() > 1000) { // Edge case: limit size length
            throw new IllegalArgumentException("Size cannot be null, empty, or excessively large.");
        }

        // Generate a unique advertisement ID
        String advertID = generateAdvertID();

        // Create and add the advertisement
        Advertisement ad = new Advertisement(advertID, advertiserName, contactInfo, content, placementPreferences, appearanceDate, size);
        advertisements.add(ad);

        // Log details
        System.out.println("Advertisement details captured: " + ad.getDetails());
    }


    // Generates a unique advertisement ID
    private String generateAdvertID() {
        int idNumber = advertisements.size() + 1;
        return String.format("A%03d", idNumber);
    }

    // Reviews the suitability of an advertisement
    public void reviewAdvertisementSuitability(Advertisement ad) {
        ReviewProcess reviewProcess = new ReviewProcess();
        reviewProcess.assessSuitability(ad);
    }

    // Approves an advertisement if it meets all criteria
    public void approveAdvertisement(Advertisement ad) {
        if (ad.getReviewStatus().equals("Approved")) {
            if (ad.isPaid()) {
//                ad.approveForForwarding();
                System.out.println("Advertisement approved for processing: " + ad.getAdvertID() + " - " +  ad.getReviewStatus());
            } else {
                ad.setReviewStatus("Not Paid");
                System.out.println("Advertisement cannot be approved: " + ad.getAdvertID() + " - " + ad.getReviewStatus());
            }
        } else {
            System.out.println("Advertisement cannot be approved: " + ad.getAdvertID() + " - " + ad.getReviewStatus());
        }
    }

    // Retrieves advertisements by their status
    public List<Advertisement> getAdvertisementsByStatus(String status) {
        List<Advertisement> filteredAds = new ArrayList<>();
        for (Advertisement ad : advertisements) {
            if (ad.getReviewStatus().equals(status)) {
                filteredAds.add(ad);
            }
        }
        return filteredAds;
    }
}

/**
 * Handles the archiving of unused advertisements.
 */
class ArchiveManager {
    private List<Advertisement> archivedAdvertisements = new ArrayList<>(); // List to store archived advertisements

    // Archives advertisements unused for six months
    public void archiveUnusedAdvertisements(List<Advertisement> ads) {
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        for (Advertisement ad : ads) {
            if (!ad.isProcessed() && ad.getAppearanceDate().isBefore(sixMonthsAgo) && !archivedAdvertisements.contains(ad)) {
                archivedAdvertisements.add(ad);
                System.out.println("Archiving advertisement: " + ad.getAdvertID());
            }
        }
    }



    // Retrieves the list of archived advertisements
    public List<Advertisement> getArchivedAdvertisements() {
        return archivedAdvertisements;
    }
}

/**
 * Handles payment confirmation for advertisements.
 */
class PaymentProcessor {
    // Confirms payment for an advertisement
    public void confirmPayment(Advertisement ad) {
        ad.setPaid(true);
        System.out.println("Payment confirmed for advertisement: " + ad.getAdvertID());
    }

    // Checks the payment status of an advertisement
    public boolean checkPaymentStatus(Advertisement ad) {
        return ad.isPaid();
    }
}

/**
 * Handles advertisement suitability checks and approval.
 */
class ReviewProcess {
    // Assesses the suitability of an advertisement
    public void assessSuitability(Advertisement ad) {
        if (ad.getContent().contains("inappropriate")) {
            ad.setReviewStatus("Rejected");
            System.out.println("Advertisement rejected for inappropriate content: "  + ad.getAdvertID());
        } else {
            ad.setReviewStatus("Approved");
            System.out.println("Advertisement approved for forwarding: "  + ad.getAdvertID());
        }
    }
}

/**
 * Handles the processing of advertisements.
 */
class ProcessingCenter {
    // Processes an advertisement for publication
    public void receiveAdvertisement(Advertisement ad) {
        if (ad.getReviewStatus().equals("Approved") && ad.isPaid()) {
            ad.setProcessed(true);
            System.out.println("Processing advertisement for publication: " + ad.getAdvertID());
        } else {
            System.out.println("Cannot process advertisement: " + ad.getAdvertID());
        }
    }
}
