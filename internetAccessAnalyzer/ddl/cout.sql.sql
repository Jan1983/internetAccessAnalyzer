/****** Skript für SelectTopNRows-Befehl aus SSMS  ******/

-- truncate table [IAA].[dbo].[PINGDATA]
SELECT COUNT(*) AS 'Verbindung vorhanden' FROM [IAA].[dbo].[PINGDATA] WHERE SUCCESSSTATE = 1
SELECT COUNT(*) AS 'Verbindung abgebrochen' FROM [IAA].[dbo].[PINGDATA] WHERE SUCCESSSTATE = 0