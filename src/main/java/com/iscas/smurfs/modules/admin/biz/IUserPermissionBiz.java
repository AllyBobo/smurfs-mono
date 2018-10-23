package com.iscas.smurfs.modules.admin.biz;


import com.iscas.smurfs.modules.admin.entity.po.UserPermission;

public interface IUserPermissionBiz extends IBaseBiz<UserPermission> {
    boolean checkUserAndPermission(Integer userid, Long permissionid);
}
