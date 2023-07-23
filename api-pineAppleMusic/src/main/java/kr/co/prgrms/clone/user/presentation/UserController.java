package kr.co.prgrms.clone.user.presentation;

import kr.co.prgrms.clone.user.application.UserService;
import kr.co.prgrms.clone.user.application.dto.request.UserLoginRequest;
import kr.co.prgrms.clone.user.application.dto.request.UserSignUpRequest;
import kr.co.prgrms.clone.user.application.dto.response.UserLoginResponse;
import kr.co.prgrms.clone.user.application.dto.response.UserSignUpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponse> signUpUser(@Validated @RequestBody
                                                         UserSignUpRequest request) {
        UserSignUpResponse response = userService.join(request);

        return ResponseEntity
                .status(CREATED)
                .body(response);
    }

    @PostMapping("/signup/duplicate")
    public ResponseEntity<Boolean> checkDuplicateEmail(@Validated @RequestBody
                                                       String email) {
        Boolean result = userService.checkDuplicateEmail(email);

        return ResponseEntity
                .ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> loginUser(@Validated @RequestBody
                                                       UserLoginRequest request) {
        UserLoginResponse response = userService.login(request);

        return ResponseEntity
                .ok(response);
    }
}
