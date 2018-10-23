package com.iscas.smurfs.modules.admin.controller;

import com.iscas.smurfs.common.auth.entity.dto.UserLoginDto;
import com.iscas.smurfs.common.config.KeyConfiguration;
import com.iscas.smurfs.common.entity.dto.ResponseData;
import com.iscas.smurfs.modules.admin.biz.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private IAuthService authService;
    @Autowired
    private KeyConfiguration keyConfiguration;


    @RequestMapping(value = "/login", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public ResponseData createAuthenticationToken(
            @RequestBody UserLoginDto userLoginDto) throws Exception {
        final String token = authService.login(userLoginDto);
        return ResponseData.ok(token);
    }

    @RequestMapping(value = "/userPubKey",method = RequestMethod.GET)
    public ResponseData<byte[]> getUserPublicKey(){
        return ResponseData.ok(keyConfiguration.getUserPubKey());
    }

}
