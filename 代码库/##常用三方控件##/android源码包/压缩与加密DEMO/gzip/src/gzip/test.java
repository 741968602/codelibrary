 
 package gzip;  
   
 import java.io.ByteArrayInputStream;  
 import java.io.ByteArrayOutputStream;  
 import java.io.IOException;  
 import java.util.zip.GZIPInputStream;  
 import java.util.zip.GZIPOutputStream;  
   
 /**  
  *   
  *Module:          ZipUtil.java  
  *Description:    对字符串的压缩及解压  
  */  
 public class test {  
   
     public static void main(String[] args) throws IOException {  
         // 字符串超过一定的长度  
         String str ="ABCdef123中文~!@#$%^&*()_+{};/1111111111111111111111111A"
          		+
        		 "ABCdef123中文~!@#$%^&*()_+{};/1111111111111111111111111A"
         		+ "AAAAAAAAAAJDLFJDLFJDLFJLDFFFFJEIIIIIIIIIIFJJJJJJJJJJJJALLLLLLLLLLLLLLLLLLLLLL" + "ABCdef123中文~!@#$%^&*()_+{};/1111111111111111111111111AAAAAAAAAAAJDLFJDLFJDLFJLDFFFFJEIIIIIIIIIIFJJJJJJJJJJJJALLLLLLLLLLLLLLLLLLLLLL" + "ABCdef123中文~!@#$%^&*()_+{};/1111111111111111111111111AAAAAAAAAAAJDLFJDLFJDLFJLDFFFFJEIIIIIIIIIIFJJJJJJJJJJJJALLLLLLLLLLLLLLLLLLLLLL" +  
                 "LLppppppppppppppppppppppppppppppppppppppppp===========================------------------------------iiiiiiiiiiiiiiiiiiiiiii";  
         System.out.println("\n原始的字符串为------->" + str);  
         float len0=str.length();  
         System.out.println("原始的字符串长度为------->"+len0);  
   
         String ys = compress(str);  
         System.out.println("\n压缩后的字符串为----->" + ys);  
         float len1=ys.length();  
         System.out.println("压缩后的字符串长度为----->" + len1);  
   
         String jy = unCompress(ys);  
         System.out.println("\n解压缩后的字符串为--->" + jy);  
         System.out.println("解压缩后的字符串长度为--->"+jy.length());  
           
         System.out.println("\n压缩比例为"+len1/len0);  
           
         //判断  
         if(str.equals(jy)){  
             System.out.println("先压缩再解压以后字符串和原来的是一模一样的");  
         }  
     }  
   
     /**  
      * 字符串的压缩  
      *   
      * @param str  
      *            待压缩的字符串  
      * @return    返回压缩后的字符串  
      * @throws IOException  
      */  
     public static String compress(String str) throws IOException {  
         if (null == str || str.length() <= 0) {  
             return str;  
         }  
         // 创建一个新的 byte 数组输出流  
         ByteArrayOutputStream out = new ByteArrayOutputStream();  
         // 使用默认缓冲区大小创建新的输出流  
         GZIPOutputStream gzip = new GZIPOutputStream(out);  
         // 将 b.length 个字节写入此输出流  
         gzip.write(str.getBytes());  
         gzip.close();  
         // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串  
         return out.toString("ISO-8859-1");  
     }  
       
     /**  
      * 字符串的解压  
      *   
      * @param str  
      *            对字符串解压  
      * @return    返回解压缩后的字符串  
      * @throws IOException  
      */  
     public static String unCompress(String str) throws IOException {  
         if (null == str || str.length() <= 0) {  
             return str;  
         }  
         // 创建一个新的 byte 数组输出流  
         ByteArrayOutputStream out = new ByteArrayOutputStream();  
         // 创建一个 ByteArrayInputStream，使用 buf 作为其缓冲区数组  
         ByteArrayInputStream in = new ByteArrayInputStream(str  
                 .getBytes("ISO-8859-1"));  
         // 使用默认缓冲区大小创建新的输入流  
         GZIPInputStream gzip = new GZIPInputStream(in);  
         byte[] buffer = new byte[256];  
         int n = 0;  
         while ((n = gzip.read(buffer)) >= 0) {// 将未压缩数据读入字节数组  
             // 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此 byte数组输出流  
             out.write(buffer, 0, n);  
         }  
         // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串  
         return out.toString("gbk");  
     }  
   
 }  