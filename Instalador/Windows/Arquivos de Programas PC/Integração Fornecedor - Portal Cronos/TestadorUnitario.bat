
REM goto OutrosTestes
goto TesteParamDosOptional
goto TesteVersao

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

