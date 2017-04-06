package com.varun.fbproj.service;




import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.varun.fbproj.resource.Pathuse;

public class UserImageService {

	public String uploadProfilePic(InputStream fileInputStream,
			String fileName, String token,String emailID) throws IOException {
		//uploading profile picture of user in his specific folder
		/*Properties prop=new Properties();
        String propFileName = "config.property";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        prop.load(inputStream);
        String userName = prop.getProperty("DataFilePath");*/
		
	     String userName=Pathuse.USER_PATH;
			OutputStream outputStream=null;
			OutputStream outputStream1=null;
			//getEmailId s1=new getEmailId();
			//String email=s1.getemailId(token);
			fileName=""+Calendar.getInstance().getTimeInMillis()+fileName;
			//InputStream buffer=toBufferedImage(fileInputStream,100,100);

	//		String path="/home/varun/git/fb_ki_testing/src/main/webapp/users/"+emailID+"/images/";
	
		//	String profilePicPath="/home/varun/git/fb_ki_testing/src/main/webapp/users/"+emailID+"/";
	
			String path=userName+emailID+"/images/";
			
			String profilePicPath=userName+emailID+"/";
	
			
			
			
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
			//ImageIcon icon = new ImageIcon("/home/varun/git/fb_ki_testing/src/main/webapp/users/"+emailID+"/profilePic.jpg");
			ImageIcon icon = new ImageIcon(userName+emailID+"/profilePic.jpg");

			
			BufferedImage bi = new BufferedImage(
				    icon.getIconWidth(),
				    icon.getIconHeight(),
				    BufferedImage.TYPE_INT_RGB);
				Graphics g = bi.createGraphics();
				// paint the Icon to the BufferedImage.
				icon.paintIcon(null, g, 0,0);
				g.dispose();
				bi=resize(bi, 200, 200);



//File outputfile = new File("/home/varun/git/fb_ki_testing/src/main/webapp/users/"+emailID+"/tn.jpg");
File outputfile = new File(userName+emailID+"/tn.jpg");


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
    
    
    
    
    public String uploadProfilePic2(InputStream fileInputStream,
    		String fileName, String token, String emailID,String statusid,String timelineid,String group_name) throws IOException {
    	
    	//uploading pictures posted through status in user's specific folder
    	/*Properties prop=new Properties();
        String propFileName = "config.property";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        prop.load(inputStream);
        String userName = prop.getProperty("DataFilePath");*/

        String userName=Pathuse.USER_PATH;
    	    OutputStream outputStream=null;
    		OutputStream outputStream1=null;
    		fileName=""+Calendar.getInstance().getTimeInMillis()+fileName;
    		
    		String path=userName+emailID+"/";
    		
    		String profilePicPath=userName+emailID+"/";
    		  
    		boolean check=false;
    		   DBAccess db=new DBAccess();
    		try{
    		 while(check!=true){
    		 System.out.println("trying connection in getStatus");
    		check= db.start();
    		 }
    		   if(timelineid.equals("group"))
    		   {
    		    String privacy=GroupService.getPrivacy(group_name);
    		    
    		 if(privacy.equals("public"))
    		 {
    		 
    		 String query1="update status set flag=0 where statusID=?";   
    		 PreparedStatement pstmnt=db.con.prepareStatement(query1);
    		 pstmnt.setInt(1,Integer.parseInt(statusid)); // user_id is the one sent in paramater
    		  // timelineid is the one sent in paramater
    		 
    		pstmnt.executeUpdate();
    		 
    		 }
    		 else if(privacy.equals("private"))
    		 {  
    		 System.out.println("staus ID ypppppppppppppp="+statusid);
    		 String query1="update privategroupstatus set flag=0 where statusID=?";   
    		 PreparedStatement pstmnt=db.con.prepareStatement(query1);
    		 pstmnt.setInt(1,Integer.parseInt(statusid)); // user_id is the one sent in paramater
    		  // timelineid is the one sent in paramater
    		 
    		pstmnt.executeUpdate();  
    		 }
    		 
    		   }
    		   
    		   else
    		   {
    		    
    		    String query1="update status set flag=0 where statusID=?";   
    		 PreparedStatement pstmnt=db.con.prepareStatement(query1);
    		 pstmnt.setInt(1,Integer.parseInt(statusid)); // user_id is the one sent in paramater
    		  // timelineid is the one sent in paramater
    		 
    		pstmnt.executeUpdate();
	
    		   }
    		   
    		   
    		   
    		}
    		 catch(Exception e){e.printStackTrace();}
    		 
    		   
    		 
    		try{ 
    		System.out.println("upload222222222222222"+statusid);
    		System.out.println("filesream availableeeeeeeeeeee"+fileInputStream.available());  

    		outputStream=new FileOutputStream(new File(path+fileName));
    		outputStream1=new FileOutputStream(new File(profilePicPath+statusid+".jpg"));
    		int read = 0,temp=0;
    		           byte[] bytes = new byte[1024];
    		           while ((read = fileInputStream.read(bytes)) != -1) {
    		           { 
    		           System.out.println(); 
    		           outputStream.write(bytes, 0, read);
    		           outputStream1.write(bytes, 0, read);
    		           temp=read;
    		           }
    		                
    		}outputStream.close();outputStream1.close();
    		/*ImageIcon icon = new ImageIcon("/home/vishal/git/fb_ki_testing/src/main/webapp/users/"+emailID+"/statusid.jpg");

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



    		File outputfile = new File("/home/vishal/git/fb_ki_testing/src/main/webapp/users/"+emailID+"/tn.jpg");

    		//File outputfile = new File("/home/umesh/Desktop/sem1/fb_ki_testing/src/main/webapp/users/"+emailID+"/tn.jpg");

    		ImageIO.write(bi, "jpg", outputfile);

    		*/
    		}
    		catch(Exception e)
    		{
    		e.printStackTrace();
    		}
    		finally{
    		 
    		if(outputStream!=null)
    		return "uploaded Successfully!!!!";
    		}

    		// TODO Auto-generated method stub
    		return null;
    		}
    		
}//class ends here