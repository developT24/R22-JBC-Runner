PROGRAM MAIN.PRG
*-----------------------------------------------------------------------------
*
*-----------------------------------------------------------------------------
* Modification History :
*-----------------------------------------------------------------------------

*-----------------------------------------------------------------------------
    
    LOGFILE.ID = ''
    Y.FILE = ''
	LOGFILE.ID = 'MAIN.PRG'
    OPENSEQ "AA.TRACE", LOGFILE.ID TO Y.FILE ELSE
		CREATE Y.FILE ELSE
            TEXT = "COULD NOT CREATE FILE ":LOGFILE.ID
            CRT TEXT
        END
		TRACE.MSG = 'Writing Hello World!'
        WRITESEQ TRACE.MSG TO Y.FILE ELSE NULL
		CLOSESEQ Y.FILE
    END
	
	RETURN
    
END
