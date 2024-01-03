package com.pickax.status.page.server.domain.model;

import java.time.LocalDateTime;

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

	@Column(name = "content", nullable = false)
	private String content;

	@Column(name = "is_checked", nullable = false)
	private Boolean isChecked;

	@Column(name = "expired_date", nullable = false)
	private LocalDateTime expiredDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id")
	private Site site;

	public void updateSite(Site site) {
		this.site = site;
	}

	private MetaTag(String content, LocalDateTime expiredDate) {
		this.content = content;
		this.expiredDate = expiredDate;
		this.isChecked = false;
	}

	public static MetaTag of(String metaTag, LocalDateTime expiredDate) {
		return new MetaTag(metaTag, expiredDate);
	}

	public boolean isChecked() {
		return isChecked;
	}

	public boolean isExpired() {
		return expiredDate.isBefore(LocalDateTime.now());
	}

	public boolean isSameAs(String content) {
		return this.content.equals(content);
	}

	public void check() {
		isChecked = true;
	}
}
