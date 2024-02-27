package com.stone.elm.springboot.demo.basictech.common.utils;

import com.google.common.collect.Lists;
import com.stone.elm.springboot.demo.basictech.common.constant.BaseConstant;
import com.stone.elm.springboot.demo.basictech.common.exception.SystemException;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseConstant;

import java.util.List;

public class ConvertUtil {

    private ConvertUtil() {
    }

    public static <T, E> List<E> convertListFromListIgnoreNull(List<T> ao, Class<E> e) {
        List<E> list = Lists.newArrayList();
        try {
            BeanCopyUtil.copyList(ao, list, e);
        } catch (Exception ex) {
            throw new SystemException(BaseConstant.BEAN_COPY_ERROR, ResponseConstant.FAIL);
        }
        return list;
    }
}
