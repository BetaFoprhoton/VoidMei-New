package org.matrixsukhoi.voidmei.parser

class MapInfo {
	@JvmField
	var str: String = ""
	@JvmField
	var gridStepsX: Double = 0.0
	@JvmField
	var gridStepsY: Double = 0.0
	@JvmField
	var gridZeroX: Double = 0.0
	@JvmField
	var gridZeroY: Double = 0.0
	@JvmField
	var mapGeneration: Int = 0
	var mapMaxX: Double = 0.0
	@JvmField
	var mapMaxY: Double = 0.0
	@JvmField
	var mapMinX: Double = 0.0
	@JvmField
	var mapMinY: Double = 0.0
	@JvmField
	var cMapMaxSizeX: Double = 0.0
	@JvmField
	var cMapMaxSizeY: Double = 0.0

	@JvmField
	var inGameOffset: Double = 0.0 // 游戏内地图的偏移量
	@JvmField
	var tp: Zb = Zb()
	@JvmField
	var mapStage: Double = 0.0

	fun getMapInfoParserArray(t: String): Zb {
		var bix: Int
		var eix: Int
		val a = Zb()
		bix = str.indexOf(t)

		if (bix >= 0) {
			eix = bix
			while (str[eix] != ':') {
				eix++
			}
			eix ++
			bix = eix + 3
			while (str[eix] != ',') {
				eix++
				if (eix == str.length + 1)
					break
			}

			a.x = str.substring(bix, eix).toDouble()

			bix = eix + 2
			while (str[eix] != ']') {
				eix++
				if (eix == str.length + 1)
					break
			}
			eix -= 1

			a.y = str.substring(bix, eix).toDouble()

		}
		return a
	}

	fun update(newStr: String) {
		str = newStr
		// System.out.print(s);
		tp = getMapInfoParserArray("grid_steps")
		gridStepsX = tp.x
		gridStepsY = tp.y
		tp = getMapInfoParserArray("grid_zero")
		gridZeroX = tp.x
		gridZeroY = tp.y
		tp = getMapInfoParserArray("map_max")
		mapMaxX = tp.x
		mapMaxY = tp.y
		tp = getMapInfoParserArray("map_min")
		mapMinX = tp.x
		mapMinY = tp.y
		cMapMaxSizeX = mapMaxX - mapMinX
		cMapMaxSizeY = mapMaxY - mapMinY
		inGameOffset = ((gridZeroY - gridZeroX) - (mapMaxX + mapMaxY)) / (gridStepsX + gridStepsY)
		mapStage = (mapMaxX + mapMaxY) * 2 / (gridStepsX + gridStepsY)

		// VoidMeiMain.debugPrint("ingame mapinfo offset:" + inGameOffset + "map stage: " + mapStage);
	}
}