package com.tracetech.minioserver.common.response;

/**
 * 错误码枚举
 *
 * @author yangshaowei@tracetech.cn
 * @since 2022-5-13 17:35:20
 */
public enum ResponseEnum {
    ResultSuccess(0, "success"),
    ResultFailed(-1, "failed"),
    ResultOutRange(-2, "out of range"),
    ResultFileNotExists(-3, "file not exists"),
    ResultNoPermission(-4, "no permission"),
    ResultParamInvalid(-11, "param invalid"),
    ResultParamMissing(-12, "param missing"),
    ResultParamNil(-13, "param is nil"),
    ResultValueEmpty(-21, "value is empty"),
    ResultNotImplemented(-101, "target not implemented yet"),
    ResultNotAllowed(-102, "target not allowed"),
    ResultNotFound(-103, "target not found"),
    ResultNotNeed(-104, "not need do it"),
    ResultInvalidAccessToken(20000, "invalid access token"),
    ;

    private Integer code;

    private String desc;

    ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ResponseEnum getByCode(String code) {
        ResponseEnum[] values = ResponseEnum.values();
        for (ResponseEnum responseEnum : values) {
            if (responseEnum.code.equals(code)) {
                return responseEnum;
            }
        }
        return null;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
