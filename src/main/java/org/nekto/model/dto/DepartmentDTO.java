package org.nekto.model.dto;

public class DepartmentDTO extends BaseDTO<Long> {
    private String departmentName;

    private String universityName;

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getUniversityName() {
        return universityName;
    }
}
