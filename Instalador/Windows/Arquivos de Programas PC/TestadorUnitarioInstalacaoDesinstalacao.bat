cls
@echo off

REM "chcp 1252>nul" � necess�rio para evitar que DOS n�o reconhece acentos Portugueses no caminho 
REM "Integra��o Fornecedor - Portal Cronos" em alguns ou talvez at� em todos os servidores:

chcp 1252>nul

REM goto InstalarManualTI
REM goto DesinstalarManualTI
REM goto InstalarDirLog
REM goto DesinstalarDirLog
REM goto TesteIfNotExist
REM goto TesteDelProprioArq
REM goto TesteLimpeza
REM goto TesteDelInstalador
REM goto TesteRegedit
REM goto TesteSubDirBin
REM goto TesteIfExist
REM goto TesteAttrib
REM goto TesteVersao
REM goto TesteTresParam
goto TesteTemplate


REM ================ Testes Instala��o Manual Manuten��o TI do menu de Windows: ========================================
REM ================  (testado com Windows Server 2008 R2 SP1, funcionou)       ========================================
REM ================  (testado com Windows Server 2012 R2,     funcionou)       ========================================
REM ================  (testado com Windows Server 2016,        funcionou)       ========================================
REM ================  (testado com Windows Server 2016,        funcionou)       ========================================
REM ================  (testado com Windows 10 Pro - apenas testado no caso de um processador e SO 64 bit, funcionou)
:InstalarManualTI
call InstalarManualTI.bat
exit

REM ================ Testes Desinstala��o Manual Manuten��o TI do menu de Windows: ========================================
REM ================  (testado com Windows Server 2008 R2 SP1, funcionou)       ========================================
REM ================  (testado com Windows Server 2012 R2,     funcionou)       ========================================
REM ================  (testado com Windows Server 2016,        funcionou)       ========================================
REM ================  (testado com Windows 10 Pro - apenas testado no caso de um processador e SO 64 bit, funcionou)

:DesinstalarManualTI
call "Integra��o Fornecedor - Portal Cronos\bin\DesinstalarManualTI.bat"

echo Passou DesinstalarManualTI.bat
echo Testador.bat: ERRORLEVEL = %ERRORLEVEL%
pause

IF %ERRORLEVEL% NEQ 0 (
    goto PULAR_MENSAGEM_SUCESSO
)
echo MSGBOX "Erro: este comando n�o foi pulado indevidamente!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q


:PULAR_MENSAGEM_SUCESSO
echo MSGBOX "Sucesso: o comando acima foi pulado devidamente!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q

pause
exit

REM ================ Testes instala��o diret�rio de Log (testado com Windows Server 2008 R2 SP1, funcionou): ========================================
REM ================                                    (testado com Windows Server 2012 R2,     funcionou)  ========================================
REM ================                                    (testado com Windows Server 2016,        funcionou)  ========================================
REM ================                                     Obs.: foi testado tamb�m no caso que o dir =================================================
REM ================                                     C:\ProgramData\PortalCronos\Logs\Remoto\ j� existia antes ==================================

:InstalarDirLog
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

pause
exit

REM ================ Testes Desinstala��o diret�rio de Log (testado com Windows Server 2008 R2 SP1, funcionou): ========================================
REM ================                                       (testado com Windows Server 2012 R2,     funcionou)  ========================================
REM ================                                       (testado com Windows Server 2016,        funcionou)  ========================================
REM ================                                        Obs.: foi testado tamb�m no caso que o dir =================================================
REM ================                                        C:\ProgramData\PortalCronos\Logs\Remoto\ tamb�m existe =====================================

:DesinstalarDirLog
REM Evitando interfer�ncia indevida com outros diret�rios:
REM    C:\ProgramData\PortalCronos\Logs\Local
REM    C:\ProgramData\PortalCronos\Logs\Remoto\Integracao\
REM    C:\ProgramData\PortalCronos\Logs\Remoto\APK\

cd\
cd ProgramData

REM Foi testado que "rmdir /s /q PortalCronos" n�o funciona como deveria. 
REM Talvez isso � porque existem diversos n�veis de subsubsubdiret�rios:
 
cd PortalCronos
cd Logs
rmdir /s /q Local

if NOT exist "Remoto" (
  cd\
  cd ProgramData
  cd PortalCronos
  rmdir /s /q Logs
  cd\
  cd ProgramData
  rmdir /s /q PortalCronos
)
cd\

pause
exit

REM ================ Testes if NOT exist subdir (testado, funcionou): ========================================

:TesteIfNotExist
cd\
cd ProgramData
cd PortalCronos
cd Logs

if NOT exist "Remoto" (
    echo MSGBOX "O subdiret�rio Remoto n�o existe." > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
)

if exist "Remoto" (
    echo MSGBOX "O subdiret�rio Remoto existe sim." > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
)

pause
exit


REM ================ Teste de exclus�o deste arquivo mesmo (testado, funcionou): ========================================

:TesteDelProprioArq
del /f /q TestadorUnitarioInstalacaoDesinstalacao.bat

pause
exit


REM ================ Teste Limpeza com wildcards (testado, funcionou): ========================================

:TesteLimpeza
del /f /q *.reg
del /f /q *.xml
del /f /q *.exe
del /f /q InstalarManualTI.bat
del /f /q Primeira_Instalacao_Versao_Windows.bat
REM Excluir este pr�prio arquivo apenas no final, 
REM pois foi testado que n�o vai excluir os arquivos que viriam depois disso:
del /f /q TestadorUnitarioInstalacaoDesinstalacao.bat

pause
exit


REM ================ Teste Exclus�o Instalador (testado, funcionou): ========================================

:TesteDelInstalador
del /f /q C:\temp\"Instalador do Integrador Fornecedores - Portal Cronos.*.exe"

pause
exit


REM ================ Teste instala��o chaves no regedit (testado com Windows Server 2008 R2 SP1, N�O funcionou    ): ========================================
REM ================                                    (testado com Windows Server 2012 R2,         funcionou sim)  ========================================

:TesteRegedit

REM Primeiro rodar na m�o, como Administrador, se o JRE n�o estiver instalado: 
REM Start /wait jre-8u92-windows-x64.exe /s INSTALLDIR=C:\\"Program Files"\Java\jre1.8.0_92

set osVersion=Windows_Server_2012_R2
set /A tipoOS=64
set drive=C:
set Windows_10_Pro_64bit=0

if %osVersion% == Windows_10_Pro (
   if %tipoOS% == 64 (
      set Windows_10_Pro_64bit=1
   )
)

if %osVersion% == Windows_Server_2008_R2_SP1 (
    echo Entrou no if 2008
    set arquivoRegedit="%drive%\\Arquivos de Programas PC\\DeshabilitarJavaUpdates.x64.reg"
) else if %osVersion% == Windows_Server_2012_R2 (
    echo Entrou no else if 2012
    set arquivoRegedit="%~dp0DeshabilitarJavaUpdates.x64.reg"
) else if %osVersion% == Windows_Server_2016 (
    echo Entrou no else if 2016
    set arquivoRegedit="%~dp0DeshabilitarJavaUpdates.x64.reg"
) else if %Windows_10_Pro_64bit% == 1 (
    echo Entrou no else if Win 10
    set arquivoRegedit=DeshabilitarJavaUpdates.x64.reg
) else if %osVersion% == Windows_7_Professional_SP1 (
    echo Entrou no else if Win 7 Professional
    set arquivoRegedit=DeshabilitarJavaUpdates.i586.reg
) else (
    echo Entrou no else das osVersions
    echo MSGBOX "Esta vers�o de Windows ainda n�o est� suportada! Favor entrar em contato com o Suporte do Portal Cronos." > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
    exit
)


echo arquivoRegedit = %arquivoRegedit%
pause

if %tipoOS% == 64 (
    regedit.exe %arquivoRegedit%
) else if %tipoOS% == 32 (
    regedit.exe %arquivoRegedit%
) else (
    echo Tipo OS %tipoOS% n�o reconhecido ^! Op��es: 32 ou 64 ^(Bits^)
)

pause
exit

REM ================ Teste de chamada de arqs .bat em subdirs (testado, funcionou): ========================================

:TesteSubDirBin

REM Mover temporariamente "TestadorUnitario.bat" de \Integra��o Fornecedor - Portal Cronos\
REM para \Integra��o Fornecedor - Portal Cronos\bin\;
call "Integra��o Fornecedor - Portal Cronos\bin\TestadorUnitario.bat"

pause
exit

REM ================ Teste unit�rio if exist C:/"Program Files (x86)"/Java/jre1.8.0_191/bin/java.exe (testado, funcionou): ========

:TesteIfExist

if exist C:/"Program Files (x86)"/Java/jre1.8.0_191/bin/java.exe (
    echo MSGBOX "jre1.8.0_191 existe!" > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
) else if exist C:/"Program Files"/Java/jre1.8.0_92/bin/java.exe (
    echo MSGBOX "jre1.8.0_92 existe!" > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
) else (
    echo MSGBOX "Erro! Nem jre1.8.0_191 nem jre1.8.0_92 existe!" > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
)
		
pause
exit

REM ================ Teste attrib +S de diret�rio (testado, funcionou): ========================================

:TesteAttrib

REM Prote��o contra exclus�o indevida por gerente ignorante que gosta de apagar coisas 
REM e que apaga tudo que n�o foi ele mesmo que tinha feito, sem avisar ningu�m:

cd\
attrib "Arquivos de Programas PC" +S +H
attrib /S /D "Arquivos de Programas PC"

pause
exit

REM ================ Teste Vers�o (testado, funcionou): ========================================

:TesteVersao

SETLOCAL

call "Integra��o Fornecedor - Portal Cronos\bin\Versao.bat"
echo integr-fornecedor-%versaoIntegrador%.jar


ENDLOCAL

pause
exit

REM ================ Teste Passagem 3 par�metros (testado, funcionou): ========================================

:TesteTresParam

REM "chcp 1252>nul" � necess�rio para evitar que DOS n�o reconhece acentos Portugueses no caminho 
REM "Integra��o Fornecedor - Portal Cronos" em alguns ou talvez at� em todos os servidores:

chcp 1252>nul

echo.
echo Favor entrar em contato com o setor Desenvolvimento do Portal Cronos para obter o ID da empresa fornecedora.
echo.

SET /P idFornecedor=Favor digitar o ID da empresa fornecedora: 
IF "%idFornecedor%"=="" GOTO ErroIdFornecedor
GOTO PularErro
:ErroIdFornecedor
echo MSGBOX "Erro: ID do fornecedor n�o informado! Instala��o n�o conclu�da!!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
REM Fechar o script chamador tamb�m: 
exit
:PularErro


call "Integra��o Fornecedor - Portal Cronos\bin\Versao.bat"
call "Integra��o Fornecedor - Portal Cronos\bin\ComponenteTestador.bat" TesteTresParam.log TestadorSnippets %idFornecedor%


pause
exit

REM ================ Teste Template (testado??????????, funcionou????????????????): ========================================

:TesteTemplate

........comandos DOS..........


pause
exit

REM ============================ Fim =========================================================================

:FIM
