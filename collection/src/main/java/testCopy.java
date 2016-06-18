import java.io.*;
import java.util.ArrayList;
import java.util.List;

/** */

/**
 * <p>标题: </p>
 * <p>功能描述: <br>
 * <p/>
 * </p>
 * <p>版权: Copyright (c) 2007</p>
 * <p>公司: </p>
 *
 * @author fbysss
 * @version 1.0
 *          <p/>
 *          </p>
 *          <p>修改记录：</p>
 *          创建时间：2007-7-12 13:34:56
 *          类说明
 */
public class testCopy {

    //深拷贝1：递归方法
    public void copy(List src, List dest) {
        for (int i = 0; i < src.size(); i++) {
            Object obj = src.get(i);
            if (obj instanceof List) {
                dest.add(new ArrayList());
                copy((List) obj, (List) ((List) dest).get(i));
            } else {
                dest.add(obj);
            }
        }

    }


    //深拷贝2:序列化|反序列化方法
    public List copyBySerialize(List src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        List dest = (List) in.readObject();
        return dest;
    }

    //浅拷贝
    public void copyByAdd(List src, List dest) {
        for (Object obj : src) {//jdk 1.5 以上版本
            dest.add(obj);
        }
    }

    //添加引用
    public void evalByAdd(List src, List dest) {
        dest.add(src);
    }

    //直接引用
    public List evalByRef(List src) {
        return src;
    }
    /** */
    /**
     * @param args
     */
    public static void main(String[] args) {
        List srcList = new ArrayList();
        List srcSubList1 = new ArrayList();
        srcSubList1.add("subItem1-1");
        srcSubList1.add("subItem1-2");
        srcSubList1.add("subItem1-3");
        List srcSubList2 = new ArrayList();
        srcSubList2.add("subItem2-1");
        srcSubList2.add("subItem2-2");
        srcSubList2.add("subItem2-3");
        srcList.add(srcSubList1);
        srcList.add(srcSubList2);

        List destList = new ArrayList();
        testCopy dc = new testCopy();
        /** *//***********test#1*******************/
//         dc.copy(srcList,destList);

        /** *//***********test#2*******************/
         /**//*
         try {
             destList = dc.copyBySerialize(srcList);
         } catch (IOException e) {
             e.printStackTrace();
         } catch (ClassNotFoundException e) {            
             e.printStackTrace();
         }
         */
        /** *//***********test#3*******************/
//         dc.copyByAdd(srcList,destList);
//         ((List)srcList.get(0)).remove(0);
        /** *//***********test#4*******************/
         /**//*
         destList = dc.evalByRef(srcList);        
         */
        srcList.remove(1);
        dc.printList(destList);


    }

    private void printList(List destList) {
        for (Object obj : destList) {
            if (obj instanceof List) {
                List listObj = (List) obj;
                printList((List) listObj);
            } else {
                System.out.println(obj.toString());
            }
        }

    }

}

