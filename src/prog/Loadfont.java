package prog;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
 
public class Loadfont
{
    public static Font loadFont(String fontFileName, float fontSize)  //��һ���������ⲿ���������ڶ����������С
    {
        try
        {
            File file = new File(fontFileName);
            FileInputStream aixing = new FileInputStream(file);
            Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, aixing);
            Font dynamicFontPt = dynamicFont.deriveFont(fontSize);
            aixing.close();
            return dynamicFontPt;
        }
        catch(Exception e)//�쳣����
        {
            e.printStackTrace();
            return new java.awt.Font("΢���ź�", Font.PLAIN, 14);
        }
    }

}
