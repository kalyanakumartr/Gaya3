package org.hbs.gaya.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public enum EDate implements EnumInterface
{
	DD_MMMM_YYYY_HH_MM_AM_PM("dd MMMM yyyy-hh:mm a"), //
	DD_MMM_YYYY("dd-MMM-yyyy"), //
	DD_MMM_YYYY_HH_MM_AM_PM("dd-MMM-yyyy HH:mm a"), //
	DD_MMM_YYYY_HH_MM_SS_AM_PM("dd-MMM-yyyy hh:mm:ss a"), //
	DD_MMM_YYYY_SPACE("dd MMM yyyy"), //
	DD_MM_YYYY("dd/MM/yyyy"), //
	DD_MM_YYYY_("dd-MM-YYYY"), //
	DD_MM_YYYY_HH_MM("dd/MM/yyyy HH:mm"), //
	DD_MM_YYYY_HH_MM_SS_24("dd/MM/yyyy HH:mm:ss.SSS"), //
	MM_DD_YYYY_HH_MM_SS_24("MM/dd/yyyy HH:mm:ss.SSS"), //
	DD_MM_YYYY_HH_MM_("dd-MM-yyyy HH:mm"), //
	MM_DD_YYYY("MM/dd/yyyy"), //
	MM_DD_YYYY_("MM-dd-yyyy"), //
	MM_DD_YYYY_HH_MM("MM-dd-yyyy HH:mm"), //
	MM_DD_YYYY_HH_MM_SS_AM_PM("MM-dd-yyyy hh:mm:ss a"), //
	YYYYMMDD("yyyyMMdd"), //
	YYYYMMDDHHMM("yyyyMMddHHmm"), //
	YYYY_MMM_DD("yyyy-MMM-dd"), //
	YYYY_MM_DD("yyyy-MM-dd"), //
	YYYY_MM_DD_HH_MM("yyyy-MM-dd hh:mm"), //
	YYYY_MM_DD_HH_MM_24("yyyy-MM-dd HH:mm"), //
	YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd hh:mm:ss"), //
	YYYY_MM_DD_HH_MM_SS_24("yyyy-MM-dd HH:mm:ss"), //
	YYYY_MM_DD_HH_MM_SS_SSS("yyyy-MM-dd hh:mm:ss.SSS"), //
	YYYY_MM_DD_HH_MM_SS_SSS_24("yyyy-MM-dd HH:mm:ss.SSS"), //
	YYYY_MM_DD_HH_MM_SS_SSS_24_TZ("yyyy-MM-ddTHH:mm:ss.SSS"), //
	HHMM("HHmm"), //
	HH_MM("HH:mm"), //
	DD("dd");

	String format;

	EDate(String format)
	{
		this.format = format;
	}

	public String get()
	{
		return format;
	}

	public Timestamp byTimeZone(Date date, String timeZone)
	{
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone.toUpperCase()));
		calendar.setTime(date);
		return new Timestamp(calendar.getTimeInMillis());
	}

	public String byTimeZone(String destinationTZ, Timestamp timeStamp)
	{
		return byTimeZone(ServerUtilFactory.getInstance().getTimeZone(), destinationTZ, timeStamp.toString());
	}

	public String byTimeZone(Timestamp timeStamp)
	{
		return byTimeZone(ServerUtilFactory.getInstance().getTimeZone(), ServerUtilFactory.getInstance().getTimeZone(), timeStamp.toString());
	}

	public String byTimeZone(String sourceTZ, String destinationTZ, Timestamp timeStamp)
	{
		return byTimeZone(sourceTZ, destinationTZ, timeStamp.toString());
	}

	public String byTimeZone(String sourceTZ, String destinationTZ, String date)
	{
		if (date.contains(ConstUtil.DOT))
			date = date.substring(0, date.indexOf(ConstUtil.DOT));

		LocalDateTime ldt = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(EDate.YYYY_MM_DD_HH_MM_SS_24.get()));

		ZonedDateTime serverZonedDateTime = ldt.atZone(ZoneId.of(sourceTZ));

		ZonedDateTime dateTime = serverZonedDateTime.withZoneSameInstant(ZoneId.of(destinationTZ));

		return DateTimeFormatter.ofPattern(format).format(dateTime);
	}

	public String formatted(Date date)
	{
		return new SimpleDateFormat(format).format(date);
	}

	public LocalDate getDateFromMilliSeconds(long milliSeconds)
	{
		return getDateFromMilliSeconds(milliSeconds, ZoneId.systemDefault());
	}

	public LocalDate getDateFromMilliSeconds(long milliSeconds, ZoneId zone)
	{
		return Instant.ofEpochMilli(milliSeconds).atZone(zone).toLocalDate();
	}

	public Date formatted(String date)
	{
		try
		{
			return new SimpleDateFormat(format).parse(date);
		}
		catch (ParseException e)
		{
			return null;
		}
	}
}