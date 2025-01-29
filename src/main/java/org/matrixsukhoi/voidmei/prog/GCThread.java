package org.matrixsukhoi.voidmei.prog;

import org.matrixsukhoi.voidmei.VoidMeiMain;

public class GCThread implements Runnable {
	long GCCheckMili;
	Controller c;
	Boolean doit;

	public void init(Controller xc) {
		c = xc;
		doit = Boolean.TRUE;
	}

	@Override
	public void run() {

		while (doit) {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (c.S != null) {
				long stime = c.S.SystemTime;

				// 20秒回收一次内存
				if (stime - GCCheckMili > VoidMeiMain.gcSeconds * 1000) {
//					VoidMeiMain.debugPrint("内存回收");
					GCCheckMili = stime;
					System.gc();
				}
			}

		}
	}
}
