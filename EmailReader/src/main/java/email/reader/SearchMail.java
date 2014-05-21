package email.reader;

import javax.activation.DataHandler;
import javax.mail.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

public class SearchMail {


    public static void main(String[] args) throws Exception {

        Properties props = new Properties();
        try {
            String path = new File("").getAbsolutePath();
            System.out.print(path);
            props.load(new FileInputStream(new File(path + "/src/main/resources/properties/settings.properties")));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Session session = Session.getDefaultInstance(props, null);

        Store store = session.getStore("imaps");
        store
                .connect("smtp.gmail.com", "irjanith@gmail.com",
                        "IJR@17IJR@17");
        System.out.println(store);

        Folder inbox = store.getFolder("inbox");
        inbox.open(Folder.READ_WRITE); // Folder.READ_ONLY



        String subject = "Tomcat dependency on application server";
        SearchTerm term = null;
        term = new SubjectTerm(subject);
        int messageCount = inbox.getMessageCount();
        System.out.println("Total Messages" + messageCount);
        int startMessage = messageCount - 25;
        int endMessage = messageCount;

        if (messageCount < 5) {
            startMessage = 0;
        }


        Message[] messages = inbox.search(term);

        for (Message message : messages) {

            boolean isMessageRead = true;

            for (Flags.Flag flag : message.getFlags().getSystemFlags()) {
                if (flag == Flags.Flag.SEEN) {
                    isMessageRead = true;
                    break;
                }
            }

//            message.setFlag(Flags.Flag.SEEN, true);
//            System.out.println(message.getSubject() + " "
//                    + (isMessageRead ? " [READ]" : " [UNREAD]"));
//            System.out.println(message.getContent().toString());

            String from = InternetAddress.toString(message.getFrom());
            if (from != null) {
                System.out.println("From: " + from);
            }

            String replyTo = InternetAddress.toString(
                    message.getReplyTo());
            if (replyTo != null) {
                System.out.println("Reply-to: " + replyTo);
            }
            String to = InternetAddress.toString(
                    message.getRecipients(Message.RecipientType.TO));
            if (to != null) {
                System.out.println("To: " + to);
            }

            String subjectM = message.getSubject();
            if (subjectM != null) {
                System.out.println("Subject: " + subject);
            }
            Date sent = message.getSentDate();
            if (sent != null) {
                System.out.println("Sent: " + sent);
            }

            System.out.println();
            System.out.println("Message : ");


            if (message.getContent() instanceof Multipart) {
                Multipart multipart = (Multipart) message.getContent();

                for (int x = 0; x < multipart.getCount(); x++) {
                    BodyPart bodyPart = multipart.getBodyPart(x);

                    String disposition = bodyPart.getDisposition();

                    if (disposition != null && (disposition.equals(BodyPart.ATTACHMENT))) {
                        System.out.println("Mail have some attachment : ");

                        DataHandler handler = bodyPart.getDataHandler();
                        System.out.println("file name : " + handler.getName());
                    } else {
                        System.out.println(bodyPart.getContent());
                    }


                }
            }
            else{
                System.out.println(message.getContent().toString());
            }
        }

        inbox.close(true);
        System.out.println("Done....");
        store.close();
    }
}

