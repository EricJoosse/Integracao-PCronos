
REM Fazer o seguinte apenas nos servidores dos fornecedores e n�o dentro do Eclipse:
REM O seguinte � necess�rio se executar o .bat usando "Executar como Administrador" ou se rodar 
REM dentro do Task Scheduler: 
REM "chcp 1252>nul" � necess�rio para evitar que DOS n�o reconhece acentos Portugueses no caminho 
REM "Integra��o Fornecedor - Portal Cronos" em alguns ou talvez at� em todos os servidores:

chcp 1252>nul

if not exist C:/PCronos/"Integra��o Fornecedor - Portal Cronos"/Instalador/Windows/"Arquivos de Programas PC"/InstalarManualTI.bat (
  cd\
  cd "Arquivos de Programas PC"
  cd "Integra��o Fornecedor - Portal Cronos"
)

REM goto TesteVersao
REM goto TesteParamDosOptional
goto OutrosTestes

REM ================ Teste de chamada de arqs .bat em subdirs (testado, funcionou): ========================================

echo MSGBOX "Teste de chamada de arqs .bat em subdirs" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q

pause
exit

REM ==================== Outros testes: ===============================================================
REM ==================== 1. Teste Par�metro optional CaminhoJRE.bat (testado, funcionou): ===============================================================
REM ==================== 2. ............... ===============================================================

:TesteParamDosOptional
:OutrosTestes

cls
@echo off

call bin\Inicializacoes.bat
call bin\Versao.bat
call bin\ComponenteTestador.bat TestadorUnitario.log TestadorSnippets

ENDLOCAL
pause
exit


REM ==================== Teste Vari�vel Vers�o: ===============================================================

:TesteVersao

SETLOCAL

call bin\Versao.bat
C:/"Program Files"/Java/jre1.8.0_92/bin/java.exe -cp integr-fornecedor-%versaoIntegrador%.jar pcronos.integracao.fornecedor.TestadorSnippets >> TestadorUnitario.log

ENDLOCAL
pause
exit

