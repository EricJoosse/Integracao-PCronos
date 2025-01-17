package pcronos.integracao.fornecedor;

import java.awt.Window;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import mslinks.ShellLink;

public class ManualManutencao {

	public String nomeArquivo = null;
	private String nomeAtalho = null;
	private String conteudo;
	private Fornecedor fornecedor;
	private String siglaSistemaFornecedor;
	private String caminhoManual = null;
	private String caminhoAtalhoManual = null;
	private String caminhoAtalhoManualPai = null;
	
	
	private void setCaminhoManualMaisCaminhoAtalho() throws Exception { 	
    	this.caminhoManual = "C:/Arquivos de Programas PC/";
	  	this.caminhoAtalhoManualPai = "C:/ProgramData/Microsoft/Windows/Start Menu/Programs/Portal Cronos/";

    	if (this.fornecedor.IsServicoNuvem) {
		  	this.caminhoAtalhoManual = "C:/ProgramData/Microsoft/Windows/Start Menu/Programs/Portal Cronos/Integrador " + this.fornecedor.SiglaSistemaFornecedor + " Cloud/";
		}
		else if (   this.fornecedor.tipoSO.equals("Windows Server 2019") 
				  || this.fornecedor.tipoSO.equals("Windows Server 2016") 
				  || this.fornecedor.tipoSO.equals("Windows Server 2012 R2")
				  || this.fornecedor.tipoSO.equals("Windows Server 2008 R2 SP1")
				  || this.fornecedor.tipoSO.equals("Windows 10 Pro")) 
		{
		  	this.caminhoAtalhoManual = "C:/ProgramData/Microsoft/Windows/Start Menu/Programs/Portal Cronos/Integrador " + this.siglaSistemaFornecedor + "/";
	    }
	    else
	    	throw new Exception("O sistema operacional \"" + this.fornecedor.tipoSO + "\" ainda est� sem diret�rio padr�o definido para o Manual de Manuten��o para a TI.");
	}


	private void setNomeArquivoMaisAtalho() throws Exception { 	
		this.nomeArquivo = "Manual solucionamento paradas da integra��o Portal Cronos - v1.5.1 (13.09.2020).txt";
		this.nomeAtalho = "Resolver Paradas.lnk";

//		if (this.fornecedor.tipoSO.equals("Windows Server 2008 R2 SP1")) {
//			this.nomeArquivo = "Manual solucionamento paradas da integra��o Portal Cronos - v1.5.0 (20.09.2019).txt";
//	    }
//	    else if (this.fornecedor.tipoSO.equals("Windows Server 2012 R2")) {
//			this.nomeArquivo = "Portal Cronos - Manual solucionamento paradas da integra��o - v1.5.0 (20.09.2019).txt";
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
        
        
        if (     this.fornecedor.IsServicoNuvem
        	  || this.fornecedor.tipoSO.equals("Windows Server 2019") 
              || this.fornecedor.tipoSO.equals("Windows Server 2016") 
              || this.fornecedor.tipoSO.equals("Windows 10 Pro")
              || this.fornecedor.tipoSO.equals("Windows Server 2012 R2")
              || this.fornecedor.tipoSO.equals("Windows Server 2008 R2 SP1")
            ) {
        	
        	File diretorioAtalhoManual = new File(caminhoAtalhoManual);
        	if (!diretorioAtalhoManual.exists()) 
        	{ 
       		    diretorioAtalhoManual.mkdirs();
        	}
        	else {
        		// Excluir eventuais atalhos antigos:
        		for (final File file : diretorioManual.listFiles()) 
        		{
        			if (file.getName().endsWith(".lnk"))
       			       file.delete();
        		}
        	}

        	if (!this.fornecedor.IsServicoNuvem)
        	{
        		ShellLink sLink = ShellLink.createLink(caminhoManual + "Info Geral v1.6.0 (01.11.2019).txt")
                                           .setIconLocation("%SystemRoot%\\system32\\SHELL32.dll");
				sLink.getHeader().setIconIndex(277);
				sLink.saveTo(caminhoAtalhoManual + "Info Geral.lnk");

				
				ShellLink sLink2 = ShellLink.createLink(caminhoManual + nomeArquivo)
											.setIconLocation("%SystemRoot%\\system32\\SHELL32.dll");
										 // .setIconLocation("C:/Arquivos de Programas PC/Integra��o Fornecedor - Portal Cronos/res/Troubleshooting.ico");
				sLink2.getHeader().setIconIndex(23); // (35) ou (0)
				sLink2.saveTo(caminhoAtalhoManual + nomeAtalho);
        	}
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
		
        if (       this.fornecedor.IsServicoNuvem
          	    || this.fornecedor.tipoSO.equals("Windows Server 2019") 
          	    || this.fornecedor.tipoSO.equals("Windows Server 2016") 
        		|| this.fornecedor.tipoSO.equals("Windows 10 Pro")
                || this.fornecedor.tipoSO.equals("Windows Server 2012 R2")
                || this.fornecedor.tipoSO.equals("Windows Server 2008 R2 SP1")
           ) {
        	File diretorioAtalhoManual = new File(caminhoAtalhoManual);
        	
        	if(diretorioAtalhoManual.exists()) { 
        	    boolean temOutrosAtalhos = false;
        		
        	    // O seguinte remove os seguintes atalhos no menu de Windows:
        	    // (i)  Em todos os casos:
        	    //         - Manual "Resolver Paradas"
        	    // 
        	    // (ii) No caso de ambientes nuvem:
        	    //         - Adicionar Inst�ncia
        	    //         - Remover Inst�ncia
        	    //         - Configura��es Marizpan
        	    //         - Configura��es Atacamax
        	    //         - Configura��es ...etc.....
        		for (final File shortcut : diretorioAtalhoManual.listFiles()) 
        		{
           		 // if (shortcut.getName().startsWith("Manual") && shortcut.getName().endsWith(".lnk"))
           		    if (shortcut.getName().endsWith(".lnk"))
        				shortcut.delete();
        		    else
        		      temOutrosAtalhos = true;
        		}

        	    if (!temOutrosAtalhos) 
        	    {
            	    // Remover os seguintes menus de Windows: 
            	    // (i)  No caso de ambientes n�o-nuvem:
            	    //         - Start Menu/Programs/Portal Cronos/Integrador XXXXXXXX/
            	    // (ii) No caso de ambientes nuvem:
            	    //         - Start Menu/Programs/Portal Cronos/Integrador XXXXXXXX Cloud/
        			diretorioAtalhoManual.delete();
	        	    
	        	    // Remover os seguintes menus de Windows: 
	        	    // Em todos os casos de ambientes nuvem:
	        	    //         - Start Menu/Programs/Portal Cronos/
	            	File diretorioAtalhoManualPai = new File(caminhoAtalhoManualPai);
	            	if (diretorioAtalhoManualPai.exists()) 
	        			 diretorioAtalhoManualPai.delete();
        	    }
        	}
        }
	}
	
	
	
	public ManualManutencao(Fornecedor f, String siglaSistema) throws Exception {
		this.fornecedor = f;
		this.siglaSistemaFornecedor = siglaSistema;
		setNomeArquivoMaisAtalho();
		

        this.conteudo = "" +
"A. Poss�veis causas de paradas da integra��o " + f.SiglaSistemaFornecedor + "/PCronos:" + "\r\n" +
"=============================================================" + "\r\n";
        
if (f.IsServicoNuvem)
    this.conteudo += "  - O servidor nuvem da APS Cloud que est� hospedando este servi�o est� desligado?" + "\r\n";
else
    this.conteudo += "  - O servidor da " + f.NomeFantasiaEmpresa + " que est� hospedando este servi�o est� desligado?" + "\r\n";


this.conteudo +=        
"" + "\r\n" +
"  - O disco r�gido (\"HD\") " + f.DiscoIntegrador + ":\\ est� cheio?" + "\r\n" +
"" + "\r\n" +
"  - Houve uma atualiza��o de Windows?" + "\r\n" +
"    Isso pode travar ou at� acabar com o servi�o. Neste caso, reiniciar o servidor mais uma (segunda) vez," + "\r\n" + 
"    mesmo se o servidor j� foi reinicado uma vez durante a atualiza��o de Windows. " + "\r\n" + 
"    A segunda reiniciada deve ser feita SEM atualiza��o automatica de Windows ao reiniciar o servidor." + "\r\n" +
"    Se o problema ainda persistir, entrar em contato com \"Eric Jo\" via Skype (ou com eric.jo@bol.com.br via email)." + "\r\n" +
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
"" + "\r\n";


if (f.IsServicoNuvem)
    this.conteudo += "  - A(s) senha(s) de um ou mais usu�rios ws-marizpan, ws-atacamax, etc, do site do Portal Cronos foi(foram) alterada(s) no site? " + "\r\n";
else
    this.conteudo += "  - A senha do usu�rio " + f.usuarioWebservice + " do site do Portal Cronos foi alterada no site? " + "\r\n";


this.conteudo += "" +
"    Neste caso verifica se a mesma foi atualizada tamb�m no seguinte arquivo de configura��o: " + "\r\n";


if (f.IsServicoNuvem)
    this.conteudo += "    C:\\Arquivos de Programas PC\\Integra��o Fornecedor - Portal Cronos\\conf\\Integra��o APS - Portal Cronos.<NomeFornecedor>.properties" + "\r\n" ;
else
    this.conteudo += "    C:\\Arquivos de Programas PC\\Integra��o Fornecedor - Portal Cronos\\conf\\Integra��o Fornecedor - Portal Cronos.properties" + "\r\n" ;


this.conteudo += "" +
"" + "\r\n";

	
if ( !(    f.tipoSO.equals("Windows 10 Pro")
	    && f.IdFornecedor == 947
      )
   )
    this.conteudo += "" +
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
"        " + "\r\n";

	
this.conteudo += "" +
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
"  1. Favor verificar ap�s 15 minutos se o servi�o atualizou o arquivo" + "\r\n";

if (f.IsServicoNuvem)
    this.conteudo += "     \"C:/ProgramData/PortalCronos/Logs/Local/TemposExecu��o.<NomeFornecedor>.log\" no final do arquivo." + "\r\n";
else
    this.conteudo += "     \"C:/ProgramData/PortalCronos/Logs/Local/TemposExecu��o.log\" no final do arquivo." + "\r\n";


this.conteudo += "" +
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
