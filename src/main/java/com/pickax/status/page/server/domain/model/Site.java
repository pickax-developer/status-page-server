package com.pickax.status.page.server.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.pickax.status.page.server.domain.enumclass.RegistrationStatus;
import com.pickax.status.page.server.dto.request.SiteRequestDto;

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
@Table(name = "sites")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Site {
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
		metaTag.updateSite(this);
	}

	private Site(SiteRequestDto siteRequestDto) {
		this.name = siteRequestDto.getName();
		this.description = siteRequestDto.getDescription();
		this.url = siteRequestDto.getUrl();
		this.registrationStatus = RegistrationStatus.UNVERIFIED;
	}

	public static Site from(SiteRequestDto siteRequestDto) {
		return new Site(siteRequestDto);
	}
}
