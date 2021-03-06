package com.xiangyong.manager.core.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

@SuppressWarnings("unchecked")
public class KryoSerializer<T> implements Serializer<T> {
	
	private static final KryoFactory kryoFactory = KryoFactory.getFactory();

	public byte[] serialize(T t) {
		Kryo kryo = kryoFactory.getKryo();
		try {
			Output output = new Output(1024, 1024 * 500);
			kryo.writeClassAndObject(output, t);
			return output.toBytes();
		} finally {
			kryoFactory.returnKryo(kryo);
			kryo = null;
		}
	}

	public T deserialize(byte[] bytes){
		if(bytes == null || bytes.length == 0){
			return null;
		}
		Kryo kryo = kryoFactory.getKryo();
		try {
			Input input = new Input(bytes);
			return (T) kryo.readClassAndObject(input);
		} finally {
			kryoFactory.returnKryo(kryo);
			kryo = null;
		}
	}
}