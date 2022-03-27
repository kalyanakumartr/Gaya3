package org.hbs.gaya.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommonValidator
{
	private CommonValidator()
	{

	}

	public static boolean isArrayFirstNotNull(Object[] objArr)
	{
		return isNotNullNotEmpty(objArr) && objArr.length > 0 && isNotNullNotEmpty(objArr[0]);
	}

	public static boolean isCollectionFirstNotEmpty(Collection<?> collectionObj)
	{
		return isNotNullNotEmpty(collectionObj) && isNotNullNotEmpty(collectionObj.iterator().next());
	}

	public static boolean isEqual(EnumInterface strObject, EnumInterface eqParam)
	{
		return isNotNullNotEmpty(strObject) && isNotNullNotEmpty(eqParam) && strObject.name().equalsIgnoreCase(eqParam.name());
	}

	public static boolean isEqual(String strObject, EnumInterface eqParam)
	{
		return isNotNullNotEmpty(strObject) && isNotNullNotEmpty(eqParam) && strObject.equalsIgnoreCase(eqParam.name());
	}

	public static boolean isEqual(String strObject, String eqParam)
	{
		return isNotNullNotEmpty(strObject) && isNotNullNotEmpty(eqParam) && strObject.equalsIgnoreCase(eqParam);
	}

	@SuppressWarnings("rawtypes")
	public static boolean isListFirstNotEmpty(List list)
	{
		return isNotNullNotEmpty(list) && isNotNullNotEmpty(list.iterator().next());
	}

	public static boolean isNotEqual(EnumInterface strObject, EnumInterface eqParam)
	{
		return isNotNullNotEmpty(strObject) && isNotNullNotEmpty(eqParam) && !strObject.name().equalsIgnoreCase(eqParam.name());
	}

	public static boolean isNotEqual(String strObject, EnumInterface eqParam)
	{
		return isNotNullNotEmpty(strObject) && isNotNullNotEmpty(eqParam) && !strObject.equalsIgnoreCase(eqParam.name());
	}

	public static boolean isNotEqual(String strObject, String eqParam)
	{
		return isNotNullNotEmpty(strObject) && isNotNullNotEmpty(eqParam) && !strObject.equalsIgnoreCase(eqParam);
	}

	@SuppressWarnings("rawtypes")
	public static boolean isNotNullNotEmpty(Object object)
	{
		if (object instanceof String)
		{
			String objStr = (String) object;
			return !objStr.trim().equals("");
		}
		else if (object instanceof String[])
		{
			String[] objStr = (String[]) object;

			for (String stArr : objStr)
			{
				if (!isNotNullNotEmpty(stArr))
				{
					return false;
				}
			}
			return objStr.length > 0;
		}
		else if (object instanceof Integer || object instanceof Long || object instanceof Double)
		{
			return isNotNullNotEmpty(String.valueOf(object));
		}
		else if (object instanceof List)
		{
			List objList = (List) object;
			return !objList.isEmpty();
		}
		else if (object instanceof Set)
		{
			Set objSet = (Set) object;
			return !objSet.isEmpty();
		}
		else if (object instanceof Map)
		{
			Map objMap = (Map) object;
			return !objMap.isEmpty();
		}
		else if (object instanceof Collection<?>)
		{
			Collection<?> objSet = (Collection<?>) object;
			return !objSet.isEmpty();
		}
		else
		{
			return object != null;
		}
	}

	public static boolean isNotNullNotEmpty(Object object, Object nestedObject)
	{
		if (isNotNullNotEmpty(object))
		{
			return isNotNullNotEmpty(nestedObject);
		}
		return false;
	}

	public static boolean isNotNullNotEmpty(Object object, Object individualObject, Object individualObject1)
	{
		return isNotNullNotEmpty(object) && isNotNullNotEmpty(individualObject) && isNotNullNotEmpty(individualObject1);
	}

	public static boolean isNullOrEmpty(Object object)
	{
		return !isNotNullNotEmpty(object);
	}

	@SuppressWarnings("rawtypes")
	public static boolean isSetFirstNotEmpty(Set set)
	{
		return isNotNullNotEmpty(set) && isNotNullNotEmpty(set.iterator().next());
	}
}
