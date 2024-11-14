package org.nekto.model.entity;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="departments")
public class DepartmentEntity extends BasedEntity<Long> {

    @OneToMany(targetEntity = EmployeeEntity.class, mappedBy = "department", cascade = CascadeType.ALL)
    private Set<EmployeeEntity> departmentEmployees;
    @Column(name = "department_name", length = 50)
    private String departmentName;

    @Column(name = "university_name", length = 50)
    private String universityName;

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setUniversity(String name) {
        this.universityName = name;
    }

    public String getUniversity() {
        return universityName;
    }
}
