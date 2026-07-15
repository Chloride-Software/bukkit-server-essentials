package com.chloride.utilities.misc.ttl;

public interface TtlHandler<E> {

    void onExpire(E element);

    long getTimestamp(E element);

}
