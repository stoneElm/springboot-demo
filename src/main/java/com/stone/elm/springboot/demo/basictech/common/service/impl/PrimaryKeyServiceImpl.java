package com.stone.elm.springboot.demo.basictech.common.service.impl;

import com.stone.elm.springboot.demo.basictech.common.constant.NumberConstant;
import com.stone.elm.springboot.demo.basictech.common.constant.PrimaryKeySymbolConstant;
import com.stone.elm.springboot.demo.basictech.common.constant.RedisKeyConstant;
import com.stone.elm.springboot.demo.basictech.common.constant.SymbolConstant;
import com.stone.elm.springboot.demo.basictech.common.service.IPrimaryKeyService;
import com.stone.elm.springboot.demo.basictech.common.utils.DateUtils;
import com.stone.elm.springboot.demo.basictech.common.utils.RedisCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class PrimaryKeyServiceImpl implements IPrimaryKeyService {

    @Value("${network.service.point.code}")
    private String netPointCode;

    @Autowired
    private RedisCache redisCache;


    @Override
    public Long getPrimaryKey() {
        return getPrimaryKey(PrimaryKeySymbolConstant.TACTICS_COMMON);
    }

    @Override
    public Long getPrimaryKey(String symbol) {
        if (StringUtils.isBlank(symbol)) {
            symbol = PrimaryKeySymbolConstant.TACTICS_COMMON;
        }

        String sixDigitDay = DateUtils.getCurrentSixDigitDay();

        // 得到 1424010100000001
        Long result = Long.parseLong(netPointCode + sixDigitDay) * NumberConstant.LONG_ONE_HUNDRED_MILLION;

        String redisKey = RedisKeyConstant.PRIMARY_KEY + SymbolConstant.BAR + symbol + SymbolConstant.BAR + sixDigitDay;

        Long redisPK =  redisCache.getCacheObject(redisKey);

        if (Objects.isNull(redisPK)) {
            result++;
            redisCache.setCacheObject(redisKey, result, NumberConstant.ONE, TimeUnit.DAYS);
            return result;
        }

        redisPK++;
        redisCache.setCacheObject(redisKey, redisPK, NumberConstant.ONE, TimeUnit.DAYS);
        return redisPK;
    }
}
