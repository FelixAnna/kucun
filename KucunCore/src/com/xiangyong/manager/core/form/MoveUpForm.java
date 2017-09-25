package com.xiangyong.manager.core.form;

import javax.validation.constraints.NotNull;

public class MoveUpForm {

    @NotNull
    private Long id;

    @NotNull
    private Long prevId;

    public MoveUpForm() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrevId() {
        return this.prevId;
    }

    public void setPrevId(Long prevId) {
        this.prevId = prevId;
    }
}
