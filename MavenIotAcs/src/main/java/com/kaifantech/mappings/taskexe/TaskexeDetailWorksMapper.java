package com.kaifantech.mappings.taskexe;

import java.util.List;

import com.kaifantech.bean.taskexe.TaskexeBean;
import com.kaifantech.bean.taskexe.TaskexeDetailWorksBean;
import com.kaifantech.mappings.AppBaseMapper;

public interface TaskexeDetailWorksMapper extends AppBaseMapper<Object> {

	public List<TaskexeDetailWorksBean> find(TaskexeDetailWorksBean taskexeDetailWorksBean);

	public int add(TaskexeDetailWorksBean taskexeDetailWorksBean);

	public int updateOpflag(TaskexeDetailWorksBean taskexeDetailWorksBean);

	public int updateAllOpflag(TaskexeBean taskexe);

	public int updatAddedinfo(TaskexeDetailWorksBean taskexeDetailWorksBean);

	public void delete(TaskexeBean taskexe);

}
