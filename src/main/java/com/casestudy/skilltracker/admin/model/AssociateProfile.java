package com.casestudy.skilltracker.admin.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;


@Getter
@Valid
@Builder
@ToString
@Document(collection = "associateProfile")
public class AssociateProfile implements Serializable {
    @Id
    private String userId;
    private String associateId;
    private String name;
    private String email;
    private String mobile;
    private List<Skill> skills;
}