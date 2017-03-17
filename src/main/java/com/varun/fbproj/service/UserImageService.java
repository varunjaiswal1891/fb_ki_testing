package com.varun.fbproj.service;




import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class UserImageService {

	public String uploadProfilePic(InputStream fileInputStream,
			String fileName, String token,String emailID) {
			OutputStream outputStream=null;
			OutputStream outputStream1=null;
			//getEmailId s1=new getEmailId();
			//String email=s1.getemailId(token);
			fileName=""+Calendar.getInstance().getTimeInMillis()+fileName;
			//InputStream buffer=toBufferedImage(fileInputStream,100,100);

			String path="/home/varun/git/fb_ki_testing/src/main/webapp/users/"+emailID+"/images/";
			//System.out.print("/home/umesh/Desktop/sem1/fb_ki_testing/src/main/webapp/users/"+emailID+"/images/");
			String profilePicPath="/home/varun/git/fb_ki_testing/src/main/webapp/users/"+emailID+"/";
			//String path="/home/umesh/Desktop/sem1/fb_ki_testing/src/main/webapp/users/"+emailID+"/images/";
			//System.out.print("/home/umesh/Desktop/sem1/fb_ki_testing/src/main/webapp/users/"+emailID+"/images/");
			//String profilePicPath="/home/umesh/Desktop/sem1/fb_ki_testing/src/main/webapp/users/"+emailID+"/";

			try{
			outputStream=new FileOutputStream(new File(path+fileName));
			outputStream1=new FileOutputStream(new File(profilePicPath+"profilePic.jpg"));
			int read = 0;
			            byte[] bytes = new byte[1024];
			            while ((read = fileInputStream.read(bytes)) != -1) {
			            { 
			            outputStream.write(bytes, 0, read);
			            outputStream1.write(bytes, 0, read);
			            }
			                
			}outputStream.close();outputStream1.close();
			ImageIcon icon = new ImageIcon("/home/varun/git/fb_ki_testing/src/main/webapp/users/"+emailID+"/profilePic.jpg");

			//ImageIcon icon = new ImageIcon("/home/umesh/Desktop/sem1/fb_ki_testing/src/main/webapp/users/"+emailID+"/profilePic.jpg");

			BufferedImage bi = new BufferedImage(
				    icon.getIconWidth(),
				    icon.getIconHeight(),
				    BufferedImage.TYPE_INT_RGB);
				Graphics g = bi.createGraphics();
				// paint the Icon to the BufferedImage.
				icon.paintIcon(null, g, 0,0);
				g.dispose();
				bi=resize(bi, 200, 200);



File outputfile = new File("/home/varun/git/fb_ki_testing/src/main/webapp/users/"+emailID+"/tn.jpg");

//File outputfile = new File("/home/umesh/Desktop/sem1/fb_ki_testing/src/main/webapp/users/"+emailID+"/tn.jpg");

ImageIO.write(bi, "jpg", outputfile);


				
			}
			catch(Exception e)
			{
			e.printStackTrace();
			}
			finally{
			 
			if(outputStream!=null)
			 return "uploaded Successfully!!!!";
			}
			
			return null;
			
			
	}
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {  
        int w = img.getWidth();  
        int h = img.getHeight();  
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());  
        Graphics2D g = dimg.createGraphics();  
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);  
        g.dispose();  
        return dimg;  
    }  

	
}//class ends here


