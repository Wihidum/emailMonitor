package email.reader.util;


import email.reader.data.MailEvent;
import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.mail.*;

import javax.mail.internet.InternetAddress;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class EmailReaderHelper {

    private static Logger logger = Logger.getLogger(EmailReaderHelper.class);

    public static Folder getFolder(String folderType, String username, String password) {
        Folder folder = null;
        Properties props = new Properties();
        try {
            String path = new File("").getAbsolutePath();
            props.load(new FileInputStream(new File(path + EmailReaderConstants.SETTINGS_FILE_PATH)));
            Session session = Session.getDefaultInstance(props, null);
            Store store = null;
            store = session.getStore(EmailReaderConstants.IMAPS);
            store.connect(EmailReaderConstants.SMTP_GMAIL_COM, username, password);
            store.getFolder(folderType);
        } catch (FileNotFoundException e1) {
            logger.error(e1.getMessage());
        } catch (IOException e1) {
            logger.error(e1.getMessage());
        } catch (NoSuchProviderException e) {
            logger.error(e.getMessage());
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }
        return folder;
    }

    public static Message[] getMails(String subject, Folder inbox) {
        SearchTerm term = new SubjectTerm(subject);
        Message[] messages = null;
        try {
            messages = inbox.search(term);
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }
        return messages;
    }

    public static MailEvent getMailEvent(Message message){
        MailEvent event = new MailEvent();
        try {

            String from = InternetAddress.toString(message.getFrom());
            String replyTo = InternetAddress.toString(
                    message.getReplyTo());

            String to = InternetAddress.toString(
                    message.getRecipients(Message.RecipientType.TO));


            String subjectM = message.getSubject();

            Date sent = message.getSentDate();
            event.setMailfrom(from);
            event.setMailto(to);
            event.setSubject(subjectM);
            event.setSendDate(sent.toLocaleString());
//            if (message.getContent() instanceof Multipart) {
//                Multipart multipart = (Multipart) message.getContent();
//
//                for (int x = 0; x < multipart.getCount(); x++) {
//                    BodyPart bodyPart = multipart.getBodyPart(x);
//
//                    String disposition = bodyPart.getDisposition();
//
//                    if (disposition != null && (disposition.equals(BodyPart.ATTACHMENT))) {
//                        System.out.println("Mail have some attachment : ");
//
//                        DataHandler handler = bodyPart.getDataHandler();
//                        System.out.println("file name : " + handler.getName());
//                    } else {
//                        System.out.println(bodyPart.getContent());
//                    }
//
//
//                }
//            }
//            else{
//                System.out.println(message.getContent().toString());
//            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }

return event;
    }








}
