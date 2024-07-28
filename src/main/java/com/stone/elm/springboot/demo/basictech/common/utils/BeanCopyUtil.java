package com.stone.elm.springboot.demo.basictech.common.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class BeanCopyUtil {

    private BeanCopyUtil() {
    }

    public static void copy(Object srcObj, Object destObj) {
        if (null != srcObj && null != destObj) {
            BeanUtils.copyProperties(srcObj, destObj);
        }
    }

    public static void copyIgnoreNullPropValues(Object srcObj, Object destObj) throws Exception {
        if (null != srcObj && null != destObj) {
            Field[] fields = getAllFields(srcObj);
            List<String> ignores = new ArrayList();
            Field[] var1 = fields;
            int var2 = fields.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                Field field = var1[var3];
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(srcObj);
                if (value == null) {
                    ignores.add(fieldName);
                }
            }

            String[] a = new String[ignores.size()];
            BeanUtils.copyProperties(srcObj, destObj, (String[])ignores.toArray(a));
        }
    }

    public static void copyList(List srcList, List destList, Class<?> destClazz) {
        if (CollectionUtils.isNotEmpty(srcList) && destList != null ) {
            try {
                Object deskObj = null;
                Iterator var1 = srcList.iterator();

                while (var1.hasNext()) {
                    Object obj = var1.next();
                    deskObj = destClazz.newInstance();
                    BeanUtils.copyProperties(obj, deskObj);
                    destList.add(deskObj);
                }
            } catch (Exception var2) {
                var2.printStackTrace();
            }
        }
    }

    public static <T> List<?> copyList(List srcList, Class<?> destClazz) {
        return JsonUtil.convertObjectToList(JsonUtil.convertObjectToJson(srcList), destClazz);
    }

    public static Field[] getAllFields(Object object) {
        Class<?> clazz = object.getClass();

        ArrayList fieldList;
        for (fieldList = new ArrayList(); clazz != null; clazz.getSuperclass()) {
            fieldList.addAll(new ArrayList(Arrays.asList(clazz.getDeclaredFields())));
        }

        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }
}
