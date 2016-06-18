//package gof23.Proxy;
//import java.lang.reflect.Proxy;
//
//public class Test {
//    public static void main(String[] args) throws SecurityException, NoSuchMethodException, Throwable {
//        //JDK代理
//        gof23.Proxy.Proxy imp = new gof23.Proxy.Proxy();
//        IProxy proxy = (IProxy) Proxy.newProxyInstance(imp.getClass().getClassLoader(), imp.getClass().getInterfaces(), new gof23.Proxy.ProxyHandler(imp));
//        proxy.doSomething();
//
//        //cglig代理
//        CgLibProxy cg = new CgLibProxy();
//        IProxy cgProxy = (IProxy) cg.getInstance(imp);
//        cgProxy.doSomething();
//    }
//}
