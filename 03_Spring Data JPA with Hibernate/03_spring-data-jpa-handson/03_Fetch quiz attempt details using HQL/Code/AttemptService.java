package com.cognizant.ormlearn.service;

import com.cognizant.ormlearn.entity.Attempt;

public interface AttemptService {
    Attempt getAttempt(int userId, int attemptId);
}