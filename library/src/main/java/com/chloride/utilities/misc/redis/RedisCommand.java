package com.chloride.utilities.misc.redis;

public interface RedisCommand<T> {

    void execute(T t);
}
