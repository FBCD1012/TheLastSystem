package Mapper;

import Enitity.userInfo;
import org.apache.ibatis.annotations.*;

public interface userInfoMapper {
    //添加用户信息
    //TODO 实现密码的哈希函数的字符串配置操作，这样可以大幅度增强软件的安全性质
    @Insert("INSERT INTO userinfo (username,PASSWORD) VALUE(#{username},#{password})")
    int insertUserInfo(@Param("username")String userName,@Param("password")String password);

    //删除用户信息
    @Delete("DELETE FROM userinfo where username=#{username}")
    int forgetTheInfo(String username);

    //获取用户信息，用于对比其中的信息是否一致
    @Select("SELECT * FROM userinfo where username=#{username} and password=#{password}")
    userInfo getTheUserInfo();

    //修改密码操作
    @Update("UPDATE userinfo SET password=#{password} where username=#{username}")
    int setNewlyPassword();
}
