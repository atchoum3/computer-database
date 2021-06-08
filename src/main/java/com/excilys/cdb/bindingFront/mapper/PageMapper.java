package com.excilys.cdb.bindingFront.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.bindingFront.PageDTO;
import com.excilys.cdb.bindingFront.validator.PageValidator;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.Paginable;

@Scope
@Component
public class PageMapper {

	@Autowired
	private PageValidator validator;
	@Autowired
	private Paginable paginable;

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
		builder.withColumn(dto.getColumn());
		builder.withOrder(dto.getOrder());

		Page page = builder.build();
		paginable.updatePage(page);
		return page;
	}
}
