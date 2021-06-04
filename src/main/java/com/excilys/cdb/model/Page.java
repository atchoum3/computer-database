package com.excilys.cdb.model;

public class Page {
	public static final int INDEX_FIRST_PAGE = 1;
	private static final int INDEX_PAGE_WINDOW = 5;

	private int currentPage;
	private int nbElement;
	private int nbElementByPage;
	private int indexLastPage;
	private OrderBy column;
	private Order order;
	private int pageIndexBegin;
	private int pageIndexEnd;

	private Page(Builder builder) {
		nbElement = builder.nbElement;
		nbElementByPage = builder.nbElementByPage;
		column = builder.column;
		order = builder.order;
		computeLastPage();

		setCurrentPage(builder.currentPage);
	}

	public static class Builder {
		private static final int DEFAULT_NB_ELEMENT_BY_PAGE = 10;

		private int currentPage;
		private int nbElement;
		private int nbElementByPage;
		private OrderBy column;
		private Order order;

		public Builder() {
			nbElementByPage = DEFAULT_NB_ELEMENT_BY_PAGE;
			order = Order.ASC;
			currentPage = INDEX_FIRST_PAGE;
			column = OrderBy.COMPUTER_NAME;
		}

		public Builder withCurrentPage(int currentPage) {
			this.currentPage = currentPage;
			return this;
		}

		public Builder withNbElement(int nbElement) {
			this.nbElement = nbElement;
			return this;
		}

		public Builder withNbElementByPage(int nbElementByPage) {
			this.nbElementByPage = nbElementByPage;
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
		return (currentPage - 1) * nbElementByPage;
	}

	private void computeLastPage() {
		indexLastPage = (int) Math.ceil(nbElement / nbElementByPage) + INDEX_FIRST_PAGE;
	}

	@Override
	public String toString() {
		return "Page [currentPage=" + currentPage + ", nbElement=" + nbElement + ", nbElementByPage=" + nbElementByPage
				+ ", indexLastPage=" + indexLastPage + ", column=" + column + ", order=" + order + ", pageIndexBegin="
				+ pageIndexBegin + ", pageIndexEnd=" + pageIndexEnd + "]";
	}

	private void computeIndex() {
		int halfPageWindow = INDEX_PAGE_WINDOW / 2;
		pageIndexBegin = Math.max(currentPage - halfPageWindow, Page.INDEX_FIRST_PAGE);
		pageIndexEnd = Math.min(pageIndexBegin + INDEX_PAGE_WINDOW - 1, indexLastPage);
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
		computeIndex();
	}

	public void setNbElementByPage(int nbElementByPage) {
		this.nbElementByPage = nbElementByPage;
		computeLastPage();
		setCurrentPage(currentPage);
	}

	public void setNbElement(int nbElement) {
		this.nbElement = nbElement;
		computeLastPage();
		setCurrentPage(currentPage);
	}

	public int getTotalNumberElement() {
		return nbElementByPage;
	}

	public int getIndexLastPage() {
		return indexLastPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getNbElementByPage() {
		return nbElementByPage;
	}

	public int getNbElement() {
		return nbElement;
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

	public int getPageIndexBegin() {
		return pageIndexBegin;
	}

	public int getPageIndexEnd() {
		return pageIndexEnd;
	}
}
