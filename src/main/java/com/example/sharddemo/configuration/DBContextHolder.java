package com.example.sharddemo.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DBContextHolder {

    private static final ThreadLocal<DBTypeEnum> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setCurrentDb(DBTypeEnum dbType) {
        CONTEXT_HOLDER.set(dbType);
    }

    public static DBTypeEnum getCurrentDb() {
        return CONTEXT_HOLDER.get();
    }

    public static void clear() {
        CONTEXT_HOLDER.remove();
    }

}
