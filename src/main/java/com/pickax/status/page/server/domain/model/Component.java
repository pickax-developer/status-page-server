package com.pickax.status.page.server.domain.model;

import com.pickax.status.page.server.domain.enumclass.ComponentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "components")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Component {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "component_status")
	@Enumerated(EnumType.STRING)
	private ComponentStatus componentStatus;

	@Column(name = "frequency")
	private Long frequency;

	@Column(name = "is_active")
	private Boolean isActive;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id")
	private Site site;

	@Column(name = "last_updated_at")
	private LocalDateTime lastUpdatedDate;

	private Component(String name, String description, ComponentStatus status, Long frequency, boolean isActive, Site site) {
		this.name = name;
		this.description = description;
		this.componentStatus = status;
		this.frequency = frequency;
		this.isActive = isActive;
		this.site = site;
	}

	public static Component create(String name, String description, ComponentStatus status, Long frequency, boolean isActive, Site site) {
		return new Component(name, description, status, frequency, isActive, site);
	}
}
