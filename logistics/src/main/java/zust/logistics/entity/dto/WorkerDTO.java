package zust.logistics.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("维修工")
public class WorkerDTO {
    @ApiModelProperty("工号")
    private String code;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("电话")
    private String tel;
    @ApiModelProperty("照片")
    private String photo;
}
