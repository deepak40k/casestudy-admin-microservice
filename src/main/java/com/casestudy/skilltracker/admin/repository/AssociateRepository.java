package com.casestudy.skilltracker.admin.repository;

import com.casestudy.skilltracker.admin.model.AssociateProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociateRepository extends MongoRepository<AssociateProfile, String> {

    @Query("{name: { $regex : ?0 } }")
    Page<AssociateProfile> findByName(String name, Pageable pageable);

    @Query("{\"skills\": { $elemMatch: {name : ?0,level: { $gt: 10 }}}}")
    Page<AssociateProfile> findBySkill(String skill, Pageable pageable);

    Page<AssociateProfile> findByAssociateId(String skill, Pageable pageable);

}