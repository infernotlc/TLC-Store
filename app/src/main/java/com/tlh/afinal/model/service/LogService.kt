package com.tlh.afinal.model.service


interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}