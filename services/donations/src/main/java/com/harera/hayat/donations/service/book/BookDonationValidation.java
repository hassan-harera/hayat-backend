package com.harera.hayat.donations.service.book;

import com.harera.hayat.donations.model.book.BookDonationRequest;
import com.harera.hayat.donations.service.DonationValidation;
import com.harera.hayat.framework.exception.MandatoryFieldException;
import com.harera.hayat.framework.util.ErrorCode;
import org.springframework.stereotype.Service;

@Service
public class BookDonationValidation {

    private final DonationValidation donationValidation;

    public BookDonationValidation(DonationValidation donationValidation) {
        this.donationValidation = donationValidation;
    }

    public void validateCreate(BookDonationRequest request) {
        donationValidation.validateCreate(request);
        validateMandatory(request);
        validateFormat(request);
        // TODO: validate other not null values (e.g. book status, book sub title)
    }

    private void validateFormat(BookDonationRequest request) {
        if (request.getBookTitle().length() < 4
                        || request.getBookTitle().length() > 100) {
            throw new MandatoryFieldException(ErrorCode.FORMAT_BOOK_DONATION_TITLE,
                            "book_title");
        }
        if (request.getAmount() < 1 || request.getAmount() > 1000) {
            throw new MandatoryFieldException(ErrorCode.FORMAT_BOOK_DONATION_AMOUNT,
                            "quantity");
        }
    }

    private void validateMandatory(BookDonationRequest request) {
        if (request.getBookTitle() == null) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_BOOK_DONATION_TITLE,
                            "book_title");
        }
        if (request.getAmount() == null) {
            throw new MandatoryFieldException(ErrorCode.MANDATORY_BOOK_DONATION_AMOUNT,
                            "quantity");
        }
    }
}
