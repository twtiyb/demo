package gof23.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {
    public Object targetObj;

    public ProxyHandler(Object obj) {
        this.targetObj = obj;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        System.out.println("做饭");
        method.invoke(this.targetObj, args);
        return null;
    }
}
