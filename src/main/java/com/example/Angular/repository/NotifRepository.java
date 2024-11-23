package com.example.Angular.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Angular.Entity.Notif;
import java.util.List;

public interface NotifRepository extends JpaRepository<Notif, Integer> {

    List<Notif> findByRecent(boolean recent);
}