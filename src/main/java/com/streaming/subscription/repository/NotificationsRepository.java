package com.streaming.subscription.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.streaming.subscription.bean.jpa.NotificationEntity;

public interface NotificationsRepository extends PagingAndSortingRepository<NotificationEntity, Integer> {
}
