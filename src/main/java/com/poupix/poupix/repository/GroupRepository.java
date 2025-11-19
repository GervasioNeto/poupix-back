package com.poupix.poupix.repository;

import com.poupix.poupix.entity.Group;
import com.poupix.poupix.entity.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByGroupMembersUserId(Long userId);
    Group findByUuid(String uuid);
    List<Group> findByNameContainingIgnoreCase(String name);
    List<Group> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
