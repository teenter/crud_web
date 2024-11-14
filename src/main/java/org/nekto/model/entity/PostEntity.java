package org.nekto.model.entity;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "posts")
public class PostEntity extends BasedEntity<Long> {

    @Column(name="post_name")
    private String postName;

    @OneToMany(targetEntity = EmployeeEntity.class, mappedBy = "post", cascade = CascadeType.ALL)
    private Set<EmployeeEntity> employees;

    public void setPostName(String name) {
        this.postName = name;
    }

    public String getPostName() {
        return postName;
    }
}
