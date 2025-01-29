package org.matrixsukhoi.voidmei.parser;

import static org.matrixsukhoi.voidmei.parser.StringHelper.*;

public class State {
	public String valid;
	public boolean flag;
//	public boolean engineAlive = false;

	public static final int maxEngNum = 8;
	public int engineNum;
//	public int isEngineJet;
	public int aileron;
	public int elevator;
	public int rudder;
	public int flaps;
	public int gear;
	public int TAS;
	public int IAS;
	public double M;
	public double AoA;
	public double heightm;
	public double AoS;
	public double Ny;
	public double Vy;
	public double Wx;
	public int throttle;
	public int RPMthrottle;
	public int radiator;
	public int oilradiator;
	public int mixture;
	public int compressorstage;
	public int magenato;
	public double[] power;
	public int RPM;
	public double manifoldpressure;
	public double watertemp;
	public double oiltemp;
	public double mfuel;
	public double mfuel_1;
	public double mfuel0;
	public double[] pitch;
	public int[] thrust;
	public double[] efficiency;
	public int airbrake;
	public double totalThr;
	public int[] throttles;
	// 临时变量

	public void init() {
		// System.out.println("state初始化了");
		valid = "false";
		throttles = new int[maxEngNum];
		power = new double[maxEngNum];
		pitch = new double[maxEngNum];
		thrust = new int[maxEngNum];
		efficiency = new double[maxEngNum];
		engineNum = 0;
//		isEngineJet = -1;
//		engineAlive = false;
		airbrake = 0;
	}

	public void getEngineNumber(String buffer) {
		for (int i = 0; i < maxEngNum; i++) {
			thrust[i] = getDataInt(extractSubstring(buffer, "thrust " + i));
			if (thrust[i] != -65535)
				engineNum ++;
		}
//		thrust[0] = StringHelper.getDataInt(StringHelper.getString(buffer, "thrust 1"));
//		if (thrust[0] != -65535)
//			engineNum++;
//		thrust[1] = StringHelper.getDataInt(StringHelper.getString(buffer, "thrust 2"));
//		if (thrust[1] != -65535)
//			engineNum++;
//		thrust[2] = StringHelper.getDataInt(StringHelper.getString(buffer, "thrust 3"));
//		if (thrust[2] != -65535)
//			engineNum++;
//		thrust[3] = StringHelper.getDataInt(StringHelper.getString(buffer, "thrust 4"));
//		if (thrust[3] != -65535)
//			engineNum++;
//		thrust[4] = StringHelper.getDataInt(StringHelper.getString(buffer, "thrust 5"));
//		if (thrust[4] != -65535)
//			engineNum++;
	}

	public int update(String buffer) {
		int i;
		valid = extractSubstring(buffer, "valid");
		// System.out.println(valid);
		if (valid == null){
			return -1;
		}
		if (valid.equals("true")) {
			// 无异常的
			flag = true;
//			if (engineNum == 0) {
//				getEngNum(buffer);
//			}
//			 System.out.println(engineNum);
			aileron = getDataInt(extractSubstring(buffer, "aileron"));
			elevator = getDataInt(extractSubstring(buffer, "elevator"));
			rudder = getDataInt(extractSubstring(buffer, "rudder"));
			flaps = getDataInt(extractSubstring(buffer, "flaps"));
			airbrake = getDataInt(extractSubstring(buffer, "airbrake"));
			gear = getDataInt(extractSubstring(buffer, "gear"));
			TAS = getDataInt(extractSubstring(buffer, "TAS"));
			IAS = getDataInt(extractSubstring(buffer, "IAS"));
			M = getDataFloat(extractSubstring(buffer, "\"M\""));
			heightm = getDataFloat(extractSubstring(buffer, "H, m"));
			AoA = getDataFloat(extractSubstring(buffer, "AoA"));
			AoS = getDataFloat(extractSubstring(buffer, "AoS"));
			Ny = getDataFloat(extractSubstring(buffer, "Ny"));
			Vy = getDataFloat(extractSubstring(buffer, "Vy"));
			Wx = getDataFloat(extractSubstring(buffer, "Wx"));
			throttle = getDataInt(extractSubstring(buffer, "throttle"));
			RPMthrottle = getDataInt(extractSubstring(buffer, "RPM throttle"));
//			if (RPMthrottle == -65535)
//				RPMthrottle = 0;
			radiator = getDataInt(extractSubstring(buffer, "radiator"));
//			if (radiator == -65535)
//				radiator = 0;
			// oilradiator = StringHelper.getDataInt(StringHelper.getString(buffer, "oilraditor"));
//			power[0] = StringHelper.getDataFloat(StringHelper.getString(buffer, "power 1"));
			RPM = getDataInt(extractSubstring(buffer, "RPM 1"));

			manifoldpressure = getDataFloat(extractSubstring(buffer, "manifold"));

			mfuel = getDataFloat(extractSubstring(buffer, "Mfuel"));
			mfuel_1 = getDataFloat(extractSubstring(buffer, "Mfuel 1"));
			mfuel0 = getDataFloat(extractSubstring(buffer, "Mfuel0"));

			oiltemp = getDataFloat(extractSubstring(buffer, "oil temp"));
//			thrust[0] = StringHelper.getDataInt(StringHelper.getString(buffer, "thrust 1"));
//
//			efficiency[0] = StringHelper.getDataFloat(StringHelper.getString(buffer, "efficiency 1"));
			// engineNum = 1;
			mixture = getDataInt(extractSubstring(buffer, "mixture"));
			if (mixture == -65535)
				mixture = -1;
			compressorstage = getDataInt(extractSubstring(buffer, "compressor stage"));
			if (compressorstage == -65535)
				compressorstage = 0;

			magenato = getDataInt(extractSubstring(buffer, "magneto"));

			watertemp = getDataFloat(extractSubstring(buffer, "water temp"));
			
			double tmpThrust = 0;

			int totalEngineNum = 0;
			for (i = 0; i < maxEngNum; i++) {
				// System.out.println(engineType);
				throttles[i] = getDataInt(extractSubstring(buffer, "throttle " + (i+1)));
				power[i] = getDataFloat(extractSubstring(buffer, "power " + (i+1)));
//				if(power[i] == -65535)break;
				thrust[i] = getDataInt(extractSubstring(buffer, "thrust " + (i+1)));
				pitch[i] = getDataFloat(extractSubstring(buffer, "pitch " + (i+1)));
//				if(pitch[i] == -65535)break;
				efficiency[i] = getDataInt(extractSubstring(buffer, "efficiency " + (i+1)));
//				if(efficiency[i] == -65535)break;
				// System.out.println(pitch[0]);
//				System.out.println("thrust "+i+" "+thrust[i]);
				if(thrust[i] == -65535) break;
				
				tmpThrust += thrust[i];
				totalEngineNum += 1;
				
			}
			engineNum = totalEngineNum;
			totalThr = tmpThrust;
//			VoidMeiMain.debugPrint(String.format("引擎数量%d, 功率%.0f", engineNum, power[0]));
//			if (thrust[0] != 0){
//				engineAlive = true;
//			}
//			else{
//				engineAlive = false;
//			}
//			
//			else{
//				// 推力为空且
//			}
//			if (thrust[0] != 0 || Vy != 0)
//				engineAlive = true;
//			else{
//				// dead逻辑
//				if (gear >= 0 && IAS < 10 && Vy < 1)
//					engineAlive = false;				
//			}


//			if (pitch[0] <= 0 && efficiency[0] <= 0 && power[0] == 0 && thrust[0] != 0) {
//				isEngineJet = 1;
//			} else
//				isEngineJet = 0;
			 
//			if(magenato >= 0){
//				isEngineJet = 0;
//			}
//			else{
//				isEngineJet = 1;
//			}
			
		} else {
			flag = false;
		}
		return 0;
	}
}