package com.leonardofuchs.imageliteapi.domain.entities;

import com.leonardofuchs.imageliteapi.domain.enums.ImageExtension;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_image")
@EntityListeners(AuditingEntityListener.class) // Para funcionar o CreatedDate que é para gerar a data e hora que foi salvo no banco de dados.
@Data // Gera os getters and setters e os equals and hashCode.
@NoArgsConstructor // Construtos sem argumentos
@AllArgsConstructor // Construtor com todos atributos e necessário para o builder!
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column
    private String name;
    @Column
    private Long size;
    @Column
    @Enumerated(EnumType.STRING) // qual o tipo de imagem (jpg, png etc..)
    private ImageExtension extension;
    @CreatedDate // data que foi salvo no db
    private LocalDateTime uploadDate;
    @Column
    private String tags;
    @Column
    @Lob // dizendo que é um arquivo
    private byte[] file;

    public String getFileName() {

        return getName().concat(".").concat(getExtension().name()); // Retorna a forma que vai ser disponibilizado o arquivo.
        // retorna o nome da image contatenada com .
        // retorna o tipo de extensão da imagem
    }

}
