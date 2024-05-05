package com.leonardofuchs.imageliteapi.application.app.images;

import com.leonardofuchs.imageliteapi.domain.entities.Image;
import com.leonardofuchs.imageliteapi.domain.enums.ImageExtension;
import com.leonardofuchs.imageliteapi.domain.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/vi/images")
@Slf4j // Anotação para os Logs
@RequiredArgsConstructor
public class ImagesController {

    private final ImageService service;
    private final ImageMapper mapper;

    @PostMapping
    public ResponseEntity save(

            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("tags") List<String> tags

    ) throws IOException {
        log.info("Imagem recebida: name: {}, size: {}", file.getOriginalFilename(), file.getSize());

        Image image = mapper.mapToImage(file, name, tags); // Chamando o metodo do mapper (Aonde está a lógica)
        Image savedImage = service.save(image); // Salvando no banco de dados e armazenando na variável

        URI imageUri = buildImageUri(savedImage);
        return ResponseEntity.created(imageUri).build(); // RETORNA UM CREATED
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) { // Metodo para acessar a imagem
        var possibleImage = service.getImage(id);

        if (possibleImage.isEmpty()) { // Se a imagem não existir
            return ResponseEntity.notFound().build(); // Terá este retorno
        }
        var image = possibleImage.get();
        HttpHeaders headers = new HttpHeaders(); // Precisamos retornar alguns headers para que o browser saiba renderizar a imagem.
        headers.setContentType(image.getExtension().getMediaType()); // O tipo de midia que nossa imagem irá retornar (jpeg, png, git)
        headers.setContentLength(image.getSize());
        headers.setContentDispositionFormData("inline; fileName =\"" + image.getFileName() + "\"", image.getFileName()); // Retorna a forma que vai ser disponibilizado o arquivo.

        return new ResponseEntity<>(image.getFile(), headers, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<byte[]> findToDelete(@PathVariable String id) {
        var imageFind = service.delete(id);

        if (imageFind.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping // Não terá nada na url, será buscado direto na raiz!
    public ResponseEntity<List<ImageDto>> search(

            @RequestParam(value = "extension", required = false, defaultValue = "") String extension,  // Passa uma extensão no parâmetro no formato String. Vamos converter ao chamar o método do service
            @RequestParam(value = "query", required = false) String query) { // Passa uma query no parâmetro

        var result = service.search(ImageExtension.ofName(extension), query); // Chama o método do service passando os parâmetros

        var images = result.stream().map(image -> { // Lógica para retornar um dto
            var url = buildImageUri(image); // cria uma url com o resultado da busca
            return mapper.imageToDto(image, url.toString()); // retorna dto mapeado com seus atributos e passa a image e url criada

        }).collect(Collectors.toList()); // Passando essa pesquisa para uma lista

        return ResponseEntity.ok(images);
    }

    private URI buildImageUri(Image image) { // ESSE MÉTODO PEGA UMA IMAGEM POR PARÂMETRO
        String imagePath = "/" + image.getId(); // ARMAZENA O ID DESSA IMAGEM

        return ServletUriComponentsBuilder // ESSE METODO PEGA O REQUEST PRINCIPAL DA CLASSE (/vi/images) e adiciona o imagePath.
                .fromCurrentRequestUri()
                .path(imagePath)
                .build().toUri();

        // A partir disso foi criado (CREATED) uma nova url com o id da imagem salvada junto com o caminho do resquestMapping
    }
}
