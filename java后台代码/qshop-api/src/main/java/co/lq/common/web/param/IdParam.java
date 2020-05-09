package co.lq.common.web.param;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("ID参数")
public class IdParam implements Serializable {
    private static final long serialVersionUID = -5353973980674510450L;

    @NotBlank(message = "ID不能为空")
    private String            id;
}
