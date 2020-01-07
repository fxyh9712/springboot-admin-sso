package com.fxyh.sso.enums;

/**
 * @ClassName: MetasTypeEnum
 * @description: 项目类型枚举
 * @author: fengzhaoquan
 * @create: 2019-05-18 13:37
 * @Version 1.0
 **/
public enum DeleteStatusEnum {
    DELETE(0, "无效"),
    USEING(1, "有效");
    private Integer value;

    private String displayName;

    DeleteStatusEnum(Integer value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getDisplayName() {
        return this.displayName;
    }

}
