package facerecognition.gui;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import facerecognition.utils.Utils;
import facerecognition.javafaces.FaceRec;
import facerecognition.javafaces.FaceRecError;
import facerecognition.javafaces.MatchResult;

public class FaceRecMain{
	public static void main(String [] args){
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	FaceRecManager view=new FaceRecManager("FACE RECOGNITION");
            	view.setVisible(true);
                FaceRec model=new FaceRec();
            	new SimpleController(view,model);
            }
        });
	}
	public static void debug(String msg){
		System.out.println(msg);
	}
}

class SimpleController{
	private FaceRecManager view;
	private FaceRec model;
	private SimpleValidator sValidator;
	private StringBuilder errorMsg;
	public SimpleController(FaceRecManager v,FaceRec m){
		view = v;
		view.addOKButtonListener(new SimpleButtonListener());
		model = m;
		sValidator = new SimpleValidator();
	}
	private void handleUserInputs(File selectedFile,File selectedFolder,String eigenFaces,String threshold){
		view.displayMatchingImage(null);
		try{
			if (sValidator.validateAllInput(selectedFile,selectedFolder,eigenFaces, threshold)){
				//view.displaySelectedImage(selectedFile);
				MatchResult r = model.processSelections(selectedFile.getPath(),selectedFolder.getPath(),eigenFaces, threshold);
				if (r.getMatchSuccess()){
					view.displayMatchingImage(new File(r.getMatchFileName()));
                                        File fi = new File(r.getMatchFileName());
					view.displayMessage(selectedFile.getPath() + " matches " + fi.getName()/*r.getMatchFileName()*/+" at distance="+r.getMatchDistance());
				}else{
					view.displayMessage("match failed:"+r.getMatchMessage());
				}
			}else{
				view.displayMessage(errorMsg.toString());
			}
		}catch (FaceRecError e) {
			view.displayMessage(e.getMessage());
		}
	}
	class SimpleButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent evt) {
			view.clearImageDisplay();
			view.displayMessage("processing images...");
			String intEntry = view.getNumFacesEntry();
			String decEntry = view.getThresholdEntry();
			File selectedFile = view.getSelectedFile();
			File selectedFolder = view.getSelectedFolder();
			handleUserInputs(selectedFile,selectedFolder,intEntry,decEntry);
		}
	}
	
	class SimpleValidator{
		public boolean validateAllInput(File file,File dir,String nField,String dField) throws FaceRecError{
			boolean allvalidated = false;
			errorMsg = new StringBuilder();
			boolean textFieldEntriesValid = validateTextFields(nField,dField);
			boolean selectedFileValid;
			try {
				selectedFileValid = validateFileSelection(file);
			} catch (IOException e) {
				throw new FaceRecError(e.getMessage());
			}
			boolean selectedDirectoryValid = validateFolderSelection(dir);
			allvalidated = textFieldEntriesValid && selectedFileValid && selectedDirectoryValid   ;
			return allvalidated;
		}
		private boolean validateTextFields(String nField,String dField){
			boolean isValid = false;
			try{
				Integer.parseInt(nField);
				Double.parseDouble(dField);
				isValid = true;
			}catch(NumberFormatException e){
				errorMsg.append("enter correct values in textfields");
			}
			return isValid;
		}
		private boolean validateFolderSelection(File dir){
			boolean isValidFolder = false;
			if ( (dir!=null) && (dir.isDirectory()) ){
				isValidFolder = true;
			}else{
				errorMsg.append("\nselect a directory");
				isValidFolder = false;
			}
			return isValidFolder;
		}
		private boolean validateFileSelection(File file) throws IOException{
			boolean isValidFile = false;
			if ( (file!=null) && ( file.isFile() ) && ( Utils.isImageFile(file.getPath() ))  ){
				isValidFile = true;
			}else{
				errorMsg.append("\nselect an image file");
				isValidFile = false;
			}
			return isValidFile;
		}
	}
}
