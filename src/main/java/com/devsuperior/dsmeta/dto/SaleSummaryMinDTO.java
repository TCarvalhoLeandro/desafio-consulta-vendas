package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projections.SaleSummaryMinProjection;

public class SaleSummaryMinDTO {
	
	private String sellerName;
	private Double total;
	
	public SaleSummaryMinDTO() {
	
	}

	public SaleSummaryMinDTO(SaleSummaryMinProjection projection) {
		sellerName = projection.getSellerName();
		total = projection.getTotal();
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
