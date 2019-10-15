package pcronos.integracao.fornecedor;

import java.util.HashMap;
import java.util.Map.Entry;

public class FornecedorRepositorio {

	public static HashMap<Integer, Fornecedor> hashMap;

    static {
		hashMap = new HashMap<Integer, Fornecedor>(); 
		final int qtdFornecedores = 13;
		Fornecedor[] f = new Fornecedor[qtdFornecedores];

		for (int j=0; j < (qtdFornecedores); j++) {
			f[j] = new Fornecedor();			
		}
		
		int i = 0;
		
		f[i].IdFornecedor = 2016;
		f[i].NomeFantasiaEmpresa = "Teste Windows Server 2016";
		f[i].versaoIntegrador = "";
		f[i].SiglaSistemaFornecedor = "WinThor";
		f[i].IsServicoNuvem = false;
		f[i].EmailResponsavelTI = "";
		f[i].EmailResponsavelTIAlternativo = "";
		f[i].ApelidoResponsavelTI = "";
		f[i].ApelidoResponsavelTIAlternativo = "";
		f[i].AplicativoDesktopRemoto = "";
		f[i].usuarioWebservice = "";
		f[i].versaoJRE = "";
		f[i].tipoSO = "Windows Server 2016";
		f[i].dirProgramFiles = "Program Files";

		i++;
		f[i].IdFornecedor = null;
		f[i].NomeFantasiaEmpresa = "Monitoramento";
		f[i].versaoIntegrador = "3.2.0";
		f[i].SiglaSistemaFornecedor = "PCronos";
		f[i].IsServicoNuvem = false;
		f[i].EmailResponsavelTI = "";
		f[i].EmailResponsavelTIAlternativo = "";
		f[i].ApelidoResponsavelTI = "";
		f[i].ApelidoResponsavelTIAlternativo = "";
		f[i].AplicativoDesktopRemoto = "mstsc";
		f[i].versaoJRE = "jre1.8.0_92";
		f[i].tipoJRE = "(.. bit)";
		f[i].tipoSO = "Windows Server 2008 R2 SP1";
		f[i].dirProgramFiles = "Program Files";
		f[i].DiscoIntegrador = "C";
		f[i].MemoriaRamLivre = "1.4 - 1.9 GB";

		i++;
		f[i].IdFornecedor = 13;
		f[i].NomeFantasiaEmpresa = "Formaggio";
		f[i].versaoIntegrador = "2.6";
		f[i].SiglaSistemaFornecedor = "APS";
		f[i].IsServicoNuvem = false;
		f[i].EmailResponsavelTI = "projetosti@formaggio.com.br";  
		f[i].EmailResponsavelTIAlternativo = "ti@formaggio.com.br??????"; 
		f[i].SkypeResponsavelTI = "live:projetosti_1 = Geymison Lima - TI Formaggio";
		f[i].SkypeResponsavelTIAlternativo = "Braytner Vasconcelos";
		f[i].ApelidoResponsavelTI = "Geymison";
		f[i].ApelidoResponsavelTIAlternativo = "";
		f[i].FuncaoResponsavelTI = "Coordenador de Projetos de TI";
		f[i].FuncaoResponsavelTIAlternativo = "";
		f[i].TelefoneResponsavelTI = "";
		f[i].AplicativoDesktopRemoto = "Team Viewer";
		f[i].usuarioWebservice = "ws-formaggio";
		f[i].versaoJRE = "jre1.8.0_92";
		f[i].tipoJRE = "(.. bit)";
		f[i].tipoSO = "Windows Server 2012 R2";
		f[i].dirProgramFiles = "Program Files";
		f[i].cnpjFornecedor = "02870737000190";
		f[i].DiscoIntegrador = "C";
		f[i].MemoriaRamLivre = "";

		i++;
		f[i].IdFornecedor = 947;
		f[i].NomeFantasiaEmpresa = "JR Distribui��o";
		f[i].versaoIntegrador = "2.8.1";
		f[i].SiglaSistemaFornecedor = "WinThor";
		f[i].IsServicoNuvem = false;
		f[i].EmailResponsavelTI = "jrembalagem.ti@gmail.com"; 
		f[i].EmailResponsavelTIAlternativo = "";
		f[i].SkypeResponsavelTI = "ivan barros";
		f[i].ApelidoResponsavelTI = "Ivan";
		f[i].ApelidoResponsavelTIAlternativo = "";
		f[i].TelefoneResponsavelTI = "";
		f[i].AplicativoDesktopRemoto = "AnyDesk";
		f[i].IdAplicativoDesktopRemoto = "734228115";
		f[i].usuarioWebservice = "ws-jrembalagem";
		f[i].versaoJRE = "jre1.8.0_92";
		f[i].tipoJRE = "(.. bit)";
		f[i].tipoSO = "Windows 10 Pro";
		f[i].SO32ou64bit = "64bit";
		f[i].dirProgramFiles = "Program Files";
		f[i].cnpjFornecedor = "00680755000265";
		f[i].DiscoIntegrador = "C";
		f[i].MemoriaRamLivre = "";

		i++;
		f[i].IdFornecedor = 30;
		f[i].NomeFantasiaEmpresa = "Prolac";
		f[i].versaoIntegrador = "2.8.4";
		f[i].SiglaSistemaFornecedor = "WinThor";
		f[i].IsServicoNuvem = false;
		f[i].EmailResponsavelTI = "mscomprolac@gmail.com"; 
		f[i].EmailResponsavelTIAlternativo = "marcelo@casadoqueijo.net.br";
		f[i].SkypeResponsavelTI = "marcos.scognamiglio";
		f[i].SkypeResponsavelTIAlternativo = "Marcelo Bezerra";
		f[i].ApelidoResponsavelTI = "Marcos";
		f[i].ApelidoResponsavelTIAlternativo = "Marcelo";
		f[i].TelefoneResponsavelTI = "";
		f[i].AplicativoDesktopRemoto = "AnyDesk";
		f[i].IdAplicativoDesktopRemoto = "685 885 292";
		f[i].usuarioWebservice = "ws-prolac";
		f[i].versaoJRE = "jre1.8.0_92";
		f[i].tipoJRE = "(64 bit)";
		f[i].tipoSO = "Windows Server 2012 R2";
		f[i].dirProgramFiles = "Program Files";
		f[i].cnpjFornecedor = "07182763000140"; 
		f[i].DiscoIntegrador = "C";
		f[i].MemoriaRamLivre = "";

		i++;
		f[i].IdFornecedor = 21;
		f[i].NomeFantasiaEmpresa = "Mar�timos";
		f[i].versaoIntegrador = "1.2.3";
		f[i].SiglaSistemaFornecedor = "WinThor";
		f[i].IsServicoNuvem = false;
		f[i].EmailResponsavelTI = "felipe.lolaia@maritimospescados.com.br"; 
		f[i].EmailResponsavelTIAlternativo = "";
		f[i].SkypeResponsavelTI = "Felipe Lolaia";
		f[i].ApelidoResponsavelTI = "Felipe";
		f[i].ApelidoResponsavelTIAlternativo = "";
		f[i].TelefoneResponsavelTI = "";
		f[i].AplicativoDesktopRemoto = "Team Viewer";
		f[i].usuarioWebservice = "ws-mpescados";
		f[i].versaoJRE = "jre1.8.0_92";
		f[i].tipoJRE = "(.. bit)";
		f[i].tipoSO = "?????????????????????";
		f[i].dirProgramFiles = "Program Files";
		f[i].cnpjFornecedor = "04666316000178";
		f[i].DiscoIntegrador = "C";
		f[i].MemoriaRamLivre = "";

		i++;
		f[i].IdFornecedor = 170;
		f[i].NomeFantasiaEmpresa = "SOST";
		f[i].versaoIntegrador = "2.3.1";
		f[i].SiglaSistemaFornecedor = "WinThor";
		f[i].IsServicoNuvem = false;
		f[i].EmailResponsavelTI = "informatica@sost.com.br OU cleijonatassilva@sost.com.br"; 
		f[i].EmailResponsavelTIAlternativo = "informatica@sost.com.br OU carlossena@sost.com.br";
		f[i].SkypeResponsavelTI = "Cleijonatas S Silva";
		f[i].SkypeResponsavelTIAlternativo = "Sena Junior";
		f[i].ApelidoResponsavelTI = "Cleijonatas";
		f[i].ApelidoResponsavelTIAlternativo = "Sena";
		f[i].TelefoneResponsavelTI = "";
		f[i].AplicativoDesktopRemoto = "AnyDesk";
		f[i].usuarioWebservice = "ws-sost";
		f[i].versaoJRE = "jre1.8.0_92";
		f[i].tipoJRE = "(.. bit)";
		f[i].tipoSO = "Windows Server 2008 R2 SP1";
		f[i].dirProgramFiles = "Program Files";
		f[i].cnpjFornecedor = "07041307000180";
		f[i].DiscoIntegrador = "C";
		f[i].MemoriaRamLivre = "";

		i++;
		f[i].IdFornecedor = 60;
		f[i].NomeFantasiaEmpresa = "Karne Keijo";
		f[i].versaoIntegrador = "2.4.1";
		f[i].SiglaSistemaFornecedor = "SAP";
		f[i].IsServicoNuvem = false;
		f[i].EmailResponsavelTI = "timons@kk.com.br";
		f[i].EmailResponsavelTIAlternativo = "";
		f[i].SkypeResponsavelTI = "Timon Dourado";
		f[i].ApelidoResponsavelTI = "Timon";
		f[i].ApelidoResponsavelTIAlternativo = "";
		f[i].TelefoneResponsavelTI = "";
		f[i].AplicativoDesktopRemoto = "Team Viewer";
		f[i].usuarioWebservice = "ws-karnekeijo";
		f[i].versaoJRE = "jre1.8.0_92";
		f[i].tipoJRE = "(.. bit)";
		f[i].tipoSO = "Windows Server 2008 R2 SP1";
		f[i].dirProgramFiles = "Program Files";
		f[i].cnpjFornecedor = "24150377000195";
		f[i].DiscoIntegrador = "C";
		f[i].MemoriaRamLivre = "";

		i++;
		f[i].IdFornecedor = 33;
		f[i].NomeFantasiaEmpresa = "Comal";
		f[i].versaoIntegrador = "2.8.2";
		f[i].SiglaSistemaFornecedor = "WinThor";
		f[i].IsServicoNuvem = false;
		f[i].EmailResponsavelTI = "ti@comalcomercio.com.br";
		f[i].EmailResponsavelTIAlternativo = "";
		f[i].SkypeResponsavelTI = "Rildson Carlos";
		f[i].ApelidoResponsavelTI = "Rildson"; 
						// Tem vontade para ajudar, por�m � muito incompetente 
						// e muito burro, tem que explicar tudo detalhadamente
		f[i].ApelidoResponsavelTIAlternativo = "";
		f[i].TelefoneResponsavelTI = "98609-4887";
		f[i].AplicativoDesktopRemoto = "AnyDesk";
		f[i].usuarioWebservice = "ws-comal";
		f[i].versaoJRE = "jre1.8.0_92";
		f[i].tipoJRE = "(.. bit)";
		f[i].tipoSO = "Windows Server 2016"; // Windows Server 2016 Standard
		f[i].dirProgramFiles = "Program Files";
		f[i].cnpjFornecedor = "07534303000133";
		f[i].DiscoIntegrador = "C";
		f[i].MemoriaRamLivre = "";

		i++;
		f[i].IdFornecedor = 171;
		f[i].NomeFantasiaEmpresa = "Prop�o";
		f[i].versaoIntegrador = "2.6.1";
		f[i].SiglaSistemaFornecedor = "WinThor";
		f[i].IsServicoNuvem = false;
		f[i].EmailResponsavelTI = "ti@propao.com.br";
		f[i].EmailResponsavelTIAlternativo = "";
		f[i].SkypeResponsavelTI = "Apoio.propao@hotmail.com";
		f[i].ApelidoResponsavelTI = "Elthon"; 
						// Desenrolado, respons�vel para o cadastro de De-Para�s
		f[i].ApelidoResponsavelTIAlternativo = "";
		f[i].TelefoneResponsavelTI = "99535-1999";
		f[i].AplicativoDesktopRemoto = "AnyDesk";
		f[i].usuarioWebservice = "ws-propao";
		f[i].versaoJRE = "jre1.8.0_92";
		f[i].tipoJRE = "(.. bit)";
		f[i].tipoSO = "Windows Server 2008 R2 SP1"; // Na verdade "Windows Server 2008 R2 Enterprise" sem nenhum service pack.........
		f[i].dirProgramFiles = "Program Files";
		f[i].cnpjFornecedor = "24407389000233";
		f[i].DiscoIntegrador = "C";
		f[i].MemoriaRamLivre = "";

		i++;
		f[i].IdFornecedor = 14;
		f[i].NomeFantasiaEmpresa = "Padeir�o";
		f[i].versaoIntegrador = "2.8.3";
		f[i].SiglaSistemaFornecedor = "WinThor";
		f[i].IsServicoNuvem = false;
		f[i].EmailResponsavelTI = "tiagoautran@padeirao.com";
		f[i].EmailResponsavelTIAlternativo = "";
		f[i].SkypeResponsavelTI = "tiagoautran";
		f[i].ApelidoResponsavelTI = "Tiago"; 
		f[i].ApelidoResponsavelTIAlternativo = "";
		f[i].TelefoneResponsavelTI = "99116-8070 ou 3073-4551";
		f[i].AplicativoDesktopRemoto = "AnyDesk";
		f[i].usuarioWebservice = "ws-padeirao";
		f[i].versaoJRE = "jre1.8.0_111";
		f[i].tipoJRE = "(.. bit)";
		f[i].tipoSO = "Windows Server 2012 R2"; // Windows Server 2012 R2 Standard (sem nenhum service pack)
		f[i].dirProgramFiles = "Program Files";
		f[i].cnpjFornecedor = "03042263000151";
		f[i].DiscoIntegrador = "C";
		f[i].MemoriaRamLivre = "";

		
		
		
		String versaoIntegradorApsCloud = "3.0.0";
		String SiglaSistemaFornecedorApsCloud = "APS";
		String AplicativoDesktopRemotoApsCloud = "AnyDesk";
		String tipoSOApsCloud = "Windows Server 2008 R2 SP1"; 
		String dirProgramFilesApsCloud = "Program Files (x86)";
		String versaoJREApsCloud = "jre1.8.0_191";
		String tipoJREApsCloud = "(32 bit)";
		String DiscoIntegradorApsCloud = "C";
		String MemoriaRamLivreApsCloud = "0 - 200 MB";
		
		String EmailResponsavelTIApsCloud = "bernardino@apsinformatica.com.br";
		String SkypeResponsavelTIApsCloud = "Bernardino Borba (live:berna31pe)";
		String ApelidoResponsavelTIApsCloud = "Bernardino"; 
		String FuncaoResponsavelTIApsCloud = "S�cio do APS";
		String TelefoneResponsavelTIApsCloud = "99780-1192";
		
		String EmailResponsavelTIAlternativoApsCloud = "saulo@apsinformatica.com.br";		
		String SkypeResponsavelTIAlternativoApsCloud = "Saulo Gomes de Lima (saulo_analista)";		
		String ApelidoResponsavelTIAlternativoApsCloud = "Saulo"; // Algu�m foi demitido, provavelmente foi ele
		String FuncaoResponsavelTIAlternativoApsCloud = "Analista";
		
		String EmailResponsavelTI_NuvemApsCloud = "bernardino@apsinformatica.com.br";
		String SkypeResponsavelTI_NuvemApsCloud = "Bernardino Borba (live:berna31pe)"; 
		String ApelidoResponsavelTI_NuvemApsCloud = "Bernardino"; 
		String TelefoneResponsavelTI_NuvemApsCloud = "99780-1192"; 

		
		
		i++;
		f[i].IdFornecedor = 1995; // APS Cloud - primeiro fornecedor de muitos
		f[i].NomeFantasiaEmpresa = "Marizpan";   // APS Cloud - primeiro fornecedor de muitos
		f[i].cnpjFornecedor = "12286800000108";  // APS Cloud - primeiro fornecedor de muitos
		f[i].versaoIntegrador = versaoIntegradorApsCloud;
		f[i].SiglaSistemaFornecedor = SiglaSistemaFornecedorApsCloud;

		f[i].IsServicoNuvem = true;
		f[i].SequenciaInstanciaNuvem = 1;

		f[i].AplicativoDesktopRemoto = AplicativoDesktopRemotoApsCloud;
		f[i].usuarioWebservice = "ws-marizpan";  // APS Cloud - primeiro fornecedor de muitos
		f[i].tipoSO = tipoSOApsCloud; 
		f[i].dirProgramFiles = dirProgramFilesApsCloud;
		f[i].versaoJRE = versaoJREApsCloud;
		f[i].tipoJRE = tipoJREApsCloud;
		f[i].DiscoIntegrador = DiscoIntegradorApsCloud;
		f[i].MemoriaRamLivre = MemoriaRamLivreApsCloud;
		f[i].ResponsavelDeParasProdutos = "Uma vendedora";  // APS Cloud - primeiro fornecedor de muitos

		f[i].EmailResponsavelTI = EmailResponsavelTIApsCloud;
		f[i].SkypeResponsavelTI = SkypeResponsavelTIApsCloud;
		f[i].ApelidoResponsavelTI = ApelidoResponsavelTIApsCloud; 
		f[i].FuncaoResponsavelTI = FuncaoResponsavelTIApsCloud;
		f[i].TelefoneResponsavelTI = TelefoneResponsavelTIApsCloud;
		
		f[i].EmailResponsavelTIAlternativo = EmailResponsavelTIAlternativoApsCloud;		
		f[i].SkypeResponsavelTIAlternativo = SkypeResponsavelTIAlternativoApsCloud;		
		f[i].ApelidoResponsavelTIAlternativo = ApelidoResponsavelTIAlternativoApsCloud;
		f[i].FuncaoResponsavelTIAlternativo = FuncaoResponsavelTIAlternativoApsCloud;
		
		f[i].EmailResponsavelTI_Nuvem = EmailResponsavelTI_NuvemApsCloud;
		f[i].SkypeResponsavelTI_Nuvem = SkypeResponsavelTI_NuvemApsCloud; 
		f[i].ApelidoResponsavelTI_Nuvem = ApelidoResponsavelTI_NuvemApsCloud;
		f[i].TelefoneResponsavelTI_Nuvem = TelefoneResponsavelTI_NuvemApsCloud;

		

		
		i++;
		f[i].IdFornecedor = 1; 
		f[i].NomeFantasiaEmpresa = "Atacamax";   
		f[i].cnpjFornecedor = "08305623000184";  
		f[i].versaoIntegrador = "3.2.0"; // versaoIntegradorApsCloud;
		f[i].SiglaSistemaFornecedor = SiglaSistemaFornecedorApsCloud;

		f[i].IsServicoNuvem = false; // true;
	 // f[i].SequenciaInstanciaNuvem = 2;

		f[i].AplicativoDesktopRemoto = AplicativoDesktopRemotoApsCloud;
		f[i].usuarioWebservice = "ws-atacamax";  
		f[i].tipoSO = "Windows Server 2012 R2"; //tipoSOApsCloud; 
		f[i].dirProgramFiles = "Program Files (x86)"; // dirProgramFilesApsCloud;
		f[i].versaoJRE = "jre1.8.0_211"; // versaoJREApsCloud;
		f[i].tipoJRE = "(32 bit)";  // tipoJREApsCloud
		f[i].DiscoIntegrador = "C"; // DiscoIntegradorApsCloud;
		f[i].MemoriaRamLivre = "17 GB"; // MemoriaRamLivreApsCloud;
		f[i].ResponsavelDeParasProdutos = "Uma vendedora";  

		f[i].EmailResponsavelTI = EmailResponsavelTIApsCloud;
		f[i].SkypeResponsavelTI = SkypeResponsavelTIApsCloud;
		f[i].ApelidoResponsavelTI = ApelidoResponsavelTIApsCloud; 
		f[i].FuncaoResponsavelTI = FuncaoResponsavelTIApsCloud;
		f[i].TelefoneResponsavelTI = TelefoneResponsavelTIApsCloud;
		
		f[i].EmailResponsavelTIAlternativo = EmailResponsavelTIAlternativoApsCloud;		
		f[i].SkypeResponsavelTIAlternativo = SkypeResponsavelTIAlternativoApsCloud;		
		f[i].ApelidoResponsavelTIAlternativo = ApelidoResponsavelTIAlternativoApsCloud;
		f[i].FuncaoResponsavelTIAlternativo = FuncaoResponsavelTIAlternativoApsCloud;
		
		f[i].EmailResponsavelTI_Nuvem = EmailResponsavelTI_NuvemApsCloud;
		f[i].SkypeResponsavelTI_Nuvem = SkypeResponsavelTI_NuvemApsCloud; 
		f[i].ApelidoResponsavelTI_Nuvem = ApelidoResponsavelTI_NuvemApsCloud;
		f[i].TelefoneResponsavelTI_Nuvem = TelefoneResponsavelTI_NuvemApsCloud;

		
/*  Template:
    =========
    !!!!!!!!! N�o se esque�a incremementar o vari�vel constante "qtdFornecedores" mais 1 !!!!!
    
		i++;
		f[i].IdFornecedor = ;
		f[i].NomeFantasiaEmpresa = "";
		f[i].cnpjFornecedor = "";
		f[i].versaoIntegrador = "";
		f[i].SiglaSistemaFornecedor = "";
		f[i].AplicativoDesktopRemoto = "AnyDesk";
		f[i].usuarioWebservice = "";
		f[i].tipoSO = ""; 
		f[i].dirProgramFiles = "";
		f[i].versaoJRE = "";
		f[i].tipoJRE = "(.. bit)";
		f[i].DiscoIntegrador = "C";
		f[i].MemoriaRamLivre = "";
		f[i].ResponsavelDeParasProdutos = "Uma vendedora";

		f[i].EmailResponsavelTI = "";
		f[i].SkypeResponsavelTI = "";
		f[i].ApelidoResponsavelTI = ""; 
		f[i].FuncaoResponsavelTI = "";
		f[i].TelefoneResponsavelTI = "";
		
		f[i].EmailResponsavelTIAlternativo = "";		
		f[i].SkypeResponsavelTIAlternativo = "";		
		f[i].ApelidoResponsavelTIAlternativo = "";
		f[i].FuncaoResponsavelTIAlternativo = "";
		
		f[i].EmailResponsavelTI_Nuvem = "";
		f[i].SkypeResponsavelTI_Nuvem = ""; 
		f[i].ApelidoResponsavelTI_Nuvem = ""; 
 */
		
		for (int j=0; j < (qtdFornecedores); j++) {
			hashMap.put(f[j].IdFornecedor, f[j]);
		}
		
    }
	
	public FornecedorRepositorio() {
	}
	

	// O m�todo getIdFornecedorByCnpj(String cnpj) nunca fica chamado se siglaSistema == "PCronos", 
	// ent�o n�o precisa retornar um objeto Integer (nullable). 
	// Observa��o: se um dia precisar trocar "int" por "Integer", tem que tomar providencias para  
	// continuar funcionando com valor -1, veja https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
	int getIdFornecedorByCnpj(String cnpj) throws Exception 
	{
		if (cnpj == null || cnpj.equals("11222333444455"))
			throw new Exception("Erro! O CNPJ da empresa fornecedora n�o foi informado no arquivo C:/Arquivos de Programas PC/Integra��o Fornecedor - Portal Cronos/" + Constants.DIR_ARQUIVOS_PROPERTIES + (!IntegracaoFornecedorCompleta.IsSistemaFornecedorNuvem ? Constants.NOME_ARQUIVO_PROPERTIES : IntegracaoFornecedorCompleta.nomeArquivoConfigNuvemAtual) + "!");
		else 
		{
			Fornecedor f = null;
			
	        for (Entry<Integer, Fornecedor> entry : hashMap.entrySet()) {
		        Object value = entry.getValue();
		        
		        if ( !Utils.isNullOrBlank(((Fornecedor)value).cnpjFornecedor) && ((Fornecedor)value).cnpjFornecedor.equals(cnpj)) 
		        {
		        	f = ((Fornecedor)value);
		        }
		    }

			if (f == null)
				throw new Exception("Erro! O CNPJ da empresa fornecedora \"" + cnpj + "\" no arquivo C:/Arquivos de Programas PC/Integra��o Fornecedor - Portal Cronos/" + Constants.DIR_ARQUIVOS_PROPERTIES + (!IntegracaoFornecedorCompleta.IsSistemaFornecedorNuvem ? Constants.NOME_ARQUIVO_PROPERTIES : IntegracaoFornecedorCompleta.nomeArquivoConfigNuvemAtual) + " est� errado!");
			else
	            return f.IdFornecedor;
		}
	}

	
	
	public Fornecedor getFornecedor(Integer idFornecedor) throws Exception {		
		Fornecedor f = hashMap.get(idFornecedor);
		
		if (f == null)
			throw new Exception("Erro: idFornecedor " + idFornecedor.toString() + " n�o existe");

		return f;
	}

}
