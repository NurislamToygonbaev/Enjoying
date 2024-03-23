package enjoying.service;

import enjoying.dto.request.SignInRequest;
import enjoying.dto.response.SignResponse;
import enjoying.dto.request.SignUpRequest;

public interface UserService {
    SignResponse signUpUser(SignUpRequest signUpReques);

    SignResponse signIn(SignInRequest signInRequest);
}
