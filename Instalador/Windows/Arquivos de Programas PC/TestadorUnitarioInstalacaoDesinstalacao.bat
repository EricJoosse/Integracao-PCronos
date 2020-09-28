cls
@echo off

REM Windows, por default, usa o charset WINDOWS-1252 (ou outra varia��o, dependendo da lingua), 
REM que � uma extens�o do ISO-8859-1. 
REM Mesmo assim, n�o se compara com UTF-8! O Linux, por default, usa o charset UTF-8. 
REM Eclipse herda o charset de Windows por default. 
REM "chcp 1252>nul" � necess�rio para evitar que DOS n�o reconhece acentos Portugueses no caminho 
REM "Integra��o Fornecedor - Portal Cronos" em alguns ou talvez at� em todos os servidores:

chcp 1252>nul


REM o diret�rio default de MS-DOS � C:\Windows\system32, ent�o � melhor em todos os casos 
REM pelo menos navegar primeiro para o diret�rio onde fica este Testador Unit�rio do Instalador e do Desinstalador:

cd\

REM Se testar dentro do Eclipse ao inv�s de nos servidores:
if exist C:/PCronos/"Integra��o Fornecedor - Portal Cronos"/Instalador/Windows/"Arquivos de Programas PC"/Instalador.bat (
  cd PCronos
  cd "Integra��o Fornecedor - Portal Cronos"
  cd Instalador
  cd Windows
  cd "Arquivos de Programas PC"
) else (  
  cd "Arquivos de Programas PC"
)

REM "pwd" em Linux = "%cd% em DOS:
echo %cd%

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
REM goto TesteTemplate
REM goto TesteDelTpl
REM goto TesteIsAmbienteNuvem
REM goto TesteRemocaoMultiplasTarefasWindows
REM goto TesteIfExistArqConfEspecifico
REM goto TesteFindTarefaEspecifica
goto TesteTresParam


REM ================ Testes Instala��o Resolver Paradas do menu de Windows: ========================================
REM ================  (testado com Windows Server 2008 R2 SP1, funcionou)       ========================================
REM ================  (testado com Windows Server 2012 R2,     funcionou)       ========================================
REM ================  (testado com Windows Server 2016,        funcionou)       ========================================
REM ================  (testado com Windows Server 2016,        funcionou)       ========================================
REM ================  (testado com Windows 10 Pro - apenas testado no caso de um processador e SO 64 bit, funcionou)
:InstalarManualTI
call Instalador.bat
exit

REM ================ Testes Desinstala��o Resolver Paradas do menu de Windows: ========================================
REM ================  (testado com Windows Server 2008 R2 SP1, funcionou)       ========================================
REM ================  (testado com Windows Server 2012 R2,     funcionou)       ========================================
REM ================  (testado com Windows Server 2016,        funcionou)       ========================================
REM ================  (testado com Windows 10 Pro - apenas testado no caso de um processador e SO 64 bit, funcionou)

:DesinstalarManualTI
call "Integra��o Fornecedor - Portal Cronos\bin\Desinstalador.bat"

echo Passou Desinstalador.bat
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
del /f /q Instalador.bat
del /f /q Instalador_Integrador.bat
del /f /q Instalador_Monitorador.bat
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

REM Windows, por default, usa o charset WINDOWS-1252 (ou outra varia��o, dependendo da lingua), 
REM que � uma extens�o do ISO-8859-1. 
REM Mesmo assim, n�o se compara com UTF-8! O Linux, por default, usa o charset UTF-8. 
REM Eclipse herda o charset de Windows por default. 
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

cd "Integra��o Fornecedor - Portal Cronos"
call bin\Versao.bat
call bin\ComponenteTestador.bat TesteTresParam.log TestadorSnippets %idFornecedor%


pause
exit

REM ================ Teste del tpl (testado, funcionou com -1 e com 33): ========================================

:TesteDelTpl

chcp 1252>nul

cd\
cd "Arquivos de Programas PC"
call Instalador.bat
cd\
cd "Arquivos de Programas PC"
cd "Integra��o Fornecedor - Portal Cronos"

if %idFornecedor% NEQ -1 (
    rmdir /s /q tpl
)


pause
exit

REM ================ Teste IsAmbienteNuvem (testado, funcionou): ========================================

:TesteIsAmbienteNuvem

chcp 1252>nul

cd\
cd "Arquivos de Programas PC"

call "Integra��o Fornecedor - Portal Cronos\bin\IsAmbienteNuvem.bat"

REM Aspas duplas s�o necess�rias porque se o vari�vel for empty, este .bat vai falhar pois n�o entende o parentese direto ap�s o ==
if "%IsAmbienteNuvem%" == "1" (
       echo IsAmbienteNuvem == 1
) else if "%IsAmbienteNuvem%" == "0" (
       echo IsAmbienteNuvem == 0
) else (
    echo.
    echo          A desinstala��o falhou! Vari�vel IsAmbienteNuvem inv�lido!
    echo.
    
    echo MSGBOX "A instala��o falhou! Vari�vel IsAmbienteNuvem inv�lido!" > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
REM start notepad Desinstalador.log
)

pause
exit

REM ================ Teste Remo��o m�ltiplas tarefas Windows no caso de Integradores Cloud (testado, funcionou): ========================================

:TesteRemocaoMultiplasTarefasWindows

chcp 1252>nul
cd\
cd "Arquivos de Programas PC"
call "Integra��o Fornecedor - Portal Cronos\bin\IsAmbienteNuvem.bat"

for /f "tokens=* delims=\" %%x in ('SCHTASKS /QUERY /FO:LIST ^| FINDSTR "Integra��o Portal Cronos."') do echo "\%%x"

REM Aspas duplas s�o necess�rias porque se o vari�vel for empty, este .bat vai falhar pois n�o entende o parentese direto ap�s o ==
if "%IsAmbienteNuvem%" == "1" (
    for /f "tokens=2 delims=\" %%x in ('SCHTASKS /QUERY /FO:LIST ^| FINDSTR "Integra��o Portal Cronos."') do SCHTASKS /End /TN "\%%x"
    for /f "tokens=2 delims=\" %%x in ('SCHTASKS /QUERY /FO:LIST ^| FINDSTR "Integra��o Portal Cronos."') do SCHTASKS /Delete /TN "\%%x" /F
) else (
    echo.
    echo          A desinstala��o falhou! Vari�vel IsAmbienteNuvem inv�lido!
    echo.
    
    echo MSGBOX "A instala��o falhou! Vari�vel IsAmbienteNuvem inv�lido!" > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
REM start notepad Desinstalador.log
)


pause
exit

REM ================ Teste Find Tarefa Espec�fica (testado??????????, funcionou????????????????): ========================================

:TesteFindTarefaEspecifica

chcp 1252>nul

cd\
cd "Arquivos de Programas PC"

echo.
echo.
SET /P nmFornecedor=Digite o nome fantasia da empresa cliente: 
IF "%nmFornecedor%"=="" GOTO ErroNmFornecedor
if not "%nmFornecedor%"=="%nmFornecedor: =%" goto ErroEspacosNmFornecedor
GOTO PularErroNmFornecedor
:ErroEspacosNmFornecedor
echo MSGBOX "Erro: n�o pode ter nenhum espa�o em branco no nome! Remo��o deste cliente n�o efetuada!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
REM Fechar o script chamador tamb�m: 
exit
:ErroNmFornecedor
echo MSGBOX "Erro: nome n�o informado! Remo��o deste cliente n�o efetuada!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
REM Fechar o script chamador tamb�m: 
exit
:PularErroNmFornecedor

set ClienteExiste=0
for /f "tokens=2 delims=\" %%x in ('SCHTASKS /QUERY /FO:LIST ^| FINDSTR "Integra��o Portal Cronos - ^%nmFornecedor^%"') do set ClienteExiste=1
for /f "tokens=2 delims=\" %%x in ('SCHTASKS /QUERY /FO:LIST ^| FINDSTR "Integra��o Portal Cronos - ^%nmFornecedor^%"') do echo "\%%x"
if %ClienteExiste% == 0 (
    echo.
    echo          Nome inv�lido!
    echo.
    
    echo MSGBOX "Nome inv�lido!" > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
    exit
) else (
    echo.
    echo          Tarefa de %nmFornecedor% encontrada!
    echo.
    
    echo MSGBOX "Tarefa de %nmFornecedor% encontrada!" > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
    exit
)

pause
exit

REM ================ Teste If Exist Arq Conf Especifico (testado, funcionou): ========================================

:TesteIfExistArqConfEspecifico

chcp 1252>nul

cd\
cd "Arquivos de Programas PC"

echo.
echo.
SET /P nmFornecedor=Digite o nome fantasia da empresa cliente: 
IF "%nmFornecedor%"=="" GOTO ErroNmFornecedor
if not "%nmFornecedor%"=="%nmFornecedor: =%" goto ErroEspacosNmFornecedor
GOTO PularErroNmFornecedor
:ErroEspacosNmFornecedor
echo MSGBOX "Erro: n�o pode ter nenhum espa�o em branco no nome! Remo��o deste cliente n�o efetuada!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
REM Fechar o script chamador tamb�m: 
exit
:ErroNmFornecedor
echo MSGBOX "Erro: nome n�o informado! Remo��o deste cliente n�o efetuada!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
REM Fechar o script chamador tamb�m: 
exit
:PularErroNmFornecedor

if not exist "C:/Arquivos de Programas PC/Integra��o Fornecedor - Portal Cronos/conf/Integra��o APS - Portal Cronos.%nmFornecedor%.properties" (
    echo.
    echo          Nome inv�lido!
    echo.
    
    echo MSGBOX "Nome inv�lido!" > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
    exit
) else (
    echo.
    echo          Arq config de %nmFornecedor% encontrado!
    echo.
    
    echo MSGBOX "Arq config de %nmFornecedor% encontrado!" > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
    exit
)


pause
exit

REM ================ Teste Template (testado??????????, funcionou????????????????): ========================================

:TesteTemplate

........comandos DOS..........


pause
exit

REM ============================ Fim =========================================================================

:FIM
