package com.mabis.domain.menu_item;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record CreateMenuItemDTO(
   @NotBlank()
   String name,

   @NotNull
   @Min(0)
   @Max(9999)
   Float price,

   String description,

   @NotNull
   UUID dish_type_id,

   MultipartFile image
) {}
