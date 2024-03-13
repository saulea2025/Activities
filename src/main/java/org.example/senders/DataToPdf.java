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

import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;
import java.util.List;

public class DataToPdf {
    public static void generatePdfFromResultSet(OutputStream outputStream) {
        int personId = 1;//нужно переназначить, пока что статично для меня
        List<ScheduleForPDF> schedules = ScheduleDB.getSchedulesForPerson(personId);//для таблицы
        Person person = PersonDB.getById(personId);//для первой строки в файле. Или можно в качестве названия pdf
        try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);

                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                int verticalOffset = 0;
                for(ScheduleForPDF schedule : schedules){
                    contentStream.newLineAtOffset(0, -verticalOffset);
                    contentStream.showText(schedule.toString());
                    verticalOffset += 15;
                }
                contentStream.endText();
                contentStream.close();
                document.save(outputStream);
                System.out.println("PDF created successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
