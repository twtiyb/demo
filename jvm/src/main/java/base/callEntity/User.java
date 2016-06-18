package base.callEntity;

import java.util.Date;

public class User {
    private String userName;
    private String userPass;
    private Long sss;
    private Date date;

    public Long getSss() {
        return sss;
    }

    public void setSss(Long sss) {
        this.sss = sss;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
