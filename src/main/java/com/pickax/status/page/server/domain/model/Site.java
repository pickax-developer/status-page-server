package com.pickax.status.page.server.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.pickax.status.page.server.domain.enumclass.SiteRegistrationStatus;
import com.pickax.status.page.server.dto.request.SiteCreateRequestDto;

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

	@Column(name = "site_registration_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private SiteRegistrationStatus siteRegistrationStatus;

	@OneToMany(mappedBy = "site", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<MetaTag> mataTags = new ArrayList<>();

	public void addMetaTag(MetaTag metaTag) {
		this.mataTags.add(metaTag);
		metaTag.updateSite(this);
	}

	private Site(SiteCreateRequestDto siteCreateRequestDto) {
		this.name = siteCreateRequestDto.getName();
		this.description = siteCreateRequestDto.getDescription();
		this.url = siteCreateRequestDto.getUrl();
		this.siteRegistrationStatus = SiteRegistrationStatus.UNVERIFIED;
	}

	public static Site from(SiteCreateRequestDto siteCreateRequestDto) {
		return new Site(siteCreateRequestDto);
	}
}
