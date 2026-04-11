package com.gustavodscruz.livranada.model.mappers;

import com.gustavodscruz.livranada.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    void updateEntityFromDTO(User dto, @MappingTarget User entity);
}
