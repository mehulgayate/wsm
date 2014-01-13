package com.wsm.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

	public Date provideDate(String dateString) throws ParseException{

		DateFormat dateFormatter=new SimpleDateFormat("yyyy-MM-dd");
		return dateFormatter.parse(dateString);
	}

	public String provideDateString(Date date) throws ParseException{

		DateFormat dateFormatter=new SimpleDateFormat("yyyy-MM");
		return dateFormatter.format(date);
	}

}
