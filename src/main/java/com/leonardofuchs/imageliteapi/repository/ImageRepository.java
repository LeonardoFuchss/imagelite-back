package com.leonardofuchs.imageliteapi.repository;

import com.leonardofuchs.imageliteapi.domain.entities.Image;
import com.leonardofuchs.imageliteapi.domain.enums.ImageExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import javax.swing.*;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {
    default List<Image> findByExtensionAndNameOrTagsLikes(ImageExtension extension, String query) {

        // SELECT * FROM IMAGE WHERE 1 = 1
        Specification<Image> conjunction = (root, q, criteriaBuilder) -> criteriaBuilder.conjunction(); // where 1 = 1
        Specification<Image> spec = Specification.where(conjunction); // Retorna todos os dados da entidade image

        if (extension != null) {
            Specification<Image> extensionEqual = (root, q, cb) -> cb.equal(root.get("extension"), extension); // Busca se a extension fornecida existe
            spec = spec.and(extensionEqual); // adiciona na spec principal
        }
        if (StringUtils.hasText(query)) { // Verifica se é nulo ou vazio.
            Specification<Image> nameLike = (root, q, cb) -> cb.like(cb.upper(root.get("name")), "%" + query.toUpperCase() + "%"); // Busca se o nome fornecido existe
            Specification<Image> tagsLike = (root, q, cb) -> cb.like(cb.upper(root.get("tags")), "%" + query.toUpperCase() + "%"); // Busca se a tag fornecida existe
            Specification<Image> nameOrTagsLike = Specification.anyOf(nameLike, tagsLike); // Especifica que qualquer um dos 2 pode ser buscado na query
            spec = spec.and(nameOrTagsLike); // Adiciona na spec principal
        }
        return findAll(spec); // Retorna spec com especificações ou sem especificações.
    }
}
