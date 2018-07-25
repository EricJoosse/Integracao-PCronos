package pcronos.integracao.fornecedor;


public class Desinstalador {

	public static void main(String[] args) {
		try {	
			
			// siglaSistema == "PCronos" no caso de instala��o do servi�o de monitoramento autom�tico no servidor de aplica��o do Portal Cronos:
    		if (!IntegracaoFornecedorCompleta.siglaSistema.equals("PCronos")) {
			    FornecedorRepositorio fRep = new FornecedorRepositorio();
             // int idFornecedor = Integer.parseInt(args[0]);
		   	    int idFornecedor = fRep.getIdFornecedorByCnpj(IntegracaoFornecedorCompleta.cnpjFornecedor);
			
	            ManualManutencao m = new ManualManutencao(fRep.getFornecedor(idFornecedor));
	            m.removerPCronosDoMenuWindows();
			}
    		
		}
//		catch (java.lang.ArrayIndexOutOfBoundsException aioex) {
//			System.out.println("Par�metro \"idFornecedor\" n�o informado na chamada de pcronos.integracao.fornecedor.Desinstalador");
//		}
//		catch (NumberFormatException nfex) {
//			System.out.println("Par�metro \"idFornecedor\" inv�lido na chamada de pcronos.integracao.fornecedor.Desinstalador");
//		}
		catch (Exception ex) {
			System.out.println("Erro: " + ex.getMessage() + "\r\n" + "N�o foi poss�vel desinstalar o servi�o!");
		}
    }

}
