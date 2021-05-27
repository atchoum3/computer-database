package com.excilys.cdb.model;

public class Page {
	public static final int INDEX_FIRST_PAGE = 1;
	private int currentPage;
	private int elementByPage;
	private int totalNumberElem;
	private int indexLastPage;
	
	public Page(int currentPage, int elementByPage, int totalNumberElem) {
		this.elementByPage = elementByPage;
		this.totalNumberElem = totalNumberElem;
		computeLastPage();
		
		this.currentPage = INDEX_FIRST_PAGE;
		setCurrentPage(currentPage);
	}
	
	/**
	 * go to the next page if this page is not the last page.
	 */
	public void nextPage() {
		if (currentPage < indexLastPage) {
			currentPage++;
		}
	}

	/**
	 * go to the previous page if this page is not the first page.
	 */
	public void previousPage() {
		if (currentPage > INDEX_FIRST_PAGE) {
			currentPage--;
		}
	}
	
	public int getIndexFirstElement() {
		return currentPage * elementByPage;
	}
	
	private void computeLastPage() {
		indexLastPage = (int) Math.ceil(totalNumberElem / elementByPage);
	}
	
	@Override
	public String toString() {
		return "Page [currentPage=" + currentPage + ", elementByPage=" + elementByPage + ", totalNumberElem="
				+ totalNumberElem + ", indexLastPage=" + indexLastPage + "]";
	}
	
	/// setters & getters
	
	public void setCurrentPage(int page) {
		if (page < INDEX_FIRST_PAGE) {
			currentPage = INDEX_FIRST_PAGE;
		} else if (page > indexLastPage) {
			currentPage = indexLastPage;
		} else {
			currentPage = page;
		}
	}
	
	public void setElementByPage(int elementByPage) {
		this.elementByPage = elementByPage;
		computeLastPage();
		setCurrentPage(currentPage);
	}
	
	public int getTotalNumberElem() {
		return totalNumberElem;
	}

	public int getIndexLastPage() {
		return indexLastPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getElementByPage() {
		return elementByPage;
	}
}
