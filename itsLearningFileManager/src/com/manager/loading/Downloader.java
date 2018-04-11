package com.manager.loading;

import java.io.File;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import com.structures.itsLearning.Element;

public class Downloader {
	
	// Properties and References
	private Settings settings;
	private WebDriver driver;
	private String path;
	
	// Parametric constructor
	public Downloader(WebDriver driver, Settings settings){
		this.setDriver(driver);
		this.setPath(System.getProperty("user.home") + "/Downloads");
		this.setSettings(settings);
	}
	
	public void download(LinkedList<Element> elements){
		
		LinkedList<String> hrefs = new LinkedList<String>();
		
		// Collect download links
		for (Element element : elements){
			
			// Layout the folder structure first
			try {
				File folder = new File(settings.getResourcesPath() + (element.getPath().substring(0, (element.getPath().length() - element.getName().length()))));
				folder.mkdirs();
			} catch (Exception e){
				e.printStackTrace();
			}
			
			// Navigate to frames where download buttons (should be) present
			driver.navigate().to(element.getHref());
			driver.switchTo().frame("ctl00_ContentPlaceHolder_ExtensionIframe");
			
			String href;
			
			// Differentiate 2 types of download buttons
			try {
				href = driver.findElement(By.xpath("//*[@id=\"ctl00_ctl00_MainFormContent_DownloadLinkForViewType\"]")).getAttribute("href");
			} catch (NoSuchElementException e){
				href = driver.findElement(By.xpath("//*[@id=\"ctl00_ctl00_MainFormContent_ResourceContent_DownloadButton_DownloadLink\"]")).getAttribute("href");
			} catch (Exception e){
				elements.remove(element);
				continue;
			}
			hrefs.add(href);
		}
		
		// Go through the download links
		for (int i = 0; i < elements.size(); i++){
			
			Element element = elements.get(i);
			String href = hrefs.get(i);
			driver.navigate().to(href);
			File file = new File(this.getPath() + "/" + element.getName());
			
			// Check if target file already exists.
			if (new File(settings.getResourcesPath() + element.getPath()).exists()){
				continue;
			}
			
			// Move file loop
			while (true) {
				
				// Check if download has finished
				while (!file.exists()) {
				    try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						JOptionPane.showMessageDialog(null, "Download process has been interrupted.");
						continue;
					}
				}
				
				// Move the file from the default download location to where element path points to
				if (!moveFile(file.getAbsolutePath(), settings.getResourcesPath() + element.getPath())){
					continue;
				} else {
					break;
				}
			}
		}
		
	}
	
	private boolean moveFile(String oldPath, String newPath){
		
		File file = new File(oldPath);
        
        // renaming the file and moving it to a new location
        if(file.renameTo(new File(newPath)))
        {
            // if file copied successfully then delete the original file
            file.delete();
            return true;
        }
        else
        {
            return false;
        }
        
	}
	
	// Get & Set
	// Driver
	public void setDriver(WebDriver driver){
		this.driver = driver;
	}
	// Path
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	// Settings
	public void setSettings(Settings settings){
		this.settings = settings;
	}
	public Settings getSettings(){
		return this.settings;
	}
	

}
