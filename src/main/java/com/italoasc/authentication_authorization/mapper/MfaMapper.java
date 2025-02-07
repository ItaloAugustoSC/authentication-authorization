package com.italoasc.authentication_authorization.mapper;

import com.italoasc.authentication_authorization.model.LoginRequest;
import com.italoasc.authentication_authorization.model.MfaRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MfaMapper {
    MfaMapper mfaMapper = Mappers.getMapper( MfaMapper.class );

    LoginRequest map(MfaRequest mfaRequest);
}
