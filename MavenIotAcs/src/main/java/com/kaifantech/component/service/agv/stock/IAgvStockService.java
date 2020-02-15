package com.kaifantech.component.service.agv.stock;

import java.util.Map;

public interface IAgvStockService {
	public void clearStocks(Integer agvId);

	public Map<String, String> getStocks(Integer agvId);

	public Integer getNextLayerToStock(Integer agvId, String targetAlloc);

	public Integer getNextLayerToStock(Integer agvId);

	public Integer getNextLayerToGet(Integer agvId);

	public void stockIn(Integer agvId, Integer agvStock);

	public void stockIn(Integer agvId, Integer agvStock, String allocId);

	public void stockOut(Integer agvId, Integer agvStock);

	public Integer getNextLayerToGet(Integer agvId, String targetAlloc);
}
