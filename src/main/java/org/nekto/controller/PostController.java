package org.nekto.controller;

import org.nekto.model.dto.PostDTO;
import org.nekto.model.entity.PostEntity;
import org.nekto.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("posts")
public class PostController implements ICrudController<PostDTO, Long> {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostRepository postRepository;

    public PostDTO buildDto(PostEntity entity) {
        PostDTO dto = new PostDTO();
        dto.setId(entity.getId());
        dto.setPostName(entity.getPostName());
        return dto;
    }

    @Override
    public List<PostDTO> getAll() {
        logger.info("EmployeeController.getAll: Getting all employees");
        List<PostEntity> entityList = postRepository.findAll();
        List<PostDTO> dtoList = new ArrayList<>(entityList.size());

        for (PostEntity entity: entityList) {
            dtoList.add(buildDto(entity));
        }
        return dtoList;
    }

    @Override
    public PostDTO getById(Long id) {
        logger.info("EmployeeController.getById: Getting employee with ID {}", id);
        PostDTO dto = null;

        Optional<PostEntity> preloadOptional = postRepository.findById(id);
        if (preloadOptional.isPresent()) {
            dto = buildDto(preloadOptional.get());
        }
        return dto;
    }

    @Override
    public void deleteById(Long id) {
        logger.info("EmployeeController.deleteById: Deleting Employee with ID {}", id);
        postRepository.deleteById(id);
    }

    @Override
    public PostDTO saveOrUpdate(@RequestBody PostDTO dto) {
        logger.info("EmployeeController.saveOrUpdate: Performing operation of saving or updating id: {} name: {}", dto.getId(), dto.getPostName());

        PostEntity preload;

        if (dto.getId() == null) {
            preload = new PostEntity();
        } else {
            Optional<PostEntity> preloadOptional = postRepository.findById(dto.getId());
            preload = preloadOptional.orElseGet(PostEntity::new);
        }

        preload.setPostName(dto.getPostName());

        preload = postRepository.save(preload);

        dto.setId(preload.getId());

        return dto;
    }
}
