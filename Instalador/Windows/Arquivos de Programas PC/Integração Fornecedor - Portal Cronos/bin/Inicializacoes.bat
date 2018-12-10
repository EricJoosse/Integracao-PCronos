REM By default, variables are global to your entire command prompt session. 
REM Call the SETLOCAL command to make variables local to the scope of your script. 
REM After calling SETLOCAL, any variable assignments revert upon calling ENDLOCAL, 
REM calling EXIT, or when execution reaches the end of file (EOF) in your script.
REM The setlocal command sets the ERRORLEVEL variable to 1.
REM "SETLOCAL" � necess�rio aqui para poder usar ERRORLEVEL corretamente:   

SETLOCAL

REM Me esqueci para que serve "chcp 1252>nul", por�m provavelmente isso � necess�rio 
REM para evitar que DOS n�o reconhece acentos Portugeses no caminho 
REM "Integra��o Fornecedor - Portal Cronos" em alguns ou talvez at� em todos os servidores:

chcp 1252>nul

