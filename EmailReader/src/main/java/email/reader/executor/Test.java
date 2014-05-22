package email.reader.executor;


import com.sun.mail.gimap.GmailSSLStore;
import com.sun.mail.gimap.GmailStore;
import email.reader.data.MailEvent;
import email.reader.util.EmailReaderConstants;
import email.reader.util.EmailReaderHelper;

import javax.activation.DataHandler;
import javax.mail.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.sun.mail.gimap.GmailMessage;


public class Test {


    public static void main(String[] args) throws MessagingException {



        Properties props = new Properties();
        try {
            String path = new File("").getAbsolutePath();
            System.out.print(path);
            props.load(new FileInputStream(new File(path+"/src/main/resources/properties/settings.properties")));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }



        Session session = Session.getDefaultInstance(props, null);


        GmailSSLStore store = (GmailSSLStore) session.getStore("gimaps");
        try {
            store.connect("smtp.gmail.com", "synapse.demo.1@gmail.com",
                    "mailpassword1");
        } catch (MessagingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println(store);

        Folder inbox = store.getFolder("inbox");
        inbox.open(Folder.READ_WRITE); // Folder.READ_ONLY
        int messageCount = inbox.getMessageCount();
        System.out.println("Total Messages" + messageCount);
        int endMessage = messageCount;



        Message[] messages = inbox.getMessages(endMessage-2, endMessage);
        GmailMessage gm;
        for (Message message : messages) {
            gm = (GmailMessage) message;
            System.out.println("threadID"+gm.getThrId());
            System.out.println("sender"+gm.getSender().toString().split("<|>")[1]  );
            System.out.println("subject"+gm.getSubject());



            boolean isMessageRead = true;

            for (Flags.Flag flag : gm.getFlags().getSystemFlags()) {
                if (flag == Flags.Flag.SEEN) {
                    isMessageRead = true;
                    break;
                }
            }

            message.setFlag(Flags.Flag.SEEN, true);
            System.out.println(gm.getSubject() + " "
                    + (isMessageRead ? " [READ]" : " [UNREAD]"));
            try {
                System.out.println(gm.getContent());
                Object content = gm.getContent();
                Multipart multipart = (Multipart) content;

                for (int x = 0; x < multipart.getCount(); x++) {
                    System.out.println("******************************************"+ x);

                    BodyPart bodyPart = multipart.getBodyPart(x);

                    String disposition = bodyPart.getDisposition();

                    if (disposition != null && (disposition.equals(BodyPart.ATTACHMENT))) {
                        System.out.println("Mail have some attachment : ");

                        DataHandler handler = bodyPart.getDataHandler();
                        System.out.println("file name : " + handler.getName());
                    } else {
                        //    System.out.println(bodyPart.getContent());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }









        }

        inbox.close(true);
        System.out.println("Done....");
        store.close();
    }
    }























