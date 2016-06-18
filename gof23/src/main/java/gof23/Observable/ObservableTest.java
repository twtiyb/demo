package Gof23.Observable;

import java.util.Observable;

public class ObservableTest extends Observable {
    public static void main(String[] args) {
        //创建被观察者
        ObservableTest test = new ObservableTest();

        //创建观察者
        PeopleA a = new PeopleA();
        PeopleB b = new PeopleB();
        //把观察者加入到被观察者的名单中
        test.addObserver(a);
        test.addObserver(b);

        //设置被观察者可以被观察了
        test.setChanged();
        //观察者看了之后，可以做出自己的反应了。
        test.notifyObservers("新闻");
    }
}
