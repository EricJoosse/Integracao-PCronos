package pcronos.integracao.fornecedor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import mslinks.ShellLink;

public class Instalador {

	private static void gravarIsAmbienteNuvem(int isAmbienteNuvem) {
      try
   	  {
	        BufferedWriter bWriter = new BufferedWriter(new FileWriter("bin/IsAmbienteNuvem.bat", true));
	        bWriter.append(Integer.toString(isAmbienteNuvem));
	        bWriter.newLine();
	     // bWriter.newLine();
	        bWriter.flush();
	        bWriter.close();
   	  }
   	  catch (IOException ioe)
   	  {
   	    System.out.println(Integer.toString(isAmbienteNuvem));
   	  }
	}
	
	
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
					
					gravarIsAmbienteNuvem(1);

					String caminhoMaisNomeArquivo = "C:/Arquivos de Programas PC/AdicionarFornecedorNuvem.bat";
					String nomeAtalho = "C:/ProgramData/Microsoft/Windows/Start Menu/Programs/Portal Cronos/Integrador " + f.SiglaSistemaFornecedor + " Cloud/Adicionar Inst�ncia.lnk";
					ShellLink slAdicionar = ShellLink.createLink(caminhoMaisNomeArquivo, nomeAtalho);
					slAdicionar.setIconLocation("C:/Arquivos de Programas PC/Integra��o Fornecedor - Portal Cronos/res/AdicionarInstancia.ico");

					caminhoMaisNomeArquivo = "C:/Arquivos de Programas PC/RemoverFornecedorNuvem.bat";
					nomeAtalho = "C:/ProgramData/Microsoft/Windows/Start Menu/Programs/Portal Cronos/Integrador " + f.SiglaSistemaFornecedor + " Cloud/Remover Inst�ncia.lnk";
					ShellLink slRemover = ShellLink.createLink(caminhoMaisNomeArquivo, nomeAtalho);
					slRemover.setIconLocation("C:/Arquivos de Programas PC/Integra��o Fornecedor - Portal Cronos/res/RemoverInstancia.ico");
				}
				else if (!f.IsServicoNuvem)
				{
					gravarIsAmbienteNuvem(0);

			        TarefaWindows tarefaWindows = new TarefaWindows(false, null, idFornecedor);
					tarefaWindows.gravarEmArquivoXML();
				}
			}
			else if (idFornecedor == -1)
			{
				// idFornecedor == -1 no caso de instala��o do servi�o de monitoramento autom�tico no servidor de aplica��o do Portal Cronos
				gravarIsAmbienteNuvem(0);
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
