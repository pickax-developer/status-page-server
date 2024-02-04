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
import jakarta.persistence.*;
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

	@Column(name = "secret_key", length = 50, nullable = false)
	private String secretKey;

	@OneToMany(mappedBy = "site", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<MetaTag> mataTags = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "site", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Component> components = new ArrayList<>();

	public void addMetaTag(MetaTag metaTag) {
		this.mataTags.add(metaTag);
		metaTag.updateSite(this);
	}

	private Site(SiteCreateRequestDto siteCreateRequestDto, String secretKey, User user) {
		this.name = siteCreateRequestDto.name();
		this.description = siteCreateRequestDto.description();
		this.url = siteCreateRequestDto.url();
		this.siteRegistrationStatus = SiteRegistrationStatus.UNVERIFIED;
		this.secretKey = secretKey;
		this.user = user;
	}

	public static Site of(SiteCreateRequestDto siteCreateRequestDto, String secretKey, User user) {
		return new Site(siteCreateRequestDto, secretKey, user);
	}

	public boolean isUnverified() {
		return SiteRegistrationStatus.UNVERIFIED.equals(siteRegistrationStatus);
	}

	public void complete() {
		this.siteRegistrationStatus = SiteRegistrationStatus.COMPLETED;
	}

	public boolean hasValidatedOwnerByMetaTag() {
		return SiteRegistrationStatus.COMPLETED.equals(this.siteRegistrationStatus);
	}

	public boolean ownerEqualsBy(Long requesterId) {
		return this.getUser().getId().equals(requesterId);
	}

	public void cancel() {
		siteRegistrationStatus = SiteRegistrationStatus.CANCELED;
	}
}
