package com.example.Angular.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Angular.Entity.Notif_DPR_SAF;
import java.util.List;

public interface NotifRepository extends JpaRepository<Notif_DPR_SAF, Integer> {

    List<Notif_DPR_SAF> findByRecent(boolean recent);
}