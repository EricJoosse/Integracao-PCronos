package pcronos.integracao;

public class DefaultCharsetException extends Exception {
	public DefaultCharsetException(String charset) {
        super("Erro interno! O Monitorador j� foi adaptado para charsets default de Windows/Linux diferente de Cp1252, e provavelmente j� funciona, por�m o envio dos emails autom�ticos ainda n�o foi testado com o charset " + charset + " como charset default de Windows/Linux.");
	}

}
