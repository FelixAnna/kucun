package com.xiangyong.manager.biz;

import com.google.common.base.Strings;
import com.xiangyong.manager.biz.interfaces.FileBizService;
import com.xiangyong.manager.biz.interfaces.UserBizService;
import com.xiangyong.manager.common.util.DataUtils;
import com.xiangyong.manager.common.util.ValidUtils;
import com.xiangyong.manager.core.exception.BadRequestException;
import com.xiangyong.manager.data.UserRepository;
import com.xiangyong.manager.dto.LoginMiddleDto;
import com.xiangyong.manager.dto.SimpleMemberDto;
import com.xiangyong.manager.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class UserBizServiceImp implements UserBizService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileBizService fileBizService;

    /**
     * 处理用户登录
     * @param loginMiddleDto
     * @return 登录信息 + token
     */
    @Override
    public SimpleMemberDto Login(LoginMiddleDto loginMiddleDto) {
        UserEntity userEntity;
        String password = loginMiddleDto.getPassword();
        password = DataUtils.md5(password);
        if(ValidUtils.isPhone(loginMiddleDto.getUserName(), false)){
            //手机登陆
            userEntity = userRepository.findByCellPhoneAndPassword(loginMiddleDto.getUserName(), password);
        } else if(ValidUtils.isEmail(loginMiddleDto.getUserName())){
            //邮箱登陆
            userEntity = userRepository.findByEmailAndPassword(loginMiddleDto.getUserName(), password);
        }else{
            //用户名登陆
            userEntity = userRepository.findByUserNameAndPassword(loginMiddleDto.getUserName(), password);
        }

        if(userEntity != null){
            SimpleMemberDto simpleMemberDto = new SimpleMemberDto();
            simpleMemberDto.setUserId(userEntity.getUserId());
            simpleMemberDto.setUserName(userEntity.getUserName());
            simpleMemberDto.setCellPhone(userEntity.getCellPhone());
            simpleMemberDto.setToken(UUID.randomUUID().toString().replace("-",""));
            return simpleMemberDto;
        } else {
            throw new BadRequestException("用户名或密码错误");
        }
    }

    public Boolean UpdateUserAvatar(int userId, String icon){
        UserEntity userEntity = this.userRepository.findByUserId(userId);
        String oldIcon=userEntity.getIcon();

        userEntity.setUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
        userEntity.setIcon(icon);
        this.userRepository.save(userEntity);

        if(!Strings.isNullOrEmpty(oldIcon)){
            fileBizService.Remove(oldIcon);
        }
        return  true;
    }
}
