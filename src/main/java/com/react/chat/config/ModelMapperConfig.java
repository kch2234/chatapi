package com.react.chat.config;

import com.react.chat.domain.member.Member;
import com.react.chat.dto.MemberDTO;
import com.react.chat.dto.ProfileImageDTO;
import org.modelmapper.Conditions;
import org.modelmapper.ConfigurationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper getMapper() {
    ModelMapper modelMapper = new ModelMapper();

//    try {
      modelMapper.getConfiguration()
          .setFieldMatchingEnabled(true)
          .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
          .setMatchingStrategy(MatchingStrategies.LOOSE);

//      modelMapper.addMappings(new PropertyMap<Member, MemberDTO>() {
//        @Override
//        protected void configure() {
//          // 필드 매핑
//          map().setId(source.getId());
//          map().setEmail(source.getEmail());
//          map().setNickname(source.getNickname());
//          map().setPassword(source.getPassword());
//          map().setPhone(source.getPhone());
//          map().setIntroduction(source.getIntroduction());
//          map().setBirth(source.getBirth());
//          map().setDisabled(source.isDisabled());
//          map().setDisabledDate(source.getDisabledDate());
//          map().setNationality(source.getNationality());
//          map().setGender(source.getGender());
//          map().setRole(source.getRole());
//          map().setCreateDate(source.getCreateDate());
//          map().setUpdateDate(source.getUpdateDate());
//        }
//      });
//    } catch (ConfigurationException e) {
//      e.printStackTrace();
//      throw e;
//    }

    return modelMapper;
  }
}
