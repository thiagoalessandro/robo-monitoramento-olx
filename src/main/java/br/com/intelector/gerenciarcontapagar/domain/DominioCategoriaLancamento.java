package br.com.intelector.gerenciarcontapagar.domain;

public enum DominioCategoriaLancamento {

	ASSINATURA("ASSINATURA"),
	MANUTENCAO_CASA("MANUTENCAO_CASA"),
	COMPRA_PARCELADA("COMPRA_PARCELADA"),
	COMPRA_PARCELA_UNICA("COMPRA_PARCELA_UNICA");

	private String description;

	DominioCategoriaLancamento(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

}
