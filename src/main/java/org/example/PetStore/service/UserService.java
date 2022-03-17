package org.example.PetStore.service;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.HexUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.example.PetStore.utils.WeBASEUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        String output = (String) respBody.get("output");
        if(output == null){
            output = (String) respBody.get("errorMessage");
            result.set("result", output);
            result.set("code", 0);
            return result;
        }
        long resInt = HexUtil.hexToLong(output.substring(2));
        result.set("result", resInt);
        result.set("code", 1);
        return result;
    }
    public Dict login(String userAddress) {
        List funcParam = new ArrayList();
        funcParam.add(userAddress);
        Dict result = weBASEUtils.commonReq(userAddress, "login", funcParam, ABI, "Adoption", contractAddress);
        try{
            JSONArray respBody = JSONUtil.parseArray(result.get("result"));
            if (respBody.size() > 0) {
                result.set("result", respBody.get(0));
                result.set("code", 1);
            }
            return result;
        }catch (Exception e){
//            e.printStackTrace();
            JSONObject respBody = JSONUtil.parseObj(result.get("result"));
            result.set("result", respBody.get("errorMessage"));
            result.set("code",respBody.get("code"));
            return result;
        }

    }
}
