package zust.logistics.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("维修单")
public class RepairDTO {
    @ApiModelProperty("寝室号")
    private String room;
    @ApiModelProperty("备注")
    private String note;
}
