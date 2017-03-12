package com.github.wiljgriff.ethereumwallet.data.ethereum.icap

enum class IbanParam(paramString: String) {
    AMOUNT("amount"),
    LABEL("label"),
    UKNOWN("unknown");

    private object Static {
        internal var params: MutableMap<String, IbanParam> = HashMap()
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