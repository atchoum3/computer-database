package com.excilys.cdb.model;

public class Page {
	public static final int INDEX_FIRST_PAGE = 1;

	private int currentPage;
	private int elementByPage;
	private int totalNumberElem;
	private int indexLastPage;
	private OrderBy column;
	private Order order;

	private Page(Builder builder) {
		elementByPage = builder.elementByPage;
		totalNumberElem = builder.totalNumberElem;
		column = builder.column;
		order = builder.order;
		computeLastPage();

		setCurrentPage(currentPage);
	}

	public static class Builder {
		private static final int DEFAULT_ELEM_BY_PAGE = 10;

		private int currentPage;
		private int elementByPage;
		private int totalNumberElem;
		private OrderBy column;
		private Order order;

		public Builder() {
			elementByPage = DEFAULT_ELEM_BY_PAGE;
			order = Order.ASC;
			currentPage = INDEX_FIRST_PAGE;
			column = OrderBy.COMPUTER_NAME;
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

		public Builder withColumn(OrderBy column) {
			this.column = column;
			return this;
		}

		public Builder withOrder(Order order) {
			this.order = order;
			return this;
		}

		public Page build() {
			return new Page(this);
		}
	}

	public void previousPage() {
		if (currentPage > INDEX_FIRST_PAGE) {
			currentPage--;
		}
	}

	public void nextPage() {
		if (currentPage < indexLastPage) {
			currentPage++;
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

	public OrderBy getColumn() {
		return column;
	}

	public void setColumn(OrderBy column) {
		this.column = column;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
