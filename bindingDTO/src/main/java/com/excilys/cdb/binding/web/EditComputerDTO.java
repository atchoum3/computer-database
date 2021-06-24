package com.excilys.cdb.binding.web;

public class EditComputerDTO extends AddComputerDTO {
	private long id;

	public EditComputerDTO() { }

	protected EditComputerDTO(Builder builder) {
		super(builder);
		id = builder.id;
	}

	public static class Builder extends AddComputerDTO.Builder<Builder> {
		private long id;

		public Builder(String name) {
			super(name);
		}

		public Builder withId(long id) {
			this.id = id;
			return this;
		}

		public EditComputerDTO build() {
			return new EditComputerDTO(this);
		}
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "AddComputerDTO [id=" + id + ", name=" + name + ", introduced=" + introduced
				+ ", discontinued=" + discontinued + ", companyId=" + companyId + "]";
	}

	public void setId(long id) {
		this.id = id;
	}


}
