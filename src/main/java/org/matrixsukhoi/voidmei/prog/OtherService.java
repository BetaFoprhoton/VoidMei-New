package org.matrixsukhoi.voidmei.prog;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import org.matrixsukhoi.voidmei.VoidMeiMain;
import org.matrixsukhoi.voidmei.parser.*;

public class OtherService implements Runnable {

	String sMapInfo;
	String sMapObj;
	String shudMsg;

	Controller xc;
	public MapInfo mapInfo;
	public MapObj mapo;

	public double distance;
	public double enemyspeed;
	public double AOT;
	public double AZI;

	public int enemycount;
	public int friendcount;

	public int dislmt;
	double pX;
	double pY;
	long SpeedCheckMili;

	HudMessage msg;
	int lastEvt;
	int lastDmg;

	public volatile boolean isRun;
	boolean isgetMsg;
	boolean isgetmapObj;
	boolean isOverheat;
	boolean hisOverheat;
	int check;

	public double angleToclock(double angle) {
		double temp;
		temp = 12 + angle / 30.0f;
		if (temp >= 12)
			temp = temp - 12;
		return temp;
	}

	public double dxdyToangle(double dx, double dy) {
		double tems;
		tems = Math.atan(dy / dx) * 180 / Math.PI;
		if (dy >= 0 && dx <= 0) {
			tems = 180 + tems;
		}
		if (dy <= 0 && dx <= 0) {
			tems = 180 + tems;
		}
		if (dy <= 0 && dx >= 0) {
			tems=360+tems;
		}
		return tems;
	}

	public String sendGet(String host, int port, String path) throws IOException {

		String result = "";
		Socket socket = new Socket();
		SocketAddress dest = new InetSocketAddress(host, port);
		socket.connect(dest);
		OutputStreamWriter streamWriter = new OutputStreamWriter(socket.getOutputStream());
		BufferedWriter bufferedWriter = new BufferedWriter(streamWriter);

		bufferedWriter.write("GET " + path + " HTTP/1.1\r\n");
		bufferedWriter.write("Host: " + host + "\r\n");
		bufferedWriter.write(VoidMeiMain.httpHeader);
		bufferedWriter.write("\r\n");
		bufferedWriter.flush();

		BufferedInputStream streamReader = new BufferedInputStream(socket.getInputStream());

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(streamReader, "utf-8"));

		String line;

		bufferedReader.ready();
		bufferedReader.readLine();

		bufferedReader.readLine();
		bufferedReader.readLine();
		bufferedReader.readLine();
		bufferedReader.readLine();
		bufferedReader.readLine();
		// VoidMeiMain.debugPrint(System.currentTimeMillis()-testCheckMili);
		StringBuilder contentBuf = new StringBuilder();
		while ((line = bufferedReader.readLine()) != null) {
			contentBuf.append(line);
		}
		result = contentBuf.toString();

		bufferedReader.close();
		bufferedWriter.close();
		socket.close();
		return result;
	}

	public void init(Controller c) {
		isRun = true;
		xc = c;
		pX = 0;
		pY = 0;
		lastEvt = 0;
		lastDmg = 0;
		lastEvt = xc.lastEvt;
		lastDmg = xc.lastDmg;
		isgetMsg = true;
		isgetmapObj = true;
		isOverheat = false;
		hisOverheat = false;
		//
		dislmt = 1200;
		SpeedCheckMili = System.currentTimeMillis();
		mapInfo = new MapInfo();
		//mapInfo.init();
		if (isgetmapObj) {
			mapo = new MapObj();
			mapo.init();
		}
		if (isgetMsg) {
			msg = new HudMessage();
			msg.init();
		}

		// 初始化地图设置，计算尺寸
		try {
			sMapInfo = sendGet("127.0.0.1", 8111, "/map_info.json");
			mapInfo.update(sMapInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void calculate() {
		// 计算选择目标的水平相对距离及速度及AOT
		double pys;
		double eys;
		if (!mapo.slc.type.isEmpty()) {

			distance = (double) Math.sqrt((mapo.slc.x - mapo.pla.x) * (mapo.slc.x - mapo.pla.x) * mapInfo.cMapMaxSizeX
					* mapInfo.cMapMaxSizeX
					+ (mapo.slc.y - mapo.pla.y) * (mapo.slc.y - mapo.pla.y) * mapInfo.cMapMaxSizeY * mapInfo.cMapMaxSizeY);
			// VoidMeiMain.debugPrint(distance);

			if (mapo.slc.dx != 0 && distance < dislmt) {
				enemycount++;
			}

			enemyspeed = (double) (Math
					.sqrt(((mapo.slc.x - pX) * mapInfo.cMapMaxSizeX) * ((mapo.slc.x - pX) * mapInfo.cMapMaxSizeX)
							+ ((mapo.slc.y - pY) * mapInfo.cMapMaxSizeX) * ((mapo.slc.y - pY) * mapInfo.cMapMaxSizeY))
					* 1000 / (System.currentTimeMillis() - SpeedCheckMili));
			SpeedCheckMili = System.currentTimeMillis();
			pys = dxdyToangle(mapo.pla.dx, mapo.pla.dy);
			eys = dxdyToangle(mapo.slc.dx, mapo.slc.dy);
			AOT = Math.abs(pys - eys);
			if(AOT>180)AOT=360-AOT;
			// VoidMeiMain.debugPrint(enemyspeed*3.6 );
			AZI = angleToclock(dxdyToangle(mapo.slc.x - mapo.pla.x, mapo.slc.y - mapo.pla.y) - pys);
			// VoidMeiMain.debugPrint(mapo.slc.dx);
			// VoidMeiMain.debugPrint(enemycount);
		}

		// 统计周围敌机数和友机数
		int i;
		for (i = 0; i < mapo.movcur; i++) {
			double sdistance = Math.sqrt(
					(mapo.mov[i].x - mapo.pla.x) * (mapo.mov[i].x - mapo.pla.x) * mapInfo.cMapMaxSizeX * mapInfo.cMapMaxSizeX
							+ (mapo.mov[i].y - mapo.pla.y) * (mapo.mov[i].y - mapo.mov[i].y) * mapInfo.cMapMaxSizeY
									* mapInfo.cMapMaxSizeY);
			if (sdistance < dislmt && sdistance < mapo.mov[i].distance) {
				if (mapo.mov[i].colorg.getBlue() > 200 || mapo.mov[i].colorg.getGreen() > 200) {
					friendcount++;
					// VoidMeiMain.debugPrint((mapo.mov[i].type+"友军"+i+"距离"+sdistance));
				}
				if (mapo.mov[i].colorg.getRed() > 200) {
					enemycount++;
				}
			}
			mapo.mov[i].distance = sdistance;
		}

		// VoidMeiMain.debugPrint("周围友机数" + friendcount + " 周围敌机数" + enemycount);
	}

	public void close() {
		this.isRun = false;
	}

	public void judgeOverheat() {
//		xc.judgeEngineload();
		
		/*
		// 初次
		if (!hisOverheat && isOverheat) {
			hisOverheat = true;
			// VoidMeiMain.debugPrint("打开过热计时器");
			check = 3;// 六次检测
			xc.startOverheatTime();
		}
		// 更新过热时间
		if (hisOverheat && isOverheat) {
			// VoidMeiMain.debugPrint("更新过热时间");
			check = 3;
			xc.updateOverheatTime();

		}
		// 如果不再接受过热消息
		if (!isOverheat) {
			if (hisOverheat) {
				if (check == 0) {
					// VoidMeiMain.debugPrint("终结过热计时器");
					xc.endOverheatTime();
					hisOverheat = false;
					check--;

				} else {
					// VoidMeiMain.debugPrint("不过热检查次数-1");
					check--;
				}
			}
		}
		*/
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (isRun) {
			// 500毫秒执行一次
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 取得地图数据
			// VoidMeiMain.debugPrint("正在处理地图数据");
			enemycount = 0;
			friendcount = 0;
			try {
				if (isgetmapObj)
					sMapObj = sendGet("127.0.0.1", 8111, "/map_obj.json");
				if (isgetMsg)
					shudMsg = sendGet("127.0.0.1", 8111, "/hudmsg?lastEvt=" + lastEvt + "&lastDmg=" + lastDmg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			// VoidMeiMain.debugPrint(sMapObj);
			if (isgetmapObj)
				mapo.update(sMapObj);
			if (isgetMsg) {
				lastDmg = msg.update(shudMsg, lastDmg);
				if (msg.dmg.updated) {
					// VoidMeiMain.debugPrint("过热检查" + msg.dmg.msg.indexOf("热") +
					// "过高检查" + msg.dmg.msg.indexOf("温"));
					if (msg.dmg.msg.indexOf(Lang.oSkeyWord1) != -1
							|| msg.dmg.msg.indexOf(Lang.oSkeyWord2) != -1) {
						isOverheat = true;
						// VoidMeiMain.debugPrint("检测到过热标志" + isOverheat);
					}
				} else {

					isOverheat = false;
					// VoidMeiMain.debugPrint("检测到不过热标志" + isOverheat);
				}
			}
			// 处理地图数据

			calculate();
			pX = mapo.slc.x;
			pY = mapo.slc.y;

			// 获得HUDMSG消息并通知玩家过热
			judgeOverheat();
			// VoidMeiMain.debugPrint("otherService执行了");
		}

	}
}