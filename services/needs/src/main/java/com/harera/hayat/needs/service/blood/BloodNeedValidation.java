package com.harera.hayat.needs.service.blood;

import com.harera.hayat.framework.exception.FieldFormatException;
import com.harera.hayat.framework.exception.MandatoryFieldException;
import com.harera.hayat.needs.model.blood.BloodNeedRequest;
import org.springframework.stereotype.Service;

@Service
public class BloodNeedValidation {

    public void validate(BloodNeedRequest bloodNeedRequest) {
        validateMandatory(bloodNeedRequest);
        validateFormat(bloodNeedRequest);
    }

    private void validateFormat(BloodNeedRequest bloodNeedRequest) {
        if (bloodNeedRequest.getAge() < 0 || bloodNeedRequest.getAge() > 120) {
            throw new FieldFormatException(null, "age");
        }
        if (!bloodNeedRequest.getBloodType().matches("^(A|B|AB|O)[+-]$")) {
            throw new FieldFormatException(null, "blood_type");
        }
    }

    private void validateMandatory(BloodNeedRequest bloodNeedRequest) {
        if (bloodNeedRequest.getBloodType() == null) {
            throw new MandatoryFieldException(null, "blood_type");
        }
        if (bloodNeedRequest.getAge() == 0) {
            throw new MandatoryFieldException(null, "age");
        }
    }
}
