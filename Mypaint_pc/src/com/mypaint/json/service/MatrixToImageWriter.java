package com.mypaint.json.service;
import com.google.zxing.BarcodeFormat;  
import com.google.zxing.BinaryBitmap;  
import com.google.zxing.DecodeHintType;  
import com.google.zxing.EncodeHintType;  
import com.google.zxing.LuminanceSource;  
import com.google.zxing.MultiFormatReader;  
import com.google.zxing.MultiFormatWriter;  
import com.google.zxing.Result;  
import com.google.zxing.WriterException;  
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;  
import com.google.zxing.common.BitMatrix;  
import com.google.zxing.common.HybridBinarizer;  
  
import javax.imageio.ImageIO;  
  
import java.io.File;  
import java.io.OutputStream;  
import java.io.IOException;  
import java.util.Hashtable;  
import java.awt.image.BufferedImage;  
  
/** 
 * ʹ��ZXing2.3����������ĸ����ࡣ���Ա��롢���롣����ʹ��code����������Ҫjavase���� 
 *  
 * <br/> 
 * <br/> 
 * ���ߣ�wallimn<br/> 
 * ��ϵ��54871876@qq.com��http://wallimn.iteye.com<br/> 
 * ʱ�䣺2014��5��25�ա�������10:33:05<br/> 
 */  
public final class MatrixToImageWriter {  
  
    private static final String CHARSET = "utf-8";  
    private static final int BLACK = 0xFF000000;  
    private static final int WHITE = 0xFFFFFFFF;  
  
    /** 
     * ��ֹ����ʵ��������ʵ��Ҳû�����塣 
     */  
    private MatrixToImageWriter() {  
    }  
  
    /** 
     * ���ɾ�����һ���򵥵ĺ����������̶����������ʹ��ʾ���� 
     *  
     * <br/> 
     * <br/> 
     * ���ߣ�wallimn<br/> 
     * ʱ�䣺2014��5��25�ա�������10:41:12<br/> 
     * ��ϵ��54871876@qq.com<br/> 
     *  
     * @param text 
     * @return 
     */  
    public static BitMatrix toQRCodeMatrix(String text, Integer width,  
            Integer height) {  
        if (width == null || width < 300) {  
            width = 300;  
        }  
  
        if (height == null || height < 300) {  
            height = 300;  
        }  
        // ��ά���ͼƬ��ʽ  
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();  
        // ������ʹ�ñ���  
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);  
        BitMatrix bitMatrix = null;  
        try {  
            bitMatrix = new MultiFormatWriter().encode(text,  
                    BarcodeFormat.QR_CODE, width, height, hints);  
        } catch (WriterException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        // ���ɶ�ά��  
        // File outputFile = new File("d:"+File.separator+"new.gif");  
        // MatrixUtil.writeToFile(bitMatrix, format, outputFile);  
        return bitMatrix;  
    }  
  
    /** 
     * ��ָ�����ַ������ɶ�ά��ͼƬ���򵥵�ʹ��ʾ���� 
     *  
     * <br/> 
     * <br/> 
     * ���ߣ�wallimn<br/> 
     * ʱ�䣺2014��5��25�ա�������10:44:52<br/> 
     * ��ϵ��54871876@qq.com<br/> 
     *  
     * @param text 
     * @param file 
     * @param format 
     * @return 
     */  
    public boolean toQrcodeFile(String text, File file, String format) {  
        BitMatrix matrix = toQRCodeMatrix(text, null, null);  
        if (matrix != null) {  
            try {  
                writeToFile(matrix, format, file);  
                return true;  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
        return false;  
    }  
  
    /** 
     * ���ݵ�������ɺڰ�ͼ�� ���ߣ�wallimn<br/> 
     * ʱ�䣺2014��5��25�ա�������10:26:22<br/> 
     * ��ϵ��54871876@qq.com<br/> 
     */  
    public static BufferedImage toBufferedImage(BitMatrix matrix) {  
        int width = matrix.getWidth();  
        int height = matrix.getHeight();  
        BufferedImage image = new BufferedImage(width, height,  
                BufferedImage.TYPE_INT_RGB);  
        for (int x = 0; x < width; x++) {  
            for (int y = 0; y < height; y++) {  
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);  
            }  
        }  
        return image;  
    }  
  
    /** 
     * ���ַ������һά����ľ��� 
     *  
     * <br/> 
     * <br/> 
     * ���ߣ�wallimn<br/> 
     * ʱ�䣺2014��5��25�ա�������10:56:34<br/> 
     * ��ϵ��54871876@qq.com<br/> 
     *  
     * @param str 
     * @param width 
     * @param height 
     * @return 
     */  
    public static BitMatrix toBarCodeMatrix(String str, Integer width,  
            Integer height) {  
  
        if (width == null || width < 200) {  
            width = 200;  
        }  
  
        if (height == null || height < 50) {  
            height = 50;  
        }  
  
        try {  
            // ���ֱ���  
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();  
            hints.put(EncodeHintType.CHARACTER_SET, CHARSET);  
  
            BitMatrix bitMatrix = new MultiFormatWriter().encode(str,  
                    BarcodeFormat.CODE_128, width, height, hints);  
  
            return bitMatrix;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /** 
     * ���ݾ���ͼƬ��ʽ�������ļ��� ���ߣ�wallimn<br/> 
     * ʱ�䣺2014��5��25�ա�������10:26:43<br/> 
     * ��ϵ��54871876@qq.com<br/> 
     */  
    public static void writeToFile(BitMatrix matrix, String format, File file)  
            throws IOException {  
        BufferedImage image = toBufferedImage(matrix);  
        if (!ImageIO.write(image, format, file)) {  
            throw new IOException("Could not write an image of format "  
                    + format + " to " + file);  
        }  
    }  
  
    /** 
     * ������д�뵽������С� ���ߣ�wallimn<br/> 
     * ʱ�䣺2014��5��25�ա�������10:27:58<br/> 
     * ��ϵ��54871876@qq.com<br/> 
     */  
    public static void writeToStream(BitMatrix matrix, String format,  
            OutputStream stream) throws IOException {  
        BufferedImage image = toBufferedImage(matrix);  
        if (!ImageIO.write(image, format, stream)) {  
            throw new IOException("Could not write an image of format "  
                    + format);  
        }  
    }  
  
    /** 
     * ���룬��Ҫjavase���� 
     *  
     * <br/> 
     * <br/> 
     * ���ߣ�wallimn<br/> 
     * ʱ�䣺2014��5��25�ա�������11:06:07<br/> 
     * ��ϵ��54871876@qq.com<br/> 
     *  
     * @param file 
     * @return 
     */  
    public static String decode(File file) {  
  
        BufferedImage image;  
        try {  
            if (file == null || file.exists() == false) {  
                throw new Exception(" File not found:" + file.getPath());  
            }  
  
            image = ImageIO.read(file);  
  
            LuminanceSource source = new BufferedImageLuminanceSource(image);  
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));  
  
            Result result;  
  
            // �������ñ��뷽ʽΪ��utf-8��  
            Hashtable hints = new Hashtable();  
            hints.put(DecodeHintType.CHARACTER_SET, CHARSET);  
  
            result = new MultiFormatReader().decode(bitmap, hints);  
  
            return result.getText();  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        return null;  
    }  
}  