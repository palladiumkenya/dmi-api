package com.kenyahmis.dmiapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.experimental.Delegate;
import java.util.*;

@Data
@Schema(hidden = true)
public class ValidList <E> implements List<E> {

    @Valid
    @Schema(hidden = true)
    @Delegate
    private List<E> list = new ArrayList<>();
}
