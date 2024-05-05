package com.leonardofuchs.imageliteapi.domain.service;

import com.leonardofuchs.imageliteapi.domain.entities.Image;
import com.leonardofuchs.imageliteapi.domain.enums.ImageExtension;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    Image save(Image image); // Metodo abstrato
    Optional<Image> getImage(String id);
    List<Image> search(ImageExtension extension, String query);
    Optional<Image> delete (String id);

}
