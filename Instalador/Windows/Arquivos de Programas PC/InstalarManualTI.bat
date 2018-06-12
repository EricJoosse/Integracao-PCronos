cls
@echo off

SET /P idFornecedor=Favor digitar o ID do fornecedor: 
IF "%idFornecedor%"=="" GOTO ErroIdFornecedor
GOTO PularErro
:ErroIdFornecedor
echo MSGBOX "Erro: ID do fornecedor n�o informado! Instala��o n�o conclu�da!!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
REM Fechar o script chamador tamb�m: 
exit
:PularErro

REM Limpar CLASSPATH :
REM set CLASSPATH=

SETLOCAL

REM Se tiver um parentese dentro do path, o seguinte n�o funciona:

REM if %idFornecedor% == 30 (
REM     set path=C:\Program Files ^(x86^)\Java\jre1.8.0_161\bin;%path%
REM ) else (
REM     set path=C:\Program Files\Java\jre1.8.0_92\bin;%path%
REM )

if %idFornecedor% == 30 (
    goto PathProlac
) else (
    goto PathOutros
)

:PathProlac
set path=C:\Program Files (x86)\Java\jre1.8.0_161\bin;%path%
goto PularPathOutros
:PathOutros
set path=C:\Program Files\Java\jre1.8.0_92\bin;%path%
:PularPathOutros


REM         Errado em vers�es < 2.1.2B: set path=%path%;C:\Program Files\Java\jre1.8.0_161\bin
REM Prolac: Errado em vers�es < 2.1.2B: set path=%path%;C:\Program Files (x86)\Java\jre1.8.0_161\bin

REM http://stackoverflow.com/questions/23730887/why-is-rt-jar-not-part-of-the-class-path-system-property : 
REM 	"rt.jar doesn't need to be in the classpath, since it is already in the bootclasspath. It is safe to remove it from your classpath."
REM set CLASSPATH=%CLASSPATH%;C:\Program Files\Java\jre1.8.0_92\lib\rt.jar

REM set CLASSPATH=%CLASSPATH%;lib/jersey-bundle-1.12.jar
REM set CLASSPATH=%CLASSPATH%;lib/jersey-core-1.12.jar
REM set CLASSPATH=%CLASSPATH%;lib/jersey-multipart-1.12.jar
REM set CLASSPATH=%CLASSPATH%;lib/jdbc-oracle.jar
REM set CLASSPATH=%CLASSPATH%;lib/jaybird-full-2.2.10.jar
REM set CLASSPATH=%CLASSPATH%;.

REM echo %classpath%

chcp 1252>nul
cd\
cd "Arquivos de Programas PC"
cd "Integra��o Fornecedor - Portal Cronos"


if exist Instalador.log del /f /q Instalador.log

REM Caminho completo para o caso que tiver 2 JRE�s no mesmo servidor,
REM e o caminho do outro JRE vem primeiro no PATH de DOS :

if %idFornecedor% == 30 (
    C:/"Program Files (x86)"/Java/jre1.8.0_161/bin/java.exe -cp integr-fornecedor-2.5.1.jar pcronos.integracao.fornecedor.Instalador %idFornecedor% >> Instalador.log
) else (
    C:/"Program Files"/Java/jre1.8.0_92/bin/java.exe -cp integr-fornecedor-2.5.1.jar pcronos.integracao.fornecedor.Instalador %idFornecedor% >> Instalador.log
)


set arquivoLog="Instalador.log"
set tamanhoArqLog=0

FOR /F "usebackq" %%A IN ('%arquivoLog%') DO set tamanhoArqLog=%%~zA

if %tamanhoArqLog% GTR 0 (
    echo.
    echo          A instala��o falhou!
    echo.
    
    echo MSGBOX "A instala��o falhou!" > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
    start notepad Instalador.log
) ELSE (
    echo.
    echo          Instala��o concluida!
    echo.
    
    echo MSGBOX "Instala��o concluida!" > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
)


ENDLOCAL
REM /B para n�o fechar o script chamador:  
exit /B 0


