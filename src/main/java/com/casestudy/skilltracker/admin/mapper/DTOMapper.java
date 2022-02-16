package com.casestudy.skilltracker.admin.mapper;

public interface DTOMapper<T,R> {
     T mapToDto(R r);
     R mapToEntity(T t);
}
