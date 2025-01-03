import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class Task1Test {

    private MarketingStaff staff;
    private PaymentProcessor paymentProcessor;
    private ProcessingCenter processingCenter;
    private ArchiveManager archiveManager;

    @BeforeEach
    void setUp() {
        staff = new MarketingStaff("M001", "John Doe");
        paymentProcessor = new PaymentProcessor();
        processingCenter = new ProcessingCenter();
        archiveManager = new ArchiveManager();
    }

    @Test
    void testCaptureAdvertisementDetails() {
        // Positive case
        staff.captureAdvertisementDetails("Advertiser A", "contact@a.com", "Valid content", "Front Page", LocalDate.of(2025, 1, 15), "Large");
        assertEquals(1, staff.advertisements.size());
        assertEquals("A001", staff.advertisements.get(0).getAdvertID());

        // Negative case: Missing content
        assertThrows(IllegalArgumentException.class, () -> {
            staff.captureAdvertisementDetails("Advertiser B", "contact@b.com", "", "Back Page", LocalDate.of(2025, 2, 10), "Medium");
        });
    }

    @Test
    void testReviewAdvertisementSuitability() {
        // Positive case
        Advertisement ad = new Advertisement("A001", "Advertiser A", "contact@a.com", "Valid content", "Front Page", LocalDate.of(2025, 1, 15), "Large");
        staff.reviewAdvertisementSuitability(ad);
        assertEquals("Approved", ad.getReviewStatus());

        // Negative case: Inappropriate content
        ad = new Advertisement("A002", "Advertiser B", "contact@b.com", "inappropriate content", "Back Page", LocalDate.of(2025, 2, 10), "Medium");
        staff.reviewAdvertisementSuitability(ad);
        assertEquals("Rejected", ad.getReviewStatus());
    }

    @Test
    void testConfirmPayment() {
        // Positive case
        Advertisement ad = new Advertisement("A001", "Advertiser A", "contact@a.com", "Valid content", "Front Page", LocalDate.of(2025, 1, 15), "Large");
        paymentProcessor.confirmPayment(ad);
        assertTrue(ad.isPaid());

        // Negative case: Double payment confirmation
        paymentProcessor.confirmPayment(ad);
        assertTrue(ad.isPaid()); // Still true, but log double confirmation

        // Edge case: Null advertisement
        assertThrows(NullPointerException.class, () -> paymentProcessor.confirmPayment(null));
    }

    @Test
    void testApproveAdvertisement() {
        // Positive case
        Advertisement ad = new Advertisement("A001", "Advertiser A", "contact@a.com", "Valid content", "Front Page", LocalDate.of(2025, 1, 15), "Large");
        staff.reviewAdvertisementSuitability(ad);
        paymentProcessor.confirmPayment(ad);
        staff.approveAdvertisement(ad);
        assertEquals("Approved", ad.getReviewStatus());

        // Negative case: Unpaid advertisement
        ad = new Advertisement("A002", "Advertiser B", "contact@b.com", "Valid content", "Back Page", LocalDate.of(2025, 2, 10), "Medium");
        staff.reviewAdvertisementSuitability(ad);
        staff.approveAdvertisement(ad);
        assertEquals("Not Paid", ad.getReviewStatus());

        // Edge case: Null advertisement
        assertThrows(NullPointerException.class, () -> staff.approveAdvertisement(null));
    }

    @Test
    void testReceiveAdvertisement() {
        // Positive case
        Advertisement ad = new Advertisement("A001", "Advertiser A", "contact@a.com", "Valid content", "Front Page", LocalDate.of(2025, 1, 15), "Large");
        staff.reviewAdvertisementSuitability(ad);
        paymentProcessor.confirmPayment(ad);
        processingCenter.receiveAdvertisement(ad);
        assertTrue(ad.isProcessed());

        // Negative case: Unapproved advertisement
        ad = new Advertisement("A002", "Advertiser B", "contact@b.com", "Valid content", "Back Page", LocalDate.of(2025, 2, 10), "Medium");
        processingCenter.receiveAdvertisement(ad);
        assertFalse(ad.isProcessed());
    }

    @Test
    void testArchiveUnusedAdvertisements() {
        // Positive case
        Advertisement ad = new Advertisement("A001", "Advertiser A", "contact@a.com", "Valid content", "Front Page", LocalDate.of(2023, 1, 15), "Large");
        staff.advertisements.add(ad);
        archiveManager.archiveUnusedAdvertisements(staff.advertisements);
        List<Advertisement> archivedAds = archiveManager.getArchivedAdvertisements();
        assertEquals(1, archivedAds.size());
        assertEquals("A001", archivedAds.get(0).getAdvertID());
        
        
        // Negative case: Active advertisement
        ad = new Advertisement("A002", "Advertiser B", "contact@b.com", "Valid content", "Back Page", LocalDate.now().plusMonths(1), "Medium");
        staff.advertisements.add(ad);
        archiveManager.archiveUnusedAdvertisements(staff.advertisements);
        archivedAds = archiveManager.getArchivedAdvertisements();
        assertEquals(1, archivedAds.size()); // Shouldnt be added

        // Edge case: Advertisement exactly six months old
        ad = new Advertisement("A003", "Advertiser C", "contact@c.com", "Valid content", "Middle Page", LocalDate.now().minusMonths(6), "Small");
        staff.advertisements.add(ad);
        archiveManager.archiveUnusedAdvertisements(staff.advertisements);
        archivedAds = archiveManager.getArchivedAdvertisements();
        assertEquals(1, archivedAds.size()); // Shouldnt be Added
    }
}
