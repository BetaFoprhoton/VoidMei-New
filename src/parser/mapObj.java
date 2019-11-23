package parser;

import java.awt.Color;

public class mapObj {

	public class Movobj {
		public String type;
		public String color;
		public Color colorg;
		public int blink;
		public double distance;
		public String icon;
		public String iconBg;
		public float x;
		public float y;
		public float dx;
		public float dy;

	}

	public class Staobj {
		public String type;
		public String color;
		public Color colorg;
		public int blink;
		public String icon;
		public String iconBg;
		public float x;
		public float y;
	}

	public class Plaobj {
		public String type;
		public String color;
		public Color colorg;
		public int blink;
		public String icon;
		public String iconBg;
		public float x;
		public float y;
		public float dx;
		public float dy;
	}

	public class Slcobj {
		public String type;
		public String color;
		public Color colorg;
		public int blink;
		public String icon;
		public String iconBg;
		public float x;
		public float y;
		public float dx;
		public float dy;
	}

	int num;
	public Movobj mov[];
	public int movcur;
	public Staobj sta[];
	public Plaobj pla;
	public Slcobj slc;
	public int stacur;
	public double aot;
	String s;

	String getLine() {
		int bix;
		int eix;
		String buf;
		bix = s.indexOf('{');
		if (bix != -1) {
			eix = s.indexOf('}');
			buf = s.substring(bix, eix + 1);
			// System.out.println("��Ƭֵ"+buf);
			s = s.substring(eix + 1, s.length());
			// System.out.println("��Ƭ��"+s);
			return buf;
		} else
			return ("");
	}

	void parseObj(String t) {
		int bix;
		int eix;
		int quoteloc = 1;
		int flag = 0;
		String type = "";
		String color = "";
		Color colorg;
		int blink;
		String icon;
		String iconBg;
		float x;
		float y;
		float dx;
		float dy;

		boolean isPlayer = false;
		boolean isSelected = false;
		// ����type
		bix = t.indexOf('"');
		if (bix != -1) {
			eix = bix + 5;
			while (t.charAt(eix) != ':') {
				eix++;
			}
			bix = eix + 1 + quoteloc;
			eix = bix;
			while (t.charAt(eix) != '"') {
				eix++;
			}
			type = t.substring(bix, eix);
			// System.out.println(type.charAt(3));
			// ������������Color
			eix = eix + 2;
			// ����������
			while (t.charAt(eix) != '"') {
				eix++;
			}
			eix++;
			while (t.charAt(eix) != '"') {
				eix++;
			}
			eix++;
			while (t.charAt(eix) != '"') {
				eix++;
			}
			eix++;
			bix = eix;
			while (t.charAt(eix) != '"') {
				eix++;
			}
			color = t.substring(bix, eix);

			// ������������Color[]
			eix = eix + 2;
			// ��2������
			while (t.charAt(eix) != '"') {
				eix++;
			}
			eix++;
			while (t.charAt(eix) != '"') {
				eix++;
			}
			eix++;
			while (t.charAt(eix) != '[') {
				eix++;
			}
			eix++;
			bix = eix;
			while (t.charAt(eix) != ',') {
				eix++;
			}
			int red = Integer.parseInt(t.substring(bix, eix));
			// System.out.println(red);
			eix++;
			bix = eix;
			while (t.charAt(eix) != ',') {
				eix++;
			}
			int green = Integer.parseInt(t.substring(bix, eix));
			eix++;
			bix = eix;
			while (t.charAt(eix) != ']') {
				eix++;
			}
			int blue = Integer.parseInt(t.substring(bix, eix));
			colorg = new Color(red, green, blue);
			// System.out.println(colorg);

			// ������������blink
			eix = eix + 2;
			// ��2������
			while (t.charAt(eix) != '"') {
				eix++;
			}
			eix++;
			while (t.charAt(eix) != '"') {
				eix++;
			}
			eix++;
			while (t.charAt(eix) != ':') {
				eix++;
			}
			eix++;
			bix = eix;
			while (t.charAt(eix) != ',') {
				eix++;
			}
			blink = Integer.parseInt(t.substring(bix, eix));
			// System.out.println(blink);

			// ������������icon
			eix = eix + 1;
			// ����������
			while (t.charAt(eix) != '"') {
				eix++;
			}
			eix++;
			while (t.charAt(eix) != '"') {
				eix++;
			}
			eix++;
			while (t.charAt(eix) != '"') {
				eix++;
			}
			eix++;
			bix = eix;
			while (t.charAt(eix) != '"') {
				eix++;
			}
			icon = t.substring(bix, eix);
			if (icon.equals("Player"))
				isPlayer = true;

			// System.out.println(icon);

			// ������������icon_bg
			eix = eix + 2;
			// ����������
			while (t.charAt(eix) != '"') {
				eix++;
			}
			eix++;
			while (t.charAt(eix) != '"') {
				eix++;
			}
			eix++;
			while (t.charAt(eix) != '"') {
				eix++;
			}
			eix++;
			bix = eix;
			while (t.charAt(eix) != '"') {
				eix++;
			}
			iconBg = t.substring(bix, eix);
			if (iconBg.equals("none") != true)
				isSelected = true;
			// System.out.println(iconBg);
			// ������������x
			eix = eix + 2;
			// ��2������
			while (t.charAt(eix) != '"') {
				eix++;
			}
			eix++;
			while (t.charAt(eix) != '"') {
				eix++;
			}
			eix++;
			while (t.charAt(eix) != ':') {
				eix++;
			}
			eix++;
			bix = eix;
			while (t.charAt(eix) != ',') {
				eix++;
			}
			x = Float.parseFloat(t.substring(bix, eix));
			// System.out.println(x);
			// ������������y
			eix = eix + 1;
			// ��2������
			while (t.charAt(eix) != '"') {
				eix++;
			}
			eix++;
			while (t.charAt(eix) != '"') {
				eix++;
			}
			eix++;
			while (t.charAt(eix) != ':') {
				eix++;
			}
			eix++;
			bix = eix;
			while (t.charAt(eix) != ',' && t.charAt(eix) != '}') {
				eix++;
			}
			y = Float.parseFloat(t.substring(bix, eix));

			if (t.charAt(eix) == '}')
				flag = 0;
			else
				flag = 1;

			// System.out.println(t.substring(bix,eix));

			// �ٸ���type�ж��Ƿ�ȡdx��dy
			// System.out.println(flag);

			if (flag == 0) {
				// ����staobjдֵ
				if (isSelected) {
					slc.type = type;
					slc.color = color;
					slc.colorg = colorg;
					slc.blink = blink;
					slc.icon = icon;
					slc.iconBg = iconBg;
					slc.x = x;
					slc.y = y;
					slc.dx = 0;
					slc.dy = 0;
				} else {
					sta[stacur].type = type;
					sta[stacur].color = color;
					sta[stacur].colorg = colorg;
					sta[stacur].blink = blink;
					sta[stacur].icon = icon;
					sta[stacur].iconBg = iconBg;
					sta[stacur].x = x;
					sta[stacur].y = y;
					// System.out.println("sдֵ�ɹ�" + sta[stacur].toString());
					stacur++;
				}
			}
			if (flag == 1) {
				// ����movobj�ж�
				// ������������y
				// System.out.println(t);
				eix = eix + 1;
				// ��2������
				// System.out.println(t);
				// System.out.println("sad");
				while (t.charAt(eix) != '"') {
					eix++;
				}
				eix++;
				while (t.charAt(eix) != '"') {
					eix++;
				}
				eix++;
				while (t.charAt(eix) != ':') {
					eix++;
				}
				eix++;
				bix = eix;
				while (t.charAt(eix) != ',' && t.charAt(eix) != '}') {
					eix++;
				}
				// System.out.println(t.substring(bix,eix));
				dx = Float.parseFloat(t.substring(bix, eix));

				// ������������y
				eix = eix + 1;
				// ��2������
				while (t.charAt(eix) != '"') {
					eix++;
				}
				eix++;
				while (t.charAt(eix) != '"') {
					eix++;
				}
				eix++;
				while (t.charAt(eix) != ':') {
					eix++;
				}
				eix++;
				bix = eix;
				while (t.charAt(eix) != ',' && t.charAt(eix) != '}') {
					eix++;
				}
				// System.out.println(t.substring(bix,eix));
				dy = Float.parseFloat(t.substring(bix, eix));
				if (!isPlayer) {
					if (isSelected) {
						slc.type = type;

						slc.color = color;
						slc.colorg = colorg;
						slc.blink = blink;
						slc.icon = icon;
						slc.iconBg = iconBg;
						slc.x = x;
						slc.y = y;
						slc.dx = dx;
						slc.dy = dy;
					} else {

						mov[movcur].type = type;

						mov[movcur].color = color;
						mov[movcur].colorg = colorg;
						mov[movcur].blink = blink;
						mov[movcur].icon = icon;
						mov[movcur].iconBg = iconBg;
						mov[movcur].x = x;
						mov[movcur].y = y;
						// System.out.println("mдֵ�ɹ�" + mov[movcur].toString());
						movcur++;
					}
				} else {
					pla.type = type;
					pla.color = color;
					pla.colorg = colorg;
					pla.blink = blink;
					pla.icon = icon;
					pla.iconBg = iconBg;
					pla.x = x;
					pla.y = y;
					pla.dx = dx;
					pla.dy = dy;
					// System.out.println("���дֵ�ɹ�" + pla.toString());
				}
			}

		}

	}

	void processObj() {
		String sobj;
		sobj = getLine();
		while (sobj != "") {
			parseObj(sobj);
			sobj = getLine();
		}
		// testmov();//������
		// System.out.println(mov[movcur-1].x);
		//System.out.println("��Ƭ���");

	}

	void testmov() {
		int i;
		for (i = 0; i < num; i++) {
			System.out.print(mov[i].x + " ");

		}
		System.out.println(i);
	}

	void initMobj() {
		int i;
		for (i = 0; i < num; i++) {
			mov[i] = new Movobj();
			sta[i] = new Staobj();
		}
	}

	public void init() {
		num = 100;
		//System.out.println("mapObj��ʼ����");
		mov = new Movobj[num];
		sta = new Staobj[num];
		initMobj();
		pla = new Plaobj();
		slc = new Slcobj();
		s = "";

	}
	public void calculate(){
		aot = Math.abs(Math.atan(slc.dy/slc.dx) - Math.atan(pla.dy/pla.dx));
	}
	public void update(String S) {
		s = S;
		// System.out.println("��ʼֵ"+s);
		movcur = 0;
		stacur = 0;
		slc.type="";
		processObj();
		calculate();
	}
}