package com.harera.hayat.donations.service.book;

import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.DonationStatus;
import com.harera.hayat.donations.model.book.BookDonation;
import com.harera.hayat.donations.model.book.BookDonationRequest;
import com.harera.hayat.donations.model.book.BookDonationResponse;
import com.harera.hayat.donations.model.book.BookDonationUpdateRequest;
import com.harera.hayat.donations.repository.book.BookDonationRepository;
import com.harera.hayat.donations.service.BaseService;
import com.harera.hayat.framework.service.city.CityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class BookDonationService extends BaseService {

    @Value("${donation.book.expiration.days:45}")
    private int bookDonationExpirationDays;

    private final BookDonationValidation bookDonationValidation;
    private final ModelMapper modelMapper;
    private final BookDonationRepository bookDonationRepository;
    private final CityService cityService;

    public BookDonationService(BookDonationValidation bookDonationValidation,
                    ModelMapper modelMapper,
                    BookDonationRepository bookDonationRepository,
                    CityService cityService) {
        this.bookDonationValidation = bookDonationValidation;
        this.modelMapper = modelMapper;
        this.bookDonationRepository = bookDonationRepository;
        this.cityService = cityService;
    }

    public BookDonationResponse create(BookDonationRequest request,
                    String authorization) {
        bookDonationValidation.validateCreate(request);

        BookDonation bookDonation = modelMapper.map(request, BookDonation.class);

        bookDonation.setCategory(DonationCategory.BOOKS);
        bookDonation.setStatus(DonationStatus.PENDING);
        bookDonation.setDonationDate(OffsetDateTime.now());
        bookDonation.setDonationExpirationDate(
                        OffsetDateTime.now().plusDays(bookDonationExpirationDays));
        bookDonation.setUser(getUser(authorization));
        bookDonation.setCity(cityService.getCity(request.getCityId()));

        bookDonationRepository.save(bookDonation);
        return modelMapper.map(bookDonation, BookDonationResponse.class);
    }

    public BookDonationResponse update(Long id,
                    BookDonationUpdateRequest bookDonationUpdateRequest) {
        return null;
    }

    public List<BookDonationResponse> list(int size, int page) {
        return null;
    }

    public BookDonationResponse get(Long id) {
        return null;
    }

    public void upvote(Long id, String authorization) {
        BookDonation bookDonation = bookDonationRepository.findById(id).orElseThrow();
        bookDonation.upvote(getUser(authorization));
        bookDonationRepository.save(bookDonation);
    }

    public void downvote(Long id, String authorization) {
        BookDonation bookDonation = bookDonationRepository.findById(id).orElseThrow();
        bookDonation.downvote(getUser(authorization));
        bookDonationRepository.save(bookDonation);
    }
}
