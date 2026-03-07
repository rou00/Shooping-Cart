package com.rou00.shopcart.model.dto;

import com.rou00.shopcart.model.entity.Product;
import lombok.Data;


import java.sql.Blob;

@Data
public class ImageDTO {

    private Long id;
    private String fileName;
    private String fileType;

    private Blob image;
    private String downloadUrl;

    private Product product;

}
