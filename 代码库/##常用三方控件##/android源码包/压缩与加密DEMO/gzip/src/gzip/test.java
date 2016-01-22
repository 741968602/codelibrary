 
 package gzip;  
   
 import java.io.ByteArrayInputStream;  
 import java.io.ByteArrayOutputStream;  
 import java.io.IOException;  
 import java.util.zip.GZIPInputStream;  
 import java.util.zip.GZIPOutputStream;  
   
 /**  
  *   
  *Module:          ZipUtil.java  
  *Description:    ���ַ�����ѹ������ѹ  
  */  
 public class test {  
   
     public static void main(String[] args) throws IOException {  
         // �ַ�������һ���ĳ���  
         String str ="ABCdef123����~!@#$%^&*()_+{};/1111111111111111111111111A"
          		+
        		 "ABCdef123����~!@#$%^&*()_+{};/1111111111111111111111111A"
         		+ "AAAAAAAAAAJDLFJDLFJDLFJLDFFFFJEIIIIIIIIIIFJJJJJJJJJJJJALLLLLLLLLLLLLLLLLLLLLL" + "ABCdef123����~!@#$%^&*()_+{};/1111111111111111111111111AAAAAAAAAAAJDLFJDLFJDLFJLDFFFFJEIIIIIIIIIIFJJJJJJJJJJJJALLLLLLLLLLLLLLLLLLLLLL" + "ABCdef123����~!@#$%^&*()_+{};/1111111111111111111111111AAAAAAAAAAAJDLFJDLFJDLFJLDFFFFJEIIIIIIIIIIFJJJJJJJJJJJJALLLLLLLLLLLLLLLLLLLLLL" +  
                 "LLppppppppppppppppppppppppppppppppppppppppp===========================------------------------------iiiiiiiiiiiiiiiiiiiiiii";  
         System.out.println("\nԭʼ���ַ���Ϊ------->" + str);  
         float len0=str.length();  
         System.out.println("ԭʼ���ַ�������Ϊ------->"+len0);  
   
         String ys = compress(str);  
         System.out.println("\nѹ������ַ���Ϊ----->" + ys);  
         float len1=ys.length();  
         System.out.println("ѹ������ַ�������Ϊ----->" + len1);  
   
         String jy = unCompress(ys);  
         System.out.println("\n��ѹ������ַ���Ϊ--->" + jy);  
         System.out.println("��ѹ������ַ�������Ϊ--->"+jy.length());  
           
         System.out.println("\nѹ������Ϊ"+len1/len0);  
           
         //�ж�  
         if(str.equals(jy)){  
             System.out.println("��ѹ���ٽ�ѹ�Ժ��ַ�����ԭ������һģһ����");  
         }  
     }  
   
     /**  
      * �ַ�����ѹ��  
      *   
      * @param str  
      *            ��ѹ�����ַ���  
      * @return    ����ѹ������ַ���  
      * @throws IOException  
      */  
     public static String compress(String str) throws IOException {  
         if (null == str || str.length() <= 0) {  
             return str;  
         }  
         // ����һ���µ� byte ���������  
         ByteArrayOutputStream out = new ByteArrayOutputStream();  
         // ʹ��Ĭ�ϻ�������С�����µ������  
         GZIPOutputStream gzip = new GZIPOutputStream(out);  
         // �� b.length ���ֽ�д��������  
         gzip.write(str.getBytes());  
         gzip.close();  
         // ʹ��ָ���� charsetName��ͨ�������ֽڽ�����������ת��Ϊ�ַ���  
         return out.toString("ISO-8859-1");  
     }  
       
     /**  
      * �ַ����Ľ�ѹ  
      *   
      * @param str  
      *            ���ַ�����ѹ  
      * @return    ���ؽ�ѹ������ַ���  
      * @throws IOException  
      */  
     public static String unCompress(String str) throws IOException {  
         if (null == str || str.length() <= 0) {  
             return str;  
         }  
         // ����һ���µ� byte ���������  
         ByteArrayOutputStream out = new ByteArrayOutputStream();  
         // ����һ�� ByteArrayInputStream��ʹ�� buf ��Ϊ�仺��������  
         ByteArrayInputStream in = new ByteArrayInputStream(str  
                 .getBytes("ISO-8859-1"));  
         // ʹ��Ĭ�ϻ�������С�����µ�������  
         GZIPInputStream gzip = new GZIPInputStream(in);  
         byte[] buffer = new byte[256];  
         int n = 0;  
         while ((n = gzip.read(buffer)) >= 0) {// ��δѹ�����ݶ����ֽ�����  
             // ��ָ�� byte �����д�ƫ���� off ��ʼ�� len ���ֽ�д��� byte���������  
             out.write(buffer, 0, n);  
         }  
         // ʹ��ָ���� charsetName��ͨ�������ֽڽ�����������ת��Ϊ�ַ���  
         return out.toString("gbk");  
     }  
   
 }  