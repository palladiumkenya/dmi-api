package com.kenyahmis.dmiapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Delegate;
import java.util.*;

@Data
@Schema(hidden = true)
public class ValidList <E> implements List<E> {

    @Valid
    @NotNull(message = "Request contains no case objects. Request should contain at least one case objects ")
    @Schema(hidden = true)
    @Delegate
    private List<E> list = new ArrayList<>();
}
