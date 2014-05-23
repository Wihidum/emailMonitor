package email.reader.data;


import org.compass.core.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MailEvent {

    private List<String> recipents;
    private List<String> mailto;
    private List<String> mailcc;
    private List<String> mailbcc;
    private String mailfrom;
    private String subject;
    private String sendDate;
    private long sentTime;
    private long threadID;
    private long messageID;
    private List<String> reply_to;
    private boolean haveAttachement;
    private String attachedFileName;
    private String attachedFileType;
    private String content;


    public MailEvent() {
        recipents = new ArrayList<String>();
        reply_to = new ArrayList<String>();
        mailto = new ArrayList<String>();
        mailcc = new ArrayList<String>();
        mailbcc = new ArrayList<String>();

    }

    public String getMailfrom() {
        return mailfrom;
    }

    public void addMailfrom(String mailfrom) {
        this.mailfrom = mailfrom;
    }

    public List<String> getRecipents() {
        return recipents;
    }

    public void setRecipents(String recipent) {
        this.recipents.add(recipent);
    }

    public boolean isHaveAttachement() {
        return haveAttachement;
    }

    public void setHaveAttachement(boolean haveAttachement) {
        this.haveAttachement = haveAttachement;
    }

    public long getThreadID() {
        return threadID;
    }

    public void setThreadID(long threadID) {
        this.threadID = threadID;
    }

    public long getMessageID() {
        return messageID;
    }

    public void setMessageID(long messageID) {
        this.messageID = messageID;
    }

    public long getSentTime() {
        return sentTime;
    }

    public void setSentTime(long sentTime) {
        this.sentTime = sentTime;
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }


    public String getAttachedFileType() {
        return attachedFileType;
    }

    public void setAttachedFileType(String attachedFileType) {
        this.attachedFileType = attachedFileType;
    }

    public String getAttachedFileName() {
        return attachedFileName;
    }

    public void setAttachedFileName(String attachedFileName) {
        this.attachedFileName = attachedFileName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public List<String> getMailto() {
        return mailto;
    }

    public void addMailto(String mailto) {
        this.mailto.add(mailto);
    }

    public List<String> getMailcc() {
        return mailcc;
    }

    public void addMailcc(String mailcc) {
        this.mailcc.add(mailcc);
    }

    public List<String> getMailbcc() {
        return mailbcc;
    }

    public void addMailbcc(String mailbcc) {
        this.mailbcc.add(mailbcc);
    }

    public List<String> getReply_to() {
        return reply_to;
    }

    public void addReply_to(String reply_to) {
        this.reply_to.add(reply_to);
    }

    public void addRecipents(String recipents) {
        this.recipents.add(recipents);
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Message ID:" + messageID);
        buffer.append("\n");
        buffer.append("Thread ID:" + threadID);
        buffer.append("\n");
        buffer.append("Subject: " + subject);
        buffer.append("\n");
        buffer.append("From: " + mailfrom);
        String bcc = StringUtils.collectionToCommaDelimitedString(mailbcc);
        buffer.append("\n");
        buffer.append("Bcc: " + bcc);
        String cc = StringUtils.collectionToCommaDelimitedString(mailcc);
        buffer.append("\n");
        buffer.append("CC :" + cc);
        String to = StringUtils.collectionToCommaDelimitedString(mailto);
        buffer.append("\n");
        buffer.append("TO :" + to);
        String allrecp = StringUtils.collectionToCommaDelimitedString(recipents);
        buffer.append("\n");
        buffer.append("Recipients:" + allrecp);
        buffer.append("\n");
        buffer.append("content:" + content);
        buffer.append("\n");
        buffer.append("Attachement:" + isHaveAttachement());
        buffer.append("\n");
        if (isHaveAttachement()) {
            buffer.append("File Name:" + attachedFileName);
            buffer.append("\n");
            buffer.append("File type:" + attachedFileType);
            buffer.append("\n");
        }
        buffer.append("send Date:" + sendDate);
        buffer.append("\n");
        buffer.append("send Time:" + sentTime);
        buffer.append("\n");
        return buffer.toString();
    }


}
