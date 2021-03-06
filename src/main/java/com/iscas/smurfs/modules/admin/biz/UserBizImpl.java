package com.iscas.smurfs.modules.admin.biz;

import com.iscas.smurfs.common.constant.Constant;

import com.iscas.smurfs.common.utils.BCryptUtils;
import com.iscas.smurfs.modules.admin.entity.po.User;
import com.iscas.smurfs.modules.admin.mapper.UserMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description:
 * Userbiz
 *
 * @author 123
 * @date 2018/8/31
 */

@Service
@Transactional
@CacheConfig(cacheNames = "user",keyGenerator = "cacheKeyGenerator")
//@Cacheable(value = "user",keyGenerator = "cacheKeyGenerator")
public class UserBizImpl extends BaseBizImpl<UserMapper, User> implements IUserBiz{


    @Cacheable(value = "user")
    @Override
    public User getUserByUsername(String username){
        System.out.println("缓存击穿了哦");
        User user = new User();
        user.setUsername(username);
        return super.mapper.selectOne(user);
    }

    @CachePut
    public int insert(User user){
        //String password = new BCryptPasswordEncoder(Constant.PW_ENCORDER_SALT).encode(user.getPassword());
        String password = BCryptUtils.hashpw(user.getPassword(), BCryptUtils.gensalt(Constant.PW_ENCORDER_SALT));
        user.setPassword(password);
        return mapper.insert(user);
    }
}
