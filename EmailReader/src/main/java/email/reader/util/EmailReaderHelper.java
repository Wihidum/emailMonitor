package email.reader.util;


import email.reader.data.MailEvent;
import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.mail.*;


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

    public static Folder getFolder(String storeType,String folderType, String username, String password) {
        Folder folder = null;
        Properties props = new Properties();
        try {
            String path = new File("").getAbsolutePath();
            props.load(new FileInputStream(new File(path + EmailReaderConstants.SETTINGS_FILE_PATH)));
            Session session = Session.getDefaultInstance(props, null);
            Store store = null;
            store = session.getStore(storeType);
            store.connect(EmailReaderConstants.SMTP_GMAIL_COM, username, password);
           folder = store.getFolder(folderType);
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
        if(! inbox.isOpen()){
            try {
                inbox.open(Folder.READ_WRITE);
            } catch (MessagingException e) {
              logger.error(e.getMessage());
            }
        }
        SearchTerm term = new SubjectTerm(subject);
        Message[] messages = null;
        try {
            messages = inbox.search(term);
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }
        return messages;
    }

    public static MailEvent getMailEvent(Message message) {
        MailEvent event = new MailEvent();
        try {


            Address[] in = message.getFrom();
            if(in != null) {
                for (Address address : in) {
                    event.addMailfrom(address.toString());
                }
            }
            Address[] to = message.getRecipients(Message.RecipientType.TO);
            if(to != null) {
                for (Address addto : to) {
                    event.addMailto(addto.toString());
                }
            }
            Address[] bcc = message.getRecipients(Message.RecipientType.BCC);
            if(bcc != null) {
                for (Address addbcc : bcc) {
                    event.addMailbcc(addbcc.toString());
                }
            }
            Address[] cc = message.getRecipients(Message.RecipientType.CC);
            if(cc != null) {
                for (Address addcc : cc) {
                    event.addMailcc(addcc.toString());
                }
            }

            Address[] replyTo = message.getReplyTo();
            if(replyTo != null) {
                for (Address addrep : replyTo) {
                    event.addReply_to(addrep.toString());
                }
            }
            Address[] recp = message.getAllRecipients();
            if(recp != null) {
                for (Address addrecp : recp) {
                    event.addRecipents(addrecp.toString());
                }
            }

            String subjectM = message.getSubject();
            event.setSubject(subjectM);


            Date sent = message.getSentDate();
            event.setSendDate(sent.toLocaleString());


            if (message.getContent() instanceof Multipart) {
                Multipart multipart = (Multipart) message.getContent();

                for (int x = 0; x < multipart.getCount(); x++) {
                    BodyPart bodyPart = multipart.getBodyPart(x);

                    String disposition = bodyPart.getDisposition();

                    if (disposition != null && (disposition.equals(BodyPart.ATTACHMENT))) {
                        event.setHaveAttachement(true);
                        DataHandler handler = bodyPart.getDataHandler();
                        event.setAttachedFileName(handler.getName());
                        event.setAttachedFileType(handler.getContentType());
                    } else {
                        event.setContent((String)bodyPart.getContent());
                    }


                }
            } else {
              event.setContent(message.getContent().toString());

            }
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return event;
    }


}
