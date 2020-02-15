package com.kaifantech.component.service.pi.work;

public interface IPiWorkDoneService {
	public void doneControl();

	public void addToStop(Integer forkliFtId);

	public void addToContinue(Integer forkliFtId);
}
