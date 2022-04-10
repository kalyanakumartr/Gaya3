package org.hbs.gaya.util;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.tags.Param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataTableParam extends Param implements Serializable, IDataTableParam {
	private static final long serialVersionUID = 763667035218149349L;

	public static DataTableParam init(HttpServletRequest request) {
		return DataTableParam.builder()//
				.page(Integer.parseInt(request.getParameter("pagination[page]")))//
				.pages(Integer.parseInt(request.getParameter("pagination[pages]")))//
				.perpage(Integer.parseInt(request.getParameter("pagination[perpage]")))//
				.total(Integer.parseInt(request.getParameter("pagination[total]")))//
				.generalSearch(request.getParameter("query[generalSearch]"))//
				.build();

	}

	public int page = 0;
	public int pages = 0;
	public int perpage = 10;
	public int total = 0;
	public String sort = "asc";
	public String field = "";
	public String generalSearch = "";
	public HttpServletRequest request;

	DataTableParam(HttpServletRequest request) {
		this.request = request;
	}

}
