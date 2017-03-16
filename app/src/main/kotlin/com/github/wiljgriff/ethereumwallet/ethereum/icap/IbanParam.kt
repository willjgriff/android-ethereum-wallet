package com.github.wiljgriff.ethereumwallet.ethereum.icap

import java.util.*

enum class IbanParam(paramString: String) {
    AMOUNT("amount"),
    LABEL("label"),
    UKNOWN("unknown");

    private object Static {
        val params: MutableMap<String, IbanParam> = HashMap()
    }

    init {
        Static.params.put(paramString, this)
    }

    companion object {
        fun fromString(paramString: String): IbanParam {
            return Static.params.get(paramString) ?: UKNOWN
        }
    }
}