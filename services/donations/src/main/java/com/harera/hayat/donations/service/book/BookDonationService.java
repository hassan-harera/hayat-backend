package com.harera.hayat.donations.service.book;

import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.DonationStatus;
import com.harera.hayat.donations.model.book.BookDonation;
import com.harera.hayat.donations.model.book.BookDonationRequest;
import com.harera.hayat.donations.model.book.BookDonationResponse;
import com.harera.hayat.donations.model.book.BookDonationUpdateRequest;
import com.harera.hayat.donations.model.medicine.MedicineDonation;
import com.harera.hayat.donations.model.medicine.MedicineDonationResponse;
import com.harera.hayat.donations.repository.book.BookDonationRepository;
import com.harera.hayat.donations.repository.food.FoodDonationRepository;
import com.harera.hayat.donations.repository.medicine.MedicineDonationRepository;
import com.harera.hayat.donations.service.BaseService;
import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.service.city.CityService;
import com.harera.hayat.framework.service.file.CloudFileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.List;

import static com.harera.hayat.framework.util.FileUtils.convertMultiPartToFile;
import static com.harera.hayat.framework.util.ObjectMapperUtils.mapAll;

@Service
public class BookDonationService extends BaseService {

    @Value("${donation.book.expiration.days:45}")
    private int bookDonationExpirationDays;

    private final CloudFileService cloudFileService;
    private final BookDonationValidation bookDonationValidation;
    private final ModelMapper modelMapper;
    private final BookDonationRepository bookDonationRepository;
    private final CityService cityService;
    private final MedicineDonationRepository medicineDonationRepository;

    public BookDonationService(CloudFileService cloudFileService,
                    BookDonationValidation bookDonationValidation,
                    ModelMapper modelMapper,
                    BookDonationRepository bookDonationRepository,
                    CityService cityService,
                    MedicineDonationRepository medicineDonationRepository) {
        this.cloudFileService = cloudFileService;
        this.bookDonationValidation = bookDonationValidation;
        this.modelMapper = modelMapper;
        this.bookDonationRepository = bookDonationRepository;
        this.cityService = cityService;
        this.medicineDonationRepository = medicineDonationRepository;
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

    public List<BookDonationResponse> search(String query, Integer page) {
        page = Integer.max(page, 1) - 1;
        List<BookDonation> search = bookDonationRepository.search(query,
                        Pageable.ofSize(16).withPage(page));
        return mapAll(search, BookDonationResponse.class);
    }

    public List<BookDonationResponse> list(Integer page) {
        page = Integer.max(page, 1) - 1;
        return bookDonationRepository.findAll(Pageable.ofSize(16).withPage(page))
                        .map(bookDonation -> modelMapper.map(bookDonation,
                                        BookDonationResponse.class))
                        .toList();
    }

    public BookDonationResponse get(Long id) {
        BookDonation bookDonation = bookDonationRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(BookDonation.class, id));
        return modelMapper.map(bookDonation, BookDonationResponse.class);
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

    public MedicineDonationResponse updateImage(Long id, MultipartFile file) {
        MedicineDonation medicineDonation = medicineDonationRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(
                                        MedicineDonation.class, id));

        String imageUrl = cloudFileService.uploadFile(convertMultiPartToFile(file));
        if (medicineDonation.getImageUrl() == null) {
            medicineDonation.setImageUrl(imageUrl);
        } else {
            cloudFileService.deleteFile(medicineDonation.getImageUrl());
            medicineDonation.setImageUrl(imageUrl);
        }

        medicineDonationRepository.save(medicineDonation);
        return modelMapper.map(medicineDonation, MedicineDonationResponse.class);
    }
}
