package com.iscas.smurfs.modules.admin.biz;


import com.iscas.smurfs.modules.admin.entity.po.User;

public interface IUserBiz extends IBaseBiz<User> {

    User getUserByUsername(String username);
}
