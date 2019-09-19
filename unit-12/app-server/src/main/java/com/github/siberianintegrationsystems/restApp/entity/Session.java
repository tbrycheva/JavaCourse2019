package com.github.siberianintegrationsystems.restApp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Session extends BaseEntity {


	@Column
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column
	private LocalDateTime insertDate;

	public LocalDateTime getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(LocalDateTime insertDate) {
		this.insertDate = insertDate;
	}

	@Column
	private BigDecimal result;

	public BigDecimal getResult() {
		return result;
	}

	public void setResult(BigDecimal result) {
		this.result = result;
	}
}