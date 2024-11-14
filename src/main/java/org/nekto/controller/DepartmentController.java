package org.nekto.controller;

import org.nekto.model.dto.DepartmentDTO;
import org.nekto.model.dto.PostDTO;
import org.nekto.model.entity.DepartmentEntity;
import org.nekto.model.entity.PostEntity;
import org.nekto.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("departments")
public class DepartmentController implements ICrudController<DepartmentDTO, Long> {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    public DepartmentDTO buildDto(DepartmentEntity entity) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(entity.getId());
        dto.setDepartmentName(entity.getDepartmentName());
        dto.setUniversityName(entity.getUniversity());
        return dto;
    }

    public DepartmentEntity buildEntity(DepartmentDTO dto) {
        DepartmentEntity entity = new DepartmentEntity();
        entity.setUniversity(dto.getUniversityName());
        entity.setDepartmentName(dto.getDepartmentName());
        entity.setId(dto.getId());
        return entity;
    }

    @Override
    @CrossOrigin
    public List<DepartmentDTO> getAll() {
        logger.info("DepartmentController.getAll: Getting all Department");
        List<DepartmentEntity> entityList = departmentRepository.findAll();
        List<DepartmentDTO> dtoList = new ArrayList<>(entityList.size());

        for (DepartmentEntity entity: entityList) {
            dtoList.add(buildDto(entity));
        }
        return dtoList;
    }

    @Override
    @CrossOrigin
    public DepartmentDTO getById(Long id) {
        logger.info("DepartmentController.getById: Getting Department with ID {}", id);
        DepartmentDTO dto = null;

        Optional<DepartmentEntity> preloadOptional = departmentRepository.findById(id);
        if (preloadOptional.isPresent()) {
            dto = buildDto(preloadOptional.get());
        }
        return dto;
    }

    @Override
    @CrossOrigin
    public void deleteById(Long id) {
        logger.info("DepartmentController.deleteById: Deleting Department with ID {}", id);
        departmentRepository.deleteById(id);
    }

    @Override
    @CrossOrigin
    public DepartmentDTO saveOrUpdate(@RequestBody DepartmentDTO dto) {
        logger.info("DepartmentController.saveOrUpdate: Performing operation of saving or updating id: {} name: {}", dto.getId(), dto.getDepartmentName());

        DepartmentEntity preload;

        if (dto.getId() == null) {
            preload = new DepartmentEntity();
        } else {
            Optional<DepartmentEntity> preloadOptional = departmentRepository.findById(dto.getId());
            preload = preloadOptional.orElseGet(DepartmentEntity::new);
        }

        preload.setDepartmentName(dto.getDepartmentName());
        preload.setUniversity(dto.getUniversityName());

        preload = departmentRepository.save(preload);

        dto.setId(preload.getId());

        return dto;
    }
}
