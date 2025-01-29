package org.matrixsukhoi.voidmei;

import com.alee.laf.WebLookAndFeel
import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.NativeHookException
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import mu.KotlinLogging
import org.matrixsukhoi.voidmei.VoidMeiMain.Companion.addDisplayFmListener
import org.matrixsukhoi.voidmei.VoidMeiMain.Companion.appName
import org.matrixsukhoi.voidmei.VoidMeiMain.Companion.appPort
import org.matrixsukhoi.voidmei.VoidMeiMain.Companion.appPortBkp
import org.matrixsukhoi.voidmei.VoidMeiMain.Companion.appTooltips
import org.matrixsukhoi.voidmei.VoidMeiMain.Companion.checkUpdate
import org.matrixsukhoi.voidmei.VoidMeiMain.Companion.ctr
import org.matrixsukhoi.voidmei.VoidMeiMain.Companion.debugLog
import org.matrixsukhoi.voidmei.VoidMeiMain.Companion.defaultFontName
import org.matrixsukhoi.voidmei.VoidMeiMain.Companion.defaultFontsize
import org.matrixsukhoi.voidmei.VoidMeiMain.Companion.displayFmCtrl
import org.matrixsukhoi.voidmei.VoidMeiMain.Companion.httpHeader
import org.matrixsukhoi.voidmei.VoidMeiMain.Companion.initFont
import org.matrixsukhoi.voidmei.VoidMeiMain.Companion.initSystemTray
import org.matrixsukhoi.voidmei.VoidMeiMain.Companion.initWebLaf
import org.matrixsukhoi.voidmei.VoidMeiMain.Companion.requestDest
import org.matrixsukhoi.voidmei.VoidMeiMain.Companion.requestDestBkp
import org.matrixsukhoi.voidmei.VoidMeiMain.Companion.screenSize
import org.matrixsukhoi.voidmei.VoidMeiMain.Companion.setDebugLog
import org.matrixsukhoi.voidmei.VoidMeiMain.Companion.setErrLog
import org.matrixsukhoi.voidmei.VoidMeiMain.Companion.setUTF8
import org.matrixsukhoi.voidmei.VoidMeiMain.Companion.threadPool
import org.matrixsukhoi.voidmei.prog.Controller
import org.matrixsukhoi.voidmei.prog.HttpHelper
import org.matrixsukhoi.voidmei.prog.Lang
import org.matrixsukhoi.voidmei.prog.getResource
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.image.BufferedImage
import java.io.*
import java.lang.reflect.Field
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.charset.Charset
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.regex.Pattern
import javax.swing.SwingUtilities

// import parser.StringHelper;

val logger = KotlinLogging.logger {}

class VoidMeiMain {
	fun pluginOpen() {
		runtime = Runtime.getRuntime()
		var taskList1 = -1
		try {
			val cmd1 = "cmd.exe /c  tasklist"
			val p = Runtime.getRuntime().exec(cmd1)
			val out = StringBuffer()
			val b = ByteArray(1024)
			var n: Int
			while ((p.inputStream.read(b).also { n = it }) != -1) {
				out.append(String(b, 0, n))
			}
			taskList1 = out.toString().indexOf("TaskBarHider.exe") // 检查进程
		} catch (e1: Exception) {
			e1.printStackTrace()
		}
		if (taskList1 == -1) {
			// 程序在进程中没有发现
			try {
				plugin = runtime.exec(System.getProperty("user.dir") + "\\TaskBarHider.exe")
			} catch (e: IOException) {
				// TODO Auto-generated catch block
				e.printStackTrace()
			}
		} else {
			if (debug) logger.info { "The TaskBarHider program has been opened!"}
		}

		// robot.keyRelease(17);
		// robot.keyRelease(192);
	}

	fun pluginOff() {
		plugin!!.destroy()
	}

	companion object {
		// 一些全局配置
		const val debug: Boolean = false

		// 测试FM
		@JvmField
		var fmTesting: Boolean = false

		// 调试日志
		const val debugLog: Boolean = false
		const val maxEngLoad: Int = 10

		// 用于检查最新版本
		var owner: String = "matrixsukhoi"
		var repository: String = "voidmei"

		const val gcSeconds: Long = 15
		@JvmField
		val previewColor: Color = Color(0, 0, 0, 10)

		@JvmField
		var appName: String? = null
		@JvmField
		var defaultNumfontName: String = "Roboto"
		@JvmField
		var appTooltips: String? = null
		@JvmField
		var version: String = "1.569"
		@JvmField
		var httpHeader: String? = null
		@JvmField
		var voiceVolumn: Int = 100
		@JvmField
		var defaultFontName: String = "Microsoft YaHei UI"
		@JvmField
		var defaultFont: Font? = null
		@JvmField
		var defaultFontBig: Font? = null
		@JvmField
		var defaultFontBigBold: Font? = null
		@JvmField
		var defaultFontSmall: Font? = null
		@JvmField
		var defaultFontsize: Int = 0

		@JvmField
		var plugin: Process? = null
		lateinit var runtime: Runtime

		// 抗锯齿
		@JvmField
		var aaEnable: Boolean = true
		@JvmField
		var textAASetting: Any = RenderingHints.VALUE_TEXT_ANTIALIAS_GASP
		@JvmField
		var graphAASetting: Any = RenderingHints.VALUE_ANTIALIAS_ON

		//	public static Object textAASetting = RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
		var colorFailure: Color = Color(255, 69, 0, 100)
		@JvmField
		var colorWarning: Color = Color(216, 33, 13, 100)
		@JvmField
		var colorShade: Color = Color(0, 0, 0, 240)
		@JvmField
		var colorShadeShape: Color = Color(0, 0, 0, 42)
		@JvmField
		var colorUnit: Color = Color(166, 166, 166, 220)
		@JvmField
		var colorLabel: Color = Color(27, 255, 128, 166)
		@JvmField
		var colorNum: Color = Color(27, 255, 128, 240)

		@JvmField
		var requestDest: SocketAddress? = null // = new InetSocketAddress("127.0.0.1", 8111);
		@JvmField
		var requestDestBkp: SocketAddress? = null // = new InetSocketAddress("127.0.0.1", 9222);
		@JvmField
		var appPort: Int = 0
		@JvmField
		var appPortBkp: Int = 0

		// 线程休眠时间
		@JvmField
		var threadSleepTime: Long = 33

		// 图形环境
		lateinit var environment: GraphicsEnvironment
		@JvmField
		var screenWidth: Int = 0
		@JvmField
		var screenHeight: Int = 0
		lateinit var fonts: Array<String>

		@JvmField
		var ctr: Controller? = null

		@JvmField
		var displayFm: Boolean = true
		@JvmField
		var displayFmCtrl: Boolean = false

		// 空鼠标指针
		@JvmField
		var cursorImg: BufferedImage = BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB)
		@JvmField
		var blankCursor: Cursor = Toolkit.getDefaultToolkit().createCustomCursor(
			cursorImg, Point(0, 0),
			"blank cursor"
		)

		// 是否开启
		@JvmField
		var drawFontShape: Boolean = false
		@JvmField
		var threadPool: ExecutorService? = null
		val javaVersion: String
			get() {
				runtime = Runtime.getRuntime()
				try {
					val cmd1 = "java -version"
					val p = runtime.exec(cmd1)
					/* 读取执行结果 */
					val br = BufferedReader(InputStreamReader(p.errorStream, "UTF-8"))
					var line: String? = null
					val sb = StringBuilder()
					while ((br.readLine().also { line = it }) != null) {
						sb.append(line)
					}
					p.inputStream.close()

					/* 正则提取版本 */
					val pt = Pattern.compile("\"[0-9].[0-9].*\"")
					val m = pt.matcher(sb.toString())


					//			app.debugPrint("输出"+sb.toString());
					if (m.find()) {
						val ret = m.group(0)
						return ret
					}
				} catch (e1: Exception) {
					e1.printStackTrace()
				}
				return "0.0"
			}

		@Deprecated("Don't use this irregular logging method.", ReplaceWith("logger.info { }"))
		fun debugPrint(t: String?) {
			println(t)
		}

		fun initSystemTray() {
			if (SystemTray.isSupported()) {
				val tray = SystemTray.getSystemTray()
				val image = Toolkit.getDefaultToolkit().getImage("image/16x16.png")
				val icon = TrayIcon(image)
				icon.toolTip = appName
				val p = PopupMenu("")
				val close = MenuItem(Lang.close)
				val about = MenuItem(Lang.about)
				close.font = defaultFont
				about.font = defaultFont
				p.font = defaultFont
				close.addActionListener {
					tray.remove(icon)
					System.exit(0)
				}
				about.addActionListener { // Controller.s
					Controller.notificationtimeAbout(Lang.aboutcontentsub2, 24000)
					Controller.notificationtimeAbout(Lang.aboutcontentsub1, 16000)
					Controller.notificationtimeAbout(Lang.aboutcontent, 8000)
				}
				p.add(about)
				p.add(close)
				icon.popupMenu = p

				// left click
				icon.addMouseListener(object : MouseAdapter() {
					override fun mouseClicked(e: MouseEvent) {
						if (e.button == MouseEvent.BUTTON1) {
							ctr?.stop()
							ctr = Controller()
						}
					}
				})

				try {
					tray.add(icon)
				} catch (e1: AWTException) {
					// TODO Auto-generated catch block
					// e1.printStackTrace();
					logger.error { Lang.failaddtoTray }
				}
			}
		}

		fun checkOS() {
			if (System.getProperty("os.version").toFloat() < 6.0) {
				Controller.notificationtime(Lang.Systemerror, 10000)
				try {
					Thread.sleep(10000)
				} catch (e: InterruptedException) {
					// TODO Auto-generated catch block
					e.printStackTrace()
				}
			}
		}

		fun initFont() {
			environment = GraphicsEnvironment.getLocalGraphicsEnvironment()

			// 遍历所有font
			val file = getResource("/fonts")
			if (file != null) {
				println(file.absolutePath)
			}
			val fileList = file?.list()
			if (fileList != null) {
				for (i in fileList.indices) {
					try {
						//create the font to use. Specify the size!

						val customFont = Font.createFont(Font.TRUETYPE_FONT, File("fonts/" + fileList[i]))

						//register the font
						environment.registerFont(customFont)
					} catch (e: IOException) {
						e.printStackTrace()
					} catch (e: FontFormatException) {
						e.printStackTrace()
					}
				}
			}


			fonts = environment.availableFontFamilyNames // 获得系统字体

			var findFont = false
			for (i in fonts.indices) {
				// debugPrint(fonts[i]);
				if (fonts[i] == defaultFontName) {
					findFont = true
				}
			}
			if (!findFont) {
				logger.warn { "Fonts cannot find!" }
				defaultFontName = if (defaultFontName == "Microsoft YaHei UI") "宋体" else "Arial"
			}
			defaultFont = Font(defaultFontName, Font.PLAIN, defaultFontsize)
			defaultFontBig = Font(defaultFontName, Font.PLAIN, defaultFontsize + 2)
			defaultFontBigBold = Font(defaultFontName, Font.BOLD, defaultFontsize + 4)
			defaultFontSmall = Font(defaultFontName, Font.PLAIN, defaultFontsize - 2)
			//environment = null
		}

		val screenSize: Unit
			get() {
				screenWidth = Toolkit.getDefaultToolkit().screenSize.width
				screenHeight = Toolkit.getDefaultToolkit().screenSize.height
			}

		fun setDebugLog(path: String?) {
			var out: PrintStream? = null
			try {
				out = PrintStream(path)
			} catch (e: FileNotFoundException) {
				// TODO Auto-generated catch block
				e.printStackTrace()
			}
			System.setOut(out)
		}

		fun setErrLog(path: String?) {
			var out: PrintStream? = null
			try {
				out = PrintStream(path)
			} catch (e: FileNotFoundException) {
				// TODO Auto-generated catch block
				e.printStackTrace()
			}
			System.setErr(out)
		}

		fun setUTF8() {
			if (System.getProperty("file.encoding") != "UTF-8") {
				logger.info { "Default Charset=" + Charset.defaultCharset() }
				logger.info { "file.encoding=" + Charset.defaultCharset().displayName() }
				logger.info { "Default Charset=" + Charset.defaultCharset() }
				System.setProperty("file.encoding", "UTF-8")
				val charset: Field
				try {
					charset = Charset::class.java.getDeclaredField("defaultCharset")

					charset.isAccessible = true
					charset[null] = null
				} catch (e: NoSuchFieldException) {
					// TODO Auto-generated catch block
					e.printStackTrace()
				} catch (e: SecurityException) {
					// TODO Auto-generated catch block
					e.printStackTrace()
				} catch (e: IllegalArgumentException) {
					// TODO Auto-generated catch block
					e.printStackTrace()
				} catch (e: IllegalAccessException) {
					// TODO Auto-generated catch block
					e.printStackTrace()
				}
			}
		}

		fun checkUpdate() {
			val httpClient: HttpHelper = HttpHelper()
			try {
				/* 异步请求 */
				threadPool!!.submit<Any?> {
					var res: String =
						httpClient.sendGetURL("https://api.github.com/repos/" + owner + "/" + repository + "/releases/latest")
					// debugPrint(res);
					/* 截取tag_name */
					val sidx = res.indexOf("tag_name")
					val eidx = res.indexOf(",", sidx)
					res = res.substring(sidx, eidx)
					/* 正则匹配版本号 */
					val pt = Pattern.compile("[0-9].([0-9])*")
					val m = pt.matcher(res)
					if (m.find()) {
						val latestVersion = m.group(0)
						logger.info { "latest version is:$latestVersion" }
						if (version.toDouble() < latestVersion.toDouble()) {
							val notice = "A newer version is released on github, version: $latestVersion"
							Controller.notificationtimeAbout(String.format(notice), 5000)
						}
					}
					null
				}
			} catch (e: Exception) {
				// TODO Auto-generated catch block
				e.printStackTrace()
			}
		}

		fun checkBlkxUpdate() {
			val httpClient: HttpHelper = HttpHelper()
			try {
				/* 异步请求 */
				threadPool!!.submit<Any?> {
					var res: String =
						httpClient.sendGetURL("https://api.github.com/repos/" + owner + "/" + repository + "/releases/latest")
					// debugPrint(res);
					/* 截取tag_name */
					val sidx = res.indexOf("tag_name")
					val eidx = res.indexOf(",", sidx)
					res = res.substring(sidx, eidx)
					/* 正则匹配版本号 */
					val pt = Pattern.compile("[0-9].([0-9])*")
					val m = pt.matcher(res)
					if (m.find()) {
						val latestVersion = m.group(0)
						logger.info { "Latest version is:$latestVersion" }
						if (version.toDouble() < latestVersion.toDouble()) {
							val notice = "A newer version is released on github, version: $latestVersion"
							Controller.notificationtimeAbout(String.format(notice), 5000)
						}
					}
					null
				}
			} catch (e: Exception) {
				// TODO Auto-generated catch block
				e.printStackTrace()
			}
		}

		fun addDisplayFmListener() {
			try {
				GlobalScreen.registerNativeHook()
			} catch (ex: NativeHookException) {
				logger.error { "A problem occurs when registering the native hook, details: ${ex.message}" }
			}

			GlobalScreen.addNativeKeyListener(object : NativeKeyListener {
				override fun nativeKeyPressed(e: NativeKeyEvent) {
					if (e.keyCode == NativeKeyEvent.VC_P) {
						logger.info { "Switched fmDisplay." }
						displayFm = !displayFm
					}
				}
			})
		}


		fun initWebLaf() {
			WebLookAndFeel.install()

			/*
			StyleConstants.textRenderingHints = RenderingHints(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON
			)*/

			// WebLookAndFeel.set
			WebLookAndFeel.globalControlFont = defaultFont
			WebLookAndFeel.globalTooltipFont = defaultFont
			WebLookAndFeel.globalAlertFont = defaultFont
			WebLookAndFeel.globalMenuFont = defaultFont
			WebLookAndFeel.globalAcceleratorFont = defaultFont
			WebLookAndFeel.globalTitleFont = defaultFont
			WebLookAndFeel.globalTextFont = defaultFont
			WebLookAndFeel.setDecorateFrames(true)
			WebLookAndFeel.setDecorateAllWindows(true)

			WebLookAndFeel.setAllowLinuxTransparency(true)
		}

	}


}

fun main() {
	// set output stream
	setUTF8()
	logger.info { "Currently java version:  " + System.getProperty("java.version") }

	if (debugLog) {
		setDebugLog("./output.log")
		setErrLog("./error.log")
	}

	Lang.initLang()

	// 初始化端口
	appPort = Lang.httpPort.toInt()
	appPortBkp = appPort + 1111
	requestDest = InetSocketAddress(Lang.httpIp, appPort)
	requestDestBkp = InetSocketAddress(Lang.httpIp, appPortBkp)


	// 相关变量初始化
	appName = Lang.appName
	appTooltips = Lang.appTooltips
	httpHeader = Lang.httpHeader
	defaultFontName = Lang.lanuageConfig.getValue("defaultFontName")
	defaultFontsize = Lang.lanuageConfig.getValue("defaultFontSize").toInt()

	// 线程池
	threadPool = Executors.newCachedThreadPool()

	// checkOS();
	initFont()
	screenSize

	// System.setProperty("awt.useSystemAAFontSettings", "on");
	initSystemTray()

	checkUpdate()

	if (displayFmCtrl) addDisplayFmListener()

	SwingUtilities.invokeLater { // Install WebLaF as application L&F
		initWebLaf()
		ctr = Controller()
		if (System.getProperty("java.version").indexOf("1.8") == -1) {
			Controller.notificationtimeAbout(
				String.format(
					"Detected current Java version %s. Java 1.8 is needed.",
					System.getProperty("java.version")
				), 3000
			)
		}
	}
}