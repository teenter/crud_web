package org.nekto.model.dto;

import java.util.Objects;

public class BaseDTO<ID> {
    protected ID id;

    public void setId(ID id) {
        this.id = id;
    }

    public ID getId() {
        return this.id;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof BaseDTO<?> && ((BaseDTO<?>) o).id.equals(id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
