package com.excilys.cbd.model;

public class Page {
	private final static int FIRST_PAGE = 1;
	private final static int DEFAULT_ELEMENT_BY_PAGE = 10;
	private int currentPage;
	private int elementByPage;
	private boolean isLastPage;
	
	/**
	 * Constructor
	 * @param elementByPage
	 * @param currentPage
	 * @throws IllegalArgumentException is thrown if the elementByPage is negative
	 */
	public Page(int elementByPage, int currentPage) throws IllegalArgumentException {
		setCurrentPage(currentPage);
		setElementByPage(elementByPage);
		isLastPage = false;
	}
	
	public Page(int elementByPage) {
		this(elementByPage, FIRST_PAGE);
	}
	
	public Page() {
		this(DEFAULT_ELEMENT_BY_PAGE, FIRST_PAGE);
	}

	/**
	 * go to the next page if this page is not the last page
	 */
	public void nextPage() {
		if ( !isLastPage) {
			currentPage++;
		}
	}
	
	/**
	 * go to the previous page if this page is not the first page
	 */
	public void previousPage() {
		if (currentPage > FIRST_PAGE) {
			isLastPage = false;
			currentPage--;
		}
	}
	
	/**
	 * get the index of the first element of the current page
	 * @return the index of the first element of the current page
	 */
	public int getIndexFirstElement() {
		return elementByPage*(currentPage-1);
	}
	
	/**
	 * get the index of the first element of the last page
	 * @return the index of the first element of the last page
	 */
	public int getIndexLastElement() {
		return elementByPage*currentPage;
	}
	
	/// setters & getters
	
	public int getCurrentPage() {
		return currentPage;
	}
	
	/**
	 * set the current page
	 * @param currentPage
	 * @throws IndexOutOfBoundsException if the current page is before the first page
	 */
	public void setCurrentPage(int currentPage) throws IndexOutOfBoundsException {
		if (currentPage < FIRST_PAGE) {
			throw new IndexOutOfBoundsException();
		}
		this.currentPage = currentPage;
	}

	public int getElementByPage() {
		return elementByPage;
	}
	/**
	 * 
	 * @param elementByPage
	 * @throws IllegalArgumentException is thrown if the elementByPage is negative
	 */
	public void setElementByPage(int elementByPage) throws IllegalArgumentException {
		if (elementByPage < 0) {
			throw new IllegalArgumentException("This attribut need to be positive");
		}
		this.elementByPage = elementByPage;
	}
	
	public boolean isLastPage() {
		return isLastPage; 
	}
	public void setIsLastPage() {
		isLastPage = true;
	}
}
