package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/**
 * This class is responsible for saving and loading files. It is not used directly, but rather by
 * Other classes to save and load files needed for the game to operate
 * @author David
 * @version 1.0
 */
public class FileLoader 
{
	static Vector<String> fileContents = new Vector<String>();
	
	public FileLoader()
	{
		
	}
	
	public FileLoader(String path)
	{
		LoadFile(path);
	}
	
	public void LoadFileFromPath(String path)
	{
		fileContents = new Vector<String>();
		File inputFile = new File(path);

		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			
			String line = "";
			while((line = br.readLine()) != null)
			{
				fileContents.add(line);
			}
			
			br.close();
		} catch (IOException e) 
		{
			System.out.println("File not loaded successfully: " + path);
		}
	}
	
	public Vector<String> LoadFile(String path)
	{
		LoadFileFromPath(path);
		return fileContents;
	}
	
	public Vector<String> GetFileContents()
	{
		return fileContents;
	}
}
