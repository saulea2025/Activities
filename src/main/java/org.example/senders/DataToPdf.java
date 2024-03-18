package org.example.senders;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.example.DAO.ScheduleDB;
import org.example.models.ScheduleForPDF;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DataToPdf {
    private static final float margin = 50;
    private static final float rowHeight = 20;

    public static void generatePdfFromResultSet(OutputStream outputStream) {
        List<ScheduleForPDF> schedules = ScheduleDB.getSchedule();
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            PDFont font = new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN);
            contentStream.setFont(font, 10);

            float yStart = page.getMediaBox().getHeight() - margin;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float bottomMargin = 70;
            float yPosition = yStart;
            float cellMargin = 5f;

            String[] headers = {"Time of Start", "Person's Name", "Task", "Priority", "Status"};
            float[] columnWidths = {tableWidth * 0.2f, tableWidth * 0.2f, tableWidth * 0.2f, tableWidth * 0.2f, tableWidth * 0.2f};

            drawTable(contentStream, yStart, tableWidth, bottomMargin, headers, columnWidths, 10);

            for (ScheduleForPDF schedule : schedules) {
                drawRow(contentStream, yPosition -= rowHeight, tableWidth, rowHeight, schedule);
            }

            contentStream.close();
            document.save(outputStream);
            System.out.println("PDF created successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void drawTable(PDPageContentStream contentStream, float yStart, float tableWidth, float bottomMargin,
                                  String[] headers, float[] columnWidths, float fontSize) throws IOException {
        float headerHeight = 25;

        contentStream.setStrokingColor(Color.BLACK);
        contentStream.setNonStrokingColor(Color.BLACK);


        // Drawing headers
        PDFont font = new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN);
        float yHeader = yStart - headerHeight + fontSize - 2; // Move headers closer to the top
        float x = margin; // Initialize x-coordinate for the first header
        for (int i = 0; i < headers.length; i++) {
            float xOffset = (columnWidths[i] - font.getStringWidth(headers[i]) / 1000 * fontSize) / 2;
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.newLineAtOffset(x + xOffset, yHeader);
            contentStream.showText(headers[i]);
            contentStream.endText();
            x += columnWidths[i]; // Update x-coordinate for the next header
        }

        // Drawing rows
        float nextY = yStart - headerHeight; // Adjust initial y position for rows
        for (int i = 0; i < headers.length; i++) {
            contentStream.moveTo(margin + columnWidths[i], yStart);
            contentStream.lineTo(margin + columnWidths[i], nextY);
            contentStream.stroke();
        }
        contentStream.moveTo(margin, yStart);
        contentStream.lineTo(margin + tableWidth, yStart);
        contentStream.stroke();
        for (int i = 0; i < headers.length; i++) {
            contentStream.moveTo(margin + columnWidths[i], yStart);
            contentStream.lineTo(margin + columnWidths[i], nextY);
            contentStream.stroke();
        }
        contentStream.moveTo(margin, nextY);
        contentStream.lineTo(margin + tableWidth, nextY);
        contentStream.stroke();
    }



    private static void drawRow(PDPageContentStream contentStream, float y, float tableWidth, float rowHeight,
                                ScheduleForPDF schedule) throws IOException {
        float fontSize = 10;
        float cellMargin = 5f;

        PDFont font = new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN);

        contentStream.beginText();
        contentStream.newLineAtOffset(margin + cellMargin, y - rowHeight / 2 - fontSize / 2); // Adjust y position for rows
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        contentStream.showText(schedule.getLocalDateTime().format(formatter));
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(margin + tableWidth * 0.2f + cellMargin, y - rowHeight / 2 - fontSize / 2); // Adjust y position for rows
        contentStream.showText(schedule.getPersonName());
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(margin + tableWidth * 0.4f + cellMargin, y - rowHeight / 2 - fontSize / 2); // Adjust y position for rows
        contentStream.showText(schedule.getTask());
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(margin + tableWidth * 0.6f + cellMargin, y - rowHeight / 2 - fontSize / 2); // Adjust y position for rows
        contentStream.showText(schedule.getPriority());
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(margin + tableWidth * 0.8f + cellMargin, y - rowHeight / 2 - fontSize / 2); // Adjust y position for rows
        contentStream.showText(schedule.getStatus());
        contentStream.endText();
    }
}
