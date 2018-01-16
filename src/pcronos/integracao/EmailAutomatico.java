package pcronos.integracao;
//
////--------------------------------------------------------------------------
///** 
//*
//* <p>
//* 
//* <b> Autor </b>              : Eric Joosse - MV Sistemas
//* <p>
//* 
//* <b> Data Cria��o </b>       : 15.08.2005
//* <p>
//* 
//* <b> �ltima atualiza��o< /b> : 10.02.2009 
//* <p>
//* 
//* <b> Por </b>                : Eric Joosse - Apply Solutions / GIJS : 
//* <p>
//* 
//* <b> Altera��o </b>          : Inclus�o de anexos no email, e destinos de tipo CC 
//* <p>
//* 
//* <b> Objetivo </b>           : Enviar Email autom�tico com anexos e com c�pias CC e BCC
//*                               pois a package de PL/SQL SMTP_MAIL da Oracle n�o permite nem anexos
//*                               nem c�pias CC
//* <p>
//*
//* <b> Observa��es </b> : 
//* <p>
//*
//*/
////--------------------------------------------------------------------------
//
import javax.mail.internet.* ;

import pcronos.integracao.fornecedor.IntegracaoFornecedorCompleta;

import javax.mail.*          ; 
import javax.activation.*    ;
//
public abstract class EmailAutomatico { 
//
//private   static   boolean             bDebug  = true                ; 
//private   static   java.sql.Statement  Stat                          ;
//private   static   java.sql.ResultSet  Rset                          ;
//private   static   java.lang.String    erroStaticConstructor         ; 
//private   static   java.lang.String    StrEmailGinfoFabricaCoord     ; 
//private   static   java.lang.String    StrEmailPrefeitura            ; 
//private   static   java.lang.String    StrEmailPrefeituraCC          ; 
//private   static   java.lang.String    StrEnderecoIPServidorEmail    ; 
//
//
//static { 
//  IntegracaoFornecedorCompleta.debugar( "Static constructor da classe EmailAutomatico entrado" ) ; 
//  IntegracaoFornecedorCompleta.debugar( "java.lang.System.getProperty(\"java.vm.version\") = " 
//         + java.lang.System.getProperty("java.vm.version") ) ; 
//  EmailAutomatico.getConfiguracoes() ; 
//}
//
//
//private static void IntegracaoFornecedorCompleta.debugar( java.lang.String     pTextoDepuracao ) {
//    if ( bDebug ) {
//      // java.lang.System.err.println( pTextoDepuracao + "\n" ) ;
//         java.lang.System.out.println( pTextoDepuracao ) ; 
//    }
//}
//
//
//private static void getConfiguracoes() {
//  // Este m�todo facilita o static constructor pois dentro de static constructors 
//  // n�o � poss�vel usar "return" nem � poss�vel "throw new Exception",
//  // e dentro de m�todos pode usar "return" nos lugares onde precisa abortar o static constructor. 
//
//  java.lang.String    sqlString  = null   ;
//
//  try { 
//
//    Stat = SIV.getConnection().createStatement() ;
//    sqlString = "Select Email_GinfoFabrica_Coord   "
//              + "     , Email_Prefeitura           "
//              + "     , Email_Prefeitura_CC        "
//              + "     , Endereco_IP_Servidor_Email "
//              + "  From  Config_SIV"               ;
//    Rset = Stat.executeQuery( sqlString ) ;
//
//    if (Rset.next()) {
//      StrEmailGinfoFabricaCoord  = Rset.getString(1) ; 
//      StrEmailPrefeitura         = Rset.getString(2) ; 
//      StrEmailPrefeituraCC       = Rset.getString(3) ; 
//      StrEnderecoIPServidorEmail = Rset.getString(4) ; 
//    } else {
//       erroStaticConstructor = "M�todo EmailAutomatico.getConfiguracoes : " 
//                   + "Erro NO_DATA_FOUND no seguinte Select : " + sqlString ; 
//       return ; 
//    }
//
//    IntegracaoFornecedorCompleta.debugar( "M�todo EmailAutomatico.getConfiguracoes : StrEmailGinfoFabricaCoord  = " + StrEmailGinfoFabricaCoord  ) ; 
//    IntegracaoFornecedorCompleta.debugar( "M�todo EmailAutomatico.getConfiguracoes : StrEmailPrefeitura         = " + StrEmailPrefeitura         ) ; 
//    IntegracaoFornecedorCompleta.debugar( "M�todo EmailAutomatico.getConfiguracoes : StrEmailPrefeituraCC       = " + StrEmailPrefeituraCC       ) ; 
//    IntegracaoFornecedorCompleta.debugar( "M�todo EmailAutomatico.getConfiguracoes : StrEnderecoIPServidorEmail = " + StrEnderecoIPServidorEmail ) ; 
//
//    if ( StrEmailGinfoFabricaCoord == null ) { 
//      erroStaticConstructor = "Erro na parametriza��o do endere�o de Email do coordenador de inform�tica na f�brica\n"
//                              + " : StrEmailGinfoFabricaCoord == null" ; 
//      return ; 
//    }
//
//    if ( StrEmailPrefeitura == null ) { 
//      erroStaticConstructor = "Erro na parametriza��o do endere�o de Email da prefeitura\n"
//                              + " : StrEmailPrefeitura == null" ; 
//      return ; 
//    }
//
//    if ( StrEnderecoIPServidorEmail == null ) { 
//      erroStaticConstructor = "Erro na parametriza��o do endere�o IP do Servidor de Email da f�brica\n"
//                              + " : StrEnderecoIPServidorEmail == null" ; 
//      return ; 
//    }
//
//  }
//  catch (java.sql.SQLException e) {
//    IntegracaoFornecedorCompleta.debugar( "M�todo EmailAutomatico.getConfiguracoes : catch SQLException entrado." ) ; 
//    erroStaticConstructor = 
//         "Erro na consulta dos endere�os de email da prefeitura, e outros endere�os \n"
//         + "na tabela CONFIG_SIV : JDBC-" 
//         + e.getErrorCode() + ": " + e.toString() + ". SQLState : " + e.getSQLState() ; 
//  }
//  catch (java.lang.Throwable t) { 
//    IntegracaoFornecedorCompleta.debugar( "M�todo EmailAutomatico.getConfiguracoes : catch Throwable entrado." ) ; 
//    erroStaticConstructor = 
//         "Erro na consulta dos endere�os de email da prefeitura, e outros endere�os \n"
//         + "na tabela CONFIG_SIV :\n" 
//                          + "M�todo EmailAutomatico.getConfiguracoes : "
//                          + t.toString() ;  
//     // Oracle 9i (n�o tem o Throwable.getCause de Java 1.4) : 
//     //                   + t.toString() ;  
//     // Oracle 10g, 11g, etc : 
//     //                   + t.toString() + ". \n Causa : " + t.getCause() ;  
//  }
//  finally { 
//    if (sqlString != null) sqlString = null ;
//    if (Stat != null) {
//       try {
//         Stat.close() ;  // Isso fecha o Rset automaticamente tamb�m
//       }
//       catch (java.sql.SQLException e) {
//         // N�o fazer nada.
//       }
//       Stat = null  ;
//    }
//    if (Rset != null) Rset = null ;
//  }
//} 
//
//
//public static void enviarEmailPrefeitura( java.lang.String p_Assunto
//                                        , java.lang.String p_Anexo
//                                        , java.lang.String p_Mensagem 
//                                        ) throws ErroTabelaLogException {
//
//  IntegracaoFornecedorCompleta.debugar( "M�todo EmailAutomatico.enviar com 3 par�metros entrado" ) ; 
//  IntegracaoFornecedorCompleta.debugar( "EmailAutomatico.erroStaticConstructor = " + EmailAutomatico.erroStaticConstructor ) ; 
//
//  if ( EmailAutomatico.erroStaticConstructor != null ) { 
//    IntegracaoFornecedorCompleta.debugar( "Erro no static constructor da classe EmailAutomatico : \n"
//           + EmailAutomatico.erroStaticConstructor  ) ; 
//    IntegracaoFornecedorCompleta.logarErro( "Erro no envio autom�tico de email para a prefeitura ! \n"
//                 + "Informar o seguinte erro � GINFO-Esc.Recife (Eric Joosse) :\n"
//                 +  EmailAutomatico.erroStaticConstructor ) ;
//  } else 
//      EmailAutomatico.enviar( StrEmailGinfoFabricaCoord 
//                            , StrEmailPrefeitura
//                            , StrEmailPrefeituraCC
//                            , p_Assunto
//                            , p_Anexo
//                            , p_Mensagem 
//                            , StrEnderecoIPServidorEmail
//                            ) ;
//}
//
//
//
//
//public static void enviar( java.lang.String p_Para
//                         , java.lang.String p_CC
//                         , java.lang.String p_Assunto
//                         , java.lang.String p_Anexo
//                         , java.lang.String p_Mensagem 
//                         ) throws ErroTabelaLogException {
//
//  IntegracaoFornecedorCompleta.debugar( "M�todo EmailAutomatico.enviar com 5 par�metros entrado" ) ; 
//  IntegracaoFornecedorCompleta.debugar( "EmailAutomatico.erroStaticConstructor = " + EmailAutomatico.erroStaticConstructor ) ; 
//
//  if ( EmailAutomatico.erroStaticConstructor != null ) { 
//    IntegracaoFornecedorCompleta.debugar( "Erro no static constructor da classe EmailAutomatico : \n"
//           + EmailAutomatico.erroStaticConstructor  ) ; 
//    IntegracaoFornecedorCompleta.logarErro( "Erro no envio autom�tico de email para a prefeitura ! \n"
//                 + "Informar o seguinte erro � GINFO-Esc.Recife (Eric Joosse) :\n"
//                 +  EmailAutomatico.erroStaticConstructor ) ;
//  } else 
//    EmailAutomatico.enviar( StrEmailGinfoFabricaCoord 
//                          , p_Para
//                          , p_CC
//                          , p_Assunto
//                          , p_Anexo
//                          , p_Mensagem 
//                          , StrEnderecoIPServidorEmail
//                          ) ;
//}
//
//
//
public static void enviar( java.lang.String p_De 
                          , java.lang.String p_Para
                          , java.lang.String p_CC
                          , java.lang.String p_Assunto
                          , java.lang.String p_Anexo
                          , java.lang.String p_Mensagem 
                          , java.lang.String p_Endereco_IP_Servidor_Email
                          , java.lang.String PortaEmailAutomatico
                          , java.lang.String UsuarioEmailAutomatico
                          , java.lang.String SenhaCriptografadaEmailAutomatico
                          ) {
javax.mail.internet.MimeMessage       mmsg                     ; 
javax.mail.internet.MimeBodyPart      mbp1, mbp2               ;
javax.mail.internet.MimeMultipart     mmp                      ;
javax.mail.internet.InternetAddress[] EnderecoPara, EnderecoCC ;
javax.activation.FileDataSource       fds                      ;
javax.mail.Session                    session                  ;

//SIV.setBooleanDebug( EmailAutomatico.bDebug ) ; 

IntegracaoFornecedorCompleta.debugar( "M�todo EmailAutomatico.enviar com 7 par�metros entrado" ) ; 

if ( ( p_Assunto == null ) && ( p_Anexo == null ) && ( p_Mensagem == null ) ) {
  IntegracaoFornecedorCompleta.logarErro( "Erro ! Informar o seguinte erro ao GINFO-Recife (Eric Joosse) \n"
               + "Email autom�tico encontrado sem assunto, sem anexo, sem mensagem...\n" 
               + "Este email n�o foi enviado para " + p_Para + " por isso !" ) ; 
  return ;
}

java.util.Properties props = java.lang.System.getProperties()       ;
props.setProperty( "mail.smtp.host", p_Endereco_IP_Servidor_Email ) ;
props.put("mail.smtp.auth", "true");
//props.put("mail.smtp.starttls.enable", "true");
props.setProperty("mail.smtp.port", PortaEmailAutomatico);

//session = Session.getInstance( props, null ) ;  // Sess�o default
session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(UsuarioEmailAutomatico, SenhaCriptografadaEmailAutomatico);
			}
		  });

try 
{
    mmsg = new MimeMessage( session ) ;
    mmsg.setFrom( new javax.mail.internet.InternetAddress( p_De ) ) ;

    EnderecoPara = new InternetAddress[1] ;
    EnderecoPara[0] = new InternetAddress( p_Para ) ;
    mmsg.setRecipients( MimeMessage.RecipientType.TO, EnderecoPara ) ;

    IntegracaoFornecedorCompleta.debugar( "M�todo EmailAutomatico.enviar : p_CC       = " + p_CC       ) ; 
    IntegracaoFornecedorCompleta.debugar( "M�todo EmailAutomatico.enviar : p_Assunto  = " + p_Assunto  ) ; 
    IntegracaoFornecedorCompleta.debugar( "M�todo EmailAutomatico.enviar : p_Mensagem = " + p_Mensagem ) ; 
    IntegracaoFornecedorCompleta.debugar( "M�todo EmailAutomatico.enviar : p_Anexo    = " + p_Anexo    ) ; 

    if ( p_CC != null ) { 
      EnderecoCC = new InternetAddress[1] ;
      EnderecoCC[0] = new InternetAddress( p_CC ) ;
      mmsg.setRecipients( MimeMessage.RecipientType.CC, EnderecoCC ) ;
    }

    if ( p_Assunto != null ) mmsg.setSubject( p_Assunto ) ; 

    mbp1 = new MimeBodyPart()  ;
    if ( p_Mensagem == null ) p_Mensagem = "" ;
    mbp1.setText( p_Mensagem 
                + "\n\n\n\n\n\n\n\n\n\n\n\nEste email foi enviado automaticamente pelo sistema Portal Cronos\n" ) ;

    mbp2 = new MimeBodyPart() ;

    if ( p_Anexo != null ) { 
      fds = new FileDataSource( p_Anexo ) ;
      mbp2.setDataHandler( new javax.activation.DataHandler(fds) ) ;
      mbp2.setFileName( fds.getName() ) ;
    }

    mmp = new MimeMultipart() ;
    mmp.addBodyPart( mbp1 )   ;
    if ( p_Anexo != null )  mmp.addBodyPart( mbp2 ) ;

    mmsg.setContent( mmp ) ;

    mmsg.setSentDate(new java.util.Date() ) ;
    
    javax.mail.Transport.send( mmsg ) ;    
} 
catch ( javax.mail.internet.AddressException ae ) { 
  IntegracaoFornecedorCompleta.debugar( "M�todo EmailAutomatico.enviar : catch AddressException entrado." ) ; 
  if ( ae.toString().indexOf( "Illegal semicolon" ) != -1 ) 
    IntegracaoFornecedorCompleta.logarErro( "Erro ! N�o pode ter mais de um endere�o nos campos \"Para\" e \"Cc\" \n"
                 + "e n�o pode ter ponto-v�rgulas nos endere�os ! \n"
                 + "O email autom�tico n�o foi enviado para nenhum destes endere�os ! \n\n" 
                 + "Se precisar de mais de 2 endere�os, solicitar Eric Joosse \n"
                 + "providenciar uma vers�o nova do email autom�tico no SIV." ) ;
  else 
    IntegracaoFornecedorCompleta.logarErro( "Erro imprevisto no m�todo EmailAutomatico.enviar : AddressException : "
                 + ae.toString() ) ;
}
catch ( javax.mail.SendFailedException sfe ) { 
  IntegracaoFornecedorCompleta.debugar( "M�todo EmailAutomatico.enviar : catch SendFailedException entrado." ) ; 
  if ((sfe.getInvalidAddresses() != null) && (sfe.getInvalidAddresses().length > 0)) 
      IntegracaoFornecedorCompleta.logarErro( "Erro ! Endere�o " + p_Para
                   + " n�o existe, ent�o o email autom�tico n�o foi enviado para este endere�o !" ) ;
  else { 
    if ( sfe.toString().indexOf( "java.io.FileNotFoundException" ) != -1 ) 
      IntegracaoFornecedorCompleta.logarErro( "Erro ! Arquivo " + p_Anexo 
                   + " n�o encontrado, ent�o o email autom�tico n�o foi enviado para " 
                   + p_Para + " !" ) ;
    else {
      if ( sfe.toString().indexOf( "java.net.UnknownHostException" ) != -1 ) 
        IntegracaoFornecedorCompleta.logarErro( "Erro ! Servidor de Email " + p_Endereco_IP_Servidor_Email 
                     + " n�o encontrado, ent�o o email autom�tico n�o foi enviado para " 
                     + p_Para + " !" ) ;
      else
        IntegracaoFornecedorCompleta.logarErro( "Erro imprevisto no m�todo EmailAutomatico.enviar : SendFailedException : "
                     + sfe.toString() ) ;
    }
  }  
}
catch ( javax.mail.MessagingException me ) {
  IntegracaoFornecedorCompleta.debugar( "M�todo EmailAutomatico.enviar : catch MessagingException entrado." ) ; 
  IntegracaoFornecedorCompleta.logarErro( "Erro imprevisto no m�todo EmailAutomatico.enviar : MessagingException : "
               + me.toString() ) ;
  if ( IntegracaoFornecedorCompleta.toDebugar ) {
    me.printStackTrace() ;
    java.lang.Exception ex = null ;
    if ((ex = me.getNextException()) != null) { 
      IntegracaoFornecedorCompleta.debugar( "M�todo EmailAutomatico.enviar : me.getNextException() entrado." ) ; 
      ex.printStackTrace() ;
    }
  }
}
catch ( java.lang.Throwable  t ) { 
  IntegracaoFornecedorCompleta.debugar( "M�todo EmailAutomatico.enviar : catch Throwable entrado." ) ; 
  IntegracaoFornecedorCompleta.logarErro( "Erro imprevisto no m�todo EmailAutomatico.enviar : Throwable : "
               + t.toString() ) ;
  if ( IntegracaoFornecedorCompleta.toDebugar ) t.printStackTrace() ; 
}
}

}
