package com.gustavodscruz.livranada.model.mappers;

import com.gustavodscruz.livranada.model.User;
import com.gustavodscruz.livranada.model.UserLoginRequest;
import com.gustavodscruz.livranada.model.UserRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    void updateEntityFromDTO(User dto, @MappingTarget User entity);
    User toEntity(UserRegisterRequest dto);
    UserLoginRequest toUserLoginRequest(UserRegisterRequest dto);
}
