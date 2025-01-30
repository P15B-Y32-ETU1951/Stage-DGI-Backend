package com.example.Angular.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Angular.Entity.Notif_CHEF_SERVICE;
import com.example.Angular.repository.NotifChefServiceRepository;
import com.example.Angular.repository.NotifRepository;

import java.util.List;

@Service
public class NotifChefServiceService {
    @Autowired
    NotifChefServiceRepository notifRepository;

    public List<Notif_CHEF_SERVICE> getNewNotifs() {
        return notifRepository.findByRecent(true);
    }

    public void setSeen(List<Integer> notifs) {
        for (Integer id : notifs) {
            Notif_CHEF_SERVICE notif = notifRepository.findById(id).get();
            notif.setRecent(false);
            notifRepository.save(notif);
        }

    }
}