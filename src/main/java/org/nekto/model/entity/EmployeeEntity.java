package org.nekto.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employees")
public class EmployeeEntity extends BasedEntity<Long> {
    @Column(name = "name", length = 50)
    private String name;
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;
    @Column(name = "birth_day")
    private Date birthday;
    @Column(name = "comments", length = 100)
    private String comments;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    public String getName() {
        return this.name;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public Date getBirthday() {
        return this.birthday;
    }
    public String getComments() {
        return this.comments;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }
}
