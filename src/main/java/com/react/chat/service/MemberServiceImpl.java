package com.react.chat.service;

import com.react.chat.domain.enumFiles.Role;
import com.react.chat.domain.enumFiles.UserLanguages;
import com.react.chat.domain.member.Member;
import com.react.chat.dto.MemberDTO;
import com.react.chat.dto.MemberFormDTO;
import com.react.chat.repository.MemberRepository;
import com.react.chat.util.FileUtilCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
  private final ModelMapper modelMapper;
  private final MemberRepository memberRepository;
  private final FileUtilCustom fileUtil;
  private final PasswordEncoder encoder;

  @Override
  public Long signup(MemberFormDTO memberFormDTO) {
    log.info("********** MemberServiceImpl signup - memberFormDTO : {}", memberFormDTO);

    List<MultipartFile> files = memberFormDTO.getFiles();
    List<String> uploadFileNames = fileUtil.saveFiles(files);
    memberFormDTO.setUploadedFileNames(uploadFileNames);

    List<String> languageStrings = memberFormDTO.getLanguageList();
    log.info("********** MemberServiceImpl signup - languageStrings : {}", languageStrings);
    List<UserLanguages> languages = languageStrings.stream().map(UserLanguages::valueOf).collect(Collectors.toList());
    log.info("********** MemberServiceImpl signup - languages : {}", languages);

    log.info("********** MemberServiceImpl signup - password : {}", memberFormDTO.getPassword());
    Member member = dtoToEntity(memberFormDTO);

    member.addUserLanguages(languages);
    member.addRole(Role.USER);
    member.changePassword(encoder.encode(member.getPassword()));
    log.info("********** MemberServiceImpl signup - encodedPassword : {}", member.getPassword());

    if (memberFormDTO.getUploadedFileNames() != null) {
      memberFormDTO.getUploadedFileNames().forEach(name -> {
        member.addImageString(name);
      });
    }

    Member saved = memberRepository.save(member);
    log.info("********** MemberServiceImpl signup - member : {}", member);
    return saved.getId();

  }

  @Override
  public Boolean checkEmail(String email) {

//    boolean result = memberRepository.findByEmail(email).isPresent();
//    log.info("***** MemberServiceImpl checkEmail - findByEmail : {}", result);

    boolean result = memberRepository.existsByEmail(email);
    log.info("***** MemberServiceImpl checkEmail - existsByEmail : {}", result);

    return result;
  }

  @Override
  public Boolean checkNickname(String nickname) {

    boolean result = memberRepository.existsByNickname(nickname);
    log.info("***** MemberServiceImpl checkNickname - checkNickname : {}", result);

    return result;
  }

  @Override
  public List<MemberFormDTO> getAllMembers() {
    List<Member> members = memberRepository.findAll();
    return members.stream()
            .map(member -> modelMapper.map(member, MemberFormDTO.class))
            .collect(Collectors.toList());
  }

  // 내부에서만 사용할 메서드 -> private 으로 지정
  // Entity -> MemberFormDTO
  private MemberFormDTO entityToDTO(Member member) {
    MemberFormDTO memberFormDTO = MemberFormDTO.builder()
            .id(member.getId())
            .email(member.getEmail())
            .nickname(member.getNickname())
            .password(member.getPassword())
            .phone(member.getPhone())
            .introduction(member.getIntroduction())
            .birth(member.getBirth())
            .nationality(member.getNationality())
            .languageList(member.getLanguageList().stream().map(Enum::toString) // Enum -> String
                    .collect(Collectors.toList()))
            .gender(member.getGender())
            .role(member.getRole())
            .build();
    return memberFormDTO;
  }

  // MemberFormDTO -> Entity
  private Member dtoToEntity(MemberFormDTO memberFormDTO) {
    Member member = Member.builder()
            .id(memberFormDTO.getId())
            .email(memberFormDTO.getEmail())
            .nickname(memberFormDTO.getNickname())
            .password(memberFormDTO.getPassword())
            .phone(memberFormDTO.getPhone())
            .introduction(memberFormDTO.getIntroduction())
            .birth(memberFormDTO.getBirth())
            .nationality(memberFormDTO.getNationality())
            /*        .languageList(memberFormDTO.getLanguageList()
                        .stream()
                        .map(UserLanguages::valueOf) // String -> Enum
                        .collect(Collectors.toList()))*/
            .gender(memberFormDTO.getGender())
            .role(memberFormDTO.getRole())
            .build();
    return member;
  }

}
