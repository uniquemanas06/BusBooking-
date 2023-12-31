package red.bus.util;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import org.springframework.stereotype.Service;
import red.bus.user.payload.CancellationDto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class CancellationPdfService {

    public byte[] generatePdf(CancellationDto cancellationDto) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (PdfWriter writer = new PdfWriter(baos);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Set font
            PdfFont font = PdfFontFactory.createFont();
            document.setFont(font);

            document.add(new Paragraph("Email: " + cancellationDto.getEmail()));
            document.add(new Paragraph("Message : " + cancellationDto.getMessage()));
            document.add(new Paragraph("Refund Amount: " + cancellationDto.getRefundAmount()));

        }

        return baos.toByteArray();
    }
}
