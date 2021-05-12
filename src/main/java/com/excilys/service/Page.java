package com.excilys.service;

public class Page {
	private final static int FIRST_PAGE = 1;
	private int maxPage;
	private int currentPage;
	private int elementByPage;
	
	public Page(int elementByPage, int maxPage, int currentPage) {
		this.elementByPage = elementByPage;
		this.maxPage = maxPage;
		this.currentPage = currentPage;
	}
	
	public Page(int elementByPage, int maxPage) {
		this(elementByPage, maxPage, FIRST_PAGE);
	}

	public void nextPage() {
		if (currentPage < maxPage) {
			currentPage++;
		}
	}
	
	public void previousPage() {
		if (currentPage > FIRST_PAGE) {
			currentPage--;
		}
	}
	
	public int getIndexFirstElementCurentPage() {
		return elementByPage*(currentPage-1);
	}
	
	public int getIndexLastElementCurentPage() {
		return elementByPage*currentPage;
	}
	
	/// setters & getters
	public int getMaxPage() {
		return maxPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getElementByPage() {
		return elementByPage;
	}
}
