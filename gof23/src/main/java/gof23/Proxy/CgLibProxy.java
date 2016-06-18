//package gof23.Proxy;
//
//
//import java.lang.reflect.Method;
//
//public class CgLibProxy implements MethodInterceptor {
//    private Object target;
//
//    /**
//     * 创建代理对象
//     *
//     * @param target
//     * @return
//     */
//    public Object getInstance(Object target) {
//        this.target = target;
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(this.target.getClass());
//        // 回调方法
//        enhancer.setCallback(this);
//        // 创建代理对象
//        return enhancer.create();
//    }
//
//    @Override
//    // 回调方法
//    public Object intercept(Object obj, Method method, Object[] args,
//                            MethodProxy proxy) throws Throwable {
//        System.out.println("事物开始");
//        proxy.invokeSuper(obj, args);//虽然第一个参数是被代理对象，也不会出现死循环的问题。jdk的是循环。
//        System.out.println("事物结束");
//        return null;
//    }
//
//
//}
