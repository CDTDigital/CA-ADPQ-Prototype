package com.intimetec.crns.core.service.currentuser;

import com.intimetec.crns.core.models.CurrentUser;

/**
 * @author In Time Tec
 */
public interface CurrentUserService {

    boolean canAccessUser(CurrentUser currentUser, Long userId);
    
    boolean canAccessUserByRoles(CurrentUser currentUser, String[] roles);

}
