package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;

import prog.app;
import prog.controller;
import prog.otherService;
import prog.service;

public class minimalHUD extends WebFrame implements Runnable {

	/**
	 * 
	 */
	public volatile boolean doit = true;
	Boolean on;
	controller xc;
	WebPanel panel;
	int HUDFontsize;
	int Width;
	int Height;
	int CrossX;
	int CrossY;
	int AoAFuselagePix;
	int Vx;
	service xs;
	otherService cs;
	Image A;
	Image C;
	BufferedImage B;
	String crosshairName;
	boolean busetexturecrosshair;
	int isDragging;
	int xx;
	int yy;
	private static final long serialVersionUID = -3898679368097973617L;
	String lineCompass;
	String lineHorizon;
	// String line2;
	// String line3;
	String lines[];
	String NumFont;
	Font drawFont;
	int CrossWidth;
	int CrossWidthVario;
	int pitch;
	int rightDraw;
	public int roundCompass;
	public boolean warnVne;
	public boolean drawAttitude;

	/*
	 * public static Image makeColorTransparent(Image im, final Color color) {
	 * ImageFilter filter = new RGBImageFilter() {
	 * 
	 * // the color we are looking for... Alpha bits are set to opaque public
	 * int markerRGB = color.getRGB() | 0xFF000000;
	 * 
	 * @Override public final int filterRGB(int x, int y, int rgb) { if ((rgb |
	 * 0xFF000000) == markerRGB) { // Mark the alpha bits as zero - transparent
	 * return 0x00FFFFFF & rgb; } else { // nothing to do return rgb; } } };
	 * 
	 * ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
	 * return Toolkit.getDefaultToolkit().createImage(ip); }
	 */
	public void setFrameOpaque() {
		this.getWebRootPaneUI().setMiddleBg(new Color(0, 0, 0, 0));// 中部透明
		this.getWebRootPaneUI().setTopBg(new Color(0, 0, 0, 0));// 顶部透明
		this.getWebRootPaneUI().setBorderColor(new Color(0, 0, 0, 0));// 内描边透明
		this.getWebRootPaneUI().setInnerBorderColor(new Color(0, 0, 0, 0));// 外描边透明
		setShadeWidth(0);
	}

	public void initpanel() {
		panel.setWebColoredBackground(false);
		panel.setBackground(new Color(0, 0, 0, 0));
	}

	public void drawCrossair(Graphics2D g, int dx, int dy, int CrossX, int CrossY, int CrossWidth) {
		int l = 4;

		g.setStroke(new BasicStroke(3));
		g.setColor(new Color(0, 0, 0, 75));

		// 圆圈
		g.drawOval((dx - CrossWidth) / 2, (dy - CrossWidth) / 2, CrossWidth, CrossWidth);
		// 横线1
		g.drawLine(CrossX - CrossWidth * l / 4, CrossY, CrossX - CrossWidth / 4, CrossY);
		// 横线2
		g.drawLine(CrossX + CrossWidth / 4, CrossY, CrossX + CrossWidth * l / 4, CrossY);
		// 竖线1
		g.drawLine(CrossX, CrossY - CrossWidth * l / 4, CrossX, CrossY - CrossWidth / 4);
		// 竖线2
		g.drawLine(CrossX, CrossY + CrossWidth / 4, CrossX, CrossY + CrossWidth * l / 4);

		g.setStroke(new BasicStroke(2));
		g.setColor(new Color(255, 215, 8, 255));
		// 圆圈
		g.drawOval((dx - CrossWidth) / 2, (dy - CrossWidth) / 2, CrossWidth, CrossWidth);
		// 横线1
		g.drawLine(CrossX - CrossWidth * l / 4, CrossY, CrossX - CrossWidth / 4, CrossY);
		// 横线2
		g.drawLine(CrossX + CrossWidth / 4, CrossY, CrossX + CrossWidth * l / 4, CrossY);
		// 竖线1
		g.drawLine(CrossX, CrossY - CrossWidth * l / 4, CrossX, CrossY - CrossWidth / 4);
		// 竖线2
		g.drawLine(CrossX, CrossY + CrossWidth / 4, CrossX, CrossY + CrossWidth * l / 4);
	}

	public int throttley = 0;
	public int OilX = 0;
	public int aoaY = 0;
	public boolean inAction = false;
	private boolean drawHudMach = false;
	public Color throttleColor;
	public Color aoaColor;
	public int throttleLineWidth = 1;

	public void drawTextseries(Graphics2D g, int x, int y) {
		int n = 0;
		g.setFont(drawFont);
		int kx = 0;
		// drawStringShade(g, x, n+y, lines[0], drawFont);
		int n5 = 5 * HUDFontsize;

		uiBaseElem.drawVRect(g, kx, n5 + 2, barWidth, throttley, 1, throttleColor);
		// uiBaseElem.__drawVRect(g, kx, n5 + 2, barWidth, throttley,
		// throttleLineWidth, app.colorNum, throttleColor);
		kx += barWidth;

		int round = roundCompass;
		if (drawAttitude) {
			g.setStroke(new BasicStroke(lineWidth + 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g.setColor(app.colorShadeShape);
			g.drawArc(lineWidth + aosX + rightDraw + round / 2, n5 - round / 2 + pitch, round, round, rollDeg, -180);
			g.drawLine(lineWidth + rightDraw + round, n5, lineWidth + round + aosX + rightDraw, n5 + pitch);
			g.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			if (realSpdPitch < 0)
				g.setColor(app.colorUnit);
			else
				g.setColor(app.colorNum);
			g.drawArc(lineWidth + rightDraw + aosX + round / 2, n5 - round / 2 + pitch, round, round, rollDeg, -180);
			g.drawLine(lineWidth + rightDraw + round, n5, lineWidth + round + aosX + rightDraw, n5 + pitch);
		}

		for (int i = 0; i < 5; i++) {

			//
			// g.setColor(app.lblShadeColorMinor);
			// g.drawString(lines[i], x + 1, n + y + 1);
			// // g.drawString(lines[i], x - 1, n + y - 1);
			//// g.drawString(lines[i], x - 1, n + y - 1);
			// g.setColor(app.lblNumColor);
			// if (i == 2 && inAction) {
			// g.setColor(app.warning);
			// }
			// if (i == 0 && warnVne) {
			// g.setColor(app.warning);
			// }
			// g.drawString(lines[i], x, n + y);

			// i = 0画直线提示
			if (i == 0) {

				// g.setColor(app.lblShadeColorMinor);
				// g.drawLine((HUDFontsize * 3) >> 1, 2 + y, rightDraw - 2, 2 +
				// y);
				// g.setColor(app.lblNumColor);
				// g.drawLine(((HUDFontsize * 3) >> 1) - 1, 1 + y, rightDraw -
				// 3, 1 + y);

				int linex = (HUDFontsize * 3) >> 1;
				int liney = 1 + y;

				// uiBaseElem.drawHRect(g, linex, liney, rightDraw - 2 - linex,
				// 2+lineWidth, 1, app.lblNumColor);
				// uiBaseElem.drawHRect(g, (int)((rightDraw - x) * availableAoA
				// / 30) + x, liney, (int)((rightDraw - x) * (30 - availableAoA)
				// / 30), 2+lineWidth, 1, app.lblNumColor);
				// availableAoA

				uiBaseElem.drawHRect(g, x + (rightDraw - aoaY), liney, aoaY, lineWidth + 3, 1, aoaColor);
				// uiBaseElem.drawHBar(g, x + (rightDraw - aoaY), liney, width,
				// height, aoaY, borderwidth, c);b

				uiBaseElem.__drawStringShade(g, x + rightDraw, liney - 1, 1, lineAoA, drawFontSmall, aoaColor);

			}

			if ((i == 2 && inAction) || (i == 0 && warnVne)) {
				uiBaseElem.__drawStringShade(g, x, n + y, 1, lines[i], drawFont, app.colorWarning);
			} else
				uiBaseElem.__drawStringShade(g, x, n + y, 1, lines[i], drawFont, app.colorNum);
			// if( i==1){
			n = n + HUDFontsize;
			// }
		}

		n += 2;

		// g.setColor(app.lblShadeColorMinor);
		// g.drawLine(3, n, 3, n - throttley);
		// g.setColor(app.lblNumColor);
		// g.drawLine(2, n - 1, 2, n - 1 - throttley);

		// uiBaseElem.drawVRect(g, kx, n, barWidth, aoaY, 1, app.warning);
		// uiBaseElem.drawVBar(g, kx, n, barWidth, AoAFuselagePix, aoaY, 1,
		// app.warning);
		// kx += barWidth;
		//

		// uiBaseElem.drawVBarTextNum(g, kx, n, barWidth, HUDFontsize * 5,
		// throttley, 1, app.lblNumColor, "", "T"+throttley, drawFont,
		// drawFont);
		// uiBaseElem.drawVBar(g, kx, n, barWidth, HUDFontsize * 5, throttley,
		// 1, app.lblNumColor);
		if (!drawAttitude) {
			/*
			 * // if (pitch > 0) { // uiBaseElem.drawVRect(g, kx, n, barWidth,
			 * pitch, 1, // app.colorWarning); // } else { //
			 * uiBaseElem.drawVRect(g, kx, n, barWidth, pitch, 1, app.colorNum);
			 * // } // kx += barWidth;
			 */ }

		// uiBaseElem.drawVBar(g, x + rightDraw, n, barWidth, AoAFuselagePix,
		// aoaY, 1, app.warning);
		// AoA
		// g.setColor(app.lblShadeColorMinor);
		// g.drawLine(1, n, 1, n - aoaY);
		// g.setColor(app.warning);
		// g.drawLine(0, n - 1, 0, n - 1 - aoaY);

		// g.setColor(app.lblShadeColorMinor);
		// g.drawLine(0, n-1,0, n-1 - aoaY );
		// g.setColor(app.warning);
		// g.drawLine(1, n, 1, n-aoaY);
		//
		// 燃油量
		// int rightDraw = (int)(HUDFontsize * 4.5f + 1);
		// 引擎健康度
		// g.setColor(app.lblShadeColorMinor);
		// g.drawLine(rightDraw, n, -2 + (rightDraw - OilX), n);
		// g.setColor(app.lblNumColor);
		// g.drawLine(rightDraw - 1, n-1, -2 + (rightDraw - OilX) - 1, n-1);
		//
		// 方位

		// g.setColor(app.lblShadeColorMinor);
		// g.drawLine(rightDraw, n, rightDraw, n - pitch);
		// if (pitch > 0)
		// g.setColor(app.lblNumColor);
		// else
		// g.setColor(app.warning);
		// if (pitch > 0){
		// uiBaseElem.drawVRect(g, rightDraw, n, 5, pitch, 1, app.lblNumColor);
		// }
		// else{
		// uiBaseElem.drawVRect(g, rightDraw, n, 5, pitch, 1, app.warning);
		// }

		// g.drawLine(rightDraw - 1, n - 1, rightDraw - 1, n - 1 - pitch);

		// 画一个半圆

		// 绘制方向
		n += 2;

		// 2倍半径
		int r = roundCompass;

		g.setStroke(new BasicStroke(lineWidth + 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g.setColor(app.colorShadeShape);
		// g.drawLine(2 + r + 1, n + r + 1, 2 + r + compassDx + 1, n + r -
		// compassDy + 1);
		// g.drawOval(2 + 1, n + 1, r + r, r + r);
		g.drawLine(kx + r, n + r, kx + r + compassDx, n + r - compassDy);
		g.drawOval(kx, n, r + r, r + r);

		// 引擎健康度
		// g.drawArc(2 + 3, n + 3, r + r - 4, r + r - 4, -180, OilX);

		g.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g.setColor(app.colorNum);
		g.drawLine(kx + r, n + r, kx + r + compassDx, n + r - compassDy);
		g.drawOval(kx, n, r + r, r + r);

		// g.setColor(app.warning);
		// g.drawArc(2 + 2, n + 2, r + r - 4, r + r - 4, -180, OilX);

		// g.setColor(app.lblShadeColorMinor);
		// g.drawString(lineHorizon, x + 1, n + y + 1);
		//
		// g.setColor(app.lblNumColor);
		// g.drawString(lineHorizon, x, n + y);

		// g.setColor(app.lblShadeColorMinor);
		// g.drawString(lineCompass, x + 1, n + (r - HUDFontsize / 2) + y + 1);
		//
		// g.setColor(app.lblNumColor);
		// g.drawString(lineCompass, x, n + (r - HUDFontsize / 2) + y);

		uiBaseElem.drawStringShade(g, x, n + (r - HUDFontsize / 2) + y, 1, lineCompass, drawFont);
		n = n + 3 * roundCompass;

		// drawLabelBOSType(g, 0, n, 1, drawFont, drawFont, drawFont, "500",
		// "IAS", "km/h");
		// drawLabelBOSType(g, 0, n, 1,)
		// g.drawLine(0, Height-1, 0, throttley );
		// g.drawLine(0, 0, 0, Height - 1 - throttley);
	}

	// public void drawTextseries2(Graphics2D g, int x, int y) {
	// int n = 0;
	// g.setFont(drawFont);
	//
	// g.setColor(Color.gray);
	// g.drawString(line3, x + 1, n + y + 1);
	// g.setColor(new Color(255, 215, 8, 100));
	// g.drawString(line3, x, n + y);
	//
	// }

	/*
	 * private static BufferedImage setAlpha(String srcImageFile,int alpha) {
	 * 
	 * try { //读取图片 FileInputStream stream = new FileInputStream(new
	 * File(srcImageFile));// 指定要读取的图片
	 * 
	 * // 定义一个字节数组输出流，用于转换数组 ByteArrayOutputStream os = new
	 * ByteArrayOutputStream();
	 * 
	 * byte[] data =new byte[1024];// 定义一个1K大小的数组 while (stream.read(data) !=
	 * -1) { os.write(data); }
	 * 
	 * ImageIcon imageIcon = new ImageIcon(os.toByteArray()); BufferedImage
	 * bufferedImage = new BufferedImage(imageIcon.getIconWidth(),
	 * imageIcon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR); Graphics2D g2D
	 * = (Graphics2D) bufferedImage.getGraphics();
	 * g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
	 * 
	 * //判读透明度是否越界 if (alpha < 0) { alpha = 0; } else if (alpha > 10) { alpha =
	 * 10; }
	 * 
	 * // 循环每一个像素点，改变像素点的Alpha值 for (int j1 = bufferedImage.getMinY(); j1 <
	 * bufferedImage.getHeight(); j1++) { for (int j2 = bufferedImage.getMinX();
	 * j2 < bufferedImage.getWidth(); j2++) { int rgb = bufferedImage.getRGB(j2,
	 * j1); rgb = ((alpha * 255 / 10) << 24) | (rgb & 0x00ffffff);
	 * bufferedImage.setRGB(j2, j1, rgb); } } g2D.drawImage(bufferedImage, 0, 0,
	 * imageIcon.getImageObserver());
	 * 
	 * // 生成图片为PNG
	 * 
	 * return bufferedImage;
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return null;
	 * 
	 * }
	 */

	public void initPreview(controller c) {
		init(c, null, null);
		// setShadeWidth(10);
		this.setVisible(false);
		this.getWebRootPaneUI().setTopBg(app.previewColor);
		this.getWebRootPaneUI().setMiddleBg(app.previewColor);
		// setFocusableWindowState(true);
		// setFocusable(true);

		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				/*
				 * if(A.tag==0){ if(f.mode==1){ A.setVisible(false);
				 * A.visibletag=0; } }
				 */
			}

			public void mousePressed(MouseEvent e) {
				isDragging = 1;
				xx = e.getX();
				yy = e.getY();

			}

			public void mouseReleased(MouseEvent e) {
				if (isDragging == 1) {
					isDragging = 0;
				}
				/*
				 * if(A.tag==0){ A.setVisible(false); }
				 */
			}
			/*
			 * public void mouseReleased(MouseEvent e){ if(A.tag==0){
			 * A.setVisible(true); } }
			 */
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (isDragging == 1) {
					int left = getLocation().x;
					int top = getLocation().y;
					setLocation(left + e.getX() - xx, top + e.getY() - yy);
					setVisible(true);
					repaint();
				}
			}
		});

		this.setCursor(null);
		setVisible(true);
	}

	public String ILbl = "I";
	public String HLbl = "H";
	public String SLbl = "S";
	private boolean crossOn;
	private int barWidth;
	private int lineWidth;
	private float aoaLength;
	private Font drawFontSmall;
	private boolean disableAoA;
	private Container root;
	private int rollDeg;

	public void init(controller c, service s, otherService os) {
		int lx;
		int ly;

		xs = s;
		xc = c;
		Vx = 0;
		cs = os;
		setFrameOpaque();

		if (xc.getconfig("MonoNumFont") != "")
			NumFont = xc.getconfig("MonoNumFont");
		else
			NumFont = app.DefaultNumfontName;
		if (xc.getconfig("crosshairX") != "")
			lx = Integer.parseInt(xc.getconfig("crosshairX"));
		else
			lx = (app.ScreenWidth - Width) / 2;
		if (xc.getconfig("crosshairY") != "")
			ly = Integer.parseInt(xc.getconfig("crosshairY"));
		else
			ly = (app.ScreenHeight - Height) / 2;
		if (xc.getconfig("crosshairScale") != "")
			CrossWidth = Integer.parseInt(xc.getconfig("crosshairScale"));
		else
			CrossWidth = 70;
		if (CrossWidth == 0)
			CrossWidth = 1;
		if (xc.getconfig("crosshairName") != "")
			crosshairName = xc.getconfig("crosshairName");
		else
			crosshairName = "";
		// System.out.println(xc.getconfig("usetexturecrosshair"));
		if (xc.getconfig("displayCrosshair") != "") {
			crossOn = Boolean.parseBoolean(xc.getconfig("displayCrosshair"));

		} else {
			crossOn = false;
		}

		if (xc.getconfig("usetexturecrosshair") != "")
			busetexturecrosshair = Boolean.parseBoolean(xc.getconfig("usetexturecrosshair"));
		else
			busetexturecrosshair = false;

		if (xc.getconfig("drawHUDtext") != "") {
			on = Boolean.parseBoolean(xc.getconfig("drawHUDtext"));

		} else {
			on = true;
		}
		if (xc.getconfig("drawHUDAttitude") != "") {
			drawAttitude = Boolean.parseBoolean(xc.getconfig("drawHUDAttitude"));
		} else {
			drawAttitude = true;
		}

		HUDFontsize = CrossWidth / 4;
		barWidth = HUDFontsize / 4;
		lineWidth = HUDFontsize / 10;
		if (!crossOn)
			Width = (int) (CrossWidth * 2) - HUDFontsize;
		else {
			Width = (int) (CrossWidth * 2);
		}
		Height = (int) (CrossWidth * 2);
		CrossWidthVario = CrossWidth;
		if (lineWidth == 0)
			lineWidth = 1;
		roundCompass = (int) (Math.round(HUDFontsize * 0.75f));
		rightDraw = (int) (HUDFontsize * 3.5f);
		if (xc.getconfig("hudMach") != "")
			drawHudMach = Boolean.parseBoolean(c.getconfig("hudMach"));

		if (xc.getconfig("disableHUDSpeedLabel") != "")
			if (Boolean.parseBoolean(c.getconfig("disableHUDSpeedLabel"))) {
				ILbl = " ";
			}
		if (xc.getconfig("disableHUDHeightLabel") != "")
			if (Boolean.parseBoolean(c.getconfig("disableHUDHeightLabel"))) {
				HLbl = " ";
			}
		if (xc.getconfig("disableHUDSEPLabel") != "")
			if (Boolean.parseBoolean(c.getconfig("disableHUDSEPLabel"))) {
				SLbl = " ";
			}
		lines = new String[6];
		// for (int i = 0; i < 6; i++) {
		// lines[i] = new String();
		// }
		// lines[0] = ""
		lines[0] = ILbl + String.format("%5s", "360");
		lines[1] = HLbl + String.format("%5s", "1024");
		lines[3] = SLbl + String.format("%5s", "30");
		lines[4] = "G" + String.format("%5s", "2.0");
		lines[2] = "F" + String.format("%3s", "100");
		lines[2] += "BRK";
		lines[2] += "GEAR";
		// lines[4] = "A" + String.format("%5s", "1.0");
		lineCompass = " " + String.format("%5s", "102");
		lineHorizon = " " + String.format("%5s", "45");
		throttley = 100;
		aoaY = 10;
		disableAoA = false;
		throttleColor = app.colorShadeShape;
		lineAoA = String.format("α%3.0f", 20.0);
		if (xc.getconfig("disableHUDAoA") != "") {
			if (Boolean.parseBoolean(c.getconfig("disableHUDAoA"))) {
				lineAoA = "";
				disableAoA = true;
			}
		}
		aosX = 0;
		rollDeg = 0;
		aoaLength = rightDraw - HUDFontsize / 2;
		if (aoaY > rightDraw)
			aoaY = rightDraw;
		aoaColor = app.colorNum;
		// System.out.println(lx);
		// System.out.println(ly);
		A = Toolkit.getDefaultToolkit().createImage("image/gunsight/" + crosshairName + ".png");
		C = A.getScaledInstance(CrossWidth * 2, CrossWidth * 2, Image.SCALE_SMOOTH);
		// B=setAlpha("image/gunsight/" + crosshairName + ".png",200);
		// B.getScaledInstance(CrossWidth * 2, CrossWidth * 2,
		// Image.SCALE_SMOOTH);
		// A=makeColorTransparent(A,new Color(0,0,0));
		if (crossOn)
			this.setBounds(lx, ly, Width * 2, Height);
		else
			this.setBounds(lx, ly, Width, Height);
		drawFont = new Font(NumFont, Font.BOLD, HUDFontsize);
		drawFontSmall = new Font(NumFont, Font.BOLD, (int) (HUDFontsize * 0.75f));
		CrossX = Width / 2;
		CrossY = Height / 2;
		panel = new WebPanel() {

			private static final long serialVersionUID = -9061280572815010060L;

			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				// 开始绘图
				// g2d.draw
				g2d.setPaintMode();
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, app.graphAASetting);
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, app.textAASetting);
				// g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
				// RenderingHints.VALUE_RENDER_QUALITY);
				g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
						RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
				g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
				
				// if (busetexturecrosshair) {
				// g2d.drawImage(C, CrossX - CrossWidthVario, CrossY -
				// CrossWidthVario, CrossWidthVario * 2, CrossWidthVario * 2,
				// this);
				//
				// } else {
				// drawCrossair(g2d, CrossX, CrossY, CrossWidth);
				// }
				// 显示攻角和水平
				if (on) {
					drawTextseries(g2d, HUDFontsize / 2, HUDFontsize);

				}
				if (crossOn) {
					if (busetexturecrosshair) {
						g2d.drawImage(C, Width + CrossX - CrossWidthVario, CrossY - CrossWidthVario,
								CrossWidthVario * 2, CrossWidthVario * 2, this);
					} else {
						drawCrossair(g2d, 2 * Width, 1 * Height, Width + CrossX, CrossY, CrossWidth);
					}
				}
//				g.dispose();
			}
		};
		// initpanel();
		this.add(panel);
		// if (app.debug)setShadeWidth(8);
		// setShowWindowButtons(false);
		// setShowTitleComponent(false);
		// setShowResizeCorner(false);
		// setDefaultCloseOperation(3);
		// setTitle("hud");
		// setAlwaysOnTop(true);
		// root = this.getContentPane();
		// setFocusable(false);
		// setFocusableWindowState(false);// 取消窗口焦点
		// this.setCursor(app.blankCursor);
		// setVisible(true);

		uiWebLafSetting.setWindowOpaque(this);
		root = this.getContentPane();
		// this.createBufferStrategy(2);

	}

	public long hudCheckMili;
	private int compassDx;
	private int compassDy;
	private float availableAoA;
	private String lineAoA;
	private int aosX;

	float realSpdPitch;

	public void drawTick() {

		warnVne = false;
		int throttle = xs.sState.throttle;
		if (throttle >= 100) {
			throttleColor = app.colorNum;
		} else {
			if (throttle >= 85) {
				throttleColor = app.colorLabel;
			} else {
				if (throttle >= 50) {
					throttleColor = app.colorUnit;
				} 
//				else
//					throttleColor = app.colorShadeShape;
			}
		}
		throttley = throttle * HUDFontsize * 5 / 110;

		float compassRads = (float) Math.toRadians(xs.sIndic.compass);
		compassDx = (int) ((roundCompass * 1.2f) * Math.sin(compassRads));
		compassDy = (int) ((roundCompass * 1.2f) * Math.cos(compassRads));
		float aoa = xs.sState.AoA;
		// lineHorizon = " " + String.format("%5s", xs.sPitchUp);
		float p = xs.curLoadMinWorkTime < xs.fueltime ? xs.curLoadMinWorkTime : xs.fueltime;
		OilX = (int) (p * 360 / 600000);
		if (OilX > 360)
			OilX = 360;
		OilX = OilX - 360;
		// OilX = OilX - 180;
		float aviahp = xs.sIndic.aviahorizon_pitch;

		// int pitchLimit = HUDFontsize * 5;
		int pitchLimit = roundCompass << 1;
		realSpdPitch = -(aviahp + aoa);
		if (aviahp != -65535)
			pitch = (int) ((-aviahp * pitchLimit / 90.0f));
		else
			pitch = 0;
		// System.out.println(-(aviahp+aoa));
		if (xs.sState.AoS != -65535) {
			aosX = (int) (-xs.sState.AoS * pitchLimit / 30.0f);
		} else
			aosX = 0;
		rollDeg = (int) (-xs.sIndic.aviahorizon_roll);
		lineCompass = " " + String.format("%5s", xs.compass);
		if (drawHudMach)
			lines[0] = "M" + String.format("%5s", xs.M);
		else
			lines[0] = ILbl + String.format("%5s", xs.IAS);
		lines[1] = HLbl + String.format("%5s", xs.salt);

		String t;
		if (xs.SEP > 0) {
			t = "+" + xs.sSEP;
		} else
			t = xs.sSEP;
		// if(xs.sState.se)
		lines[3] = SLbl + String.format("%5s", t);

		if (xs.sState.Ny > 1.5f || xs.sState.Ny < -0.5f)
			lines[4] = "G" + String.format("%5s", xs.Ny);
		else {
			// 燃油量和增压器
			String s = xs.sfueltime;
			for (int i = 1; i < xs.sState.compressorstage; i++) {
				s += "C";
			}

			lines[4] = "L" + String.format("%5s", s);
			// if(xs.sState.compressorstage != 0){
			// lines[3] += "S"
			// +String.format("%d",xs.sState.compressorstage);
			// }
			s = null;
		}
		if (xs.sState.flaps > 0) {
			lines[2] = "F" + String.format("%3s", xs.flaps);
		} else {
			if (xs.hasWingSweepVario) {
				lines[2] = "W" + String.format("%3s", xs.sWingSweep);
			} else {
				lines[2] = "";
			}
		}
		inAction = false;
		if (xs.sState.airbrake > 0) {
			lines[2] += "BRK";
			if (xs.sState.airbrake != 100) {
				inAction |= true;
			}
		}

		if (xs.sState.gear > 0) {
			lines[2] += "GEAR";
			if (xs.sState.gear != 100)
				inAction |= true;
		}

		// 襟翼告警
		if (xs.sState.IAS > xs.flapAllowSpeed - 40) {
			//
			inAction = true;
		}

		if (xc.blkx != null && xc.blkx.valid) {
			// 速度

			if (xs.IASv + 50 > xc.blkx.vne) {
				warnVne = true;
			}

			int flaps = xs.sState.flaps > 0 ? xs.sState.flaps : 0;
			availableAoA = (xc.blkx.NoFlapsWing.AoACritHigh
					+ (xc.blkx.FullFlapsWing.AoACritHigh - xc.blkx.NoFlapsWing.AoACritHigh) * flaps / 100.0f) - aoa;

			if (availableAoA < 0.35f * xc.blkx.NoFlapsWing.AoACritHigh)
				aoaColor = app.colorWarning;
			else {
				aoaColor = app.colorNum;
			}
			aoaY = (int) (availableAoA * aoaLength / xc.blkx.NoFlapsWing.AoACritHigh);

			if (aoaY > rightDraw)
				aoaY = rightDraw;

			// if (xc.blkx.NoFlapsWing.AoACritHigh - xs.sState.AoA <
			// 0.33f*xc.blkx.NoFlapsWing.AoACritHigh)
			// lines[4] = "A" + String.format("%5.1f",
			// xc.blkx.NoFlapsWing.AoACritHigh - xs.sState.AoA);
			// else
			// lines[4] = "";
			AoAFuselagePix = (int) ((xc.blkx.NoFlapsWing.AoACritHigh - xc.blkx.Fuselage.AoACritHigh) * aoaLength
					/ xc.blkx.NoFlapsWing.AoACritHigh);
		} else {
			AoAFuselagePix = (int) (aoa * aoaLength / 15);
			aoaY = (int) (aoa * aoaLength / 30);
		}
		if (!disableAoA) {
			lineAoA = String.format("α%3.0f", aoa);
		}

		root.repaint();
	}

	@Override
	public void run() {
		while (doit) {
			try {
				Thread.sleep(app.threadSleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			long time = xs.SystemTime;
			if (time - hudCheckMili > xc.freqService) {

				hudCheckMili = xs.SystemTime;
				drawTick();
			}
		}
	}

}