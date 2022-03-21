package org.example.PetStore.service;

import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.example.PetStore.utils.WeBASEUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
    @Value("${system.contract.adoptionAddress}")
    String contractAddress;

    public static final String ABI = org.example.PetStore.utils.IOUtil.readResourceAsString("abi/Adoption.abi");

    @Autowired
    WeBASEUtils weBASEUtils;
    public Dict register(String userAddress) {
        List funcParam = new ArrayList();
        funcParam.add(userAddress);
        Dict result = weBASEUtils.commonReq(
                userAddress,
                "register",
                funcParam,
                ABI,
                "Adoption",
                contractAddress
        );
        JSONObject respBody = JSONUtil.parseObj(result.get("result"));
        boolean statusOK = (boolean) respBody.get("statusOK");

        if(statusOK){
            result.set("result", "注册成功");
            result.set("code", 1);
        }else {
            log.info("function register error:"+respBody);
            result.set("result", "注册失败，失败原因："+respBody.get("message"));
            result.set("code", 0);

        }
        return result;
    }
    public Dict login(String userAddress) {
        List funcParam = new ArrayList();
        funcParam.add(userAddress);
        Dict result = weBASEUtils.commonReq(userAddress, "login", funcParam, ABI, "Adoption", contractAddress);
        JSONArray respBody = JSONUtil.parseArray(result.get("result"));
        if(respBody.getInt(0) != null && respBody.getInt(0) > 0){
            result.set("result","登录成功");
            result.set("code", 1);
        }else {
            log.info("function login error:"+respBody.get(0));
            result.set("result","用户未注册");
            result.set("code",0);
        }
        return result;
    }
}
