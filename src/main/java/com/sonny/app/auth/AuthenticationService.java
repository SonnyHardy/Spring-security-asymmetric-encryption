package com.sonny.app.auth;

import com.sonny.app.auth.request.AuthenticationRequest;
import com.sonny.app.auth.request.RefreshRequest;
import com.sonny.app.auth.request.RegistrationRequest;
import com.sonny.app.auth.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);

    void register(RegistrationRequest request);

    AuthenticationResponse refreshToken(RefreshRequest request);
}
