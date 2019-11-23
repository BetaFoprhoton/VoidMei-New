package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.RenderingHints;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.alee.extended.panel.WebButtonGroup;
import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;

import parser.blkxparser;
import parser.flightAnalyzer;
import prog.app;
import prog.controller;
import prog.language;

public class drawFrame extends WebFrame implements Runnable {
	/**
	 * 
	 */
	public volatile boolean doit = true;
	private static final long serialVersionUID = 6290400898885722422L;
	controller xc;
	flightAnalyzer fA;
	WebPanel panel;
	int pixIndex = 0;
	int Index = 8;
	boolean useBlkx = true;
	double ggx4;
	double ggy4;
	blkxparser blkx;
	double fY[];
	double fX[];
	public WebButton createButton(String text) {
		WebButton a = new WebButton(text);
		a.setShadeWidth(1);
		a.setDrawShade(true);
		// a.getWebUI().setInnerShadeColor(new Color(255,255,255,200));
		// a.getWebUI().setInnerShadeWidth(10);
		a.setFont(app.DefaultFontBig);
		a.setTopBgColor(new Color(0, 0, 0, 0));
		a.setBottomBgColor(new Color(0, 0, 0, 0));
		// a.setUndecorated(false);
		// a.setShadeWidth(1);
		a.setBorderPainted(false);

		return a;

	}

	public WebButtonGroup createbuttonGroup() {
		WebButton A = createButton(language.dFprev);
		WebButton B = createButton(language.dFnext);

		WebButtonGroup G = new WebButtonGroup(true, A, B);
		// G.setBorderColor(new Color(0, 0, 0, 0));
		// G.setButtonsDrawSides(true, true, false,false);
		// A.setPreferredHeight(30);
		A.setPreferredWidth(120);
		// B.setPreferredHeight(30);

		B.setPreferredWidth(120);
		B.setRound(10);
		G.setButtonsDrawSides(false, false, false, true);
		G.setButtonsForeground(new Color(0, 0, 0, 200));
		// G.setButtonsInnerShadeColor(new Color(0,0,0));
		// G.setButtonsInnerShadeWidth(5);
		G.setButtonsShadeWidth(3);
		// G.setButtonsDrawFocus(false);
		A.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pixIndex > 0)
					pixIndex--;
				repaint();
			}
		});
		B.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pixIndex + 1 < Index)
					pixIndex++;
				repaint();
			}
		});
		// G.setPaintSides(false, false, false, false);
		return G;
	}

	double findMin(double X[]){
		int i;
		double min=Double.MAX_VALUE;
		for(i=0;i<X.length;i++){
			if(X[i]<min)
				min=X[i];
		}
		return min;
	}

	double findMax(double X[]){
		int i;
		double max=Double.MIN_VALUE;
		for(i=0;i<X.length;i++){
			if(X[i]>max)
				max=X[i];
		}
		return max;
	}

	int searchMin(int X[]) {
		int i;
		int min = 655353535;
		for (i = fA.initaltStage; i <= fA.curaltStage - 1; i++) {
			if (X[i] < min)
				min = X[i];
		}
		return min;
	}

	int searchMax(int X[]) {
		int i;
		int max = -655353535;
		for (i = fA.initaltStage; i <= fA.curaltStage - 1; i++) {
			if (X[i] > max)
				max = X[i];
		}
		return max;
	}

	double searchMin(double X[]) {
		int i;
		double min = 655353535;
		for (i = fA.initaltStage; i <= fA.curaltStage - 1; i++) {
			if (X[i] < min)
				min = X[i];
		}
		return min;
	}

	double searchMax(double X[]) {
		int i;
		double max = -655353535;
		for (i = fA.initaltStage; i <= fA.curaltStage - 1; i++) {
			if (X[i] > max)
				max = X[i];
		}
		return max;
	}

	void initpanel() {
		panel.setWebColoredBackground(false);
		panel.setBackground(new Color(0, 0, 0, 0));
	}

	void setFrameOpaque() {
		this.getWebRootPaneUI().setMiddleBg(new Color(255, 255, 255, 255));// �в�͸��
		this.getWebRootPaneUI().setTopBg(new Color(255, 255, 255, 255));// ����͸��
		this.getWebRootPaneUI().setBorderColor(new Color(255, 255, 255, 255));// �����͸��
		this.getWebRootPaneUI().setInnerBorderColor(new Color(255, 255, 255, 255));// �����͸��
	}

	void getdata(String planename){
		String fmfile;
		String unitSystem;
		int i;
		//����fm
		blkx=new blkxparser("./data/aces/gamedata/flightmodels/"+planename+".blkx");
		fmfile=blkx.getlastone("fmfile");
		fmfile=fmfile.substring(1, fmfile.length()-1);
		if(fmfile.indexOf("blk")==-1)fmfile=fmfile+".blk";
		for( i=0;i<fmfile.length();i++){
			if(fmfile.charAt(i)=='/')break;
		}
		fmfile=fmfile.substring(i+1);
		//System.out.println(fmfile);
		
		//����fmfile
		blkx=new blkxparser("./data/aces/gamedata/flightmodels/fm/"+fmfile+"x");
		//System.out.println(blkx.data);
		blkx.getAllplotdata();
		

	}
	void drawXY(Graphics2D g, int x, int y, int dwidth, int dheight, String title, String xName, String yName,String xD,String yD,
		double xmin, double xmax, double ymin, double ymax, int xgap, int ygap) {
		// ȷ������
		g.setStroke(new BasicStroke(3));
		g.setColor(new Color(0, 0, 0, 250));
		int pxmin = (int) xmin;
		int pxmax = (int) xmax + xgap;
		int pymin = (int) ymin;
		int pymax = (int) ymax + ygap;
		int intervalX = xgap;
		int intervalY = ygap;
		double ggx = 0;
		double ggy = 0;
		if(intervalX==0)intervalX=1;
		if(intervalY==0)intervalY=1;
		if (pxmax - pxmin != 0) {
			ggx = (double)dwidth / (double) (pxmax - pxmin);
		}
		if (pymax - pymin != 0) {
			ggy =  (double)dheight/ (double)(pymax-pymin);
		}
		
		// ����
		g.setFont(new Font(app.DefaultFontName, Font.PLAIN, 16));
		g.drawString(title, x + dwidth / 2, y);
		y = y + 10;// ������10
		g.setFont(new Font(app.DefaultFontName, Font.PLAIN, 10));
		
		// x�����ͷ
		g.drawLine(x, y + dheight, x + dwidth, y + dheight);

		int ii = (int) ((pxmax - pxmin) / intervalX);
		for (; ii >= 0; ii--) {
			// ������̶�
			g.setStroke(new BasicStroke(1));
			g.drawLine((int) (  x + ii * intervalX * ggx), y + dheight, (int) ( x + ii * intervalX * ggx),
					y);
			g.drawString(String.valueOf(pxmin + ii * intervalX), (int) ( x + ii * intervalX * ggx),
					y + dheight + 15);
		}
		// x�ᵥλ
		g.setFont(new Font(app.DefaultFontName, Font.PLAIN, 14));
		g.drawString(xD, x + dwidth + 5, y + dheight);
		g.setFont(new Font(app.DefaultFontName, Font.PLAIN, 10));

		g.setStroke(new BasicStroke(3));
		// y�����ͷ
		g.drawLine(x, y + dheight, x, y);
		// y��̶�
		ii = (int) ((pymax-pymin)/ intervalY);
		for (; ii >= 0; ii--) {
			g.setStroke(new BasicStroke(1));
			g.drawLine(x, (int) (y + dheight - ii * intervalY * ggy), x + dwidth,
					(int) (y + dheight - ii * intervalY * ggy));

			g.drawString(String.valueOf(pymin+ii * intervalY), x - 30, (int) (y + dheight - ii * intervalY * ggy));
		}
		// y�ᵥλ
		g.setFont(new Font(app.DefaultFontName, Font.PLAIN, 14));
		g.drawString(yD, x - 5, y - 10);
		
	}


	
	void drawPoint(Graphics2D g,int x,int y,int dwidth,int dheight,double ggx,double ggy,double ix[], double iy[],int pxmin,int pymin,Color C) {
		g.setStroke(new BasicStroke(1));
		g.setColor(C);
		y = y + 10;// ������10
		// ���
		int ii=0;
		for (ii = 0; ii < ix.length; ii++) {
			//System.out.println((y + dheight) +" "+(y + dheight -(iy[ii]-pymin) * ggy));	
			g.drawOval((int) ( x + (ix[ii] -pxmin ) * ggx) - 1, (int) (y + dheight -(iy[ii]-pymin) * ggy) - 1, 2, 2);
		}
	
		// ����
		g.setStroke(new BasicStroke(1));
	
		for (ii = 0; ii <ix.length-1 ; ii++) {
			g.drawLine((int) ( x + (ix[ii] - pxmin) * ggx), (int) (y + dheight - (iy[ii]-pymin)  * ggy),
					(int) ( x+ (ix[ii + 1] - pxmin) * ggx), (int) (y + dheight - (iy[ii + 1]-pymin) * ggy));
		}
		
		
	}
	
	void drawExample(Graphics2D g,int x,int y,int dheight,Color C,String name){
		g.setStroke(new BasicStroke(1));
		g.setColor(C);
		g.drawLine(x, y+dheight+40, x+20, y+dheight+40);
		g.setColor(new Color(0,0,0,250));
		g.setFont(new Font(app.DefaultFontName, Font.PLAIN, 10));
		g.drawString(name, x+25, y+dheight+45);
	}

	void drawCoordinates(Graphics2D g, int x, int y, int dwidth, int dheight, String title, String xName, String yName,
			double X[], String xD, String yD) {
		g.setStroke(new BasicStroke(3));
		g.setColor(new Color(0, 0, 0, 250));
		int movex = 0;
		int pmin = (int) searchMin(X);
		int pmax = (int) searchMax(X) + 1;
		double ggx = 0;
		double ggy = 0;
		int intervalX = Math.round(((pmax - pmin) / 10) / 10.0f) * 10;// X����
		int intervalY = Math.round((fA.curaltStage - fA.initaltStage) * 100 / 800.0f) * 100;// Y��߶ȼ��
		if (pmax - pmin != 0) {
			ggx = (dwidth - movex) / (double) (pmax - pmin);
		}
		ggy = (double) (dheight) / (fA.curaltStage * 100);

		// ����
		g.setFont(new Font(app.DefaultFontName, Font.PLAIN, 16));
		g.drawString(title, x + dwidth / 2, y);
		y = y + 10;// ������10
		g.setFont(new Font(app.DefaultFontName, Font.PLAIN, 10));
		// x�����ͷ
		g.drawLine(x, y + dheight, x + dwidth, y + dheight);

		// x��̶�
		if (intervalX == 0)
			intervalX = 1;
		int ii = (int) ((pmax - pmin) / intervalX);
		for (; ii >= 0; ii--) {
			// ������̶�
			g.setStroke(new BasicStroke(1));
			g.drawLine((int) (movex + x + ii * intervalX * ggx), y + dheight, (int) (movex + x + ii * intervalX * ggx),
					y);
			// System.out.println("X����"+(pmin + ii * intervalX)+"λ��"+(int)
			// (movex + x + ii * intervalX * ggx));
			g.drawString(String.valueOf(pmin + ii * intervalX), (int) (movex + x + ii * intervalX * ggx),
					y + dheight + 15);
		}
		// x�ᵥλ
		g.setFont(new Font(app.DefaultFontName, Font.PLAIN, 14));
		g.drawString(xD, x + dwidth + 5, y + dheight);
		g.setFont(new Font(app.DefaultFontName, Font.PLAIN, 10));

		g.setStroke(new BasicStroke(3));

		// y�����ͷ
		g.drawLine(x, y + dheight, x, y);
		// g.drawLine(x - 5, y + 5, x, y);
		// g.drawLine(x + 5, y + 5, x, y);
		// y��̶�
		if (intervalY == 0)
			intervalY = 100;
		ii = (int) (fA.curaltStage * 100 / intervalY);
		for (; ii >= 0; ii--) {
			g.setStroke(new BasicStroke(1));
			g.drawLine(x, (int) (y + dheight - ii * intervalY * ggy), x + dwidth,
					(int) (y + dheight - ii * intervalY * ggy));

			g.drawString(String.valueOf(ii * intervalY), x - 30, (int) (y + dheight - ii * intervalY * ggy));
		}
		// y�ᵥλ
		g.setFont(new Font(app.DefaultFontName, Font.PLAIN, 14));
		g.drawString(yD, x - 5, y - 10);

		// ���
		for (ii = fA.initaltStage; ii <= fA.curaltStage - 1; ii++) {
			g.drawOval((int) (movex + x + (X[ii] - pmin) * ggx) - 1, (int) (y + dheight - (ii) * 100 * ggy) - 1, 2, 2);
		}

		// ����
		g.setStroke(new BasicStroke(1));
		for (ii = fA.initaltStage; ii < fA.curaltStage - 1; ii++) {
			if (Math.abs(X[ii] - X[ii + 1]) > 100) {
				X[ii + 1] = X[ii + 2];
			}
			g.drawLine((int) (movex + x + (X[ii] - pmin) * ggx), (int) (y + dheight - (ii) * 100 * ggy),
					(int) (movex + x + (X[ii + 1] - pmin) * ggx), (int) (y + dheight - (ii + 1) * 100 * ggy));
		}

	}

	void drawCoordinates(Graphics2D g, int x, int y, int dwidth, int dheight, String title, String xName, String yName,
			int X[], String xD, String yD) {
		g.setStroke(new BasicStroke(3));
		g.setColor(new Color(0, 0, 0, 250));
		int movex = 0;
		int pmin = searchMin(X) / 10 * 10;
		int pmax = searchMax(X);
		double ggx = 0;
		double ggy = 0;
		int intervalX = Math.round(((pmax - pmin) / 10) / 10.0f) * 10;// X����
		int intervalY = Math.round((fA.curaltStage - fA.initaltStage) * 100 / 800.0f) * 100;// Y��߶ȼ��
		if (pmax - pmin != 0) {
			ggx = (dwidth - movex) / (double) (pmax - pmin);
		}
		ggy = (double) (dheight) / (fA.curaltStage * 100);

		// ����
		g.setFont(new Font(app.DefaultFontName, Font.PLAIN, 16));
		g.drawString(title, x + dwidth / 2, y);
		y = y + 10;// ������10

		g.setFont(new Font(app.DefaultFontName, Font.PLAIN, 10));
		// x�����ͷ
		g.drawLine(x, y + dheight, x + dwidth, y + dheight);
		// g.drawLine(x + dwidth, y + dheight, x + dwidth - 5, y + dheight -
		// 5);��ͷ
		// g.drawLine(x + dwidth - 5, y + dheight + 5, x + dwidth, y +
		// dheight);��ͷ

		// x��̶�
		if (intervalX == 0)
			intervalX = 1;
		int ii = (int) ((pmax - pmin) / intervalX);
		for (; ii >= 0; ii--) {
			// ������̶�
			g.setStroke(new BasicStroke(1));
			g.drawLine((int) (movex + x + ii * intervalX * ggx), y + dheight, (int) (movex + x + ii * intervalX * ggx),
					y);
			// System.out.println("X����"+(pmin + ii * intervalX)+"λ��"+(int)
			// (movex + x + ii * intervalX * ggx));
			g.drawString(String.valueOf(pmin + ii * intervalX), (int) (movex + x + ii * intervalX * ggx),
					y + dheight + 15);
		}
		// x�ᵥλ
		g.setFont(new Font(app.DefaultFontName, Font.PLAIN, 14));
		g.drawString(xD, x + dwidth + 5, y + dheight);
		g.setFont(new Font(app.DefaultFontName, Font.PLAIN, 10));

		g.setStroke(new BasicStroke(3));

		// y�����ͷ
		g.drawLine(x, y + dheight, x, y);
		// g.drawLine(x - 5, y + 5, x, y);
		// g.drawLine(x + 5, y + 5, x, y);
		// y��̶�
		if (intervalY == 0)
			intervalY = 100;
		ii = (int) (fA.curaltStage * 100 / intervalY);
		for (; ii >= 0; ii--) {
			g.setStroke(new BasicStroke(1));
			g.drawLine(x, (int) (y + dheight - ii * intervalY * ggy), x + dwidth,
					(int) (y + dheight - ii * intervalY * ggy));

			g.drawString(String.valueOf(ii * intervalY), x - 30, (int) (y + dheight - ii * intervalY * ggy));
		}
		// y�ᵥλ
		
		g.setFont(new Font(app.DefaultFontName, Font.PLAIN, 14));
		g.drawString(yD, x - 5, y - 10);

		// ���
		for (ii = fA.initaltStage; ii <= fA.curaltStage - 1; ii++) {
			
			g.drawOval((int) (movex + x + (X[ii] - pmin) * ggx) - 1, (int) (y + dheight - (ii) * 100 * ggy) - 1, 2, 2);
		}

		// ����
		g.setStroke(new BasicStroke(1));
		for (ii = fA.initaltStage; ii < fA.curaltStage - 1; ii++) {
			if (Math.abs(X[ii] - X[ii + 1]) > 100) {
				X[ii + 1] = X[ii + 2];
			}
			g.drawLine((int) (movex + x + (X[ii] - pmin) * ggx), (int) (y + dheight - (ii) * 100 * ggy),
					(int) (movex + x + (X[ii + 1] - pmin) * ggx), (int) (y + dheight - (ii + 1) * 100 * ggy));
		}

	}

	public void init(controller c, flightAnalyzer A) {
		// ���⴦��
		xc = c;
		fA = A;

		fA.time[fA.initaltStage] = 0;
		fA.power[fA.initaltStage] = fA.power[fA.initaltStage + 1] + fA.power[fA.initaltStage + 1]
				- fA.power[fA.initaltStage + 2];
		fA.thrust[fA.initaltStage] = fA.thrust[fA.initaltStage + 1] + fA.thrust[fA.initaltStage + 1]
				- fA.thrust[fA.initaltStage + 2];
		fA.eff[fA.initaltStage] = fA.eff[fA.initaltStage + 1] + fA.eff[fA.initaltStage + 1]
				- fA.eff[fA.initaltStage + 2];
		fA.sep[fA.initaltStage] = fA.sep[fA.initaltStage + 1] + fA.sep[fA.initaltStage + 1]
				- fA.sep[fA.initaltStage + 2];
		
		fY=new double[fA.curaltStage-fA.initaltStage];
		int xk=fA.initaltStage;
		for(int i=0;i<fY.length;i++){
			fY[i]=xk*100;
			xk++;
		}
	
		getdata(fA.type);
		
		setFrameOpaque();

		this.setBounds(0, 0, 1200, 830);

		panel = new WebPanel() {

			private static final long serialVersionUID = -9061280572815010060L;

			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				// ��ʼ��ͼ
				// g2d.draw

				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

				// ��������ϵ
				if (pixIndex == 0)
					drawCoordinates(g2d, 50, 50, 1024, 576, language.dFTitle1, language.dFTitle1X, language.dFTitle1Y,
							fA.time, "s", "m");
				// ���ƿ̶�
				if (pixIndex == 1) {
					if (fA.engineType == 0) {
						drawCoordinates(g2d, 50, 50, 1024, 576, language.dFTitle2, language.dFTitle2X,
								language.dFTitle2Y, fA.power, "bhp", "m");
					} else {
						drawCoordinates(g2d, 50, 50, 1024, 576, language.dFTitle3, language.dFTitle3X,
								language.dFTitle3Y, fA.thrust, "kg", "m");
					}
				}
				if (pixIndex == 2)
					drawCoordinates(g2d, 50, 50, 1024, 576, language.dFTitle4, language.dFTitle4X, language.dFTitle4Y,
							fA.eff, "bhp", "m");
				if (pixIndex == 3)
					drawCoordinates(g2d, 50, 50, 1024, 576, language.dFTitle5, language.dFTitle5X, language.dFTitle5Y,
							fA.sep, "m/s", "m");
				if(pixIndex==5){
					int xk=fA.initaltStage;
					fX=new double[fA.curaltStage-fA.initaltStage];
					for(int i=0;i<fX.length;i++){
						fX[i]=fA.time[xk];
						//System.out.println(fX[i]);
						xk++;
					}
					double xmin=0;
					double xmax=findMax(blkx.loc.x)>findMax(fA.time)?findMax(blkx.loc.x):findMax(fA.time);
					double ymin=0;
					double ymax=findMax(blkx.loc.y)>findMax(fY)?findMax(blkx.loc.y):findMax(fY);
					int dwidth=1024;
					int dheight=576;
					int xgap=Math.round((((int)xmax+1 - (int)xmin) / 10) / 10.0f) * 10;
					int ygap=1000;
					int pxmin = (int) xmin;
					int pxmax = (int) xmax + xgap;
					int pymin = (int) ymin;
					int pymax = (int) ymax + ygap;
					double ggx4 = 0;
					double ggy4 = 0;
					if (pxmax - pxmin != 0) {
						ggx4 =(double) dwidth / (double) (pxmax - pxmin);
					}
					if (pymax - pymin != 0) {
						ggy4 =  (double)dheight/ (double)(pymax-pymin);
					}
					

					drawXY(g2d,50,50,dwidth,dheight,"�����Ա�","ʱ��","�߶�","s","m",xmin,xmax,ymin,ymax,xgap,ygap);
					drawPoint(g2d,50,50,dwidth,dheight,ggx4,ggy4,blkx.loc.x,blkx.loc.y,pxmin,pymin,Color.blue);
					drawExample(g2d,50,50,dheight,Color.blue,"FM��ȡ����");
					drawPoint(g2d,50,50,dwidth,dheight,ggx4,ggy4,fX,fY,pxmin,pymin,Color.red);
					drawExample(g2d,50,60,dheight,Color.red,"�Է�����");
				}
				if(pixIndex==6){
					double xmin=findMin(blkx.loc2.x)<findMin(blkx.loc1.x)?findMin(blkx.loc2.x):findMin(blkx.loc1.x);
					double xmax=findMax(blkx.loc1.x)>findMax(blkx.loc1.x)?findMax(blkx.loc1.x):findMax(blkx.loc1.x);
					//System.out.println(xmin+" "+xmax);
					double ymin=0;
					double ymax=findMax(blkx.loc1.y)>findMax(blkx.loc2.y)?findMax(blkx.loc1.y):findMax(blkx.loc2.y);
					int dwidth=1024;
					int dheight=576;
					int xgap=Math.round((((int)xmax+1 - (int)xmin) / 10) / 10.0f) * 10;
					int ygap=1000;
					int pxmin = (int) xmin;
					int pxmax = (int) xmax + xgap;
					int pymin = (int) ymin;
					int pymax = (int) ymax + ygap;
					double ggx4 = 0;
					double ggy4 = 0;
					if (pxmax - pxmin != 0) {
						ggx4 =(double) dwidth / (double) (pxmax - pxmin);
					}
					if (pymax - pymin != 0) {
						ggy4 =  (double)dheight/ (double)(pymax-pymin);
					}

					drawXY(g2d,50,50,dwidth,dheight,"�ٶ�-�߶����ߣ�FM�ļ�����������ݣ�","�ٶ�","�߶�","km/h","m",xmin,xmax,ymin,ymax,xgap,ygap);
					drawPoint(g2d,50,50,dwidth,dheight,ggx4,ggy4,blkx.loc1.x,blkx.loc1.y,pxmin,pymin,Color.red);
					drawExample(g2d,50,60,dheight,Color.red,"WEP�ٶ�");
					drawPoint(g2d,50,50,dwidth,dheight,ggx4,ggy4,blkx.loc2.x,blkx.loc2.y,pxmin,pymin,Color.blue);
					drawExample(g2d,50,50,dheight,Color.blue,"100%�����ٶ�");
					
				}
				if(pixIndex==7){
					double xmin=findMin(blkx.loc3.y);
					double xmax=findMax(blkx.loc3.y);
					//System.out.println(xmin+" "+xmax);
					double ymin=findMin(blkx.loc3.x);
					double ymax=findMax(blkx.loc3.x);
					int dwidth=1024;
					int dheight=576;
					int xgap=Math.round((((int)xmax+1 - (int)xmin) / 10) / 10.0f) * 10;
					int ygap=5;
					int pxmin = (int) xmin;
					int pxmax = (int) xmax + xgap;
					int pymin = (int) (ymin/5)*5;
					int pymax = (int) (ymax/5)*5 + ygap;
					double ggx4 = 0;
					double ggy4 = 0;
					if (pxmax - pxmin != 0) {
						ggx4 =(double) dwidth / (double) (pxmax - pxmin);
					}
					if (pymax - pymin != 0) {
						ggy4 =  (double)dheight/ (double)(pymax-pymin);
					}

					drawXY(g2d,50,50,dwidth,dheight,"�ٶ�-��ת�����ߣ�������FM�ļ���","�ٶ�","��ת��","km/h","Deg/s",xmin,xmax,ymin,ymax,xgap,ygap);
					drawPoint(g2d,50,50,dwidth,dheight,ggx4,ggy4,blkx.loc3.y,blkx.loc3.x,pxmin,pymin,new Color(0,0,0,250));
					drawExample(g2d,50,60,dheight,new Color(0,0,0,250),"��ת��");
					
				}
				// ���Ƶ�

				// ���ӵ�
			}

		};
		initpanel();
		WebButtonGroup G = createbuttonGroup();

		panel.add(G);
		panel.setLayout(null);
		G.setBounds(840, 700, 300, 50);
		this.add(panel);
		this.setShowMaximizeButton(false);
		this.getWebRootPaneUI().getTitleComponent().getComponent(1)
				.setFont(new Font(app.DefaultFont.getName(), Font.PLAIN, 14));// ����title����
		this.getWebRootPaneUI().getWindowButtons().setBorderColor(new Color(0, 0, 0, 0));
		// this.getWebRootPaneUI().getWindowButtons().setButtonsDrawBottom(false);
		this.getWebRootPaneUI().getWindowButtons().setButtonsDrawSides(false, false, false, false);
		this.getWebRootPaneUI().getWindowButtons().getWebButton(0).setTopBgColor(new Color(0, 0, 0, 0));
		this.getWebRootPaneUI().getWindowButtons().getWebButton(0).setBottomBgColor(new Color(0, 0, 0, 0));
		this.getWebRootPaneUI().getWindowButtons().getWebButton(1).setTopBgColor(new Color(0, 0, 0, 0));
		this.getWebRootPaneUI().getWindowButtons().getWebButton(1).setBottomBgColor(new Color(0, 0, 0, 0));
		// setShowWindowButtons(false);

		// setShowTitleComponent(false);
		setShowResizeCorner(false);
		setDefaultCloseOperation(2);
		setTitle(fA.type + language.dFTitleHZ);
		setAlwaysOnTop(true);

		// setFocusable(false);
		// setFocusableWindowState(false);// ȡ�����ڽ���
		setVisible(true);
		
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
			// System.out.println("drawFrameִ����");
			repaint();
		}
	}
}