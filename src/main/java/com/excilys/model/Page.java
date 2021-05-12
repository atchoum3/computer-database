package com.excilys.model;

public class Page {
	private final static int FIRST_PAGE = 1;
	private final static int DEFAULT_ELEMENT_BY_PAGE = 10;
	private int currentPage;
	private int elementByPage;
	private boolean isLastPage;
	
	public Page(int elementByPage, int currentPage) {
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

	public void nextPage() {
		if ( !isLastPage) {
			currentPage++;
		}
	}
	
	public void previousPage() {
		if (currentPage > FIRST_PAGE) {
			isLastPage = false;
			currentPage--;
		}
	}
	
	public int getIndexFirstElement() {
		return elementByPage*(currentPage-1);
	}
	
	public int getIndexLastElement() {
		return elementByPage*currentPage;
	}
	
	/// setters & getters
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		if (currentPage < FIRST_PAGE) {
			throw new IndexOutOfBoundsException();
		}
		this.currentPage = currentPage;
	}

	public int getElementByPage() {
		return elementByPage;
	}
	public void setElementByPage(int elementByPage) {
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
