package com.pickax.status.page.server.domain.model;

import java.time.ZonedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "meta_tags")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MetaTag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "meta_tag", nullable = false)
	private String metaTag;

	@Column(name = "is_checked", nullable = false)
	private Boolean isChecked;

	@Column(name = "expired_date", nullable = false)
	private ZonedDateTime expiredDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id")
	private Site site;

	public void updateSite(Site site) {
		this.site = site;
	}

	private MetaTag(String metaTag) {
		this.metaTag = metaTag;
		this.isChecked = false;
		this.expiredDate = ZonedDateTime.now().plusDays(7);
	}

	public static MetaTag from(String metaTag) {
		return new MetaTag(metaTag);
	}
}
