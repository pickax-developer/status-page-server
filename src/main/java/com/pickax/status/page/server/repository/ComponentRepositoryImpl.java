package com.pickax.status.page.server.repository;

import static com.pickax.status.page.server.domain.model.QComponent.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pickax.status.page.server.dto.reseponse.ComponentResponseDto;
import com.pickax.status.page.server.dto.reseponse.QComponentResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ComponentRepositoryImpl implements ComponentRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<ComponentResponseDto> getComponents(Long siteId, boolean isActive) {
		return queryFactory
			.select(new QComponentResponseDto(
				component.id,
				component.name,
				component.description,
				component.componentStatus
			)
			)
			.from(component)
			.where(
				component.site.id.eq(siteId),
				component.isActive.eq(isActive)
			)
			.fetch();
	}
}
