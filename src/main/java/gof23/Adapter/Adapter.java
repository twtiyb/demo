package gof23.Adapter;

/**
 * Created by xuchun on 15/3/7.
 */
public class Adapter implements IAdapter {
    private Target tar;

    public Adapter(Target tar) {
        this.tar = tar;
    }


    @Override
    public void debug(String info) {
        tar.debug(info);
    }
}
