package com.harera.hayat.needs.service.book;

import com.harera.hayat.framework.exception.DocumentNotFoundException;
import com.harera.hayat.framework.model.user.BaseUserDto;
import com.harera.hayat.framework.service.city.CityService;
import com.harera.hayat.framework.service.file.CloudFileService;
import com.harera.hayat.needs.model.NeedCategory;
import com.harera.hayat.needs.model.NeedStatus;
import com.harera.hayat.needs.model.book.BookNeed;
import com.harera.hayat.needs.model.book.BookNeedRequest;
import com.harera.hayat.needs.model.book.BookNeedResponse;
import com.harera.hayat.needs.repository.book.BookNeedRepository;
import com.harera.hayat.needs.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import static com.harera.hayat.framework.util.FileUtils.convertMultiPartToFile;
import static com.harera.hayat.framework.util.ObjectMapperUtils.mapAll;

@Service
public class BookNeedService {

    private final CityService cityService;
    private final BookNeedValidation bookNeedValidation;
    private final ModelMapper modelMapper;
    private final BookNeedRepository bookNeedRepository;
    private final UserService userService;
    private final CloudFileService cloudFileService;

    public BookNeedService(CityService cityService, BookNeedValidation bookNeedValidation,
                    ModelMapper modelMapper, BookNeedRepository bookNeedRepository,
                    UserService userService, CloudFileService cloudFileService) {
        this.cityService = cityService;
        this.bookNeedValidation = bookNeedValidation;
        this.modelMapper = modelMapper;
        this.bookNeedRepository = bookNeedRepository;
        this.userService = userService;
        this.cloudFileService = cloudFileService;
    }

    public BookNeedResponse create(BookNeedRequest bookNeedRequest,
                    String authorization) {
        bookNeedValidation.validate(bookNeedRequest);

        BookNeed bookNeed = modelMapper.map(bookNeedRequest, BookNeed.class);
        bookNeed.setNeedDate(LocalDateTime.now());
        bookNeed.setNeedExpirationDate(LocalDateTime.now().plusDays(45));
        bookNeed.setCategory(NeedCategory.BOOKS);
        bookNeed.setCity(cityService.get(bookNeedRequest.getCityId()));
        bookNeed.setStatus(NeedStatus.PENDING);
        // TODO: send to AI model to process the request
        bookNeed.setUser(modelMapper.map(userService.getUser(authorization),
                        BaseUserDto.class));

        var bookNeedResponse = bookNeedRepository.save(bookNeed);
        return modelMapper.map(bookNeedResponse, BookNeedResponse.class);
    }

    public List<BookNeedResponse> list() {
        List<BookNeed> all = bookNeedRepository.findAll();
        return mapAll(all, BookNeedResponse.class);
    }

    public void upvote(String id, String authorization) {
        BookNeed bookNeed = bookNeedRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Book Need not found"));
        bookNeed.downvote(userService.getUser(authorization).getId());
        bookNeedRepository.save(bookNeed);
    }

    public void downvote(String id, String authorization) {
        BookNeed bookNeed = bookNeedRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Book Need not found"));
        bookNeed.upvote(userService.getUser(authorization).getId());
        bookNeedRepository.save(bookNeed);
    }

    public List<BookNeedResponse> search(String query, int page) {
        page = Integer.max(page, 1) - 1;
        List<BookNeed> bookNeeds = bookNeedRepository.search(query, NeedStatus.ACTIVE);
        return mapAll(bookNeeds, BookNeedResponse.class);
    }

    public BookNeedResponse get(String id) {
        BookNeed bookNeed = bookNeedRepository.findById(id).orElseThrow(
                        () -> new DocumentNotFoundException("Book Need not found"));
        return modelMapper.map(bookNeed, BookNeedResponse.class);
    }

    public BookNeedResponse updateImage(String id, MultipartFile file) {
        BookNeed bookNeed = bookNeedRepository.findById(id).orElseThrow(
                        () -> new DocumentNotFoundException(BookNeed.class, id));

        String imageUrl = cloudFileService.uploadFile(convertMultiPartToFile(file));
        if (bookNeed.getImageUrl() == null) {
            bookNeed.setImageUrl(imageUrl);
        } else {
            cloudFileService.deleteFile(bookNeed.getImageUrl());
            bookNeed.setImageUrl(imageUrl);
        }

        bookNeedRepository.save(bookNeed);
        return modelMapper.map(bookNeed, BookNeedResponse.class);
    }
}
