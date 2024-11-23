package com.example.Angular.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Angular.Entity.Notif;
import com.example.Angular.repository.NotifRepository;

import java.util.List;

@Service
public class NotifService {
    @Autowired
    NotifRepository notifRepository;

    public List<Notif> getNewNotifs() {
        return notifRepository.findByRecent(true);
    }

    public void setSeen(List<Integer> notifs) {
        for (Integer id : notifs) {
            Notif notif = notifRepository.findById(id).get();
            notif.setRecent(false);
            notifRepository.save(notif);
        }

    }
}