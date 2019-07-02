package app.controller.admin;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;

import app.helper.Constants;

public class BaseController {
	private static final Logger logger = Logger.getLogger(BaseController.class);

	public Properties getProperties() {
		Properties prop = new Properties();
		try (InputStream input = new FileInputStream("src/main/resources/messages.properties")) {
			prop.load(input);
			return prop;
		} catch (IOException ex) {
			logger.error(ex);
			return null;
		}
	}

	public long setEndPagination(long count) {
		if (count % Constants.PAGESIZE == 0)
			return count / Constants.PAGESIZE;
		
		return count / Constants.PAGESIZE + 1;
	}
    
	public Model setPaginationModelObject(int pageNumber,long end,Model model) {
		long begin = Constants.BEGINPAGEFIRST;
		long limitpage = 0;
		
		if(end < Constants.LIMITOFPAGELOAD) limitpage = end;
		else {
			limitpage = Constants.LIMITOFPAGELOAD;
		if(pageNumber >= (limitpage + begin)) {
			begin = (begin+limitpage)*(pageNumber/limitpage);
			limitpage = limitpage+(begin - 1);
			if(limitpage > end) limitpage = end;
		}
		}
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", limitpage);
		model.addAttribute("currentIndex", pageNumber);
		model.addAttribute("totalPageCount", end);
		
		return model;
	}
}
