package org.hbs.gaya.dao;

import java.io.Serializable;
import java.util.Map;

public interface SequenceDao extends Serializable
{
	String create(String keyName, Map<String, String> dataMap);

	String create(String keyName);
}