package com.pickax.status.page.server.domain;

import java.util.ArrayList;
import java.util.List;

import com.pickax.status.page.server.enumclass.RegistrationStatus;
import com.pickax.status.page.server.dto.request.ServiceRequestDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "services")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Service {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 50, nullable = false)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "url", nullable = false)
	private String url;

	@Column(name = "registration_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private RegistrationStatus registrationStatus;

	@OneToMany(mappedBy = "metaTag", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<MetaTag> mataTags = new ArrayList<>();

	public void addMetaTag(MetaTag metaTag) {
		this.mataTags.add(metaTag);
		metaTag.updateService(this);
	}

	private Service(ServiceRequestDto serviceRequestDto) {
		this.name = serviceRequestDto.getName();
		this.description = serviceRequestDto.getDescription();
		this.url = serviceRequestDto.getUrl();
		this.registrationStatus = RegistrationStatus.UNVERIFIED;
	}

	public static Service from(ServiceRequestDto serviceRequestDto) {
		return new Service(serviceRequestDto);
	}
}
