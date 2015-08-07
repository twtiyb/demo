package test;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class test {
    // 申请的appkey
    public static final String appkey = "9da5e15d";
    // 申请的secret
    public static final String secret = "8b229ee1e4de43628c5401414b904ee6";
    // 申请的token
    public static final String token = "beaae0fa7da14b0faf3435ba7ddeb00b";

    // 主帐号
    public static final String dbhost = "edb_a12345";
    //返回格式
    public static final String format = "xml";
    //调用API地址
    protected static String testUrl = "http://vip802.6x86.com/edb2/rest/index.aspx";

    //修改客户分类会员分组
    public static void edbCustomerClassUpdate() {
        TreeMap<String, String> apiparamsMap = new TreeMap<String, String>();

        apiparamsMap.put("dbhost", dbhost);//添加请求参数——主帐号

        apiparamsMap.put("format", format);//添加请求参数——返回格式

        apiparamsMap.put("method", "edbCustomerClassUpdate");//添加请求参数——接口名称

        apiparamsMap.put("slencry", "0");//添加请求参数——返回结果是否加密（0，为不加密 ，1.加密）

        apiparamsMap.put("ip", "192.168.60.80");//添加请求参数——IP地址

        apiparamsMap.put("appkey", appkey);//添加请求参数——appkey

        apiparamsMap.put("appscret", secret);//添加请求参数——appscret

        apiparamsMap.put("token", token);//添加请求参数——token

        apiparamsMap.put("v", "2.0");//添加请求参数——版本号（目前只提供2.0版本）

        apiparamsMap.put("fields", "result");//添加请求参数——返回数据列

        String timestamp = new SimpleDateFormat("yyyyMMddHHmm")
                .format(new Date());

        apiparamsMap.put("timestamp", timestamp);//添加请求参数——时间戳

        apiparamsMap.put("type_no", "4,5,6");//客户分类编号（可以是多个，用逗号隔开）
        apiparamsMap.put("customers", "CU1207060001814,CU1207060001816");//会员编号（可以是多个，用逗号隔开）

        //获取数字签名
        String sign = Util.md5Signature(apiparamsMap, appkey);

        apiparamsMap.put("sign", sign);

        StringBuilder param = new StringBuilder();

        for (Iterator<Map.Entry<String, String>> it = apiparamsMap.entrySet()
                .iterator(); it.hasNext(); ) {
            Map.Entry<String, String> e = it.next();
            if (e.getKey() != "appscret" && e.getKey() != "token") {
                if (e.getKey() == "type_no" || e.getKey() == "customers") {
                    try {
                        param.append("&").append(e.getKey()).append("=").append(Util.encodeUri(e.getValue()));
                    } catch (UnsupportedEncodingException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } else {
                    param.append("&").append(e.getKey()).append("=").append(e.getValue());
                }
            }
        }


        String PostData = "";
        PostData = param.toString().substring(1);
        System.out.println(testUrl + "?" + PostData);
        String result = "";
        result = Util.getResult(testUrl, PostData);
        System.out.println(result);
    }

    /*创建客户分类信息
             根据传入的分类名称，如果名称在表中存在，提示分类已经存在
    0.父节点不能为空
    1.如果父节点存在：判断子节点是否存在，如果存在返回并提示“已经存在节点”，否则添加子节点
    2.如果父节点不存在：判断子节点是否为空，如果为空，添加父节点，否则返回并提示‘不存在父节点’
    */
    public static void edbCustomerClassCreate() {
        TreeMap<String, String> apiparamsMap = new TreeMap<String, String>();

        apiparamsMap.put("dbhost", dbhost);//添加请求参数——主帐号

        apiparamsMap.put("format", format);//添加请求参数——返回格式

        apiparamsMap.put("method", "edbCustomerClassCreate");//添加请求参数——接口名称

        apiparamsMap.put("slencry", "0");//添加请求参数——返回结果是否加密（0，为不加密 ，1.加密）

        apiparamsMap.put("ip", "192.168.60.80");//添加请求参数——IP地址

        apiparamsMap.put("appkey", appkey);//添加请求参数——appkey

        apiparamsMap.put("appscret", secret);//添加请求参数——appscret

        apiparamsMap.put("token", token);//添加请求参数——token

        apiparamsMap.put("v", "2.0");//添加请求参数——版本号（目前只提供2.0版本）

        apiparamsMap.put("fields", "result");//添加请求参数——返回数据列

        String timestamp = new SimpleDateFormat("yyyyMMddHHmm")
                .format(new Date());

        apiparamsMap.put("timestamp", timestamp);//添加请求参数——时间戳
        apiparamsMap.put("ptype_name", "三创农民");//父级类型
        apiparamsMap.put("type_name", "测试一yyy");//子类型

        //获取数字签名
        String sign = Util.md5Signature(apiparamsMap, appkey);

        apiparamsMap.put("sign", sign);

        StringBuilder param = new StringBuilder();

        for (Iterator<Map.Entry<String, String>> it = apiparamsMap.entrySet()
                .iterator(); it.hasNext(); ) {
            Map.Entry<String, String> e = it.next();
            if (e.getKey() != "appscret" && e.getKey() != "token") {
                if (e.getKey() == "ptype_name" || e.getKey() == "type_name") {
                    try {
                        param.append("&").append(e.getKey()).append("=").append(Util.encodeUri(e.getValue()));
                    } catch (UnsupportedEncodingException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } else {
                    param.append("&").append(e.getKey()).append("=").append(e.getValue());
                }
            }
        }


        String PostData = "";
        PostData = param.toString().substring(1);
        System.out.println(testUrl + "?" + PostData);
        String result = "";
        result = Util.getResult(testUrl, PostData);
        System.out.println(result);
    }

    //获取客户信息
    public static void edbCustomerGet() {

        TreeMap<String, String> apiparamsMap = new TreeMap<String, String>();

        apiparamsMap.put("dbhost", dbhost);//添加请求参数——主帐号

        apiparamsMap.put("format", format);//添加请求参数——返回格式

        apiparamsMap.put("method", "edbCustomerGet");//添加请求参数——接口名称

        apiparamsMap.put("slencry", "0");//添加请求参数——返回结果是否加密（0，为不加密 ，1.加密）

        apiparamsMap.put("ip", "192.168.60.80");//添加请求参数——IP地址

        apiparamsMap.put("appkey", appkey);//添加请求参数——appkey

        apiparamsMap.put("appscret", secret);//添加请求参数——appscret

        apiparamsMap.put("token", token);//添加请求参数——token

        apiparamsMap.put("v", "2.0");//添加请求参数——版本号（目前只提供2.0版本）

        apiparamsMap.put("fields", "msn,alipay_account,shop,mobile,sales,post,total_score,plan_balance,customerlevel_no,customer_name,email,customer_type,costomer_catalog,begin_time,end_time,consignee,consignee_address,telephone,province,actualremind_date,birthday,fp_date,lp_date,transaction_num,transaction_hz,total,shop_name,sex,pay_score,lsm_date,distributor_type,city,county,character,zl_degree,wangwang,qq,belong_distributor,from_shop,is_blanklist,is_potentialcustomer,totolplan_money,is_specialcustomer");

        String timestamp = new SimpleDateFormat("yyyyMMddHHmm")
                .format(new Date());

        apiparamsMap.put("timestamp", timestamp);//添加请求参数——时间戳


        apiparamsMap.put("shop_id", "27");//店铺ID
        apiparamsMap.put("wangwang_id", "chouchoubuchou");//旺旺号

        apiparamsMap.put("begin_time", "2002-07-22");//开始时间
        apiparamsMap.put("end_time", "2013-09-23");//结束时间
        apiparamsMap.put("date_type", "首次购物时间");//时间类型
        apiparamsMap.put("page_no", "1");//分页
        apiparamsMap.put("page_size", "50");//页大小
        //获取数字签名
        String sign = Util.md5Signature(apiparamsMap, appkey);

        apiparamsMap.put("sign", sign);

        StringBuilder param = new StringBuilder();

        for (Iterator<Map.Entry<String, String>> it = apiparamsMap.entrySet()
                .iterator(); it.hasNext(); ) {
            Map.Entry<String, String> e = it.next();
            if (e.getKey() != "appscret" && e.getKey() != "token") {
                if (e.getKey() == "shop_id" || e.getKey() == "wangwang_id" || e.getKey() == "date_type") {
                    try {
                        param.append("&").append(e.getKey()).append("=").append(Util.encodeUri(e.getValue()));
                    } catch (UnsupportedEncodingException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } else {
                    param.append("&").append(e.getKey()).append("=").append(e.getValue());
                }
            }
        }


        String PostData = "";
        PostData = param.toString().substring(1);
        System.out.println(testUrl + "?" + PostData);
        String result = "";
        result = Util.getResult(testUrl, PostData);
        System.out.println(result);
    }

    //修改客户信息
    public static void edbCustomerUpdate() {

        TreeMap<String, String> apiparamsMap = new TreeMap<String, String>();

        apiparamsMap.put("dbhost", dbhost);//添加请求参数——主帐号

        apiparamsMap.put("format", format);//添加请求参数——返回格式

        apiparamsMap.put("method", "edbCustomerUpdate");//添加请求参数——接口名称

        apiparamsMap.put("slencry", "0");//添加请求参数——返回结果是否加密（0，为不加密 ，1.加密）

        apiparamsMap.put("ip", "192.168.60.80");//添加请求参数——IP地址

        apiparamsMap.put("appkey", appkey);//添加请求参数——appkey

        apiparamsMap.put("appscret", secret);//添加请求参数——appscret

        apiparamsMap.put("token", token);//添加请求参数——token

        apiparamsMap.put("v", "2.0");//添加请求参数——版本号（目前只提供2.0版本）

        apiparamsMap.put("fields", "result");

        String timestamp = new SimpleDateFormat("yyyyMMddHHmm")
                .format(new Date());

        apiparamsMap.put("timestamp", timestamp);//添加请求参数——时间戳
        /*shop_name,店铺名称
         * wangwang 旺旺号
		 * level 用户级别，传入名称
		 * customer_type 客户分组  可以传多个，，以,号分隔
		 * buy_num 交易 次数
		 * order_money 订货总额
		 * custom_1  星座   ----value_1 星座名称
		 * custom_2 生日类型   ----value_2生日名称
		 * custom_3  情感状态   ----value_3 情感名称
		 * custom_4  血型   ----value_4 血型名称
		 * custom_5 生肖   ----value_5 生肖名称
		 */
        String postdate = "<order>";
        for (int i = 0; i < 50; i++) {
            postdate += "<orderInfo><shop_name>三创农民</shop_name><wangwang>chouchoubuchou</wangwang>";
            postdate += "<level>普通会员</level><customer_type>测试分组</customer_type><buy_num>1</buy_num><order_money>99</order_money>";
            postdate += "<custom_1>星座</custom_1><value_1>双子座</value_1><custom_2>生日类型</custom_2><value_2>农历</value_2>";
            postdate += "<custom_3>情感状态</custom_3><value_3>单身</value_3><custom_4>血型</custom_4><value_4>A型</value_4>";
            postdate += "<custom_5>生肖</custom_5><value_5>丑牛</value_5><custom_6></custom_6><value_6></value_6>";
            postdate += "<custom_7></custom_7><value_7></value_7><custom_8></custom_8><value_8></value_8>";
            postdate += "<custom_9></custom_9><value_9></value_9>";
            postdate += "</orderInfo>";
        }
        postdate += "</order>";
        java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String st = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date());
        apiparamsMap.put("xmlValues", postdate);
        //获取数字签名
        String sign = Util.md5Signature(apiparamsMap, appkey);

        apiparamsMap.put("sign", sign);

        StringBuilder param = new StringBuilder();

        for (Iterator<Map.Entry<String, String>> it = apiparamsMap.entrySet()
                .iterator(); it.hasNext(); ) {
            Map.Entry<String, String> e = it.next();
            if (e.getKey() != "appscret" && e.getKey() != "token") {
                if (e.getKey() == "xmlValues") {
                    try {
                        param.append("&").append(e.getKey()).append("=").append(Util.encodeUri(e.getValue()));
                    } catch (UnsupportedEncodingException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } else {
                    param.append("&").append(e.getKey()).append("=").append(e.getValue());
                }
            }
        }
        String PostData = "";
        PostData = param.toString().substring(1);
        System.out.println(testUrl + "?" + PostData);
        String result = "";
        result = Util.getResult(testUrl, PostData);
        System.out.println(result);

        String et = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date());
        try {
            java.util.Date begin = df.parse(st);
            java.util.Date end = df.parse(et);
            System.out.println(st);
            System.out.println(et);
            long between = (end.getTime() - begin.getTime()) / 1000;
            System.out.println(between);
        } catch (java.text.ParseException e) {
            System.err.println("格式不正确");
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        //test.edbCustomerUpdate();
//		test.edbCustomerGet();
        //test.edbCustomerClassCreate();
        //test.edbCustomerClassUpdate();
        // TODO Auto-generated method stub
//		Set set = new HashSet<String>();

    }
    }