* @ValidationCode : MjoxNjU4NzY2MTk4OkNwMTI1MjoxNzI0OTIwNDY1ODAwOmJzYXVyYXZrdW1hcjotMTotMTowOjA6ZmFsc2U6Ti9BOlIyMl9TUDI3LjA6LTE6LTE=
* @ValidationInfo : Timestamp         : 29 Aug 2024 16:34:25
* @ValidationInfo : Encoding          : Cp1252
* @ValidationInfo : User Name         : bsauravkumar
* @ValidationInfo : Nb tests success  : N/A
* @ValidationInfo : Nb tests failure  : N/A
* @ValidationInfo : Rating            : N/A
* @ValidationInfo : Coverage          : N/A
* @ValidationInfo : Strict flag       : N/A
* @ValidationInfo : Bypass GateKeeper : false
* @ValidationInfo : Compiler Version  : R22_SP27.0
* @ValidationInfo : Copyright Temenos Headquarters SA 1993-2021. All rights reserved.
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
