package email.reader.executor;


import email.reader.data.MailEvent;
import email.reader.util.EmailReaderConstants;
import email.reader.util.EmailReaderHelper;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;


public class Test {


    public static void main(String[] args) {
        Folder folder = EmailReaderHelper.getFolder(EmailReaderConstants.IMAPS,EmailReaderConstants.INBOX,"synapse.demo.1@gmail.com","mailpassword1");
        try {
            folder.open(Folder.READ_WRITE);
           Message[] messages = EmailReaderHelper.getMails("Message Thread Detecting", folder);
            for(Message message:messages){
             MailEvent mailEvent = EmailReaderHelper.getMailEvent(message);
                System.out.println(mailEvent);
            }








        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }























}
