package gof23.Adapter;

/**
 * Created by xuchun on 15/3/7.
 */
public class Target implements ITarget {
    @Override
    public void debug(String info) {
        System.out.println("我在用log4j的日志:" + info);

    }
}
