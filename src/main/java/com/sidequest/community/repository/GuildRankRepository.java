package com.sidequest.community.repository;

import com.sidequest.community.entity.GuildRank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuildRankRepository extends JpaRepository<GuildRank, Long> {

	Optional<GuildRank> findByUserId(Long userId);
}
