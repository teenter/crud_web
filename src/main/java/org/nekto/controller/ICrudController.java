package org.nekto.controller;

import org.nekto.model.dto.BaseDTO;
import org.springframework.web.bind.annotation.*;
import java.util.List;


public interface ICrudController<D extends BaseDTO<I>, I> {
    @GetMapping
    List<D> getAll();

    @GetMapping("{id}")
    D getById(@PathVariable("id") I id);

    @DeleteMapping("{id}")
    void deleteById(@PathVariable("id") I id);

    @PostMapping
    D saveOrUpdate(@RequestBody D dto);
}
