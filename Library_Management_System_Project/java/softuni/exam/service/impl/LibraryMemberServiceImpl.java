package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.MemberSeedDto;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.LibraryMemberService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.constant.FilesPath.MEMBERS_FILE_PATH;
import static softuni.exam.constant.Messages.INVALID_MEMBER;
import static softuni.exam.constant.Messages.SUCCESSFULLY_IMPORT_MEMBER;

@Service
public class LibraryMemberServiceImpl implements LibraryMemberService {
    private final LibraryMemberRepository libraryMemberRepository;

    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public LibraryMemberServiceImpl(LibraryMemberRepository libraryMemberRepository, Gson gson, ModelMapper mapper, ValidationUtil validationUtil) {
        this.libraryMemberRepository = libraryMemberRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.libraryMemberRepository.count() > 0;
    }

    @Override
    public String readLibraryMembersFileContent() throws IOException {
        return Files.readString(Path.of(MEMBERS_FILE_PATH));
    }

    @Override
    public String importLibraryMembers() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<MemberSeedDto> memberSeedDTOs =
                Arrays.stream(gson.fromJson(readLibraryMembersFileContent(), MemberSeedDto[].class)).collect(Collectors.toList());

        for (MemberSeedDto memberSeedDto : memberSeedDTOs) {

            boolean isMemberExist = isMemberWithThisNumberExist(memberSeedDto.getPhoneNumber()).isPresent();

            if (isMemberExist) {
                sb.append(INVALID_MEMBER);
                continue;
            }
            boolean isValid = validationUtil.isValid(memberSeedDto);

            if (isValid) {
                LibraryMember memberToSave = mapper.map(memberSeedDto, LibraryMember.class);
                libraryMemberRepository.saveAndFlush(memberToSave);
            }

            sb.append(isValid ? String.format(SUCCESSFULLY_IMPORT_MEMBER, memberSeedDto.getFirstName(), memberSeedDto.getLastName())
                    : INVALID_MEMBER);
        }

        return sb.toString();
    }

    @Override
    public Optional<LibraryMember> isMemberWithThisNumberExist(String phoneNumber) {
        return this.libraryMemberRepository.findLibraryMemberByPhoneNumber(phoneNumber);
    }

    @Override
    public LibraryMember isMemberExist(Long id) {
        return this.libraryMemberRepository.findLibraryMemberById(id).orElse(null);
    }
}
