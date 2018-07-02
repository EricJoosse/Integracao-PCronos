cls
@echo off

REM Exemplo para teste: 13.92.86.174

SET /P enderecoIP=Favor digitar o endere�o IP do fornecedor: 
IF "%enderecoIP%"=="" GOTO ErroEnderecoIP
GOTO PularErroEnderecoIP
:ErroEnderecoIP
echo MSGBOX "Erro: endere�o IP do fornecedor n�o informado!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
REM Fechar o script chamador tamb�m: 
exit
:PularErroEnderecoIP

echo.
echo Exemplos: porta 80, ou porta 3389 das �reas de Trabalho
echo.

SET /P portaIP=Favor digitar a porta IP do fornecedor: 
IF "%portaIP%"=="" GOTO ErroPortaIP
GOTO PularPortaIP
:ErroPortaIP
echo MSGBOX "Erro: porta IP do fornecedor n�o informada!" > %temp%\TEMPmessage.vbs
call %temp%\TEMPmessage.vbs
del %temp%\TEMPmessage.vbs /f /q
REM Fechar o script chamador tamb�m: 
exit
:PularPortaIP


cd\
cd PsPing

psping -w 0 %enderecoIP%:%portaIP%

echo.
echo Se a porta 80 n�o funcionar, usa porta 3389 das �reas de Trabalho:

pause
