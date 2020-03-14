package br.com.intelector.gerenciarfaturacartao.domain;

import br.com.intelector.gerenciarfaturacartao.exception.TagException;

public enum TagArquivo {

	NUBANK("NUBANK"),
	ITAU_GOLD("ITAU_GOLD"),
	ITAU_ICONTA("ITAU_ICONTA");

	private String description;

	TagArquivo(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public static TagArquivo convertStringToEnum(String situacao) throws TagException {
		switch (situacao) {
			case "NUBANK" :
				return TagArquivo.NUBANK;
			case "ITAU_GOLD" :
				return TagArquivo.ITAU_GOLD;
			case "ITAU_ICONTA" :
				return TagArquivo.ITAU_ICONTA;
			default:
				throw new TagException("Tag de arquivo não encontrado!");
		}
	}
	
	
}
