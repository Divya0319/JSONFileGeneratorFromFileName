package com.json.jsongenerator;

import java.io.File;

public class JsonGeneratorForRenamingFilesNumerically {
	static String folderName = "floorPlans";

	public static void main( String[] args )
    {
    	// this line gets reference of specified directory
        File directoryPath = new File("D:\\Personal Projects\\Androshow Github pages API\\divya0319.github.io\\" + folderName);
        // this line gets contents of that directory, the names of files and folders using list()
        File[] files = directoryPath.listFiles();
        System.out.println("List of files before rename : ");
        
        for(File f : files) {
        	System.out.println(f.getName());
        }
        
        for(int i = 0; i < files.length; i++) {
        	files[i].renameTo(new File("" + i+1 + ".jpeg"));
        }
              
        System.out.println("List of files after rename : ");
        
        for(File f : files) {
        	System.out.println(f.getName());
        }
        
    }

}
