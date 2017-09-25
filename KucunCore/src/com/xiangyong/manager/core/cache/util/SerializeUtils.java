package com.xiangyong.manager.core.cache.util;

import java.io.Serializable;
import org.apache.commons.lang3.SerializationUtils;

public class SerializeUtils
{
  public static Object deserialize(byte[] bytes)
  {
    if (isEmpty(bytes)) {
      return null;
    }

    return SerializationUtils.deserialize(bytes);
  }

  public static boolean isEmpty(byte[] data)
  {
    return (data == null) || (data.length == 0);
  }

  public static byte[] serialize(Object object)
  {
    if (object == null) {
      return new byte[0];
    }

    return SerializationUtils.serialize((Serializable)object);
  }
}