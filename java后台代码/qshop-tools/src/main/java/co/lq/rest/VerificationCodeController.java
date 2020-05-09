package co.lq.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.lq.domain.VerificationCode;
import co.lq.domain.vo.EmailVo;
import co.lq.service.EmailService;
import co.lq.service.VerificationCodeService;
import co.lq.utils.QshopConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author billy
 * @date 2018-12-26
 */
@RestController
@RequestMapping("/api/code")
@Api(tags = "工具：验证码管理")
public class VerificationCodeController {

    private final VerificationCodeService verificationCodeService;

    private final EmailService            emailService;

    public VerificationCodeController(VerificationCodeService verificationCodeService, EmailService emailService) {
        this.verificationCodeService = verificationCodeService;
        this.emailService = emailService;
    }

    @PostMapping(value = "/resetEmail")
    @ApiOperation("重置邮箱，发送验证码")
    public ResponseEntity<Object> resetEmail(@RequestBody VerificationCode code) throws Exception {
        code.setScenes(QshopConstant.RESET_MAIL);
        EmailVo emailVo = verificationCodeService.sendEmail(code);
        emailService.send(emailVo, emailService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/email/resetPass")
    @ApiOperation("重置密码，发送验证码")
    public ResponseEntity<Object> resetPass(@RequestParam String email) throws Exception {
        VerificationCode code = new VerificationCode();
        code.setType("email");
        code.setValue(email);
        code.setScenes(QshopConstant.RESET_MAIL);
        EmailVo emailVo = verificationCodeService.sendEmail(code);
        emailService.send(emailVo, emailService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/validated")
    @ApiOperation("验证码验证")
    public ResponseEntity<Object> validated(VerificationCode code) {
        verificationCodeService.validated(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
