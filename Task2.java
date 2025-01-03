// Concrete Commands

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Command Interface
// This interface defines a common execute method for all commands.
interface Command {
    void execute();
}

// Concrete Command: Capture Advertisement
// Encapsulates the logic to capture advertisement details using the MarketingStaff class.
class CaptureAdvertisementCommand implements Command {
    private MarketingStaff staff;
    private String advertiserName;
    private String contactInfo;
    private String content;
    private String placementPreferences;
    private LocalDate appearanceDate;
    private String size;

    public CaptureAdvertisementCommand(MarketingStaff staff, String advertiserName, String contactInfo, String content,
                                        String placementPreferences, LocalDate appearanceDate, String size) {
        this.staff = staff;
        this.advertiserName = advertiserName;
        this.contactInfo = contactInfo;
        this.content = content;
        this.placementPreferences = placementPreferences;
        this.appearanceDate = appearanceDate;
        this.size = size;
    }

    @Override
    public void execute() {
        staff.captureAdvertisementDetails(advertiserName, contactInfo, content, placementPreferences, appearanceDate, size);
    }
}

// Concrete Command: Review Advertisement
// Encapsulates the logic to review the suitability of an advertisement.
class ReviewAdvertisementCommand implements Command {
    private MarketingStaff staff;
    private Advertisement advertisement;

    public ReviewAdvertisementCommand(MarketingStaff staff, Advertisement advertisement) {
        this.staff = staff;
        this.advertisement = advertisement;
    }

    @Override
    public void execute() {
        staff.reviewAdvertisementSuitability(advertisement);
    }
}

// Concrete Command: Confirm Payment
// Encapsulates the logic to confirm payment for an advertisement.
class ConfirmPaymentCommand implements Command {
    private PaymentProcessor paymentProcessor;
    private Advertisement advertisement;

    public ConfirmPaymentCommand(PaymentProcessor paymentProcessor, Advertisement advertisement) {
        this.paymentProcessor = paymentProcessor;
        this.advertisement = advertisement;
    }

    @Override
    public void execute() {
        paymentProcessor.confirmPayment(advertisement);
    }
}

// Concrete Command: Approve Advertisement
// Encapsulates the logic to approve an advertisement after review and payment confirmation.
class ApproveAdvertisementCommand implements Command {
    private MarketingStaff staff;
    private Advertisement advertisement;

    public ApproveAdvertisementCommand(MarketingStaff staff, Advertisement advertisement) {
        this.staff = staff;
        this.advertisement = advertisement;
    }

    @Override
    public void execute() {
        staff.approveAdvertisement(advertisement);
    }
}

// Concrete Command: Forward Advertisement
// Encapsulates the logic to forward an advertisement to the processing center.
class ForwardAdvertisementCommand implements Command {
    private ProcessingCenter processingCenter;
    private Advertisement advertisement;

    public ForwardAdvertisementCommand(ProcessingCenter processingCenter, Advertisement advertisement) {
        this.processingCenter = processingCenter;
        this.advertisement = advertisement;
    }

    @Override
    public void execute() {
        processingCenter.receiveAdvertisement(advertisement);
    }
}

// Concrete Command: Archive Advertisements
// Encapsulates the logic to archive advertisements that are unused or outdated.
class ArchiveAdvertisementCommand implements Command {
    private ArchiveManager archiveManager;
    private List<Advertisement> advertisements;

    public ArchiveAdvertisementCommand(ArchiveManager archiveManager, List<Advertisement> advertisements) {
        this.archiveManager = archiveManager;
        this.advertisements = advertisements;
    }

    @Override
    public void execute() {
        archiveManager.archiveUnusedAdvertisements(advertisements);
    }
}

// Invoker
// This class manages a queue of commands and executes them sequentially.
class CommandExecutor {
    private List<Command> commandQueue = new ArrayList<>();

    // Adds a command to the queue
    public void addCommand(Command command) {
        getCommandQueue().add(command);
    }

    // Executes all commands in the queue
    public void executeCommands() {
        for (Command command : getCommandQueue()) {
            command.execute();
        }
        getCommandQueue().clear(); // Clear the queue after execution
    }

	public List<Command> getCommandQueue() {
		return commandQueue;
	}

	public void setCommandQueue(List<Command> commandQueue) {
		this.commandQueue = commandQueue;
	}
}

// Client Code (Main Class)
public class Task2 {
    public static void main(String[] args) {
        // Initialize components
        MarketingStaff staff = new MarketingStaff("M001", "John Doe");
        PaymentProcessor paymentProcessor = new PaymentProcessor();
        ProcessingCenter processingCenter = new ProcessingCenter();
        ArchiveManager archiveManager = new ArchiveManager();
        CommandExecutor executor = new CommandExecutor();

        // Step 1: Capture advertisements using Command
        System.out.println("=== Step 1: Capture Advertisement Details ===");
        executor.addCommand(new CaptureAdvertisementCommand(staff, "Advertiser A", "contact@a.com", "This is a valid ad.", "Front Page", LocalDate.of(2025, 1, 15), "Large"));
        executor.addCommand(new CaptureAdvertisementCommand(staff, "Advertiser B", "contact@b.com", "This ad is inappropriate.", "Back Page", LocalDate.of(2025, 2, 10), "Medium"));
        executor.addCommand(new CaptureAdvertisementCommand(staff, "Advertiser C", "contact@c.com", "Another valid ad.", "Middle Page", LocalDate.of(2025, 3, 5), "Small"));
        executor.addCommand(new CaptureAdvertisementCommand(staff, "Advertiser D", "contact@d.com", "Valid ad but not paid.", "Front Page", LocalDate.of(2025, 4, 1), "Large")); // Not paid
        executor.addCommand(new CaptureAdvertisementCommand(staff, "Advertiser E", "contact@e.com", "This content is inappropriate.", "Back Page", LocalDate.of(2025, 4, 10), "Medium")); // Paid but review rejected
        executor.addCommand(new CaptureAdvertisementCommand(staff, "Advertiser F", "contact@f.com", "Old ad for archival.", "Middle Page", LocalDate.of(2023, 1, 15), "Small")); // Should be archived
        executor.executeCommands(); // Execute all capture commands

        // Step 2: Review advertisements
        System.out.println("\n=== Step 2: Review Advertisement Suitability ===");
        for (Advertisement ad : staff.advertisements) {
            executor.addCommand(new ReviewAdvertisementCommand(staff, ad));
        }
        executor.executeCommands(); // Execute all review commands

        // Step 3: Confirm payment for selected advertisements
        System.out.println("\n=== Step 3: Confirm Payment ===");
        executor.addCommand(new ConfirmPaymentCommand(paymentProcessor, staff.advertisements.get(0))); // Payment for A
        executor.addCommand(new ConfirmPaymentCommand(paymentProcessor, staff.advertisements.get(2))); // Payment for C
        executor.addCommand(new ConfirmPaymentCommand(paymentProcessor, staff.advertisements.get(4))); // Payment for E
        executor.executeCommands(); // Execute all payment confirmation commands

        // Step 4: Approve advertisements
        System.out.println("\n=== Step 4: Approve Advertisements ===");
        for (Advertisement ad : staff.advertisements) {
            executor.addCommand(new ApproveAdvertisementCommand(staff, ad));
        }
        executor.executeCommands(); // Execute all approval commands

        // Step 5: Forward advertisements to processing center
        System.out.println("\n=== Step 5: Forward Advertisements ===");
        for (Advertisement ad : staff.advertisements) {
            executor.addCommand(new ForwardAdvertisementCommand(processingCenter, ad));
        }
        executor.executeCommands(); // Execute all forwarding commands

        // Step 6: Archive advertisements
        System.out.println("\n=== Step 6: Archive Unused Advertisements ===");
        executor.addCommand(new ArchiveAdvertisementCommand(archiveManager, staff.advertisements));
        executor.executeCommands(); // Execute the archive command

        // Display archived advertisements
        System.out.println("\nArchived Advertisements:");
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
