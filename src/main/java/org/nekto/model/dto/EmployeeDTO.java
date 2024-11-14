package org.nekto.model.dto;

import java.util.Date;

public class EmployeeDTO extends BaseDTO<Long> {
    private String name;
    private String phoneNumber;
    private Date birthday;
    private String comments;
    private DepartmentDTO department;
    private PostDTO post;
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
    public DepartmentDTO getDepartment() {
        return department;
    }
    public PostDTO getPost() {
        return post;
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

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public void setPost(PostDTO post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + this.id + ", name='" + this.name + '\'' +'}';
    }

}
