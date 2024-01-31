package com.pickax.status.page.server.repository;

import com.pickax.status.page.server.domain.enumclass.UserStatus;
import com.pickax.status.page.server.domain.model.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.pickax.status.page.server.domain.model.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<User> getUser(String email, UserStatus status) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(user)
                        .where(
                                user.email.eq(email),
                                user.status.eq(status)
                        )
                        .fetchOne()
        );
    }
}
