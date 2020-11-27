package com.sunlu.chengxin.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunlu.chengxin.entity.ResultEntity;
import java.util.List;

public class Utils {
    public static ResultEntity resultEntity = new ResultEntity();
    //封装json返回
    //200200操作成功 200201操作失败 200204参数错误
    public static ResultEntity createJson(String code, String msg, Object data){
        resultEntity.setCode(code);
        resultEntity.setMsg(msg);
        resultEntity.setData(data);
        return resultEntity;
    }

    /**
     * 判断对象为null 和 "" 和 集合长度为0
     */
    public static boolean isEmpty(Object obj)
    {
        if (obj == null)
        {
            return true;
        }
        if ((obj instanceof List))
        {
            return ((List) obj).size() == 0;
        }
        if ((obj instanceof String))
        {
            return ((String) obj).trim().equals("");
        }
        return false;
    }

    /**
     * 判断对象不为null 和 ""
     *
     * @param obj
     *            对象名
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Object obj)
    {
        return !isEmpty(obj);
    }


}
