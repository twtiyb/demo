package other;

/**
 * 测试程序运行时间
 *
 * @author Administrator
 */
public class NanoTime {
    private long start, end;

    public void testFor() {
        start = System.nanoTime();
        for (int i = 1, sum = 0; i <= 1000000000; i++)
            sum += i;
        end = System.nanoTime();
    }

    public long getUsedTime() {
        testFor();
        return end - start;
    }

    public static void main(String args[]) {
        NanoTime testFor = new NanoTime();
        System.out.println("执行代码后时间差单位为毫微秒:" + testFor.getUsedTime());// 单位为毫微秒
        System.out
                .println("执行代码后时间差单位为秒:" + testFor.getUsedTime() / 1000000000);// 单位秒

    }
}
/**
 * 第一种是以毫秒为单位计算的。
 *
 * Java代码
 *
 * //伪代码
 *
 * long startTime=System.currentTimeMillis(); //获取开始时间
 *
 * doSomeThing(); //测试的代码段
 *
 * long endTime=System.currentTimeMillis(); //获取结束时间
 *
 * System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
 *
 * 第二种是以纳秒为单位计算的。
 *
 * Java代码
 *
 * //伪代码
 *
 * long startTime=System.nanoTime(); //获取开始时间
 *
 * doSomeThing(); //测试的代码段
 *
 * long endTime=System.nanoTime(); //获取结束时间
 *
 * System.out.println("程序运行时间： "+(endTime-startTime)+"ns");
 *
 *
 *
 * 一般在实际应用中通常使用System.currentTimeMillis()
 * 来记录某段代码的运行时间。然而在需要精确计算某些时间的时候用该方法很可能您得到的时间为0，除非您的程序执行的时间超过1毫秒。
 * 那么有没有方法来记录这么短的时间呢？回答是肯定的。 java API中的System类给我们提供了nanoTime方法，对其描述如下
 * 返回最准确的可用系统计时器的当前值，以毫微秒为单位。
 * 此方法只能用于测量已过的时间，与系统或钟表时间的其他任何时间概念无关。返回值表示从某一固定但任意的时间算起的毫微秒数（或许从以后算起，所以该值可能为负）。此方法提供毫微秒的精度，但不是必要的毫微秒的准确度。它对于值的更改频率没有作出保证。在取值范围大于约
 * 292 年（263 毫微秒）的连续调用的不同点在于：由于数字溢出，将无法准确计算已过的时间 1秒=1000豪秒 1毫秒=1000微秒
 * 1微秒=1000毫微秒 所以1秒=1000*1000*1000=1000000000毫微秒
 *
 */
