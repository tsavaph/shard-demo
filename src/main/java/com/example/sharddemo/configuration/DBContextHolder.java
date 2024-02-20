package com.example.sharddemo.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DBContextHolder {

    private static final ThreadLocal<DBSourceEnum> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setCurrentDb(DBSourceEnum dbType) {
        CONTEXT_HOLDER.set(dbType);
    }

    public static DBSourceEnum getCurrentDb() {
        return CONTEXT_HOLDER.get();
    }

    public static void clear() {
        CONTEXT_HOLDER.remove();
    }

}
