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


echo.
echo.
echo Op�oes: 
echo.
echo 1 = Sim
echo 2 = Nao
echo.
echo IMPORTANTE: � recomend�vel primeiro fazer backup das configura�oes do cliente!
echo.

SET /P temCerteza=Tem certeza que deseja remover um cliente? 
IF "%temCerteza%"=="" GOTO ErroTemCerteza
if "%temCerteza%"=="1" (
   GOTO PularErroTemCerteza
) else (
REM Fechar o script chamador tamb�m: 
   exit
)
:ErroTemCerteza
echo MSGBOX "Erro: confirma��o n�o informada! Remo��o de nenhum cliente efetuada!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
REM Fechar o script chamador tamb�m: 
exit
:PularErroTemCerteza


cls

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



REM set ClienteExiste=0
REM for /f "tokens=2 delims=\" %%x in ('SCHTASKS /QUERY /FO:LIST ^| FINDSTR "Integra��o Portal Cronos - %nmFornecedor%"') do set ClienteExiste=1
REM if %ClienteExiste% == 0 (
REM     echo.
REM     echo          Nome inv�lido!
REM     echo.
REM     
REM     echo MSGBOX "Nome inv�lido!" > %temp%\TEMPmessage.vbs
REM     call %temp%\TEMPmessage.vbs
REM     del %temp%\TEMPmessage.vbs /f /q
REM     exit
REM )


if not exist "C:/Arquivos de Programas PC/Integra��o Fornecedor - Portal Cronos/conf/Integra��o APS - Portal Cronos.%nmFornecedor%.properties" (
    echo.
    echo          Nome inv�lido!
    echo.
    
    echo MSGBOX "Nome inv�lido!" > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
    exit
)


REM Remover as coisas na seguinte ordem:
REM  1. primeiro remover o Task Schedule,
REM  2. em seguida remover a entrada no menu de Windows,
REM  3. no final remover o arquivo de configura��o .properties: 


SCHTASKS /End /TN "Integra��o Portal Cronos - %nmFornecedor%"
SCHTASKS /Delete /TN "Integra��o Portal Cronos - %nmFornecedor%" /F



cd\
cd "Arquivos de Programas PC"
call "Integra��o Fornecedor - Portal Cronos\bin\Inicializacoes.bat"
call "Integra��o Fornecedor - Portal Cronos\bin\Versao.bat"
call "Integra��o Fornecedor - Portal Cronos\bin\CaminhoJRE.bat" RemovedorFornecedorNuvem.log RemovedorFornecedorNuvem %nmFornecedor%

set arquivoLog="RemovedorFornecedorNuvem.log"
set tamanhoArqLog=0

FOR /F "usebackq" %%A IN ('%arquivoLog%') DO set tamanhoArqLog=%%~zA

if %tamanhoArqLog% GTR 0 (
    echo.
    echo          A remo�ao falhou!
    echo.
    
    echo MSGBOX "A remo��o falhou!" > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
    start notepad RemovedorFornecedorNuvem.log
) else (
  cd\
  cd "Arquivos de Programas PC"
  cd "Integra��o Fornecedor - Portal Cronos"
  cd conf
  del /f /q "Integra��o APS - Portal Cronos.%nmFornecedor%.properties"
)


REM /B para n�o fechar o script chamador (Primeira_Instalacao_Versao_Windows.bat):  
exit /B 0


:FIM
