import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 创建日期 2007-8-22
 */

/**
 * DES加密算法
 *
 * @author jyh
 */
public class test {

    public test() {
        String aString;
    }

    /**
     * 以下代码为测试用
     *
     * @throws UnsupportedEncodingException
     * @throws ParseException
     */
    public static void main(String[] args) throws UnsupportedEncodingException, ParseException {
        String url = "http://sss saa 天天";
        String urlEn = URLEncoder.encode(url, "UTF-8");
        String urlDe = URLDecoder.decode(urlEn, "UTF-8");
        String urlTest = URLEncoder.encode(urlEn, "UTF-8");
        System.out.println(url);
        System.out.println(urlEn);
        System.out.println(urlDe);
        System.out.println(urlTest);


        test ss = new test();
        Object obj = ss.get("ss");
        if (obj instanceof String) {
            System.out.print("String " + obj);
        }
        if (obj instanceof Double) {
            System.out.print("Double " + obj);
        }
        if (ss instanceof test) {
            System.out.print("Double " + obj);
        }

        double rate1 = 0.290625;
        //四舍五入保留两位小数
        BigDecimal df = new BigDecimal(rate1);
        double rate2 = df.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        //rate2打印结果为0.29
        System.out.println(rate2);
        Double rate = rate2 * 100.0;
        //rate打印结果为28.9999999996
        System.out.println(String.valueOf(rate));
        test t = new test();
        Date date = new Date();
        date.setTime(date.getTime() + 3600000 * 88);
        System.out.println(t.getWorkPercent(new Date(), date));

    }

    public Object get(Object i) {
        return i;
    }

    public Double getWorkPercent(Date in, Date out) {
        //错误
        if (in.after(out)) {
            return 0D;
        }
        //将超过上下班时间设置为上下班时间
        //考虑周末
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        if ("星期日".equals(dateFm.format(in)) || "星期六".equals(dateFm.format(in))) {
            return 0D;
        }
        Date restStart = in;
        Date restEnd = in;
        Date recortStart = in;
        Date recortEnd = in;

        restStart.setHours(12);
        restStart.setMinutes(0);
        restStart.setSeconds(0);


        restEnd.setHours(13);
        restEnd.setMinutes(0);
        restEnd.setSeconds(0);

        recortStart.setHours(12);
        recortStart.setMinutes(0);
        recortStart.setSeconds(0);

        recortEnd.setHours(12);
        recortEnd.setMinutes(0);
        recortEnd.setSeconds(0);

        //上午时间
        Long morningTimes = restStart.getTime() - (in.after(recortStart) ? in.getTime() : recortStart.getTime());
        //下午时间
        Long afterTimes = (out.after(recortEnd) ? recortEnd.getTime() : out.getTime()) - restEnd.getTime();
        //上班时间 如果在休息时间之后，或者休息时间中间上班，下班。则上班总时间为0;
        Long totalTimes = (morningTimes < 0 ? 0 : morningTimes) + (afterTimes < 0 ? 0 : afterTimes);

        in.setHours(0);
        in.setMinutes(0);
        in.setSeconds(0);
        out.setHours(0);
        out.setMinutes(0);
        out.setSeconds(0);
        Long diff = 0L;
        if (in.compareTo(out) != 0) {
            in.setTime(in.getTime() + 3600000 * 24);
            while (in.compareTo(out) != 0) {
                if (!"星期日".equals(dateFm.format(in)) && !"星期六".equals(dateFm.format(in))) {
                    diff += 3600000 * 8;
                }
                in.setTime(in.getTime() + 3600000 * 24);
            }
        }
        return (totalTimes + diff) / 3600000.0;
    }


}