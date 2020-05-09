package co.lq.exception.handler;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author billy
 * @since 2019-10-02
 */
@Data
class ApiErr {

    private Integer       status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private String        msg;

    private ApiErr() {
        timestamp = LocalDateTime.now();
    }

    public ApiErr(Integer status, String message) {
        this();
        this.status = status;
        this.msg = message;
    }
}
