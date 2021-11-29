package com.epam.esm.service.config;

import com.epam.esm.service.dto.GiftCertificateConverter;
import com.epam.esm.service.dto.TagConverter;
import com.epam.esm.service.dto.UserConverter;
import com.epam.esm.service.util.Validator;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
public class ServiceConfig {
  @Bean
  public Validator validator() {
    return new Validator();
  }

  @Bean
  public UserConverter userConverter(ModelMapper modelMapper) {
    return new UserConverter(modelMapper);
  }

  @Bean
  public TagConverter tagConverter(ModelMapper modelMapper) {
    return new TagConverter(modelMapper);
  }

  @Bean
  public GiftCertificateConverter certificateConverter(ModelMapper modelMapper, TagConverter tagConverter) {
    return new GiftCertificateConverter(modelMapper, tagConverter);
  }

  @Bean
  public ModelMapper modelMapper(){
    ModelMapper modelMapper = new ModelMapper();

    modelMapper.getConfiguration()
        .setMatchingStrategy(MatchingStrategies.STRICT)
        .setFieldMatchingEnabled(true)
        .setSkipNullEnabled(true)
        .setFieldAccessLevel(PRIVATE);
    return modelMapper;
  }
}
