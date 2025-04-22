package com.example.trainning.point.configuration;

import com.example.trainning.point.dto.request.lockmark.LockMarkDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LockMarkConfig {
    private final Set<LockMarkDTO> lockMarkConfigs = ConcurrentHashMap.newKeySet();
    public void createLock(Long semesterId, String userId){
        LockMarkDTO lockMarkDTO = LockMarkDTO.builder()
                .semesterId(semesterId)
                .userId(userId)
                .build();

        lockMarkConfigs.add(lockMarkDTO);
    }

    public void unLock(Long semesterId, String userId){
        LockMarkDTO lockMarkDTO = LockMarkDTO.builder()
                .semesterId(semesterId)
                .userId(userId)
                .build();
        lockMarkConfigs.remove(lockMarkDTO);
    }

    public boolean getLock(Long semesterId, String userId){
        for (var it: lockMarkConfigs){
            if(it.getSemesterId().equals(semesterId) && it.getUserId().equals(userId))
                return true;
        }
        return false;
    }
}
