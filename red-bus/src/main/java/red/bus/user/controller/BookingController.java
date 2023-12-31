package red.bus.user.controller;

import com.stripe.exception.StripeException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import red.bus.user.payload.BookingDto;
import red.bus.user.payload.PassengerDetails;
import red.bus.user.service.BookingService;
import red.bus.util.EmailService;
import red.bus.util.PdfService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

  private BookingService bookingService;
  private EmailService emailService;
  private PdfService pdfService;

  public BookingController(BookingService bookingService, EmailService emailService,
                           PdfService pdfService) {
    this.bookingService = bookingService;
    this.emailService = emailService;
    this.pdfService = pdfService;
  }

  @PostMapping
  public ResponseEntity<BookingDto> bookBus(
          @RequestParam("busId") String busId,
          @RequestParam("ticketId") String ticketId,
          @RequestBody PassengerDetails passengerDetails) throws StripeException {
    BookingDto booking = bookingService.createBooking(busId, ticketId, passengerDetails);

    if ("Payment failed. Please check your payment details and try again.".equals(booking.getMessage())) {
      return new ResponseEntity<>(booking, HttpStatus.BAD_REQUEST);
    } else {
      if (booking != null) {
        // Send confirmation email
        emailService.sendEmail(passengerDetails.getEmail(), "Booking Confirmed" + booking.getBookingId(),
                "Your booking is confirmed\n Name: " + passengerDetails.getFirstName() + passengerDetails.getLastName());

        // Generate PDF
        try {
          // Generate PDF
          byte[] pdfBytes = pdfService.generatePdf(booking);

          // Send confirmation email with PDF attachment
          emailService.sendEmailWithAttachment(
                  passengerDetails.getEmail(),
                  "Booking Confirmed " + booking.getBookingId(),
                  "Your booking is confirmed\n Name: " + passengerDetails.getFirstName() + passengerDetails.getLastName(),
                  "booking_details.pdf", // Attachment file name
                  pdfBytes
          );

        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }
  }

  @GetMapping("/seatInfo/{busId}")
  public ResponseEntity<String> getSeatAvailabilityInfo(@PathVariable String busId) {
    String seatDetails = bookingService.getSeatAvailabilityInfo(busId);
    return new ResponseEntity<>(seatDetails,HttpStatus.OK);
  }
  @GetMapping("/{busId}/booked-seats")
  public List<Integer> getAllBookedSeats(@PathVariable String busId) {
    return bookingService.getAllBookedSeats(busId);
  }
}

