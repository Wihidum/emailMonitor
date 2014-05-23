package event.publisher.util;


import email.reader.data.MailEvent;
import event.publisher.EmailEventPublisher;
import org.apache.log4j.Logger;

public class EventPublisherHelper {
    private static Logger logger = Logger.getLogger(EventPublisherHelper.class);




    public void extractEventInfo(MailEvent mailEvent) {
        Object[] metaData = new Object[]{mailEvent.getMessageID()};
        Object[]payloadData = new Object[]{mailEvent.getThreadID(), mailEvent.getSubject(), mailEvent.getSentTime(), mailEvent.getMailfrom(), mailEvent.getContent()};
        sendToPublish(metaData,null,payloadData);

    }

    public void  sendToPublish(Object[] metaData,Object[]correlationData , Object[]payloadData ){
        try {
            EmailEventPublisher.publish(metaData,correlationData,payloadData);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());

        }

    }
}
