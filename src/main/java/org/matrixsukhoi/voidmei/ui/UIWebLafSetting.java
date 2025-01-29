package org.matrixsukhoi.voidmei.ui;

import java.awt.Color;

import com.alee.laf.rootpane.WebFrame;
import org.matrixsukhoi.voidmei.VoidMeiMain;

public class UIWebLafSetting {

	static final Color transparent = new Color(0, 0, 0, 0);
	public static void setWindowFocus(WebFrame frame){
		frame.setShowWindowButtons(false);
		frame.setShowTitleComponent(false);
		frame.setShowResizeCorner(false);
		
		frame.setDefaultCloseOperation(3);
		
		frame.setIgnoreRepaint(true);
//		frame.setRound(100);
//		frame.setUndecorated(false);
//		frame.setDefaultLookAndFeelDecorated(false);
		
		frame.setAlwaysOnTop(true);
		frame.setFocusableWindowState(false);// 取消窗口焦点
		frame.setFocusable(false);
		frame.setCursor(VoidMeiMain.blankCursor);
		frame.setVisible(true);
	}
	
	public static void setWindowOpaque(WebFrame frame){
		frame.getWebRootPaneUI().setMiddleBg(transparent);// 中部透明
		frame.getWebRootPaneUI().setTopBg(transparent);// 顶部透明
		frame.getWebRootPaneUI().setBorderColor(transparent);// 内描边透明
		frame.getWebRootPaneUI().setInnerBorderColor(transparent);// 外描边透明
		
		setWindowFocus(frame);
		
	}
}
