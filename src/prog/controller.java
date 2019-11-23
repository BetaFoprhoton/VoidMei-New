package prog;

import java.awt.AWTException;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

import com.alee.extended.panel.GroupPanel;
import com.alee.extended.time.ClockType;
import com.alee.extended.time.WebClock;
import com.alee.laf.label.WebLabel;
import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.WebNotification;

import parser.flightAnalyzer;
import parser.flightLog;
import ui.statusBar;
import ui.stickValue;
import ui.crosshair;
import ui.drawFrame;
import ui.engineInfo;
import ui.flightInfo;
import ui.gearAndFlaps;
import ui.mainform;
import ui.situationAware;

public class controller {

	public int flag;

	public boolean logon = false;

	engineInfo F;
	statusBar SB;
	mainform M;
	crosshair H;
	otherService O;
	situationAware SA;
	flightInfo FL;
	flightLog Log;
	drawFrame dF;
	stickValue sV;
	gearAndFlaps fS;
	flapsControl flc;
	
	Thread S1;
	Thread F1;
	Thread SB1;
	Thread M1;
	Thread H1;
	Thread O1;
	Thread SA1;
	Thread FL1;
	Thread Log1;
	Thread sV1;
	Thread fS1;
	Thread flc1;
	
	service S;
	public config cfg;
	// �洢����
	// ������

	public static int freqService;// Serviceȡ�������������
	// ���������
	public static int freqengineInfo;// engineInfoˢ������

	public static boolean engineInfoSwitch;// engineInfo��忪��
	public static boolean engineInfoEdge;// engineInfo����Ե����

	public static int engineInfoX;// engineInfo����λ��
	public static int engineInfoY;

	public static Font engineInfoFont;

	public static int engineInfoOpaque;// engineInfo����͸����

	public static boolean usetempratureInformation;

	public int lastEvt;
	public int lastDmg;
	public int step;
	long overheatCheckMili;
	long restoreCheckMili;
	public int overheattime;
	public int restoretime;
	public int availableoverheattime;
	public boolean isfirstOverheat;
	WebNotification tempCheck;

	public void initStatusBar() {
		// ״̬1����ʼ��״̬��
		if (flag == 1) {
			// System.out.println("״̬1����ʼ��״̬��");
			SB = new statusBar();
			SB.init(this);
			SB.S1();
			SB1 = new Thread(SB);
			SB1.start();
			flag = 2;

		}
		// SB.repaint();
	}

	public void changeS2() {
		// ״̬2��״̬�����ӳɹ����ȴ�������Ϸ
		// System.out.println(flag);
		// SB.repaint();
		if (flag == 2) {
			// System.out.println("״̬2��״̬�����ӳɹ����ȴ�������Ϸ");
			// NotificationManager.showNotification(createWebNotification("�������ӳɹ����������Ϸ"));
			SB.S2();
			flag = 3;
		}
	}

	public void changeS3() {
		// ״̬3�����ӳɹ����ͷ�״̬���������
		// SB.repaint();
		if (flag == 3) {
			// ��ʼ��MapObj�Լ�Msg��gamechat

			// System.out.println("״̬3�����ӳɹ����ͷ�״̬���������");
			usetempratureInformation = Boolean.parseBoolean(getconfig("usetempInfoSwitch"));
			// System.out.println(usetempratureInformation);
			NotificationManager.showNotification(createWebNotificationTime(3000));
			SB.S3();
			SB.doit = false;
			SB.dispose();
			SB = null;
			restoretime = 120;
			isfirstOverheat = true;
			restoreCheckMili = System.currentTimeMillis();
			O = new otherService();
			O.init(this);
			O1 = new Thread(O);
			O1.start();
			

			flag = 4;
			openpad();

		}
	}

	public void S4toS1() {
		// ״̬4����Ϸ���أ�������״̬1
		if (flag == 4) {
			// System.out.println("״̬4����Ϸ�˳����ͷ�Service��Դ��������״̬1");
			closepad();
			// �ͷ���Դ
			lastEvt = O.lastEvt;
			lastDmg = O.lastDmg;
			// System.out.println("���DMGID"+lastDmg);
			O.close();
			O = null;
			O1 = null;
			S.clear();
			flag = 1;
		}

	}

	public void openpad() {
		if (getconfig("engineInfoSwitch").equals("true")) {
			F = new engineInfo();
			F1 = new Thread(F);
			F.init(this, S);
			F1.start();
			//
		}
		if(getconfig("flapsControlSwitch").equals("true")){
			flc=new flapsControl();
			try {
				flc.init(this);
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			flc1=new Thread(flc);
			flc1.start();
		}

		if (Boolean.parseBoolean(getconfig("crosshairSwitch"))) {
			H = new crosshair();
			H1 = new Thread(H);
			H.init(this, S,O);
			H1.start();
		}
		if (Boolean.parseBoolean(getconfig("flightInfoSwitch"))) {
			FL = new flightInfo();
			FL1 = new Thread(FL);
			FL.init(this, S);
			FL1.start();
		}
		if (Boolean.parseBoolean(getconfig("enableLogging"))) {
			if (dF != null) {
				dF.doit = false;
				dF = null;
			}
			notification(language.cStartlog);
			Log = new flightLog();
			Log.init(this, S);
			Log1 = new Thread(Log);
			Log1.start();
			logon = true;
		}

		if (Boolean.parseBoolean(getconfig("enableAxis"))) {
			sV = new stickValue();
			sV.init(this, S);
			sV1 = new Thread(sV);
			sV1.start();
		}
		if (Boolean.parseBoolean(getconfig("enablegearAndFlaps"))) {
			fS = new gearAndFlaps();
			fS.init(this, S);
			fS1 = new Thread(fS);
			fS1.start();

		}
		if (app.debug) {
			// SA
			SA = new situationAware();
			SA.init(this, O);
			SA1 = new Thread(SA);
			SA1.start();

		}
		S.startTime = System.currentTimeMillis();
	}

	public void closepad() {
		if (getconfig("engineInfoSwitch").equals("true")) {
			F.doit = false;
			F1 = null;
			F.fclose();
			F = null;

		}
		if(getconfig("flapsControlSwitch").equals("true")){
			flc.close();
			flc=null;
			flc1=null;
		}
		if (getconfig("crosshairSwitch").equals("true")) {
			H.doit = false;
			H1 = null;
			H.dispose();
			H = null;
		}

		if (getconfig("flightInfoSwitch").equals("true")) {
			FL.doit = false;
			FL1 = null;
			FL.dispose();
			FL = null;
		}

		if (getconfig("enableLogging").equals("true")) {
			notification(language.cSavelog + Log.fileName + language.cPlsopen);
			//System.out.println("�׶β�:"+(Log.fA.curaltStage - Log.fA.initaltStage));
			if (Log.fA.curaltStage - Log.fA.initaltStage >= 1) {
				dF = new drawFrame();
				showdrawFrame(Log.fA);
			}

			logon = false;
			Log.close();
			Log1 = null;
			Log = null;

		}
		if (getconfig("enableAxis").equals("true")) {
			sV.doit = false;
			sV1 = null;
			sV.dispose();
			sV = null;

		}
		if (getconfig("enablegearAndFlaps").equals("true")) {
			fS.doit = false;
			fS1 = null;
			fS.dispose();
			fS = null;

		}

		if (app.debug) {
			SA.doit = false;
			SA1 = null;
			SA.dispose();
			SA = null;

			// �ͷ�

		}
	}

	public void init() {
		initconfig();// װ�������ļ�
		// ����Ƶ��
		// System.out.println("controllerִ����");
		usetempratureInformation = false;
		freqService = 80;
		overheatCheckMili = System.currentTimeMillis();
		// ˢ��Ƶ��
		freqengineInfo = 100;
		flag = 0;
		lastEvt = 0;
		lastDmg = 0;

		// ״̬0����ʼ��������������ļ�
		// System.out.println("״̬0����ʼ��������");
		initconfig();// װ�������ļ�
		M = new mainform();
		M1 = new Thread(M);
		M.init(this);
		M1.start();
		M.doit = true;
		// start();

	}

	public void start() {
		if (flag == 1) {
			freqService = Integer.parseInt(getconfig("Interval"));

			// System.out.println(freqService);
			// ״̬1���ͷ����ô��ڴ��γ�ʼ����̨
			// System.out.println("״̬1�����γ�ʼ��Service");
			M.doit = false;
			M.dispose();
			M = null;
			System.gc();
			// NotificationManager.showNotification(createWebNotification("������С�������̣�ע�����Ͻ�״̬����ʾ"));

			S = new service();

			S1 = new Thread(S);

			S.init(this);

			S1.start();

		}

	}

	public void Preview() {
		if (Boolean.parseBoolean(getconfig("engineInfoSwitch"))) {
			F = new engineInfo();
			F.initPreview(this);
		}
		if (Boolean.parseBoolean(getconfig("crosshairSwitch"))) {
			H = new crosshair();
			H.initPreview(this);
		}
		if (Boolean.parseBoolean(getconfig("flightInfoSwitch"))) {
			FL = new flightInfo();
			FL.initPreview(this);
		}
		if (Boolean.parseBoolean(getconfig("enableAxis"))) {
			sV = new stickValue();
			sV.initpreview(this);
		}
		if (Boolean.parseBoolean(getconfig("enablegearAndFlaps"))) {
			fS = new gearAndFlaps();
			fS.initPreview(this);
		}
		if (app.debug) {
			SA = new situationAware();
			SA.initPreview(this);

			// dF=new drawFrame();
			// showdrawFrame(null);

		}
	}

	public void endPreviewengineInfo() {

		// System.out.println(F.getLocationOnScreen().x);
		// System.out.println(F.getLocationOnScreen().y);
		if (Boolean.parseBoolean(getconfig("engineInfoSwitch"))) {
			// shade������Ҫ�Ӳ���
			// System.out.println(F.getLocationOnScreen().x);
			// System.out.println(F.getLocationOnScreen().y);
			setconfig("engineInfoX", Integer.toString(F.getLocationOnScreen().x - 15));
			setconfig("engineInfoY", Integer.toString(F.getLocationOnScreen().y - 15));
			F.dispose();
			F = null;
		}
		if (Boolean.parseBoolean(getconfig("crosshairSwitch"))) {
			// shade������Ҫ�Ӳ���
			setconfig("crosshairX", Integer.toString(H.getLocationOnScreen().x + 10));
			setconfig("crosshairY", Integer.toString(H.getLocationOnScreen().y + 10));
			H.dispose();
			H = null;
		}
		if (Boolean.parseBoolean(getconfig("flightInfoSwitch"))) {

			setconfig("flightInfoX", Integer.toString(FL.getLocationOnScreen().x - 15));
			setconfig("flightInfoY", Integer.toString(FL.getLocationOnScreen().y - 15));
			FL.dispose();
			FL = null;
		}
		if (Boolean.parseBoolean(getconfig("enableAxis"))) {
			// System.out.println(sV.getLocationOnScreen().x );
			// System.out.println(sV.getLocationOnScreen().y);
			setconfig("stickValueX", Integer.toString(sV.getLocationOnScreen().x + 10));
			setconfig("stickValueY", Integer.toString(sV.getLocationOnScreen().y + 10));
			sV.dispose();
			sV = null;
		}
		if (Boolean.parseBoolean(getconfig("enablegearAndFlaps"))) {
			// System.out.println(fS.getLocationOnScreen().x );
			// System.out.println(fS.getLocationOnScreen().y);

			setconfig("gearAndFlapsX", Integer.toString(fS.getLocationOnScreen().x + 10));
			setconfig("gearAndFlapsY", Integer.toString(fS.getLocationOnScreen().y + 10));

			fS.dispose();
			fS = null;
		}
		if (app.debug) {
			// System.out.println(SA.getLocationOnScreen().x );
			// System.out.println(SA.getLocationOnScreen().y);
			setconfig("situationAwareX", Integer.toString(SA.getLocationOnScreen().x - 15));
			setconfig("situationAwareY", Integer.toString(SA.getLocationOnScreen().y - 15));

			SA.dispose();
			SA = null;

		}
		saveconfig();

		// �ͷ�

		System.gc();

	}

	public void initconfig() {
		cfg = new config("./config/config.properties");
		// NotificationManager.showNotification(createWebNotification("������Ϣ�������"));
	}

	public String getconfig(String key) {
		return cfg.getValue(key);
	}

	public void setconfig(String key, String value) {
		cfg.setValue(key, value);
	}

	public void saveconfig() {
		cfg.saveFile("./config/config.properties", "8111");
		// NotificationManager.showNotification(createWebNotification("������Ϣд�����"));
	}

	public static void notificationtime(String text, int time) {
		NotificationManager.showNotification(createWebNotifications(text, time));
	}
	public static void notificationtimeAbout(String text, int time) {
		NotificationManager.showNotification(createWebNotificationsAbout(text, time));
	}

	public static void notification(String text) {
		NotificationManager.showNotification(createWebNotification(text));
	}

	static WebNotification createWebNotificationTime(long time) {
		WebNotification a = new WebNotification();
		// WebLabel text1=new WebLabel(text);
		// text1.setFont(app.DefaultFont);
		// text1.setVisible(false);
		a.setFont(app.DefaultFont);
		a.setIcon(NotificationIcon.clock.getIcon());

		a.setWindowOpacity((float) (0.5));
		WebClock clock = new WebClock();
		clock.setClockType(ClockType.timer);
		clock.setTimeLeft(time);
		clock.setFont(app.DefaultFont);
		clock.setTimePattern(language.cOpenpad);
		a.setContent(new GroupPanel(clock));
		// a.setOpaque(true);
		clock.start();
		a.setDisplayTime(time);

		a.setFocusable(false);
		return a;

	}
	
	

	static WebNotification createWebNotificationEngineTime(long time) {
		WebNotification a = new WebNotification();
		// WebLabel text1=new WebLabel(text);
		// text1.setFont(app.DefaultFont);
		// text1.setVisible(false);
		a.setFont(app.DefaultFont);
		a.setIcon(NotificationIcon.clock.getIcon());

		a.setWindowOpacity((float) (0.5));
		WebClock clock = new WebClock();
		clock.setClockType(ClockType.timer);
		clock.setTimeLeft(time);
		clock.setFont(app.DefaultFont);
		clock.setTimePattern(language.cEnginedmg);
		a.setContent(new GroupPanel(clock));
		// a.setOpaque(true);
		clock.start();
		a.setDisplayTime(time);

		a.setFocusable(false);
		return a;

	}

	static WebNotification createWebNotifications(String text, int time) {
		WebNotification a = new WebNotification();
		WebLabel text1 = new WebLabel(text);
		text1.setFont(app.DefaultFont);
		// text1.setVisible(false);
		// a.setWindowOpacity((float) (0.5));

		a.setFont(app.DefaultFont);
		a.setIcon(NotificationIcon.information.getIcon());
		a.add(text1);
		a.setDisplayTime(time);
		a.setFocusable(false);
		return a;

	}

	static WebNotification createWebNotificationsAbout(String text, int time) {
		WebNotification a = new WebNotification();
		WebLabel text1 = new WebLabel(text);
		text1.setFont(new Font(app.DefaultFontName,Font.PLAIN,14));
		// text1.setVisible(false);
		// a.setWindowOpacity((float) (0.5));
		Image I=Toolkit.getDefaultToolkit().createImage("image/fubuki.jpg");
		ImageIcon A=new ImageIcon(I);
		a.setFont(app.DefaultFont);
		a.setIcon(A);
		a.add(text1);
		a.setDisplayTime(time);
		a.setFocusable(false);
		return a;

	}
	static WebNotification createWebNotification(String text) {
		WebNotification a = new WebNotification();
		WebLabel text1 = new WebLabel(text);
		text1.setFont(app.DefaultFont);
		// text1.setVisible(false);
		// a.setWindowOpacity((float) (0.5));

		a.setFont(app.DefaultFont);
		a.setIcon(NotificationIcon.information.getIcon());
		a.add(text1);
		a.setDisplayTime(5000);
		a.setFocusable(false);
		return a;

	}

	static WebNotification createWebNotificationEngineBomb(String text) {
		WebNotification a = new WebNotification();
		WebLabel text1 = new WebLabel(text);
		text1.setFont(app.DefaultFont);
		// text1.setVisible(false);
		// a.setWindowOpacity((float) (0.5));

		a.setFont(app.DefaultFont);
		a.setIcon(NotificationIcon.error);
		a.add(text1);
		a.setDisplayTime(3000);
		a.setFocusable(false);
		return a;

	}

	void startOverheatTime() {
		overheatCheckMili = System.currentTimeMillis();
		step = 0;
		if (!isfirstOverheat) {
			/*
			 * System.out.println("�ڶ��ι���,�ϴι���ʱ�䣺" + overheattime + "�ϴ�ӵ��ʱ��" +
			 * restoretime + "���λָ�ʱ��" + (overheatCheckMili - restoreCheckMili) /
			 * 500 + "�´ο���ʱ��" + ((restoretime - overheattime) +
			 * (overheatCheckMili - restoreCheckMili) / 500));
			 */
			restoretime = (int) ((restoretime - overheattime) + (overheatCheckMili - restoreCheckMili));
		}
		if (restoretime > 120)
			restoretime = 120;
	}

	void updateOverheatTime() {
		int Time = (int) ((System.currentTimeMillis() - overheatCheckMili) / 1000);
		if (F != null)
			F.updateOverheatTime(Time);
		availableoverheattime = restoretime - Time;
		if (usetempratureInformation) {
			if (availableoverheattime < 60) {
				if (step == 0) {
					tempCheck = createWebNotification(language.cWarn1min);
					NotificationManager.showNotification(tempCheck);
					step = 1;
				}
			}
			if (availableoverheattime < 30) {

				if (step == 1) {
					tempCheck = createWebNotificationEngineTime(30000);
					NotificationManager.showNotification(tempCheck);
					step = 2;
				}
			}
			if (availableoverheattime < 1) {
				if (step == 2) {
					tempCheck = createWebNotificationEngineBomb(language.cEngBomb);
					NotificationManager.showNotification(tempCheck);
					step = 3;
				}

			}
		}
	}

	public void showdrawFrame(flightAnalyzer fA) {
		dF.init(this, fA);
	}

	public void writeDown() {

		if (logon) {
			if (Log.doit == false) {
				Log.doit = true;
				// Log1.start();

			} else {
				Log.doit = true;
				System.out.println("�߳�ͬ������");
			}
			// System.out.println(Log.doit);
		}
	}

	void endOverheatTime() {

		restoreCheckMili = System.currentTimeMillis() - 1000;
		overheattime = F.overheattime;
		if (overheattime > 5)
			isfirstOverheat = false;
		step = 0;
		if (F != null)
			F.updateOverheatTime(0);
	}
}