package org.example.senders;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataToPdf {
    public static void generatePdfFromResultSet(OutputStream outputStream) {
        try (ResultSet resultSet = DataFetcher.fetchData()) {
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                // Removing rotation (making it portrait)
                document.addPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // Set font for content
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);

                // Draw table header
                drawTableHeader(contentStream);

                // Draw table content
                drawTableContent(contentStream, resultSet);

                contentStream.close();
                document.save(outputStream);
                System.out.println("PDF created successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void drawTableHeader(PDPageContentStream contentStream) throws IOException {
        // Set background color for header
        contentStream.setNonStrokingColor(Color.BLUE);

        // Set text color for header
        contentStream.setStrokingColor(Color.BLACK);

        // Draw header background
        contentStream.addRect(50, 750, 500, 20);
        contentStream.fill();

        // Draw header text
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.beginText();
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);
        contentStream.newLineAtOffset(60, 753);
        contentStream.showText("ID Person Activity Date");
        contentStream.endText();
    }

    private static void drawTableContent(PDPageContentStream contentStream, ResultSet resultSet) throws IOException, SQLException {
        // Set font for content
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);

        // Set text color for content
        contentStream.setNonStrokingColor(Color.BLACK);

        // Draw table content
        int verticalOffset = 0;
        while (resultSet.next()) {
            String id = String.valueOf(resultSet.getInt("id"));
            String person = resultSet.getString("person");
            String activity = resultSet.getString("activity");
            String date = resultSet.getDate("date").toString();

            String data = id + " " + person + " " + activity + " " + date;

            // Draw content text
            contentStream.beginText();
            contentStream.newLineAtOffset(60, 730 - verticalOffset);
            contentStream.showText(data);
            contentStream.endText();

            // Update vertical offset
            verticalOffset += 15;
        }
    }
}