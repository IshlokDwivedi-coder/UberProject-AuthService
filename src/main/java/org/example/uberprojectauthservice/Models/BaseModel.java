package org.example.uberprojectauthservice.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseModel {

    @Id // This annotation make the id property a primary key of our table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Identity means auto_Increment
    // by default primary key chahiye to @GeneratedValue(strategy = GenerationType.something jo tummhe chahiye lagate hain
    private Long id;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)  // this annotaion tells spring about the formats of Date object to be stored i.e. Date/Time ? Timestamp
    @CreatedDate    // This annotation tells spring only handle it for object creation
    protected Date createdAt;


    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate   // This annotation tells spring only handle it for object update
    protected Date updatedAt;
}
