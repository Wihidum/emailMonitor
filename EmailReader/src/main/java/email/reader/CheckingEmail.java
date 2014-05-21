package email.reader;

    import java.io.File;
    import java.io.FileInputStream;
    import java.io.FileNotFoundException;
    import java.io.IOException;
    import java.util.Properties;

    import javax.mail.Flags;
    import javax.mail.Folder;
    import javax.mail.Message;
    import javax.mail.Session;
    import javax.mail.Store;

    public class CheckingEmail {

        /**
         * @param args
         */
        public static void main(String[] args) throws Exception {

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

            Store store = session.getStore("imaps");
            store
                    .connect("smtp.gmail.com", "irjanith@gmail.com",
                            "IJR@17IJR@17");
            System.out.println(store);

            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_WRITE); // Folder.READ_ONLY
            int messageCount = inbox.getMessageCount();
            System.out.println("Total Messages" + messageCount);
            int startMessage = messageCount - 25;
            int endMessage = messageCount;

            if (messageCount < 5) {
                startMessage = 0;
            }

            Message[] messages = inbox.getMessages(startMessage-5, endMessage);

            for (Message message : messages) {

                boolean isMessageRead = true;

                for (Flags.Flag flag : message.getFlags().getSystemFlags()) {
                    if (flag == Flags.Flag.SEEN) {
                        isMessageRead = true;
                        break;
                    }
                }

                message.setFlag(Flags.Flag.SEEN, true);
                System.out.println(message.getSubject() + " "
                        + (isMessageRead ? " [READ]" : " [UNREAD]"));
                System.out.println(message.getContent().toString());

            }

            inbox.close(true);
            System.out.println("Done....");
            store.close();
        }
    }





































