package com.anthonny.springcloud.msvc.items.services;

import com.anthonny.springcloud.msvc.items.clients.ProductFeignClient;
import com.anthonny.springcloud.msvc.items.models.Item;
import com.anthonny.springcloud.msvc.items.models.Product;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import feign.FeignException;

@Service
public class ItemServiceFeign implements ItemService {

    private final ProductFeignClient client;

    public ItemServiceFeign(ProductFeignClient productFeignClient) {
        this.client = productFeignClient;
    }

    @Override
    public List<Item> findAll() {
        Random random = new Random();

        return client.findAll().stream()
                .map(product -> new Item(product, random.nextInt(10) + 1))
                .toList();
    }

    @Override
    public Optional<Item> findById(Long id) {
        try {
            Product product = client.findById(id);
            return Optional.of(new Item(product, new Random().nextInt(10) + 1));
        } catch (FeignException e) {
            return Optional.empty();
        }
    }
}
