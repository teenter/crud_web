package org.nekto.controller;

import org.nekto.model.dto.DepartmentDTO;
import org.nekto.model.dto.EmployeeDTO;
import org.nekto.model.dto.PostDTO;
import org.nekto.model.entity.DepartmentEntity;
import org.nekto.model.entity.EmployeeEntity;
import org.nekto.model.entity.PostEntity;
import org.nekto.repository.DepartmentRepository;
import org.nekto.repository.EmployeeRepository;
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
@RequestMapping("employee")
public class EmployeeController implements ICrudController<EmployeeDTO, Long> {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    @CrossOrigin
    public List<EmployeeDTO> getAll() {
        logger.info("EmployeeController.getAll: Getting all employees");

        List<EmployeeEntity> entityList = employeeRepository.findAll();
        List<EmployeeDTO> dtoList = new ArrayList<>(entityList.size());

        for (EmployeeEntity entity: entityList) {
            dtoList.add(buildDto(entity));
        }
        return dtoList;
    }

    private EmployeeDTO buildDto(EmployeeEntity entity) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setBirthday(entity.getBirthday());
        dto.setComments(entity.getComments());
        dto.setPhoneNumber(entity.getPhoneNumber());
        DepartmentEntity dep = entity.getDepartment();
        if (dep != null) {
            DepartmentDTO temp = new DepartmentDTO();
            temp.setUniversityName(dep.getUniversity());
            temp.setDepartmentName(dep.getDepartmentName());
            temp.setId(dep.getId());
            dto.setDepartment(temp);
        }
        PostEntity post = entity.getPost();
        if (post != null) {
            PostDTO postDTO = new PostDTO();
            postDTO.setPostName(post.getPostName());
            postDTO.setId(post.getId());
            dto.setPost(postDTO);
        }

        return dto;
    }

    @Override
    @CrossOrigin
    public EmployeeDTO getById(Long id) {
        logger.info("EmployeeController.getById: Getting employee with ID {}", id);
        EmployeeDTO dto = null;
        Optional<EmployeeEntity> preloadOption = employeeRepository.findById(id);

        if (preloadOption.isPresent()) {
            dto = buildDto(preloadOption.get());
        }
        return dto;
    }

    @Override
    @CrossOrigin
    public void deleteById(Long id) {
        logger.info("EmployeeController.deleteById: Deleting Employee with ID {}", id);
        employeeRepository.deleteById(id);
    }

    @Override
    @CrossOrigin
    public EmployeeDTO saveOrUpdate(@RequestBody EmployeeDTO dto) {
        logger.info("EmployeeController.saveOrUpdate: Performing operation of saving or updating id: {} name: {}", dto.getId(), dto.getName());

        EmployeeEntity preload;

        if (dto.getId() == null) {
            preload = new EmployeeEntity();
        }
        else {
            Optional<EmployeeEntity> preloadOptional = employeeRepository.findById(dto.getId());
            preload = preloadOptional.orElseGet(EmployeeEntity::new);
        }

        preload.setName(dto.getName());
        preload.setComments(dto.getComments());
        preload.setBirthday(dto.getBirthday());
        preload.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getDepartment() != null && dto.getDepartment().getId() != null) {
            Optional<DepartmentEntity> temp = departmentRepository.findById(dto.getDepartment().getId());
            if (temp.isPresent()) {
                logger.info("setDepartment");
                logger.info("Department is {}", temp.get().getDepartmentName());
                preload.setDepartment(temp.get());
            }
        }
        if (dto.getPost() != null && dto.getPost().getId() != null) {
            Optional<PostEntity> temp = postRepository.findById(dto.getPost().getId());
            if (temp.isPresent()) {
                logger.info("setPost");
                PostEntity pst = temp.get();
                logger.info("Post is {}", pst.getPostName());
                preload.setPost(temp.get());

            }
        }

        preload = employeeRepository.save(preload);

        dto.setId(preload.getId());
        return dto;
    }
}
