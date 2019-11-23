package prog;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;

import parser.indicators;
import parser.state;

public class service implements Runnable {

	public static URL urlstate;
	public static URL urlindicators;
	public static String buf;

	public volatile int freq;
	public volatile state sState;
	public volatile indicators iIndic;
	public volatile String status;
	public volatile String sTime;
	public volatile int totalhp;
	public volatile String stotalhp;
	public volatile int totalhpeff;
	public volatile String stotalhpeff;
	public volatile int totalthr;
	public volatile String stotalthr;
	public volatile float totalfuel;
	public volatile float totalfuelp;
	public volatile String stotalfuel;
	public int checkAlt;
	public volatile float dfuel;
	public volatile int fueltime;
	public volatile String sfueltime;
	public boolean notCheckInch;
	public boolean isFuelpressure;
	public boolean altperCirclflag;
	public long intv;
	public float althour;
	public float altperCircle;
	public float alt;
	public float altp;
	public float altreg;
	long SystemTime;
	long MainCheckMili;
	long FuelCheckMili;
	long GCCheckMili;
	long SlowCheckMili;
	long intvCheckMili;
	long startTime;
	public long time;
	// public int enginenum;
	// public int enginetype;

	public volatile float speedv;
	public volatile float speedvp;
	public volatile float acceleration;
	public volatile float SEP;

	public volatile String salt;
	public volatile String sSEP;

	public volatile String s;
	public volatile String s1;
	public volatile String s2;
	public volatile controller c;

	// sStateת����

	public volatile String svalid;
	public volatile String engineNum;
	public volatile String engineType;
	public volatile String aileron;
	public volatile String elevator;
	public volatile String rudder;
	public volatile String flaps;
	public volatile String gear;
	public volatile String TAS;
	public volatile String IAS;
	public volatile String M;
	public volatile String AoA;
	public volatile String AoS;
	public volatile String Ao;
	public volatile String Ny;
	public volatile String Vy;
	public volatile String Wx;
	public volatile String throttle;
	public volatile String RPMthrottle;
	public volatile String radiator;
	public volatile String mixture;
	public volatile String compass;
	public volatile String compressorstage;
	public volatile String magenato;
	public volatile String power[];
	public volatile String manifoldpressure;
	public volatile String pressurePounds;
	public volatile String pressureInchHg;
	public volatile String pressureMmHg;
	public volatile String watertemp;
	public volatile String oiltemp;
	public volatile String pitch[];
	public volatile String thrust[];
	public volatile String aclrt;
	
	public String efficiency[];
	long testCheckMili;
	// iIndic
	public volatile String rpm;

	public String NumtoString(int Num, int arg) {
		return String.format("%0" + arg + "d", Num);
	}

	public String NumtoString(float Num, int arg1, int arg2) {
		return String.format("%-" + arg1 + "." + arg2 + "f", Num);
	}

	public void transtoString() {

		// ����ת����ʽ
		// sState
		/*
		 * String.format("%01d", engineNum);
		 * 
		 * 
		 * 
		 * 
		 * public String valid; public int engineNum; public int engineType;
		 * public int aileron; public int elevator; public int rudder; public
		 * int flaps; public int gear; public int TAS; public int IAS; public
		 * float M; public float AoA; public float AoS; public float Ny; public
		 * float Vy; public float Wx; public int throttle; public int
		 * RPMthrottle; public int radiator; public int mixture; public int
		 * compressorstage; public int magenato; public float power[]; public
		 * int RPM; public float manifoldpressure; public float watertemp;
		 * public float oiltemp; public float pitch[]; public int thrust[];
		 * public float efficiency[];
		 */
		//
		if(iIndic.fuelpressure==true)isFuelpressure=true;
	
		throttle = String.format("%d", sState.throttle);
		aileron = String.format("%d", sState.aileron);
		elevator = String.format("%d", sState.elevator);
		rudder = String.format("%d", sState.rudder);

		sTime = String.format("%.2f", time / 1000f);
		sfueltime = String.format("%d", fueltime / 60000);
		stotalthr = String.format("%d", totalthr);
		stotalhp = String.format("%d", totalhp);
		rpm = String.format("%d", (int) sState.RPM);
		stotalhpeff = String.format("%d", totalhpeff);
		efficiency[0] = String.format("%.0f", sState.efficiency[0]);
		if (sState.watertemp == -65535) {
			if (iIndic.engine_temperature == -65535)
				watertemp = String.format("%.0f", 0.0);
			else
				watertemp = String.format("%.0f", iIndic.engine_temperature);

		} else {
			watertemp = String.format("%.0f", sState.watertemp);
		}
		oiltemp = String.format("%.0f", sState.oiltemp);
		manifoldpressure = String.format("%.2f", sState.manifoldpressure);
		pressurePounds=String.format("%+d", Math.round((sState.manifoldpressure-1)*14.696));
		pressureMmHg=String.format("%d",Math.round(sState.manifoldpressure));
		pressureInchHg=String.format("%d",Math.round(sState.manifoldpressure*760/25.4));
		stotalfuel = String.format("%.1f", totalfuel);
		pitch[0] = String.format("%.1f", sState.pitch[0]);
		RPMthrottle = String.format("%d", sState.RPMthrottle);
		radiator = String.format("%d", sState.radiator);
		mixture = String.format("%d", sState.mixture);
		flaps = String.format("%d", sState.flaps);
		//
		Vy = String.format("%.1f", sState.Vy);
		IAS = String.format("%d", sState.IAS);
		TAS = String.format("%d", sState.TAS);
		salt = String.format("%.0f", alt);
		Wx = String.format("%.0f", Math.abs(sState.Wx));
		M = String.format("%.2f", sState.M);
		Ny = String.format("%.1f", sState.Ny);
		sSEP = String.format("%d", Math.round(SEP / 9.78));
		aclrt = String.format("%.3f", acceleration);
		// Ao=String.format("%.1f",
		// Math.sqrt(sState.AoA*sState.AoA+sState.AoS*sState.AoS));
		AoA = String.format("%.1f", Math.abs(sState.AoA));
		AoS = String.format("%.1f", Math.abs(sState.AoS));
		// iIndic
		compass = String.format("%.0f", iIndic.compass);
		

	}

	public void slowcalculate() {
		// ��������ʼ�����ʱ��
		// System.out.println(totalfuelp - totalfuel);
		if (MainCheckMili - FuelCheckMili > 1000) {
			dfuel = (totalfuelp - totalfuel) / (MainCheckMili - FuelCheckMili);

			if (dfuel > 0)
				fueltime = (int) (totalfuel / dfuel);
			else
				fueltime = 0;
			if (fueltime > 18000000)
				fueltime = 0;

			FuelCheckMili = MainCheckMili;
			totalfuelp = totalfuel;
		}
	}

	public void calculate() {
		int i;

		// ��ÿ�ʼʱ��
		time = System.currentTimeMillis() - startTime;
		// System.out.println(time/60000+"��"+(time%60000)/1000+"��");
		// ���׼ȷ�߶�
		altp = alt;
		if (iIndic.altitude_10k != -65535)
			alt = iIndic.altitude_10k;
		else
			alt = iIndic.altitude_hour;
		
		if (!notCheckInch&&Math.abs(sState.Vy)>0) {
			//System.out.println("checkalt"+checkAlt+"alt:"+alt+"altp:"+altp+"�Ա�"+Math.abs(alt - altp)*1000+"?"+Math.abs(2 *sState.Vy * intv));
			if ((Math.abs(alt - altp) * 1000 > Math.abs(2 * sState.Vy * intv))) {
				checkAlt++;
			} else {
				checkAlt--;
			}
			if (Math.abs(checkAlt) > 1000)
				notCheckInch = true;
		}
		if (checkAlt > 2)
			alt = alt * 0.3048f;
		// System.out.println(Math.abs(alt - altp)*1000+"?"+Math.abs(2 *
		// sState.Vy * intv));

		// �����è�ĸ߶�����
		alt = alt + altperCircle * altreg;
		//System.out.println("checkalt"+checkAlt);
	
		if (notCheckInch&&alt>0) {
			//System.out.println("��һ��" + altp + " �˴�" + alt + "һȦ" + altperCircle + "Ȧ��" + altreg);
			if (Math.abs(checkAlt) > 20 && (Math.abs(alt - altp) * 1000 > 2500 * intv)) {
				if (!altperCirclflag) {
					altperCircle = altp;
					altperCirclflag = true;
				}
				if (sState.Vy > 0) {
					altreg++;
				} else {
					altreg--;
				}
				alt = altp;
			}
		}

		// System.out.println(Math.abs(alt-altp)*1000+"
		// "+Math.abs(1.5*sState.Vy*intv)+" "+checkAlt+" "+alt);

		// ��ȡ׼ȷ�ٶ�
		speedvp = speedv;
		if (iIndic.speed != -65535)
			speedv = iIndic.speed;
		else
			speedv = sState.TAS / 3.6f;
		// �������������ܹ��ʺ����Ṧ��
		// System.out.println(sState.engineType);
		// System.out.println(sState.engineNum);
		if (sState.engineType == 0) {
			// ������
			totalhp = 0;
			totalhpeff = 0;
			totalthr = 0;
			for (i = 0; i < sState.engineNum; i++) {
				totalthr = totalthr + (int) sState.thrust[i];
				// System.out.println(sState.engineNum);
				totalhp = totalhp + (int) sState.power[i];
				totalhpeff = totalhpeff + (int) (sState.thrust[i]*9.78*speedv*sState.TAS/(double)sState.IAS) / 735;
			}
			// System.out.println(totalhp);
			// System.out.println(totalhpeff);
		} else {
			// ������
			totalthr = 0;
			for (i = 0; i < sState.engineNum; i++) {
				// System.out.println(sState.thrust[0]);
				totalthr = totalthr + (int) sState.thrust[i];
			}
			// System.out.println(totalthr+" "+totalhpeff);
			totalhpeff = (int) ((totalthr * 9.78 * speedv*sState.TAS/(double)sState.IAS) / 735);
		}

		// ����������
		if (iIndic.fuelnum != 0) {
			totalfuel = 0;
			for (i = 0; i < iIndic.fuelnum; i++) {
				totalfuel = totalfuel + iIndic.fuel[i];
			}
			// System.out.println(totalfuel);
		}

		// ����SEP
		if (sState.IAS != 0) {
			acceleration = (speedv - speedvp) * sState.TAS * 1000 / (intv * sState.IAS);
			SEP = acceleration * (speedvp + speedv) * sState.TAS / (2 * sState.IAS) + 9.78f * sState.Vy;
		} else {
			acceleration = 0;
			SEP = 0;
		}

	}

	/*
	 * public String get(String ip) throws UnsupportedEncodingException,
	 * IOException {
	 * 
	 * testCheckMili = System.currentTimeMillis();// URL url; BufferedReader
	 * bufReader; String buf = ""; HttpURLConnection httpConn;
	 * 
	 * url = new URL("http://127.0.0.1:8111/" + ip); httpConn =
	 * (HttpURLConnection) url.openConnection(); InputStreamReader input = new
	 * InputStreamReader(httpConn.getInputStream(), "utf-8"); bufReader = new
	 * BufferedReader(input); String line; // StringBuilder contentBuf = new
	 * StringBuilder(); while ((line = bufReader.readLine()) != null) { buf +=
	 * line; } System.out.println(System.currentTimeMillis() - testCheckMili);//
	 * // System.out.println(buf.length()); return buf;
	 * 
	 * // bufReader.close(); // input.close();
	 * 
	 * }
	 */
	public String sendGet(String host, int port, String path) throws IOException {

		String result = "";
		Socket socket = new Socket();
		SocketAddress dest = new InetSocketAddress(host, port);
		socket.connect(dest);
		OutputStreamWriter streamWriter = new OutputStreamWriter(socket.getOutputStream());
		BufferedWriter bufferedWriter = new BufferedWriter(streamWriter);

		bufferedWriter.write("GET " + path + " HTTP/1.1\r\n");
		bufferedWriter.write("Host: " + host + "\r\n");
		bufferedWriter.write("Cache-Control:no-cache");
		bufferedWriter.write(app.httpHeader);
		bufferedWriter.write("\r\n");
		bufferedWriter.flush();

		BufferedInputStream streamReader = new BufferedInputStream(socket.getInputStream());

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(streamReader, "utf-8"));

		String line = null;

		bufferedReader.ready();
		bufferedReader.readLine();

		bufferedReader.readLine();
		bufferedReader.readLine();
		bufferedReader.readLine();
		bufferedReader.readLine();
		bufferedReader.readLine();

		StringBuilder contentBuf = new StringBuilder();
		while ((line = bufferedReader.readLine()) != null) {
			contentBuf.append(line);
		}
		result = contentBuf.toString();
		// System.out.println(result);
		bufferedReader.close();
		bufferedWriter.close();
		socket.close();
		return result;
	}

	// ���ñ���
	public void clearvaria() {
		sState = null;
		iIndic = null;
		time = 0;
		altperCircle = 0;
		checkAlt = 0;
		altreg = 0;
		altp = 0;
		alt = 0;
		FuelCheckMili = System.currentTimeMillis();
		notCheckInch = false;
		altperCirclflag = false;
		isFuelpressure=false;
		notCheckInch=false;
	}

	public void clear() {
		//System.out.println("ִ�����");
		clearvaria();
		System.gc();
		sState = new state();
		iIndic = new indicators();
		sState.init();
		iIndic.init();

	}

	public void init(controller xc) {
		try {

			urlstate = new URL("http://127.0.0.1:8111/state");
			urlindicators = new URL("http://127.0.0.1:8111/indicators");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("serviceִ����");
		this.c = xc;
		clearvaria();

		freq = controller.freqService;
		sState = new state();
		sState.init();
		iIndic = new indicators();
		iIndic.init();
		power = new String[4];
		pitch = new String[4];
		thrust = new String[4];
		efficiency = new String[4];	
		FuelCheckMili = System.currentTimeMillis();
		isFuelpressure=false;
	
	}

	@Override
	public void run() {

		while (true) {

			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SystemTime = System.currentTimeMillis();

			if (SystemTime - MainCheckMili > freq) {
				// status = "Service����";
				// System.out.println(this.status);
				// ������json
				c.initStatusBar();

				MainCheckMili = System.currentTimeMillis();

				try {
					intv = (System.currentTimeMillis() - intvCheckMili);
					intvCheckMili = System.currentTimeMillis();
					s = sendGet("127.0.0.1", 8111, "/state");
					s1 = sendGet("127.0.0.1", 8111, "/indicators");

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					// e1.printStackTrace();
					s = "";
					s1 = "";
				}
				// System.out.println("s:"+s+"s1:"+s1);
				// ����state
				if (s.length()>0&& s1.length()>0) {
					// �ı�״̬Ϊ���ӳɹ�
					//System.out.println(sState);
					sState.update(s);
					c.changeS2();
					if (sState.flag) {
						iIndic.update(s1);
						//System.out.println(sState.engineAlive);
						if (sState.engineAlive) {
							c.changeS3();// �����

							// ��ʼ��������
							calculate();


							// ������ת����ʽ
							transtoString();

							// д���ĵ�	
							c.writeDown();
						}
					} else {
						// ״̬��Ϊ�ȴ���Ϸ��ʼ��״̬1��
						// c.changeS2();//���ӳɹ��ȴ���Ϸ��ʼ

						c.S4toS1();
						// System.out.println("�ȴ���Ϸ��ʼ");
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} else {
					// ״̬��Ϊ�ȴ�������
					c.S4toS1();
					// System.out.println("�ȴ�������");
				}

				// c.flag=1;
				// status = ("Service���");
				// System.out.println(this.status);
				// System.out.println(sState.valid);

				// �ͷ�s
				// s=null;
				// s1=null;
			}

			
			if (SystemTime - SlowCheckMili > freq * 4) {
				// System.out.println(SystemTime - SlowCheckMili);
				SlowCheckMili = SystemTime;
				// ���ټ���
				slowcalculate();
			
				
			}

			// 20�����һ���ڴ�
			if (SystemTime - GCCheckMili > 20000) {
				// System.out.println("�ڴ����");
				GCCheckMili = SystemTime;
				System.gc();
			}

		}
	}
}