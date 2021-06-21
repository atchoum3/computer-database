package com.excilys.cdb.bindingFront.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.bindingFront.PageDTO;
import com.excilys.cdb.bindingFront.validator.PageValidator;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.Paginable;

@Component
public class PageMapper {

	private PageValidator validator;
	private Paginable paginable;

	public PageMapper(PageValidator validator, Paginable paginable) {
		this.validator = validator;
		this.paginable = paginable;
	}

	public PageDTO toPageDTO(Page p) {
		PageDTO.Builder builder = new PageDTO.Builder();
		builder.withBeginSlider(p.getBeginSlider());
		builder.withEndSlider(p.getEndSlider());

		builder.withColumn(p.getColumn());
		builder.withOrder(p.getOrder());
		builder.withReversedOrder(p.getOrder().reverse());

		builder.withCurrentPage(p.getCurrentPage());
		builder.withIndexLastPage(p.getIndexLastPage());
		builder.withNbElementByPage(p.getNbElementByPage());
		builder.withNbElementTotal(p.getNbElementTotal());
		return builder.build();
	}

	public Page toPage(PageDTO dto) {
		Page.Builder builder = new Page.Builder();

		if (validator.isValidNbElementTotal(dto)) {
			builder.withNbElementTotal(dto.getNbElementTotal());
		}
		if (validator.isValidNbElementByPage(dto)) {
			builder.withNbElementByPage(dto.getNbElementByPage());
		}
		if (validator.isValidCurrentPage(dto)) {
			builder.withCurrentPage(dto.getCurrentPage());
		}

		if (dto.getColumn() != null) {
			builder.withColumn(dto.getColumn());
		}
		if (dto.getOrder() != null) {
			builder.withOrder(dto.getOrder());
		}

		Page page = builder.build();
		paginable.updatePage(page);
		return page;
	}
}
