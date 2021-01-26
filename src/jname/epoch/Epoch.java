package jname.epoch;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.Base64;

public class Epoch {
	private static String MAGIC_HEADER;
	static {
		MAGIC_HEADER = Base64.getEncoder().encodeToString("EPOCH".getBytes());
	}

	public static void main(String[] args) {
		try {
			String type = args[0];
			File fileOrDir = new File(args[1]);
			rename(fileOrDir, type);
		} catch (Exception e) {
			System.out.println("error: " + e.toString());
			System.out.println("usage: java -jar epoch.jar <decode|encode> <file|dir>");
		}
	}

	private static void rename(File file, String type) {
		try {
			if (file.isDirectory()) {
				for (File var0 : file.listFiles())
					rename(var0, type);
			}
			String genericName = file.getName();
			String modifyName = "";
			switch (type) {
			case "decode":
			    modifyName=new String(Base64.getDecoder().decode(genericName.substring(genericName.indexOf("_")+1,genericName.length())));
				break;
			case "encode":
				modifyName = MAGIC_HEADER + "_" + Base64.getEncoder().encodeToString(genericName.getBytes());
				break;
			default:
				return;
			}
			if(file.isDirectory()) file.renameTo(new File(file.getParentFile().getAbsolutePath()+FileSystems.getDefault().getSeparator()+modifyName));
			else file.renameTo(new File(dir(file)+FileSystems.getDefault().getSeparator()+modifyName));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("modify error: " + file.getName());
		}
	}
    private static String dir(File file) {
    	return !file.isDirectory()?dir(file.getParentFile()):file.getAbsolutePath();
    }
}
