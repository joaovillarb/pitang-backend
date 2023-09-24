package jfvb.com.pitangbackend.infrastructure.utils;

import jfvb.com.pitangbackend.infrastructure.exception.UserNotLoggedInException;
import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static AccountUser getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof AccountUser accountUser) {
            return accountUser;
        }

        throw new UserNotLoggedInException();
    }
}
