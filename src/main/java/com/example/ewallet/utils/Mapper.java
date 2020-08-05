package com.example.ewallet.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {
  private final ModelMapper modelMapper;

  @Autowired
  public Mapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public <T> T map(Object source, Class<T> destinationClass) {
    return modelMapper.map(source, destinationClass);
  }

  public void map(Object source, Object destination) {
    modelMapper.map(source, destination);
  }

  public <T> List<T> mapList(List<?> sourceList, Class<T> destinationListElementClass) {
    if (sourceList == null) {
      return null;
    }
    return sourceList.stream()
        .map(source -> map(source, destinationListElementClass))
        .collect(Collectors.toList());
  }
}
