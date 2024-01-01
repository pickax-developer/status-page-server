package com.pickax.status.page.server.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pickax.status.page.server.domain.MetaTag;
import com.pickax.status.page.server.repository.MetaTagRepository;
import com.pickax.status.page.server.repository.ServiceRepository;
import com.pickax.status.page.server.dto.request.ServiceRequestDto;
import com.pickax.status.page.server.dto.reseponse.ServiceResponseDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceService {

	private final ServiceRepository serviceRepository;
	private final MetaTagRepository metaTagRepository;

	@Transactional
	public ServiceResponseDto createService(ServiceRequestDto serviceRequestDto) {
		com.pickax.status.page.server.domain.Service service = com.pickax.status.page.server.domain.Service.from(
			serviceRequestDto);

		MetaTag metaTag = createMetaTag();
		service.addMetaTag(metaTag);

		serviceRepository.save(service);

		return ServiceResponseDto.from(metaTag.getMetaTag());
	}

	private MetaTag createMetaTag() {
		String randomUUID = UUID.randomUUID().toString();
		String metaTagString = "<meta name=\"service verification\" content=\"" + randomUUID + "\"/>";

		MetaTag metaTag = MetaTag.from(metaTagString);
		metaTagRepository.save(metaTag);

		return metaTag;
	}
}
