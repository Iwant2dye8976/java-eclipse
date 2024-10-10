package TH1;

import java.io.*;

public interface SaveNLoad {
	public static void addAndSaveBinLanguage() throws IOException, ClassNotFoundException{};

	public static void loadBinLanguages() throws IOException, ClassNotFoundException{};
	
	public static void addAndSaveTextLanguage() throws IOException, ClassNotFoundException{};
	
	public static void loadTextLanguage() throws IOException, ClassNotFoundException{};
}
