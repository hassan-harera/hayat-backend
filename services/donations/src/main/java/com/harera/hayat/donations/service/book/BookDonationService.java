package com.harera.hayat.donations.service.book;

import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.DonationStatus;
import com.harera.hayat.donations.model.ai.Prediction;
import com.harera.hayat.donations.model.book.BookDonation;
import com.harera.hayat.donations.model.book.BookDonationRequest;
import com.harera.hayat.donations.model.book.BookDonationResponse;
import com.harera.hayat.donations.model.book.BookDonationUpdateRequest;
import com.harera.hayat.donations.model.clothing.ClothingDonation;
import com.harera.hayat.donations.model.medicine.MedicineDonation;
import com.harera.hayat.donations.repository.book.BookDonationRepository;
import com.harera.hayat.donations.service.BaseService;
import com.harera.hayat.donations.service.DonationNotificationsService;
import com.harera.hayat.donations.service.ai.PredictionService;
import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.service.city.CityService;
import com.harera.hayat.framework.service.file.CloudFileService;
import jakarta.ws.rs.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

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
    private final DonationNotificationsService donationNotificationsService;
    private final PredictionService predictionService;

    public BookDonationService(CloudFileService cloudFileService,
                    BookDonationValidation bookDonationValidation,
                    ModelMapper modelMapper,
                    BookDonationRepository bookDonationRepository,
                    CityService cityService,
                    DonationNotificationsService donationNotificationsService,
                    PredictionService predictionService) {
        this.cloudFileService = cloudFileService;
        this.bookDonationValidation = bookDonationValidation;
        this.modelMapper = modelMapper;
        this.bookDonationRepository = bookDonationRepository;
        this.cityService = cityService;
        this.donationNotificationsService = donationNotificationsService;
        this.predictionService = predictionService;
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

        donationNotificationsService.notifyProcessingDonation(bookDonation);
        reviewDonation(bookDonation);

        return modelMapper.map(bookDonation, BookDonationResponse.class);
    }

    private void reviewDonation(BookDonation bookDonation) {
        Prediction prediction = predictionService.predict(
                        bookDonation.getTitle() + " " + bookDonation.getDescription());
        if (Objects.equals(prediction.getLabel(), "BOOK")
                        && prediction.getCertainty() > 0.5) {
            bookDonation.setStatus(DonationStatus.ACTIVE);
            donationNotificationsService.notifyDonationAccepted(bookDonation);
        } else {
            bookDonation.setStatus(DonationStatus.REJECTED);
            donationNotificationsService.notifyDonationRejected(bookDonation);
        }
        bookDonationRepository.save(bookDonation);
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

    public BookDonationResponse updateImage(Long id, MultipartFile file) {
        BookDonation bookDonation = bookDonationRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException(MedicineDonation.class, id));

        String imageUrl = cloudFileService.uploadFile(convertMultiPartToFile(file));
        if (bookDonation.getImageUrl() == null) {
            bookDonation.setImageUrl(imageUrl);
        } else {
            cloudFileService.deleteFile(bookDonation.getImageUrl());
            bookDonation.setImageUrl(imageUrl);
        }

        bookDonationRepository.save(bookDonation);
        return modelMapper.map(bookDonation, BookDonationResponse.class);
    }

    public void receive(Long id) {
        BookDonation bookDonation = bookDonationRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MedicineDonation.class, id));
        if (bookDonation.getStatus() != DonationStatus.ACTIVE) {
            throw new BadRequestException("Donation is not active");
        }
        bookDonation.setStatus(DonationStatus.DONE);
        bookDonation.getUser().setReputation(bookDonation.getUser().getReputation() + 50);
        bookDonationRepository.save(bookDonation);
    }
}
