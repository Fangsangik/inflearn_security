package com.example.springsecurity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SessionInfoService {
    //햔제 잡석한 사용자 정보
    private final SessionRegistry sessionRegistry;


    public void sessionInfo() {
        for(Object principal : sessionRegistry.getAllPrincipals()) {
            List<SessionInformation> sessions = sessionRegistry.getAllSessions(principal, false);
            for(SessionInformation sessionInformation : sessions) {
                System.out.println("사용자: " + principal + " | 세션ID: " + sessionInformation.getSessionId() +
                        " | 최종 요청 시간: " + sessionInformation.getLastRequest());
            }
        }

    }
}
