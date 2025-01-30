package com.example.Angular.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Angular.Entity.Notif_CHEF_SERVICE;

public interface NotifChefServiceRepository extends JpaRepository<Notif_CHEF_SERVICE, Integer> {

    List<Notif_CHEF_SERVICE> findByRecent(boolean recent);
}
