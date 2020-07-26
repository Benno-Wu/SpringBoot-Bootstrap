package zust.logistics.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("结果")
public class ResultUtil {
    @ApiModelProperty("状态码")
    private Integer code;
    @ApiModelProperty("信息")
    private String message;
    @ApiModelProperty("对象")
    private Object obj;

    public static ResultUtil success(String message, Object obj) {
        ResultUtil resultUtil = init(message, obj);
        resultUtil.code = 200;
        return resultUtil;
    }

    public static ResultUtil SFailure(String message, Object obj) {
        ResultUtil resultUtil = init(message, obj);
        resultUtil.code = 500;
        return resultUtil;
    }

    public static ResultUtil BFailure(String message, Object obj) {
        ResultUtil resultUtil = init(message, obj);
        resultUtil.code = 400;
        return resultUtil;
    }

    private static ResultUtil init(String message, Object obj) {
        ResultUtil resultUtil = new ResultUtil();
        resultUtil.message = message;
        resultUtil.obj = obj;
        return resultUtil;
    }
}