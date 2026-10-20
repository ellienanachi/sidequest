package com.sidequest.transaction.repository;

import com.sidequest.transaction.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
