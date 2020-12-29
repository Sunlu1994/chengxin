package com.sunlu.chengxin;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Test {
    public static List<Map> list = new ArrayList<>();
    public static String[] classify_name={"塔式起重机","施工升降机","履带式起重机","轮式装载机","电动叉车","轮式挖掘机","履带式挖掘机","工业泵及工业系统","平地机","起重机械","装载机械","挖掘机械","混凝土机械","道路机械","轮式挖掘机_son"};
    public static Integer[] parent_classify_id={10,10,10,11,11,12,12,13,14,0,0,0,0,0,6};
    public static void main(String[] args){
        String timestamp = "2018-12-12 16:29:23";

        //（新）将字符串转换为标准的ISO-8601的时间格式
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dt1 = LocalDateTime.parse(timestamp,formatter);
            ZoneOffset offset = ZoneOffset.of("+08:00");
            OffsetDateTime date = OffsetDateTime.of(dt1,offset);

            DateTimeFormatter formatter2=DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
            timestamp=date.format(formatter2);
            System.out.println(timestamp);
        }
        catch (DateTimeParseException ex) {
            ex.printStackTrace();
        }
//结果：2018-12-12T16:29:23+08:00

//        for (int i=0;i<classify_name.length;i++){
//            Classify classify = new Classify();
//            classify.id = i+1;
//            classify.classifyName = classify_name[i];
//            classify.parent_classify_id = parent_classify_id[i];
//            cates.add(classify);
//        }
//
//        List<Classify> cList = tree( cates , 0  ,list);
//        System.out.println(cList);
//
//        System.out.println(JSON.toJSONString(list));
    }
    private static List<Classify> tmpCates = new ArrayList<Classify>();
    public static List<Classify> cates = new ArrayList<>();

    public static Map<String,Object> treeData(Classify cs){
        List<Map<String,Object>> listTemp = new ArrayList<>();
        Map<String,Object> mapTemp = new HashMap<>();
        mapTemp.put("id",cs.getId());
        mapTemp.put("parentId",cs.getParent_classify_id());
        mapTemp.put("name",cs.getClassifyName());
        mapTemp.put("list",listTemp);
        return mapTemp;
    }
    //封装的递归无限分类结果查询
    public static List<Classify> tree( List<Classify> cs , Integer pid ,List<Map> list){
        for (Classify c: cs) {
            if( c.getParent_classify_id() == pid ){
                //封装返回结果
                Map m = treeData(c);
                list.add(m);
                tmpCates.add(c);
                tree( cs , c.getId(),((List)m.get("list")));
            }
        }
        return tmpCates;
    }

}
@Data
class Classify{
    public int id;
    public String classifyName;
    public Integer parent_classify_id;
    public Integer level;
}