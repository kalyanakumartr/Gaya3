package org.hbs.gaya.util;

public enum EWrap implements EnumInterface
{
	Brace("()"), Percent("%"), Quote("'"), QuotePercent(""), Hyphen("-"), Comma(",");

	private String wrap;

	private EWrap(String eWrap)
	{
		this.wrap = eWrap;
	}

	public String enclose(Object data)
	{
		if (data != null)
		{
			String encData = (data instanceof EnumInterface) ? ((EnumInterface) data).name() : String.valueOf(data);
			if (wrap.equals(""))
			{
				return Quote.wrap + Percent.wrap + encData.trim() + Percent.wrap + Quote.wrap;
			}
			else if (wrap.equals("()"))
			{
				return "(" + encData.trim() + ")";
			}
			else if (wrap.equals("-"))
			{
				return encData.trim() + Hyphen.wrap;
			}
			else if (wrap.equals(","))
			{
				return encData.trim();
			}
			else
			{
				return wrap + encData.trim() + wrap;
			}
		}
		else if (wrap.equals(""))
		{
			return Quote.wrap + Percent.wrap + Percent.wrap + Quote.wrap;
		}

		return "";
	}

	public String enclose(Object... dataArr)
	{
		StringBuilder dataQt = new StringBuilder("");
		if (CommonValidator.isNotNullNotEmpty(dataArr) && (dataArr[0] instanceof String || dataArr[0] instanceof Integer || dataArr[0] instanceof Long || dataArr[0] instanceof Float
				|| dataArr[0] instanceof Double || dataArr[0] instanceof Boolean || dataArr[0] instanceof EnumInterface))
		{
			for (Object datum : dataArr)
			{
				dataQt.append(enclose(datum));
				dataQt.append(ConstUtil.COMMA_SPACE);
			}
			if (dataQt.toString().contains(ConstUtil.COMMA_SPACE))
			{
				return dataQt.toString().substring(0, dataQt.toString().lastIndexOf(ConstUtil.COMMA_SPACE));
			}
		}
		return dataQt.toString();
	}

	public String append(Object... dataArr)
	{
		StringBuilder dataQt = new StringBuilder("");
		if (CommonValidator.isNotNullNotEmpty(dataArr) && (dataArr[0] instanceof String || dataArr[0] instanceof Integer || dataArr[0] instanceof Long || dataArr[0] instanceof Float
				|| dataArr[0] instanceof Double || dataArr[0] instanceof Boolean || dataArr[0] instanceof EnumInterface))

		{
			for (Object datum : dataArr)
			{
				dataQt.append(enclose(datum));
			}
			return dataQt.substring(0, dataQt.length() - 1);
		}
		return dataQt.toString();
	}
}