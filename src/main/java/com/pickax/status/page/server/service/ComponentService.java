package com.pickax.status.page.server.service;

import com.pickax.status.page.server.domain.enumclass.ComponentStatus;
import com.pickax.status.page.server.domain.model.Component;
import com.pickax.status.page.server.domain.model.Site;
import com.pickax.status.page.server.dto.request.ComponentCreateRequestDto;
import com.pickax.status.page.server.repository.ComponentRepository;
import com.pickax.status.page.server.repository.SiteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pickax.status.page.server.dto.reseponse.ComponentResponseDto;
import lombok.RequiredArgsConstructor;

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
                request.isActive(),
                site
        );

        this.componentRepository.save(component);
    }

	public List<ComponentResponseDto> getActiveComponents(Long siteId) {
		siteRepository.findById(siteId).orElseThrow(EntityNotFoundException::new);
		return componentRepository.getComponents(siteId, true);
	}
}
