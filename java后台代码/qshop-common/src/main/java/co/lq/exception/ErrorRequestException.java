package co.lq.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * @author billy
 * @date 2019-11-11 统一异常处理
 */
@Getter
public class ErrorRequestException extends RuntimeException {

    private Integer status = BAD_REQUEST.value();

    public ErrorRequestException(String msg) {
        super(msg);
    }

    public ErrorRequestException(HttpStatus status, String msg) {
        super(msg);
        this.status = status.value();
    }
}
