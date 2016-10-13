package com.modelo;

import java.lang.reflect.Field;

//@Entity
public class Entidade {
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Field f : this.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			try {
				sb.append(f.getName() + ": " + f.get(this) + ", ");
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
//	@Override
//	public boolean equals(Object obj) {
//		if (obj != null && obj instanceof this) {
//			Politico pk = (Politico) obj;
//			// TODO tratar null
//			if (cpf == null && pk.cpf == null) {
//				return super.equals(obj);
//			}
//			if (pk.cpf.equals(cpf)) {
//				return true;
//			}
//		}
//		return false;
//	}
}