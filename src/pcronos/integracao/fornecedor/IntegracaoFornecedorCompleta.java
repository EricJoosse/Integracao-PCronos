package pcronos.integracao.fornecedor;

import pcronos.integracao.ConfiguracaoException;
import pcronos.integracao.Criptografia;
import pcronos.integracao.EmailAutomatico;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Properties;
import java.util.Locale;
import java.util.List;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.net.URI;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection; 
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.core.MediaType;
import javax.xml.crypto.Data;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.CharacterData;
import org.xml.sax.InputSource;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import oracle.jdbc.driver.OracleDriver ; // http://www.java2s.com/Code/Jar/j/Downloadjdbcoraclejar.htm
import org.firebirdsql.jdbc.FBDriver   ;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.AbapException;

// Javadocs Jersey downloaded de http://repo1.maven.org/maven2/com/sun/jersey/
//                             e https://www.versioneye.com/java/com.sun.jersey.contribs:jersey-multipart/1.12       

//          Jaybird :            http://www.firebirdsql.org/en/jdbc-driver/
//                            ou https://www.versioneye.com/java/org.firebirdsql.jdbc:jaybird-jdk18/2.2.10

//          JDBC Oracle :        N�o existe..........?????????????????????????

import java.io.FilePermission;
import java.security.AccessController;
 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
//import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

/** 
 * 
 * @author Eric Joosse - Cronos Tecnology <p>
 * 
 *Este programa das ofertas autom�ticas: <br>
 *  1. recebe um �nico arquivo XML com todas as cota��es n�o ofertadas juntas no mesmo arquivo XML das cota��es <br>
 *  2. faz um "loop" sobre todas as cota��es e dentro do loop envia os arquivos XML das ofertas um por um de volta <br> &nbsp;&nbsp;
 *     para o Portal Cronos, um arquivo XML para cada cota��o 
 * 
 */
public final class IntegracaoFornecedorCompleta {

   public static final String NOME_ARQUIVO_PROPERTIES = "conf/Integra��o Fornecedor - Portal Cronos.properties";
                              // Using an absolute path (one that starts WITH '/') means that the current 
                              // package is ignored.

	                         //  Relative paths (those WITHOUT a leading '/') mean that the resource 
						     //  will be searched relative to the directory which represents the package 
						     //  the class is in.
                         	//  Quer dizer: in a stand-alone environment (production env. por exemplo, sem Eclipse).  
	                        //  O comando "Run" in Eclipse searches however relative to the project root, and not relative to /src, 
	                        //  and DOS also searches however relative to the current directory DOS is in.
	                        //  No ambiiente de produ��o o Agendador de Tarefas de Windows executa um arquivo .bat 
	                        //  ent�o tamb�m searches relative to the current directory DOS is in, which happens to coincide 
	                        //  with the directory the jar-file is in.	
   
  public static final String NAO_OFERTADA_IMPACTO_SE_ALTERAR = "n�o ofertada";
  public static Locale       locale;
  public static NumberFormat nf;
  public static LocalDateTime dtCadastroIni;
  public static LocalDateTime dtCadastroFim;
  public static String       siglaSistema;
  public static boolean      toVerificarEstoque;
  public static String       criterioVerificacaoEstoque;
  public static boolean      toUsarValorMinimoSistemaFornecedor;
  public static int          codigoFilialWinThor;
  public static String       username;
  public static String       senha;
  public static boolean      senhaCriptografada;
  public static String       tipoBancoDeDados;
  public static String       usernameBancoDeDados;
  public static String       senhaBancoDeDados;
  public static boolean      senhaBancoDeDadosCriptografada;
  public static String       enderecoIpServidorBancoDeDados;
  public static String       portaServidorBancoDeDados;
  public static String       instanciaBancoDeDados;
  public static String       cnpjFornecedor;
  public static String       nomeFantasiaFornecedor;
  public static String       tipoAmbiente;
  public static String       enderecoBaseWebService;
  public static String       diretorioArquivosXml;
  public static String       diretorioArquivosXmlSemBarraNoFinal;
  public static String       ObsOfertasPadraoSeNaoTemNoSistema;
  public static int          qtdDiasArquivosXmlGuardados;
  public static boolean      toDebugar;
  public static boolean      toEnviarEmailAutomatico;
  public static String       provedorEmailAutomatico;
  public static String       portaEmailAutomatico;
  public static String       remetenteEmailAutomatico;
  public static String       destinoEmailAutomatico;
  public static String       ccEmailAutomatico;
  public static String       usuarioEmailAutomatico;
  public static String       senhaCriptografadaEmailAutomatico;	  
  public static boolean      temErroGeralCotacao;
  public static String       erroStaticConstructor;
  public static String       nomeArquivoDebug;
  public static TransformerFactory transformerFactory;


  static 
  {
    try {
      Date hoje = new Date();
      nomeArquivoDebug = "Debug" + new SimpleDateFormat("yyyy.MM.dd - HH'h'mm ").format(hoje) + ".log";
      transformerFactory = TransformerFactory.newInstance();
 
      erroStaticConstructor = null;

      locale = new Locale("pt", "BR");
      nf = NumberFormat.getInstance(locale);

      Properties config = new Properties();
      config.load(new FileInputStream(NOME_ARQUIVO_PROPERTIES));

      siglaSistema  = config.getProperty("SiglaSistema");

      if ( !( siglaSistema.equals("SAP") || siglaSistema.equals("APS") || siglaSistema.equals("WinThor") || siglaSistema.equals("PCronos") ) ) {
    	  String msgErro = "O sistema " + siglaSistema + " ainda n�o est� homologado. Favor entrar em contato com o Suporte do Portal Cronos.";
    	  throw new ConfiguracaoException(msgErro);
      }
      

      if (siglaSistema.equals("PCronos"))
	  {
          toEnviarEmailAutomatico = Boolean.parseBoolean(config.getProperty("EnviarEmailAutomatico"));
          // Foi debugado que toEnviarEmailAutomatico fica false corretamente no caso que a configura��o N�O existe no arq .config

          
          if (toEnviarEmailAutomatico) 
          {
         	 provedorEmailAutomatico = config.getProperty("ProvedorEmailAutomatico");
        	 portaEmailAutomatico = config.getProperty("PortaEmailAutomatico");
        	 remetenteEmailAutomatico = config.getProperty("RemetenteEmailAutomatico");
        	 destinoEmailAutomatico = config.getProperty("DestinoEmailAutomatico");
        	 ccEmailAutomatico = (config.getProperty("CcEmailAutomatico").equals("") ? null : config.getProperty("CcEmailAutomatico"));
             usuarioEmailAutomatico = config.getProperty("UsuarioEmailAutomatico");
             senhaCriptografadaEmailAutomatico = Criptografia.decrypt(config.getProperty("SenhaCriptografadaEmailAutomatico"), toDebugar);
          }
	  }
      
      if (!siglaSistema.equals("SAP"))
	  {
         toVerificarEstoque                = Boolean.parseBoolean(config.getProperty("VerificarEstoque"));
         criterioVerificacaoEstoque        = config.getProperty("CriterioVerificacaoEstoque");
	    
         if (    !criterioVerificacaoEstoque.equals("QtdEstoqueMaiorOuIgualQtdSolicitada")
		      && !criterioVerificacaoEstoque.equals("QtdEstoqueMaiorZero")
		    )
		 {
         	 String msgErro = "Erro : configura��o \"CriterioVerificacaoEstoque\" inv�lida! Op��es permitidas: \"QtdEstoqueMaiorOuIgualQtdSolicitada\" ou \"QtdEstoqueMaiorZero\".";
         	 throw new ConfiguracaoException(msgErro);
		 }
			
         ObsOfertasPadraoSeNaoTemNoSistema = config.getProperty("ObsOfertasPadraoSeNaoTemNoSistema");
         tipoBancoDeDados                  = config.getProperty("TipoBancoDeDados");
         
         if ( !( tipoBancoDeDados.equals("Firebird") || tipoBancoDeDados.equals("Oracle") || tipoBancoDeDados.equals("SQL Server") ) ) {
       	  String msgErro = "O banco de dados " + tipoBancoDeDados + " ainda n�o est� homologado. Favor entrar em contato com o Suporte do Portal Cronos.";
       	  throw new ConfiguracaoException(msgErro);
         }
         usernameBancoDeDados              = config.getProperty("UsuarioBancoDeDados");
         senhaBancoDeDadosCriptografada    = Boolean.parseBoolean(config.getProperty("SenhaBancoDeDadosCriptografada"));

         if (senhaBancoDeDadosCriptografada)
             senhaBancoDeDados             = Criptografia.decrypt(config.getProperty("SenhaBancoDeDados"), toDebugar);
         else
             senhaBancoDeDados             = config.getProperty("SenhaBancoDeDados");
        	 
         enderecoIpServidorBancoDeDados    = config.getProperty("EnderecoIpServidorBancoDeDados");
         portaServidorBancoDeDados         = config.getProperty("PortaServidorBancoDeDados");
         instanciaBancoDeDados             = config.getProperty("InstanciaBancoDeDados");
         
         if (config.getProperty("UsarValorMinimoSistemaFornecedor") == null) // Se esta chave n�o existir no *.properties
             toUsarValorMinimoSistemaFornecedor = true;
         else 
             toUsarValorMinimoSistemaFornecedor = Boolean.parseBoolean(config.getProperty("UsarValorMinimoSistemaFornecedor"));
         
         if (siglaSistema.equals("WinThor"))
        	 codigoFilialWinThor = Integer.parseInt(config.getProperty("CodigoFilial"));
	  }
      username                          = config.getProperty("UsuarioWebService");
      senhaCriptografada                = Boolean.parseBoolean(config.getProperty("SenhaWebServiceCriptografada"));
      
      if (senhaCriptografada)
          senha                         = Criptografia.decrypt(config.getProperty("SenhaWebService"), toDebugar);
      else
          senha                         = config.getProperty("SenhaWebService");
    	  
      cnpjFornecedor                    = config.getProperty("CnpjFornecedor");
      nomeFantasiaFornecedor            = config.getProperty("NomeFantasiaFornecedor");
      toDebugar                         = Boolean.parseBoolean(config.getProperty("Debugar"));
      tipoAmbiente                      = config.getProperty("TipoAmbiente");

      if (tipoAmbiente.equals("P"))
          enderecoBaseWebService          = config.getProperty("EnderecoBaseWebServiceProducao");
      else if (tipoAmbiente.equals("H"))
          enderecoBaseWebService          = config.getProperty("EnderecoBaseWebServiceHomologacao");
      else if (tipoAmbiente.equals("T"))
          enderecoBaseWebService          = config.getProperty("EnderecoBaseWebServiceTeste");
      else {
    	  String msgErro = "O tipo de ambiente " + tipoAmbiente + " n�o existe. Op��es permitidas: P (= Produ��o), H (= Homologa��o), T (= Teste)";
    	  throw new ConfiguracaoException(msgErro);
      }
      
      
      try {
          qtdDiasArquivosXmlGuardados = Integer.parseInt(config.getProperty("QtdDiasArquivosXmlGuardados"));
      }
      catch (NumberFormatException nfex) {
    	  throw new ConfiguracaoException("Par�metro \"QtdDiasArquivosXmlGuardados\" inv�lido. Deve ser um n�mero inteiro igual a zero ou maior.");
      }
	
      
      diretorioArquivosXml = config.getProperty("DiretorioArquivosXml");
      
      if (!Files.isDirectory(Paths.get(diretorioArquivosXml))) {
    	  String msgErroDiretorio = "Erro! O diret�rio " + diretorioArquivosXml + " n�o existe! Favor contatar o setor TI.";
    	  diretorioArquivosXml = "C:/";
    	  throw new ConfiguracaoException(msgErroDiretorio);
      }
      
      try {
  	    // O seguinte tem apenas efeito em Linux, e nenhum efeito em Windows, 
    	// pois com Windows >= XP n�o � poss�vel alterar diret�rios para read-only (apenas arquivos)
    	  
        File testDir = new File(diretorioArquivosXml);
        if (!testDir.canWrite()) 
        { 
           String msgErroDiretorio = "Erro! O diret�rio " + diretorioArquivosXml + " � protegido contra grava��o de arquivos ! Favor contatar o setor TI.";
      	   diretorioArquivosXml = "C:/";
           throw new ConfiguracaoException(msgErroDiretorio);
        }

    	  // O seguinte tb n�o funciona com Windows : 
          // AccessController.checkPermission(new FilePermission(diretorioArquivosXml, "write"));
        
          // A �nica maneira para verificar os privi�gios necess�rios em Java 8 � : 
          File.createTempFile("check", null, testDir).delete();

          diretorioArquivosXmlSemBarraNoFinal = diretorioArquivosXml;
          diretorioArquivosXml = diretorioArquivosXml + "/" ;
      }
      catch (SecurityException | IOException se_io_ex)
      {
   	     String msgErroDiretorio = "Erro! N�o tem permiss�es suficientes para gravar arquivos no diret�rio " + diretorioArquivosXml + " ! Favor contatar o setor TI.";
   	     diretorioArquivosXml = "C:/";
      	 throw new ConfiguracaoException(msgErroDiretorio);
      }


      // Para n�o impossibilitar o limite de 11 emails por dia, no caso do Bol:
      if (siglaSistema.equals("PCronos") && qtdDiasArquivosXmlGuardados < 1) {
    	     String msgErro = "Erro! QtdDiasArquivosXmlGuardados n�o pode ser menor que 1 no caso que SiglaSistema = PCronos! (para n�o impossibilitar o limite de 11 emails por dia, no caso do Bol)";
          	 throw new ConfiguracaoException(msgErro);
      }

    	  
      debugar("sun.boot.class.path = " + java.lang.management.ManagementFactory.getRuntimeMXBean().getBootClassPath());

	  debugar("CnpjFornecedor                    = " + cnpjFornecedor);
	  debugar("NomeFantasiaFornecedor            = " + nomeFantasiaFornecedor);
	  debugar("SiglaSistema                      = " + siglaSistema);

	  if (!siglaSistema.equals("SAP"))
	  {
	      debugar("VerificarEstoque                  = " + toVerificarEstoque);
	      debugar("CriterioVerificacaoEstoque        = " + criterioVerificacaoEstoque);
		  debugar("ObsOfertasPadraoSeNaoTemNoSistema = " + ObsOfertasPadraoSeNaoTemNoSistema);
		  
		  if (siglaSistema.equals("WinThor")) {
			  debugar("UsarValorMinimoSistemaFornecedor  = " + toUsarValorMinimoSistemaFornecedor);
			  debugar("CodigoFilial                      = " + codigoFilialWinThor);
		  }

		  debugar("TipoBancoDeDados                  = " + tipoBancoDeDados);
		  debugar("EnderecoIpServidorBancoDeDados    = " + enderecoIpServidorBancoDeDados);
		  debugar("InstanciaBancoDeDados             = " + instanciaBancoDeDados);
		  debugar("PortaServidorBancoDeDados         = " + portaServidorBancoDeDados);
		  debugar("UsuarioBancoDeDados               = " + usernameBancoDeDados);
		  debugar("SenhaBancoDeDados                 = " + config.getProperty("SenhaBancoDeDados"));
		  debugar("SenhaBancoDeDadosCriptografada    = " + senhaBancoDeDadosCriptografada);
	  }
	  debugar("EnderecoBaseWebService            = " + enderecoBaseWebService);
	  debugar("TipoAmbiente                      = " + tipoAmbiente);
	  debugar("UsuarioWebService                 = " + username);
	  debugar("SenhaWebService                   = " + config.getProperty("SenhaWebService"));
	  debugar("SenhaWebServiceCriptografada      = " + senhaCriptografada);
	  debugar("DiretorioArquivosXml              = " + diretorioArquivosXml);
      debugar("QtdDiasArquivosXmlGuardados       = " + qtdDiasArquivosXmlGuardados);
	  debugar("Debugar                           = " + toDebugar);
         
      // throw new Exception("teste exception static constructor");
    } 
    catch (ConfiguracaoException cex) {
        try
        {
          erroStaticConstructor = cex.getMessage(); 
          logarErro(cex.getMessage());
        }
        catch (Exception ex2)
        {
          throw new ExceptionInInitializerError(ex2);
        }
    }
    catch (Exception ex) {
      try
      {
        erroStaticConstructor = "Erro imprevisto! " + printStackTraceToString(ex); 
        logarErro(ex, true);
      }
      catch (Exception ex2)
      {
        throw new ExceptionInInitializerError(ex2);
      }
    }
  }


  private static void criarTabelasTeste() 
  {
	  try
	  {
    if (siglaSistema.equals("APS") && tipoBancoDeDados.equals("Firebird"))
    {
        FBDriver fbDriver = new FBDriver();
        DriverManager.registerDriver(fbDriver); 
        String connectionString = "jdbc:firebirdsql://" + enderecoIpServidorBancoDeDados + ":" + portaServidorBancoDeDados + "/" + instanciaBancoDeDados;
      	String sqlString = "create table produto (codproduto int, ativo int, ativopesq int)";
	    java.sql.Connection conn = DriverManager.getConnection(connectionString, usernameBancoDeDados, senhaBancoDeDados ) ;
	             // Por default AutoCommit = true
	    java.sql.Statement stat = conn.createStatement() ;
	    
	    try {
	      stat.executeUpdate(sqlString);
	    }
	    catch (Exception ex) { 
	    	ex.printStackTrace();
	    }

      	sqlString = "create table prodfilho (codproduto int, codprodfilho int)";

	    try {
		      stat.executeUpdate(sqlString);
        }
	    catch (Exception ex) { 
	    	ex.printStackTrace();
	    }

	    int qtdInsert = 0;
	    
	    try {
          sqlString = "insert into produto(codproduto, ativo, ativopesq) values (1, 2, 1)";
  	      qtdInsert = stat.executeUpdate(sqlString);
          System.out.println("qtdInsert = " + qtdInsert);
        }
	    catch (Exception ex) { 
	    	ex.printStackTrace();
	    }

	    try {
       	  sqlString = "insert into prodfilho(codproduto, codprodfilho) select 1, precoempresa.codprodfilho from precoempresa";
  	      qtdInsert = stat.executeUpdate(sqlString);
          System.out.println("qtdInsert = " + qtdInsert);
        }
	    catch (Exception ex) { 
	    	ex.printStackTrace();
	    }
  
    } // if (siglaSistema.equals("APS") && tipoBancoDeDados.equals("Firebird"))       			
	  }
      catch (Exception ex) { 
	    	ex.printStackTrace();
	  }
  }        			

  public static String getCharacterDataFromElement(Element e) 
  {
    Node child = e.getFirstChild();
    if (child instanceof CharacterData) {
      CharacterData cd = (CharacterData) child;
      return cd.getData();
    }
    return "";
  }

/*


<Cotacoes xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
<Cotacao>
 	<Dt_Gravacao>16/04/2015 14:31:35</Dt_Gravacao>
  	<Cd_Cotacao>001-0100</Cd_Cotacao>
  	<Cd_Comprador>01234567000145</Cd_Comprador>  
 	<Cd_Condicao_Pagamento>123</Cd_Condicao_Pagamento>
  	<Tp_Frete>CIF</Tp_Frete>
  	<Dt_Previsao_Entrega>21/04/2015</Dt_Previsao_Entrega>
  	<Dt_Inicio_Cotacao>16/04/2015 14:30:00</Dt_Inicio_Cotacao>
  	<Dt_Fim_Cotacao>20/04/2015 11:00:00</Dt_Fim_Cotacao>
  	<Dt_Cadastro>16/04/2015 14:29:46</Dt_Cadastro>
  	<Produtos>
    		<Produto>
      			<Cd_Produto_Fornecedor>045879</Cd_Produto_Fornecedor>
			<Cd_Produto>620</Cd_Produto>
      			<Cd_Unidade_Compra>UN</Cd_Unidade_Compra>
      			<Qt_Embalagem>12</Qt_ Embalagem>
      			<Qt_Produto>1000</Qt_Produto>
     		</Produto>
  	</Produtos>
</Cotacao>
</Cotacoes>



<?xml version="1.0" encoding="iso-8859-1"?>
<RecebimentoCotacao>
  <Cd_Cotacao>082-0239</Cd_Cotacao>
  <Cd_Comprador>06056930000143</Cd_Comprador>
  <Cd_Fornecedor>02870737000190</Cd_Fornecedor>
  <Dt_Recebimento>24/03/2016 00:05:44</Dt_Recebimento>
</RecebimentoCotacao>


<?xml version="1.0" encoding="iso-8859-1"?>
<OfertasCotacao>
  <Dt_Gravacao>24/03/2016 11:26:04</Dt_Gravacao>
  <Cd_Cotacao>082-0240</Cd_Cotacao>
  <Cd_Comprador>06056930000143</Cd_Comprador>
  <Cd_Fornecedor>02870737000190</Cd_Fornecedor>
  <Cd_Condicao_Pagamento_Fornecedor>4</Cd_Condicao_Pagamento_Fornecedor>
  <Tp_Frete_Fornecedor>CIF</Tp_Frete_Fornecedor>
  <Qt_Prz_Entrega>1</Qt_Prz_Entrega>
  <Vl_Minimo_Pedido>300,00</Vl_Minimo_Pedido>
  <Ds_Observacao_Fornecedor>Texto padr�o do fornecedor para todas as cota��es de todos os compradores.</Ds_Observacao_Fornecedor>
  <Produtos>
    <Produto>
      <Cd_Produto_Fornecedor>1852</Cd_Produto_Fornecedor>
      <Cd_Produto>428</Cd_Produto>
      <Qt_Embalagem>3</Qt_Embalagem>
      <Vl_Preco>33,4500</Vl_Preco>
      <Ds_Obs_Oferta_Fornecedor></Ds_Obs_Oferta_Fornecedor>
    </Produto>
  </Produtos>
  <Erros />
</OfertasCotacao>

*/

  public static String performPostCall(String txt, String tipoArquivo) {

      URL url = null;
      String response = "";
      
      try {
          JSONObject tokenJSON = new JSONObject();
          tokenJSON.put("userName", username);
       // tokenJSON.put("nomeFantasiaFornecedor", nomeFantasiaFornecedor);
          tokenJSON.put("linhaArqLog", txt);

          String payload = tokenJSON.toString();
          String contentType = "application/json";
          String requestMethod = "POST";

          if (tipoArquivo.equals("Debug"))
              url = new URL(enderecoBaseWebService.replace("api", "ControloAcesso") + "LogRemoto");
          else if (tipoArquivo.equals("Erro"))
              url = new URL(enderecoBaseWebService.replace("api", "ControloAcesso") + "LogErroRemoto");
          else if (tipoArquivo.equals("TemposExecu��o"))
              url = new URL(enderecoBaseWebService.replace("api", "ControloAcesso") + "LogTempoExecucaoRemoto");

          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setReadTimeout(30000);
          conn.setConnectTimeout(30000);
          conn.setRequestMethod(requestMethod);
          conn.setDoInput(true);
          conn.setDoOutput(true);
          conn.setRequestProperty("content-type", contentType);


          byte[] outputBytes = payload.getBytes("UTF-8");
          OutputStream os = conn.getOutputStream();
          os.write(outputBytes);

          int responseCode = conn.getResponseCode();

          if (responseCode == HttpsURLConnection.HTTP_OK) {
              String line;
              BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
              while ((line=br.readLine()) != null) {
                  response += line;
              }
          // debugarApenasLocalmente("COOKIE: " + conn.getHeaderField("Set-Cookie"));
                // -> deu "COOKIE: NULL"
          //    this.cookieManager.setCookie(this.server, conn.getHeaderField("Set-Cookie"));
          }
          else {
              response = "";
              // Para evitar la�os infinitos:
              debugarApenasLocalmente("Response Message: " + conn.getResponseMessage());
          }
      } 
      catch (JSONException jsonex) {
    	  // Para evitar la�os infinitos:
          logarErroApenasLocalmente("performPostCall(): Can�t format JSON");  
      }
      catch (Exception ex) {
    	  // Para evitar la�os infinitos:
    	  logarErroApenasLocalmente(ex, true);
      }

      return response;
  }

  
  
  public static void debugarApenasLocalmente(String txt) 
  {  
     if (toDebugar) 
     {
         try
    	 {
	        BufferedWriter bWriter = new BufferedWriter(new FileWriter(diretorioArquivosXml + nomeArquivoDebug, true));
	        bWriter.append(txt);
	        bWriter.newLine();
	     // bWriter.newLine();
	        bWriter.flush();
	        bWriter.close();
    	 }
    	 catch (IOException ioe)
    	 {
    	   System.out.println(txt);
    	 }
     }
  }
  

  public static void debugarApenasRemotamente(String txt) 
  {  
     if (!siglaSistema.equals("PCronos"))
       performPostCall(txt, "Debug");
  }
  

  public static void debugar(String txt) 
  {  
	 debugarApenasLocalmente(txt); 
	 debugarApenasRemotamente(txt);
  }
  

  public static void logarErroApenasLocalmente( String erro )
  {
	     Date hoje = new Date();
	      
	 	 try
	 	 {
		        BufferedWriter bWriter = new BufferedWriter(new FileWriter(diretorioArquivosXml + "Erro - " + new SimpleDateFormat("yyyy.MM.dd - HH'h'mm.ss ").format(hoje) + ".log", true));
		        bWriter.append(erro);
		        bWriter.newLine();
		        bWriter.newLine();
		        bWriter.flush();
		        bWriter.close();
	 	 }
	 	 catch (IOException ioe)
	 	 {
	 	   System.out.println(erro);
	 	 }  

		 debugarApenasLocalmente(erro); 
  }
	  
  
  private static void logarErroApenasLocalmente( Exception ex, boolean toConsoleTambem ) 
  {  
	  if (toConsoleTambem)
	  {
		  ex.printStackTrace();
	  }

	  Date hoje = new Date();
      File file = new File(diretorioArquivosXml + "Erro - " + new SimpleDateFormat("yyyy.MM.dd - HH'h'mm.ss ").format(hoje) + ".log");
      
      try
      {
    	  PrintStream ps = new PrintStream(file);
          ex.printStackTrace(ps);
          ps.close();
      }
      catch (FileNotFoundException fnfex)
      {
    	  // Deve ter tratado no static constructor
    	  throw new RuntimeException("Erro interno no m�todo LogarErro() : arquivo n�o encontrado.");
      }

      debugarApenasLocalmente(printStackTraceToString(ex)); 
  }


  public static void logarErro( String erro )
  {
	  logarErroApenasLocalmente(erro);  

      if (!siglaSistema.equals("PCronos")) {
         performPostCall(erro, "Erro");
         debugarApenasRemotamente(erro);
      }
  }
  
  
  private static void logarErro( Exception ex, boolean toConsoleTambem ) 
  {
	  logarErroApenasLocalmente(ex, toConsoleTambem);  

      if (!siglaSistema.equals("PCronos"))
      {
    	  String erro = printStackTraceToString(ex);
          performPostCall(erro, "Erro");
          debugarApenasRemotamente(erro);
      }
  }
  
  
  public static String printStackTraceToString(Exception ex)
  {

   // Writer writer = new StringWriter();
      StringWriter sWriter = new StringWriter();
      ex.printStackTrace(new PrintWriter(sWriter));
      return sWriter.toString();
  }


  private static void  enviarErroParaPortalCronos(Document docOfertas, Element elmErros, String cdProdutoFornecedor, String mensagemErro) 
  {
      temErroGeralCotacao = true; 

      Element elmErro = docOfertas.createElement("Erro");
      elmErros.appendChild(elmErro);

      Element elmCdProdutoFornecedorErro = docOfertas.createElement("Cd_Produto_Fornecedor");
      elmCdProdutoFornecedorErro.appendChild(docOfertas.createTextNode(cdProdutoFornecedor));
      elmErro.appendChild(elmCdProdutoFornecedorErro);

      debugar(mensagemErro);
      Element elmMensagem = docOfertas.createElement("Mensagem");
      elmMensagem.appendChild(docOfertas.createTextNode(mensagemErro));
      elmErro.appendChild(elmMensagem);

  }



  private static void readCotacao(NodeList cotacoes, int i, DocumentBuilder docBuilder) throws SQLException, ParserConfigurationException, TransformerException
  {
    Document docOfertas = null;
    Element elmErros = null;

    try
    {  
	    Element element = (Element)cotacoes.item(i);
	
        NodeList nlist = element.getElementsByTagName("Cd_Cotacao");
	    Element elm = (Element) nlist.item(0);
	    String cdCotacao = getCharacterDataFromElement(elm);
	    debugar("\r\n\r\ncdCotacao: " + cdCotacao);
	
	    nlist = element.getElementsByTagName("Cd_Comprador");
	    elm = (Element) nlist.item(0);
	    String cdComprador = getCharacterDataFromElement(elm);
	 // debugar("cdComprador: " + cdComprador);
	
	 	// Tag <Cd_Condicao_Pagamento>123</Cd_Condicao_Pagamento> pulado, pois n�o serve para nada 

	  	nlist = element.getElementsByTagName("Tp_Frete");
	    elm = (Element) nlist.item(0);
	    String tpFrete = getCharacterDataFromElement(elm);
	
	 // Tags pulados, pois n�o servem para nada :
	 //    <Dt_Previsao_Entrega>21/04/2015</Dt_Previsao_Entrega>
	 //    <Dt_Inicio_Cotacao>16/04/2015 14:30:00</Dt_Inicio_Cotacao>
	 //    <Dt_Fim_Cotacao>20/04/2015 11:00:00</Dt_Fim_Cotacao>
	 //    <Dt_Cadastro>16/04/2015 14:29:46</Dt_Cadastro>

	  	NodeList produtos = element.getElementsByTagName("Produto");
	
	
	//  =====================================================================================
	//
	//  Confirma��o de Recebimento da Cota��o
	//
	//  =====================================================================================
	
	    Document docRecebimentosCotacoes = docBuilder.newDocument();
	
	    Element elmRecebimentoCotacao = docRecebimentosCotacoes.createElement("RecebimentoCotacao");
	    docRecebimentosCotacoes.appendChild(elmRecebimentoCotacao);
	
	    Element elmCdCotacao = docRecebimentosCotacoes.createElement("Cd_Cotacao");
	    elmCdCotacao.appendChild(docRecebimentosCotacoes.createTextNode(cdCotacao));
	    elmRecebimentoCotacao.appendChild(elmCdCotacao);
	
	    Element elmCdComprador = docRecebimentosCotacoes.createElement("Cd_Comprador");
	    elmCdComprador.appendChild(docRecebimentosCotacoes.createTextNode(cdComprador));
	    elmRecebimentoCotacao.appendChild(elmCdComprador);
	
	    Element elmCdFornecedor = docRecebimentosCotacoes.createElement("Cd_Fornecedor");
	    elmCdFornecedor.appendChild(docRecebimentosCotacoes.createTextNode(cnpjFornecedor));
	    elmRecebimentoCotacao.appendChild(elmCdFornecedor);
	
	    Element elmDtRecebimento = docRecebimentosCotacoes.createElement("Dt_Recebimento");
	    Date hoje = new Date();
	    elmDtRecebimento.appendChild(docRecebimentosCotacoes.createTextNode(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(hoje)));
	    elmRecebimentoCotacao.appendChild(elmDtRecebimento);
	
	    Transformer transformer = transformerFactory.newTransformer();
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	 // transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "iso-8859-1");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	
	    final DOMSource source = new DOMSource(docRecebimentosCotacoes);
	    String filenameConfirmReceb = diretorioArquivosXml + String.format("REC_%s_%s_%s.xml", cnpjFornecedor, cdCotacao, new SimpleDateFormat("yyyyMMdd'_'HHmmss").format(hoje));
    	Files.deleteIfExists(Paths.get(filenameConfirmReceb));
    	final StreamResult result = new StreamResult(new File(filenameConfirmReceb));
	
	 // Output to console for testing
	 // StreamResult result = new StreamResult(System.out);
	
	    transformer.transform(source, result);
	
	    upload_File(enderecoBaseWebService + "cotacao/EnviaArquivosRecebimentoCotacao", new File(filenameConfirmReceb), "form1", username, senha) ;
	    
	//  =====================================================================================
	//
	//  Ofertas
	//
	//  =====================================================================================
		    
        if (siglaSistema.equals("SAP")) {

        // No caso do SAP, quebrar as cota��es em peda�os e enviar de uma por uma para o SAP, 
        // transformando Element element para string:

           Transformer transformerSAP = transformerFactory.newTransformer();    
	       transformerSAP.setOutputProperty(OutputKeys.ENCODING, "iso-8859-1");
		   transformerSAP.setOutputProperty(OutputKeys.INDENT, "no");
           transformerSAP.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");
           final DOMSource sourceSAP = new DOMSource(element);
           final StreamResult resultSAP = new StreamResult(new StringWriter());
           transformerSAP.transform(sourceSAP, resultSAP);
           String strCotacao = resultSAP.getWriter().toString().replaceAll("<Cotacao>", "<Cotacoes><Cotacao>").replaceAll("</Cotacao>", "</Cotacao></Cotacoes>");
           debugar("O par�metro \"I_XML\" de tipo string Xml da fun��o do SAP \"ZFCSD00_IMPCOT\" = " + strCotacao);
           debugar("O mesmo par�metro transformado para leg�vel = \r\n1. String I_XML, transformado para xml leg�vel:\r\n===============================================\r\n" + Utils.transformarXmlParaLegivel(strCotacao));

           try {
	            JCoDestination destination = JCoDestinationManager.getDestination("conf/SAP_API");
	            debugar("Passou JCoDestination destination");
	            debugar("Attributes: ");
	            debugar(destination.getAttributes().toString());
	            JCoFunction function = destination.getRepository().getFunction("ZFCSD00_IMPCOT");
	            debugar("Passou JCoFunction function");
	
	            if (function == null)
	              throw new Exception("Fun�ao ZFCSD00_IMPCOT n�o encontrada no SAP.");
	
	            function.getImportParameterList().setValue("I_XML", strCotacao);

                function.execute(destination);
	            String strXmlProblemas = function.getExportParameterList().getString("E_RESULT");
                debugar("A fun��o do SAP \"ZFCSD00_IMPCOT\" retornou o par�metro \"E_RESULT\" de tipo string Xml = " + strXmlProblemas);
                debugar("O mesmo par�metro transformado para leg�vel = \r\n2. String E_RESULT, transformado para xml leg�vel:\r\n==================================================\r\n" + Utils.transformarXmlParaLegivel(strXmlProblemas));

                if (strXmlProblemas == null) {
                   logarErro("Erro ao chamar a fun��o do SAP \"ZFCSD00_IMPCOT\": o par�metro \"E_RESULT\" de tipo string Xml retornou \"null\"");
                   return;
                }
                else if (strXmlProblemas.trim().equals("")) {
                    logarErro("Erro ao chamar a fun��o do SAP \"ZFCSD00_IMPCOT\": o par�metro \"E_RESULT\" de tipo string Xml que deveria conter pelo menos os dados gerais da cota��o retornou um valor vazio de tamanho zero.");
                    return;
                }
                else {
                	if (strXmlProblemas.substring(0,3).toUpperCase().equals("ERR")) {
                       debugar("Entrou no if strXmlProblemas.substring(0,3).toUpperCase().equals(ERR)");
                       logarErro("Erro ao chamar a fun��o do SAP \"ZFCSD00_IMPCOT\": o par�metro \"E_RESULT\" de tipo string Xml retornou: " + strXmlProblemas);
                       logarErro("O mesmo par�metro transformado para leg�vel = \r\nString E_RESULT, transformado para xml leg�vel:\r\n===============================================\r\n" + Utils.transformarXmlParaLegivel(strXmlProblemas));
                    }
                }
                
//              Thread.sleep((5 * 60 * 1000);
                
//	            function = destination.getRepository().getFunction("ZFCSD00_EXPCOT");
//	            debugar("Passou JCoFunction function");
//	
//	            if (function == null)
//	              throw new Exception("Fun�ao ZFCSD00_EXPCOT n�o encontrada no SAP.");
//	
//              function.execute(destination);
                String strXmlOfertasRecebido = function.getExportParameterList().getString("E_XML");
                debugar("A fun��o do SAP \"ZFCSD00_IMPCOT\" retornou o par�metro \"E_XML\" de tipo string Xml = " + strXmlOfertasRecebido);
                debugar("O mesmo par�metro transformado para leg�vel = \r\n3. String E_XML, transformado para xml leg�vel:\r\n===============================================\r\n" + Utils.transformarXmlParaLegivel(strXmlOfertasRecebido));

                if (strXmlOfertasRecebido == null) {
                    logarErro("Erro ao chamar a fun��o do SAP \"ZFCSD00_IMPCOT\": o par�metro \"E_XML\" de tipo string Xml que deveria conter a lista de ofertas retornou \"null\".");
                    return;
                }
                else if (strXmlOfertasRecebido.trim().equals("")) {
                    return;
                }
                
                debugar("Chegou antes de InputSource inputsource = new InputSource();");
		        InputSource inputsource = new InputSource();
		        inputsource.setCharacterStream(new StringReader(strXmlOfertasRecebido));
		
		        docOfertas = docBuilder.parse(inputsource);
		    	uploadXmlOfertas(docOfertas, cdCotacao, transformer, hoje);
           }
           catch(AbapException aex)
           {
              logarErro("Exception aex entrado: " + (aex.getKey() == null ? "" : aex.getKey()) + ": " + aex.toString() + " - " + aex.getMessageText());
           }
           catch (JCoException jex) {
              logarErro("Exception jex entrado: " + (jex.getKey() == null ? "" : jex.getKey()) + ": " + jex.toString() + " - " + jex.getMessageText());
           }
           catch (Exception ex) { // Exemplo: xml invalido retornado pela function do SAP, ou erro SQL na function do SAP
              logarErro("Exception ex entrado: Erro ao chamar a fun��o do SAP: " + ex.getMessage());
           }
        }
        else  // (!siglaSistema.equals("SAP"))
           montarXmlOfertas(transformer, docOfertas, produtos, elmErros, cdCotacao, cdComprador, tpFrete, docBuilder);
    }
    catch (java.lang.Exception ex) { 
      logarErro(ex, false);
      if (!siglaSistema.equals("SAP")) enviarErroParaPortalCronos(docOfertas, elmErros, null, printStackTraceToString(ex));  
    }
  }


  private static void monitorarPendencias(LocalDateTime horaInicio) {
		java.sql.Connection conn = null;
		java.sql.CallableStatement cstat = null;
		java.sql.ResultSet rSet = null;

				
		try
	    {  
		    // Para evitar que o Bol talvez vai cancelar a conta de email por motivo de abuso: 
			File dir = new File(diretorioArquivosXmlSemBarraNoFinal); 
	        for (final File file : dir.listFiles()) 
		    {
				 LocalDateTime datahoraArquivo = LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), ZoneId.systemDefault()); 
				   
			    if (file.getName().startsWith("Erro"))
			    {
			    	// Aguardar 54 horas para o reset do limite di�ria ou semanal de emails,  
			    	// e tentar enviar um email automaticamente avisando do erro: 
			    	if (datahoraArquivo.isAfter(horaInicio.minusHours(54).minusMinutes(25)) && datahoraArquivo.isBefore(horaInicio.minusHours(54))) {
			  	        EmailAutomatico.enviar(remetenteEmailAutomatico, destinoEmailAutomatico, ccEmailAutomatico, "Monitoramento integra��o - Erro fatal! ", null, "Erro! Monitoramento parado! Aconteceu um erro fatal 54 horas atr�s. Veja a causa no arquivo de log " + file.getName() + ". O monitoramento autom�tico continuar� parado at� a verifica��o e a exclus�o manual deste arquivo de log de erro! ", provedorEmailAutomatico, portaEmailAutomatico, usuarioEmailAutomatico, senhaCriptografadaEmailAutomatico, diretorioArquivosXmlSemBarraNoFinal, horaInicio, diretorioArquivosXml, "Monitoramento");
			    	}
			    	// Se existir um arquivo de log com erro pelo email do Bol de qualquer data, 
					// automaticamente nunca mais enviar nenhum email (nem sobre fornecedores nem sobre o monitoramento) 
			    	// at� este arquivo de log de erro ser� excluido: 
			    	return;
			    }
			}
		    	
		    			    

	        
	        String connectionString = null;
		    
		    if (tipoBancoDeDados.equals("SQL Server"))
		    {
		      SQLServerDriver sqlsrvDriver = new SQLServerDriver() ; 
		      DriverManager.registerDriver( sqlsrvDriver ) ; 	
		      //connectionUrl = "jdbc:sqlserver://localhost:1433;" +  
		    	//         "databaseName=AdventureWorks;user=UserName;password=*****";  
		      connectionString = "jdbc:sqlserver://" + enderecoIpServidorBancoDeDados + ":" + portaServidorBancoDeDados + ";databaseName=" + instanciaBancoDeDados; 
		    }
		    else 
		    {
		         logarErro("O tipo de banco de dados pode ser apenas SQL Server no caso que o sistema for PCronos.");
		         return;
		    }
	   
		    conn = DriverManager.getConnection(connectionString, usernameBancoDeDados, senhaBancoDeDados ) ;	    
		    String sqlString = "{call dbo.monitorarIntegracaoFornecedores()}";
		    cstat = conn.prepareCall(sqlString);
	        boolean results = cstat.execute();
	        int rsCount = 0;

	       while (results) {
	             rSet = cstat.getResultSet();
	             String body = "";
	             String assunto = "";
            	 String nmFornecedor = null;
	             
	             while (rSet.next()) {
	            	 nmFornecedor = rSet.getString(2);
            		 FornecedorRepositorio fRep = new FornecedorRepositorio();
	            	 
	            	 if (nmFornecedor != null && nmFornecedor.equals("INI")) {
	            		 Fornecedor f = fRep.getFornecedor(Integer.parseInt(rSet.getString(1)));
		            	 assunto = "Integra��o ofertas colocada em produ��o!";
		            	 body += "Come�ou a integra��o do fornecedor com id_fornecedor = " + rSet.getString(1) + " em produ��o!\r\n";
		            	 body += "1. Favor excluir o \"OR\" deste id_fornecedor na sp dbo.monitorarIntegracaoFornecedores.\r\n";
		            	 body += "    Dica: procura \"" + rSet.getString(1) + "\" nesta sp.\r\n";
		            	 body += "2. Enviar o manual \"Manual solucionamento paradas integra��o Portal Cronos - v1.4 (24.04.2018).txt\" para o TI (" + f.EmailResponsavelTI + ".). \r\n\r\n\r\n\r\n";
		            	 dtCadastroIni = rSet.getTimestamp(6).toLocalDateTime();
		            	 dtCadastroFim = rSet.getTimestamp(7).toLocalDateTime();
	            	 }
	            	 else if (!Utils.isNullOrBlank(nmFornecedor)) {
	            		 Fornecedor f = fRep.getFornecedor(rSet.getInt(1));
		            	 assunto = "URGENTE! Parada integra��o PCronos / " + f.SiglaSistemaFornecedor + " - " + nmFornecedor;
		           	     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		           	     DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");
		           	     
		           	     DateTimeFormatter formatterParenteses = DateTimeFormatter.ofPattern("'('dd/MM/yyyy')' HH:mm");
		           	     String ateQuando = "";
		           	     if (rSet.getTimestamp(10).toLocalDateTime().getDayOfYear() == horaInicio.getDayOfYear())
		           	       ateQuando = "hoje " + rSet.getTimestamp(10).toLocalDateTime().format(formatterParenteses);
		           	     else if (rSet.getTimestamp(10).toLocalDateTime().getDayOfYear() == (horaInicio.getDayOfYear() + 1))
			           	       ateQuando = "amanh� " + rSet.getTimestamp(10).toLocalDateTime().format(formatterParenteses);
		           	     else 
			           	       ateQuando = rSet.getTimestamp(10).toLocalDateTime().format(formatter);
		           	     
		            	 String strParteDoDia = null;
		            	 if (horaInicio.getDayOfWeek() == DayOfWeek.SATURDAY || horaInicio.getDayOfWeek() == DayOfWeek.SUNDAY)
			            	    strParteDoDia = "bom dia"; // o email ser� enviado na pr�xima segunda-feira por enquanto
		            	 else if (horaInicio.getHour() < 12)
			            	    strParteDoDia = "bom dia";
		            	 else if (horaInicio.getHour() >= 12 && horaInicio.getHour() < 18)
			            	    strParteDoDia = "boa tarde";
		            	 else
			            	    strParteDoDia = "bom dia"; //"boa noite"; o email ser� enviado no pr�ximo dia por enquanto
		            	 

		            	 // O seguinte vale pelo menos para o seguinte erro: 
		            	 //       "No Portal Cronos n�o tem nenhum DE/PARA cadastrado para o C�digo ou Descri��o 
		            	 //        da condi��o de pagamento 2 do fornecedor informado no arquivo XML da oferta 
		            	 //        para a cota��o 105-0553"
		            	 // Ficar acomanhando se isso vale para todos os outros casos que Erro == null ou Empty????????
		            	 //???????????????????????????????
		            	 if (!Utils.isNullOrBlank(rSet.getString(6))) {
			            	 body += "Assunto: Erro integra��o " + nmFornecedor + " - " + rSet.getString(6) + "\r\n"
		            			  + "Para: leao@cronos-tech.com.br"+ "\r\n"
		            			  + "Le�o, " + strParteDoDia + "!" + "\r\n"
 	            				  + " " + "\r\n"
 	            				  + "Recebi um email autom�tico (provis�rio) com o seguinte erro: " + "\r\n"
 	            				  + "      \"<i>" + rSet.getString(6) + "</i>\"" + "\r\n"
 	            				  + " " + "\r\n"
 	            				  + rSet.getString(3) + "\r\n\r\n" 
			              		  +  ((rSet.getInt(4) < 30) ? "Qtd. Meus Produtos: " + Integer.toString(rSet.getInt(4)) + "\r\n\r\n" 
        		                            : "Isso � importante para resolver logo pois tem muitos Meus Produtos (" + Integer.toString(rSet.getInt(4)) + ") nesta cota��o \r\n\r\n"
			              			)
			              		  +  "e s� temos at� " + ateQuando + " para resolver este problema (data fim da cota��o). \r\n\r\n"
 	            				  + " " + "\r\n"
 	            				  + "<b>Depois disso favor resettar a qtd. tentativas de ofertamento para zero com o seguinte script:<b> " + "\r\n"
 	            				  + " " + "\r\n"
 	            				  + "delete" + "\r\n"
 	            				  + "   from [PCronos_Producao].[dbo].[Integracao_Cotacao_Fornecedor]" + "\r\n"
 	            				  + "  where id_cotacao_cot in (........)  " + "\r\n"
 	            				  + "    and id_fornecedor_fornec = " + Integer.toString(rSet.getInt(1)) + "\r\n"
 	            				  + " " + "\r\n"
 	            				  + "Atc, " + "\r\n"
 	            				  + "Eric " + "\r\n"
 	            				  ;
		           	     }
		           	     else {
		           	     
			            	 body += rSet.getString(3) + "\r\n\r\n" 
			              		  +  ((rSet.getInt(4) < 30) ? "Qtd. Meus Produtos: " + Integer.toString(rSet.getInt(4)) + "\r\n\r\n" 
			              		                            : "Isso � importante para resolver logo pois tem muitos Meus Produtos (" + Integer.toString(rSet.getInt(4)) + ") nesta cota��o!\r\n\r\n"
			              		     )                                
			              		  +  "S� temos at� " + ateQuando + " para resolver este problema (data fim da cota��o). \r\n\r\n"
			              		  + "Qtd. Tentativas: " + Integer.toString(rSet.getInt(5)) + "\r\n\r\n" 
			              		  +  ((rSet.getInt(5) > 0) ? ( "Favor verificar o percentual de ocupa��o da mem�ria RAM ou verificar a comunica��o com o servidor de banco.\r\n"
			              				                     + "Enviar email para o TI: Favor habilitar o Team Viewer/AnyDesk pois preciso analisar os arquivos de log pois esta parada est� fora do comum.\r\n\r\n"
			              				                     )
	    		                                           : ""
			              			 )                                
			            	      +  "Erro: " + rSet.getString(6) + "\r\n" 
			            		  +  "\r\n\r\n\r\n\r\n";
			            	 
			            	 body += "LIGAR O SKYPE!!!!!!\r\n";
			            	 body += "N�O COPIAR LE�O!!!!!!\r\n";
			            	 body += "N�O ENVIAR NAS SEXTA-FEIRAS DE MANH� E N�O ENVIAR SE PODE PREJUDICAR O ALMO�O!!! \r\n\r\n\r\n\r\n";
			            	 body += "Para: " + f.EmailResponsavelTI + "\r\n"
	+ f.ApelidoResponsavelTI + ", " + strParteDoDia + "!" + "\r\n"
	+ " " + "\r\n"
	+ "<i>Este email foi enviado automaticamente pelo sistema Portal Cronos</i>\r\n"
	+ "Desde hoje (23/02/2018) 08:40 o Portal Cronos n�o est� mais recebendo ofertas autom�ticas da " + nmFornecedor + "." + "\r\n"
	+ " " + "\r\n"
	+ "<b>Isso � urgente e importante para resolver logo para evitar que a " + nmFornecedor + " perde muitas oportunidades de venda!</b>" + "\r\n"
	+ " " + "\r\n"
	+ "OU:  " + "\r\n"
	+ "S� temos at� " + ateQuando + " para resolver este problema (� a data fim da cota��o que vence primeiro e que n�o est� ofertada)." + "\r\n"
	+ "� urgente pois tem muitos produtos vendidos pela " + nmFornecedor + " (200 \"Meus Produtos\") pendentes sem ofertas vencendo �s " + rSet.getTimestamp(10).toLocalDateTime().format(formatterHora) + " horas!" + "\r\n"
	+ " " + "\r\n"
	+ "OU:" + "\r\n"
	+ "� melhor resolver isso logo, pois j� tem uma cota��o grande pendente com muitos \"Meus Produtos\" vendidos pela " + nmFornecedor + " (164) nesta cota��o!" + "\r\n"
	+ "E a quantidade de cota��es vai crescer rapidamente no final da semana!" + "\r\n"
	+ " " + "\r\n"
	+ "OU:" + "\r\n"
	+ "No momento j� tem 3 cota��es esperando e esta quantidade vai crescer rapidamente!" + "\r\n"
	+ " " + "\r\n"
	+ "OU:  " + "\r\n"
	+ "S� temos at� " + ateQuando + " para resolver este problema (� a data fim da cota��o que vence primeiro e que n�o est� ofertada)." + "\r\n" 
	+ " " + "\r\n"
	+ "OU:" + "\r\n"
	+ "Favor solicitar os vendedores ofertar as cota��es ...... manualmente, pois n�o vai dar mais tempo suficiente" + "\r\n" 
	+ "para ofertar estas cota��es automaticamente. " + "\r\n"
	+ " " + "\r\n"
	+ "OU:" + "\r\n"
	+ "Se n�o conseguir resolver antes de " + ateQuando + " horas, favor solicitar os vendedores ofertar as cota��es ...... manualmente," + "\r\n" 
	+ "pois a cota��o que vence primeiro e que n�o est� ofertada vence " + rSet.getTimestamp(10).toLocalDateTime().format(formatter) + ","   + "\r\n"
	+ "e tem muitos produtos vendidos pela " + nmFornecedor + " (200 \"Meus Produtos\") nesta cota��o!" + "\r\n"
	+ " " + "\r\n"
	+ "OU:" + "\r\n"
	+ "� melhor resolver isso logo, antes de " + ateQuando + "  (� a data fim da cota��o que vence primeiro e que n�o est� ofertada)," + "\r\n"  
	+ "pois tem muitos produtos vendidos pela " + nmFornecedor + " (200 \"Meus Produtos\") nesta cota��o!" + "\r\n"
	+ " " + "\r\n"
	+ "<b>� melhor N�O simplesmente reiniciar o servidor</b>, por�m � melhor identificar a causa <red>para podermos evitar repeti��o durante finais da semana quando n�o tem ningu�m dispon�vel para ficar reiniciando</red>." + "\r\n" 
	+ "Se voc� n�o tem nenhuma ideia da causa, veja em anexo uma lista de poss�veis causas e outras dicas. " + "\r\n"
	+ " " + "\r\n"
	+ "OU:" + "\r\n"
	+ "Se voc� n�o tem nenhuma ideia da causa, veja em anexo a �ltima vers�o da lista de poss�veis causas e outras dicas." + "\r\n"
	+ "Ap�s a solu��o da causa, veja no manual em anexo como verificar se o servi�o realmente voltou a funcionar.\r\n"
	+ " " + "\r\n"
	;
	
	//		     		    String sqlVerificacaoCadastros = 
	//		     		    		  "select distinct ds_ocorrencia_logeint "
	//		     		    		+ "  from dbo.Log_Erro_Integracao "
	//		     		    		+ " where id_fornecedor_fornec = " + Integer.toString(f.IdFornecedor)
	//		     		    		+ "   and dt_hr_ocorrencia_logeint > DATEADD(\"DAY\", -8, getdate()) "
	//		     		    		+ "   and isnull(ds_ocorrencia_logeint, '') not like '%est� fora dos padr�es do mercado.' ";
			     		    
			     		    String sqlVerificacaoCadastros = 
			     		   "select max(SUBSTRING(lei.ds_ocorrencia_logeint, 32, LEN(lei.ds_ocorrencia_logeint))) "
			     	+ "     , comp.nm_pessoa  as nm_comprador "
			     	+ " 	     , SUBSTRING(lei.ds_ocorrencia_logeint, 0, 32) "
			     	+ " 	  from dbo.Log_Erro_Integracao lei "
			     	+ " 	       INNER JOIN      dbo.Cotacao            as c    on lei.cd_cotacao_logeint   = c.cd_cotacao_cot "
			     	+ " 	       INNER JOIN      dbo.Comprador          as co   on co.id_comprador_compr = c.id_comprador_compr "
			     	+ " 	       INNER JOIN      dbo.pessoa             as comp on comp.id_pessoa = co.id_pessoa  "
			     	+ " 	 where lei.id_fornecedor_fornec = " + Integer.toString(f.IdFornecedor)
			     	+ " 	   and lei.dt_hr_ocorrencia_logeint > DATEADD(\"DAY\", -8, getdate()) "
			     	+ " 	   and isnull(lei.ds_ocorrencia_logeint, '') not like '%est� fora dos padr�es do mercado.' "
			     	+ " 	   and isnull(lei.ds_ocorrencia_logeint, '')     like '%O CNPJ%' "
			     	+ " 	group by comp.nm_pessoa "
			     	+ "         , SUBSTRING(lei.ds_ocorrencia_logeint, 0, 32) "  
			     	+ " 	UNION "
			     	+ " 	select distinct lei.ds_ocorrencia_logeint "
			     	+ " 	     , comp.nm_pessoa  as nm_comprador "
			     	+ " 	     , lei.ds_ocorrencia_logeint "
			     	+ " 	  from dbo.Log_Erro_Integracao lei "
			     	+ " 	       INNER JOIN      dbo.Cotacao            as c    on lei.cd_cotacao_logeint   = c.cd_cotacao_cot "
			     	+ " 	       INNER JOIN      dbo.Comprador          as co   on co.id_comprador_compr = c.id_comprador_compr "
			     	+ " 	       INNER JOIN      dbo.pessoa             as comp on comp.id_pessoa = co.id_pessoa  "
			     	+ " 	 where lei.id_fornecedor_fornec = " + Integer.toString(f.IdFornecedor)
			     	+ " 	   and lei.dt_hr_ocorrencia_logeint > DATEADD(\"DAY\", -8, getdate()) "
			     	+ " 	   and isnull(lei.ds_ocorrencia_logeint, '') not like '%est� fora dos padr�es do mercado.' "
			     	+ " 	   and isnull(lei.ds_ocorrencia_logeint, '') not like '%O CNPJ%' "
			     	+ " 	   and isnull(lei.ds_ocorrencia_logeint, '') not like '%informada no XML das ofertas n�o pode ser diferente da condi��o%' "
			     	+ " 	   and isnull(lei.ds_ocorrencia_logeint, '') not like 'J� existe outra oferta ativa da mesma empresa fornecedora%' "
			     	+ " 	   and isnull(lei.ds_ocorrencia_logeint, '') not like '%est� bloqueada no sistema " + f.SiglaSistemaFornecedor + " do fornecedor%' "
    	            + "        and isnull(lei.ds_ocorrencia_logeint, '') not like 'O C�digo de Produto %'";
	
			     		   Statement statVerificacaoCadastros = conn.createStatement();
			     		    ResultSet rSetVerificacaoCadastros = statVerificacaoCadastros.executeQuery(sqlVerificacaoCadastros);
			     		    int qtdVerificacaoCadastros = 0;

			     		    while (rSetVerificacaoCadastros.next()) {
			     		    	qtdVerificacaoCadastros += 1;
			     		    	if (qtdVerificacaoCadastros == 1) {
			     		    		body += " " + "\r\n"
			     		    	         + "<b>Aproveitando, tem mais um problema para resolver: favor informar ao gerente de vendas, ou ao vendedor respons�vel pelo Portal Cronos, que o motivo porque diversas cota��es n�o foram ofertadas automaticamente nos �ltimos 7 dias, � por causa de falta de cadastro dos seguintes clientes no " + f.SiglaSistemaFornecedor +": " + "<b>\r\n"
			     		    			 ;
			     		    	}
								body += rSetVerificacaoCadastros.getString(1).replace("da empresa compradora n�o foi encontrado no sistema", "da empresa compradora " + rSetVerificacaoCadastros.getString(2) + " n�o foi encontrado no sistema") + "\r\n";
								
							} 
	
			     		    sqlVerificacaoCadastros = "select distinct lei.ds_ocorrencia_logeint "
			     			     	+ "   from dbo.Log_Erro_Integracao lei "
			     			     	+ "  where lei.id_fornecedor_fornec = " + Integer.toString(f.IdFornecedor)
			     			     	+ "    and lei.dt_hr_ocorrencia_logeint > DATEADD(\"DAY\", -21, getdate()) "
			     	                + "    and isnull(lei.ds_ocorrencia_logeint, '') like 'O C�digo de Produto %'";
	   		
			     		    rSetVerificacaoCadastros = statVerificacaoCadastros.executeQuery(sqlVerificacaoCadastros);
			     		    int qtdVerificacaoProdutos = 0;

			     		    while (rSetVerificacaoCadastros.next()) {
			     		    	qtdVerificacaoProdutos += 1;
			     		    	if (qtdVerificacaoProdutos == 1 && qtdVerificacaoCadastros == 0) {
				 		     		   body += " " + "\r\n"
				 				     			+ "<b>Aproveitando, tem mais um problema para resolver: favor informar ao gerente de vendas, ou ao vendedor respons�vel pelo Portal Cronos, que o motivo porque os seguintes produtos n�o foram ofertados automaticamente em nenhuma cota��o nos �ltimos 20 dias, � por causa de falta ou erro de cadastro: <b>" + "\r\n";
				     		    }
			     		    	else if (qtdVerificacaoProdutos == 1 && qtdVerificacaoCadastros > 0) {
				 		     		   body += " " + "\r\n"
				 				     			+ "<b>Tem mais um problema para encaminhar para o gerente de vendas: nos �ltimos 20 dias os seguintes produtos n�o foram ofertados automaticamente em nenhuma cota��o por causa de falta ou erro de cadastro: <b>" + "\r\n";
			     		    	}
								body += rSetVerificacaoCadastros.getString(1) + "\r\n";
								
							} 
	
			     		    body += " " + "\r\n"
				     			+ "Atc," + "\r\n"
			     				+ "O email autom�tico do Portal Cronos " + "\r\n"
			     				+  "\r\n\r\n\r\n\r\n";
		           	    } // if (Utils.isNullOrBlank(rSet.getString(6)))
		           	     
		     		    dtCadastroIni = rSet.getTimestamp(7).toLocalDateTime();
		            	dtCadastroFim = rSet.getTimestamp(8).toLocalDateTime();
	            	 } // if (!nmFornecedor.equals("INI")) 
	            	 else {
		            	 dtCadastroIni = rSet.getTimestamp(7).toLocalDateTime();
		            	 dtCadastroFim = rSet.getTimestamp(8).toLocalDateTime();	            		 
	            	 }
	             } // while (rSet.next())
	             
	             if (!Utils.isNullOrBlank(nmFornecedor)) {
	 	             EmailAutomatico.enviar(remetenteEmailAutomatico, destinoEmailAutomatico, ccEmailAutomatico, assunto, null, body, provedorEmailAutomatico, portaEmailAutomatico, usuarioEmailAutomatico, senhaCriptografadaEmailAutomatico, diretorioArquivosXmlSemBarraNoFinal, horaInicio, diretorioArquivosXml, nmFornecedor);
	             }

	 	         rSet.close();
  	             results = cstat.getMoreResults();
	        } // while (results)
	    }
	    catch (java.lang.Exception ex) { 
	       logarErro(ex, false);	      
 	       EmailAutomatico.enviar(remetenteEmailAutomatico, destinoEmailAutomatico, ccEmailAutomatico, "Monitoramento integra��o - Erro! ", null, "Erro: " + ex.getMessage(), provedorEmailAutomatico, portaEmailAutomatico, usuarioEmailAutomatico, senhaCriptografadaEmailAutomatico, diretorioArquivosXmlSemBarraNoFinal, horaInicio, diretorioArquivosXml, "Monitoramento");
	    }
	    finally { 
	      if (cstat != null) {
	        try {
	          cstat.close() ;  // Isso fecha o rSet automaticamente tamb�m
	        }
	        catch (java.sql.SQLException e) {
	        }
	        cstat = null  ;
	      }
	      if (rSet != null)  rSet = null  ;
	      
		  if (conn != null) { 
		      try { 
		        if ( !conn.isClosed() ) {
		        	conn.close() ;
		        }
		      }
		      catch ( java.lang.Throwable t ) {  
		      }

		      conn = null ; 
		  }
	    } // finally  
  }

		
  private static void montarXmlOfertas(Transformer transformer, Document docOfertas, NodeList produtos, Element elmErros, String cdCotacao, String cdComprador, String tpFrete, DocumentBuilder docBuilder) throws SQLException {

		java.sql.Connection conn = null;
		java.sql.Statement stat = null;
		java.sql.ResultSet rSet = null;

		try
	    {  
		    docOfertas = docBuilder.newDocument();
			
		    elmErros = docOfertas.createElement("Erros");
		    String mensagemErro;
		
		
		    if (erroStaticConstructor != null)
		    {
		      enviarErroParaPortalCronos(docOfertas, elmErros, "", erroStaticConstructor);
		    }
		
		
		    Element elmOfertasCotacao = docOfertas.createElement("OfertasCotacao");
		    docOfertas.appendChild(elmOfertasCotacao);
		
		    Element elmDtGravacao = docOfertas.createElement("Dt_Gravacao");
		    Date hoje = new Date();
		    elmDtGravacao.appendChild(docOfertas.createTextNode(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(hoje)));
		    elmOfertasCotacao.appendChild(elmDtGravacao);
		
		    Element elmCdCotacao2 = docOfertas.createElement("Cd_Cotacao");
		    elmCdCotacao2.appendChild(docOfertas.createTextNode(cdCotacao));
		    elmOfertasCotacao.appendChild(elmCdCotacao2);
		
		    Element elmCdComprador2 = docOfertas.createElement("Cd_Comprador");
		    elmCdComprador2.appendChild(docOfertas.createTextNode(cdComprador));
		    elmOfertasCotacao.appendChild(elmCdComprador2);
		
		    Element elmCdFornecedor2 = docOfertas.createElement("Cd_Fornecedor");
		    elmCdFornecedor2.appendChild(docOfertas.createTextNode(cnpjFornecedor));
		    elmOfertasCotacao.appendChild(elmCdFornecedor2);
		
		    String connectionString = null;
		    
		    if (tipoBancoDeDados.equals("Oracle"))
		    {
		      OracleDriver orclDriver = new OracleDriver() ; 
		      DriverManager.registerDriver( orclDriver ) ; 	
		      connectionString = "jdbc:oracle:thin:@" + enderecoIpServidorBancoDeDados + ":" + portaServidorBancoDeDados + ":" + instanciaBancoDeDados; 
		    }
		    else if (tipoBancoDeDados.equals("Firebird"))
		    {
		         FBDriver fbDriver = new FBDriver();
		         DriverManager.registerDriver(fbDriver); // Antigamente: Class.forName("org.firebirdsql.jdbc.FBDriver"); 
		         connectionString = "jdbc:firebirdsql://" + enderecoIpServidorBancoDeDados + ":" + portaServidorBancoDeDados + "/" + instanciaBancoDeDados;
		    }
	   
		    conn = DriverManager.getConnection(connectionString, usernameBancoDeDados, senhaBancoDeDados ) ;	    
		    stat = conn.createStatement() ;
		    String sqlString = null;
		    boolean toNaoVerificarDemaisErros = false;
		    boolean existeCompradora = true;
		    String dsComprador = "";		
		    
		    
	        if (siglaSistema.equals("APS"))
	        {
	        	sqlString = "select cpfcgc   "
	                      + "  from cliente  "
	                      + " where replace(replace(replace(cpfcgc, '.',''), '/',''), '-','') = '" + cdComprador + "'"
	                      ;
	        }
	        else if (siglaSistema.equals("WinThor"))
	        {
			    sqlString = "select (PCCLIENT.CLIENTE || ' - ' || nvl(PCCLIENT.ESTCOB, '')) "
			              + "  from PCCLIENT        "
			              + " where replace(replace(replace(PCCLIENT.CGCENT, '.',''), '/',''), '-','') = '" + cdComprador + "'"
			              ;
	        }
		    // Para executar o SELECT direto no banco de dados, se precisar :
		    debugar(sqlString);
		
		    rSet = stat.executeQuery( sqlString ) ;
		    
		    if (rSet == null || !rSet.next()) 
		    {
		    	toNaoVerificarDemaisErros = true;
		    	existeCompradora = false;
		        enviarErroParaPortalCronos(docOfertas, elmErros, "", "Cota��o " + cdCotacao + " " + NAO_OFERTADA_IMPACTO_SE_ALTERAR + "! O CNPJ " + cdComprador + " da empresa compradora n�o foi encontrado no sistema " + siglaSistema + " do fornecedor " + nomeFantasiaFornecedor + ".");
		    }
		    else
		    {
			      if (siglaSistema.equals("WinThor")) 
			      {
				      dsComprador = ( (rSet.getObject(1) == null) ? "" : rSet.getString(1) ) ; 
			      }	    	
		    }

		    
		    
		    
		    rSet = null;
		    
		    if (existeCompradora)
		    {
				if (siglaSistema.equals("APS"))
		        {
		        	sqlString = null;
		        }
		        else if (siglaSistema.equals("WinThor"))
		        {
				    sqlString = "select PCCLIENT.DTEXCLUSAO "
				              + "  from PCCLIENT            "
				              + " where replace(replace(replace(PCCLIENT.CGCENT, '.',''), '/',''), '-','') = '" + cdComprador + "'"
				              + "   and PCCLIENT.DTEXCLUSAO is not null "
				              ;
		        }
		
		        if (sqlString != null)
		        {
				    // Para executar o SELECT direto no banco de dados, se precisar :
				    debugar(sqlString);
				
				    rSet = stat.executeQuery( sqlString ) ;
				
				    if (rSet != null && rSet.next()) 
				    {
			    	   toNaoVerificarDemaisErros = true;
			    	   try {
					       Date dataExclusao = rSet.getDate(1);  
			    	       enviarErroParaPortalCronos(docOfertas, elmErros, "", "Cota��o " + cdCotacao + " " + NAO_OFERTADA_IMPACTO_SE_ALTERAR + "! A empresa compradora " + (dsComprador != "" ? dsComprador : cdComprador) + " (CNPJ " + cdComprador + ") foi desativada no sistema " + siglaSistema + " do fornecedor " + nomeFantasiaFornecedor + " no dia " + new SimpleDateFormat("dd/MM/yyyy").format(dataExclusao) + ".");
			    	   }
			    	   catch (java.lang.Exception ex) {
				    	   debugar("Catch Exception entrado: n�o foi poss�vel enviar a seguinte mensagem informando dataExclusao: Cota��o " + cdCotacao + " " + NAO_OFERTADA_IMPACTO_SE_ALTERAR + "! A empresa compradora " + (dsComprador != "" ? dsComprador : cdComprador) + " (CNPJ " + cdComprador + ") foi desativada no sistema " + siglaSistema + " do fornecedor " + nomeFantasiaFornecedor + ".");			    	     
			    	   }
				    }
		        } // if (sqlString != null)

		    
			    rSet = null;
			    
				if (siglaSistema.equals("APS"))
		        {
		        	sqlString = null;
		        }
		        else if (siglaSistema.equals("WinThor"))
		        {
				    sqlString = "select PCCLIENT.DTBLOQ "
				              + "  from PCCLIENT        "
				              + " where replace(replace(replace(PCCLIENT.CGCENT, '.',''), '/',''), '-','') = '" + cdComprador + "'"
				              + "   and PCCLIENT.BLOQUEIO = 'S'  "
				              ;
		        }
		
		        if (sqlString != null && !toNaoVerificarDemaisErros)
		        {
				    // Para executar o SELECT direto no banco de dados, se precisar :
				    debugar(sqlString);
				
				    rSet = stat.executeQuery( sqlString ) ;
				
				    if (rSet != null && rSet.next()) 
				    {
			    	   toNaoVerificarDemaisErros = true;
			    	   
			    	   if (rSet.getObject(1) == null)
				    	   enviarErroParaPortalCronos(docOfertas, elmErros, "", "Cota��o " + cdCotacao + " " + NAO_OFERTADA_IMPACTO_SE_ALTERAR + "! A empresa compradora " + (dsComprador != "" ? dsComprador : cdComprador) + " (CNPJ " + cdComprador + ") est� bloqueada no sistema " + siglaSistema + " do fornecedor " + nomeFantasiaFornecedor + ".");
			    	   else 
			    	   {
				    	   try {
			    	          Date dataBloqueio = rSet.getDate(1);  
			    	          enviarErroParaPortalCronos(docOfertas, elmErros, "", "Cota��o " + cdCotacao + " " + NAO_OFERTADA_IMPACTO_SE_ALTERAR + "! A empresa compradora " + (dsComprador != "" ? dsComprador : cdComprador) + " (CNPJ " + cdComprador + ") est� bloqueada no sistema " + siglaSistema + " do fornecedor " + nomeFantasiaFornecedor + " desde o dia " + new SimpleDateFormat("dd/MM/yyyy").format(dataBloqueio) + ".");
				    	   }
				    	   catch (java.lang.Exception ex) {
					    	   debugar("Catch Exception entrado: n�o foi poss�vel enviar a seguinte mensagem informando dataBloqueio: Cota��o " + cdCotacao + " " + NAO_OFERTADA_IMPACTO_SE_ALTERAR + "! A empresa compradora " + (dsComprador != "" ? dsComprador : cdComprador) + " (CNPJ " + cdComprador + ") est� bloqueada no sistema " + siglaSistema + " do fornecedor " + nomeFantasiaFornecedor + ".");			    	     
				    	   }
			    	   }
				    }
		        } // if (sqlString != null)
		    } // if (existeCompradora)

		    
	        
		    rSet = null;

		    if (siglaSistema.equals("APS"))
	        {
	        	sqlString = "select codtipopag "
	                      + "  from cliente    "
	                      + " where replace(replace(replace(cpfcgc, '.',''), '/',''), '-','') = '" + cdComprador + "'"
	                      ;
	                   // + "   and codstatus = 1" // 1 = BLOQUEADO, no APS isso quer dizer apenas bloqueado para pagto. a prazo, e N�O bloqueado para pagto. a vista !
	        }
	        else if (siglaSistema.equals("WinThor"))
	        {
			    sqlString = "select PCCLIENT.CODPLPAG "
			              + "  from PCCLIENT          "
			              + " where replace(replace(replace(PCCLIENT.CGCENT, '.',''), '/',''), '-','') = '" + cdComprador + "'"
			              + "   and PCCLIENT.BLOQUEIO   <> 'S'  "
			              + "   and PCCLIENT.DTEXCLUSAO is null "
			              ;
	        }
		    // Para executar o SELECT direto no banco de dados, se precisar :
		    debugar(sqlString);
		
		    rSet = stat.executeQuery( sqlString ) ;
		
		    String cdCondicaoPagamento = "";
		
		    if (rSet != null && rSet.next()) 
		    {
		      cdCondicaoPagamento = ( (rSet.getObject(1) == null) ? "" 
		    		                                              : (siglaSistema.equals("APS") ? Integer.toString(rSet.getInt(1))
		    		                                            		                        : (siglaSistema.equals("WinThor") ? rSet.getString(1)
		    		                                            		                        		                          : ""
		    		                                            		                          )
		    		                                                )
		    		                ) ;  
		    } 
		
		    if (cdCondicaoPagamento.equals("") && !toNaoVerificarDemaisErros)
		    {
		      enviarErroParaPortalCronos(docOfertas, elmErros, "", "Cota��o " + cdCotacao + " " + NAO_OFERTADA_IMPACTO_SE_ALTERAR + "! A Condi��o de Pagamento da empresa compradora " + (dsComprador != "" ? dsComprador : cdComprador) + " (CNPJ " + cdComprador + ") n�o foi informada no sistema " + siglaSistema + " do fornecedor " + nomeFantasiaFornecedor + ".");
		    }
		
		
		
		    Element elmCdCondicaoPagamento = docOfertas.createElement("Cd_Condicao_Pagamento_Fornecedor");
		    elmCdCondicaoPagamento.appendChild(docOfertas.createTextNode(cdCondicaoPagamento));
		    elmOfertasCotacao.appendChild(elmCdCondicaoPagamento);
		
		    Element elmTpFrete = docOfertas.createElement("Tp_Frete_Fornecedor");
		    elmTpFrete.appendChild(docOfertas.createTextNode(tpFrete));
		    elmOfertasCotacao.appendChild(elmTpFrete);
		 
		    String strQtPrzEntrega = "";
	        if (siglaSistema.equals("APS"))
	 	      strQtPrzEntrega = "";
	        else if (siglaSistema.equals("WinThor"))
	   	      strQtPrzEntrega = "";
		
		    Element elmQtPrzEntrega = docOfertas.createElement("Qt_Prz_Entrega");
		    elmQtPrzEntrega.appendChild(docOfertas.createTextNode(strQtPrzEntrega));
		    elmOfertasCotacao.appendChild(elmQtPrzEntrega);
		
		    BigDecimal vlMinimoPedido = null;
			
		    rSet = null;

		    if (        siglaSistema.equals("APS")
		    		|| (siglaSistema.equals("WinThor") && !toUsarValorMinimoSistemaFornecedor )
		       )
	        {
	        	vlMinimoPedido = null; // Vers�o anterior : new BigDecimal(300.00); 
	        }
	        else if (siglaSistema.equals("WinThor"))
	        {
			    sqlString = "select PCPLPAG.VLMINPEDIDO "
			              + "  from PCCLIENT            "
			              + "     , PCPLPAG             "
			              + " where replace(replace(replace(PCCLIENT.CGCENT, '.',''), '/',''), '-','') = '" + cdComprador + "'"
			              + "   and PCCLIENT.CODPLPAG   =  PCPLPAG.CODPLPAG "
			              + "   and PCCLIENT.BLOQUEIO   <> 'S'              "
			              + "   and PCCLIENT.DTEXCLUSAO is null             "
			              ;
			    // Para executar o SELECT direto no banco de dados, se precisar :
			    debugar(sqlString);
			
			    rSet = stat.executeQuery( sqlString ) ;
			
			    if (rSet != null && rSet.next()) 
			    {
			      vlMinimoPedido = ( (rSet.getObject(1) == null) ? null : rSet.getBigDecimal(1).setScale(2, BigDecimal.ROUND_HALF_UP)  ) ;  
			    }

			    if (vlMinimoPedido == null && !toNaoVerificarDemaisErros)
			    {
			    	// O Valor M�nimo pode ser R$ 0,00 por�m n�o pode ser em branco porque assim o sistema n�o sabe 
			    	// se for R$ 0,00 ou se � para usar o Valor M�nimo geral do fornecedor cadastrado no Portal Cronos :
			        enviarErroParaPortalCronos(docOfertas, elmErros, "", "Cota��o " + cdCotacao + " " + NAO_OFERTADA_IMPACTO_SE_ALTERAR + "! O Valor M�nimo para Entrega para a empresa compradora " + (dsComprador != "" ? dsComprador : cdComprador) + " (CNPJ " + cdComprador + ") n�o pode ser nulo ou em branco no sistema " + siglaSistema + " do fornecedor " + nomeFantasiaFornecedor + ", pois a configura��o \"UsarValorMinimoSistemaFornecedor\" = \"true\". Valores permitidos: (R$) 0,00 ou um valor > 0.");
			    }
	        }
		
	        
		
		
		    nf.setGroupingUsed(false);
		    nf.setMaximumFractionDigits(2);
		    nf.setMinimumFractionDigits(2);  
		
		    Element elmVlMinimoPedido = docOfertas.createElement("Vl_Minimo_Pedido");
		    elmVlMinimoPedido.appendChild(docOfertas.createTextNode( (vlMinimoPedido == null ? "" : nf.format(vlMinimoPedido))  ));
		    elmOfertasCotacao.appendChild(elmVlMinimoPedido);
		
		    Element elmDsObservacaoFornecedor = docOfertas.createElement("Ds_Observacao_Fornecedor");
		    elmDsObservacaoFornecedor.appendChild(docOfertas.createTextNode(ObsOfertasPadraoSeNaoTemNoSistema));
		    elmOfertasCotacao.appendChild(elmDsObservacaoFornecedor);
		
		
		    rSet = null;

		    if (siglaSistema.equals("APS"))
	        {
	        	sqlString = "select replace(precousado,'�','c') "
	                      + "  from cliente "
	                      + " where replace(replace(replace(cpfcgc, '.',''), '/',''), '-','') = '" + cdComprador + "'";
	        }
	        else if (siglaSistema.equals("WinThor"))
	        {
			    // PVENDA1 NUMBER(16,6) preco referente tabela 1
			    // PVENDA2 NUMBER(16,6) preco referente tabela 2
			    // PVENDA3 NUMBER(16,6) preco referente tabela 3
			    // PVENDA4 NUMBER(16,6) preco referente tabela 4
			    // PVENDA5 NUMBER(16,6) preco referente tabela 5
			    // PVENDA6 NUMBER(16,6) preco referente tabela 6
			    // PVENDA7 NUMBER(16,6) preco referente tabela 7
			
			    // OBS: Compor o campo pvenda com a seguinte forma
			    // 1. Acessar a tabela PCPLPAG (TABELA DE PLANO DE PAGAMENTO) atraves do campo CODPLPAG na tabela PCLIENT (Tabela de Clientes)
			    // 2. Acessar os seguintes campos:
			    //    NUMPR NUMBER(6,2) = O intervalo fica entre 1 e 7. Serve para identificar o final do campo PVENDAx
			    //    VLMINPEDIDO NUMBER(12,2) = Valor minimo para o pedido.
			
			    sqlString = "select PCPLPAG.NUMPR "
			              + "  from PCCLIENT, PCPLPAG "
			              + " where replace(replace(replace(PCCLIENT.CGCENT, '.',''), '/',''), '-','') = '" + cdComprador + "'"
			              + "   and PCCLIENT.CODPLPAG   =  PCPLPAG.CODPLPAG "
			              + "   and PCCLIENT.BLOQUEIO   <> 'S' "
			              + "   and PCCLIENT.DTEXCLUSAO is null "
			              ;
	        }
		
		    // Para executar o SELECT direto no banco de dados, se precisar :
		    debugar(sqlString);
		
		    rSet = stat.executeQuery( sqlString ) ;
			
		    String tipoPrecoComprador = null;
		
		    if (rSet != null && rSet.next()) 
		    {
	  	      tipoPrecoComprador = ( (rSet.getObject(1) == null) ? null 
	  	    		                                             : (siglaSistema.equals("APS") ? rSet.getString(1) 
	  	    		                                            		                       : (siglaSistema.equals("WinThor") ? rSet.getBigDecimal(1).toString()
	  	    		                                            		                    		                             : null
	  	    		                                            		                         )
	  	    		                                               )
	  	    		               ) ; 
		    }
		
		    if (tipoPrecoComprador == null && !toNaoVerificarDemaisErros)
		    {
		      enviarErroParaPortalCronos(docOfertas, elmErros, "", "Cota��o " + cdCotacao + " " + NAO_OFERTADA_IMPACTO_SE_ALTERAR + "! O tipo de pre�o para a empresa compradora " + (dsComprador != "" ? dsComprador : cdComprador) + " (CNPJ " + cdComprador + ") n�o foi informado no sistema " + siglaSistema + " do fornecedor " + nomeFantasiaFornecedor + ".");
		    }
		    else if (    siglaSistema.equals("WinThor")
	                      && tipoPrecoComprador != null 
	     	              && !(    tipoPrecoComprador.equals("1")
			                    || tipoPrecoComprador.equals("2")
			                    || tipoPrecoComprador.equals("3")
			                    || tipoPrecoComprador.equals("4")
			                    || tipoPrecoComprador.equals("5")
			                    || tipoPrecoComprador.equals("6")
			                    || tipoPrecoComprador.equals("7")
		                  )
		            )
		    {
	          // Apenas verificar isso na primeira instala��o en cada empresa fornecedora,
	          // pois a causa do "erro" pode ser que uma vers�o nova do WinThor tem uma coluna nova, ent�o n�o � erro, 
	          // e tb pode ser algum erro de verdade : 
		      debugar("Poss�vel erro: tipo de pre�o (" + tipoPrecoComprador + ") imprevisto no sistema " + siglaSistema + " do fornecedor " + nomeFantasiaFornecedor + ". Valores previstos : 1, 2, 3, 4, 5, 6, 7. Favor verificar.");
		    }
		    
		
		    Integer numRegiaoWinThor = null;
		    rSet = null;
			
		    if (siglaSistema.equals("WinThor"))
		    {
			    sqlString = "select PCPRACA.NUMREGIAO "
			              + "  from PCPRACA           "
			              + "     , PCCLIENT          "
			              + " where PCPRACA.CODPRACA = PCCLIENT.CODPRACA "
			              + "   and replace(replace(replace(PCCLIENT.CGCENT, '.',''), '/',''), '-','') = '" + cdComprador + "'"
			              + "   and PCCLIENT.BLOQUEIO   <> 'S'  "
			              + "   and PCCLIENT.DTEXCLUSAO is null "
			              ;
			
			    // Para executar o SELECT direto no banco de dados, se precisar :
			    debugar(sqlString);
			
			    rSet = stat.executeQuery( sqlString ) ;
			
			    if (rSet != null && rSet.next()) 
			    {
			      numRegiaoWinThor = ( (rSet.getObject(1) == null) ? null : rSet.getInt(1)  ) ;  
			    }

			    if (numRegiaoWinThor == null && !toNaoVerificarDemaisErros)
			    {
			      enviarErroParaPortalCronos(docOfertas, elmErros, "", "Cota��o " + cdCotacao + " " + NAO_OFERTADA_IMPACTO_SE_ALTERAR + "! A regi�o da empresa compradora " + (dsComprador != "" ? dsComprador : cdComprador) + " (CNPJ " + cdComprador + ") n�o foi informada no sistema " + siglaSistema + " do fornecedor " + nomeFantasiaFornecedor + ".");
			    }
		    }
		
		
		    Element elmProdutos = docOfertas.createElement("Produtos");
	        elmOfertasCotacao.appendChild(elmProdutos); // Para pelo menos gerar o tag vazio "<Produtos/>" no caso que o arquivo XML for gravado 
		
		    if (!temErroGeralCotacao)
		    {	
		      for (int j = 0; j < produtos.getLength(); j++) 
		      {
		        readProduto(produtos, j, docOfertas, elmProdutos, elmErros, stat, rSet, tipoPrecoComprador, numRegiaoWinThor);
		      }
		    }
		
		    elmOfertasCotacao.appendChild(elmErros);  // Para pelo menos gerar o tag vazio "<Erros/>" no caso que o arquivo XML for gravado
		    
		    debugar("elmProdutos.hasChildNodes() = " + elmProdutos.hasChildNodes());
		    debugar("elmErros.hasChildNodes()    = " + elmErros.hasChildNodes());
		
		    // elmProdutos e elmErros nunca s�o null se chegar aqui :
		    if (    elmProdutos.hasChildNodes()
		         || elmErros.hasChildNodes() 
		       )
		    {   
		    	uploadXmlOfertas(docOfertas, cdCotacao, transformer, hoje);
		    }
	    }
	    catch (java.lang.Exception ex) { 
	      logarErro(ex, false);
	      enviarErroParaPortalCronos(docOfertas, elmErros, null, printStackTraceToString(ex));  
	    }
	    finally { 
	      if (stat != null) {
	        try {
	          stat.close() ;  // Isso fecha o rSet automaticamente tamb�m
	        }
	        catch (java.sql.SQLException e) {
	        }
	        stat = null  ;
	      }
	      if (rSet != null)  rSet = null  ;
	      
		  if (conn != null) { 
		      try { 
		        if ( !conn.isClosed() ) {
		        	conn.close() ;
		        }
		      }
		      catch ( java.lang.Throwable t ) {  
		      }

		      conn = null ; 
		  }
	    } // finally
  }
 
  
  
  private static void uploadXmlOfertas(Document doc, String cdCotacao, Transformer transformer, Date hoje) throws IOException, TransformerException {
	    final DOMSource sourceOfertas = new DOMSource(doc);
	    String filenameOfertas = diretorioArquivosXml + String.format("OFE_%s_%s_%s.xml", cnpjFornecedor, cdCotacao, new SimpleDateFormat("yyyyMMdd'_'HHmmss").format(hoje));
      	Files.deleteIfExists(Paths.get(filenameOfertas));
	    final StreamResult resultOfertas = new StreamResult(new File(filenameOfertas));
	    transformer.transform(sourceOfertas, resultOfertas);
	
	    upload_File(enderecoBaseWebService + "FornecedorCotacao/EnviaArquivosOfertasCotacao", new File(filenameOfertas), "form1", username, senha) ;
	 // Funcionou : uploadOfertas_File(enderecoBaseWebService + "cotacao/EnviaArquivosRecebimentoCotacao", new File(diretorioArquivosXml + "TesteWebServiceConfirmRecebCotacoes.xml"), "form1", username, senha) ;
	 // uploadOfertas_BodyXML(enderecoBaseWebService + "cotacao/EnviaArquivosRecebimentoCotacao", (diretorioArquivosXml + "TesteWebServiceConfirmRecebCotacoes.xml"), username, senha) ;
  }

	    
  private static void readProduto(NodeList produtos, int i, Document docOfertas, Element elmProdutos, Element elmErros, java.sql.Statement stat, java.sql.ResultSet rSet, String tipoPrecoComprador, Integer numRegiaoWinThor) throws SQLException
  {
    String mensagemErro;

    Element element = (Element)produtos.item(i);

    NodeList nlist = element.getElementsByTagName("Cd_Produto_Fornecedor");
    Element elm = (Element) nlist.item(0);
    String cdProdutoFornecedor = getCharacterDataFromElement(elm);

    nlist = element.getElementsByTagName("Cd_Produto");
    elm = (Element) nlist.item(0);
    String cdProduto = getCharacterDataFromElement(elm);

    debugar("cdProdutoFornecedor: " + cdProdutoFornecedor + ", cdProduto: " + cdProduto);

    // Pulando <Cd_Unidade_Compra> do XML de /ObtemCotacoes

    nlist = element.getElementsByTagName("Qt_Embalagem");
    elm = (Element) nlist.item(0);
    String qtEmbalagem = getCharacterDataFromElement(elm);
 // Debugar("qtEmbalagem: " + qtEmbalagem);

    nlist = element.getElementsByTagName("Qt_Produto");
    elm = (Element) nlist.item(0);
    String qtSolicitada = getCharacterDataFromElement(elm);
 // Debugar("qtSolicitada: " + qtSolicitada);

    // =============================================================================
    //
    // Verificar se o c�digo de produto existe :
    //
    // =============================================================================

    String sqlString = null;
    rSet = null;
    
    if (siglaSistema.equals("APS"))
    {
    	sqlString = "select 1 "
                  + "  from produto "
                  + "       join prodfilho on produto.codproduto = prodfilho.codproduto "
                  + " where prodfilho.codprodfilho = " + cdProdutoFornecedor
                  + " ";
                  // No APS � normal que o produto pode ficar desativado temporariamente, 
                  // ent�o neste momento n�o usar a condi��o + "   and produto.ativo          = 2 ".
    }
    else if (siglaSistema.equals("WinThor"))
    {
	    // 3. Acessar a tabela PCTABPR (TABELA DE PRECOS) atravez do campo PCTABPR.CODPROD (CODIGO DO PRODUTO) e PCTABPR.NUMREGIAO (NUMERO DA REGIAO) COM O VALOR 1
	    // 4. Compor o preco unitario PVENDA + NUMPR.
	    sqlString = "select 1 "
	              + "  from PCPRODUT "
	              + " where PCPRODUT.CODPROD    = " + cdProdutoFornecedor
	              + "   and PCPRODUT.REVENDA    = 'S' "
	              + "   and PCPRODUT.DTEXCLUSAO is null  ";
    } // if (siglaSistema.equals("WinThor"))
    
    // Para executar o SELECT direto no banco de dados, se precisar :
    if (i == 0) debugar(sqlString);

    rSet = stat.executeQuery( sqlString ) ;

    if (rSet == null || !rSet.next()) 
    {
      enviarErroParaPortalCronos(docOfertas, elmErros, cdProdutoFornecedor, "O C�digo de Produto " + cdProdutoFornecedor + " do Fornecedor, informado na tela De-Para de Produtos no Portal Cronos, n�o existe no sistema " + siglaSistema + " do fornecedor " + nomeFantasiaFornecedor + ".");
      return; // Obs.: o produto pode ser abortado via return, por�m a cota��o nunca, para poder enviar o erro para o Portal Cronos
    }

    // =============================================================================
    //
    // Ofertar :
    //
    // =============================================================================

    rSet = null;
    sqlString = null;

    if (siglaSistema.equals("APS"))
    {
    	sqlString = "select precoempresa." + tipoPrecoComprador + " "
                  + "  from precoempresa "
                  + "       join prodfilho on prodfilho.codprodfilho = precoempresa.codprodfilho "
                  + "       join produto   on produto.codproduto     = prodfilho.codproduto ";
    }
    else if (siglaSistema.equals("WinThor"))
    {
	    // 3. Acessar a tabela PCTABPR (TABELA DE PRECOS) atravez do campo PCTABPR.CODPROD (CODIGO DO PRODUTO) e PCTABPR.NUMREGIAO (NUMERO DA REGIAO) COM O VALOR 1
	    // 4. Compor o preco unitario PVENDA + NUMPR.
    	// 5. A coluna PCPRODUT.CODSEC est� NOT NULL, por�m a coluna PSECAO.QTMAX est� NULO, 
    	//    ent�o podemos fazer uma inner join com PSECAO.
	    sqlString = "select (PCTABPR.PVENDA" + tipoPrecoComprador + " * ((nvl(PCSECAO.QTMAX, 0)/100) + 1)) " 
	              + "  from PCTABPR  "
	              + "     , PCPRODUT "
	              + "     , PCSECAO  "
	              ;
    } // if (siglaSistema.equals("WinThor"))
    
    String sqlStringSemEstoque = sqlString;
    String sqlStringComEstoque = sqlString;

    if (toVerificarEstoque)
    {
        if (siglaSistema.equals("APS"))
        {
        	sqlStringComEstoque += "       join estoqueempresa  on estoqueempresa.codprodfilho = precoempresa.codprodfilho ";

            if (criterioVerificacaoEstoque.equals("QtdEstoqueMaiorOuIgualQtdSolicitada"))
            	sqlStringComEstoque += "                        and estoqueempresa.estoque >= " + qtSolicitada;
            else if (criterioVerificacaoEstoque.equals("QtdEstoqueMaiorZero"))
            	sqlStringComEstoque += "                        and estoqueempresa.estoque > 0 ";
        }
        else if (siglaSistema.equals("WinThor"))
        {
           sqlStringComEstoque += "       , PCEST  ";
        }
    } // if (toVerificarEstoque)

    
    if (siglaSistema.equals("APS"))
    {
    	sqlStringSemEstoque += " where precoempresa.codprodfilho = " + cdProdutoFornecedor
                            +  "   and produto.ativo             = 2 "
                            +  "   and produto.ativopesq         = 1 ";
        if (toVerificarEstoque) 
        	sqlStringComEstoque += " where precoempresa.codprodfilho = " + cdProdutoFornecedor
                                +  "   and produto.ativo             = 2 "
                                +  "   and produto.ativopesq         = 1 ";
    }
    else if (siglaSistema.equals("WinThor"))
    {
    	String sqlStringTemp = " where PCTABPR.CODPROD   = " + cdProdutoFornecedor
                             +  "   and PCTABPR.NUMREGIAO = " + Integer.toString((numRegiaoWinThor)) 
                             +  "   and PCPRODUT.CODPROD = PCTABPR.CODPROD "
                             +  "   and PCPRODUT.REVENDA = 'S' "
                             +  "   and PCPRODUT.DTEXCLUSAO is null "
                             +  "   and PCPRODUT.CODSEC = PCSECAO.CODSEC "
                          // +  "   and PCTABPR.CODPLPAG = " + cdCondicaoPagamento
                             ;
	    sqlStringSemEstoque += sqlStringTemp;
	
	    if (toVerificarEstoque)
	    {
		    sqlStringComEstoque += sqlStringTemp
	                            +  "   and PCEST.CODPROD   = PCTABPR.CODPROD "
	                            +  "   and PCEST.CODFILIAL = " + Integer.toString(codigoFilialWinThor) + " "
	                            ;
	  
	        if (criterioVerificacaoEstoque.equals("QtdEstoqueMaiorOuIgualQtdSolicitada"))
	          sqlStringComEstoque += "   and (nvl(PCEST.QTESTGER,0) - nvl(PCEST.QTRESERV,0) - nvl(PCEST.QTPENDENTE,0) - nvl(PCEST.QTBLOQUEADA,0)) >= " + qtSolicitada;
	        else if (criterioVerificacaoEstoque.equals("QtdEstoqueMaiorZero"))
	          sqlStringComEstoque += "   and (nvl(PCEST.QTESTGER,0) - nvl(PCEST.QTRESERV,0) - nvl(PCEST.QTPENDENTE,0) - nvl(PCEST.QTBLOQUEADA,0)) > 0 ";
	   
	    } // if (toVerificarEstoque)
    } // if (siglaSistema.equals("WinThor"))

	BigDecimal preco = null;
	boolean temCadastroPreco = true;

    if (toVerificarEstoque) {
  	   rSet = stat.executeQuery( sqlStringSemEstoque ) ;

  	   if (rSet != null && rSet.next()) 
 	      preco = ( (rSet.getObject(1) == null) ? null : rSet.getBigDecimal(1).setScale(4, BigDecimal.ROUND_HALF_UP)  ) ;
  	   else
           temCadastroPreco = false;

  	   if (preco == null) {
           debugar("Tipo de pre�o " + tipoPrecoComprador + ": pre�o n�o encontrado para produto " + cdProdutoFornecedor + " no sistema " + siglaSistema + " do fornecedor " + nomeFantasiaFornecedor + ".");
           temCadastroPreco = false;
  	   }
  	   else if (preco.compareTo(BigDecimal.ZERO) == 0) {
           debugar("Tipo de pre�o " + tipoPrecoComprador + ": pre�o = R$ 0,00 para produto " + cdProdutoFornecedor + " no sistema " + siglaSistema + " do fornecedor " + nomeFantasiaFornecedor + ".");
           temCadastroPreco = true; // Este flag indica apenas se existe um registro na tabela de pre�os ou n�o
  	   }
    }

	rSet = null;
	preco = null;

	rSet = stat.executeQuery( toVerificarEstoque ? sqlStringComEstoque : sqlStringSemEstoque ) ;

    if (rSet != null && rSet.next()) 
    {
    	// Diferente de C# (Math.Round()), setScale() automaticamente n�o faz nada no caso do APS que s� tem 
    	// 2 decimais na base de dados, e faz round para 4 no caso do WinTor automaticamente quando se aplica :
        preco = ( (rSet.getObject(1) == null) ? null : rSet.getBigDecimal(1).setScale(4, BigDecimal.ROUND_HALF_UP)  ) ;  
    }

    nf.setGroupingUsed(false);
    nf.setMaximumFractionDigits(4);
    nf.setMinimumFractionDigits(4);

    if (preco == null)
    {
    	if (toVerificarEstoque) {
    		if (temCadastroPreco) {
                 debugar("Estoque n�o encontrado para produto " + cdProdutoFornecedor + " no sistema " + siglaSistema + " do fornecedor " + nomeFantasiaFornecedor + ".");
    		}
    	}
    	else {
    		temCadastroPreco = false;
    		debugar("Tipo de pre�o " + tipoPrecoComprador + ": pre�o n�o encontrado para produto " + cdProdutoFornecedor + " no sistema " + siglaSistema + " do fornecedor " + nomeFantasiaFornecedor + ".");
    	}

    	debugar(toVerificarEstoque ? sqlStringComEstoque : sqlStringSemEstoque);
    	// se n�o tiver pre�o ou estoque, n�o ofertar o produto e continuar com o pr�ximo produto:
        return;
    }
    else if (preco.compareTo(BigDecimal.ZERO) == 0) {
    	// N�o debugar a mesma coisa duas vezes:
    	if (!toVerificarEstoque) {
          debugar("Tipo de pre�o " + tipoPrecoComprador + ": pre�o = R$ 0,00 para produto " + cdProdutoFornecedor + " no sistema " + siglaSistema + " do fornecedor " + nomeFantasiaFornecedor + ".");
          temCadastroPreco = true; // Este flag indica apenas se existe um registro na tabela de pre�os ou n�o
        }
    }
    else if (i == 0) 
    	debugar(toVerificarEstoque ? sqlStringComEstoque : sqlStringSemEstoque);
    else
    	debugar("Pre�o: " + nf.format(preco));
     

    Element elmProduto = docOfertas.createElement("Produto");
    elmProdutos.appendChild(elmProduto);

    Element elmCdProdutoFornecedor = docOfertas.createElement("Cd_Produto_Fornecedor");
    elmCdProdutoFornecedor.appendChild(docOfertas.createTextNode(cdProdutoFornecedor));
    elmProduto.appendChild(elmCdProdutoFornecedor);

    Element elmCdProduto = docOfertas.createElement("Cd_Produto");
    elmCdProduto.appendChild(docOfertas.createTextNode(cdProduto));
    elmProduto.appendChild(elmCdProduto);

    Element elmQtEmbalagem = docOfertas.createElement("Qt_Embalagem");
    elmQtEmbalagem.appendChild(docOfertas.createTextNode(qtEmbalagem));
    elmProduto.appendChild(elmQtEmbalagem);

    Element elmVlPreco = docOfertas.createElement("Vl_Preco");
    elmVlPreco.appendChild(docOfertas.createTextNode( (preco == null ? "" : nf.format(preco)) ));
    elmProduto.appendChild(elmVlPreco);

    String dsObservacaoProduto = "";

    Element elmDsObservacaoProduto = docOfertas.createElement("Ds_Obs_Oferta_Fornecedor");
    elmDsObservacaoProduto.appendChild(docOfertas.createTextNode(dsObservacaoProduto));
    elmProduto.appendChild(elmDsObservacaoProduto);
  }



  private static void downloadCotacoes(LocalDateTime horaInicio, String url, String cnpjFornecedor, String username, String senha)   
  {
 
    try 
    {
		Client client = Client.create();
	
		WebResource webResource = client.resource(url);
	    client.addFilter(new HTTPBasicAuthFilter(username, senha));
	
		ClientResponse response = webResource.accept("application/xml")
	                                             .get(ClientResponse.class);
	
        String strXmlRecebido = response.getEntity(String.class);    	
		debugar("Output from " + url + " .... \n");
		debugar(strXmlRecebido);

		if (response.getStatus() != 200 && response.getStatus() != 202) {
	        client.destroy();
		   throw new Exception("\r\n  Erro! Favor verificar todas as configura��es no  arquivo \"" 
	                          + NOME_ARQUIVO_PROPERTIES 
	                          + "\" ! \r\n    Erro: HTTP Status Code = " 
	                          + response.getStatus() 
	                          + " (" + response.getClientResponseStatus().getReasonPhrase() 
	                          + ") ao chamar o web service " 
	                          + url 
	                          + " : \r\n"
	                          + strXmlRecebido);
		}
	
        client.destroy();


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        DocumentBuilder docBuilder = factory.newDocumentBuilder();

        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(strXmlRecebido));

        Document xmlCotacoes = docBuilder.parse(is);

	    Transformer transformerDebug = transformerFactory.newTransformer();
	    transformerDebug.setOutputProperty(OutputKeys.ENCODING, "iso-8859-1");
		transformerDebug.setOutputProperty(OutputKeys.INDENT, "yes");
        transformerDebug.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        final DOMSource sourceDebug = new DOMSource(xmlCotacoes);
        final StreamResult resultDebug = new StreamResult(new StringWriter());
        transformerDebug.transform(sourceDebug, resultDebug);
        debugar("xmlCotacoesRecebido = " + resultDebug.getWriter().toString());

        NodeList cotacoes = xmlCotacoes.getElementsByTagName("Cotacao");


        for (int i = 0; i < cotacoes.getLength(); i++) 
        { 
            temErroGeralCotacao = false;            
  		    LocalDateTime horaAtual = LocalDateTime.now();
  		    long MinutosExecucao = Duration.between(horaInicio, horaAtual).toMinutes();
  		    
  			// O seguinte � necess�rio para evitar que o processo atual fica abortado indevidamente 
  			// pelo Windows Task Scheduler, que tem que derrubar processos JRE anteriores travados 
  			// a cada 15 minutos: 
  		    if (MinutosExecucao < 10)
              readCotacao(cotacoes, i, docBuilder);
  		    else {
  		    	debugar("O processo JRE passou o limite de 10 minutos!! " + Integer.toString(cotacoes.getLength() - i) + " cota��es foram deixadas para o pr�ximo processamento.");
  		    	break;
  		    }
        }

    }
/*
    catch (ParserConfigurationException pce) 
    {
	pce.printStackTrace();
    } 
    catch (TransformerException tfe)  
    {
	tfe.printStackTrace();
    }
    catch (SQLException sqlex)
    {
	sqlex.printStackTrace();
    }
*/
    catch (Exception ex) 
    {
        logarErro(ex, false);
    }
  }



  public static void upload_File(String url, File f, String formName, String username, String senha) {

/* Formato retornado pelo web service :
 
  <xml version="1.0" encoding="UTF-8">
  <arquivos>
     <arquivo nome="Arquivo 1">
        <erros_impedindo_recebimento_xml>
            <erro>Erro 1</erro>
            <erro>Erro 2</erro>
        </erros_impedindo_recebimento_xml>
        <avisos_nao_impedindo_recebimento_xml>
            <aviso>Aviso 1</aviso>
            <aviso>Aviso 2</aviso>
        </avisos_nao_impedindo_recebimento_xml>
    <arquivo>
    <arquivo nome="Arquivo 2">
       <erros_impedindo_recebimento_xml>
            <erro>Erro 1</erro>
            <erro>Erro 2</erro>
        </erros_impedindo_recebimento_xml>
        <avisos_nao_impedindo_recebimento_xml>
            <aviso>Aviso 1</aviso>
            <aviso>Aviso 2</aviso>
        </avisos_nao_impedindo_recebimento_xml>
    <arquivo>
  </arquivos>
</xml>
  
 */
	    debugar("upload_File(): nome arquivo = " + f.getName());
	  
	    ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        client.addFilter(new HTTPBasicAuthFilter(username, senha));
 
        WebResource resource = client.resource(url);
 
        FormDataMultiPart multiPart = new FormDataMultiPart();
        if (f != null) 
        {
            multiPart.bodyPart(new FileDataBodyPart("file", f,
                    MediaType.APPLICATION_OCTET_STREAM_TYPE));
        }
 
        ClientResponse clientResp = resource.type(MediaType.MULTIPART_FORM_DATA_TYPE)
        		                                  .post(ClientResponse.class, multiPart);
        
        debugar("upload_File(): HTTP Status Code = " + clientResp.getClientResponseStatus().getStatusCode() + " (" + clientResp.getClientResponseStatus().getReasonPhrase() + ")");

        // Neste caso JAXB seria mais trabalhoso do que SAX e DOM,
    	// ent�o n�o usar um Entity :
        String xmlResposta = clientResp.getEntity(String.class);
        debugar("upload_File(): HTTP Response = " + xmlResposta) ;
        
        if (    clientResp.getClientResponseStatus().getStatusCode() != 200 
             && clientResp.getClientResponseStatus().getStatusCode() != 202)
        {
            client.destroy();
            // N�o fazer throw new RuntimeException, por�m continuar com a pr�xima cota��o :
        	logarErro("Erro! upload_File(): HTTP Status Code = " + clientResp.getClientResponseStatus().getStatusCode() + " (" + clientResp.getClientResponseStatus().getReasonPhrase() + ")");
            logarErro("upload_File(): HTTP Response = " + xmlResposta) ;
        }
        else
        {
        }
        	
        client.destroy();
  }



 

  public static void uploadOfertas_BodyXML(String url, String nomeArquivo, String username, String senha) 
  {
    Client client = Client.create();
    WebResource webResource = client.resource(url);
    client.addFilter(new HTTPBasicAuthFilter(username, senha));


    // Copiado de : http://www.coderanch.com/t/551977/Web-Services/java/Jersey-RESTful-web-service-post

    String response = webResource.type(MediaType.APPLICATION_XML)
                                 .accept(MediaType.TEXT_PLAIN)
                                 .entity(new File(nomeArquivo))
                                 .post(String.class);
    debugar("response : " + response);

   }
  
  
   public static void excluirArquivos(LocalDateTime horaInicio)
   {
	   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy '�s' HH:mm");
	   
	   // Limpeza dump files do JRE:
	   File dir = new File("C:\\Arquivos de Programas PC\\Integra��o Fornecedor - Portal Cronos");
	   for (final File file : dir.listFiles()) 
	   {
		   if (file.getName().indexOf("hs_err_pid") == 0 || file.getName().indexOf("replay_pid") == 0) 
		   {
		      logarErro("Arquivo " + file.getName() + " encontrado, ent�o a mem�ria RAM estava cheia na execu��o anterior do servi�o de ofertas autom�ticas no dia " + sdf.format(file.lastModified()) + "." );
		      file.delete();
		   }
	   }
	   
	   
	   // Limpeza dos arquivos pr�prios deste servi�o (arquivos .log e .xml):
	   dir = new File(diretorioArquivosXmlSemBarraNoFinal); // "C:\\temp\\PortalCronos\\XML"
	// System.out.println("dir = " + dir.getAbsolutePath());
	   for (final File file : dir.listFiles()) 
	   {
		// System.out.println("file = " + file.getName());
		   LocalDateTime datahoraArquivo = LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), ZoneId.systemDefault()); 
		   
		   if (datahoraArquivo.isBefore(horaInicio.minusDays(qtdDiasArquivosXmlGuardados))) 
		   {
		      file.delete();
		   }
	   }
	   
	   
   }

   
   public static void main(String[] args)
   {    
	  LocalDateTime horaInicio = LocalDateTime.now();

	  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	  DateTimeFormatter formatterIntervalo = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

	  excluirArquivos(horaInicio);
	   
   // criarTabelasTeste();

	  if (siglaSistema.equals("PCronos")) {
		  if (erroStaticConstructor == null && toEnviarEmailAutomatico)
	      {
		     monitorarPendencias(horaInicio);
	      }  
	  }
	  else
		  downloadCotacoes(horaInicio, enderecoBaseWebService + "cotacao/ObtemCotacoesGET?cdFornecedor=" + cnpjFornecedor + "&dataInicio=", cnpjFornecedor, username, senha);


	  nf.setMinimumIntegerDigits(2);
  	  nf.setMaximumFractionDigits(0);
	  LocalDateTime horaFim = LocalDateTime.now();
	  long HorasExecucao = Duration.between(horaInicio, horaFim).toHours(); // inclui os dias em horas
	  long MinutosExecucao = Duration.between(horaInicio, horaFim).toMinutes() % 60;
	  long MinutosTotalExecucao = Duration.between(horaInicio, horaFim).toMinutes();
	  long SegundosExecucao = Duration.between(horaInicio, horaFim).getSeconds() % 60;
	  String tempoExecucao = nf.format(HorasExecucao) + ":" + nf.format(MinutosExecucao) + ":" + nf.format(SegundosExecucao);
	  

	  if (siglaSistema.equals("PCronos") && MinutosTotalExecucao > 12) {
		  String msgLimite13Min = "Erro! O monitoramento parou de vez pois o processamento passou o limite de 13 minutos e demorou " + nf.format(MinutosTotalExecucao) + " minutos. O monitoramento continuar� parado at� a exclus�o do arquivo de log de erro e ap�s a solu��o da causa. A �ltima op��o seria diminuir a frequ�ncia de execu��o de 15 minutos para 30 minutos.";
	      EmailAutomatico.enviar(remetenteEmailAutomatico, destinoEmailAutomatico, ccEmailAutomatico, "Monitoramento integra��o - Erro fatal! ", null, msgLimite13Min, provedorEmailAutomatico, portaEmailAutomatico, usuarioEmailAutomatico, senhaCriptografadaEmailAutomatico, diretorioArquivosXmlSemBarraNoFinal, horaInicio, diretorioArquivosXml, "Monitoramento");
	      // Logar o erro AP�S o envio de email, pois o sistema n�o envia nenhum email se existir qualquer arquivo de log de erro:
	      IntegracaoFornecedorCompleta.logarErro(msgLimite13Min);
	  }

	  
	  if (!siglaSistema.equals("PCronos") || (siglaSistema.equals("PCronos") && toDebugar)) {
		  try
		  {
		      BufferedWriter bWriter = new BufferedWriter(new FileWriter(diretorioArquivosXml + "TemposExecu��o.log", true));
		      String strHorarioComTempoExecucao = horaInicio.format(formatter) + " - Tempo de Execu��o: " + tempoExecucao; 
		      bWriter.append(strHorarioComTempoExecucao);
		      
		      if (siglaSistema.equals("PCronos") && erroStaticConstructor == null && toEnviarEmailAutomatico)
			  {
				  bWriter.append(" - Intervalo de Cota��es: de " + dtCadastroIni.format(formatterIntervalo) + " at� " + dtCadastroFim.format(formatterIntervalo));
			  }
		      bWriter.newLine();
		      bWriter.flush();
		      bWriter.close();
	
		      if (!siglaSistema.equals("PCronos"))
		          performPostCall(strHorarioComTempoExecucao, "TemposExecu��o");
		  }
	  	  catch (IOException ioe)
		  {
	  		  ioe.printStackTrace();
		  }
      } // if (!siglaSistema.equals("PCronos") || (siglaSistema.equals("PCronos") && toDebugar)) 
    }

}
