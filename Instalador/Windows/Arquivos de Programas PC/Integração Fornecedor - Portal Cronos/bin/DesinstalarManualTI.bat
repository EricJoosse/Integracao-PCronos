cls
@echo off

REM Resettar ERRORLEVEL para 0 (n�o existe valor default):
call (exit /b 0)

call CaminhoJRE.bat

if exist Desinstalador.log del /f /q Desinstalador.log

REM Usar o caminho completo do JRE para o caso que tiver 2 JRE�s no mesmo servidor 
REM e o caminho do outro JRE est� na primeira posi��o no PATH de DOS:

REM Se tiver um parentese dentro do path, "set path" n�o funciona dentro de if�s e else�s, 
REM ent�o precisamos de goto�s:

if exist C:/"Program Files (x86)"/Java/jre1.8.0_191/bin/java.exe (
    goto PathProlac
) else if exist C:/"Program Files (x86)"/Java/jre1.8.0_111/bin/java.exe (
    goto PathPadeirao
) else if exist C:/"Program Files"/Java/jre1.8.0_92/bin/java.exe (
    goto PathOutros
) else (
    echo MSGBOX "Erro! O JRE n�o foi encontrado!" > %temp%\TEMPmessage.vbs
    exit
)

:PathProlac
set path=C:\Program Files (x86)\Java\jre1.8.0_191\bin;%path%
C:/"Program Files (x86)"/Java/jre1.8.0_191/bin/java.exe -cp integr-fornecedor-%versaoIntegrador%.jar pcronos.integracao.fornecedor.Desinstalador >> Desinstalador.log
goto PularPathOutros

:PathPadeirao
set path=C:\Program Files (x86)\Java\jre1.8.0_111\bin;%path%
C:/"Program Files (x86)"/Java/jre1.8.0_111/bin/java.exe -cp integr-fornecedor-%versaoIntegrador%.jar pcronos.integracao.fornecedor.Desinstalador >> Desinstalador.log
goto PularPathOutros

:PathOutros
set path=C:\Program Files\Java\jre1.8.0_92\bin;%path%
C:/"Program Files"/Java/jre1.8.0_92/bin/java.exe -cp integr-fornecedor-%versaoIntegrador%.jar pcronos.integracao.fornecedor.Desinstalador >> Desinstalador.log
:PularPathOutros



set arquivoLog="Desinstalador.log"
set tamanhoArqLog=0

FOR /F "usebackq" %%A IN ('%arquivoLog%') DO set tamanhoArqLog=%%~zA

if %tamanhoArqLog% GTR 0 (
    echo.
    echo          A desinstala��o falhou!
    echo.
    
    echo MSGBOX "A desinstala��o falhou!" > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
    start notepad Desinstalador.log
    ENDLOCAL
    exit /B 1
) else (
    ENDLOCAL
REM /B para n�o fechar o script chamador:  
    exit /B 0
)


