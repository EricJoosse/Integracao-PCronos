REM Abertura dos arquivos .properties via este arquivo .bat
REM =======================================================
REM
REM Motivos: 
REM --------
REM  1. Para for�ar o uso do Notepad:
REM     isso elimina a necessidade para associar arquivos com extens�o ".properties" 
REM     para todos os usu�rios de Windows existentes e novos usu�rios adicionados no futuro.
REM
REM  2. Para for�ar o usu�rio se conectar como Administrador ou para usar a op��o "Executar como Administrador": 
REM     isso � necess�rio para o salvamento dos arquivos poder funcionaar. 



cls
@echo off



REM Windows, por default, usa o charset WINDOWS-1252 (ou outra varia��o, dependendo da lingua), 
REM que � uma extens�o do ISO-8859-1. 
REM Mesmo assim, n�o se compara com UTF-8! O Linux, por default, usa o charset UTF-8. 
REM Eclipse herda o charset de Windows por default. 
REM "chcp 1252>nul" � necess�rio para evitar que DOS n�o reconhece acentos Portugueses no caminho 
REM "Integra��o Fornecedor - Portal Cronos" em alguns ou talvez at� em todos os servidores:

chcp 1252>nul



cd\
cd "Arquivos de Programas PC"

call "Integra��o Fornecedor - Portal Cronos\bin\VerificarUsuarioAdministrador.bat"

start notepad "Integra��o Fornecedor - Portal Cronos/conf/Integra��o APS - Portal Cronos.%1.properties"

exit


:FIM
