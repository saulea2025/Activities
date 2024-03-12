package services;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class EmailSender {
    private Properties sessionProps;
    private Properties librarySenderProps;
    private static final String SMTP_HOST = "smtp.gmail.com";

    public EmailSender() {
        sessionProps = new Properties();
        librarySenderProps = new Properties();
    }

    public void setProps() {
        try (FileInputStream sessionFile = new FileInputStream("src/files/session.properties");
             FileInputStream librarySenderFile = new FileInputStream("src/files/librarySender.properties")) {
            sessionProps.load(sessionFile);
            librarySenderProps.load(librarySenderFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendPdfReport(String header, String text, UserBase to) {
        ByteArrayOutputStream pdfOutputStream = generatePdfReport(header, text);

        Session session = Session.getInstance(sessionProps, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(librarySenderProps.getProperty("email"),
                        librarySenderProps.getProperty("password"));
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(librarySenderProps.getProperty("email")));
            message.setSubject(header);

            Multipart multipart = new MimeMultipart();

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(text);
            multipart.addBodyPart(textPart);

            MimeBodyPart pdfAttachment = new MimeBodyPart();
            pdfAttachment.setFileName("report.pdf");
            pdfAttachment.setContent(pdfOutputStream.toByteArray(), "application/pdf");
            multipart.addBodyPart(pdfAttachment);

            message.setContent(multipart);

            StringBuilder emails = new StringBuilder();
            for (User user : to.getUserList()) {
                emails.append(user.getEmail());
                emails.append(", ");
            }
            String emailsToSend = emails.toString();
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailsToSend));
            Transport.send(message);
            System.out.println("PDF report email sent!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error sending email");
        } finally {
            try {
                pdfOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ByteArrayOutputStream generatePdfReport(String header, String text) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText(header);
                contentStream.newLine();
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.showText(text);
                contentStream.endText();
            }

            document.save(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream;
    }
}

