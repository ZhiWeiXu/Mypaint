package com.mypaint.json.service;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

public class ImageZxing {
	public String InputZxing(String url){
		String text = ""; 
		text=url;
        String result;  
        String format = "gif";    
        //���ɶ�ά��    
        File outputFile = new File("d:"+File.separator+"rqcode.jpg");    
        try {
			MatrixToImageWriter.writeToFile(MatrixToImageWriter.toQRCodeMatrix(text, null, null), format, outputFile);
			   result = MatrixToImageWriter.decode(outputFile);  
        } catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return outputFile.getPath();    

	}
    public static void main(String gre[]){
    	ImageZxing im=new ImageZxing();
    	im.InputZxing("dasdasdasdasdasada");
    }
}
