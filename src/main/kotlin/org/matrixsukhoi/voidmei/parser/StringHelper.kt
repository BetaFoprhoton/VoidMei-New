package org.matrixsukhoi.voidmei.parser

import java.lang.StringBuilder

const val I_INVALID = -65535
const val F_INVALID: Double = -65535.0

object StringHelper {
	@JvmStatic
	fun getStringBuilder(builder: StringBuilder, str: String, buffer: CharArray, bufferLen: Int) {
		var bix: Int
		var eix: Int
		bix = builder.lastIndexOf(str)

		if (bix >= 0) {
			eix = bix
			while (builder[eix] != ':') {
				eix ++
			}
			eix ++
			bix = eix + 1
			while (builder[eix] != ',' && builder[eix] != '}') {
				eix ++
				if (eix == builder.length + 1)
					break
			}
			builder.getChars(bix, eix, buffer, bufferLen)
		}
	}
	
	//getString method
	@JvmStatic
	fun extractSubstring(sourceString: String, keyString: String): String? {
		var bix: Int
		var eix: Int
		bix = sourceString.indexOf(keyString)
		return if (bix >= 0) {
			eix = bix
			while (sourceString[eix] != ':') {
				eix ++
			}
			eix ++
			bix = eix + 1
			while (sourceString[eix] != ',' && sourceString[eix] != '}') {
				eix ++
				if (eix == sourceString.length + 1)
					break
			}
			sourceString.substring(bix, eix)
		} else null
	}

	@JvmStatic
	fun getDataFloatC(cs: CharSequence?): Double {
		return cs?.toString()?.toDouble() ?: F_INVALID
	}

	@JvmStatic
	fun getDataIntC(cs: CharSequence?): Int {
		return if (cs != null) Integer.parseInt(cs.toString()) else I_INVALID
	}

	@JvmStatic
	fun getDataFloat(data: String?): Double {
		return data?.toDouble() ?: F_INVALID
	}

	@JvmStatic
	fun getDataInt(data: String?): Int {
		return data?.toInt() ?: I_INVALID
	}

}
