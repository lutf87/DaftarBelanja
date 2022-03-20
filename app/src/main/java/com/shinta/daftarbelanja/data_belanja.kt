package com.shinta.daftarbelanja

class data_belanja {
    var key: String? = null
    var NM: String? = null
    var JML: String? =null

    constructor(){}

    constructor(NB: String?, JMLB: String?) {
        this.NM = NB
        this.JML = JMLB
    }
}