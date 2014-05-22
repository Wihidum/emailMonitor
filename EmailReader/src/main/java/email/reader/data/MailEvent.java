package email.reader.data;


public class MailEvent {

  private String mailfrom;
  private String mailto;
  private String subject;
  private String sendDate;
  private String[] recipeintList;


    public String getMailfrom() {
        return mailfrom;
    }

    public void setMailfrom(String mailfrom) {
        this.mailfrom = mailfrom;
    }

    public String getMailto() {
        return mailto;
    }

    public void setMailto(String mailto) {
        this.mailto = mailto;
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

    public String[] getRecipeintList() {
        return recipeintList;
    }

    public void setRecipeintList(String[] recipeintList) {
        this.recipeintList = recipeintList;
    }
}
