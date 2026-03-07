package com.rou00.shopcart.controller;

import com.rou00.shopcart.model.dto.ImageDTO;
import com.rou00.shopcart.model.entity.Image;
import com.rou00.shopcart.service.image.Impl.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {

    private final ImageServiceImpl imageService;

    @PostMapping("/upload")
    public ResponseEntity<List<ImageDTO>> savedImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId){
        try {
            List<ImageDTO> imageDtos = imageService.saveImage(files,productId);
            return new ResponseEntity<>(imageDtos,HttpStatus.CREATED);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1,(int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+image.getFileName()+"\"").body(resource);
    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ImageDTO> updatetmage(@PathVariable Long imageId, @RequestBody MultipartFile file){
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null){
                imageService.updateImage(file,imageId);
                return new ResponseEntity<>(null,HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.CREATED);

        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ImageDTO> deleteImage(@PathVariable Long imageId){
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null){
                imageService.deleteImageById(imageId);
                return new ResponseEntity<>(null,HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.ACCEPTED);

        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
