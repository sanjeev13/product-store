package com.store.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CatalogueService {

    public List<String> getAllProductNames(){

        String[] name = {"abc","abcdef ghi","efrd","ab","abc"};
        List<String> list = Arrays.asList(name);

        return list;
    }
}
