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
        System.out.println("事物开始");
        method.invoke(this.targetObj, args);   //如果用参数里的proxy，就会出现死循环
        System.out.println("事物结束");
        return null;
    }
}
