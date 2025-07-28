PROGRAM TEST.SUB
    
    $INSERT I_F.ACCOUNT
    
    LOGFILE.ID = ''
    Y.FILE = ''
    LOGFILE.ID = 'TEST.SUB'
    OPENSEQ "T24.TRACE", LOGFILE.ID TO Y.FILE ELSE
        CREATE Y.FILE ELSE
            TEXT = "COULD NOT CREATE FILE ":LOGFILE.ID
            CRT TEXT
        END
        TRACE.MSG = 'Inside TEST.SUB'
        WRITESEQ TRACE.MSG TO Y.FILE ELSE NULL
    END
    
    PUTENV("OFS_SOURCE=GCS")
    CALL JF.INITIALISE.CONNECTION
    CALL LOAD.COMPANY('GB0010001')
    CALL EB.TRANS('START', '')
    
    rAccount = ''
    rAccount<AC.CUSTOMER> = '100100'
    rAccount<AC.CATEGORY> = '1001'
    rAccount<AC.CURRENCY> = 'USD'
    ofsMessage = ''
    CALL OFS.BUILD.RECORD('ACCOUNT', 'I', 'PROCESS', 'ACCOUNT,', '', '0', '', rAccount, ofsMessage)
    
    TRACE.MSG = 'OFS request is ' : ofsMessage
    WRITESEQ TRACE.MSG TO Y.FILE ELSE NULL
    
    CALL OFS.GLOBUS.MANAGER('AC.OFS', ofsMessage)
    
    TRACE.MSG = 'OFS response is ' : ofsMessage
    WRITESEQ TRACE.MSG TO Y.FILE ELSE NULL
    
    CALL EB.TRANS('END', '')
    
    CLOSESEQ Y.FILE
    
RETURN
END
