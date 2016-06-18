package Gof23.Observable;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者
 *
 * @author Administrator
 */
public class PeopleB implements Observer {
    private String name = "张B";

    public void update(Observable o, Object arg) {
        System.out.println(this.name + " read " + arg);
    }
}
