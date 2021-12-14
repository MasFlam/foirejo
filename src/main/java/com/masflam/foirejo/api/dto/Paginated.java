package com.masflam.foirejo.api.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Paginated {
	private Integer pageno;
	private Integer pagesz;
	
	public Paginated(Integer pageno, Integer pagesz) {
		this.pageno = pageno;
		this.pagesz = pagesz;
	}
	
	public Integer getPageno() {
		return pageno;
	}
	
	public void setPageno(Integer pageno) {
		this.pageno = pageno;
	}
	
	public Integer getPagesz() {
		return pagesz;
	}
	
	public void setPagesz(Integer pagesz) {
		this.pagesz = pagesz;
	}
}
