
cls
@echo off

chcp 1252>nul

echo.
echo          Desinstalando...................
echo.

REM echo MSGBOX "Desinstalando..." > %temp%\TEMPmessage.vbs
REM call %temp%\TEMPmessage.vbs
REM del %temp%\TEMPmessage.vbs /f /q
  
    set drive=C:
REM set drive=D:

REM ================ Remover Manual Manuten��o TI do menu de Windows, ANTES da remo��o dos programas de Java: ========================================

call DesinstalarManualTI.bat

IF %ERRORLEVEL% NEQ 0 (
    goto PULAR_MENSAGEM_SUCESSO
)


REM ================ Remover Task no Windows Scheduler: ========================================

SCHTASKS /End /TN "Integra��o Portal Cronos - Fornecedor"
SCHTASKS /Delete /TN "Integra��o Portal Cronos - Fornecedor" /F


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

REM ================ Remover JRE: ========================================

wmic product where "name like 'Java 8 Update 92%%'" call uninstall /nointeractive


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
