package com.tfgllopis.integracion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

public class ImageUploader implements Receiver, SucceededListener, StartedListener
{
	private File file;
	private ArrayList<String> allowedMimeTypes;
	private String imageName;
	private Image imagen;
	private Upload subirImagen;
	private Label errorL;
	private String filePath;
	
	public ImageUploader(Image imagen, Upload subirImagen, Label errorL, String filePath, String imageName)
	{
		allowedMimeTypes = new ArrayList<String>();
		allowedMimeTypes.add("image/jpeg");
		allowedMimeTypes.add("image/png");
		
		this.imagen = imagen;
		this.subirImagen = subirImagen;
		this.errorL = errorL;
		this.filePath = filePath;
		this.imageName = imageName;
	}

	public OutputStream receiveUpload(String filename, String mimeType) 
	{
     // Create and return a file output stream
		 FileOutputStream fos = null; // Output stream to write to
         try 
         {
             // Open the file for writing.
        	 filePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() +"/VAADIN/";
             file = new File(filePath + filename);
             this.imageName = filename;
             fos = new FileOutputStream(file);
         } catch (final java.io.FileNotFoundException e) 
         {
             return null;
         }
         return fos; // Return the output stream to write to
	}

	public void uploadSucceeded(SucceededEvent event) 
	{
     // Show the uploaded file in the image viewer
		imagen.setSource(new FileResource(file));
		imagen.setWidthUndefined();
		imagen.setHeightUndefined();
	}
	
	public void uploadStarted(StartedEvent event) 
	{
		String contentType = event.getMIMEType();
	    boolean allowed = false;
	    for(int i=0;i<allowedMimeTypes.size();i++)
	    {
	        if(contentType.equalsIgnoreCase(allowedMimeTypes.get(i)))
	        {
	            allowed = true;
	            break;
	        }
	    }
	    if(!allowed){
	        errorL.setValue("Tipo de imagen no válida, tipos válidos: "+allowedMimeTypes);
	    	errorL.setVisible(true);
	    	this.subirImagen.interruptUpload();
	    }else
	    {
	    	errorL.setVisible(false);
	    }

	}
	
	public String getNombreImagen()
	{
		return imageName;
	}
	
	public String getFilePath()
	{
		return filePath;
	}
}
