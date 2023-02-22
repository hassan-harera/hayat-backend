package com.harera.hayat.needs.model.possession;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harera.hayat.needs.model.NeedDto;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class PossessionNeedDto extends NeedDto {

    @JsonProperty("possession_category_id")
    private Long possessionCategoryId;

    @JsonProperty("possession_condition_id")
    private Long possessionConditionId;

    @JsonProperty("possession_category")
    private PossessionCategory possessionCategory;

    @JsonProperty("possession_condition")
    private PossessionCondition possessionCondition;
}
