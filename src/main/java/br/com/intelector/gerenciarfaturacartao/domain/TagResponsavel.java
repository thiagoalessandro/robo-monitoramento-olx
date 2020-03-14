package br.com.intelector.gerenciarfaturacartao.domain;

public enum TagResponsavel {

	THIAGO("THIAGO"),
	BRENDA("BRENDA"),
	COMPARTILHADO("COMPARTILHADO");

	private String description;

	TagResponsavel(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

}
