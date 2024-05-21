package com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.jpa;

import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessageEntityRepository  extends JpaRepository<MessageEntity, UUID> {

}
