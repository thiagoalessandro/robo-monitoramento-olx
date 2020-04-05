package br.com.intelector.gerenciarcontapagar.domain;

public enum DominioResponsavel {

	INDEFINIDO("INDEFINIDO"),
	THIAGO("THIAGO"),
	BRENDA("BRENDA"),
	COMPARTILHADO("COMPARTILHADO");

	private String description;

	DominioResponsavel(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

}
