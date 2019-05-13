cls
@echo off

REM Windows, por default, usa o charset WINDOWS-1252 (ou outra varia��o, dependendo da lingua), 
REM que � uma extens�o do ISO-8859-1. 
REM Mesmo assim, n�o se compara com UTF-8! O Linux, por default, usa o charset UTF-8. 
REM Eclipse herda o charset de Windows por default. 
REM "chcp 1252>nul" � necess�rio para evitar que DOS n�o reconhece acentos Portugueses no caminho 
REM "Integra��o Fornecedor - Portal Cronos" em alguns ou talvez at� em todos os servidores:

chcp 1252>nul

echo.
echo Favor entrar em contato com o setor Desenvolvimento do Portal Cronos para obter o ID da empresa fornecedora.
echo.

SET /P idFornecedor=Favor digitar o ID da empresa fornecedora: 
IF "%idFornecedor%"=="" GOTO ErroIdFornecedor
GOTO PularErro
:ErroIdFornecedor
echo MSGBOX "Erro: ID do fornecedor n�o informado! Instala��o deste fornecedor n�o conclu�da!!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
REM Fechar o script chamador tamb�m: 
exit
:PularErro


SET /P nmFornecedor=Favor digitar um nome bem curto da empresa fornecedora: 
IF "%nmFornecedor%"=="" GOTO ErroNmFornecedor
GOTO PularErroNmFornecedor
:ErroNmFornecedor
echo MSGBOX "Erro: nome do fornecedor n�o informado! Instala��o deste fornecedor n�o conclu�da!!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
REM Fechar o script chamador tamb�m: 
exit
:PularErroNmFornecedor

SCHTASKS /Create /TN "Integra��o Portal Cronos - %nmFornecedor%" /XML "C:/Arquivos de Programas PC/FornecedorAdicionalNuvem.Windows2008_R2.TaskSchedule.xml"
SCHTASKS /Run /TN "Integra��o Portal Cronos - %nmFornecedor%"

exit

:FIM
