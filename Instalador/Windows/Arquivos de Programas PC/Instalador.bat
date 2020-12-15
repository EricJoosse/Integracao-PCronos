cls
@echo off

REM Windows, por default, usa o charset WINDOWS-1252 (ou outra varia��o, dependendo da lingua), 
REM que � uma extens�o do ISO-8859-1. 
REM Mesmo assim, n�o se compara com UTF-8! O Linux, por default, usa o charset UTF-8. 
REM Eclipse herda o charset de Windows por default. 
REM "chcp 1252>nul" � necess�rio para evitar que DOS n�o reconhece acentos Portugueses no caminho 
REM "Integra��o Fornecedor - Portal Cronos" em alguns ou talvez at� em todos os servidores:

chcp 1252>nul

if %tipoInstalacao% == 2 (
   set idFornecedor=1995
   goto PularPerguntaIdFornecedor
)

if "%1"=="m" (
    goto PerguntaIdMonitorador
) else if "%1"=="i" (
    goto PerguntaIdFornecedor
) else (
    echo.
    ECHO Erro: apenas os tipos de instala��o ^"i^" e ^"m^" est�o permitidos!
    echo.

    echo MSGBOX "Erro: apenas os tipos de instala��o ""i"" e ""m"" est�o permitidos!" > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
REM Fechar o script chamador tamb�m: 
    exit
)

:PerguntaIdMonitorador
echo.
echo.
echo ID da empresa fornecedora:
echo.
echo -1 = Monitorador do Servidor de Aplica��o
echo -2 = Monitorador do Servidor de Banco
echo -3 = Monitorador do Servidor de Banco de Conting�ncia
echo  C = Cancelar instala��o (com rollback)
echo.

SET /P idFornecedor=Digite -1, -2, -3 ou C + a tecla ^<Enter^>: 
IF "%idFornecedor%"=="C" GOTO CancelarInstalacao
IF "%idFornecedor%"=="c" GOTO CancelarInstalacao
IF "%idFornecedor%"=="" GOTO ErroIdMonitorador
IF "%idFornecedor%"=="-1" GOTO PularErroIdMonitorador 
IF "%idFornecedor%"=="-2" GOTO PularErroIdMonitorador 
IF "%idFornecedor%"=="-3" GOTO PularErroIdMonitorador 
echo MSGBOX "Erro: Op��o inv�lida!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
REM SET /P doesn't change the content of a variable, if the user doesn't enter text:
SET "idFornecedor="
cls
goto PerguntaIdMonitorador
:ErroIdMonitorador
echo MSGBOX "Erro: ID do fornecedor n�o informado!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
cls
goto PerguntaIdMonitorador
:PularErroIdMonitorador


:PerguntaIdFornecedor
echo.
echo.
echo Entre em contato com o setor Desenvolvimento do Portal Cronos 
echo para obter o ID da empresa fornecedora:
echo.

SET /P idFornecedor=Digite este ID ou a tecla C (= Cancelar) + a tecla ^<Enter^>: 
IF "%idFornecedor%"=="C" GOTO CancelarInstalacao
IF "%idFornecedor%"=="c" GOTO CancelarInstalacao
IF "%idFornecedor%"=="" GOTO ErroIdFornecedor
GOTO PularErroIdFornecedor
:ErroIdFornecedor
echo MSGBOX "Erro: ID do fornecedor n�o informado!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
cls
goto PerguntaIdFornecedor
:PularErroIdFornecedor


:PularPerguntaIdFornecedor


REM Alternativo antigo: 
REM 
REM if %idFornecedor% == 30 (
REM     goto PathProlac
REM ) else (
REM     goto PathOutros
REM )


call "Integra��o Fornecedor - Portal Cronos\bin\Inicializacoes.bat"
call "Integra��o Fornecedor - Portal Cronos\bin\Versao.bat"
call "Integra��o Fornecedor - Portal Cronos\bin\CaminhoJRE.bat" Instalador.log Instalador %idFornecedor% %2



set arquivoLog="Instalador.log"
set tamanhoArqLog=0

FOR /F "usebackq" %%A IN ('%arquivoLog%') DO set tamanhoArqLog=%%~zA

if %tamanhoArqLog% GTR 0 (
    copy /Y Instalador.log C:\temp\Instalador.log
    start notepad C:\temp\Instalador.log
    del /f /q C:\temp\"Instalador do Integrador Fornecedores - Portal Cronos.*.exe"

    echo.
    echo          A instala��o falhou! Foi feito rollback da instala��o!
    echo.
    
    echo MSGBOX "A instala��o falhou! Foi feito rollback da instala��o!" > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q

REM O seguinte consegue remover todos os arquivos no diret�rio "Arquivos de Programas PC",
REM at� este arquivo .bat, por�m n�o consegue remover o diret�rio "Arquivos de Programas PC" :
REM ????????? Funcionou quando usei vbs acima imediatamente antes disso,
REM e quando movi todos os outros camandos DOS mais para cima para antes do echo !!!!!!!!!
    cd\
    rmdir /s /q "Arquivos de Programas PC"
REM Abortar a instala��o, ent�o fechar este arquivo .bat atual 
REM e tamb�m fechar o arquivo .bat chamador (Instalador_Integrador.bat e Instalador_Monitorador.bat):     
    exit
) else (
    if exist Instalador.log del /f /q Instalador.log 
)


ENDLOCAL
REM /B para n�o fechar os scripts chamadores (Instalador_Integrador.bat e Instalador_Monitorador.bat):  
exit /B 0


goto PularCancelarInstalacao
:CancelarInstalacao

REM Foi testado via teste integrado completo que o seguinte funciona, mesmo que deixar o arquivo selecionado 
REM ap�s o duplo-clique para executar a instala��o:
REM Foi testado que o seguinte funciona tamb�m no caso que o diret�rio for C:\Temp\ ao inv�s de C:\temp\:
del /f /q C:\temp\"Instalador do Integrador Fornecedores - Portal Cronos.*.exe"

REM N�o fazer cls aqui, para poder visualizar eventuais erros
echo.
echo          Rollback da instala��o concluida!
echo.

echo MSGBOX "Rollback da instala��o concluida!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q

REM ================ Remover diret�rio "Arquivos de Programas PC": ========================================

REM O seguinte consegue remover todos os arquivos no diret�rio "Arquivos de Programas PC",
REM at� este arquivo .bat, por�m n�o consegue remover o diret�rio "Arquivos de Programas PC" :
REM ????????? Funcionou quando usei vbs acima antes disso !!!!!!!!!
cd\
rmdir /s /q "Arquivos de Programas PC"
REM Neste caso tamb�m fechar os scripts chamadores (Instalador_Integrador.bat ou Instalador_Monitorador.bat):
exit
:PularCancelarInstalacao



