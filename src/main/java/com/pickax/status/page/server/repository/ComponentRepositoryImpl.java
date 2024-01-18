package com.pickax.status.page.server.repository;

import static com.pickax.status.page.server.domain.model.QComponent.*;

import java.util.List;

import com.pickax.status.page.server.domain.enumclass.ComponentStatus;
import org.springframework.stereotype.Repository;

import com.pickax.status.page.server.dto.reseponse.component.ComponentActiveResponseDto;
import com.pickax.status.page.server.dto.reseponse.component.ComponentResponseDto;
import com.pickax.status.page.server.dto.reseponse.component.QComponentActiveResponseDto;
import com.pickax.status.page.server.dto.reseponse.component.QComponentResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ComponentRepositoryImpl implements ComponentRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	public List<ComponentActiveResponseDto> getComponentActiveResponses(Long siteId) {
		return queryFactory
			.select(new QComponentActiveResponseDto(
				component.id,
				component.name,
				component.description,
				component.componentStatus
			)
			)
			.from(component)
			.where(
				component.site.id.eq(siteId),
				component.isActive.eq(true)
			)
			.fetch();
	}

	@Override
	public List<ComponentResponseDto> getComponentResponses(Long siteId) {
		return queryFactory
			.select(new QComponentResponseDto(
				component.id,
				component.name,
				component.description,
				component.componentStatus,
				component.frequency,
				component.isActive
			)).from(component)
			.where(
				component.site.id.eq(siteId)
			)
			.fetch();
	}

	@Override
	public void updateComponentStatus(Long componentId, ComponentStatus componentStatus) {
		queryFactory
				.update(component)
				.set(component.componentStatus, componentStatus)
				.where(component.id.eq(componentId))
				.execute();
	}
}
