package com.cydeo.entity;

import com.cydeo.entity.common.UserPrincipal;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Component
public class BaseEntityListener extends AuditingEntityListener {

    @PrePersist
    public void onPrePersist(BaseEntity baseEntity){

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleKeycloakAccount details = (SimpleKeycloakAccount) authentication.getDetails();
        String username = details.getKeycloakSecurityContext().getToken().getPreferredUsername();


        baseEntity.insertDateTime = LocalDateTime.now();
        baseEntity.lastUpdateDateTime = LocalDateTime.now();
        baseEntity.insertUserId = 1L;
        baseEntity.lastUpdateUserId = 1L;

//        if(authentication != null && !username.equals("anonymousUser")){
//            Object principal = authentication.getPrincipal();
//            baseEntity.insertUserId = ((UserPrincipal) principal).getId();
//            baseEntity.lastUpdateUserId =((UserPrincipal) principal).getId();
//        }


    }

    @PreUpdate
    public void onPreUpdate(BaseEntity baseEntity){

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleKeycloakAccount details = (SimpleKeycloakAccount) authentication.getDetails();
        String username = details.getKeycloakSecurityContext().getToken().getPreferredUsername();

        baseEntity.lastUpdateDateTime = LocalDateTime.now();
         baseEntity.lastUpdateUserId = 1L;

//        if(authentication != null && !username.equals("anonymousUser")){
//            Object principal = authentication.getPrincipal();
//            baseEntity.lastUpdateUserId =((UserPrincipal) principal).getId();
//        }
    }
}
