package com.harera.hayat.needs.service.book;

import com.harera.hayat.framework.model.user.BaseUserDto;
import com.harera.hayat.framework.service.city.CityService;
import com.harera.hayat.framework.util.ObjectMapperUtils;
import com.harera.hayat.needs.model.NeedCategory;
import com.harera.hayat.needs.model.NeedStatus;
import com.harera.hayat.needs.model.books.BookNeed;
import com.harera.hayat.needs.model.books.BookNeedRequest;
import com.harera.hayat.needs.model.books.BookNeedResponse;
import com.harera.hayat.needs.repository.books.BookNeedRepository;
import com.harera.hayat.needs.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookNeedService {

    private final CityService cityService;
    private final BookNeedValidation bookNeedValidation;
    private final ModelMapper modelMapper;
    private final BookNeedRepository bookNeedRepository;
    private final UserService userService;

    public BookNeedService(CityService cityService, BookNeedValidation bookNeedValidation,
                    ModelMapper modelMapper, BookNeedRepository bookNeedRepository,
                    UserService userService) {
        this.cityService = cityService;
        this.bookNeedValidation = bookNeedValidation;
        this.modelMapper = modelMapper;
        this.bookNeedRepository = bookNeedRepository;
        this.userService = userService;
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
        return ObjectMapperUtils.mapAll(all, BookNeedResponse.class);
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
}
