package com.mabis.services;

import com.mabis.domain.attachment.*;
import com.mabis.domain.dish_type.DishType;
import com.mabis.domain.menu_item.CreateMenuItemDTO;
import com.mabis.domain.menu_item.MenuItem;
import com.mabis.domain.menu_item.ResponseMenuItemDTO;
import com.mabis.repositories.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MenuItemService
{
    private final MenuItemRepository menu_item_repository;
    private final DishTypeService dish_type_service;
    private final StorageServiceFactory storage_service_factory;
    private final ApplicationContext context;

    public ResponseMenuItemDTO create_menu_item(CreateMenuItemDTO menu_item_dto)
    {
        MenuItem menu_item = new MenuItem(menu_item_dto);

        DishType dish_type = dish_type_service.get_dish_type_by_id(menu_item_dto.dish_type_id());
        menu_item.setDish_type(dish_type);

        if (menu_item_dto.image() != null)
        {
            StorageService storageService = storage_service_factory.get_service("S3");
            AttachmentService attachmentService = context.getBean(AttachmentService.class, storageService);

            AttachmentUpload image_to_upload = new MultipartAttachmentUpload(menu_item_dto.image());
            String url = attachmentService.upload(image_to_upload);

            Attachment image = new Attachment(image_to_upload.get_name(), url);
            menu_item.setAttachment(image);
        }

        menu_item = menu_item_repository.save(menu_item);

        return ResponseMenuItemDTO.from_menu_item(menu_item);
    }

    public List<ResponseMenuItemDTO> get_all_menu_items()
    {
        return menu_item_repository.find_all();
    }
}
