package com.wafflestudio.seminar.domain.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity(
        // TODO why open var?
        @CreatedDate
        open var createdAt: LocalDateTime? = null,

        @LastModifiedDate
        open var updatedAt: LocalDateTime? = null
)
