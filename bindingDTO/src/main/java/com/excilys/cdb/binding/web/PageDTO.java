package com.excilys.cdb.binding.web;

import com.excilys.cdb.model.Order;
import com.excilys.cdb.model.OrderBy;

public class PageDTO {
	private int currentPage;
	private int nbElementTotal;
	private int nbElementByPage;
	private int indexLastPage;
	private OrderBy column;
	private Order order;
	private Order reversedOrder;
	private int endSlider;
	private int beginSlider;

	public PageDTO() { }

	public PageDTO(Builder builder) {
		currentPage = builder.currentPage;
		nbElementTotal = builder.nbElementTotal;
		nbElementByPage = builder.nbElementByPage;
		indexLastPage = builder.indexLastPage;
		endSlider = builder.endSlider;
		beginSlider = builder.beginSlider;
		column = builder.column;
		reversedOrder = builder.reversedOrder;
		order = builder.order;
	}

	public static class Builder {
		private int currentPage;
		private int nbElementTotal;
		private int nbElementByPage;
		private int indexLastPage;		// can be compute
		private OrderBy column;
		private Order order;
		private Order reversedOrder;	// can be compute
		private int endSlider;			// can be compute
		private int beginSlider;		// can be compute

		public Builder() { }

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

		public Builder withIndexLastPage(int indexLastPage) {
			this.indexLastPage = indexLastPage;
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

		public Builder withReversedOrder(Order reversedOrder) {
			this.reversedOrder = reversedOrder;
			return this;
		}

		public Builder withEndSlider(int endSlider) {
			this.endSlider = endSlider;
			return this;
		}

		public Builder withBeginSlider(int beginSlider) {
			this.beginSlider = beginSlider;
			return this;
		}

		public PageDTO build() {
			return new PageDTO(this);
		}
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setNbElementTotal(int nbElementTotal) {
		this.nbElementTotal = nbElementTotal;
	}

	public void setNbElementByPage(int nbElementByPage) {
		this.nbElementByPage = nbElementByPage;
	}

	public void setIndexLastPage(int indexLastPage) {
		this.indexLastPage = indexLastPage;
	}

	public void setColumn(OrderBy column) {
		this.column = column;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public void setReversedOrder(Order reversedOrder) {
		this.reversedOrder = reversedOrder;
	}

	public void setEndSlider(int endSlider) {
		this.endSlider = endSlider;
	}

	public void setBeginSlider(int beginSlider) {
		this.beginSlider = beginSlider;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getNbElementTotal() {
		return nbElementTotal;
	}

	public int getNbElementByPage() {
		return nbElementByPage;
	}

	public int getIndexLastPage() {
		return indexLastPage;
	}

	public OrderBy getColumn() {
		return column;
	}

	public Order getOrder() {
		return order;
	}

	public Order getReversedOrder() {
		return reversedOrder;
	}

	public int getEndSlider() {
		return endSlider;
	}

	public int getBeginSlider() {
		return beginSlider;
	}

	@Override
	public String toString() {
		return "PageDTO [currentPage=" + currentPage + ", nbElementTotal=" + nbElementTotal + ", nbElementByPage="
				+ nbElementByPage + ", indexLastPage=" + indexLastPage + ", column=" + column + ", order=" + order
				+ ", reversedOrder=" + reversedOrder + ", endSlider=" + endSlider + ", beginSlider=" + beginSlider
				+ "]";
	}


}
