import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Task2Test {

    private MarketingStaff staff;
    private PaymentProcessor paymentProcessor;
    private ProcessingCenter processingCenter;
    private ArchiveManager archiveManager;
    private CommandExecutor executor;

    @BeforeEach
    public void setUp() {
        staff = new MarketingStaff("M001", "John Doe");
        paymentProcessor = new PaymentProcessor();
        processingCenter = new ProcessingCenter();
        archiveManager = new ArchiveManager();
        executor = new CommandExecutor();
    }

    @Test
    public void testCaptureAdvertisement_PositiveCase() {
        executor.addCommand(new CaptureAdvertisementCommand(staff, "Advertiser A", "contact@a.com", "Valid ad", "Front Page", LocalDate.of(2025, 1, 15), "Large"));
        executor.executeCommands();

        assertEquals(1, staff.advertisements.size());
        assertEquals("Valid ad", staff.advertisements.get(0).getContent());
    }

    
    @Test
    public void testCaptureAdvertisement_NegativeCase() {
        assertThrows(IllegalArgumentException.class, () -> {
            executor.addCommand(new CaptureAdvertisementCommand(staff, "Advertiser A", "contact@a.com", "", "Front Page", LocalDate.of(2025, 1, 15), "Large"));
            executor.executeCommands();
        });
    }

    @Test
    public void testReviewAdvertisement_PositiveCase() {
        Advertisement ad = new Advertisement("A001", "Advertiser A", "contact@a.com", "Valid ad", "Front Page", LocalDate.of(2025, 1, 15), "Large");
        staff.advertisements.add(ad);

        executor.addCommand(new ReviewAdvertisementCommand(staff, ad));
        executor.executeCommands();

        assertEquals("Approved", ad.getReviewStatus());
    }

    @Test
    public void testReviewAdvertisement_NegativeCase() {
        Advertisement ad = new Advertisement("A002", "Advertiser B", "contact@b.com", "inappropriate ad", "Back Page", LocalDate.of(2025, 2, 10), "Medium");
        staff.advertisements.add(ad);

        executor.addCommand(new ReviewAdvertisementCommand(staff, ad));
        executor.executeCommands();

        assertEquals("Rejected", ad.getReviewStatus());
    }

    @Test
    public void testConfirmPayment_PositiveCase() {
        Advertisement ad = new Advertisement("A001", "Advertiser A", "contact@a.com", "Valid ad", "Front Page", LocalDate.of(2025, 1, 15), "Large");
        staff.advertisements.add(ad);

        executor.addCommand(new ConfirmPaymentCommand(paymentProcessor, ad));
        executor.executeCommands();

        assertTrue(ad.isPaid());
    }

    @Test
    public void testConfirmPayment_EdgeCase() {
        Advertisement ad = new Advertisement("A001", "Advertiser A", "contact@a.com", "Valid ad", "Front Page", LocalDate.of(2025, 1, 15), "Large");
        staff.advertisements.add(ad);

        // Payment not confirmed
        assertFalse(ad.isPaid());
    }

    @Test
    public void testApproveAdvertisement_PositiveCase() {
        Advertisement ad = new Advertisement("A001", "Advertiser A", "contact@a.com", "Valid ad", "Front Page", LocalDate.of(2025, 1, 15), "Large");
        ad.setReviewStatus("Approved");
        ad.setPaid(true);
        staff.advertisements.add(ad);

        executor.addCommand(new ApproveAdvertisementCommand(staff, ad));
        executor.executeCommands();

        assertEquals("Approved", ad.getReviewStatus());
    }

    @Test
    public void testApproveAdvertisement_NegativeCase() {
        Advertisement ad = new Advertisement("A002", "Advertiser B", "contact@b.com", "Valid ad", "Back Page", LocalDate.of(2025, 1, 15), "Medium");
        ad.setReviewStatus("Rejected");
        ad.setPaid(false);
        staff.advertisements.add(ad);

        executor.addCommand(new ApproveAdvertisementCommand(staff, ad));
        executor.executeCommands();

        assertEquals("Rejected", ad.getReviewStatus());
    }

    @Test
    public void testForwardAdvertisement_PositiveCase() {
        Advertisement ad = new Advertisement("A001", "Advertiser A", "contact@a.com", "Valid ad", "Front Page", LocalDate.of(2025, 1, 15), "Large");
        ad.setReviewStatus("Approved");
        ad.setPaid(true);
        staff.advertisements.add(ad);

        executor.addCommand(new ForwardAdvertisementCommand(processingCenter, ad));
        executor.executeCommands();

        assertTrue(ad.isProcessed());
    }

    @Test
    public void testArchiveAdvertisements_EdgeCase() {
        Advertisement ad = new Advertisement("A001", "Advertiser A", "contact@a.com", "Valid ad", "Front Page", LocalDate.of(2023, 1, 15), "Large");
        staff.advertisements.add(ad);

        executor.addCommand(new ArchiveAdvertisementCommand(archiveManager, staff.advertisements));
        executor.executeCommands();

        assertEquals(1, archiveManager.getArchivedAdvertisements().size());
        assertEquals("A001", archiveManager.getArchivedAdvertisements().get(0).getAdvertID());
    }

    @Test
    public void testCommandExecutor_ClearQueueAfterExecution() {
        executor.addCommand(new CaptureAdvertisementCommand(staff, "Advertiser A", "contact@a.com", "Valid ad", "Front Page", LocalDate.of(2025, 1, 15), "Large"));
        executor.executeCommands();

        assertTrue(executor.getCommandQueue().isEmpty());
    }
}
