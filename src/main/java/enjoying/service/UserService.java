package enjoying.service;

import enjoying.dto.request.AddMoneyRequest;
import enjoying.dto.request.SignInRequest;
import enjoying.dto.request.UpdateRequest;
import enjoying.dto.response.*;
import enjoying.dto.request.SignUpRequest;

import java.security.Principal;
import java.util.List;

public interface UserService {
    SignResponse signUpUser(SignUpRequest signUpReques);

    SignResponse signIn(SignInRequest signInRequest);

    List<FindAllResponse> findAll();

    SimpleResponse deleteUser(Long userId);

    SimpleResponse updateUser(UpdateRequest updateRequest);

    SimpleResponse addMoney(AddMoneyRequest addMoneyRequest);

    MyProfile myProfile();


}
