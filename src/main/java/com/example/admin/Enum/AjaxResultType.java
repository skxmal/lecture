package com.example.admin.Enum;

import lombok.Getter;

@Getter
public enum AjaxResultType {
	SUCCESS("SUCCESS"),
	FAIL("FAIL");
	
	String value;
	
	AjaxResultType(String value){
		this.value = value;
	}
}
