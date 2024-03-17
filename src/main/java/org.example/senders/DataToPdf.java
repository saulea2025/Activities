package org.example.senders;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.example.DAO.PersonDB;
import org.example.DAO.ScheduleDB;
import org.example.models.Person;
import org.example.models.ScheduleForPDF;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class DataToPdf {
    public static void generatePdfFromResultSet(OutputStream outputStream) {
        List<ScheduleForPDF> schedules = ScheduleDB.getSchedule();
       // Person person = PersonDB.getById(personId);
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            PDType1Font pdType1Font = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            contentStream.setFont(pdType1Font, 10);

            // Set background color
            contentStream.setNonStrokingColor(Color.WHITE);
            contentStream.addRect(0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
            contentStream.fill();

            // Set header color
            contentStream.setNonStrokingColor(Color.BLUE);
            contentStream.addRect(0, page.getMediaBox().getHeight() - 20, page.getMediaBox().getWidth(), 20);
            contentStream.fill();

            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, page.getMediaBox().getHeight() - 10);
            contentStream.showText("Schedule for everyone");
            //contentStream.showText("Schedule for: " + person.getName()); // Assuming getName() retrieves person's name
            contentStream.endText();

            // Set table lines and borders color
            contentStream.setStrokingColor(Color.BLACK);

            int startY = (int) (page.getMediaBox().getHeight() - 40); // Start position for table
            int rowHeight = 15; // Height of each row

            for (ScheduleForPDF schedule : schedules) {
                contentStream.beginText();
                contentStream.newLineAtOffset(50, startY);
                contentStream.showText(schedule.toString());
                contentStream.endText();
                startY -= rowHeight;
            }

            contentStream.close();
            document.save(outputStream);
            System.out.println("PDF created successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
