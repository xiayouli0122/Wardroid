package com.example.ward.file;

import java.io.File;
import java.io.FilenameFilter;

public class FilenameFileter implements FilenameFilter{
	String[] types;
	
	public FilenameFileter(){
	}
	
	public FilenameFileter(String[] types){
		super();
		this.types = types;
	}

	@Override
	public boolean accept(File dir, String filename) {
		if (dir.isDirectory()) {
			return true;
		}
		
		for(String type: types){
			if (filename.endsWith(type)) {
				return true;
			}
		}
		return false;
	}

}
