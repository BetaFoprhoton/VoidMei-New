package org.matrixsukhoi.voidmei.prog


data class FMParts(
    @JvmField
    var name: String? = "",

    @JvmField
    var sq: Double = 0.0,
    @JvmField
    var cdMin: Double = 0.0,

    @JvmField
    var cl0: Double = 0.0,

    @JvmField
    var clCritHigh: Double = 0.0,
    @JvmField
    var clCritLow: Double = 0.0,
    @JvmField
    var clAfterCrit: Double = 0.0,
    @JvmField
    var aoACritHigh: Double = 0.0,
    @JvmField
    var aoACritLow: Double = 0.0,

    @JvmField
    var lineClCoeff: Double = 0.0 // 翼展效率因数，影响诱导阻力，因数越大阻力越小
    // public double oswaldEff;
) {

}