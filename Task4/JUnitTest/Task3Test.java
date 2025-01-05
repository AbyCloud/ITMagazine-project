import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Task3Test {

   private MarketingStaff staff;
   private PaymentProcessor paymentProcessor;
   private ProcessingCenter processingCenter;
   private ArchiveManager archiveManager;

   @BeforeEach
   public void setUp() {
       staff = new MarketingStaff("M001", "John Doe");
       paymentProcessor = new PaymentProcessor();
       processingCenter = new ProcessingCenter();
       archiveManager = new ArchiveManager();
   }

   @Test
   public void testValidateAdvertisementDetails_PositiveCase() {
       Advertisement ad = new Advertisement("A001", "Advertiser A", "contact@a.com", "Valid content", "Front Page", LocalDate.of(2025, 1, 15), "Large");
       assertDoesNotThrow(ad::validateDetails);
   }

   @Test
   public void testValidateAdvertisementDetails_NegativeCase() {
       Advertisement ad = new Advertisement("A002", "", "contact@b.com", "", "Back Page", LocalDate.of(2025, 2, 10), "Medium");
       assertThrows(IllegalArgumentException.class, ad::validateDetails);
   }

   @Test
   public void testCaptureAdvertisement_PositiveCase() {
       assertDoesNotThrow(() -> staff.captureAdvertisementDetails("Advertiser A", "contact@a.com", "Valid ad content", "Front Page", LocalDate.of(2025, 1, 15), "Large"));
       assertEquals(1, staff.advertisements.size());
   }

   @Test
   public void testReviewAdvertisementSuitability_PositiveCase() {
       Advertisement ad = new Advertisement("A001", "Advertiser A", "contact@a.com", "Valid content", "Front Page", LocalDate.of(2025, 1, 15), "Large");
       staff.advertisements.add(ad);
       assertDoesNotThrow(() -> staff.reviewAdvertisementSuitability(ad));
       assertEquals("Approved", ad.getReviewStatus());
   }

   @Test
   public void testReviewAdvertisementSuitability_NegativeCase() {
       Advertisement ad = new Advertisement("A002", "Advertiser B", "contact@b.com", "inappropriate content", "Back Page", LocalDate.of(2025, 2, 10), "Medium");
       staff.advertisements.add(ad);
       assertDoesNotThrow(() -> staff.reviewAdvertisementSuitability(ad));
       assertEquals("Rejected", ad.getReviewStatus());
   }

   @Test
   public void testConfirmPayment_PositiveCase() {
       Advertisement ad = new Advertisement("A001", "Advertiser A", "contact@a.com", "Valid content", "Front Page", LocalDate.of(2025, 1, 15), "Large");
       staff.advertisements.add(ad);
       assertDoesNotThrow(() -> paymentProcessor.confirmPayment(ad));
       assertTrue(ad.isPaid());
   }

   @Test
   public void testConfirmPayment_NotPaidBeforePublication() {
       Advertisement ad = new Advertisement("A001", "Advertiser A", "contact@a.com", "Valid content", "Front Page", LocalDate.of(2025, 1, 15), "Large");
       staff.advertisements.add(ad);
       assertThrows(IllegalStateException.class, ad::ensurePaidBeforePublication);
   }

   @Test
   public void testArchiveUnusedAdvertisements_PositiveCase() {
       Advertisement ad = new Advertisement("A001", "Advertiser A", "contact@a.com", "Valid content", "Front Page", LocalDate.of(2023, 1, 15), "Large");
       staff.advertisements.add(ad);
       archiveManager.archiveUnusedAdvertisements(staff.advertisements);
       assertTrue(ad.isArchived());
   }

   @Test
   public void testArchiveUnusedAdvertisements_NegativeCase() {
       Advertisement ad = new Advertisement("A002", "Advertiser B", "contact@b.com", "Valid content", "Back Page", LocalDate.of(2025, 1, 15), "Medium");
       staff.advertisements.add(ad);
       archiveManager.archiveUnusedAdvertisements(staff.advertisements);
       assertFalse(ad.isArchived());
   }

   @Test
   public void testProcessingAdvertisement_PositiveCase() {
       Advertisement ad = new Advertisement("A001", "Advertiser A", "contact@a.com", "Valid content", "Front Page", LocalDate.of(2025, 1, 15), "Large");
       ad.setPaid(true);
       ad.setReviewStatus("Approved");
       staff.advertisements.add(ad);
       assertDoesNotThrow(() -> processingCenter.receiveAdvertisement(ad));
       assertTrue(ad.isProcessed());
   }

   @Test
   public void testProcessingAdvertisement_NotApproved() {
       Advertisement ad = new Advertisement("A002", "Advertiser B", "contact@b.com", "Valid content", "Back Page", LocalDate.of(2025, 1, 15), "Medium");
       ad.setPaid(true);
       ad.setReviewStatus("Rejected");
       staff.advertisements.add(ad);
       assertDoesNotThrow(() -> processingCenter.receiveAdvertisement(ad));
       assertFalse(ad.isProcessed());
   }
}
