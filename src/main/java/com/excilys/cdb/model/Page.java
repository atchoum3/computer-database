package com.excilys.cdb.model;

public class Page {
	public static final int INDEX_FIRST_PAGE = 1;

	private int currentPage;
	private int elementByPage;
	private int totalNumberElem;
	private int indexLastPage;
	private int indexColumn;
	private OrderBy order;

	private Page(Builder builder) {
		elementByPage = builder.elementByPage;
		totalNumberElem = builder.totalNumberElem;
		indexColumn = builder.indexColumn;
		order = builder.order;
		computeLastPage();

		setCurrentPage(currentPage);
	}

	public static class Builder {
		private static final int DEFAULT_ELEM_BY_PAGE = 10;
		private static final OrderBy DEFAULT_ORDER = OrderBy.ASC;

		private int currentPage;
		private int elementByPage;
		private int totalNumberElem;
		private int indexColumn;
		private OrderBy order;

		public Builder() {
			elementByPage = DEFAULT_ELEM_BY_PAGE;
			order = DEFAULT_ORDER;
			currentPage = INDEX_FIRST_PAGE;
		}

		public Builder withCurrentPage(int currentPage) {
			this.currentPage = currentPage;
			return this;
		}

		public Builder withElementByPage(int elementByPage) {
			this.elementByPage = elementByPage;
			return this;
		}

		public Builder withTotalNumberElem(int totalNumberElem) {
			this.totalNumberElem = totalNumberElem;
			return this;
		}

		public Builder withIndexColumn(int indexColumn) {
			this.indexColumn = indexColumn;
			return this;
		}

		public Builder withOrder(OrderBy order) {
			this.order = order;
			return this;
		}

		public Page build() {
			return new Page(this);
		}
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
		return (currentPage - 1) * elementByPage;
	}

	private void computeLastPage() {
		indexLastPage = (int) Math.ceil(totalNumberElem / elementByPage) + INDEX_FIRST_PAGE;
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

	public void setTotalNumberElem(int totalNumberElem) {
		this.totalNumberElem = totalNumberElem;
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

	public int getIndexColumn() {
		return indexColumn;
	}

	public void setIndexColumn(int indexColumn) {
		this.indexColumn = indexColumn;
	}

	public OrderBy getOrder() {
		return order;
	}

	public void setOrder(OrderBy order) {
		this.order = order;
	}
}
