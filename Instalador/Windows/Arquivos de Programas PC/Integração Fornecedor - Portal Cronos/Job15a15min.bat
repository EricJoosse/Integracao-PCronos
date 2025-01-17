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
cd "Integra��o Fornecedor - Portal Cronos"

call bin\Inicializacoes.bat
call bin\Versao.bat

REM Para evitar travamento deste Job15a15min.bat e ignoramento pelo Windows Scheduler 
REM devido a travamento do arquivo de log: 
if "%1"=="" (
  call bin\CaminhoJRE.bat Job15a15min.log IntegracaoFornecedorCompleta %1
  set arquivoLog="Job15a15min.log"
) else (
  call bin\CaminhoJRE.bat Job15a15min.%1.log IntegracaoFornecedorCompleta %1
  set arquivoLog="Job15a15min.%1.log"
)


set tamanhoArqLog=0

FOR /F "usebackq" %%A IN ('%arquivoLog%') DO set tamanhoArqLog=%%~zA

if %tamanhoArqLog% GTR 0 (
    echo.
) else (
  if exist Job15a15min.log del /f /q Job15a15min.log
  if exist Job15a15min.%1.log del /f /q Job15a15min.%1.log
)

ENDLOCAL
exit


