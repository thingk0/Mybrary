package com.mybrary.backend.global.jwt.repository;

import com.mybrary.backend.global.jwt.RefreshToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken,String> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
