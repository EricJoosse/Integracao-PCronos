SETLOCAL

REM Windows, por default, usa o charset WINDOWS-1252 (ou outra varia��o, dependendo da lingua), 
REM que � uma extens�o do ISO-8859-1. 
REM Mesmo assim, n�o se compara com UTF-8! O Linux, por default, usa o charset UTF-8. 
REM Eclipse herda o charset de Windows por default. 
REM "chcp 1252>nul" � necess�rio para evitar que DOS n�o reconhece acentos Portugueses no caminho 
REM "Integra��o Fornecedor - Portal Cronos" em alguns ou talvez at� em todos os servidores:

chcp 1252>nul

echo.
echo ComponenteTestador.bat entrado: 
echo Param 0 = %0
echo Param 1 = %1
echo Param 2 = %2
echo Param 3 = %3
echo Vers�o = %versaoIntegrador%

REM pause

cd\

REM Se testar dentro do Eclipse ao inv�s de nos servidores:
if exist C:/PCronos/"Integra��o Fornecedor - Portal Cronos"/Instalador/Windows/"Arquivos de Programas PC"/Instalador.bat (
  cd PCronos
  cd "Integra��o Fornecedor - Portal Cronos"
  cd Instalador
  cd Windows
  cd "Arquivos de Programas PC"
  cd "Integra��o Fornecedor - Portal Cronos"
) else (  
  cd "Arquivos de Programas PC"
  cd "Integra��o Fornecedor - Portal Cronos"
)

REM "pwd" em Linux = "%cd% em DOS:
echo %cd%

call CaminhoJRE.bat %1 %2 %3
