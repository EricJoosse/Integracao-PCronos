cls
@echo off

REM "chcp 1252>nul" � necess�rio para evitar que DOS n�o reconhece acentos Portugueses no caminho 
REM "Integra��o Fornecedor - Portal Cronos" em alguns ou talvez at� em todos os servidores:

chcp 1252>nul

cd\
cd "Arquivos de Programas PC"
cd "Integra��o Fornecedor - Portal Cronos"

call bin\Inicializacoes.bat
call bin\Versao.bat
call bin\CaminhoJRE.bat Job15a15minOfertamentoJava.log IntegracaoFornecedorCompleta

ENDLOCAL
exit


