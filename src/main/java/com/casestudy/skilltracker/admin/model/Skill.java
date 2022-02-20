package com.casestudy.skilltracker.admin.model;

import com.casestudy.skilltracker.admin.validator.ValidSkill;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Setter
@Getter
@Valid
@ToString
public class Skill implements Serializable, Comparable<Skill> {
    static final long serialVersionUID = 1L;
    @NotNull
    @ValidSkill
    private String name;
    @NotNull
    @Min(value=0, message = "level should be between 0 and 20")
    @Max(value=20,message = "level should be between 0 and 20")
    private Integer level;
    @JsonIgnore
    private String type;
    @Override
    public int compareTo(Skill o) {
        return o.getLevel().compareTo( this.level);
    }
}
