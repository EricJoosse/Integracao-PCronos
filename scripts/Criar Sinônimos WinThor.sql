Conectar no banco Oracle como usu�rio inga ou usu�rio SYS ou outro usu�rio DBA e rodar o seguinte:

CREATE PUBLIC SYNONYM PCCLIENT FOR inga.PCCLIENT;
CREATE PUBLIC SYNONYM PCPRODUT FOR inga.PCPRODUT;
CREATE PUBLIC SYNONYM PCEST FOR inga.PCEST;
CREATE PUBLIC SYNONYM PCTABPR FOR inga.PCTABPR;
CREATE PUBLIC SYNONYM PCPLPAG FOR inga.PCPLPAG;
CREATE PUBLIC SYNONYM PCPRACA FOR inga.PCPRACA;
CREATE PUBLIC SYNONYM PCSECAO FOR inga.PCSECAO;

Depois disso conectar no banco Oracle como usu�rio "cronos" e testar o seguinte:
select * from PCCLIENT;
