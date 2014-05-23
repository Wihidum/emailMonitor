package email.reader.executor;


import email.reader.util.EmailReaderConstants;
import email.reader.util.EmailReaderHelper;
import org.apache.log4j.Logger;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;

public class EmailFetcher {

    private static Logger logger = Logger.getLogger(EmailFetcher.class);


    public void fetchEmail(String userName, String password) {

        Folder inbox = EmailReaderHelper.getFolder(EmailReaderConstants.GIMAPS, EmailReaderConstants.INBOX, userName, password);


        try {
            inbox.open(Folder.READ_WRITE); // Folder.READ_ONLY
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }

        inbox.addMessageCountListener(new MessageCountListener() {

            @Override
            public void messagesRemoved(MessageCountEvent arg0) {
            }

            @Override
            public void messagesAdded(MessageCountEvent arg0) {
                try {

                    Message[] messages = arg0.getMessages();
                    if (messages != null && messages.length > 0) {
                        for (Message message : messages) {
                            EmailReaderHelper.getMailEvent(message);
                        }
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }


        });


    }


}