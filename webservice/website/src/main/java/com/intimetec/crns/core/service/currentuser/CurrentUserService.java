package com.intimetec.crns.core.service.currentuser;

import com.intimetec.crns.web.models.CurrentUser;

public interface CurrentUserService {

    boolean canAccessUser(CurrentUser currentUser, Long userId);

}
