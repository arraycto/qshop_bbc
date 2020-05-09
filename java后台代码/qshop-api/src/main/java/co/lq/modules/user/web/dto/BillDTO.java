package co.lq.modules.user.web.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @ClassName BillDTO
 * @Author billy
 * @Date 2019/11/12
 **/
@Data
public class BillDTO {
    private String            time;
    @JsonIgnore
    private String            ids;
    private List<UserBillDTO> list;
}
