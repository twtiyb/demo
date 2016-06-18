package objectToBean.bean;

import java.util.List;

public class Respons {
    private String seOrderNo;//原始订单
    private String tid;//正式订单
    private String ClassName;
    private List<Object> objList;

    public String getSeOrderNo() {
        return seOrderNo;
    }

    public void setSeOrderNo(String seOrderNo) {
        this.seOrderNo = seOrderNo;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public List<Object> getObjList() {
        return objList;
    }

    public void setObjList(List<Object> objList) {
        this.objList = objList;
    }
}
