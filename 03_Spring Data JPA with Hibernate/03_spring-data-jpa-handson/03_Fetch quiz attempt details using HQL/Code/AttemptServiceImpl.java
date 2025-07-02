package com.cognizant.ormlearn.service.impl;

import com.cognizant.ormlearn.entity.Attempt;
import com.cognizant.ormlearn.repository.AttemptRepository;
import com.cognizant.ormlearn.service.AttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttemptServiceImpl implements AttemptService {

    @Autowired
    private AttemptRepository attemptRepository;

    @Override
    public Attempt getAttempt(int userId, int attemptId) {
        return attemptRepository.getAttempt(userId, attemptId);
    }
}