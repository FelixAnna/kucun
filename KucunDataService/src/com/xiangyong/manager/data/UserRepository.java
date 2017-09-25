package com.xiangyong.manager.data;

import com.xiangyong.manager.entities.UserEntity;

public interface UserRepository extends BaseRepository<UserEntity, Integer> {

    /**
     * 查找用户基本信息
     * @return
     */
    UserEntity findByUserId(int userId);

    /**
     * 手机登陆
     * @param cellPhone
     * @param password
     * @return
     */
    UserEntity findByCellPhoneAndPassword(String cellPhone, String password);

    /**
     * 用户名登陆
     * @param userName
     * @param password
     * @return
     */
    UserEntity findByUserNameAndPassword(String userName, String password);

    /**
     * 邮箱登陆
     * @param email
     * @param password
     * @return
     */
    UserEntity findByEmailAndPassword(String email, String password);
}
