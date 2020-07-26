package zust.logistics.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("学生")
public class StudentDTO {
    @ApiModelProperty("学号")
    private String stuID;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("电话")
    private String tel;
    @ApiModelProperty("寝室号")
    private String room;
    @ApiModelProperty("照片")
    private String photo;
}
