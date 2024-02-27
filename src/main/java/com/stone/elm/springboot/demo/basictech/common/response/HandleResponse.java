package com.stone.elm.springboot.demo.basictech.common.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.Nullable;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class HandleResponse extends MappingJackson2HttpMessageConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(HandleResponse.class);

    @Override
    protected void writeInternal(Object object, @Nullable Type type, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        handlerResult(object, type);

        MediaType contentType = outputMessage.getHeaders().getContentType();
        LOGGER.info("---------- 处理结束 --------------");
    }

    private void handlerResult(Object object, Type type) {
        // ParameterizedTypeImpl类表示一个参数化类型，即在编译时指定类型的泛型。例如，List<String>就是一个参数化类型
        if (type != null && type.getClass().getName().equals(ParameterizedTypeImpl.class.getName())) {
            ParameterizedType tp = (ParameterizedType) type;

            // 获取参数化类型的实际类型参数(T)  例如:map 获取到 K, V
            Type[] responseTypes = tp.getActualTypeArguments();

            Type rawType = tp.getRawType();

            // 如果返回 ResponseResult 类型
            if (responseTypes.length != 0 && rawType.getTypeName().equals(ResponseResult.class.getName())) {

                // 如果当前的泛型类型也是泛型
                if (responseTypes[0].getClass().getName().equals(ParameterizedTypeImpl.class.getName())) {
                    ParameterizedType responseDataTypes = (ParameterizedType) responseTypes[0];

                    LOGGER.info("---------- 当前处理为 LIST --------------");
                } else if (responseTypes[0].getClass().getName().equals(Class.class.getName())) {
                    // 当前泛型类型为 Class
                    LOGGER.info("---------- 当前处理为CLASS --------------");
                }
            } else {
                LOGGER.info("---------- 当前处理为 其他 --------------");
            }
        }
    }
}
