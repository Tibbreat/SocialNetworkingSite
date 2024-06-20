package org.example.socialnetworkingsite.repository;

import org.example.socialnetworkingsite.entites.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepo extends JpaRepository<Friend, Integer> {
}
