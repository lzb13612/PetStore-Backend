pragma solidity ^0.4.24;

contract Adoption {
    uint8 userIndex;
    mapping(address => uint8) userMapping;
    
    // 保存领养者的地址 
    address[8] public adopters;
    
    constructor() public { 
        userIndex = 0;
    }
    
    //用户注册
    function register(address user) public returns(uint8) {
        require(userMapping[user] == 0,"user is registered");
        userIndex++;
        userMapping[user] = userIndex;
        return userIndex;
    }
    
    //判断用户是否可以登录（>0）
    function login(address user) public view returns(uint8) {
        require(userMapping[user] != 0,"user not register");
        return userMapping[user];
    }
    
    //领养宠物
    function adopt(uint8 petId) public returns (uint8) {
        // 确保 id 在数组长度内
        require(petId >= 0 && petId <= 7);
        require(userMapping[msg.sender] > 0);
        // 保存调用这地址
        adopters[petId] = msg.sender; 
        return petId;
    }
    // 返回领养者
    function getAdopters() public view returns (address[8]) { 
        return adopters;
    }
}