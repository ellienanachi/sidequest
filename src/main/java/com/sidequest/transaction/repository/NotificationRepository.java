package com.sidequest.transaction.repository;

import com.sidequest.transaction.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
