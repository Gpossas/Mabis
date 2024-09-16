package com.mabis.domain.menu_item;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record CreateMenuItemDTO(
   @NotBlank()
   @Size(max = 250)
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
