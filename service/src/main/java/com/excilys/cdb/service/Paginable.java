package com.excilys.cdb.service;

import org.springframework.stereotype.Service;

import com.excilys.cdb.model.Page;

@Service
public class Paginable {

	public void previousPage(Page page) {
		int currentPage = page.getCurrentPage();
		if (currentPage > Page.INDEX_FIRST_PAGE) {
			page.setCurrentPage(--currentPage);
		}
	}

	public void nextPage(Page page) {
		int currentPage = page.getCurrentPage();
		if (currentPage < page.getIndexLastPage()) {
			page.setCurrentPage(++currentPage);
		}
	}

	/**
	 * Compute and set all page attribute.
	 * After this method the page is consistent
	 * @param page
	 */
	public void updatePage(Page page) {
		setIndexLastPage(page);
		changeCurrentPage(page, page.getCurrentPage());
	}

	public int getIndexFirstElement(Page page) {
		return (page.getCurrentPage() - 1) * page.getNbElementByPage();
	}

	public void changeCurrentPage(Page page, int newCurrentPage) {
		int indexLastpage = page.getIndexLastPage();

		if (newCurrentPage < Page.INDEX_FIRST_PAGE) {
			page.setCurrentPage(Page.INDEX_FIRST_PAGE);
		} else if (newCurrentPage > indexLastpage) {
			page.setCurrentPage(indexLastpage);
		} else {
			page.setCurrentPage(newCurrentPage);
		}
	}

	public void setNbElementByPage(Page page, int nbElementByPage) {
		page.setNbElementByPage(nbElementByPage);
		setIndexLastPage(page);
		changeCurrentPage(page, page.getCurrentPage());
	}

	public void setNbElementTotal(Page page, int nbElementTotal) {
		page.setNbElementTotal(nbElementTotal);
		setIndexLastPage(page);
		changeCurrentPage(page, page.getCurrentPage());
	}

	private void setIndexLastPage(Page page) {
		page.setIndexLastPage((int) Math.ceil(page.getNbElementTotal() / page.getNbElementByPage()) + Page.INDEX_FIRST_PAGE);
	}

}
