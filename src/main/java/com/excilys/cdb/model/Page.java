package com.excilys.cdb.model;

public class Page {
	public static final int INDEX_FIRST_PAGE = 1;
	public static final int INDEX_PAGE_WINDOW = 5;

	private int currentPage;
	private int nbElementTotal;
	private int nbElementByPage;
	private int indexLastPage;
	private OrderBy column;
	private Order order;
	private int endSlider;
	private int beginSlider;

	private Page(Builder builder) {
		nbElementTotal = builder.nbElementTotal;
		nbElementByPage = builder.nbElementByPage;
		column = builder.column;
		order = builder.order;
		currentPage = builder.currentPage;
		beginSlider = builder.beginSlider;
		endSlider = builder.endSlider;
	}

	public static class Builder {
		private static final int DEFAULT_NB_ELEMENT_BY_PAGE = 10;

		private int currentPage;
		private int nbElementTotal;
		private int nbElementByPage;
		private OrderBy column;
		private Order order;
		private int endSlider;
		private int beginSlider;

		public Builder() {
			nbElementByPage = DEFAULT_NB_ELEMENT_BY_PAGE;
			order = Order.ASC;
			currentPage = INDEX_FIRST_PAGE;
			column = OrderBy.COMPUTER_NAME;
			beginSlider = INDEX_FIRST_PAGE;
			endSlider = INDEX_FIRST_PAGE;
		}

		public Builder withCurrentPage(int currentPage) {
			this.currentPage = currentPage;
			return this;
		}

		public Builder withNbElementTotal(int nbElementTotal) {
			this.nbElementTotal = nbElementTotal;
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

		public Builder withBeginSlider(int beginSlider) {
			this.beginSlider = beginSlider;
			return this;
		}

		public Builder withEndSlider(int endSlider) {
			this.endSlider = endSlider;
			return this;
		}

		public Page build() {
			return new Page(this);
		}
	}




	/// setters & getters

	@Override
	public String toString() {
		return "Page [currentPage=" + currentPage + ", nbElementTotal=" + nbElementTotal + ", nbElementByPage="
				+ nbElementByPage + ", indexLastPage=" + indexLastPage + ", column=" + column + ", order=" + order
				+ ", endSlider=" + endSlider + ", beginSlider=" + beginSlider + "]";
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setNbElementByPage(int nbElementByPage) {
		this.nbElementByPage = nbElementByPage;
	}

	public void setNbElementTotal(int nbElementTotal) {
		this.nbElementTotal = nbElementTotal;
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

	public int getNbElementTotal() {
		return nbElementTotal;
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

	public void setIndexLastPage(int indexLastPage) {
		this.indexLastPage = indexLastPage;
	}

	public int getEndSlider() {
		return endSlider;
	}

	public void setEndSlider(int endSlider) {
		this.endSlider = endSlider;
	}

	public int getBeginSlider() {
		return beginSlider;
	}

	public void setBeginSlider(int beginSlider) {
		this.beginSlider = beginSlider;
	}
}
