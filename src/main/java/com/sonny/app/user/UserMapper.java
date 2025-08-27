package com.sonny.app.user;

import com.sonny.app.user.request.ProfileUpdateRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public void mergeUserInfo(final User user, final ProfileUpdateRequest request) {
        if (StringUtils.isEmpty(request.getFirstName())
        && !user.getFirstName().equals(request.getFirstName())) {
            user.setFirstName(request.getFirstName());
        }

        if (StringUtils.isEmpty(request.getLastName())
                && !user.getLastName().equals(request.getLastName())) {
            user.setLastName(request.getLastName());
        }

        if (request.getDateOfBirth() != null
                && !user.getDateOfBirth().equals(request.getDateOfBirth())) {
            user.setDateOfBirth(request.getDateOfBirth());
        }
    }
}
