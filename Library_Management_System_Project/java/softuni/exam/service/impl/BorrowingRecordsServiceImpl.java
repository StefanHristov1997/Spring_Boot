package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.BorrowingRecordRootDto;
import softuni.exam.models.dto.xml.BorrowingRecordSeedDto;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.models.entity.enums.Genre;
import softuni.exam.repository.BorrowingRecordRepository;
import softuni.exam.service.BookService;
import softuni.exam.service.BorrowingRecordsService;
import softuni.exam.service.LibraryMemberService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static softuni.exam.constant.FilesPath.RECORDS_FILE_PATH;
import static softuni.exam.constant.Messages.INVALID_RECORD;
import static softuni.exam.constant.Messages.SUCCESSFULLY_IMPORT_RECORD;

@Service
public class BorrowingRecordsServiceImpl implements BorrowingRecordsService {
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookService bookService;
    private final LibraryMemberService libraryMemberService;

    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public BorrowingRecordsServiceImpl(BorrowingRecordRepository borrowingRecordRepository, BookService bookService, LibraryMemberService libraryMemberService, Gson gson, ModelMapper mapper, ValidationUtil validationUtil) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookService = bookService;
        this.libraryMemberService = libraryMemberService;
        this.gson = gson;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.borrowingRecordRepository.count() > 0;
    }

    @Override
    public String readBorrowingRecordsFromFile() throws IOException {
        return Files.readString(Path.of(RECORDS_FILE_PATH));
    }

    @Override
    public String importBorrowingRecords() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(BorrowingRecordRootDto.class);

        File file = new File(RECORDS_FILE_PATH);

        Unmarshaller unmarshaller = context.createUnmarshaller();
        BorrowingRecordRootDto recordRootDTOs = (BorrowingRecordRootDto) unmarshaller.unmarshal(file);

        List<BorrowingRecordSeedDto> recordSeedDtos = recordRootDTOs.getRecords();

        for (BorrowingRecordSeedDto recordSeedDto : recordSeedDtos) {

            Book book = bookService.isBookExist(recordSeedDto.getBook().getTitle());
            LibraryMember member = libraryMemberService.isMemberExist(recordSeedDto.getMember().getId());

            if (book == null || member == null) {
                sb.append(INVALID_RECORD);
                continue;
            }

            boolean isValid = validationUtil.isValid(recordSeedDto);

            if (isValid) {
                BorrowingRecord record = mapper.map(recordSeedDto, BorrowingRecord.class);
                record.setBook(book);
                record.setMember(member);
                borrowingRecordRepository.saveAndFlush(record);
            }

            sb.append(isValid ? String.format(SUCCESSFULLY_IMPORT_RECORD, recordSeedDto.getBook().getTitle(), recordSeedDto.getBorrowDate())
                    : INVALID_RECORD);
        }

        return sb.toString();
    }

    @Override
    public List<BorrowingRecord> findBorrowingRecordsByBookGenre(Genre genre) {
        return this.borrowingRecordRepository.findBorrowingRecordsByBookGenreOrderByBorrowDateDesc(Genre.SCIENCE_FICTION);
    }

    @Override
    public String exportBorrowingRecords() {
        StringBuilder sb = new StringBuilder();

        findBorrowingRecordsByBookGenre(Genre.SCIENCE_FICTION).forEach(sb::append);

        return sb.toString().trim();
    }
}
