package com.leonardofuchs.imageliteapi.domain.enums;

import lombok.Getter;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.http.MediaType;

import java.util.Arrays;

public enum ImageExtension {

    // Definindo o tipo de midia para cada extensao
    PNG(MediaType.IMAGE_PNG),
    JPEG(MediaType.IMAGE_JPEG),
    GIF(MediaType.IMAGE_GIF),
    ;


    @Getter
    private MediaType mediaType; // Instanciando o media type
    ImageExtension(MediaType mediaType) { // Construtor
        this.mediaType = mediaType;
    }
    public static ImageExtension valueof (MediaType mediaType){
        return Arrays.stream(values()).filter(imageExtension -> imageExtension.mediaType // filtrando as extenções da imagem de acordo com o tipo de midia fornecido
                .equals(mediaType))
                .findFirst()
                .orElse(null);

        // Explicação: O metodo retorna todos enumeradores da classe e compara o mediaType de cada enumerador com o mediaType que sera fornecido em parametro.
        // Busca a partir do primeiro (findFirst), caso não for encontrado, retorna null
    }
    public static ImageExtension ofName(String extension){ // Para comparar se a busca (extension) existe no enumerador e se não existir, retornar nulo.
        return Arrays.stream(values())
                .filter(imageExtension -> imageExtension.name()
                .equalsIgnoreCase(extension))
                .findFirst()
                .orElse(null);
        // Poderiamos usar o valueOf padrão do enum, porém se a extensão fosse vazia, iria dar erro no código. Então criamos esse método para comparar a extensão existente com a extensão do parâmetro e retornar nulo quando for vazia.
    }
}
