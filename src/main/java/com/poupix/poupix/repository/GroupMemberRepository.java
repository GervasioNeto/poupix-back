package com.poupix.poupix.repository;

import com.poupix.poupix.entity.Group;
import com.poupix.poupix.entity.GroupMember;
import com.poupix.poupix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    List<GroupMember> findByGroup(Group group);

//    List<GroupMember> findByUserId(Long userId);

    boolean existsByGroupAndUser(Group group, User user);

    GroupMember findByGroupAndUser(Group group, User user);

    List<GroupMember> findByUser_Id(Long userId);
}
