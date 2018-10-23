package com.iscas.smurfs.modules.admin.biz;

import com.iscas.smurfs.common.auth.JwtHelper;
import com.iscas.smurfs.common.auth.entity.dto.UserLoginDto;
import com.iscas.smurfs.common.config.KeyConfiguration;
import com.iscas.smurfs.common.exception.UserInvalidException;
import com.iscas.smurfs.common.utils.BCryptUtils;
import com.iscas.smurfs.modules.admin.entity.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * description:
 *
 * @author lee
 * @date 2018/9/2
 */
@Service
public class AuthServiceImpl implements IAuthService {
    @Value("${jwt.expire}")
    private int expire;

    @Autowired
    KeyConfiguration keyConfiguration;
    @Autowired
    IUserBiz userBiz;
    //private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @Override
    public String login(UserLoginDto authenticationRequest) throws Exception {
        User user = validate(authenticationRequest.getUsername(),authenticationRequest.getPassword());
        if(user!=null){
            return JwtHelper.generateToken(user.getId().toString(),user.getUsername(),keyConfiguration.getUserPriKey(),expire);
        }
        throw new UserInvalidException("用户不存在或账户密码错误!");
    }

    @Override
    public User validate(String username, String password) {
        User user = userBiz.getUserByUsername(username);
//        if (encoder.matches(password, user.getPassword())) {
//            return user;
//        }
        if(BCryptUtils.checkpw(password, user.getPassword()))
            return user;
        return null;
    }
}
