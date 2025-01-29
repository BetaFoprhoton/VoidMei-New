package org.matrixsukhoi.voidmei.parser;

import org.matrixsukhoi.voidmei.VoidMeiMainKt;
import org.matrixsukhoi.voidmei.prog.*;

public class FlightAnalyzer {
	public int engineType;
	public String type;
	public static final int maxAltStage = 256;
	public double[] time = new double[maxAltStage];// 从第零层开始
	public int[] power = new int[maxAltStage];// 从第一层开始
	public int[] thrust = new int[maxAltStage];
	public int[] eff = new int[maxAltStage];
	public double[] sep = new double[maxAltStage];
	public int initaltStage;
	public int curaltStage;
	boolean isInformation;

	Service xs;
	int count;

	void init(int stage, Service st) {
		// VoidMeiMain.debugPrint("analyzer初始化了");
		xs = st;
		count = 1;
		isInformation = Boolean.parseBoolean(st.c.getconfig("enableAltInformation"));
		type = xs.sIndic.type;
		engineType = xs.iEngType;
		initaltStage = stage;
		curaltStage = initaltStage;
		time[curaltStage] = (xs.elapsedTime / 1000f);
		power[curaltStage] = xs.iTotalHp;
		thrust[curaltStage] = xs.iTotalThr;
		eff[curaltStage] = xs.iTotalHpEff;
		sep[curaltStage] = xs.SEP;
		// VoidMeiMain.debugPrint("已经记录stage"+curaltStage+"时间戳"+time[curaltStage]+"功率"+power[curaltStage]+"实功率"+eff[curaltStage]+"SEP"+sep[curaltStage]);
	}

	void analyze(int stage) {
		engineType = xs.iEngType;
		if (stage == curaltStage + 1) {
			eff[curaltStage] = eff[curaltStage] / count;
			sep[curaltStage] = sep[curaltStage] / (count * 9.78f);
			// VoidMeiMain.debugPrint("已经记录stage"+curaltStage+"时间戳"+time[curaltStage]+"功率"+power[curaltStage]+"推力"+thrust[curaltStage]+"实功率"+eff[curaltStage]+"SEP"+sep[curaltStage]);
			curaltStage++;

			time[curaltStage] = (xs.elapsedTime / 1000f);
			power[curaltStage] = xs.iTotalHp;
			thrust[curaltStage] = xs.iTotalThr;
			eff[curaltStage] = xs.iTotalHpEff;
			sep[curaltStage] = xs.SEP;
			count = 1;
			if (isInformation)
				Controller.notification(Lang.fA1 + stage * 100 + Lang.fA2 + (int) time[curaltStage] + Lang.fA3
								+ (int) ((stage - initaltStage) * 1000 / time[curaltStage]) / 10.0f + Lang.fA4);
		} else {
			eff[curaltStage] = (eff[curaltStage] + xs.iTotalHpEff);
			sep[curaltStage] = (sep[curaltStage] + xs.SEP);
			count++;
		}
	}

	// 获得速度区间
	// 0表示非法
	// 0 - 2560km/h
	public static final int maxIASStage = 256;
	public int[] roll_rate = new int[maxIASStage];
	public int[] roll_alr = new int[maxIASStage];

	public double[] turnLoad = new double[maxIASStage];
	public int[] turn_elev = new int[maxIASStage];

	public double[] sepLoss = new double[maxIASStage];

	// 获得速度阶段
	public int getSpeedStage(double ias) {
		return (int)Math.round(ias / 10.0f);
	}

	// 使用舵面辅助判断
	public void updateEMChart(double ias, double g_load, int wx, double sep, int abs_elev, int abs_alr) {
		int stage = getSpeedStage(ias);
		if (stage >= 0 && stage < maxIASStage) {
			// 如果当前roll_rate比记录值高则更新
			// 合法roll_rate校验问题，检查两边线性插值，或是多数据叠加才能生效?
			// 如果当前舵面值大于等于则记录
			if (abs_alr > 5 && wx > 10 && abs_alr >= roll_alr[stage]) {
				if (wx > roll_rate[stage]) {
					roll_alr[stage] = abs_alr;

					if (isInformation && (wx - roll_rate[stage] > 40))
						Controller.notification(
								Lang.fA_roll1 + stage * 10 + Lang.fA_roll2 + wx + Lang.fA_roll3);

					roll_rate[stage] = wx;
				}
			}

			if (g_load > 1.0f && sep < 5 && abs_elev >= turn_elev[stage]) {

//				if (g_load > turn_load[stage] ) {
					turn_elev[stage] = abs_elev;
					if (isInformation && (g_load -  turnLoad[stage] > 3.0f))
						Controller.notification(Lang.fA_turn1 + stage * 10 + Lang.fA_turn2 + String.format("%.1f", (turnLoad[stage] + g_load) / 2) + Lang.fA_turn3 + String.format("%.1f", (sepLoss[stage] + sep) / 2) + Lang.fA_turn4);
					turnLoad[stage] = (turnLoad[stage] + g_load) / 2;
					sepLoss[stage] =  (sepLoss[stage] + sep) / 2;
//				}
				// showAllEMChart();
			}

		}

	}

	public int getNoZerosNum(int[] arr) {
		int ret = 0;
        for (int j : arr) {
            if (j != 0)
                ret++;
        }
		return ret;
	}
	
	public int getNoZerosNum(double[] arr) {
		int ret = 0;
        for (double v : arr) {
            if (v != 0)
                ret++;
        }
		return ret;
	}

	public void removeZeroes(double[] x, double[] y, int[] oy) {
		int j = 0;
		for (int i = 0; i < oy.length; i++) {
			if (oy[i] != 0) {
				x[j] = i * 10.0;
				y[j] = (double) (oy[i-1] + oy[i] + oy[i+1])/3;
				j++;
			}
		}
	}
	
	public void removeZeroes(double[] x, double[] y, double[] oy) {
		int j = 0;
		for (int i = 1; i < oy.length-1; i++) {
			if (oy[i] != 0) {
				x[j] = i * 10.0;
				y[j] = (double) (oy[i-1] + oy[i] + oy[i+1])/3;
				j++;
			}
		}
	}
	
	public void removeRollRatesZeroes(double[] ias, double[] wx) {
//		int j = 0;
		removeZeroes(ias, wx, roll_rate);
	}
	
	public void removeLoadZeroes(double[] ias, double[] g, double[] seploss) {
		int j = 0;
		for (int i = 1; i < turnLoad.length - 1; i++) {
			if (turnLoad[i] != 0) {
				ias[j] = i * 10.0;
//				g[j] = (double) turn_load[i];
//				seploss[j] = (double) sep_loss[i];
				g[j] = (double)  (turnLoad[i-1] + turnLoad[i] + turnLoad[i+1]) / 3;
				seploss[j] =  (double)  (sepLoss[i-1] + sepLoss[i] + sepLoss[i+1]) / 3;
				j++; 
			}
		}
	}

	public void showAllEMChart() {
		StringBuilder builder = new StringBuilder("Roll rate: ");
		for (int i = 0; i < 256; i++) {
            builder.append(roll_rate[i]).append(", ");
		}
		VoidMeiMainKt.getLogger().info(builder.toString());

		// VoidMeiMain.debugPrint("turn:");
		// for(int i = 0; i < 256; i++){
		// System.out.print(turn_load[i]+",");
		// }
		// for(int i = 0; i < 256; i++){
		// System.out.print(sep_loss[i]+",");
		// }
	}

}