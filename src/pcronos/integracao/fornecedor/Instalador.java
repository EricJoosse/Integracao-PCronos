package pcronos.integracao.fornecedor;

import mslinks.ShellLink;

public class Instalador {

	public static void main(String[] args) {
		try {
			int idFornecedor = Integer.parseInt(args[0]);		 
			
			FornecedorRepositorio fRep = new FornecedorRepositorio();
			Fornecedor f = fRep.getFornecedor(idFornecedor);

			// idFornecedor == -1 no caso de instala��o do servi�o de monitoramento autom�tico no servidor de aplica��o do Portal Cronos:
			if (idFornecedor != -1) 
			{
		        ManualManutencao m = new ManualManutencao(f);
		        m.gravarEmArquivoNoMenuWindows();

				if (f.IsServicoNuvem) 
				{
					String caminhoMaisNomeArquivo = "C:/Arquivos de Programas PC/AdicionarFornecedorNuvem.bat";
					String nomeAtalho = "C:/ProgramData/Microsoft/Windows/Start Menu/Programs/Portal Cronos/Integra��o " + f.SiglaSistemaFornecedor + "/Adicionar Cliente novo.lnk";
			       	ShellLink.createLink(caminhoMaisNomeArquivo, nomeAtalho);
				}
			}

			
			  
		}
		catch (java.lang.ArrayIndexOutOfBoundsException aioex) {
			System.out.println("Par�metro \"idFornecedor\" n�o informado na chamada de pcronos.integracao.fornecedor.Instalador");
		}
		catch (NumberFormatException nfex) {
			System.out.println("Par�metro \"idFornecedor\" inv�lido na chamada de pcronos.integracao.fornecedor.Instalador");
		}
		catch (Exception ex) {
			System.out.println("Erro: " + ex.getMessage() + "\r\n" + "N�o foi poss�vel instalar o servi�o 100 % !");
		}
    }

}
