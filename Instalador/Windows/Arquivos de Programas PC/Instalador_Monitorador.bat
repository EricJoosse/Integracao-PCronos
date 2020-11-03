cls
@echo off

REM Windows, por default, usa o charset WINDOWS-1252 (ou outra varia��o, dependendo da lingua), 
REM que � uma extens�o do ISO-8859-1. 
REM Mesmo assim, n�o se compara com UTF-8! O Linux, por default, usa o charset UTF-8. 
REM Eclipse herda o charset de Windows por default. 
REM "chcp 1252>nul" � necess�rio para evitar que DOS n�o reconhece acentos Portugueses no caminho 
REM "Integra��o Fornecedor - Portal Cronos" em alguns ou talvez at� em todos os servidores:

chcp 1252>nul

cd\
cd "Arquivos de Programas PC"
del /f /q Instalador_Integrador.bat
call "Integra��o Fornecedor - Portal Cronos\bin\VerificarUsuarioAdministrador.bat"


:PerguntaTipoWin
echo.
echo.
echo Tipos de Windows homologados: 
echo.
echo 1 = Windows Server 2008 R2 SP1
echo 2 = Windows Server 2012 R2
echo 3 = Windows Server 2016 ou 2019
echo C = Cancelar instala��o (com rollback)
echo.

SET /P idOsVersion=Digite 1, 2, 3 ou C + ^<Enter^>: 
IF "%idOsVersion%"=="" GOTO ErroTipoWin
IF "%idOsVersion%"=="C" GOTO CancelarInstalacao
IF "%idOsVersion%"=="c" GOTO CancelarInstalacao
IF "%idOsVersion%"=="1" GOTO PularErroTipoWin
IF "%idOsVersion%"=="2" GOTO PularErroTipoWin
IF "%idOsVersion%"=="3" GOTO PularErroTipoWin
echo MSGBOX "Erro: Op��o inv�lida!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
cls
goto PerguntaTipoWin
:ErroTipoWin
echo MSGBOX "Erro: ID do tipo de Windows n�o informado! Instala��o abortada!!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
REM Fechar o script chamador tamb�m: 
exit
:PularErroTipoWin


REM Tipo de instala��o: 
REM 	1 = Hospedagem Local
REM 	2 = Nuvem

SET tipoInstalacao=1

REM set osVersion=Windows_Server_2016
REM set osVersion=Windows_Server_2012_R2
REM set osVersion=Windows_Server_2012
REM set osVersion=Windows_Server_2008_R2_SP1
REM set osVersion=Windows_Server_2008
REM set osVersion=Windows_Server_2003_R2
REM set osVersion=Windows_Server_2003
REM set osVersion=Windows_2000
REM set osVersion=Windows_NT_40
REM set osVersion=Windows_NT_351
REM set osVersion=Windows_NT_35
REM set osVersion=Windows_NT_31
REM set osVersion=Windows_7_Professional_SP1
REM set osVersion=Windows_8_Pro
REM set osVersion=Windows_10_Pro
  
  
if "%idOsVersion%"=="1" (
    set osVersion=Windows_Server_2008_R2_SP1
) else if "%idOsVersion%"=="2" (
    set osVersion=Windows_Server_2012_R2
) else if "%idOsVersion%"=="3" (
    set osVersion=Windows_Server_2016
) else (
    echo MSGBOX "Erro: ID do tipo de Windows inv�lido! Instala��o abortada!!" > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
    REM Fechar o script chamador tamb�m: 
    exit
)

:PerguntaToInstalarJRE
echo.
echo.
echo Deseja instalar o Java Runtime? 
echo.
echo S = Sim
echo N = N�o
echo C = Cancelar instala��o (com rollback)
echo.

SET /P toInstalarJRE=Digite S, N ou C + ^<Enter^>: 
IF "%toInstalarJRE%"=="" GOTO ErroToInstalarJRE
IF "%toInstalarJRE%"=="S" GOTO PularErroToInstalarJRE
IF "%toInstalarJRE%"=="s" GOTO PularErroToInstalarJRE
IF "%toInstalarJRE%"=="N" GOTO PularErroToInstalarJRE
IF "%toInstalarJRE%"=="n" GOTO PularErroToInstalarJRE
IF "%toInstalarJRE%"=="C" GOTO CancelarInstalacao
IF "%toInstalarJRE%"=="c" GOTO CancelarInstalacao
echo MSGBOX "Erro: Op��o inv�lida!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
cls
goto PerguntaToInstalarJRE
:ErroToInstalarJRE
echo MSGBOX "Erro: Op��o n�o informada!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
goto PerguntaToInstalarJRE
:PularErroToInstalarJRE


REM echo MSGBOX "Instalando..." > %temp%\TEMPmessage.vbs
REM call %temp%\TEMPmessage.vbs
REM del %temp%\TEMPmessage.vbs /f /q

echo.
echo          Instalando...................
echo.


if %osVersion% == Windows_Server_2016 (
    set /A tipoOS=64
) else if %osVersion% == Windows_Server_2012_R2 (
    set /A tipoOS=64
) else if %osVersion% == Windows_Server_2012 (
    set /A tipoOS=64
) else if %osVersion% == Windows_NT_40 (
    set /A tipoOS=32
) else if %osVersion% == Windows_NT_351 (
    set /A tipoOS=32
) else if %osVersion% == Windows_NT_35 (
    set /A tipoOS=32
) else if %osVersion% == Windows_NT_31 (
    set /A tipoOS=32
) else if %osVersion% == Windows_10_Pro (
REM set /A tipoOS=32
    set /A tipoOS=64
) else (
REM set /A tipoOS=32
    set /A tipoOS=64
)


set Windows_10_Pro_64bit=0
if %osVersion% == Windows_10_Pro (
   if %tipoOS% == 64 (
      set Windows_10_Pro_64bit=1
   )
)


    set drive=C:
REM set drive=D:



IF "%toInstalarJRE%"=="N" goto SKIP_JRE
IF "%toInstalarJRE%"=="n" goto SKIP_JRE
REM goto SKIP_JRE_TEMPDIR
REM goto SKIP_JRE_TEMPDIR_PROGRAMDIR
REM goto FIM

REM !!!!!!!!!!!!!!! CUIDADO PARA N�O CORROMPER O REGEDIT !!!!!!!!!
REM !!!!!!!!!!!!!!! CUIDADO PARA N�O CORROMPER O REGEDIT !!!!!!!!!
REM !!!!!!!!!!!!!!! CUIDADO PARA N�O CORROMPER O REGEDIT !!!!!!!!!
REM !!!!!!!!!!!!!!! CUIDADO PARA N�O CORROMPER O REGEDIT !!!!!!!!!
REM !!!!!!!!!!!!!!! CUIDADO PARA N�O CORROMPER O REGEDIT !!!!!!!!!
REM !!!!!!!!!!!!!!! CUIDADO PARA N�O CORROMPER O REGEDIT !!!!!!!!!
REM !!!!!!!!!!!!!!! CUIDADO PARA N�O CORROMPER O REGEDIT !!!!!!!!!
REM !!!!!!!!!!!!!!! CUIDADO PARA N�O CORROMPER O REGEDIT !!!!!!!!!




REM Falta testar o seguinte!!!!!!!!!!!!!!!!!!!!!!!!
REM Se � para usar %~dp0DeshabilitarJavaUpdates.x64.reg ou DeshabilitarJavaUpdates.x64.reg
REM no seguinte caso de if Windows_10_Pro_64bit == 1:
REM ) else if %Windows_10_Pro_64bit% == 1 (
REM     set arquivoRegedit=DeshabilitarJavaUpdates.x64.reg


cd\
cd "Arquivos de Programas PC"

if %osVersion% == Windows_Server_2008_R2_SP1 (
    set arquivoRegedit="%drive%\\Arquivos de Programas PC\\DeshabilitarJavaUpdates.x64.reg"
) else if %osVersion% == Windows_Server_2012_R2 (
    set arquivoRegedit="%~dp0DeshabilitarJavaUpdates.x64.reg"
) else if %osVersion% == Windows_Server_2016 (
    set arquivoRegedit="%~dp0DeshabilitarJavaUpdates.x64.reg"
) else if %Windows_10_Pro_64bit% == 1 (
    set arquivoRegedit=DeshabilitarJavaUpdates.x64.reg
) else if %osVersion% == Windows_7_Professional_SP1 (
    set arquivoRegedit=DeshabilitarJavaUpdates.i586.reg
) else (
    echo MSGBOX "Esta vers�o de Windows ainda n�o est� suportada! Favor entrar em contato com o Suporte do Portal Cronos." > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
    exit
)

REM goto FIM


REM ================ Instalar JRE + deshabilitar updates autom�ticos: ========================================

if %tipoOS% == 64 (
    Start "" /wait jre-8u92-windows-x64.exe /s INSTALLDIR=%drive%\\"Program Files"\Java\jre1.8.0_92
REM O seguinte n�o adiantou: 
REM echo Favor esperar 300 segundos, e N�O CONTINUAR! N�o aperta nenhuma tecla!
REM timeout 300
    regedit.exe /s %arquivoRegedit%
) else if %tipoOS% == 32 (
    Start "" /wait jre-8u92-windows-i586.exe /s INSTALLDIR=%drive%\\"Program Files"\Java\jre1.8.0_92
REM O seguinte n�o adiantou: 
REM echo Favor esperar 300 segundos, e N�O CONTINUAR! N�o aperta nenhuma tecla!
REM timeout 300
    regedit.exe /s %arquivoRegedit%
) else (
    echo Tipo OS %tipoOS% n�o reconhecido ^! Op��es: 32 ou 64 ^(Bits^)
)


:SKIP_JRE

REM ================ Instalar diret�rio de Log: ========================================

cd\
if not exist C:\ProgramData\ (
    echo MSGBOX "O diret�rio padr�o de Windows ProgramData n�o existe! Favor entrar em contato com o Suporte do Portal Cronos." > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
    exit
)
cd ProgramData
if not exist C:\ProgramData\PortalCronos\ mkdir PortalCronos
cd PortalCronos
if not exist C:\ProgramData\PortalCronos\Logs\ mkdir Logs
cd Logs
if not exist C:\ProgramData\PortalCronos\Logs\Local\ mkdir Local
REM N�o precisa dar privil�gios, pois a Scheduled Task roda como SYSTEM

:SKIP_JRE_TEMPDIR

REM ================ Instalar diret�rio Arquivos de Programas PC: ========================================

REM J� instalado pelo self-extractable file 
REM cd\
REM cd "Arquivos de Programas PC"

:SKIP_JRE_TEMPDIR_PROGRAMDIR

REM ================ Instalar as diversas op��es no menu de Windows, 
REM ================ e gravar IsAmbienteNuvem.bat,
REM ================ e gerar o arquivo XML da tarefa do Agendador de Windows,   
REM ================ DEPOIS da instala��o dos programas de Java: ========================================

cd\
cd "Arquivos de Programas PC"
if exist "Integra��o Portal Cronos - Fornecedor.Windows.2008_R2.TaskSchedule.xml" del /f /q "Integra��o Portal Cronos - Fornecedor.Windows.2008_R2.TaskSchedule.xml"
call Instalador.bat m PCronos

cd\


REM Prote��o contra exclus�o indevida por gerente TI ignorante que gosta de apagar coisas 
REM e que apaga tudo que n�o foi ele mesmo que tinha feito, sem avisar ningu�m,
REM apenas no servidor de monitoramento:

if %idFornecedor% == -1 (
    attrib "Arquivos de Programas PC" +S +H
)

cd "Arquivos de Programas PC"

REM Os seguintes arquivos n�o existem se optar por instala��o usando uma JRE j� existente.
REM Para evitar erro indevido "Arquivo n�o encontrado" usar if exist:
if exist *.reg del /f /q *.reg
if exist *.exe del /f /q *.exe

del /f /q Instalador.bat
del /f /q TestadorUnitarioInstalacaoDesinstalacao.bat
if exist UpgradeNaoNuvem3.1.0.bat del /f /q UpgradeNaoNuvem3.1.0.bat
if exist .gitignore del /f /q .gitignore

if %tipoInstalacao% == 1 (
    del /f /q AdicionarFornecedorNuvem.bat
    del /f /q RemoverFornecedorNuvem.bat
)

cd "Integra��o Fornecedor - Portal Cronos"
del /f /q TestadorUnitario.log
del /f /q TesteTresParam.log
del /f /q .gitignore

if %idFornecedor% NEQ -1 (
    rmdir /s /q tpl
)

cd conf

if %tipoInstalacao% == 1 (
    del /f /q TemplateNuvemAPS.properties
) else if %tipoInstalacao% == 2 (
    del /f /q "Integra��o Fornecedor - Portal Cronos.properties"
)



cd\
cd "Arquivos de Programas PC"

REM Foi testado via teste integrado completo que o seguinte funciona, mesmo que deixar o arquivo selecionado 
REM ap�s o duplo-clique para executar a instala��o:
REM Foi testado que o seguinte funciona tamb�m no caso que o diret�rio for C:\Temp\ ao inv�s de C:\temp\:
del /f /q C:\temp\"Instalador do Monitorador.*.exe"



REM ================ Instalar Task N�o-Nuvem no Windows Scheduler: ========================================

cd\
cd "Arquivos de Programas PC"

REM O seguinte n�o marca a op��o "Run whether user is logged on or not" :
REM SCHTASKS /Create /TN "Teste Automa��o SCHTASKS" /TR "C:\Arquivos de Programas PC\Integra��o Fornecedor - Portal Cronos\Job15a15min.bat" /SC MINUTE /MO 15

REM O seguinte n�o cria automaticamente todas as configura��es desejadas : 
REM SCHTASKS /Create /TN "Integra��o Portal Cronos - Fornecedor" /TR "C:\Arquivos de Programas PC\Integra��o Fornecedor - Portal Cronos\Job15a15min.bat" /SC MINUTE /MO 15 /RU SYSTEM /NP /RL HIGHEST

REM O seguinte XML tem as configura��es completas e foi criado criando a task manualmente,
REM e em seguida exportada para XML : 

if %osVersion% == Windows_Server_2008_R2_SP1 (
REM Testado: 
    SCHTASKS /Create /TN "Integra��o Portal Cronos - Fornecedor" /XML "C:/Arquivos de Programas PC/Integra��o Portal Cronos - Fornecedor.Windows.2008_R2.TaskSchedule.xml"
) else if %Windows_10_Pro_64bit% == 1 (
REM Testado: 
    SCHTASKS /Create /TN "Integra��o Portal Cronos - Fornecedor" /XML "C:/Arquivos de Programas PC/Integra��o Portal Cronos - Fornecedor.Windows.2008_R2.TaskSchedule.xml"
) else if %osVersion% == Windows_Server_2012_R2 (
REM Testado: 
    SCHTASKS /Create /TN "Integra��o Portal Cronos - Fornecedor" /XML "C:/Arquivos de Programas PC/Integra��o Portal Cronos - Fornecedor.Windows.2008_R2.TaskSchedule.xml"
) else if %osVersion% == Windows_Server_2016 (
REM Testado: 
    SCHTASKS /Create /TN "Integra��o Portal Cronos - Fornecedor" /XML "C:/Arquivos de Programas PC/Integra��o Portal Cronos - Fornecedor.Windows.2008_R2.TaskSchedule.xml"
) else (
    SCHTASKS /Create /TN "Integra��o Portal Cronos - Fornecedor" /XML "C:/Arquivos de Programas PC/Integra��o Portal Cronos - Fornecedor.Windows.2008_R2.TaskSchedule.xml"
)

SCHTASKS /Run /TN "Integra��o Portal Cronos - Fornecedor"

:SKIP_TASK_WINDOWS_SCHEDULER

REM ================ Fim instala��o Task N�o-Nuvem no Windows Scheduler. ========================================

cd\
cd "Arquivos de Programas PC"

REM Apagar o arquivo "Integra��o Portal Cronos - Fornecedor.Windows.2008_R2.TaskSchedule.xml" e outros arquivos parecidos: 
del /f /q *.xml


echo.
echo          Primeira fase da instala��o concluida!
echo.

echo Pr�ximos passos:
echo ================
echo 1. Preencher o arquivo de configura��o, j� aberto automaticamente em segundo 
echo    plano.
echo.
echo 2. AP�S a conclus�o do preenchimento deste arquivo, 
echo    em todos os casos de todos os servidores da Cronos: 
echo    (servidor de aplica��o, servidor BD, servidor BD Conting�ncia):
echo     - No Testador Unit�rio TestadorSnippets.java descomentar 
echo       testarProvedorEmail() e comentar qualquer outra Test Unit. 
echo     - Re-gerar o .jar e copiar para o servidor e executar TestadorUnitario.bat
echo       no servidor de destino. 
echo     - Se o email de teste n�o chegar, adicionar o servidor nos "recognized
echo       devices" (dispositivos reconhecidos) na conta portalcronos.br@gmail.com
echo       do Gmail via acesso no Gmail via browser. 
echo     - Apagar TestadorUnitario.log no servidor.
echo.


echo MSGBOX "Primeira fase da instala��o concluida! Leia os pr�ximos passos na tela MS-DOS!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q

cd\
cd "Arquivos de Programas PC"
start notepad "Integra��o Fornecedor - Portal Cronos/conf/Integra��o Fornecedor - Portal Cronos.properties"

REM Provisoriamente uma pausa para a leitura dos pr�ximos passos da instala��o.
REM Tem que ser ANTES da exclus�o deste Instalador_Monitorador.bat para a pausa poder funcionar:
pause

REM Excluir este pr�prio arquivo apenas no final, 
REM pois foi testado que n�o vai excluir os arquivos que viriam depois disso:
cd\
cd "Arquivos de Programas PC"
del /f /q Instalador_Monitorador.bat

exit

:FIM


goto PularCancelarInstalacao
:CancelarInstalacao

REM Foi testado que o seguinte funciona tamb�m no caso que o diret�rio for C:\Temp\ ao inv�s de C:\temp\:
del /f /q C:\temp\"Instalador do Monitorador.*.exe"

REM N�o fazer cls aqui, para poder visualizar eventuais erros
echo.
echo          Rollback da instala��o concluida!
echo.

echo MSGBOX "Rollback da instala��o concluida!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q

REM ================ Remover diret�rio "Arquivos de Programas PC": ========================================

REM O seguinte consegue remover todos os arquivos no diret�rio "Arquivos de Programas PC",
REM at� este arquivo .bat, por�m n�o consegue remover o diret�rio "Arquivos de Programas PC" :
REM ????????? Funcionou quando usei vbs acima antes disso !!!!!!!!!
cd\
rmdir /s /q "Arquivos de Programas PC"
:PularCancelarInstalacao

