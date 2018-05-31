cls
@echo off

chcp 1252>nul

echo Tipos de Windows homologados: 
echo.
echo 1 = Windows Server 2008 R2 SP1
echo 2 = Windows Server 2012 R2
echo.

SET /P idOsVersion=Favor digitar o ID do tipo de Windows: 
IF "%idOsVersion%"=="" GOTO ErroTipoWin
GOTO PularErroTipoWin
:ErroTipoWin
echo MSGBOX "Erro: ID do tipo de Windows n�o informado! Instala��o abortada!!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
REM Fechar o script chamador tamb�m: 
exit
:PularErroTipoWin


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
) else (
    echo MSGBOX "Erro: ID do tipo de Windows inv�lido! Instala��o abortada!!" > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
    REM Fechar o script chamador tamb�m: 
    exit
)

  
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



REM goto SKIP_JRE
REM goto SKIP_JRE_TEMPDIR
REM goto SKIP_JRE_TEMPDIR_PROGRAMDIR
REM goto SKIP_JRE_TEMPDIR_PROGRAMDIR_TASK
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


if %osVersion% == Windows_Server_2008_R2_SP1 (
    set arquivoRegedit="%drive%\\Arquivos de Programas PC\\DeshabilitarJavaUpdates.x64.reg"
) else if %osVersion% == Windows_Server_2012_R2 (
    set arquivoRegedit=%~dp0DeshabilitarJavaUpdates.x64.reg
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


REM ================ Instalar JRE: ========================================

if %tipoOS% == 64 (
    Start /wait jre-8u92-windows-x64.exe /s INSTALLDIR=%drive%\\"Program Files"\Java\jre1.8.0_92
    regedit.exe /s %arquivoRegedit%
) else if %tipoOS% == 32 (
    Start /wait jre-8u92-windows-i586.exe /s INSTALLDIR=%drive%\\"Program Files"\Java\jre1.8.0_92
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

REM ================ Instalar Task no Windows Scheduler: ========================================

cd\
cd "Arquivos de Programas PC"

REM O seguinte n�o marca a op��o "Run whether user is logged on or not" :
REM SCHTASKS /Create /TN "Teste Automa��o SCHTASKS" /TR "C:\Arquivos de Programas PC\Integra��o Fornecedor - Portal Cronos\Job15a15minOfertamentoJava_Windows.bat" /SC MINUTE /MO 15

REM O seguinte n�o cria automaticamente todas as configura��es desejadas : 
REM SCHTASKS /Create /TN "Integra��o Portal Cronos - Fornecedor" /TR "C:\Arquivos de Programas PC\Integra��o Fornecedor - Portal Cronos\Job15a15minOfertamentoJava_Windows.bat" /SC MINUTE /MO 15 /RU SYSTEM /NP /RL HIGHEST

REM O seguinte XML tem as configura��es completas e foi criado criando a task manualmente,
REM e em seguida exportada para XML : 
if %osVersion% == Windows_Server_2008_R2_SP1 (
REM Testado: 
    SCHTASKS /Create /TN "Integra��o Portal Cronos - Fornecedor" /XML "C:/Arquivos de Programas PC/Integra��o Portal Cronos - Fornecedor.Windows.2008_R2.TaskSchedule.xml"
) else if %Windows_10_Pro_64bit% == 1 (
REM Testado: 
    SCHTASKS /Create /TN "Integra��o Portal Cronos - Fornecedor" /XML "C:/Arquivos de Programas PC/Integra��o Portal Cronos - Fornecedor.Windows.2008_R2.TaskSchedule.xml"
) else (
    SCHTASKS /Create /TN "Integra��o Portal Cronos - Fornecedor" /XML "C:/Arquivos de Programas PC/Integra��o Portal Cronos - Fornecedor.Windows.2008_R2.TaskSchedule.xml"
)

SCHTASKS /Run /TN "Integra��o Portal Cronos - Fornecedor"

:SKIP_JRE_TEMPDIR_PROGRAMDIR_TASK

REM ================ Instalar Manual Manuten��o TI do menu de Windows, DEPOIS da instala��o dos programas de Java: ========================================

cd\
cd "Arquivos de Programas PC"

call InstalarManualTI.bat

cd\
cd "Arquivos de Programas PC"

del /f /q *.reg
del /f /q *.xml
del /f /q *.exe
del /f /q InstalarManualTI.bat
del /f /q TestadorUnitarioInstalacaoDesinstalacao.bat

REM Foi testado via teste integrado completo que o seguinte funciona, mesmo que deixar o arquivo selecionado 
REM ap�s o duplo-clique para executar a instala��o:
del /f /q C:\temp\"Instalador do Integrador Fornecedores - Portal Cronos.*.exe"

REM Excluir este pr�prio arquivo apenas no final, 
REM pois foi testado que n�o vai excluir os arquivos que viriam depois disso:
del /f /q Primeira_Instalacao_Versao_Windows.bat

exit

:FIM

