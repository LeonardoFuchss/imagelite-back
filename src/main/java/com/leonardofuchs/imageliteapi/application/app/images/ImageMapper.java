package com.leonardofuchs.imageliteapi.application.app.images;

import com.leonardofuchs.imageliteapi.domain.entities.Image;
import com.leonardofuchs.imageliteapi.domain.enums.ImageExtension;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
public class ImageMapper { // Passando a lógica de builder da imagem para um mapeamento. Tudo que iremos receber no parametro do metodo, ira armazenar na variável image e salvá-la no Bando de dados.
    public Image mapToImage(MultipartFile file, String name, List<String> tags) throws IOException { // Método para receber os parâmetros do post

       return Image.builder()
                .name(name)
                .tags(String.join(",", tags)) // passando o array tags para um formato de String único e separando as tags por virgula.
                .size(file.getSize())
                .extension(ImageExtension.valueof(MediaType.valueOf(file.getContentType()))) // chamando o metodo valueof do imageExtension para comparação dos enumeradores (PNG, JPEG E GIF)
                // getContentType chama o tipo de midia do file para comparar
                .file(file.getBytes())
                .build();
    }
    public ImageDto imageToDto(Image image, String url){
        return ImageDto.builder()
                .url(url)
                .extension(image.getExtension().name())
                .name(image.getName())
                .size(image.getSize())
                .tags(image.getTags())
                .uploadDate(image.getUploadDate())
                .build();
    }
}
