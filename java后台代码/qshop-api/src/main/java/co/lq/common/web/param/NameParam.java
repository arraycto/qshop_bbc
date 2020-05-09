package co.lq.common.web.param;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("名称参数")
public class NameParam implements Serializable {
    private static final long serialVersionUID = -3710501706034574149L;

    @ApiModelProperty("名称")
    private String            name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NameParam{" + "name='" + name + '\'' + '}';
    }
}
