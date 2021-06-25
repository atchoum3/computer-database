package com.excilys.cdb.binding.web.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.web.PageDTO;
import com.excilys.cdb.binding.web.validator.PageValidator;
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
		builder.withBeginSlider(computeBeginSlider(p));
		builder.withEndSlider(computerEndSlider(p));

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

	private int computeBeginSlider(Page page) {
		int halfPageWindow = Page.INDEX_PAGE_WINDOW / 2;
		return Math.max(page.getCurrentPage() - halfPageWindow, Page.INDEX_FIRST_PAGE);
	}

	private int computerEndSlider(Page page) {
		int halfPageWindow = Page.INDEX_PAGE_WINDOW / 2;
		return Math.min(page.getCurrentPage() + halfPageWindow, page.getIndexLastPage());
	}
}
