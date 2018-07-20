package pcronos.integracao.fornecedor;

import java.awt.Window;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import mslinks.ShellLink;

public class ManualManutencao {

	private String nomeArquivo = null;
	private String nomeAtalho = null;
	private String conteudo;
	private Fornecedor fornecedor;
	private String caminhoManual = null;
	private String caminhoAtalhoManual = null;
	
	
	private void setCaminhoManualMaisCaminhoAtalho() throws Exception { 	
	    if (this.fornecedor.tipoSO.equals("Windows Server 2008 R2 SP1")) {
	    	this.caminhoManual = "C:/ProgramData/Microsoft/Windows/Start Menu/Programs/Portal Cronos/";
	    }
	    else if (this.fornecedor.tipoSO.equals("Windows Server 2012 R2")) {
	    	this.caminhoManual = "C:/Arquivos de Programas PC/";
		  	this.caminhoAtalhoManual = "C:/ProgramData/Microsoft/Windows/Start Menu/Programs/Portal Cronos/";
	    }
		else if (this.fornecedor.tipoSO.equals("Windows Server 2016") || this.fornecedor.tipoSO.equals("Windows 10 Pro")) {
		  	this.caminhoManual = "C:/Arquivos de Programas PC/";
		  	this.caminhoAtalhoManual = "C:/ProgramData/Microsoft/Windows/Start Menu/Programs/Portal Cronos/";
	    }
	    else
	    	throw new Exception("O sistema operacional \"" + this.fornecedor.tipoSO + "\" ainda est� sem diret�rio padr�o definido para o Manual de Manuten��o para a TI.");
	}


	private void setNomeArquivoMaisAtalho() throws Exception { 	
		this.nomeArquivo = "Manual solucionamento paradas da integra��o Portal Cronos - v1.4.3 (12.07.2018).txt";
		this.nomeAtalho = "Manual Manuten��o.lnk";

//		if (this.fornecedor.tipoSO.equals("Windows Server 2008 R2 SP1")) {
//			this.nomeArquivo = "Manual solucionamento paradas da integra��o Portal Cronos - v1.4.3 (12.07.2018).txt";
//	    }
//	    else if (this.fornecedor.tipoSO.equals("Windows Server 2012 R2")) {
//			this.nomeArquivo = "Portal Cronos - Manual solucionamento paradas da integra��o - v1.4.3 (12.07.2018).txt";
//	    }
//	    else
//	    	throw new Exception("O sistema operacional \"" + this.fornecedor.tipoSO + "\" ainda est� sem nome definido para o arquivo do Manual de Manuten��o para a TI.");
	}


	public void gravarEmArquivoNoMenuWindows() throws IOException, Exception {
		setCaminhoManualMaisCaminhoAtalho();
		
    	File diretorioManual = new File(caminhoManual);
    	if(!diretorioManual.exists()) { 
    		diretorioManual.mkdir();
    	}
    	else {
    		// Excluir eventuais manuais antigos:
    		for (final File file : diretorioManual.listFiles()) 
    		{
    			if (file.getName().startsWith("Manual") && file.getName().endsWith(".txt"))
   			       file.delete();
    		}
    	}

    	
    	BufferedWriter bWriter = new BufferedWriter(new FileWriter(caminhoManual + nomeArquivo, false));
        bWriter.write(this.conteudo);
        bWriter.flush();
        bWriter.close();
        
        
        
        if (     this.fornecedor.tipoSO.equals("Windows Server 2016") 
              || this.fornecedor.tipoSO.equals("Windows 10 Pro")
              || this.fornecedor.tipoSO.equals("Windows Server 2012 R2")
            ) {
        	
        	File diretorioAtalhoManual = new File(caminhoAtalhoManual);
        	if(!diretorioAtalhoManual.exists()) { 
        		diretorioAtalhoManual.mkdir();
        	}
        	else {
        		// Excluir eventuais atalhos antigos:
        		for (final File file : diretorioManual.listFiles()) 
        		{
        			if (file.getName().startsWith("Manual") && file.getName().endsWith(".lnk"))
       			       file.delete();
        		}
        	}

        	ShellLink.createLink(caminhoManual + nomeArquivo, caminhoAtalhoManual + nomeAtalho);
        }
	}
	
	
	
	public void gravarEmArquivoSoltoNoRaizDoProjeto() throws IOException, Exception {
		File f = new File(nomeArquivo);
		if(f.exists() && !f.isDirectory()) { 
		    f.delete();
		}
		
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(nomeArquivo, false));
        bWriter.write(this.conteudo);
        bWriter.flush();
        bWriter.close();
	}
	
	
	
	public void removerPCronosDoMenuWindows() throws Exception { 
        setCaminhoManualMaisCaminhoAtalho();
		
        if (       this.fornecedor.tipoSO.equals("Windows Server 2016") 
        		|| this.fornecedor.tipoSO.equals("Windows 10 Pro")
                || this.fornecedor.tipoSO.equals("Windows Server 2012 R2")
           ) {
        	File diretorioAtalhoManual = new File(caminhoAtalhoManual);
        	
        	if(diretorioAtalhoManual.exists()) { 
        		boolean temOutrosAtalhos = false;
        		
        		for (final File shortcut : diretorioAtalhoManual.listFiles()) 
        		{
        			if (shortcut.getName().startsWith("Manual") && shortcut.getName().endsWith(".lnk"))
        				shortcut.delete();
        			else
        			   temOutrosAtalhos = true;
        		}
        		if (!temOutrosAtalhos) diretorioAtalhoManual.delete();
        	}
        }


        
        File diretorioManual = new File(caminhoManual);
    	
    	if(diretorioManual.exists()) { 
    		boolean temOutrosArquivos = false;
    		
    		for (final File file : diretorioManual.listFiles()) 
    		{
    			if (file.getName().startsWith("Manual") && file.getName().endsWith(".txt"))
  			       file.delete();
    			else
    			   temOutrosArquivos = true;
    		}
    		if (!temOutrosArquivos) diretorioManual.delete();
    	}
	}
	
	
	
	public ManualManutencao(Fornecedor f) throws Exception {
		this.fornecedor = f;
		setNomeArquivoMaisAtalho();
		

        this.conteudo = "" +
"Introdu��o t�cnica:" + "\r\n" +
"===================" + "\r\n" +
"O \"servi�o\" de integra��o � um \"servi�o\" de Java portanto � independente de Windows," + "\r\n" + 
"ent�o n�o se encontra na lista de Servi�os de Windows, nem aparece no \"Gerenciador de Tarefas\" (\"Task Manager\")" + "\r\n" + 
"de Windows, e tamb�m nunca aparece no \"Visualizador de Eventos\" (\"Event Viewer\") de Windows. " + "\r\n" +
"Nunca precisa reiniciar, parar ou iniciar este \"servi�o\" e nem existem estas op��es. " + "\r\n" +
"Reiniciamento � coisa de Windows, n�o tem nada a ver com Java. " + "\r\n" +
"Ao ligar ou reiniciar o servidor, o servi�o starta automaticamente. " + "\r\n" +
"Apenas existe uma op��o para desinstala��o do servi�o que ser� disponibilizada no menu de Windows" + "\r\n" + 
"em uma das pr�ximas vers�es do servi�o. " + "\r\n" +
" " + "\r\n" +
"Normalmente o servi�o executa de 15 a 15 minutos." + "\r\n" + 
"Se a integra��o parou de ofertar automaticamente:  " + "\r\n" +
"  1. Se precisar, veja, opcionalmente, abaixo cap�tulo \"A\" com a lista de poss�veis causas;" + "\r\n" + 
"  2. Em todos os casos, ap�s a solu��o, veja no cap�tulo \"B\" como verificar se o servi�o " + "\r\n" +
"     realmente voltou a funcionar." + "\r\n" +
"      " + "\r\n" +
"Favor N�O SIMPLESMENTE REINICIAR O SERVIDOR, por�m identificar a causa para podermos evitar repeti��o no futuro." + "\r\n" + 
"" + "\r\n" +
"Observa��o: este manual contem apenas a parte t�cnica da integra��o. Se precisar, o objetivo e os conceitos " + "\r\n" + 
"            podem ser explicados pelo gerente de vendas ou por Le�o do Portal Cronos." + "\r\n" +
"            " + "\r\n" +
"" + "\r\n" +
"A. Poss�veis causas de paradas da integra��o " + f.SiglaSistemaFornecedor + "/PCronos:" + "\r\n" +
"=============================================================" + "\r\n" +  
"  - O servidor da " + f.NomeFantasiaEmpresa + " que est� hospedando este servi�o est� desligado?" + "\r\n" +
"" + "\r\n" +
"  - O disco r�gido (\"HD\") C:\\ est� cheio?" + "\r\n" +
"" + "\r\n" +
"  - Houve uma atualiza��o de Windows?" + "\r\n" +
"    Isso pode acabar com o servi�o. Neste caso, entrar em contato com \"Eric Jo\" via Skype (ou com eric.jo@bol.com.br via email)." + "\r\n" +
"" + "\r\n" +
"  - Houve uma atualiza��o de Java?" + "\r\n" +
"    Isso acaba com o servi�o com certeza. Neste caso, entrar em contato com \"Eric Jo\" via Skype (ou com eric.jo@bol.com.br via email)." + "\r\n" +
"" + "\r\n" +
"  - A mem�ria RAM est� 100 % cheia ou perto disso?" + "\r\n" +
"    No caso que a mem�ria RAM estiver mais de 95 %, favor deixar assim por enquanto " + "\r\n" +
"    e n�o fazer nada ainda e avisar o contato \"Eric Jo\" via Skype (ou eric.jo@bol.com.br via email) " + "\r\n" +
"    primeiro para conectar via " + f.AplicativoDesktopRemoto + " para testar uma solu��o autom�tica para este problema.  " + "\r\n" +
"    (Isso � necess�rio apenas na primeira vez que isso acontece.) " + "\r\n" +
"" + "\r\n" +
"  - Algum anti-virus est� travando a m�quina? (100 % mem�ria RAM)" + "\r\n" +
"" + "\r\n";
		
		
if (f.SiglaSistemaFornecedor.equals("SAP"))		
	this.conteudo += "" +
"  - O endere�o IP, usu�rio ou senha do SAP_API mudou? " + "\r\n" + 
"    Neste caso verifica se a mesma foi atualizada tamb�m no seguinte arquivo de configura��o: " + "\r\n" +
"    C:\\Arquivos de Programas PC\\Integra��o Fornecedor - Portal Cronos\\conf\\SAP_API.jcoDestination" + "\r\n" +
"" + "\r\n";

	
this.conteudo += "" +
"  - O endere�o IP, usu�rio ou senha da base de dados " + f.getTipoBaseDeDados() + " mudou?" + "\r\n" +
"" + "\r\n" +
"  - A senha do usu�rio " + f.usuarioWebservice + " do site do Portal Cronos foi alterada no site? " + "\r\n" + 
"    Neste caso verifica se a mesma foi atualizada tamb�m no seguinte arquivo de configura��o: " + "\r\n" +
"    C:\\Arquivos de Programas PC\\Integra��o Fornecedor - Portal Cronos\\conf\\Integra��o Fornecedor - Portal Cronos.properties" + "\r\n" + 
"" + "\r\n" +
"  - No \"Gerenciador de Tarefas\" (\"Task Manager\") ordenar por nome do processo, e procurar" + "\r\n" + 
"    \"Java(TM) Platform SE binary\". " + "\r\n" +
"    Se n�o tiver nenhum processo com este nome, tudo est� certo." + "\r\n" +   
"    Se tiver um ou mais processos na lista, verifica com o bot�o � direita do mouse," + "\r\n" + 
"    na aba \"Geral\" se o \"Local\" de cada processo for igual a \"C:/" + f.dirProgramFiles + "/Java/" + f.versaoJRE + "/bin/java.exe\"." + "\r\n" + 
"    Se n�o tiver nenhum processo com este \"Local\", tudo est� certo.   " + "\r\n" +
"    Se tiver um processo com este \"Local\" enquanto que as ofertas autom�ticas est�o paradas," + "\r\n" + 
"    este processo est� travado. Neste caso, com o bot�o a direita do mouse clicar em \"Finalizar tarefa\".  " + "\r\n" +
"    Se tiver um processo com este \"Local\" enquanto que as ofertas autom�ticas est�o processando " + "\r\n" +
"    normalmente, tudo est� certo e n�o mexe com este processo.   " + "\r\n" +
"        " + "\r\n" +
"  - Outras ideias suas (Firewall, proxy, portas, Internet, etc)." + "\r\n" + 
"" + "\r\n" +
"  - No �ltimo caso, se n�o conseguir resolver, " + "\r\n" +
"    favor entrar em contato com o contato \"Eric Jo\" via Skype (ou com eric.jo@bol.com.br via email)," + "\r\n" + 
"    e colocar o " + f.AplicativoDesktopRemoto + " no ar, e informar o ID e a senha via Skype, " + "\r\n" +
"    para ele ver se a causa foi alguma falha dentro do servi�o das ofertas autom�ticas." + "\r\n" +
"" + "\r\n" +
"" + "\r\n" +
"B. Como verificar se o servi�o realmente voltou a funcionar:" + "\r\n" +
"============================================================" + "\r\n" +
"No caso que voc� mesmo consegue resolver o problema: " + "\r\n" +
"  1. Favor verificar ap�s 15 minutos se o servi�o atualizou o arquivo" + "\r\n" + 
"     \"C:/ProgramData/PortalCronos/Logs/Local/TemposExecu��o.log\" no final do arquivo." + "\r\n" +  
"      " + "\r\n" +
"  2. Em seguida: " + "\r\n" +
"     (i).  No caso de sucesso, favor verificar tamb�m se as cota��es pendentes foram ofertadas automaticamente." + "\r\n" + 
"           Se voc� n�o tiver um usu�rio/senha no site do Portal Cronos, favor verificar isso com os vendedores. " + "\r\n" +
"     (ii). No caso que as ofertas autom�ticas ainda n�o voltaram a funcionar, " + "\r\n" +
"           veja as outras poss�veis causas neste manual. " + "\r\n" +
"  " + "\r\n" +
"  " + "\r\n" +
"C. Outras dicas:" + "\r\n" +
"================" + "\r\n" +
"- O servidor de hospedagem do servi�o das ofertas autom�ticas" + "\r\n" + 
"  � o servidor no qual existe um diret�rio C:\\Arquivos de Programas PC\\ " + "\r\n" +
"             " + "\r\n" +
"" + "\r\n";
	}
	
	
}
