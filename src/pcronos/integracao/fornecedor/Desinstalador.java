package pcronos.integracao.fornecedor;

import pcronos.integracao.fornecedor.Utils;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;


public class Desinstalador {

	  static 
	  {
		  Inicializar();
	  }

	  public static final String NOME_ARQUIVO_PROPERTIES = "conf/Integra��o Fornecedor - Portal Cronos.properties";
	  public static        String siglaSistema;
	  public static        String cnpjFornecedor;
	  public static        String erroStaticConstructor;

	  
	  public static String printStackTraceToString(Exception ex)
	  {
		
		   // Writer writer = new StringWriter();
		      StringWriter sWriter = new StringWriter();
		      ex.printStackTrace(new PrintWriter(sWriter));
		      return sWriter.toString();
	  
	  }

	  private static void Inicializar()
	  {
		    try {
		        erroStaticConstructor = null;

		        Properties config = new Properties();
		        config.load(new FileInputStream(NOME_ARQUIVO_PROPERTIES));

		        siglaSistema   = config.getProperty("SiglaSistema");
		        cnpjFornecedor = config.getProperty("CnpjFornecedor");

	      } 
	      catch (Exception ex) {
	        try
	        {
	          erroStaticConstructor = "Erro imprevisto! " + printStackTraceToString(ex);
	        }
	        catch (Exception ex2)
	        {
	          throw new ExceptionInInitializerError(ex2);
	        }
	      }
	  }

	  
	  public static void main(String[] args) {
		try {	
			
			// siglaSistema == "PCronos" no caso de instala��o do servi�o de monitoramento autom�tico 
			// no servidor de aplica��o do Portal Cronos:
    		if (!siglaSistema.equals("PCronos")) {
			    FornecedorRepositorio fRep = new FornecedorRepositorio();
             // int idFornecedor = Integer.parseInt(args[0]);
		   	    int idFornecedor = fRep.getIdFornecedorByCnpj(cnpjFornecedor);
			
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
