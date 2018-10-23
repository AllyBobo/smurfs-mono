package com.iscas.smurfs.modules.admin.biz;


import com.iscas.smurfs.modules.admin.entity.po.Permission;

import java.util.List;

public interface IPermissionBiz extends IBaseBiz<Permission> {
    boolean checkUrlPermission(String url, String method);
    List<Permission> getAllPermissions();
}
