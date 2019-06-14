package com.lambdaschool.zoos.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;
// manually imported, THIS GOES TO @Temporal(TIMESTAMP)

@MappedSuperclass
// this never gets used directly.
@EntityListeners(AuditingEntityListener.class)
//This works across all the entitys.
abstract class Auditable {
    //this class contains our audit fields that are going to be used across all other classes.
    // this has 3 fields

    @CreatedBy
    //this tells spring to fill in the data for these collumn's
    protected String createdBy;

    @CreatedDate
    @Temporal(TIMESTAMP)
    //this tells spring to fill in the data for these collumn's
    protected Date createdDate;

    @LastModifiedBy
    //this tells spring to fill in the data for these collumn's
    protected String lastModifiedBy;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    //this tells spring to fill in the data for these collumn's
    protected Date lastModifiedDate;

    // these are protected because they should only models should have access to them

    //this is done, we dont want or need anything else here
}
