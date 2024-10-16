package com.mabis.services;

import com.mabis.exceptions.InvalidStorageServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class StorageServiceFactory
{
    private final HashMap<String, StorageService> services_map = new HashMap<>();

    @Autowired
    public StorageServiceFactory(Set<StorageService> services)
    {
        services.forEach( service -> services_map.put(service.get_service_name(), service) );
    }

    public StorageService get_service(String service)
    {
        StorageService storage_service = services_map.get(service);
        if (storage_service == null)
        {
            throw new InvalidStorageServiceException();
        }
        return storage_service;
    }
}
