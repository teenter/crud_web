package org.nekto.model.dto;

public class PostDTO extends BaseDTO<Long> {
    private String postName;
    public String getPostName() {
        return this.postName;
    }
    public void setPostName(String postName) {
        this.postName = postName;
    }
}
