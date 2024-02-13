package com.mybrary.backend.global.util;

import java.security.Principal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StompPrincipal implements Principal {

    private String email;

    @Override
    public String getName() {
        return this.email;
    }
}