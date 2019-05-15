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


SET /P nmFornecedor=Favor digitar um nome bem curto da empresa fornecedora SEM NENHUM ESPA�O EM BRANCO: 
IF "%nmFornecedor%"=="" GOTO ErroNmFornecedor
if not "%VAR%"=="%VAR: =%" goto ErroEspacosNmFornecedor
GOTO PularErroNmFornecedor
:ErroEspacosNmFornecedor
echo MSGBOX "Erro: n�o pode ter nenhum espa�o em branco no nome do fornecedor! Adicionamento deste fornecedor n�o conclu�do!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
REM Fechar o script chamador tamb�m: 
exit
:ErroNmFornecedor
echo MSGBOX "Erro: nome do fornecedor n�o informado! Adicionamento deste fornecedor n�o conclu�do!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
REM Fechar o script chamador tamb�m: 
exit
:PularErroNmFornecedor


if exist "C:/Arquivos de Programas PC/Integra��o Fornecedor - Portal Cronos/conf/Integra��o APS - Portal Cronos.%nmFornecedor%.properties" (
    echo.
    echo          Este fornecedor j� foi adicionado!
    echo.
    
    echo MSGBOX "Este fornecedor j� foi adicionado!" > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
    exit
)

if exist FornecedorAdicionalNuvem.Windows2008_R2.TaskSchedule.xml del /f /q FornecedorAdicionalNuvem.Windows2008_R2.TaskSchedule.xml 

copy "C:/Arquivos de Programas PC/Integra��o Fornecedor - Portal Cronos/conf/TemplateNuvemAPS.properties" "C:/Arquivos de Programas PC/Integra��o Fornecedor - Portal Cronos/conf/Integra��o APS - Portal Cronos.%nmFornecedor%.properties"


call "Integra��o Fornecedor - Portal Cronos\bin\Inicializacoes.bat"
call "Integra��o Fornecedor - Portal Cronos\bin\Versao.bat"
call "Integra��o Fornecedor - Portal Cronos\bin\CaminhoJRE.bat" AdicionarFornecedorNuvem.log AdicionarFornecedorNuvem %nmFornecedor%

set arquivoLog="AdicionarFornecedorNuvem.log"
set tamanhoArqLog=0

FOR /F "usebackq" %%A IN ('%arquivoLog%') DO set tamanhoArqLog=%%~zA

if %tamanhoArqLog% GTR 0 (
    echo.
    echo          O adicionamento do fornecedor novo falhou!
    echo.
    
    echo MSGBOX "O adicionamento do fornecedor novo falhou!" > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
    start notepad AdicionarFornecedorNuvem.log
) ELSE (

  SCHTASKS /Create /TN "Integra��o Portal Cronos - %nmFornecedor%" /XML "C:/Arquivos de Programas PC/FornecedorAdicionalNuvem.Windows2008_R2.TaskSchedule.xml"
  SCHTASKS /Run /TN "Integra��o Portal Cronos - %nmFornecedor%"

  start notepad "conf/Integra��o APS - Portal Cronos.%nmFornecedor%.properties"
)

REM /B para n�o fechar o script chamador (Primeira_Instalacao_Versao_Windows.bat):  
exit /B 0


:FIM
