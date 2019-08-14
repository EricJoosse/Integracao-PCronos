package pcronos.integracao.fornecedor;



public class GeradorTarefaWindowsNaoNuvem {

	public static void main(String[] args) {
		int idFornecedor = Integer.parseInt(args[0]);		 
		
        try 
        {
			// Primeiro testar se o idFornecedor digitado existe:
			FornecedorRepositorio fRep = new FornecedorRepositorio();
	        Fornecedor f =  fRep.getFornecedor(idFornecedor);
	
	        TarefaWindows tarefaWindows = new TarefaWindows(false, null, idFornecedor);
			tarefaWindows.gravarEmArquivoXML();
		}
		catch (java.lang.ArrayIndexOutOfBoundsException aioex) {
			System.out.println("Par�metro \"idFornecedor\" n�o informado na chamada de pcronos.integracao.fornecedor.GeradorTarefaWindowsNaoNuvem");
		}
		catch (NumberFormatException nfex) {
			System.out.println("Par�metro \"idFornecedor\" inv�lido na chamada de pcronos.integracao.fornecedor.GeradorTarefaWindowsNaoNuvem");
		}
		catch (Exception ex) {
			System.out.println("Erro: " + ex.getMessage());
		}
    }

}
