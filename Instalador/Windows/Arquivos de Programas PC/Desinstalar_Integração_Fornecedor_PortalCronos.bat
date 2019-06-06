
cls
@echo off

REM "chcp 1252>nul" � necess�rio para evitar que DOS n�o reconhece acentos Portugueses no caminho 
REM "Integra��o Fornecedor - Portal Cronos" em alguns ou talvez at� em todos os servidores:

chcp 1252>nul

echo.
echo          Desinstalando...................
echo.

REM echo MSGBOX "Desinstalando..." > %temp%\TEMPmessage.vbs
REM call %temp%\TEMPmessage.vbs
REM del %temp%\TEMPmessage.vbs /f /q
  
    set drive=C:
REM set drive=D:

cd\
cd "Arquivos de Programas PC"

call "Integra��o Fornecedor - Portal Cronos\bin\IsAmbienteNuvem.bat"


REM ================ Remover Manual Manuten��o TI do menu de Windows, ANTES da remo��o dos programas de Java: ========================================

cd\
cd "Arquivos de Programas PC"

call "Integra��o Fornecedor - Portal Cronos\bin\Desinstalador.bat"

IF %ERRORLEVEL% NEQ 0 (
    goto PULAR_MENSAGEM_SUCESSO
)


REM ================ Remover Task(s) no Windows Scheduler: ========================================

REM Ambiente Nuvem:
REM Aspas duplas s�o necess�rias porque se o vari�vel for empty, este .bat vai falhar pois n�o entende o parentese direto ap�s o ==
if "%IsAmbienteNuvem%" == "1" (
    for /f "tokens=2 delims=\" %%x in ('SCHTASKS /QUERY /FO:LIST ^| FINDSTR "Integra��o Portal Cronos."') do SCHTASKS /End /TN "\%%x"
    for /f "tokens=2 delims=\" %%x in ('SCHTASKS /QUERY /FO:LIST ^| FINDSTR "Integra��o Portal Cronos."') do SCHTASKS /Delete /TN "\%%x" /F
) else if "%IsAmbienteNuvem%" == "0" (
    SCHTASKS /End /TN "Integra��o Portal Cronos - Fornecedor"
    SCHTASKS /Delete /TN "Integra��o Portal Cronos - Fornecedor" /F
) else (
    echo.
    echo          A desinstala��o falhou! Vari�vel IsAmbienteNuvem inv�lido!
    echo.
    
    echo MSGBOX "A instala��o falhou! Vari�vel IsAmbienteNuvem inv�lido!" > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
    start notepad Desinstalador.log
)



REM ================ Remover subdiret�rios de "Arquivos de Programas PC": ========================================

cd\
cd "Arquivos de Programas PC"
rmdir /s /q "Integra��o Fornecedor - Portal Cronos"
for %%f in (*) do if not %%~xf==.bat del /f /q "%%f"
REM N�o precisa mais, pois o Instalador j� apaga no final da instala��o: del /f /q Primeira_Instalacao_Versao_Windows.bat

REM ================ Remover diret�rio de Log: ========================================

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

REM Nunca mais remover o JRE, mesmo no caso o JRE foi instalado pela primeira vez pelo Portal Cronos, 
REM pois pode ser que depois disso outro sistema foi instalado que passou a usar o mesmo JRE:
goto SKIP_JRE

REM ================ Remover JRE: ========================================

REM Mais tarde parametrizar no Desinstalador se o usu�rio quiser desinstalar o JRE tamb�m sim/n�o, 
REM perguntando sem ele tem certeza, pois outros sistemas podem parar de funcionar. 
REM N�o dar nem esta op��o no caso que o JRE (1.8.0_92) n�o foi instalado pelo Portal Cronos 
REM por�m o Portal Cronos tinha usado um JRE j� existente instalado anteriormente por terceiros.
REM 
REM No caso, tamb�m parametrizar 'Java 8 Update 92, 111, 191, etc.'

wmic product where "name like 'Java 8 Update 92%%'" call uninstall /nointeractive

:SKIP_JRE

:FIM

echo.
echo          Desinstala��o concluida!
echo.

echo MSGBOX "Desinstala��o concluida!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q

REM ================ Remover diret�rio "Arquivos de Programas PC": ========================================

REM O seguinte consegue remover todos os arquivos no diret�rio "Arquivos de Programas PC",
REM at� este arquivo .bat, por�m n�o consegue remover o diret�rio "Arquivos de Programas PC" :
REM ????????? Funcionou quando usei vbs acima antes disso !!!!!!!!!
cd\
rmdir /s /q "Arquivos de Programas PC"

:PULAR_MENSAGEM_SUCESSO
