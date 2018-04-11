package com.manager.loading;

import java.io.File;
import java.util.LinkedList;

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
			
			
			driver.navigate().to(element.getHref());
			driver.switchTo().frame("ctl00_ContentPlaceHolder_ExtensionIframe");
			String href ;
			
			// Differentiate 2 types of download buttons
			try {
				href = driver.findElement(By.xpath("//*[@id=\"ctl00_ctl00_MainFormContent_DownloadLinkForViewType\"]")).getAttribute("href");
			} catch (NoSuchElementException e){
				href = driver.findElement(By.xpath("//*[@id=\"ctl00_ctl00_MainFormContent_ResourceContent_DownloadButton_DownloadLink\"]")).getAttribute("href");
			} catch (Exception e){
				elements.remove(element);
				continue;
			}
			
			System.out.println(href);
			hrefs.add(href);
		}
		
		DownloadInfoFrame infoframe = new DownloadInfoFrame();
		infoframe.setVisible(true);
		
		// Go through the download links
		for (int i = 0; i < elements.size(); i++){
			
			Element element = elements.get(i);
			String href = hrefs.get(i);
			driver.navigate().to(href);
			infoframe.update(element.getName());
			
			// Move file loop
			while (true) {
				if (!moveFile(this.getPath() + "/" + element.getName(), settings.getResourcesPath() + element.getPath())){
					continue;
				} else {
					break;
				}
			}
		}
		
		infoframe.setVisible(false);
		
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
