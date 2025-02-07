package com.italoasc.authentication_authorization.mapper;

import com.italoasc.authentication_authorization.entity.User;
import com.italoasc.authentication_authorization.model.LoginRequest;
import com.italoasc.authentication_authorization.model.MfaRequest;
import com.italoasc.authentication_authorization.model.UserRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper userMapper = Mappers.getMapper( UserMapper.class );

    User map(UserRegisterRequest userRegisterRequest);
}
