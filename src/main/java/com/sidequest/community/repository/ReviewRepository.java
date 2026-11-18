package com.sidequest.community.repository;

import com.sidequest.community.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	// Reviews someone RECEIVED, newest first.
	// The ERD has no "reviewee" column: the person reviewed is the other side of the acceptance.
	// So: the user was in the quest (the adventurer or the quest giver), and someone else wrote the review.
	// "join fetch" loads the reviewer, acceptance and quest in the same query,
	// so the service can read them without extra queries.
	@Query("""
			select r from Review r
			join fetch r.reviewer
			join fetch r.acceptance a
			join fetch a.quest q
			where r.reviewer.id <> :userId
			  and (a.adventurer.id = :userId or q.poster.id = :userId)
			order by r.createdAt desc, r.id desc
			""")
	List<Review> findReceivedBy(@Param("userId") Long userId);
}
