package com.casestudy.skilltracker.admin.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Valid
@Setter
@Getter
@NoArgsConstructor
public class SkillSet implements Serializable {
    @Valid
    private List<Skill> technical=new ArrayList<>();
    @Valid
    private List<Skill> nonTechnical=new ArrayList<>();

   public SkillSet(List<Skill> technical, List<Skill> nonTechnical)
    {
        Collections.sort(nonTechnical);
        Collections.sort(technical);
        this.nonTechnical = nonTechnical;
        this.technical = technical;
    }

}
