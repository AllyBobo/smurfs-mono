package com.iscas.smurfs.modules.admin.biz;

import com.iscas.smurfs.common.auth.entity.dto.UserLoginDto;
import com.iscas.smurfs.modules.admin.entity.po.User;

/**
 * description:
 *
 * @author lee
 * @date 2018/10/15
 */
public interface IAuthService {

    String login(UserLoginDto authenticationRequest) throws Exception;
    User validate(String username, String password);
}
