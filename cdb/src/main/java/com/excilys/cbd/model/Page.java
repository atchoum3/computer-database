package com.excilys.cbd.model;

public class Page {
	private static final int FIRST_PAGE = 1;
	private static final int DEFAULT_ELEMENT_BY_PAGE = 10;
	private int currentPage;
	private int elementByPage;
	private boolean isLastPage;
	
	public Page(int elementByPage) {
		this.elementByPage = elementByPage;
		this.currentPage = FIRST_PAGE;
	}
	
	public Page() {
		this(DEFAULT_ELEMENT_BY_PAGE);
	}

	/**
	 * go to the next page if this page is not the last page.
	 */
	public void nextPage() {
		if (!isLastPage) {
			currentPage++;
		}
	}
	
	/**
	 * go to the previous page if this page is not the first page.
	 */
	public void previousPage() {
		if (currentPage > FIRST_PAGE) {
			isLastPage = false;
			currentPage--;
		}
	}
	
	/**
	 * get the index of the first element of the current page.
	 * @return the index of the first element of the current page
	 */
	public int getIndexFirstElement() {
		return elementByPage * (currentPage - 1);
	}
	
	/**
	 * get the index of the first element of the last page.
	 * @return the index of the first element of the last page
	 */
	public int getIndexLastElement() {
		return elementByPage * currentPage;
	}
	
	/// setters & getters
	
	public int getCurrentPage() {
		return currentPage;
	}

	public int getElementByPage() {
		return elementByPage;
	}
	
	public boolean isLastPage() {
		return isLastPage; 
	}
	public void setIsLastPage() {
		isLastPage = true;
	}
}
