
cls
@echo off

REM "chcp 1252>nul" � necess�rio para evitar que DOS n�o reconhece acentos Portugueses no caminho 
REM "Integra��o Fornecedor - Portal Cronos" em alguns ou talvez at� em todos os servidores:

chcp 1252>nul


echo.
echo Favor entrar em contato com o setor Desenvolvimento do Portal Cronos para obter o ID da empresa fornecedora.
echo Digite -1, -2 ou -3 no caso de upgrade do Monitorador no servidor de de aplica��o, base de dados, 
echo ou base de dados de conting�ncia.
echo.

SET /P idFornecedor=Favor digitar o ID da empresa fornecedora: 
IF "%idFornecedor%"=="" GOTO ErroIdFornecedor
GOTO PularErro
:ErroIdFornecedor
echo MSGBOX "Erro: ID do fornecedor n�o informado! Upgrade para 3.1.0 n�o conclu�do!!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
REM Fechar o script chamador tamb�m: 
exit
:PularErro


cd\
cd "Arquivos de Programas PC"
if exist "Integra��o Portal Cronos - Fornecedor.Windows.2008_R2.TaskSchedule.xml" del /f /q "Integra��o Portal Cronos - Fornecedor.Windows.2008_R2.TaskSchedule.xml"

SCHTASKS /End /TN "Integra��o Portal Cronos - Fornecedor"
SCHTASKS /Delete /TN "Integra��o Portal Cronos - Fornecedor" /F

cd\
cd "Arquivos de Programas PC"
REM ?????? call "Integra��o Fornecedor - Portal Cronos\bin\Inicializacoes.bat"
call "Integra��o Fornecedor - Portal Cronos\bin\Versao.bat"
call "Integra��o Fornecedor - Portal Cronos\bin\CaminhoJRE.bat" GeradorTarefaWindowsNaoNuvem.log GeradorTarefaWindowsNaoNuvem %idFornecedor%


set arquivoLog="GeradorTarefaWindowsNaoNuvem.log"
set tamanhoArqLog=0

FOR /F "usebackq" %%A IN ('%arquivoLog%') DO set tamanhoArqLog=%%~zA

if %tamanhoArqLog% GTR 0 (
    echo.
    echo          O adicionamento da inst�ncia nova falhou!
    echo.
    
    echo MSGBOX "O upgrade para 3.1.0 falhou!" > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
    start notepad GeradorTarefaWindowsNaoNuvem.log
) else (
    SCHTASKS /Create /TN "Integra��o Portal Cronos - Fornecedor" /XML "C:/Arquivos de Programas PC/Integra��o Portal Cronos - Fornecedor.Windows.2008_R2.TaskSchedule.xml"
    SCHTASKS /Run /TN "Integra��o Portal Cronos - Fornecedor"
    if exist GeradorTarefaWindowsNaoNuvem.log del /f /q GeradorTarefaWindowsNaoNuvem.log 
)

cd\
cd "Arquivos de Programas PC"
if exist "Integra��o Portal Cronos - Fornecedor.Windows.2008_R2.TaskSchedule.xml" del /f /q "Integra��o Portal Cronos - Fornecedor.Windows.2008_R2.TaskSchedule.xml"
del /f /q UpgradeNaoNuvem3.1.0.bat
