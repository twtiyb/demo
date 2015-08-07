package reflect.objectToBean.bean;

public class User {
    private String name;
    private String pass;
    private Car car = new Car();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
