package com.leonardofuchs.imageliteapi.application.app.images;

import com.leonardofuchs.imageliteapi.domain.entities.Image;
import com.leonardofuchs.imageliteapi.domain.enums.ImageExtension;
import com.leonardofuchs.imageliteapi.repository.ImageRepository;
import com.leonardofuchs.imageliteapi.domain.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImplementation implements ImageService {

    private final ImageRepository imageRepository;
    @Override
    @Transactional
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public Optional<Image> getImage(String id) {
        return imageRepository.findById(id);
    }

    @Override
    public List<Image> search(ImageExtension extension, String query) {
        return imageRepository.findByExtensionAndNameOrTagsLikes(extension, query);
    }

    @Override
    public Optional<Image> delete(String id) { // Quando queremos encontrar, será sempr Optional.
        Optional<Image> deletedIdFind = imageRepository.findById(id); // Para deletar, é preciso encontrar a imagem primeiro.
        imageRepository.deleteById(id);
        return deletedIdFind;
    }


}
