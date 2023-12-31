package red.bus.util;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;
import red.bus.user.payload.BookingDto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService {

    public byte[] generatePdf(BookingDto bookingDto) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (PdfWriter writer = new PdfWriter(baos);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Set font
            PdfFont font = PdfFontFactory.createFont();
            document.setFont(font);


            // Add content to the PDF
            document.add(new Paragraph("Booking Details").setFont(font).setBold().setFontSize(18));
            document.add(new Paragraph("Booking ID: " + bookingDto.getBookingId()));
            document.add(new Paragraph("Bus ID: " + bookingDto.getBusId()));
            document.add(new Paragraph("Ticket ID: " + bookingDto.getTicketId()));
            document.add(new Paragraph("Bus Company: " + bookingDto.getBusCompany()));
            document.add(new Paragraph("Bus Number: " + bookingDto.getBusNumber()));
            document.add(new Paragraph("Departure City: " + bookingDto.getDepartureCity()));
            document.add(new Paragraph("Arrival City: " + bookingDto.getArrivalCity()));
            document.add(new Paragraph("First Name: " + bookingDto.getFirstName()));
            document.add(new Paragraph("Last Name: " + bookingDto.getLastName()));
            document.add(new Paragraph("Email: " + bookingDto.getEmail()));
            document.add(new Paragraph("Mobile: " + bookingDto.getMobile()));
            document.add(new Paragraph("Cost: " + bookingDto.getCost()));
            document.add(new Paragraph("Departure Date: " + bookingDto.getDepartureDate()));
            document.add(new Paragraph("Arrival Date: " + bookingDto.getArrivalDate()));
            document.add(new Paragraph("Departure Time: " + bookingDto.getDepartureTime()));
            document.add(new Paragraph("Arrival Time: " + bookingDto.getArrivalTime()));

        }

        return baos.toByteArray();
    }
}
