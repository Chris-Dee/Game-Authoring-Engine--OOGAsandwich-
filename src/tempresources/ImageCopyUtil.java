package tempresources;


import gameauthoringenvironment.leveleditor.LevelEditor;

import java.awt.Button;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.imageio.ImageIO;

import jgame.JGImage;
import jgame.impl.EngineLogic;
import jgame.platform.JGEngine;
import jgame.platform.JREImage;

public class ImageCopyUtil {
private static final String RESOURCES_PATH = "src/tempresources/";
private static final String DEFAULT_STATS = "=Platform,2,Stationary,0,0,false,blockobject,true,1";
private static final String SCR_TAG = "src/";
private static final int OBJECT_MAX_SIZE = 500;
private static final String BIG_IMAGE_TAG = "big";
private static final String FLIP_IMAGE_TAG = "180";
private int counter=0;
private static String tablePath="src/projectresources/tempTable.tbl";
private static String bgInitPath="src/gameauthoringenvironment/levelstatseditor/Resources/InitBG";
private static String buttonInitPath="src/gameauthoringenvironment/leveleditor/imagetoobject.properties";
private static String buttonInitImagePath="src/gameauthoringenvironment/leveleditor/resources/initObject.properties";

private boolean isBGChange;
public ImageCopyUtil(){
	isBGChange=true;
}
public ImageCopyUtil(int objectStats){
	isBGChange=false;
}
	public void copyImageToResources(String source, String outputName) throws IOException{
		FileChannel inputChannel=null;
		Image i=ImageIO.read(new File(source));
		JREImage image = new JREImage(i);
		image.setComponent(new Button());
		final EngineLogic logic=new EngineLogic(image, false, false);
		logic.allowScaling(true);
		logic.defineImage(outputName,""+counter , 0, image, "__", -1, -1, -1, -1);
		//logic.defineImage(new LevelEditor(null),outputName,""+counter , 0, source, "__");
		counter++;
		if(image.getSize().x<OBJECT_MAX_SIZE&&image.getSize().y<OBJECT_MAX_SIZE){
			logic.defineImage(BIG_IMAGE_TAG+outputName,""+counter , 0, image.scale(image.getSize().x*2, image.getSize().y*2), "__", -1, -1, -1, -1);
		logic.defineImage(outputName+FLIP_IMAGE_TAG,""+counter , 0, image.flip(true, false), "__", -1, -1, -1, -1);
		logic.defineImage(BIG_IMAGE_TAG+outputName+FLIP_IMAGE_TAG,""+counter , 0, image.flip(true,false).scale(image.getSize().x*2, image.getSize().y*2), "__", -1, -1, -1, -1);
		counter++;
		}
		FileChannel outputChannel=null;
		String formatFile=formatString(source);
		String[] fileName=formatFile.replace(' ', '_').split("/");
		
		try{
			inputChannel=new FileInputStream(source).getChannel();
			//This logic basically gives the last address of the URL, so the file name.
			//COpies image from source to projectresources
			outputChannel=new FileOutputStream(SCR_TAG+getFilepath(fileName[fileName.length-1])).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		}
		finally {
			
					writeToTable(outputName,getFilepath(fileName[fileName.length-1])/*formatFile*/);
					if(isBGChange)
					writeToBGInit(outputName);
					else
						writeToButtonInit(outputName);
			        inputChannel.close();
			        //outputChannel.close();
			    }
	}
	private void writeToBGInit(String name){
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(bgInitPath, true)))) {
		    out.println(name+" "+name);
		}catch (IOException e) {
		    e.printStackTrace();
		}	
	}
	//Writes to button properties initializer and button initializer.
	private void writeToButtonInit(String name){
		try(PrintWriter statsWriter = new PrintWriter(new BufferedWriter(new FileWriter(buttonInitPath, true)))) {
		    statsWriter.println(name+DEFAULT_STATS );
		   
		    Properties configProperty=new Properties();
		   configProperty.setProperty(name, RESOURCES_PATH+name);
		   FileOutputStream output=new FileOutputStream(new File(buttonInitImagePath),true);
		configProperty.store(output,null);  
		output.close();
		}
		catch (IOException e) {
		    e.printStackTrace();
		}	
	}//writes data to tempTable.tbl, so it can be defined on next opening
	private void writeToTable(String name, String file) throws IOException{
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(tablePath, true)))) {
		    out.println(name+"		-		0	"+file+"		-");
		}catch (IOException e) {
		    e.printStackTrace();
		}
	}
	//Formats string in cross format platform
	private String formatString(String file){
		String newFile=file.replace('\\', '/');
		return newFile;
	}
	//Gets path of current package
	private String getFilepath(String fileName){
		String packagePath="";
		String pkg=this.getClass().getName();
		Vector tokens=EngineLogic.tokenizeString(pkg,'.');
		for (Enumeration e=tokens.elements();e.hasMoreElements();) {
			String tok = (String)e.nextElement();
			if (e.hasMoreElements()) {
				packagePath += tok + "/";
			}
		} 
		return packagePath+fileName;
	}
}
