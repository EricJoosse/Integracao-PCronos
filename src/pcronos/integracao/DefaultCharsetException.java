package pcronos.integracao;

public class DefaultCharsetException extends Exception {
	public DefaultCharsetException(String charset) {
        super("Erro interno! O Monitorador ainda n�o suporta o charset " + charset + ".");
	}

}
