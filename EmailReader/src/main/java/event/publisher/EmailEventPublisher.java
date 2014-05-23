package event.publisher;

import event.publisher.util.KeyStoreUtil;
import org.apache.log4j.Logger;
import org.wso2.carbon.databridge.agent.thrift.DataPublisher;
import org.wso2.carbon.databridge.agent.thrift.exception.AgentException;
import org.wso2.carbon.databridge.commons.exception.*;
import java.net.MalformedURLException;


public class EmailEventPublisher {
    public static final String RECEIVER_URL="tcp://localhost:7611";


    private static Logger logger = Logger.getLogger(EmailEventPublisher.class);


    /**
     * @param metaData
     * @param correlationData
     * @param payloadData
     */
    public static void publish(Object[] metaData, Object[] correlationData, Object[] payloadData) throws InterruptedException {

        KeyStoreUtil.setTrustStoreParams();


        DataPublisher dataPublisher = null;
        String streamId = null;

        try {
            dataPublisher = new DataPublisher(RECEIVER_URL, "admin", "admin");


            try {
                streamId = dataPublisher.findStream("gmail_Statistics", "1.0.0");

            } catch (NoStreamDefinitionExistException e) {

                try {
                    streamId = dataPublisher.defineStream("{" +
                            "  'name':'gmail_Statistics'," +
                            "  'version':'1.0.0'," +
                            "  'nickName': 'Email Statistics Information'," +
                            "  'description': 'Details of emails'," +
                            "  'metaData':[" +
                            "          {'name':'messageID','type':'LONG'}" +
                            "  ]," +
                            "  'payloadData':[" +
                            "          {'name':'threadID','type':'LONG'}," +
                            "          {'name':'subject','type':'STRING'}," +
                            "          {'name':'sentTime','type':'LONG'}," +
                            "          {'name':'from','type':'STRING'}," +
                            "          {'name':'content','type':'STRING'}" +
                            "  ]" +
                            "}");
                } catch (MalformedStreamDefinitionException e1) {
                    logger.error(e1.getMessage());
                } catch (StreamDefinitionException e1) {
                    logger.error(e1.getMessage());
                } catch (DifferentStreamDefinitionAlreadyDefinedException e1) {
                    logger.error(e1.getMessage());
                }


            } catch (StreamDefinitionException e) {
                logger.error(e.getMessage());
            }


            Thread.sleep(1000);
            dataPublisher.publish(streamId, metaData, correlationData, payloadData);

            Thread.sleep(3000);


            dataPublisher.stop();


        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
        } catch (AgentException e) {
            logger.error(e.getMessage());
        } catch (AuthenticationException e) {
            logger.error(e.getMessage());
        } catch (TransportException e) {
            logger.error(e.getMessage());
        }


    }


}

