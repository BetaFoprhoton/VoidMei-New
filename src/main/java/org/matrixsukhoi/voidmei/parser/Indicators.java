package org.matrixsukhoi.voidmei.parser;

import org.matrixsukhoi.voidmei.prog.Service;

import java.util.Objects;

import static org.matrixsukhoi.voidmei.parser.StringHelper.extractSubstring;
import static org.matrixsukhoi.voidmei.parser.StringHelper.getDataFloat;

public class Indicators {
	public String valid;
	public String type;
	public boolean isdummyplane=false;
	public String stype;
	public boolean flag;
//	public boolean fuelpressure;
	public double speed;
	public double pedals;
	public double stick_elevator;
	public double stick_ailerons;
	public double altitude_hour;
	public double altitude_min;
	public double altitude_10k;
	public double bank;
	public double turn;
	public double compass;
	public double clock_hour;
	public double clock_min;
	public double clock_sec;
	public double manifold_pressure;
	public double rpm;
	public double oil_pressure;
	public double water_temperature;
	public double engine_temperature;
	public double mixture;
	public double[] fuel;
	public double fuel_pressure;
	public double oxygen;
	public double gears_lamp;
	public double flaps;
	public double trimmer;
	public double throttle;
	public double weapon1;
	public double weapon2;
	public double weapon3;
	public double prop_pitch_hour;
	public double prop_pitch_min;
	public double ammo_counter1;
	public double ammo_counter2;
	public double ammo_counter3;
	public double oilTemp;
	public double waterTemp;
	public int fuelNumber;
	public double vario;
	public double aviaHorizonPitch;
	public double aviaHorizonRoll;
	public double wSweepIndicator;
	public double radioAltitude;
	private String army;


	public void init() {
		//VoidMeiMain.debugPrint("indicator初始化了");
		valid = Service.nastring;
		fuelNumber = 0;
		fuel = new double[5];
		flag = false;
//		fuelpressure=false;
	}
	
	public void update(String buf) {
		valid = extractSubstring(buf, "valid");
		army = extractSubstring(buf, "army");
        if (army != null && valid != null && valid.equals("true") && !army.equals("tank")) {
            flag = true;
            type = Objects.requireNonNull(extractSubstring(buf, "type")).toUpperCase();

            if (!type.isEmpty()) {
                type = type.substring(1, type.length() - 1);
                //判定是否dummyplane
                //if(type.equals("DUMMY_PLANE"))isdummyplane=true;
                //else isdummyplane=false;
                if (type.length() > 9) stype = type.substring(0, 8);
                else stype = type;

            }

            speed = getDataFloat(extractSubstring(buf, "speed"));
            pedals = getDataFloat(extractSubstring(buf, "pedals"));
            stick_elevator = getDataFloat(extractSubstring(buf, "stick_elevator"));
            stick_ailerons = getDataFloat(extractSubstring(buf, "stick_ailerons"));
            altitude_hour = getDataFloat(extractSubstring(buf, "altitude_hour"));
            altitude_min = getDataFloat(extractSubstring(buf, "altitude_min"));
            ;
            altitude_10k = getDataFloat(extractSubstring(buf, "altitude_10k"));
            bank = getDataFloat(extractSubstring(buf, "bank"));
            turn = getDataFloat(extractSubstring(buf, "turn"));
            compass = getDataFloat(extractSubstring(buf, "compass"));
            clock_hour = getDataFloat(extractSubstring(buf, "clock_hour"));
            clock_min = getDataFloat(extractSubstring(buf, "clock_min"));
            clock_sec = getDataFloat(extractSubstring(buf, "clock_sec"));
            manifold_pressure = getDataFloat(extractSubstring(buf, "manifold_pressure"));
            rpm = getDataFloat(extractSubstring(buf, "rpm"));
            wSweepIndicator = getDataFloat(extractSubstring(buf, "wing_sweep_indicator"));
//			VoidMeiMain.debugPrint(wsweep_indicator);
            oil_pressure = getDataFloat(extractSubstring(buf, "oil_pressure"));
//			water_temperature=StringHelper.getDatadouble(StringHelper.getString(buf, "water_temperature"));
            engine_temperature = getDataFloat(extractSubstring(buf, "head_temperature"));
            mixture = getDataFloat(extractSubstring(buf, "mixture"));

            // 防止读到油压
            fuel[0] = getDataFloat(extractSubstring(buf, "\"fuel\""));
            fuelNumber = 1;
            for (int i = 1; i < 5; i++) {
                fuel[i] = getDataFloat(extractSubstring(buf, "fuel" + i));
                if (fuel[i] == -65535) fuel[i] = 0;
                else fuelNumber += 1;
            }
//			fuel[0]=StringHelper.getDatadouble(StringHelper.getString(buf, "fuel1"));
//			if (fuel[0] == -65535){
//				fuel[0] = StringHelper.getDatadouble(StringHelper.getString(buf, "fuel"));
//				if
//			}
            aviaHorizonPitch = getDataFloat(extractSubstring(buf, "aviahorizon_pitch"));
            aviaHorizonRoll = getDataFloat(extractSubstring(buf, "aviahorizon_roll"));
            radioAltitude = getDataFloat(extractSubstring(buf, "radio_altitude"));
            oilTemp = getDataFloat(extractSubstring(buf, "oil_temperature"));
            waterTemp = getDataFloat(extractSubstring(buf, "water_temperature"));
            if (fuel[0] == -65535) {
                fuel[0] = 0;
//				fuel[0]=StringHelper.getDatadouble(StringHelper.getString(buf, "fuel_pressure"))*10;
//				fuelpressure=true;
            }
//			else{
//				fuelpressure=false;
//			}
//			fuelpressure=false;
//			fuel[1]=StringHelper.getDataFloat(StringHelper.getString(buf, "fuel2"));
//			fuel[2]=StringHelper.getDataFloat(StringHelper.getString(buf, "fuel3"));
//			fuel[3]=StringHelper.getDataFloat(StringHelper.getString(buf, "fuel4"));
//			if(fuelnum==0){
//				if(fuel[0]!=-65535)fuelnum=fuelnum+1;
//				if(fuel[1]!=-65535)fuelnum=fuelnum+1;
//				if(fuel[2]!=-65535)fuelnum=fuelnum+1;
//				if(fuel[3]!=-65535)fuelnum=fuelnum+1;
//
//			}

            fuel_pressure = getDataFloat(extractSubstring(buf, "fuel_pressure"));
            oxygen = getDataFloat(extractSubstring(buf, "oxygen"));
            gears_lamp = getDataFloat(extractSubstring(buf, "gears_lamp"));
            flaps = getDataFloat(extractSubstring(buf, "flaps"));
            vario = getDataFloat(extractSubstring(buf, "vario"));
            trimmer = getDataFloat(extractSubstring(buf, "trimmer"));
            throttle = getDataFloat(extractSubstring(buf, "throttle"));
            weapon1 = getDataFloat(extractSubstring(buf, "weapon1"));
            weapon2 = getDataFloat(extractSubstring(buf, "weapon2"));
            weapon3 = getDataFloat(extractSubstring(buf, "weapon3"));
            prop_pitch_hour = getDataFloat(extractSubstring(buf, "prop_pitch_hour"));
            prop_pitch_min = getDataFloat(extractSubstring(buf, "prop_pitch_min"));
            ammo_counter1 = getDataFloat(extractSubstring(buf, "ammo_counter1"));
            ammo_counter2 = getDataFloat(extractSubstring(buf, "ammo_counter2"));
            ammo_counter3 = getDataFloat(extractSubstring(buf, "ammo_counter3"));


        }
    }
}