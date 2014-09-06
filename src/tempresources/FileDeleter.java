package tempresources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import jgame.impl.EngineLogic;
//TODO need to test the deletions, make all images defined by their URL(?), 
//and write delete methods for all presets. Maybe put all in same method?

public class FileDeleter {
	private String tableAddress="src/projectresources/tempTable.tbl";
	private String backgroundInit="src/gameauthoringenvironment/levelstatseditor/Resources/InitBG";
	ImageCopyUtil copier=new ImageCopyUtil();
EngineLogic logic=new EngineLogic(null, false, false);
private String imgInitProperties="src/gameauthoringenvironment/leveleditor/resources/initObject.properties";
	public void deleteFile(String fileToDelete){
		File file=new File(fileToDelete);
		file.delete();
		//logic.undefineImage(fileToDelete);
	}
	public void unwriteFromBGinit(String nameToRemove){
		File sourceFile=new File(backgroundInit);
		Scanner s;
		Map<String,String> contentMap=new HashMap<String,String>();
		try {
			s = new Scanner(sourceFile);
			while(s.hasNext()){
				System.out.println("ran");
				String[] str=s.nextLine().split(" ");
				if(str.length>1)
				contentMap.put(str[0],str[1]);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//sourceFile.delete();
		contentMap.remove(nameToRemove);
		File fs=new File(backgroundInit);
		PrintWriter writer;
		try {
			writer = new PrintWriter(fs);
			for(String str:contentMap.keySet())
				writer.write(str+" "+contentMap.get(str)+"\n");
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
		}
		
		
}
	private boolean checkIfTablePrint(String name, String line){
		return(line.contains(name+"	"));
	}

public void removeFromImageBar(String nameToDelete){
	File file=new File(imgInitProperties);
	Properties imgProperties=new Properties();
	try{
	imgProperties.load(new FileInputStream(imgInitProperties));
	imgProperties.remove(nameToDelete);
	OutputStream out=new FileOutputStream(imgInitProperties);
	imgProperties.store(out,null);
	}
	catch(IOException e){
		e.printStackTrace();
	}
}
}