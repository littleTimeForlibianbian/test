package com.example.lixc.enums;


/***
 * 基础枚举类
 *
 * @author ssr
 */
public interface BaseEnum {
    int getCode();

    String getName();

    static <T extends BaseEnum> T getByCode(Class<T> baseEnum, Integer code) {
        if (code == null || baseEnum == null || !baseEnum.isEnum()) {
            return null;
        }

        T[] enums = baseEnum.getEnumConstants();
        if (enums == null || enums.length <= 0) {
            return null;
        }

        for (T e : enums) {
            BaseEnum valuedEnum = (BaseEnum) e;
            if (valuedEnum.getCode() == code) {
                return e;
            }
        }

        return null;
    }

    static <T extends BaseEnum> T getByName(Class<T> baseEnum, String name) {
        if (name == null || name.isEmpty() || baseEnum == null || !baseEnum.isEnum()) {
            return null;
        }

        T[] enums = baseEnum.getEnumConstants();
        if (enums == null || enums.length <= 0) {
            return null;
        }

        for (T e : enums) {
            BaseEnum valuedEnum = (BaseEnum) e;
            if (valuedEnum.getName().equals(name)) {
                return e;
            }
        }

        return null;
    }
}
