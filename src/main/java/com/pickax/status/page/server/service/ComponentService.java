package com.pickax.status.page.server.service;

import com.pickax.status.page.server.common.exception.CustomException;
import com.pickax.status.page.server.common.exception.ErrorCode;
import com.pickax.status.page.server.domain.enumclass.ComponentStatus;
import com.pickax.status.page.server.domain.model.Component;
import com.pickax.status.page.server.domain.model.Site;
import com.pickax.status.page.server.dto.request.ComponentCreateRequestDto;
import com.pickax.status.page.server.dto.reseponse.component.ComponentActiveListResponseDto;
import com.pickax.status.page.server.dto.reseponse.component.ComponentResponseDto;
import com.pickax.status.page.server.repository.ComponentRepository;
import com.pickax.status.page.server.repository.SiteRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pickax.status.page.server.dto.reseponse.component.ComponentActiveResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ComponentService {

    private final SiteRepository siteRepository;
    private final ComponentRepository componentRepository;

    @Transactional
    public void createComponent(Long siteId, ComponentCreateRequestDto request, Long loggedInUserId) {
        Site site = this.siteRepository.findById(siteId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사이트 입니다."));
        // 로그인 기능이 생기기 전까지 주석처리 합니다.
//        if (!site.ownerEqualsBy(loggedInUserId)) {
//            throw new IllegalArgumentException("해당 사이트의 정당한 소유자가 아닙니다.");
//        }

        Component component = Component.create(request.getName(),
                request.getDescription(),
                ComponentStatus.NONE,
                request.getFrequency(),
                request.getIsActive(),
                site
        );

        this.componentRepository.save(component);
    }

	@Transactional(readOnly = true)
	public ComponentActiveListResponseDto getActiveComponents(Long siteId) {
		siteRepository.findById(siteId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SITE));
		List<ComponentActiveResponseDto> componentActiveResponses = componentRepository.getComponentActiveResponses(siteId);

		final List<ComponentActiveResponseDto> notNullDates = componentActiveResponses.stream()
				.filter(componentActiveResponseDto -> componentActiveResponseDto.getLastUpdatedDate() != null)
				.sorted(Comparator.comparing(ComponentActiveResponseDto::getLastUpdatedDate).reversed())
				.collect(Collectors.toList());

		return ComponentActiveListResponseDto.of(componentActiveResponses, notNullDates.isEmpty() ? null : notNullDates.get(0).getLastUpdatedDate());
	}

	@Transactional(readOnly = true)
	public List<ComponentResponseDto> getComponents(Long siteId) {
		siteRepository.findById(siteId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SITE));
		return componentRepository.getComponentResponses(siteId);
	}
}
