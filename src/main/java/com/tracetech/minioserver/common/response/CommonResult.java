package com.tracetech.minioserver.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * 公共返回类
 *
 * @author yangshaowei@tracetech.cn
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "cn.tracetech.envprotection.common.utils.CommonResult", description = "公共返回类")
public class CommonResult<T> implements Serializable {

//    /**
//     * 成功与否
//     */
//    @ApiModelProperty(value = "成功与否", position = 0)
//    private Boolean ok;

    /**
     * 错误码
     */
    @ApiModelProperty(value = "信息码:0 成功，-1 失败，20000 token不可用/失效", position = 1)
    private Integer error;

    /**
     * 错误提示
     */
    @ApiModelProperty(value = "正确/错误提示", position = 2)
    private String info;

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据", position = 3)
    private T data;

    /**
     * 将传入的result对象，转换成另外一个泛型结果
     * <p>
     * 因为A方法返回的CommonResult对象，不满足调用其的B方法的返回，所以需要继续转换。
     *
     * @param result 传入的result对象
     * @param <T>    返回的泛型
     * @return 新的CommonResult对象
     */
    public static <T> CommonResult<T> error(CommonResult<?> result) {
        return error(result.getError(), result.getInfo());
    }

    public static <T> CommonResult<T> error(Integer code, String info) {
        Assert.isTrue(!ResponseEnum.ResultSuccess.getCode().equals(code), "code必须是错误的");
        CommonResult<T> commonResult = new CommonResult<>();
//        commonResult.ok = false;
        commonResult.error = code;
        commonResult.info = info;
        return commonResult;
    }

    public static <T> CommonResult<T> error(String info) {
        CommonResult<T> commonResult = new CommonResult<>();
//        commonResult.ok = false;
        commonResult.error = ResponseEnum.ResultFailed.getCode();
        commonResult.info = info;
        return commonResult;
    }

    public static <T> CommonResult<T> success(String info, T data) {
        CommonResult<T> result = new CommonResult<>();
//        result.ok = true;
        result.error = ResponseEnum.ResultSuccess.getCode();
        result.data = data;
        result.info = info;
        return result;
    }

    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
//        result.ok = true;
        result.error = ResponseEnum.ResultSuccess.getCode();
        result.data = data;
        result.info = ResponseEnum.ResultSuccess.getDesc();
        return result;
    }

    /**
     * 忽略避免jackson序列化给前端
     * 方便判断是否成功
     *
     * @return
     */
//    @JsonIgnore
//    public boolean isOk() {
//        //判断是否成功
//        return ZjzwfwResponseEnum.ResultSuccess.getCode().equals(error);
//    }

//    @JsonIgnore
//    public boolean isError() {
//        return !isOk();
//    }
    @Override
    public String toString() {
        return "CommonResult{" +
                "error=" + error +
                ", info='" + info + '\'' +
                ", data=" + data +
                '}';
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

//    public Boolean getOk() {
//        return ok;
//    }

//    public void setOk(Boolean ok) {
//        this.ok = ok;
//    }
}