package br.com.intelector.robomonitoramentoolx.domain;

public enum DominioSituacaoRegistro {

	ATIVO("A"),
	INATIVO("I"),
	EXCLUIDO("E");
	
	private String description;

	DominioSituacaoRegistro(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public static DominioSituacaoRegistro convertStringToEnum(String situacao) {
		switch (situacao) {
			case "A" :
				return DominioSituacaoRegistro.ATIVO;
			case "I" :
				return DominioSituacaoRegistro.INATIVO;
			case "E" :
				return DominioSituacaoRegistro.EXCLUIDO;
			default:
				return DominioSituacaoRegistro.ATIVO;
		}
	}
	
	
}
