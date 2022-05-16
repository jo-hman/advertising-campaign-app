package com.jochman.request;

import com.jochman.entity.Keyword;
import com.jochman.entity.Product;
import lombok.Getter;

import java.util.List;

@Getter
public class InitRequest {
    private List<Product> products;
    private List<Keyword> keywords;
    private Double userAccountBalance;
}
