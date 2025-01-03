package com.mabis.services;

import com.mabis.domain.attachment.*;
import com.mabis.domain.dish_type.DishType;
import com.mabis.domain.menu_item.CreateMenuItemDTO;
import com.mabis.domain.menu_item.MenuItem;
import com.mabis.domain.menu_item.ResponseMenuItemDTO;
import com.mabis.repositories.MenuItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MenuItemServiceTest
{
    @Mock
    private MenuItemRepository menu_item_repository;

    @Mock
    private DishTypeService dish_type_service;

    @Mock
    private StorageServiceFactory storage_service_factory;

    @Mock
    private ApplicationContext context;

    @InjectMocks
    private MenuItemService menu_item_service;

    @Test
    void create_menu_item_required_fields()
    {
        DishType dish_type = new DishType();
        dish_type.setId(UUID.randomUUID());

        MenuItem menu_item_mock = new MenuItem(new CreateMenuItemDTO("risotto", 123F, null, null, null));
        menu_item_mock.setDish_type(dish_type);

        Mockito.when(dish_type_service.get_dish_type_by_id(dish_type.getId())).thenReturn(dish_type);
        Mockito.when(menu_item_repository.save(Mockito.any(MenuItem.class))).thenReturn(menu_item_mock);

        CreateMenuItemDTO dto = new CreateMenuItemDTO(
                menu_item_mock.getName(), menu_item_mock.getPrice(), null, dish_type.getId(),null);
        ResponseMenuItemDTO result = menu_item_service.create_menu_item(dto);

        assertEquals("Risotto", result.name());
        assertEquals(123, result.price());
    }

    @Test
    void test_upload_when_image_is_null()
    {
        AttachmentService attachment_service = Mockito.mock(AttachmentService.class);
        ArgumentCaptor<MenuItem> menu_item = ArgumentCaptor.forClass(MenuItem.class);
        CreateMenuItemDTO menu_item_dto = new CreateMenuItemDTO(
                "abc", 12F, null, null, null);

        Mockito.when(menu_item_repository.save(Mockito.any(MenuItem.class))).thenReturn(new MenuItem());

        menu_item_service.create_menu_item(menu_item_dto);

        Mockito.verify(storage_service_factory, Mockito.never()).get_service(Mockito.any());
        Mockito.verify(context, Mockito.never()).getBean(AttachmentService.class);
        Mockito.verify(attachment_service, Mockito.never()).upload(Mockito.any());
        Mockito.verify(menu_item_repository).save(menu_item.capture());
        assertNull(menu_item.getValue().getAttachment());
    }

    @Test
    void test_upload_image()
    {
        StorageService storage_service = Mockito.mock(StorageService.class);
        Mockito.when(storage_service_factory.get_service(Mockito.anyString())).thenReturn(storage_service);
        AttachmentService attachment_service = Mockito.mock(AttachmentService.class);
        Mockito.when(context.getBean(AttachmentService.class, storage_service)).thenReturn(attachment_service);

        String EXPECTED_URL = "random_url";
        Mockito.when(attachment_service.upload(Mockito.any(MultipartAttachmentUpload.class))).thenReturn(EXPECTED_URL);

        MenuItem menu_item_mock = Mockito.mock(MenuItem.class);
        Mockito.when(menu_item_repository.save(Mockito.any(MenuItem.class))).thenReturn(menu_item_mock);

        CreateMenuItemDTO menu_item_dto = new CreateMenuItemDTO(
                "abc", 12F, null, UUID.randomUUID(), Mockito.mock(MultipartFile.class));
        menu_item_service.create_menu_item(menu_item_dto);

        ArgumentCaptor<MenuItem> menu_item_argument_captor = ArgumentCaptor.forClass(MenuItem.class);

        Mockito.verify(storage_service_factory).get_service(Mockito.any());
        Mockito.verify(context).getBean(AttachmentService.class, storage_service);
        Mockito.verify(attachment_service).upload(Mockito.any());
        Mockito.verify(menu_item_repository).save(menu_item_argument_captor.capture());
        assertEquals(EXPECTED_URL, menu_item_argument_captor.getValue().getAttachment().getUrl());
    }

}