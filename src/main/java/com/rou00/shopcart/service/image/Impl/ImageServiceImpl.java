package com.rou00.shopcart.service.image.Impl;

import com.rou00.shopcart.exceptions.ResourceNotFound;
import com.rou00.shopcart.model.dto.ImageDTO;
import com.rou00.shopcart.model.dto.ProductDTO;
import com.rou00.shopcart.model.entity.Category;
import com.rou00.shopcart.model.entity.Image;
import com.rou00.shopcart.model.entity.Product;
import com.rou00.shopcart.repository.ImageRepository;
import com.rou00.shopcart.service.image.ImageService;
import com.rou00.shopcart.service.product.Impl.ProductServiceImpl;
import com.rou00.shopcart.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ProductServiceImpl productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(()-> new ResourceNotFound("No Image found with this Id "+id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,
                ()-> {
             throw new ResourceNotFound("Image Want to Delete not Found! : "+id);
                });
    }

    @Override
    public List<ImageDTO> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDTO> imageDtos = new ArrayList<>();
        for(MultipartFile file : files){
            try{
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";

                String downloadUrl = buildDownloadUrl+image.getId();
                image.setDownloadUrl(downloadUrl);

                Image savedImage = imageRepository.save(image);
                savedImage.setDownloadUrl(buildDownloadUrl+savedImage.getId());

                imageRepository.save(savedImage);

                ImageDTO imageDto = new ImageDTO();
                imageDto.setId(savedImage.getId());
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());

                imageDtos.add(imageDto);

            }catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return imageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId){
        Image image = getImageById(imageId);
       try {
           image.setFileName(file.getOriginalFilename());
           image.setImage(new SerialBlob(file.getBytes()));
           imageRepository.save(image);
       }catch (Exception e){
           throw new RuntimeException(e.getMessage());
       }
    }

}
