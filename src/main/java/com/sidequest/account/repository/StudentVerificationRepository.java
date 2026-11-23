package com.sidequest.account.repository;

import com.sidequest.account.entity.StudentVerification;
import com.sidequest.account.entity.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentVerificationRepository extends JpaRepository<StudentVerification, Long> {

	// The admin's queue, oldest first. Spring writes the query from the method name.
	List<StudentVerification> findByStatusOrderBySubmittedAtAsc(VerificationStatus status);

	// "Does this user already have one waiting?" Used to stop double submissions.
	boolean existsByUserIdAndStatus(Long userId, VerificationStatus status);
}
