package com.harera.hayat.donations.service.book;

import com.harera.hayat.donations.model.DonationCategory;
import com.harera.hayat.donations.model.DonationStatus;
import com.harera.hayat.donations.model.book.BookDonation;
import com.harera.hayat.donations.model.book.BookDonationRequest;
import com.harera.hayat.donations.model.book.BookDonationResponse;
import com.harera.hayat.donations.repository.book.BookDonationRepository;
import com.harera.hayat.framework.model.city.City;
import com.harera.hayat.framework.model.user.BaseUser;
import com.harera.hayat.framework.repository.user.BaseUserRepository;
import com.harera.hayat.framework.service.city.CityService;
import jakarta.ws.rs.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookDonationServiceTest {

    @Mock
    private BookDonationValidation bookDonationValidation;
    @Mock
    private BaseUserRepository baseUserRepository;
    @Mock
    private BookDonationRepository bookDonationRepository;
    @Mock
    private CityService cityService;

    @InjectMocks
    private BookDonationService bookDonationService;

    @Test
    @DisplayName("Should throw an exception when creating a book donation with invalid request")
    void createBookDonationWithInvalidRequestThenThrowException() {
        BookDonationRequest invalidRequest = new BookDonationRequest();
        assertThrows(BadRequestException.class, () -> bookDonationService
                        .create(invalidRequest, "authorization"));
        verify(bookDonationValidation, times(1)).validateCreate(invalidRequest);
        verifyNoInteractions(baseUserRepository);
        verifyNoInteractions(cityService);
    }

    @Test
    @DisplayName("Should throw an exception when creating a book donation with invalid authorization")
    void createBookDonationWithInvalidAuthorizationThenThrowException() {
        BookDonationRequest bookDonationRequest = new BookDonationRequest();
        bookDonationRequest.setCityId(1L);

        when(cityService.getCity(bookDonationRequest.getCityId())).thenReturn(new City());

        assertThrows(BadRequestException.class, () -> bookDonationService
                        .create(bookDonationRequest, "invalid_authorization"));
        verify(cityService, times(1)).getCity(bookDonationRequest.getCityId());
    }

    @Test
    @DisplayName("Should create a book donation with valid request and authorization")
    void createBookDonationWithValidRequestAndAuthorization() {
        BookDonationRequest request = new BookDonationRequest();
        request.setCityId(1L);
        String authorization = "Bearer token";
        BaseUser user = new BaseUser();
        user.setId(1L);
        when(baseUserRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(cityService.getCity(request.getCityId())).thenReturn(new City());

        BookDonationResponse response =
                        bookDonationService.create(request, authorization);

        assertNotNull(response);
        assertEquals(DonationCategory.BOOKS, response.getCategory());
        assertEquals(DonationStatus.PENDING, response.getStatus());
        assertNotNull(response.getDonationDate());
        assertNotNull(response.getDonationExpirationDate());
        assertEquals(user.getId(), response.getUser().getId());
        assertNotNull(response.getId());
        verify(bookDonationRepository, times(1)).save(any(BookDonation.class));
    }
}
