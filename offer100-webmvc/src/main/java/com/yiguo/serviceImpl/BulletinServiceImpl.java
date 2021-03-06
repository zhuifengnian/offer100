package com.yiguo.serviceImpl;

import com.yiguo.bean.Bulletin;
import com.yiguo.dao.BaseMapper;
import com.yiguo.dao.BulletinMapper;
import com.yiguo.service.BulletinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("Bulletin")
@Transactional
public class BulletinServiceImpl extends AbstractBaseServiceImpl<Integer, Bulletin> implements BulletinService {
@Autowired
	BulletinMapper dao;

	@Override
	public BaseMapper<Integer, Bulletin> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}


	@Override
	public List<Bulletin> getAll() {
		return dao.query();
	}

	@Override
	public int findById(Integer id) {
		return dao.findById(id);
	}

}
