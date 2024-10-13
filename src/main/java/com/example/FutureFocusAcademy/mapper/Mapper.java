package com.example.FutureFocusAcademy.mapper;

public interface Mapper <T,E>{
    public T toDto (E entity);
    public E toEntity (T dto);
    public E updateToEntity( T dto , E entity);
}
