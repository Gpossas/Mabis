package com.mabis.domain.menu_item;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record CreateMenuItemDTO(
   String name,
   Float price,
   String description,
   UUID dish_type_id,
   MultipartFile image_url
) {}
