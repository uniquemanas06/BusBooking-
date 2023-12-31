package red.bus.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import red.bus.user.payload.CancellationDto;
import red.bus.user.service.CancellationService;
import red.bus.util.CancellationPdfService;
import red.bus.util.EmailService;

import java.io.IOException;

@RestController
@RequestMapping("/api/cancellations")
public class CancellationController {

    private CancellationService cancellationService;
    private EmailService emailService;
    private CancellationPdfService pdfService;

    public CancellationController(CancellationService cancellationService, EmailService emailService, CancellationPdfService pdfService) {
        this.cancellationService = cancellationService;
        this.emailService = emailService;
        this.pdfService = pdfService;
    }

    @PostMapping
    public ResponseEntity<?> cancelBooking(@RequestParam String bookingId, @RequestParam String email) {
        CancellationDto cancellationResult = cancellationService.cancelBooking(bookingId, email);

        if (cancellationResult != null) {
            try {
                // Generate PDF
                byte[] pdfBytes = pdfService.generatePdf(cancellationResult);

                // Send cancellation confirmation email with PDF attachment
                sendCancellationConfirmationEmail(email, pdfBytes);

                return new ResponseEntity<>(cancellationResult, HttpStatus.OK);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Error generating PDF", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Handle cancellation failure
            return new ResponseEntity<>("Cancellation not completed", HttpStatus.BAD_REQUEST);
        }
    }

    private void sendCancellationConfirmationEmail(String email, byte[] pdfBytes) {
        try {
            // Send confirmation email with PDF attachment
            emailService.sendEmailWithAttachment(
                    email,
                    "Booking Canceled",
                    "Your booking has been canceled.",
                    "cancellation_details.pdf", // Attachment file name
                    pdfBytes
            );
        } catch (Exception e) {
            // Handle exception if email sending fails
            e.printStackTrace();
        }
    }
}
