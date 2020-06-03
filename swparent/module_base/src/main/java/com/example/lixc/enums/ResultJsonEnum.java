package com.example.lixc.enums;

/**
 * 数据中心类型
 * 枚举类
 * @author wgx52
 *
 */
public enum ResultJsonEnum implements BaseEnum{
	SUCCESS_CODE(200,"成功"),
	ERROR_CODE(400,"失败"),
	LOGINOUT_CODE(440,"温馨提示：请使用对应的账号登录");
	private int code;
	private String name;
	
	private ResultJsonEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getName() {
		return name;
	}

}
