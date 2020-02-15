package com.calculatedfun.erp.plugin;

import java.util.List;

import com.calculatedfun.erp.plugin.PageView;

public class PageView {
	private List<?> records;
	private long pageCount;
	private int pageSize = 10;
	private int pageNow = 1;
	private long rowCount;
	private int startPage;
	private int pagecode = 10;

	public PageView() {
	}

	public int getFirstResult() {
		return (this.pageNow - 1) * this.pageSize;
	}

	public int getPagecode() {
		return pagecode;
	}

	public void setPagecode(int pagecode) {
		this.pagecode = pagecode;
	}

	public PageView(int pageSize, int pageNow) {
		this.pageSize = pageSize;
		this.pageNow = pageNow;
	}

	public PageView(int pageNow) {
		this.pageNow = pageNow;
		startPage = (this.pageNow - 1) * this.pageSize;
	}

	public void setQueryResult(long rowCount, List<?> records) {
		setRowCount(rowCount);
		setRecords(records);
	}

	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
		setPageCount(
				this.rowCount % this.pageSize == 0 ? this.rowCount / this.pageSize : this.rowCount / this.pageSize + 1);
	}

	public List<?> getRecords() {
		return records;
	}

	public PageView setRecords(List<?> records) {
		this.records = records;
		return this;
	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public long getPageCount() {
		return pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getRowCount() {
		return rowCount;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}

	@Override
	public String toString() {
		return "PageView [ pageCount=" + pageCount + ", pageSize=" + pageSize + ", pageNow=" + pageNow + ", rowCount="
				+ rowCount + ", startPage=" + startPage + ", pagecode=" + pagecode + "]";
	}
}
