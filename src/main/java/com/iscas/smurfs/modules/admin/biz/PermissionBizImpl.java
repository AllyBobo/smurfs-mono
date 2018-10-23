package com.iscas.smurfs.modules.admin.biz;


import com.iscas.smurfs.modules.admin.entity.po.Permission;
import com.iscas.smurfs.modules.admin.mapper.PermissionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * description:
 *
 * @author 123
 * @date 2018/9/7
 */

@Service
@Transactional
public class PermissionBizImpl extends BaseBizImpl<PermissionMapper, Permission> implements IPermissionBiz {

//    @Cacheable
    @Override
    public boolean checkUrlPermission(String url, String method) {
        Permission permission = new Permission();
        permission.setUri(url);
        permission.setMethod(method);
        return super.mapper.select(permission).isEmpty();
    }

    //@Log
    //@Cacheable
    @Override
    public List<Permission> getAllPermissions() {
        return super.mapper.selectAll();
    }

}
