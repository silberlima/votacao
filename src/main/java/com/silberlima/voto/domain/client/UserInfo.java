package com.silberlima.voto.domain.client;

import lombok.Data;

@Data
public class UserInfo {
    private String status;

    public boolean isAbleToVote() {
        return "ABLE_TO_VOTE".equals(status);
    }
}
