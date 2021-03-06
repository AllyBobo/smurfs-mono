package com.iscas.smurfs.modules.admin.controller;

import com.iscas.smurfs.modules.admin.biz.IPermissionBiz;
import com.iscas.smurfs.modules.admin.biz.IUserBiz;
import com.iscas.smurfs.modules.admin.biz.IUserPermissionBiz;
import com.iscas.smurfs.modules.admin.entity.po.Permission;
import com.iscas.smurfs.modules.admin.entity.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * description:
 *
 * @author lee
 * @date 2018/9/2
 */

@RestController
@RequestMapping("db")
public class UserController {

    @Autowired
    IUserBiz userBiz;
    @Autowired
    IUserPermissionBiz userPermissionBiz;
    @Autowired
    IPermissionBiz permissionBiz;

    @RequestMapping("user/{username}")
    @ResponseBody
    public User getUserByUsername(@PathVariable("username") String username) {
        return userBiz.getUserByUsername(username);
    }

    @RequestMapping("checkUserAndPermission/{userid}/{permissionid}")
    @ResponseBody
    public Boolean checkUserAndPermission(@PathVariable("userid") Integer userid,@PathVariable("permissionid") Long permissionid){
        return  userPermissionBiz.checkUserAndPermission(userid,permissionid);
    }

    @RequestMapping("checkUrlPermission/{url}/{method}")
    @ResponseBody
    public Boolean checkUrlPermission(@PathVariable("url")String url,@PathVariable("method")String method){
        return permissionBiz.checkUrlPermission(url,method);
    }

    @RequestMapping("getAllPermissions")
    @ResponseBody
    public List<Permission> getAllPermissions(){
        return  permissionBiz.getAllPermissions();
    }

}
