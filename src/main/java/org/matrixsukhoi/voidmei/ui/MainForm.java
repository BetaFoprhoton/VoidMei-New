package org.matrixsukhoi.voidmei.ui;

import static javax.swing.JSplitPane.VERTICAL_SPLIT;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import com.alee.extended.button.WebSwitch;
import com.alee.extended.image.WebImage;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.extended.window.WebPopOver;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.slider.WebSlider;
import com.alee.laf.splitpane.WebSplitPane;
import com.alee.laf.tabbedpane.TabbedPaneStyle;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.laf.text.WebTextArea;
import com.alee.laf.text.WebTextField;
import com.alee.laf.window.WebFrame;
import com.alee.utils.ImageUtils;
import org.matrixsukhoi.voidmei.parser.Blkx;
import org.matrixsukhoi.voidmei.prog.Controller;
import org.matrixsukhoi.voidmei.prog.Lang;
import org.matrixsukhoi.voidmei.VoidMeiMain;


public class MainForm extends WebFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5917570099029563038L;
	public volatile Boolean doit = true;
	public int width;
	public int height;
	public Controller tc;
	int gcCount = 0;
	Container root;
	WebPanel jp1;
	WebPanel jp2;
	WebPanel jp3;
	WebPanel jp4;
	WebPanel jp5;
	WebPanel jp6;
	// test

	WebSwitch bFlightInfoSwitch;
	WebSwitch bFlightInfoEdge;
	WebComboBox sFlightInfoFont;
	WebSlider iFlightInfoFontSizeIncr;

	WebSwitch bEngineInfoSwitch;
	WebSwitch bEngineInfoEdge;
	WebComboBox fEngineInfoFont;
	WebSlider iEngineInfoFontSizeIncr;

	WebSwitch bCrosshairSwitch;
	WebSlider iCrosshairScale;
	WebSwitch bTextureCrosshairSwitch;
	WebComboBox sCrosshairName;
	WebSwitch bDrawHudTextSwitch;

	WebSwitch bEnableLogging;
	WebSwitch bEnableInformation;

	WebSwitch bEnableAxis;
	WebSwitch bEnableAxisEdge;
	WebSwitch bEnablegearAndFlaps;
	WebSwitch bEnablegearAndFlapsEdge;

	Boolean moveCheckFlag;

	WebSwitch bTempInfoSwitch;
	WebSlider iInterval;
	WebComboBox sGlobalNumFont;
	Color whiteBg = new Color(255, 255, 255, 255);

	public static String[] getFilelistNameNoEx(String[] list) {
		int i;
		String[] a = new String[list.length];
		for (i = 0; i < list.length; i++) {
			// VoidMeiMain.debugPrint(list[i]);
			a[i] = getFileNameNoEx(list[i]);
			// VoidMeiMain.debugPrint(list[i]);
		}
		return a;
	}

	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	public void setFrameOpaque() {
		this.getWebRootPaneUI().setMiddleBg(new Color(255, 255, 255, 255));// 中部透明
		this.getWebRootPaneUI().setTopBg(new Color(255, 255, 255, 255));// 顶部透明
		this.getWebRootPaneUI().setBorderColor(new Color(255, 255, 255, 255));// 内描边透明
		this.getWebRootPaneUI().setInnerBorderColor(new Color(255, 255, 255, 255));// 外描边透明
	}

	public void initJP(WebPanel panel) {
		panel.setWebColoredBackground(false);
		panel.setBackground(new Color(0, 0, 0, 0));
		panel.setUndecorated(false);
		// panel.setMargin ( 20 );
		// panel.setShadeTransparency((double) 0.1);

		panel.setShadeWidth(2);
		panel.setRound(StyleConstants.largeRound);
		panel.setBorderColor(new Color(0, 0, 0, 100));

		panel.setPaintBottom(false);
		panel.setPaintTop(false);
		// panel.setPaintLeft(false);
		panel.setPaintRight(false);
		// panel.setBorderColor(new Color(0,0, 0, 255));
	}

	public void initJPinside(WebPanel panel) {
		panel.setWebColoredBackground(false);
		panel.setBackground(new Color(0, 0, 0, 0));
		// panel.setUndecorated ( false);
		// panel.setMargin ( 20 );
		panel.setShadeTransparency((float) 0.1);
		panel.setShadeWidth(2);
		panel.setRound(StyleConstants.largeRound);
		panel.setBorderColor(new Color(0, 0, 0, 100));

	}

	// JP1布局
	public WebButton createButton(String text) {
		WebButton button = new WebButton(text);
		button.setShadeWidth(1);
		button.setDrawShade(true);
		// button.getWebUI().setInnerShadeColor(new Color(255,255,255,200));
		// button.getWebUI().setInnerShadeWidth(10);
		button.setFont(VoidMeiMain.defaultFontBig);
		button.setTopBgColor(new Color(0, 0, 0, 0));
		button.setBottomBgColor(new Color(0, 0, 0, 0));
		// button.setUndecorated(false);
		// button.setShadeWidth(1);
		button.setBorderPainted(false);

		return button;

	}

	public WebButtonGroup createbuttonGroup() {
		WebButton A = createButton(Lang.mCancel);
		WebButton B = createButton(Lang.mStart);

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

				System.exit(0);

			}
		});
		B.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (moveCheckFlag) {
					Controller.notification(Lang.mPlsclosePreview);
				} else {
					confirm();
				}
			}
		});
		// G.setPaintSides(false, false, false, false);
		return G;
	}

	public WebComboBox createCrosshairList(WebPanel topPanel, String text) {

		WebLabel lb = createWebLabel(text);
		File file = new File("image/gunsight");
		String[] filelist = file.list();
		// VoidMeiMain.debugPrint(file.list());
		filelist = getFilelistNameNoEx(filelist);
		// VoidMeiMain.debugPrint(filelist[0]);
		WebComboBox comboBox = new WebComboBox(filelist);
		comboBox.setWebColoredBackground(false);
		// comboBox.getWebUI().setDrawBorder(false);
		comboBox.setShadeWidth(1);
		comboBox.setDrawFocus(false);
		// comboBox.getWebUI().setWebColoredBackground(false);
		// comboBox.getComponent(0).setBackground(new Color(0, 0, 0, 0));
		comboBox.setFont(VoidMeiMain.defaultFont);

		// comboBox.getComponentPopupMenu().setBackground(new Color(0, 0, 0,
		// 0));
		// comboBox.getWebUI().setDrawBorder(false);
		comboBox.setExpandedBgColor(new Color(0, 0, 0, 0));
		// comboBox.getWebUI().setExpandedBgColor(new Color(0, 0, 0, 0));
		comboBox.setBackground(new Color(0, 0, 0, 0));

		topPanel.add(lb);
		topPanel.add(comboBox);
		return comboBox;
	}

	public WebComboBox createFMList(WebPanel topPanel, String text) {

		WebLabel lb = createWebLabel(text);
		File file = new File("data/aces/gamedata/flightmodels/fm");
		String[] filelist = file.list();
		// VoidMeiMain.debugPrint(file.list());
		filelist = getFilelistNameNoEx(filelist);
		// VoidMeiMain.debugPrint(filelist[0]);
		WebComboBox comboBox = new WebComboBox(filelist);
		comboBox.setWebColoredBackground(false);
		// comboBox.getWebUI().setDrawBorder(false);
		comboBox.setShadeWidth(1);
		comboBox.setDrawFocus(false);
		// comboBox.getWebUI().setWebColoredBackground(false);
		// comboBox.getComponent(0).setBackground(new Color(0, 0, 0, 0));
		comboBox.setFont(VoidMeiMain.defaultFont);

		// comboBox.getComponentPopupMenu().setBackground(new Color(0, 0, 0,
		// 0));
		// comboBox.getWebUI().setDrawBorder(false);
		comboBox.setExpandedBgColor(new Color(0, 0, 0, 0));
		// comboBox.getWebUI().setExpandedBgColor(new Color(0, 0, 0, 0));
		comboBox.setBackground(new Color(0, 0, 0, 0));

		topPanel.add(lb);
		topPanel.add(comboBox);
		return comboBox;
	}

	public WebComboBox createFontList(WebPanel topPanel, String text) {

		WebLabel lb = createWebLabel(text);
		/*
		 * WebList editableList = new WebList(VoidMeiMain.fonts); //
		 * VoidMeiMain.debugPrint(VoidMeiMain.fonts.length); editableList.setFont(VoidMeiMain.DefaultFont);
		 * editableList.setSelectionShadeWidth(0);
		 * editableList.setSelectionBorderColor(new Color(0, 0, 0, 0));
		 * editableList.setVisibleRowCount(5); editableList.setBackground(new Color(0,
		 * 0, 0, 0)); //editableList.setSelectionBackgroundColor(new Color(0, 0, 0, 0));
		 * editableList.getWebUI().setWebColoredSelection(false);
		 * editableList.getWebUI().setSelectionShadeWidth(1);
		 * editableList.getWebUI().setSelectionBorderColor(new Color(0, 0, 0, 100));
		 * editableList.getWebUI().setSelectionRound(5);
		 * editableList.getWebUI().setSelectionBackgroundColor(new Color(0, 0, 0, 0));
		 * editableList.getWebUI().setHighlightRolloverCell(true); //
		 * editableList.setSelectedIndex ( 0 ); // editableList.setSelectedValue("",
		 * true); // editableList.getSelectedValue(); editableList.setEditable(false);
		 * editableList.setSelectionBackground(new Color(0, 0, 0, 0));
		 * //editableList.setSelectionForeground(new Color(0, 0, 0, 0)); //
		 * VoidMeiMain.debugPrint(editableList.getScrollableTracksViewportWidth()); //
		 * editableList.setPreferredSize(400, 200); WebScrollPane WSP = new
		 * WebScrollPane(editableList); WSP.setWheelScrollingEnabled(true);
		 * WSP.getWebVerticalScrollBar().setPaintButtons(false); //
		 * WSP.getWebUI().setDrawBackground(false); WSP.setBackground(new Color(0, 0, 0,
		 * 0)); WSP.getWebVerticalScrollBar().setPaintTrack(false);
		 * WSP.setShadeWidth(0);
		 */

		WebComboBox comboBox = new WebComboBox(VoidMeiMain.fonts);
		comboBox.setWebColoredBackground(false);
		// comboBox.getWebUI().setDrawBorder(false);
		comboBox.setShadeWidth(1);
		comboBox.setDrawFocus(false);
		// comboBox.getWebUI().setWebColoredBackground(false);
		// comboBox.getComponent(0).setBackground(new Color(0, 0, 0, 0));
		comboBox.setFont(VoidMeiMain.defaultFont);

		// comboBox.getComponentPopupMenu().setBackground(new Color(0, 0, 0,
		// 0));
		// comboBox.getWebUI().setDrawBorder(false);
		comboBox.setExpandedBgColor(new Color(0, 0, 0, 0));
		// comboBox.getWebUI().setExpandedBgColor(new Color(0, 0, 0, 0));
		comboBox.setBackground(new Color(0, 0, 0, 0));

		topPanel.add(lb);
		topPanel.add(comboBox);
		return comboBox;
	}

	public WebLabel createWebLabel(String text) {
		WebLabel lb = new WebLabel();
		lb = new WebLabel(text);
		lb.setHorizontalAlignment(SwingConstants.CENTER);

		// lb.setDrawShade(true);

		lb.setForeground(new Color(0, 0, 0, 230));
		lb.setShadeColor(Color.WHITE);
		lb.setFont(VoidMeiMain.defaultFont);
		return lb;
	}

	public WebButton displayPreview;
	public WebSwitch battitudeIndicatorSwitch;
	public WebSwitch bFMPrintSwitch;
	private WebSwitch bcrosshairdisplaySwitch;
	private WebSwitch bFlightInfoIAS;
	private WebSwitch bFlightInfoTAS;
	private WebSwitch bFlightInfoMach;
	private WebSwitch bFlightInfoHeight;
	private WebSwitch bFlightInfoCompass;
	private WebSwitch bFlightInfoVario;
	private WebSwitch bFlightInfoSEP;
	private WebSwitch bFlightInfoAcc;
	private WebSwitch bFlightInfoWx;
	private WebSwitch bFlightInfoNy;
	private WebSwitch bFlightInfoTurn;
	private WebSwitch bFlightInfoTurnRadius;
	private WebSwitch bFlightInfoAoA;
	private WebSwitch bFlightInfoAoS;
	private WebSwitch bFlightInfoWingSweep;
	private WebSlider iflightInfoColumnNum;
	private WebSlider iengineInfoColumnNum;
	private WebSwitch bvoiceWarningSwitch;
	private WebSwitch bFlightInfoRadioAlt;
	private WebSwitch benableEngineControl;
	private WebSwitch bdrawShadeSwitch;
	private WebTextField cNumColor;
	private WebTextField cLabelColor;
	private WebTextField cUnitColor;
	private WebTextField cWarnColor;
	private WebTextField cShadeColor;
	private WebSwitch bEngineControlRadiator;
	private WebSwitch bEngineControlMixture;
	private WebSwitch bEngineControlPitch;
	private WebSwitch bEngineControlCompressor;
	private WebSwitch bEngineControlLFuel;
	private WebSwitch bEngineControlThrottle;
	private WebSwitch bEngineInfoHorsePower;
	private WebSwitch bEngineInfoThrust;
	private WebSwitch bEngineInfoRPM;
	private WebSwitch bEngineInfoPropPitch;
	private WebSwitch bEngineInfoEffEta;
	private WebSwitch bEngineInfoEffHp;
	private WebSwitch bEngineInfoPressure;
	private WebSwitch bEngineInfoPowerPercent;
	private WebSwitch bEngineInfoFuelKg;
	private WebSwitch bEngineInfoFuelTime;
	private WebSwitch bEngineInfoWepKg;
	private WebSwitch bEngineInfoWepTime;
	private WebSwitch bEngineInfoTemp;
	private WebSwitch bEngineInfoOilTemp;
	private WebSwitch bEngineInfoHeatTolerance;
	private WebSwitch bEngineInfoEngResponse;
	private WebSwitch bstatusSwitch;
	private WebSlider ivoiceVolume;
	private WebSwitch bAAEnable;
	private int isDragging;
	private int xx;
	private int yy;
	private WebComboBox bFMList0;
	private WebComboBox bFMList1;
	private WebComboBox sMonoFont;
	private DrawFrameImpl drawFrameSimpl;

	private void displayFM(WebComboBox bFMList, int idx) {
		String planeName = bFMList.getSelectedItem().toString();
		String path = "data/aces/gamedata/flightmodels/fm/" + planeName + ".Blkx";
//		System.out.println(path);
		Blkx fmblk = new Blkx(path, planeName);
//		fmblk.getload();
//		System.out.println(fmblk.fmdata);
		WebPopOver popOver = new WebPopOver(this);
//		popOver.setCloseOnFocusLoss ( true );
		popOver.setMargin(5);
		popOver.setLayout(new VerticalFlowLayout());
		WebButton closeButton = new WebButton(Lang.mCancel, new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				popOver.dispose();
			}
		});
		closeButton.setUndecorated(true);
		closeButton.setFont(VoidMeiMain.defaultFont);
		closeButton.setFontSize((int) (VoidMeiMain.defaultFontsize * 1.5f));
		closeButton.setFontStyle(Font.BOLD);
		WebTextArea textArea = new WebTextArea(fmblk.fmdata);
		popOver.add(textArea);
		popOver.setFont(VoidMeiMain.defaultFont);
		textArea.setFont(VoidMeiMain.defaultFont);
		textArea.setFontSize((int) (VoidMeiMain.defaultFontsize * 1.2f));
		popOver.add(closeButton);
		popOver.show(this);

		/* 增加拖动功能 */
		textArea.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				/*
				 * if(A.tag==0){ if(f.mode==1){ A.setVisible(false); A.visibletag=0; } }
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
			}
		});
		textArea.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				int left = popOver.getLocation().x;
				int top = popOver.getLocation().y;
				popOver.setLocation(left + e.getX() - xx, top + e.getY() - yy);
			}
		});

		// 移动位置
		popOver.setLocation(popOver.getLocation().x + idx * popOver.getSize().width, popOver.getLocation().y);

//		DrawFrameImpl = new DrawFrameImpl();
//		WebPopOver popOver1 = new WebPopOver(this);
////		popOver1.setMargin(5);
//		popOver1.setLayout(new VerticalFlowLayout());
//		WebPanel panel = new WebPanel() {
//
//			private static final long serialVersionUID = -9061280572815010060L;
//
//			public void paintComponent(Graphics g) {
//				
//				DrawFrameImpl.paintAction(g, fmblk);
//			}
//
//		};
//
//		panel.setBounds(0, Toolkit.getDefaultToolkit().getScreenSize().height - 500, 900, 500);
//		panel.setWebColoredBackground(false);
//		panel.setBackground(new Color(0,0,0,0));
//		panel.setLayout(null);
//		WebButton closeButton1 = new WebButton(Lang.mCancel, new ActionListener() {
//			@Override
//			public void actionPerformed(final ActionEvent e) {
//				popOver1.dispose();
//			}
//		});
//		closeButton1.setUndecorated(true);
//		closeButton1.setFont(VoidMeiMain.defaultFont);
//		closeButton1.setFontSize((int) (VoidMeiMain.defaultFontsize * 1.5f));
//		closeButton1.setFontStyle(Font.BOLD);
//		
//		
//		popOver1.add(panel);
//		popOver1.setFont(VoidMeiMain.defaultFont);
//		popOver1.add(closeButton1);
//		popOver1.show(this);
//		popOver1.repaint();
	}

	private String getColorText(final Color color) {
		return color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ", " + color.getAlpha();
	}

	private Color textToColor(String t) {
		int R, G, B, A;
		t = t.replaceAll(" ", "");
		String[] ts = t.split(",");
		if (ts.length < 4)
			return Color.BLACK;
		R = Integer.parseInt(ts[0]);
		G = Integer.parseInt(ts[1]);
		B = Integer.parseInt(ts[2]);
		A = Integer.parseInt(ts[3]);
		Color c = new Color(R, G, B, A);
//        VoidMeiMain.debugPrint(getColorText(c));
		return c;
	}

	public Color updateColorGroupColor(WebTextField trailing) {
		Color c = textToColor(trailing.getText());
//    	VoidMeiMain.debugPrint(getColorText(c));
		trailing.setLeadingComponent(new WebImage(ImageUtils.createColorIcon(c)));
		return c;
	}

	public WebTextField createColorGroup(WebPanel topPanel, String text) {
		// Initial color
		final Color initialColor = Color.WHITE;

		WebLabel lb = createWebLabel(text);
		String S = getColorText(initialColor);

//        textToColor(S);

		WebTextField trailing = new WebTextField(getColorText(initialColor), 15);
		trailing.setMargin(0, 0, 0, 2);
		trailing.setLeadingComponent(new WebImage(ImageUtils.createColorIcon(initialColor)));
		trailing.setShadeWidth(2);

		trailing.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Color c = updateColorGroupColor(trailing);
			}
		});

		topPanel.add(lb);
		topPanel.add(trailing);

		return trailing;

		// Simple color chooser
//        final WebButton colorChooserButton = new WebButton ( getColorText ( initialColor ), ImageUtils.createColorIcon ( initialColor ) );
//        colorChooserButton.setLeftRightSpacing ( 0 );
//        colorChooserButton.setMargin ( 0, 0, 0, 3 );
//        colorChooserButton.addActionListener ( new ActionListener ()
//        {
//            private WebColorChooserDialog colorChooser = null;
//            private Color lastColor = initialColor;
//
//            @Override
//            public void actionPerformed ( final ActionEvent e )
//            {
//                if ( colorChooser == null )
//                {
//                    colorChooser = new WebColorChooserDialog ( topPanel );
//                }
//                colorChooser.setColor ( lastColor );
//                colorChooser.setVisible ( true );
//
//                if ( colorChooser.getResult () == DialogOptions.OK_OPTION )
//                {
//                    final Color color = colorChooser.getColor ();
//                    lastColor = color;
//
//                    colorChooserButton.setIcon ( ImageUtils.createColorIcon ( color ) );
//                    colorChooserButton.setText ( getColorText ( color ) );
//                }
//            }
//        } );
//        GroupPanel t = new GroupPanel ( colorChooserButton );
//        topPanel.add(lb);
//        topPanel.add(t);
//        return t;

	}

	public WebButtonGroup createLBGroup(WebPanel topPanel) {
		displayPreview = createButton(Lang.mDisplayPreview);
		WebButton C = createButton(Lang.mSavePosition);
		WebButtonGroup G = new WebButtonGroup(true, displayPreview, C);
		displayPreview.setPreferredWidth(120);

		C.setPreferredWidth(120);
		displayPreview.setFont(VoidMeiMain.defaultFont);
		C.setFont(VoidMeiMain.defaultFont);
		G.setButtonsShadeWidth(3);

		// WebLabel lb=createWebLabel("调整位置");
		displayPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (moveCheckFlag == false) {

					Controller.notification(Lang.mMovePanel);
					saveconfig();
					tc.Preview();

					moveCheckFlag = true;
				} else {
//					VoidMeiMain.debugPrint("重置\n");
//					config_init();
					Controller.notification(Lang.mPreviewWarning);
				}
			}
		});
		C.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (moveCheckFlag) {
					Controller.notification(Lang.mPositionSaved);
					tc.endPreview();
					moveCheckFlag = false;
				} else {
					Controller.notification(Lang.mPreviewNotOpen);
				}
			}
		});
		G.setButtonsDrawSides(false, false, false, true);

		topPanel.add(G);
		return G;
	}

	public WebButtonGroup createLBGroupFM(WebPanel topPanel, WebComboBox fmSelectd0, WebComboBox fmSelectd1) {
		displayPreview = createButton(Lang.mDisplayPreview);
		WebButton C = createButton(Lang.mSavePosition);
		/* 显示FM */
		WebButton D = createButton(Lang.mDisplayPreview);
		WebButtonGroup G = new WebButtonGroup(true, displayPreview, C, D);
		displayPreview.setPreferredWidth(120);

		C.setPreferredWidth(120);
		D.setPreferredWidth(120);
		displayPreview.setFont(VoidMeiMain.defaultFont);
		C.setFont(VoidMeiMain.defaultFont);
		G.setButtonsShadeWidth(3);
		D.setFont(VoidMeiMain.defaultFont);

		// WebLabel lb=createWebLabel("调整位置");
		displayPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (moveCheckFlag == false) {

					Controller.notification(Lang.mMovePanel);
					saveconfig();
					tc.Preview();

					moveCheckFlag = true;
				} else {
//					VoidMeiMain.debugPrint("重置\n");
//					config_init();
					Controller.notification(Lang.mPreviewWarning);
				}
			}
		});
		C.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (moveCheckFlag) {
					Controller.notification(Lang.mPositionSaved);
					tc.endPreview();
					moveCheckFlag = false;
				} else {
					Controller.notification(Lang.mPreviewNotOpen);
				}
			}
		});

		D.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				VoidMeiMain.debugPrint("打开FM");
				displayFM(fmSelectd0, 0);
				displayFM(fmSelectd1, 1);
			}
		});
		G.setButtonsDrawSides(false, false, false, true);

		topPanel.add(G);
		return G;
	}

	public WebSlider createLSGroup(WebPanel topPanel, String text, int min, int max, int size, int tick1, int tick2) {
		WebLabel lb = createWebLabel(text);
		WebSlider ws = new WebSlider(WebSlider.HORIZONTAL);

		ws.setMinimum(min);
		ws.setMaximum(max);
		ws.setDrawProgress(true);
		ws.setPaintTicks(true);
		ws.setPaintLabels(true);
		ws.setMinorTickSpacing(tick1);
		ws.setMajorTickSpacing(tick2);
		ws.setPreferredWidth(size);
		ws.setProgressShadeWidth(0);
		ws.setTrackShadeWidth(1);
		// slider1.setDrawThumb(false);
		ws.setThumbShadeWidth(1);
		ws.setThumbBgBottom(whiteBg);
		ws.setThumbBgTop(whiteBg);
		ws.setTrackBgBottom(whiteBg);
		ws.setTrackBgTop(whiteBg);
		ws.setProgressBorderColor(whiteBg);
		ws.setProgressTrackBgBottom(whiteBg);
		ws.setProgressTrackBgTop(whiteBg);

		topPanel.add(lb);
		topPanel.add(ws);
		return ws;
	}

	public WebSwitch createLCGroup(WebPanel topPanel, String text/* , GridBagConstraints s, GridBagLayout layout */) {

		WebLabel lb = createWebLabel(text);
		WebSwitch ws;
		ws = new WebSwitch();
		// ws.setShadeWidth(0);
		ws.getWebUI().setShadeWidth(0);
		ws.setWebColoredBackground(false);
		// VoidMeiMain.debugPrint(ws.getComponent(0).getIgnoreRepaint());
		// ws.getComponent(0).getIgnoreRepaint();
		ws.setBackground(whiteBg);
		ws.getWebUI().setPaintSides(true, false, true, false);
		// ws.getWebUI().setPaintSides(false, true, false, true);
		ws.setRound(5);
//		ws.setAnimate(false);
		ws.setShadeWidth(1);
		ws.getLeftComponent().setFont(new Font(VoidMeiMain.defaultNumfontName, Font.PLAIN, 14));
		ws.getRightComponent().setFont(new Font(VoidMeiMain.defaultNumfontName, Font.PLAIN, 14));
		ws.getLeftComponent().setDrawShade(false);
		ws.getRightComponent().setDrawShade(false);
		ws.getLeftComponent().setText("On");
		ws.getRightComponent().setText("Off");
		// ws.getFirstComponent().setEnabled(false);
		// ws.getWebUI().setPaintSideLines(false, false, false, false);
		// ws.getComponent(0).setBackground(new Color(0,0,0));
		/*
		 * layout.setConstraints(lb, s); s.gridx++; layout.setConstraints(ws, s);
		 */

		topPanel.add(lb);
		topPanel.add(ws);
		return ws;

	}

	public void createvoidWebLabel(WebPanel topPanel, String text) {
		WebLabel A = createWebLabel(text);
		topPanel.add(A);
	}

	public void initJP1() {
		initJP(jp1);
		WebPanel topPanel = new WebPanel();
		WebPanel bottomPanel = new WebPanel();
		initJPinside(topPanel);
		initJPinside(bottomPanel);
		WebSplitPane splitPane = new WebSplitPane(VERTICAL_SPLIT, topPanel, bottomPanel);
		splitPane.setOneTouchExpandable(true);
		// splitPane.setPreferredSize ( new Dimension ( 250, 200 ) );
		splitPane.setDividerLocation(320);
		splitPane.setContinuousLayout(false);
		splitPane.setDividerSize(0);
		splitPane.setDrawDividerBorder(false);
		splitPane.setOneTouchExpandable(false);
		splitPane.setEnabled(false);

		// topPanel

		if (VoidMeiMain.debug)
			bTempInfoSwitch = createLCGroup(topPanel, Lang.mP1statusBar);
		if (VoidMeiMain.debug)
			createvoidWebLabel(topPanel, Lang.mP1statusBarBlank);

		bstatusSwitch = createLCGroup(topPanel, Lang.mP1statusBar);
		createvoidWebLabel(topPanel, Lang.mP1statusBarBlank);

		bdrawShadeSwitch = createLCGroup(topPanel, Lang.mP1drawFontShape);
		createvoidWebLabel(topPanel, Lang.mP1drawFontShapeBlank);

		bAAEnable = createLCGroup(topPanel, Lang.mP1AAEnable);
		createvoidWebLabel(topPanel, Lang.mP1AAEnableBlank);

		sGlobalNumFont = createFontList(topPanel, Lang.mP1GlobalNumberFont);
		createvoidWebLabel(topPanel, Lang.mP1GlobalNumberFontBlank);

		cNumColor = createColorGroup(topPanel, Lang.mP1NumColor);
		createvoidWebLabel(topPanel, Lang.mP1NumColorBlank);
		cLabelColor = createColorGroup(topPanel, Lang.mP1LabelColor);
		createvoidWebLabel(topPanel, Lang.mP1LabelColorBlank);

		cUnitColor = createColorGroup(topPanel, Lang.mP1UnitColor);
		createvoidWebLabel(topPanel, Lang.mP1UnitColorBlank);

		cWarnColor = createColorGroup(topPanel, Lang.mP1WarnColor);
		createvoidWebLabel(topPanel, Lang.mP1WarnColorBlank);

		cShadeColor = createColorGroup(topPanel, Lang.mP1ShadeColor);
		createvoidWebLabel(topPanel, Lang.mP1ShadeColorBlank);

		iInterval = createLSGroup(topPanel, Lang.mP1Interval, 10, 300, 500, 5, 40);
		bvoiceWarningSwitch = createLCGroup(topPanel, Lang.mP1VoiceWarning);
		createvoidWebLabel(topPanel, Lang.mP1VoiceWarningBlank);

		ivoiceVolume = createLSGroup(topPanel, Lang.mP1voiceVolume, 0, 200, 300, 10, 50);
		createvoidWebLabel(topPanel, Lang.mP1voiceVolumeBlank);

		/*
		 * GridBagLayout layout1 = new GridBagLayout(); GridBagConstraints s1 = new
		 * GridBagConstraints(); s1.fill = GridBagConstraints.BOTH; s1.gridwidth = 1;
		 * s1.weightx = 0; s1.weighty = 0; s1.gridx = 0; s1.gridy = 0;
		 */
		// createLCGroup(topPanel, "显示发动机面板 ");

		// topPanel.setLayout(layout1);
		topPanel.setLayout(new FlowLayout());
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);
		topPanel.setLayout(layout);

		// bottomPanel

//		WebButtonGroup G = createbuttonGroup();
//		bottomPanel.add(G, BorderLayout.LINE_END);
		WebButtonGroup G = createbuttonGroup();
		bottomPanel.add(G, BorderLayout.LINE_END);
		WebButtonGroup G1 = createLBGroup(bottomPanel);
		bottomPanel.add(G1, BorderLayout.LINE_START);

		jp1.add(splitPane);

	}

	// JP2布局
	public void initJP2() {

		initJP(jp2);

		WebPanel topPanel = new WebPanel();
		WebPanel bottomPanel = new WebPanel();
		initJPinside(topPanel);
		initJPinside(bottomPanel);
		WebSplitPane splitPane = new WebSplitPane(VERTICAL_SPLIT, topPanel, bottomPanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(320);
		splitPane.setContinuousLayout(false);
		splitPane.setDividerSize(0);
		splitPane.setDrawDividerBorder(false);
		splitPane.setOneTouchExpandable(false);
		splitPane.setEnabled(false);

		bEngineInfoSwitch = createLCGroup(topPanel, Lang.mP2EnginePanel);
		createvoidWebLabel(topPanel, Lang.mP2EnginePanelBlank);
		bEngineInfoEdge = createLCGroup(topPanel, Lang.mP2EngineGlassEdge);
		createvoidWebLabel(topPanel, Lang.mP2EngineGlassEdgeBlank);

		bEngineInfoHorsePower = createLCGroup(topPanel, Lang.mP2eiHorsePower);
		createvoidWebLabel(topPanel, Lang.mP2eiHorsePowerBlank);
		bEngineInfoThrust = createLCGroup(topPanel, Lang.mP2eiThrust);
		createvoidWebLabel(topPanel, Lang.mP2eiThrustBlank);
		bEngineInfoRPM = createLCGroup(topPanel, Lang.mP2eiRPM);
		createvoidWebLabel(topPanel, Lang.mP2eiRPMBlank);
		bEngineInfoPropPitch = createLCGroup(topPanel, Lang.mP2eiPropPitch);
		createvoidWebLabel(topPanel, Lang.mP2eiPropPitchBlank);
		bEngineInfoEffEta = createLCGroup(topPanel, Lang.mP2eiEffEta);
		createvoidWebLabel(topPanel, Lang.mP2eiEffEtaBlank);
		bEngineInfoEffHp = createLCGroup(topPanel, Lang.mP2eiEffHp);
		createvoidWebLabel(topPanel, Lang.mP2eiEffHpBlank);
		bEngineInfoPressure = createLCGroup(topPanel, Lang.mP2eiPressure);
		createvoidWebLabel(topPanel, Lang.mP2eiPressureBlank);
		bEngineInfoPowerPercent = createLCGroup(topPanel, Lang.mP2eiPowerPercent);
		createvoidWebLabel(topPanel, Lang.mP2eiPowerPercentBlank);
		bEngineInfoFuelKg = createLCGroup(topPanel, Lang.mP2eiFuelKg);
		createvoidWebLabel(topPanel, Lang.mP2eiFuelKgBlank);
		bEngineInfoFuelTime = createLCGroup(topPanel, Lang.mP2eiFuelTime);
		createvoidWebLabel(topPanel, Lang.mP2eiFuelTimeBlank);
		bEngineInfoWepKg = createLCGroup(topPanel, Lang.mP2eiWepKg);
		createvoidWebLabel(topPanel, Lang.mP2eiWepKgBlank);
		bEngineInfoWepTime = createLCGroup(topPanel, Lang.mP2eiWepTime);
		createvoidWebLabel(topPanel, Lang.mP2eiWepTimeBlank);
		bEngineInfoTemp = createLCGroup(topPanel, Lang.mP2eiTemp);
		createvoidWebLabel(topPanel, Lang.mP2eiTempBlank);
		bEngineInfoOilTemp = createLCGroup(topPanel, Lang.mP2eiOilTemp);
		createvoidWebLabel(topPanel, Lang.mP2eiOilTempBlank);
		bEngineInfoHeatTolerance = createLCGroup(topPanel, Lang.mP2eiHeatTolerance);
		createvoidWebLabel(topPanel, Lang.mP2eiHeatToleranceBlank);
		bEngineInfoEngResponse = createLCGroup(topPanel, Lang.mP2eiEngResponse);
		createvoidWebLabel(topPanel, Lang.mP2eiEngResponseBlank);

//		bEngineInfoHp = createLCGroup(topPanel, language.mP4fiIAS);
//		createvoidWebLabel(topPanel,language.mP4fiIASBlank);
//		

		// createLCGroup(topPanel, "面板透明度 ");
		// createLBGroup(topPanel);
		fEngineInfoFont = createFontList(topPanel, Lang.mP2PanelFont);
		iEngineInfoFontSizeIncr = createLSGroup(topPanel, Lang.mP2FontAdjust, -6, 20, 200, 1, 4);
		iengineInfoColumnNum = createLSGroup(topPanel, Lang.mP4ColumnAdjust, 1, 16, 200, 1, 2);

		createvoidWebLabel(topPanel, Lang.mP2EngineBlank);

		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);
		topPanel.setLayout(layout);

		// bottomPanel

		WebButtonGroup G = createbuttonGroup();
		bottomPanel.add(G, BorderLayout.LINE_END);
		WebButtonGroup G1 = createLBGroup(bottomPanel);
		bottomPanel.add(G1, BorderLayout.LINE_START);
		jp2.add(splitPane);

	}

	// JP3布局
	public void initJP3() {
		initJP(jp3);

		WebPanel topPanel = new WebPanel();
		WebPanel bottomPanel = new WebPanel();
		initJPinside(topPanel);
		initJPinside(bottomPanel);
		WebSplitPane splitPane = new WebSplitPane(VERTICAL_SPLIT, topPanel, bottomPanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(320);
		splitPane.setDividerSize(0);
		splitPane.setContinuousLayout(false);
		splitPane.setDrawDividerBorder(false);
		splitPane.setOneTouchExpandable(false);
		splitPane.setEnabled(false);

		bCrosshairSwitch = createLCGroup(topPanel, Lang.mP3Crosshair);
		createvoidWebLabel(topPanel, Lang.mP3CrosshairBlank);
		bcrosshairdisplaySwitch = createLCGroup(topPanel, Lang.mP3CrosshairDisplay);
		createvoidWebLabel(topPanel, Lang.mP3CrosshairDisplayBlank);
		createvoidWebLabel(topPanel, Lang.mP3CrosshairBlank);
		bDrawHudTextSwitch = createLCGroup(topPanel, Lang.mP3Text);
		createvoidWebLabel(topPanel, Lang.mP3TextBlank);
		bTextureCrosshairSwitch = createLCGroup(topPanel, Lang.mP3CrosshairTexture);
		createvoidWebLabel(topPanel, Lang.mP3CrosshairTextureBlank);
		sCrosshairName = createCrosshairList(topPanel, Lang.mP3ChooseTexture);
		createvoidWebLabel(topPanel, Lang.mP3ChooseTextureBlank);
		iCrosshairScale = createLSGroup(topPanel, Lang.mP3CrosshairSize, 0, 200, 500, 5, 20);

		sMonoFont = createFontList(topPanel, Lang.mP3MonoFont);
		createvoidWebLabel(topPanel, Lang.mP3MonoFontBlank);

		// createLCGroup(topPanel, "面板透明度 ");
		// createLBGroup(topPanel);
		// sengineInfoFont = createFontList(topPanel);

		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);
		topPanel.setLayout(layout);

		// bottomPanel

		WebButtonGroup G = createbuttonGroup();
		bottomPanel.add(G, BorderLayout.LINE_END);
		WebButtonGroup G1 = createLBGroup(bottomPanel);
		bottomPanel.add(G1, BorderLayout.LINE_START);
		jp3.add(splitPane);
	}

	public void initJP4() {
		initJP(jp4);

		WebPanel topPanel = new WebPanel();
		WebPanel bottomPanel = new WebPanel();
		initJPinside(topPanel);
		initJPinside(bottomPanel);
		WebSplitPane splitPane = new WebSplitPane(VERTICAL_SPLIT, topPanel, bottomPanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(320);
		splitPane.setDividerSize(0);
		splitPane.setContinuousLayout(false);
		splitPane.setDrawDividerBorder(false);
		splitPane.setOneTouchExpandable(false);
		splitPane.setEnabled(false);

		bFlightInfoSwitch = createLCGroup(topPanel, Lang.mP4FlightInfoPanel);
		createvoidWebLabel(topPanel, Lang.mP4FlightInfoBlank);
		bFlightInfoEdge = createLCGroup(topPanel, Lang.mP4FlightInfoGlassEdge);
		createvoidWebLabel(topPanel, Lang.mP4FlightInfoGlassEdgeBlank);

		battitudeIndicatorSwitch = createLCGroup(topPanel, Lang.mP4attitudeIndicatorPanel);
		createvoidWebLabel(topPanel, Lang.mP4attitudeIndicatorPanelBlank);

		bFMPrintSwitch = createLCGroup(topPanel, Lang.mP4FMPanel);
		createvoidWebLabel(topPanel, Lang.mP4FMPanelBlank);

		bFlightInfoIAS = createLCGroup(topPanel, Lang.mP4fiIAS);
		createvoidWebLabel(topPanel, Lang.mP4fiIASBlank);

		bFlightInfoTAS = createLCGroup(topPanel, Lang.mP4fiTAS);
		createvoidWebLabel(topPanel, Lang.mP4fiIASBlank);

		bFlightInfoMach = createLCGroup(topPanel, Lang.mP4fiMach);
		createvoidWebLabel(topPanel, Lang.mP4fiMachBlank);

		bFlightInfoCompass = createLCGroup(topPanel, Lang.mP4fiCompass);
		createvoidWebLabel(topPanel, Lang.mP4fiCompassBlank);

		bFlightInfoHeight = createLCGroup(topPanel, Lang.mP4fiHeight);
		createvoidWebLabel(topPanel, Lang.mP4fiHeightBlank);

		bFlightInfoVario = createLCGroup(topPanel, Lang.mP4fiVario);
		createvoidWebLabel(topPanel, Lang.mP4fiVarioBlank);

		bFlightInfoSEP = createLCGroup(topPanel, Lang.mP4fiSEP);
		createvoidWebLabel(topPanel, Lang.mP4fiSEPBlank);

		bFlightInfoAcc = createLCGroup(topPanel, Lang.mP4fiAcc);
		createvoidWebLabel(topPanel, Lang.mP4fiAccBlank);

		bFlightInfoWx = createLCGroup(topPanel, Lang.mP4fiWx);
		createvoidWebLabel(topPanel, Lang.mP4fiWxBlank);

		bFlightInfoNy = createLCGroup(topPanel, Lang.mP4fiNy);
		createvoidWebLabel(topPanel, Lang.mP4fiNyBlank);

		bFlightInfoTurn = createLCGroup(topPanel, Lang.mP4fiTurn);
		createvoidWebLabel(topPanel, Lang.mP4fiTurnBlank);

		bFlightInfoTurnRadius = createLCGroup(topPanel, Lang.mP4fiTurnRadius);
		createvoidWebLabel(topPanel, Lang.mP4fiTurnRadiusBlank);

		bFlightInfoAoA = createLCGroup(topPanel, Lang.mP4fiAoA);
		createvoidWebLabel(topPanel, Lang.mP4fiAoABlank);

		bFlightInfoAoS = createLCGroup(topPanel, Lang.mP4fiAoS);
		createvoidWebLabel(topPanel, Lang.mP4fiAoSBlank);

		bFlightInfoWingSweep = createLCGroup(topPanel, Lang.mP4fiWingSweep);
		createvoidWebLabel(topPanel, Lang.mP4fiWingSweepBlank);

		bFlightInfoRadioAlt = createLCGroup(topPanel, Lang.mP4fiRadioAlt);
		createvoidWebLabel(topPanel, Lang.mP4fiRadioAltBlank);

		// createLCGroup(topPanel, "面板透明度 ");
		// createLBGroup(topPanel);
		sFlightInfoFont = createFontList(topPanel, Lang.mP4PanelFont);
		iFlightInfoFontSizeIncr = createLSGroup(topPanel, Lang.mP4FontAdjust, -6, 20, 200, 1, 4);
		iflightInfoColumnNum = createLSGroup(topPanel, Lang.mP4ColumnAdjust, 1, 16, 200, 1, 2);
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);
		topPanel.setLayout(layout);

		// bottomPanel

		WebButtonGroup G = createbuttonGroup();
		bottomPanel.add(G, BorderLayout.LINE_END);
		WebButtonGroup G1 = createLBGroup(bottomPanel);
		bottomPanel.add(G1, BorderLayout.LINE_START);

		jp4.add(splitPane);
	}

	public void initJP5() {
		initJP(jp5);
		WebPanel topPanel = new WebPanel();
		WebPanel bottomPanel = new WebPanel();
		initJPinside(topPanel);
		initJPinside(bottomPanel);
		WebSplitPane splitPane = new WebSplitPane(VERTICAL_SPLIT, topPanel, bottomPanel);
		splitPane.setOneTouchExpandable(true);
		// splitPane.setPreferredSize ( new Dimension ( 250, 200 ) );
		splitPane.setDividerLocation(320);
		splitPane.setDividerSize(0);
		splitPane.setContinuousLayout(false);
		splitPane.setDrawDividerBorder(false);
		splitPane.setOneTouchExpandable(false);
		splitPane.setEnabled(false);

		// topPanel
		bEnableLogging = createLCGroup(topPanel, Lang.mP5LoggingAndCharting);
		createvoidWebLabel(topPanel, Lang.mP5LoggingAndChartingBlank);
		bEnableInformation = createLCGroup(topPanel, Lang.mP5Information);
		createvoidWebLabel(topPanel, Lang.mP5InformationBlank);
		/* FM文件列表 */
		bFMList0 = createFMList(topPanel, Lang.mP5FMChoose + "0");
		createvoidWebLabel(topPanel, Lang.mP5FMChooseBlank);
		bFMList0.addActionListener(new ActionListener() {
			private int t = 0;

			public void actionPerformed(ActionEvent e) {
				if (t++ != 0)
					displayFM(bFMList0, 0);
			}
		});

		bFMList1 = createFMList(topPanel, Lang.mP5FMChoose + "1");
		createvoidWebLabel(topPanel, Lang.mP5FMChooseBlank);
		bFMList1.addActionListener(new ActionListener() {
			private int t = 0;

			public void actionPerformed(ActionEvent e) {
				if (t++ != 0)
					displayFM(bFMList1, 1);
			}
		});

		/*
		 * GridBagLayout layout1 = new GridBagLayout(); GridBagConstraints s1 = new
		 * GridBagConstraints(); s1.fill = GridBagConstraints.BOTH; s1.gridwidth = 1;
		 * s1.weightx = 0; s1.weighty = 0; s1.gridx = 0; s1.gridy = 0;
		 */
		// createLCGroup(topPanel, "显示发动机面板 ");

		// topPanel.setLayout(layout1);
		topPanel.setLayout(new FlowLayout());
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);
		topPanel.setLayout(layout);

		// bottomPanel
//		WebButtonGroup G1 = createLBGroupFM(bottomPanel, bFMList0, bFMList1);
		WebButtonGroup G1 = createLBGroup(bottomPanel);
		bottomPanel.add(G1, BorderLayout.LINE_START);
		WebButtonGroup G = createbuttonGroup();
		bottomPanel.add(G, BorderLayout.LINE_END);

		jp5.add(splitPane);

	}

	public void initJP6() {
		initJP(jp6);
		WebPanel topPanel = new WebPanel();
		WebPanel bottomPanel = new WebPanel();
		initJPinside(topPanel);
		initJPinside(bottomPanel);
		WebSplitPane splitPane = new WebSplitPane(VERTICAL_SPLIT, topPanel, bottomPanel);
		splitPane.setOneTouchExpandable(true);
		// splitPane.setPreferredSize ( new Dimension ( 250, 200 ) );
		splitPane.setDividerLocation(320);
		splitPane.setDividerSize(0);
		splitPane.setContinuousLayout(false);
		splitPane.setDrawDividerBorder(false);
		splitPane.setOneTouchExpandable(false);
		splitPane.setEnabled(false);

		// topPanel
		bEnableAxis = createLCGroup(topPanel, Lang.mP6AxisPanel);
		createvoidWebLabel(topPanel, Lang.mP6AxisPanelBlank);
		bEnableAxisEdge = createLCGroup(topPanel, Lang.mP6AxisEdge);
		createvoidWebLabel(topPanel, Lang.mP6AxisEdgeBlank);
		bEnablegearAndFlaps = createLCGroup(topPanel, Lang.mP6GearAndFlaps);
		bEnablegearAndFlapsEdge = createLCGroup(topPanel, Lang.mP6GearAndFlapsEdge);
		createvoidWebLabel(topPanel, Lang.mP6GearAndFlapsEdgeBlank);
		benableEngineControl = createLCGroup(topPanel, Lang.mP6engineControl);
		createvoidWebLabel(topPanel, Lang.mP6engineControlBlank);

		// 引擎控制

		// 油门
		bEngineControlThrottle = createLCGroup(topPanel, Lang.mP6ecThrottle);
		createvoidWebLabel(topPanel, Lang.mP6ecThrottleBlank);
		// 桨距
		bEngineControlPitch = createLCGroup(topPanel, Lang.mP6ecPitch);
		createvoidWebLabel(topPanel, Lang.mP6ecPitchBlank);
		// 混合比
		bEngineControlMixture = createLCGroup(topPanel, Lang.mP6ecMixture);
		createvoidWebLabel(topPanel, Lang.mP6ecMixtureBlank);
		// 散热器
		bEngineControlRadiator = createLCGroup(topPanel, Lang.mP6ecRadiator);
		createvoidWebLabel(topPanel, Lang.mP6ecRadiatorBlank);
		// 增压器
		bEngineControlCompressor = createLCGroup(topPanel, Lang.mP6ecCompressor);
		createvoidWebLabel(topPanel, Lang.mP6ecCompressorBlank);
		// 燃油量
		bEngineControlLFuel = createLCGroup(topPanel, Lang.mP6ecLFuel);
		createvoidWebLabel(topPanel, Lang.mP6ecLFuelBlank);

		/*
		 * GridBagLayout layout1 = new GridBagLayout(); GridBagConstraints s1 = new
		 * GridBagConstraints(); s1.fill = GridBagConstraints.BOTH; s1.gridwidth = 1;
		 * s1.weightx = 0; s1.weighty = 0; s1.gridx = 0; s1.gridy = 0;
		 */
		// createLCGroup(topPanel, "显示发动机面板 ");

		// topPanel.setLayout(layout1);
		topPanel.setLayout(new FlowLayout());
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);
		topPanel.setLayout(layout);

		// bottomPanel
		WebButtonGroup G1 = createLBGroup(bottomPanel);
		bottomPanel.add(G1, BorderLayout.LINE_START);
		WebButtonGroup G = createbuttonGroup();
		bottomPanel.add(G, BorderLayout.LINE_END);

		jp6.add(splitPane);

	}

	public void initPanel() {
		WebTabbedPane tabbedPane = new WebTabbedPane();
		// tabbedPane3.setPreferredSize ( new Dimension ( 150, 120 ) );
		tabbedPane.setTabPlacement(WebTabbedPane.LEFT);
		// tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		jp1 = new WebPanel();
		jp2 = new WebPanel();
		jp3 = new WebPanel();
		jp4 = new WebPanel();
		jp5 = new WebPanel();
		jp6 = new WebPanel();
		initJP1();
		initJP2();
		initJP3();
		initJP4();
		initJP5();
		initJP6();

		tabbedPane.addTab(Lang.mFlightInfo, jp4);
		tabbedPane.addTab(Lang.mEngineInfo, jp2);
		tabbedPane.addTab(Lang.mControlInfo, jp6);
		tabbedPane.addTab(Lang.mLoggingAndAnalysis, jp5);
		tabbedPane.addTab(Lang.mCrosshair, jp3);
		tabbedPane.addTab(Lang.mAdvancedOption, jp1);
		// tabbedPane.setTabBorderColor(new Color(0, 0, 0, 0));
		// tabbedPane.setContentBorderColor(new Color(0, 0, 0, 0));
		// tabbedPane.setShadeWidth(1);
		// tabbedPane.setSelectedBottomBg(new Color(0, 0, 0,20));
		// tabbedPane.setSelectedTopBg(new Color(0, 0, 0, 20));
		// tabbedPane.setBackgroundAt(1, new Color(0, 0, 0, 0));

		// tabbedPane.setSelectedForegroundAt(0,(new Color(0, 0, 0, 0));
		tabbedPane.setSelectedIndex(0);

		tabbedPane.setBackground(new Color(0, 0, 0, 0));
		// tabbedPane.getWebUI().setBackgroundColor(new Color(0,0,0,0));
		tabbedPane.setFont(VoidMeiMain.defaultFontBig);
		// tabbedPane.setShadeWidth(5);
		// tabbedPane.setForeground(new Color(255,255,255,255));
		// tabbedPane.setComponentOrientation(
		// ComponentOrientation.RIGHT_TO_LEFT);
		// tabbedPane.setBottomBg(new Color(255, 255, 255, 255));
		// tabbedPane.setTopBg(new Color(255, 255, 255, 255));
		tabbedPane.setPaintOnlyTopBorder(true);
		tabbedPane.setPaintBorderOnlyOnSelectedTab(true);
		tabbedPane.setTabbedPaneStyle(TabbedPaneStyle.attached);
		this.add(tabbedPane);
	}

	public void initConfig() {
		// tc.initconfig();
		// 从TC中取参数即可
		// VoidMeiMain.debugPrint(Boolean.parseBoolean(tc.getconfig("engineInfoSwitch")));

		bFlightInfoSwitch.setSelected(Boolean.parseBoolean(tc.getconfig("flightInfoSwitch")));
		bFlightInfoEdge.setSelected(Boolean.parseBoolean(tc.getconfig("flightInfoEdge")));
		sFlightInfoFont.setSelectedItem(tc.getconfig("flightInfoFontC"));
		iFlightInfoFontSizeIncr.setValue(Integer.parseInt(tc.getconfig("flightInfoFontaddC")));
		battitudeIndicatorSwitch.setSelected(Boolean.parseBoolean(tc.getconfig("enableAttitudeIndicator")));
		bFMPrintSwitch.setSelected(Boolean.parseBoolean(tc.getconfig("enableFMPrint")));

		bFlightInfoIAS.setSelected(!Boolean.parseBoolean(tc.getconfig("disableFlightInfoIAS")));
		bFlightInfoTAS.setSelected(!Boolean.parseBoolean(tc.getconfig("disableFlightInfoTAS")));
		bFlightInfoMach.setSelected(!Boolean.parseBoolean(tc.getconfig("disableFlightInfoMach")));
		bFlightInfoCompass.setSelected(!Boolean.parseBoolean(tc.getconfig("disableFlightInfoCompass")));
		bFlightInfoHeight.setSelected(!Boolean.parseBoolean(tc.getconfig("disableFlightInfoHeight")));
		bFlightInfoVario.setSelected(!Boolean.parseBoolean(tc.getconfig("disableFlightInfoVario")));
		bFlightInfoSEP.setSelected(!Boolean.parseBoolean(tc.getconfig("disableFlightInfoSEP")));
		bFlightInfoAcc.setSelected(!Boolean.parseBoolean(tc.getconfig("disableFlightInfoAcc")));
		bFlightInfoWx.setSelected(!Boolean.parseBoolean(tc.getconfig("disableFlightInfoWx")));
		bFlightInfoNy.setSelected(!Boolean.parseBoolean(tc.getconfig("disableFlightInfoNy")));
		bFlightInfoTurn.setSelected(!Boolean.parseBoolean(tc.getconfig("disableFlightInfoTurn")));
		bFlightInfoTurnRadius.setSelected(!Boolean.parseBoolean(tc.getconfig("disableFlightInfoTurnRadius")));
		bFlightInfoAoA.setSelected(!Boolean.parseBoolean(tc.getconfig("disableFlightInfoAoA")));
		bFlightInfoAoS.setSelected(!Boolean.parseBoolean(tc.getconfig("disableFlightInfoAoS")));
		bFlightInfoWingSweep.setSelected(!Boolean.parseBoolean(tc.getconfig("disableFlightInfoWingSweep")));
		bFlightInfoRadioAlt.setSelected(!Boolean.parseBoolean(tc.getconfig("disableFlightInfoRadioAlt")));

		iflightInfoColumnNum.setValue(Integer.parseInt(tc.getconfig("flightInfoColumn")));

		bEngineInfoSwitch.setSelected(Boolean.parseBoolean(tc.getconfig("engineInfoSwitch")));
		bEngineInfoEdge.setSelected(Boolean.parseBoolean(tc.getconfig("engineInfoEdge")));
		fEngineInfoFont.setSelectedItem(tc.getconfig("engineInfoFont"));
		iEngineInfoFontSizeIncr.setValue(Integer.parseInt(tc.getconfig("engineInfoFontadd")));
		iengineInfoColumnNum.setValue(Integer.parseInt(tc.getconfig("engineInfoColumn")));

		bEngineInfoHorsePower.setSelected(!Boolean.parseBoolean(tc.getconfig("disableEngineInfoHorsePower")));
		bEngineInfoThrust.setSelected(!Boolean.parseBoolean(tc.getconfig("disableEngineInfoThrust")));
		bEngineInfoRPM.setSelected(!Boolean.parseBoolean(tc.getconfig("disableEngineInfoRPM")));
		bEngineInfoPropPitch.setSelected(!Boolean.parseBoolean(tc.getconfig("disableEngineInfoPropPitch")));
		bEngineInfoEffEta.setSelected(!Boolean.parseBoolean(tc.getconfig("disableEngineInfoEffEta")));
		bEngineInfoEffHp.setSelected(!Boolean.parseBoolean(tc.getconfig("disableEngineInfoEffHp")));
		bEngineInfoPressure.setSelected(!Boolean.parseBoolean(tc.getconfig("disableEngineInfoPressure")));
		bEngineInfoPowerPercent.setSelected(!Boolean.parseBoolean(tc.getconfig("disableEngineInfoPowerPercent")));
		bEngineInfoFuelKg.setSelected(!Boolean.parseBoolean(tc.getconfig("disableEngineInfoFuelKg")));
		bEngineInfoFuelTime.setSelected(!Boolean.parseBoolean(tc.getconfig("disableEngineInfoFuelTime")));
		bEngineInfoWepKg.setSelected(!Boolean.parseBoolean(tc.getconfig("disableEngineInfoWepKg")));
		bEngineInfoWepTime.setSelected(!Boolean.parseBoolean(tc.getconfig("disableEngineInfoWepTime")));
		bEngineInfoTemp.setSelected(!Boolean.parseBoolean(tc.getconfig("disableEngineInfoTemp")));
		bEngineInfoOilTemp.setSelected(!Boolean.parseBoolean(tc.getconfig("disableEngineInfoOilTemp")));
		bEngineInfoHeatTolerance.setSelected(!Boolean.parseBoolean(tc.getconfig("disableEngineInfoHeatTolerance")));
		bEngineInfoEngResponse.setSelected(!Boolean.parseBoolean(tc.getconfig("disableEngineInfoEngResponse")));

		bCrosshairSwitch.setSelected(Boolean.parseBoolean(tc.getconfig("crosshairSwitch")));
		iCrosshairScale.setValue(Integer.parseInt(tc.getconfig("crosshairScale")));
		bTextureCrosshairSwitch.setSelected(Boolean.parseBoolean(tc.getconfig("usetexturecrosshair")));
		sCrosshairName.setSelectedItem(tc.getconfig("crosshairName"));
		bDrawHudTextSwitch.setSelected(Boolean.parseBoolean(tc.getconfig("drawHUDtext")));
		bcrosshairdisplaySwitch.setSelected(Boolean.parseBoolean(tc.getconfig("displayCrosshair")));
		sMonoFont.setSelectedItem(tc.getconfig("MonoNumFont"));

		bdrawShadeSwitch.setSelected(Boolean.parseBoolean(tc.getconfig("simpleFont")));
		bAAEnable.setSelected(Boolean.parseBoolean(tc.getconfig("AAEnable")));
		bvoiceWarningSwitch.setSelected(Boolean.parseBoolean(tc.getconfig("enableVoiceWarn")));
		if (VoidMeiMain.debug)
			bTempInfoSwitch.setSelected(Boolean.parseBoolean(tc.getconfig("usetempInfoSwitch")));
		sGlobalNumFont.setSelectedItem(tc.getconfig("GlobalNumFont"));

		iInterval.setValue(Integer.parseInt(tc.getconfig("Interval")));

		bstatusSwitch.setSelected(Boolean.parseBoolean(tc.getconfig("enableStatusBar")));
		ivoiceVolume.setValue(Integer.parseInt(tc.getconfig("voiceVolume")));
//		tc.setconfig("enableStatusBar", Boolean.toString(bstatusSwitch.isSelected()));
//		tc.setconfig("voiceVolumn", Integer.toString(ivoiceVolume.getValue()));

		// 颜色
		cNumColor.setText(getColorText(tc.getColorConfig("fontNum")));
		updateColorGroupColor(cNumColor);

		cLabelColor.setText(getColorText(tc.getColorConfig("fontLabel")));
		updateColorGroupColor(cLabelColor);

		cUnitColor.setText(getColorText(tc.getColorConfig("fontUnit")));
		updateColorGroupColor(cUnitColor);

		cWarnColor.setText(getColorText(tc.getColorConfig("fontWarn")));
		updateColorGroupColor(cWarnColor);

		cShadeColor.setText(getColorText(tc.getColorConfig("fontShade")));
		updateColorGroupColor(cShadeColor);

		bEnableLogging.setSelected(Boolean.parseBoolean(tc.getconfig("enableLogging")));
		bEnableInformation.setSelected(Boolean.parseBoolean(tc.getconfig("enableAltInformation")));
		bFMList0.setSelectedItem(tc.getconfig("selectedFM0"));
		bFMList1.setSelectedItem(tc.getconfig("selectedFM1"));

		bEnableAxis.setSelected(Boolean.parseBoolean(tc.getconfig("enableAxis")));
		bEnableAxisEdge.setSelected(Boolean.parseBoolean(tc.getconfig("enableAxisEdge")));
		bEnablegearAndFlaps.setSelected(Boolean.parseBoolean(tc.getconfig("enablegearAndFlaps")));
		bEnablegearAndFlapsEdge.setSelected(Boolean.parseBoolean(tc.getconfig("enablegearAndFlapsEdge")));
		benableEngineControl.setSelected(Boolean.parseBoolean(tc.getconfig("enableEngineControl")));

		bEngineControlThrottle.setSelected(!Boolean.parseBoolean(tc.getconfig("disableEngineInfoThrottle")));
		bEngineControlPitch.setSelected(!Boolean.parseBoolean(tc.getconfig("disableEngineInfoPitch")));
		bEngineControlMixture.setSelected(!Boolean.parseBoolean(tc.getconfig("disableEngineInfoMixture")));
		bEngineControlRadiator.setSelected(!Boolean.parseBoolean(tc.getconfig("disableEngineInfoRadiator")));
		bEngineControlCompressor.setSelected(!Boolean.parseBoolean(tc.getconfig("disableEngineInfoCompressor")));
		bEngineControlLFuel.setSelected(!Boolean.parseBoolean(tc.getconfig("disableEngineInfoLFuel")));
	}

	public void config_init() {
		tc.setconfig("flightInfoSwitch", Boolean.toString(Boolean.TRUE));
		tc.setconfig("flightInfoEdge", Boolean.toString(Boolean.FALSE));
		tc.setconfig("flightInfoFontC", VoidMeiMain.defaultFontName);
		tc.setconfig("flightInfoFontaddC", Integer.toString(0));

		tc.setconfig("engineInfoSwitch", Boolean.toString(bEngineInfoSwitch.isSelected()));
		tc.setconfig("engineInfoEdge", Boolean.toString(Boolean.FALSE));
		tc.setconfig("engineInfoFont", fEngineInfoFont.getSelectedItem().toString());
		tc.setconfig("engineInfoFontadd", Integer.toString(iEngineInfoFontSizeIncr.getValue()));

		tc.setconfig("crosshairSwitch", Boolean.toString(Boolean.FALSE));
		tc.setconfig("crosshairScale", Integer.toString(10));
		tc.setconfig("usetexturecrosshair", Boolean.toString(Boolean.FALSE));
		tc.setconfig("crosshairName", sCrosshairName.getSelectedItem().toString());
		tc.setconfig("drawHUDtext", Boolean.toString(Boolean.FALSE));
		tc.setconfig("displayCrossharir", Boolean.toString(Boolean.FALSE));

		if (VoidMeiMain.debug)
			tc.setconfig("usetempInfoSwitch", Boolean.toString(Boolean.FALSE));
//		tc.setconfig(", value);
		tc.setconfig("GlobalNumFont", VoidMeiMain.defaultNumfontName);
		tc.setconfig("Interval", Integer.toString(80));

		tc.setconfig("enableLogging", Boolean.toString(Boolean.FALSE));
		tc.setconfig("enableAltInformation", Boolean.toString(Boolean.FALSE));

		tc.setconfig("enableAxis", Boolean.toString(Boolean.FALSE));
		tc.setconfig("enableAxisEdge", Boolean.toString(Boolean.FALSE));
		tc.setconfig("enablegearAndFlaps", Boolean.toString(Boolean.FALSE));
		tc.setconfig("enablegearAndFlapsEdge", Boolean.toString(Boolean.FALSE));

	}

	public void saveconfig() {
		// VoidMeiMain.debugPrint(Boolean.toString(bengineInfoSwitch.isSelected()));

		tc.setconfig("flightInfoSwitch", Boolean.toString(bFlightInfoSwitch.isSelected()));
		tc.setconfig("flightInfoEdge", Boolean.toString(bFlightInfoEdge.isSelected()));
		tc.setconfig("enableAttitudeIndicator", Boolean.toString(battitudeIndicatorSwitch.isSelected()));

		tc.setconfig("enableFMPrint", Boolean.toString(bFMPrintSwitch.isSelected()));

		tc.setconfig("disableFlightInfoIAS", Boolean.toString(!bFlightInfoIAS.isSelected()));
		tc.setconfig("disableFlightInfoTAS", Boolean.toString(!bFlightInfoTAS.isSelected()));
		tc.setconfig("disableFlightInfoMach", Boolean.toString(!bFlightInfoMach.isSelected()));
		tc.setconfig("disableFlightInfoCompass", Boolean.toString(!bFlightInfoCompass.isSelected()));
		tc.setconfig("disableFlightInfoHeight", Boolean.toString(!bFlightInfoHeight.isSelected()));
		tc.setconfig("disableFlightInfoVario", Boolean.toString(!bFlightInfoVario.isSelected()));

		tc.setconfig("disableFlightInfoSEP", Boolean.toString(!bFlightInfoSEP.isSelected()));

		tc.setconfig("disableFlightInfoAcc", Boolean.toString(!bFlightInfoAcc.isSelected()));

		tc.setconfig("disableFlightInfoVario", Boolean.toString(!bFlightInfoVario.isSelected()));
		tc.setconfig("disableFlightInfoWx", Boolean.toString(!bFlightInfoWx.isSelected()));
		tc.setconfig("disableFlightInfoNy", Boolean.toString(!bFlightInfoNy.isSelected()));
		tc.setconfig("disableFlightInfoTurn", Boolean.toString(!bFlightInfoTurn.isSelected()));
		tc.setconfig("disableFlightInfoTurnRadius", Boolean.toString(!bFlightInfoTurnRadius.isSelected()));

		tc.setconfig("disableFlightInfoAoA", Boolean.toString(!bFlightInfoAoA.isSelected()));
		tc.setconfig("disableFlightInfoAoS", Boolean.toString(!bFlightInfoAoS.isSelected()));
		tc.setconfig("disableFlightInfoWingSweep", Boolean.toString(!bFlightInfoWingSweep.isSelected()));
		tc.setconfig("disableFlightInfoRadioAlt", Boolean.toString(!bFlightInfoRadioAlt.isSelected()));

		tc.setconfig("flightInfoColumn", Integer.toString(iflightInfoColumnNum.getValue()));
		// VoidMeiMain.debugPrint(sengineInfoFont.getSelectedItem().toString());
		tc.setconfig("flightInfoFontC", sFlightInfoFont.getSelectedItem().toString());
		tc.setconfig("flightInfoFontaddC", Integer.toString(iFlightInfoFontSizeIncr.getValue()));

		tc.setconfig("engineInfoSwitch", Boolean.toString(bEngineInfoSwitch.isSelected()));
		tc.setconfig("engineInfoEdge", Boolean.toString(bEngineInfoEdge.isSelected()));
		// VoidMeiMain.debugPrint(sengineInfoFont.getSelectedValue());
		tc.setconfig("engineInfoFont", fEngineInfoFont.getSelectedItem().toString());
		tc.setconfig("engineInfoFontadd", Integer.toString(iEngineInfoFontSizeIncr.getValue()));
		tc.setconfig("engineInfoColumn", Integer.toString(iengineInfoColumnNum.getValue()));

		tc.setconfig("disableEngineInfoHorsePower", Boolean.toString(!bEngineInfoHorsePower.isSelected()));
		tc.setconfig("disableEngineInfoThrust", Boolean.toString(!bEngineInfoThrust.isSelected()));
		tc.setconfig("disableEngineInfoRPM", Boolean.toString(!bEngineInfoRPM.isSelected()));
		tc.setconfig("disableEngineInfoPropPitch", Boolean.toString(!bEngineInfoPropPitch.isSelected()));
		tc.setconfig("disableEngineInfoEffEta", Boolean.toString(!bEngineInfoEffEta.isSelected()));
		tc.setconfig("disableEngineInfoEffHp", Boolean.toString(!bEngineInfoEffHp.isSelected()));
		tc.setconfig("disableEngineInfoPressure", Boolean.toString(!bEngineInfoPressure.isSelected()));
		tc.setconfig("disableEngineInfoPowerPercent", Boolean.toString(!bEngineInfoPowerPercent.isSelected()));
		tc.setconfig("disableEngineInfoFuelKg", Boolean.toString(!bEngineInfoFuelKg.isSelected()));
		tc.setconfig("disableEngineInfoFuelTime", Boolean.toString(!bEngineInfoFuelTime.isSelected()));
		tc.setconfig("disableEngineInfoWepKg", Boolean.toString(!bEngineInfoWepKg.isSelected()));
		tc.setconfig("disableEngineInfoWepTime", Boolean.toString(!bEngineInfoWepTime.isSelected()));
		tc.setconfig("disableEngineInfoTemp", Boolean.toString(!bEngineInfoTemp.isSelected()));
		tc.setconfig("disableEngineInfoOilTemp", Boolean.toString(!bEngineInfoOilTemp.isSelected()));
		tc.setconfig("disableEngineInfoHeatTolerance", Boolean.toString(!bEngineInfoHeatTolerance.isSelected()));
		tc.setconfig("disableEngineInfoEngResponse", Boolean.toString(!bEngineInfoEngResponse.isSelected()));

		tc.setconfig("crosshairSwitch", Boolean.toString(bCrosshairSwitch.isSelected()));
		tc.setconfig("crosshairScale", Integer.toString(iCrosshairScale.getValue()));
		tc.setconfig("usetexturecrosshair", Boolean.toString(bTextureCrosshairSwitch.isSelected()));
		tc.setconfig("crosshairName", sCrosshairName.getSelectedItem().toString());
		tc.setconfig("drawHUDtext", Boolean.toString(bDrawHudTextSwitch.isSelected()));
		tc.setconfig("displayCrosshair", Boolean.toString(bcrosshairdisplaySwitch.isSelected()));
		tc.setconfig("MonoNumFont", sMonoFont.getSelectedItem().toString());

		tc.setconfig("simpleFont", Boolean.toString(bdrawShadeSwitch.isSelected()));
		tc.setconfig("AAEnable", Boolean.toString(bAAEnable.isSelected()));

		tc.setconfig("enableVoiceWarn", Boolean.toString(bvoiceWarningSwitch.isSelected()));
		if (VoidMeiMain.debug)
			tc.setconfig("usetempInfoSwitch", Boolean.toString(bTempInfoSwitch.isSelected()));
		tc.setconfig("GlobalNumFont", sGlobalNumFont.getSelectedItem().toString());
		tc.setconfig("Interval", Integer.toString(iInterval.getValue()));

		tc.setconfig("enableStatusBar", Boolean.toString(bstatusSwitch.isSelected()));
		tc.setconfig("voiceVolume", Integer.toString(ivoiceVolume.getValue()));

		// 颜色
		tc.setColorConfig("fontNum", textToColor(cNumColor.getText()));
		tc.setColorConfig("fontLabel", textToColor(cLabelColor.getText()));
		tc.setColorConfig("fontUnit", textToColor(cUnitColor.getText()));
		tc.setColorConfig("fontWarn", textToColor(cWarnColor.getText()));
		tc.setColorConfig("fontShade", textToColor(cShadeColor.getText()));

		tc.setconfig("enableLogging", Boolean.toString(bEnableLogging.isSelected()));
		tc.setconfig("enableAltInformation", Boolean.toString(bEnableInformation.isSelected()));
		tc.setconfig("selectedFM0", bFMList0.getSelectedItem().toString());
		tc.setconfig("selectedFM1", bFMList1.getSelectedItem().toString());

		tc.setconfig("enableAxis", Boolean.toString(bEnableAxis.isSelected()));
		tc.setconfig("enableAxisEdge", Boolean.toString(bEnableAxisEdge.isSelected()));
		tc.setconfig("enablegearAndFlaps", Boolean.toString(bEnablegearAndFlaps.isSelected()));
		tc.setconfig("enablegearAndFlapsEdge", Boolean.toString(bEnablegearAndFlapsEdge.isSelected()));
		tc.setconfig("enableEngineControl", Boolean.toString(benableEngineControl.isSelected()));

		tc.setconfig("disableEngineInfoThrottle", Boolean.toString(!bEngineControlThrottle.isSelected()));
		tc.setconfig("disableEngineInfoPitch", Boolean.toString(!bEngineControlPitch.isSelected()));
		tc.setconfig("disableEngineInfoMixture", Boolean.toString(!bEngineControlMixture.isSelected()));
		tc.setconfig("disableEngineInfoRadiator", Boolean.toString(!bEngineControlRadiator.isSelected()));
		tc.setconfig("disableEngineInfoCompressor", Boolean.toString(!bEngineControlCompressor.isSelected()));
		tc.setconfig("disableEngineInfoLFuel", Boolean.toString(!bEngineControlLFuel.isSelected()));

	}

	public void confirm() {
		saveconfig();
		tc.saveconfig();
		tc.loadFromConfig();

		this.setVisible(false);
		tc.flag = 1;
		tc.start();
//		this.dispose();
	}

	public MainForm(Controller controller) {
		// System.setProperty("awt.useSystemAAFontSettings", "on");
		// VoidMeiMain.debugPrint("mainForm初始化了");
		width = 800;
		height = 480;
		doit = true;
		Image I = Toolkit.getDefaultToolkit().getImage("image/form1.png");
		I = I.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		this.setIconImage(I);

		tc = controller;
		moveCheckFlag = false;

		this.setUndecorated(true);
		this.setLocation(VoidMeiMain.screenWidth / 2 - width / 2, VoidMeiMain.screenHeight / 2 - height / 2);
		this.setFont(VoidMeiMain.defaultFont);
		this.setSize(width, height);

//		setFrameOpaque();// 窗口透明
		String ti = VoidMeiMain.appName + " v" + VoidMeiMain.version;
		if (VoidMeiMain.debug)
			ti = ti + "beta";
//		ti=ti+"　　————"+VoidMeiMain.appTooltips;
		setTitle(ti);
		this.setShowMaximizeButton(false);
		this.getWebRootPaneUI().getTitleComponent().getComponent(1)
				.setFont(new Font(VoidMeiMain.defaultFont.getName(), Font.PLAIN, 14));// 设置title字体
		// this.getWebRootPaneUI().getWindowButtons().setButtonsInnerShadeWidth(0);
		// this.getWebRootPaneUI().getWindowButtons().setButtonsShadeWidth(0);
		this.getWebRootPaneUI().getWindowButtons().setBorderColor(new Color(0, 0, 0, 0));
		// this.getWebRootPaneUI().getWindowButtons().setButtonsDrawBottom(false);
		this.getWebRootPaneUI().getWindowButtons().setButtonsDrawSides(false, false, false, false);
		this.getWebRootPaneUI().getWindowButtons().getWebButton(0).setTopBgColor(new Color(0, 0, 0, 0));
		this.getWebRootPaneUI().getWindowButtons().getWebButton(0).setBottomBgColor(new Color(0, 0, 0, 0));
		this.getWebRootPaneUI().getWindowButtons().getWebButton(1).setTopBgColor(new Color(0, 0, 0, 0));
		this.getWebRootPaneUI().getWindowButtons().getWebButton(1).setBottomBgColor(new Color(0, 0, 0, 0));

		root = this.getContentPane();

		setDrawWatermark(true);
		setWatermark(new ImageIcon("image/watermark.png"));

//		this.getTitleComponent().setForeground(new Color(0,0,0,255));
		// this.getWebRootPaneUI().getWindowButtons().getWebButton(2).setBorderPainted(false);
		initPanel();
		initConfig();// 读入Config

		setShowResizeCorner(false);
		setDefaultCloseOperation(3);
		setVisible(true);
		this.setShadeWidth(10);

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (doit) {
			try {
				Thread.sleep(33);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			gcCount++;
			// VoidMeiMain.debugPrint("As");
			// VoidMeiMain.debugPrint("MainFrame执行了");
			root.repaint();
			if (gcCount++ % 256 == 0) {
				// VoidMeiMain.debugPrint("MainFrameGC");
				System.gc();
			}

		}
//		tc.loadFromConfig();
	}
}