package Collections;

import java.io.Serializable;

public class Student implements Cloneable, Serializable {
    private Long stuId;
    private String name;
    private String adress;
    private Long gradId;

    public Long getStuId() {
        return stuId;
    }

    public void setStuId(Long stuId) {
        this.stuId = stuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Long getGradId() {
        return gradId;
    }

    public void setGradId(Long gradId) {
        this.gradId = gradId;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }
}
