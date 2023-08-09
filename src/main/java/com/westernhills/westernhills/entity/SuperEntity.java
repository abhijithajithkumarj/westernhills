package com.westernhills.westernhills.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class SuperEntity {


//    @Column(updatable = false)
//    private Date createdAt;
//
//    @UpdateTimestamp
//    private Date modifiedAt;


    private boolean activated = true;
    private boolean deleted = false;

//    private Date deletedAt;
//
//    private UUID deletedBy;

//    @PrePersist
//    protected void onCreate() {
//        if (createdAt == null) {
//            createdAt = new Date();
//        }
//    }
//
//    public String getFormattedCreatedAt() {
//        Date now = this.createdAt;
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        return formatter.format(now);
//    }



}

