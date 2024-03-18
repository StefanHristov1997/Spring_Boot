package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.BookSeedDto;
import softuni.exam.models.entity.Book;
import softuni.exam.repository.BookRepository;
import softuni.exam.service.BookService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.constant.FilesPath.BOOKS_FILE_PATH;
import static softuni.exam.constant.Messages.INVALID_BOOK;
import static softuni.exam.constant.Messages.SUCCESSFULLY_IMPORT_BOOKS;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, Gson gson, ValidationUtil validationUtil, ModelMapper mapper) {
        this.bookRepository = bookRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.bookRepository.count() > 0;
    }

    @Override
    public String readBooksFromFile() throws IOException {
        return Files.readString(Path.of(BOOKS_FILE_PATH));
    }

    @Override
    public String importBooks() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<BookSeedDto> bookSeedDTOs =
                Arrays.stream(gson.fromJson(readBooksFromFile(), BookSeedDto[].class)).collect(Collectors.toList());

        for (BookSeedDto bookSeedDTO : bookSeedDTOs) {
            boolean isValid = validationUtil.isValid(bookSeedDTO);

            boolean isAlreadyExist = isBookWithThisTitleExist(bookSeedDTO.getTitle()).isPresent();

            if (isAlreadyExist) {
                sb.append(INVALID_BOOK);
                continue;
            }

            if (isValid) {
                Book bookToSave = mapper.map(bookSeedDTO, Book.class);
                bookRepository.saveAndFlush(bookToSave);
            }

            sb.append(isValid ? String.format(SUCCESSFULLY_IMPORT_BOOKS, bookSeedDTO.getAuthor(), bookSeedDTO.getTitle())
                    : INVALID_BOOK);
        }

        return sb.toString();
    }

    @Override
    public Optional<Book> isBookWithThisTitleExist(String title) {
        return this.bookRepository.findBookByTitle(title);
    }

    @Override
    public Book isBookExist(String title) {
        return this.bookRepository.findBookByTitle(title).orElse(null);
    }


}
