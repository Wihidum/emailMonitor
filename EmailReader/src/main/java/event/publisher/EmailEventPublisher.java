package event.publisher;

import event.publisher.util.KeyStoreUtil;
import org.wso2.carbon.databridge.agent.thrift.DataPublisher;
import org.wso2.carbon.databridge.agent.thrift.exception.AgentException;
import org.wso2.carbon.databridge.commons.exception.*;
import org.wso2.carbon.databridge.core.exception.DataBridgeException;
import java.net.MalformedURLException;



public class EmailEventPublisher {

    /**
     *
     * @param metaData
     * @param correlationData
     * @param payloadData
     * @throws DataBridgeException
     * @throws AgentException
     * @throws MalformedURLException
     * @throws AuthenticationException
     * @throws TransportException
     * @throws MalformedStreamDefinitionException
     * @throws StreamDefinitionException
     * @throws DifferentStreamDefinitionAlreadyDefinedException
     * @throws InterruptedException
     */
    public static void publish(Object[] metaData,Object[]correlationData , Object[]payloadData)
            throws DataBridgeException, AgentException, MalformedURLException,
            AuthenticationException, TransportException, MalformedStreamDefinitionException,
            StreamDefinitionException, DifferentStreamDefinitionAlreadyDefinedException,
            InterruptedException {

        KeyStoreUtil.setTrustStoreParams();

        //according to the convention the authentication port will be 7611+100= 7711 and its host will be the same

        DataPublisher dataPublisher = new DataPublisher("tcp://localhost:7611", "admin", "admin");

        String streamId;

        //sample stream definition
        try {
            streamId = dataPublisher.findStream("email_Statistics", "1.0.0");
        } catch (NoStreamDefinitionExistException e) {
            streamId = dataPublisher.defineStream("{" +
                    "  'name':'email_Statistics'," +
                    "  'version':'1.0.0'," +
                    "  'nickName': 'Analytics Statistics Information'," +
                    "  'description': 'Details of Analytics Statistics'," +
                    "  'metaData':[" +
                    "          {'name':'emailID','type':'STRING'}" +
                    "  ]," +
                    "  'payloadData':[" +
                    "          {'name':'content','type':'STRING'}," +
                    "          {'name':'sender','type':'STRING'}" +
                    "  ]" +
                    "}");

        }
        Thread.sleep(1000);

      //  dataPublisher.publish(streamId, new Object[]{"192.168.1.1"}, null, new Object[]{"abc@org1.com", "CEP"});

        dataPublisher.publish(streamId, metaData, correlationData, payloadData);


        Thread.sleep(3000);
        dataPublisher.stop();
    }
}

